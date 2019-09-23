package com.example.albertzkiki.movieapp;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.albertzkiki.movieapp.Adapter.CartAdapter;
import com.example.albertzkiki.movieapp.Adapter.CartViewHolder;
import com.example.albertzkiki.movieapp.Common.Common;
import com.example.albertzkiki.movieapp.Common.Config;
import com.example.albertzkiki.movieapp.Helper.RecyclerItemTouchHelper;
import com.example.albertzkiki.movieapp.Interface.RecyclerItemTouchHelperListerner;
import com.example.albertzkiki.movieapp.Remote.APIService;
import com.example.albertzkiki.movieapp.ViewHolder.FavMovieViewHolder;
import com.example.albertzkiki.movieapp.model.MyResponse;
import com.example.albertzkiki.movieapp.model.Notification;
import com.example.albertzkiki.movieapp.model.Order;
import com.example.albertzkiki.movieapp.Database.Database;
import com.example.albertzkiki.movieapp.model.Request;
import com.example.albertzkiki.movieapp.model.Sender;
import com.example.albertzkiki.movieapp.model.Token;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.sdsmdg.tastytoast.TastyToast;
import com.shashank.sony.fancydialoglib.Animation;
import com.shashank.sony.fancydialoglib.FancyAlertDialog;
import com.shashank.sony.fancydialoglib.FancyAlertDialogListener;
import com.shashank.sony.fancydialoglib.Icon;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import mehdi.sakout.fancybuttons.FancyButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class Cart extends Fragment implements RecyclerItemTouchHelperListerner {


    private static final int PAYPAL_REQUEST_CODE = 9999;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;

    public TextView totalPrice;
    FancyButton btnPlaceOrder;

    List<Order> cart = new ArrayList<>();
    CartAdapter adapter;

    APIService mService;
    RelativeLayout rootLayout;


    //paypall payment

    static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.PAYPAL_CLIENT_ID);


    public Cart() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cart, container, false);


        recyclerView = (RecyclerView) view.findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        totalPrice = (TextView) view.findViewById(R.id.total);
        btnPlaceOrder = (FancyButton) view.findViewById(R.id.btnPlaceOrder);

        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Request");

        mService = Common.getFCMSevice();

        rootLayout = (RelativeLayout) view.findViewById(R.id.root_layoutCart);

        //init paypal

        Intent intent = new Intent(getActivity(), PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        getActivity().startService(intent);


        //swipe to delete

        ItemTouchHelper.SimpleCallback itemTouchHelper = new RecyclerItemTouchHelper(0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelper).attachToRecyclerView(recyclerView);


        btnPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Common.IsConnectedToInternet(getContext())) {

                    if (cart.size() > 0) {

                        showAlertDialog();

                    } else {

                        TastyToast.makeText(getContext(), "Your cart is Empty", TastyToast.LENGTH_SHORT, TastyToast.ERROR).show();

                    }

                } else {

                    TastyToast.makeText(getContext(), "Please Check your Internet Connection", TastyToast.LENGTH_SHORT, TastyToast.INFO).show();
                    return;
                }
            }
        });


        loadFoodList();


        return view;

    }


    public void loadFoodList() {

        cart = new Database(getActivity()).getCarts();
        adapter = new CartAdapter(cart, this);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);

        int total = 0;

        for (Order order : cart) {

            total += (Integer.parseInt(order.getPrice())) * (Integer.parseInt(order.getQuantity()));
            Locale locale = new Locale("en", "US");
            NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
            totalPrice.setText(fmt.format(total));

        }

    }

    public void showAlertDialog() {


        new FancyAlertDialog.Builder(getActivity())
                .setTitle("Place order")
                .setBackgroundColor(Color.parseColor("#ff4063"))  //Don't pass R.color.colorvalue
                .setMessage("Confirm Your Order")
                .setNegativeBtnText("Cancel")
                .setPositiveBtnBackground(Color.parseColor("#ff4063"))  //Don't pass R.color.colorvalue
                .setPositiveBtnText("Ok")
                .setNegativeBtnBackground(Color.parseColor("#3F51B5"))  //Don't pass R.color.colorvalue
                .setAnimation(Animation.POP)
                .isCancellable(true)
                .setIcon(R.drawable.ic_cart, Icon.Visible)
                .OnPositiveClicked(new FancyAlertDialogListener() {
                    @Override
                    public void OnClick() {


                        //show paypal to payment

                        String formatAmount = totalPrice.getText().toString()
                                .replace("$", "")
                                .replace(",", "");

                        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(formatAmount),
                                "USD",
                                "BLACK DYNASTY",
                                PayPalPayment.PAYMENT_INTENT_SALE);

                        Intent intent = new Intent(getContext(), PaymentActivity.class);
                        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
                        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
                        startActivityForResult(intent, PAYPAL_REQUEST_CODE);


                    }
                })
                .OnNegativeClicked(new FancyAlertDialogListener() {
                    @Override
                    public void OnClick() {
                        TastyToast.makeText(getContext(), "Order canceled!", TastyToast.LENGTH_SHORT, TastyToast.ERROR).show();
                    }
                })
                .build();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PAYPAL_REQUEST_CODE) {

            if (resultCode == Activity.RESULT_OK) {

                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                if (confirmation != null) {

                    try {

                        String paymentDedails = confirmation.toJSONObject().toString(4);

                        JSONObject jsonObject = new JSONObject(paymentDedails);

                        Request request = new Request(
                                Common.current_user.getPhone(),
                                Common.current_user.getName(),
                                totalPrice.getText().toString(),
                                jsonObject.getJSONObject("response").getString("state"),
                                cart,
                                "0"

                        );

                        String order_number = String.valueOf(System.currentTimeMillis());

                        requests.child(order_number).setValue(request);

                        SendNotificationOrder(order_number);

                        //final Order deleteItem = ((CartAdapter) recyclerView.getAdapter()).getItem(or);

                        new Database(getActivity()).cleanCart();

                        TastyToast.makeText(getContext(), "Order confirmed!", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS).show();

                        try {

                            finalize();

                        } catch (Throwable throwable) {

                            throwable.printStackTrace();
                        }


                    } catch (JSONException e) {

                        e.printStackTrace();

                    }


//                if(confirmation !=null) {
//
//                    String state = confirmation.getProofOfPayment().getState();
//
//                    if(state.equals("approved")) {
//
//                        TastyToast.makeText(getActivity(),"yes",TastyToast.LENGTH_SHORT,TastyToast.SUCCESS).show();
//                    }else {
//
//                        TastyToast.makeText(getActivity(),"No",TastyToast.LENGTH_SHORT,TastyToast.SUCCESS).show();
//
//                    }
//
//                }


                } else if (resultCode == Activity.RESULT_CANCELED) {

                    TastyToast.makeText(getActivity(), "Payment Canceled!", TastyToast.LENGTH_SHORT, TastyToast.DEFAULT).show();
                } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {

                    TastyToast.makeText(getActivity(), "Invalid Payment!", TastyToast.LENGTH_SHORT, TastyToast.ERROR).show();

                }


            }


        }


    }


    private void SendNotificationOrder(final String order_number) {


        DatabaseReference tokens = FirebaseDatabase.getInstance().getReference("Tokens");

        Query data = tokens.orderByChild("severToken").equalTo(true);

        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    Token serverToken = postSnapshot.getValue(Token.class);
                    Notification notification = new Notification("Black Dynasty Movies", "you have a new order" + order_number);
                    Sender content = new Sender(serverToken.getToken(), notification);

                    mService.sendNotification(content)
                            .enqueue(new Callback<MyResponse>() {
                                @Override
                                public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {

//                                 if(response.code() == 200){
                                    if (response.body().success == 1) {

                                        TastyToast.makeText(getContext(), "Thank you for placing your Order", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);

                                        try {
                                            finalize();
                                        } catch (Throwable throwable) {
                                            throwable.printStackTrace();
                                        }
                                    } else {

                                        TastyToast.makeText(getContext(), "Failed", TastyToast.LENGTH_SHORT, TastyToast.ERROR);

                                    }
                                }

                                //                                }
                                @Override
                                public void onFailure(Call<MyResponse> call, Throwable t) {

                                    Log.e("Error", t.getMessage());

                                }
                            });

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if (item.getTitle().equals(Common.DELETE)) {

            deleteCart(item.getOrder());

            return true;
        }

        return true;
    }



    public void deleteCart(int position) {

        cart.remove(position);

        new Database(getContext()).cleanCart();

        for (Order item : cart) {

            new Database(getContext()).addtoCart(item);

        }

        loadFoodList();

    }


    @Override
    public void onSwipe(RecyclerView.ViewHolder viewHolder, int direction, int postion) {


            String name = ((CartAdapter) recyclerView.getAdapter()).getItem(viewHolder.getAdapterPosition()).getProductName();

            final Order deleteItem = ((CartAdapter) recyclerView.getAdapter()).getItem(viewHolder.getAdapterPosition());

            final int deleteIndex = viewHolder.getAdapterPosition();

            adapter.removeItem(deleteIndex);

            new Database(getContext()).removeFromCart(deleteItem.getProductId());


            //update total
            int total = 0;
            List<Order> orders = new Database(getActivity()).getCarts();
            for (Order item : orders) {
                total += (Integer.parseInt(item.getPrice())) * (Integer.parseInt(item.getQuantity()));
                Locale locale = new Locale("en", "US");
                NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
                totalPrice.setText(fmt.format(total));




                //Restore deleted item

                Snackbar snackbar = Snackbar.make(rootLayout, name + "remove from cart?", Snackbar.LENGTH_LONG);

                snackbar.setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        adapter.restore(deleteItem, deleteIndex);
                        new Database(getActivity().getBaseContext()).addtoCart(deleteItem);
                        TastyToast.makeText(getActivity(),"item deleted!",TastyToast.LENGTH_SHORT,TastyToast.SUCCESS).show();

                        //update total
                        int total = 0;
                        List<Order> orders = new Database(getActivity()).getCarts();
                        for (Order item : orders) {
                            total += (Integer.parseInt(item.getPrice())) * (Integer.parseInt(item.getQuantity()));
                            Locale locale = new Locale("en", "US");
                            NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
                            totalPrice.setText(fmt.format(total));
                        }

                    }
                });

                snackbar.setActionTextColor(Color.RED);
                snackbar.show();
            }
        }


}








