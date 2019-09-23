package com.example.albertzkiki.movieapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.albertzkiki.movieapp.Common.Common;
import com.example.albertzkiki.movieapp.Common.Config;
import com.example.albertzkiki.movieapp.model.Category;
import com.example.albertzkiki.movieapp.model.bookingModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.sdsmdg.tastytoast.TastyToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;

public class Seat extends AppCompatActivity{


    public FancyButton btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn10,
            btn11,btn12,btn13,btn14,btn15,btn16,btn17,btn18,btn19,btn20,
            btn21,btn22,btn23,btn24,btn25,btn26,btn27,btn28,btn29,btn30,
            btn31,btn32,btn33,btn34,btn35,btn36,btn37,btn38,btn39,btn40,
            btn41,btn42,btn43,btn44,btn45,btn46,btn47,btn48,btn49,btn50,
            btn51,btn52,btn53,btn54,btn55,btn56,btn57,btn58,btn59,btn60,
            btn61,btn62,btn63,btn64,btn65,btn66,confirm;

    //declear firebase

    FirebaseDatabase database;
    DatabaseReference Booking,SeatsBooked,BookingUser,DisableSeats;

    String MovieName="";
    String Date="";
    String Time="";
    String Age="";
    String MoviePrice="";


    FancyButton buttons[] = new FancyButton[10];

    TextView movietitle,userbooked,totalPrice,seats_picked,timebooked,datebooked;

    List<Integer> seatsTaken = new ArrayList<Integer>();

    //paypall payment

    static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId(Config.PAYPAL_CLIENT_ID);

    private static final int PAYPAL_REQUEST_CODE = 9999;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat);

        btn1 = (FancyButton)findViewById(R.id.btn1);
        btn2 = (FancyButton)findViewById(R.id.btn2);
        btn3 = (FancyButton)findViewById(R.id.btn3);
        btn4 = (FancyButton)findViewById(R.id.btn4);
        btn5 = (FancyButton)findViewById(R.id.btn5);
        btn6 = (FancyButton)findViewById(R.id.btn6);
        btn7 = (FancyButton)findViewById(R.id.btn7);
        btn8 = (FancyButton)findViewById(R.id.btn8);
        btn9 = (FancyButton)findViewById(R.id.btn9);
        btn10 = (FancyButton)findViewById(R.id.btn10);

        btn11 = (FancyButton)findViewById(R.id.btn11);
        btn12 = (FancyButton)findViewById(R.id.btn12);
        btn13 = (FancyButton)findViewById(R.id.btn13);
        btn14 = (FancyButton)findViewById(R.id.btn14);
        btn15 = (FancyButton)findViewById(R.id.btn15);
        btn16 = (FancyButton)findViewById(R.id.btn16);
        btn17 = (FancyButton)findViewById(R.id.btn17);
        btn18 = (FancyButton)findViewById(R.id.btn18);
        btn19 = (FancyButton)findViewById(R.id.btn19);
        btn20 = (FancyButton)findViewById(R.id.btn20);

        btn21 = (FancyButton)findViewById(R.id.btn21);
        btn22 = (FancyButton)findViewById(R.id.btn22);
        btn23 = (FancyButton)findViewById(R.id.btn23);
        btn24 = (FancyButton)findViewById(R.id.btn24);
        btn25 = (FancyButton)findViewById(R.id.btn25);
        btn26 = (FancyButton)findViewById(R.id.btn26);
        btn27 = (FancyButton)findViewById(R.id.btn27);
        btn28 = (FancyButton)findViewById(R.id.btn28);
        btn29 = (FancyButton)findViewById(R.id.btn29);
        btn30 = (FancyButton)findViewById(R.id.btn30);

        btn31 = (FancyButton)findViewById(R.id.btn31);
        btn32 = (FancyButton)findViewById(R.id.btn32);
        btn33 = (FancyButton)findViewById(R.id.btn33);
        btn34 = (FancyButton)findViewById(R.id.btn34);
        btn35 = (FancyButton)findViewById(R.id.btn35);
        btn36 = (FancyButton)findViewById(R.id.btn36);
        btn37 = (FancyButton)findViewById(R.id.btn37);
        btn38 = (FancyButton)findViewById(R.id.btn38);
        btn39 = (FancyButton)findViewById(R.id.btn39);
        btn40 = (FancyButton)findViewById(R.id.btn40);

        btn41 = (FancyButton)findViewById(R.id.btn41);
        btn42 = (FancyButton)findViewById(R.id.btn42);
        btn43 = (FancyButton)findViewById(R.id.btn43);
        btn44 = (FancyButton)findViewById(R.id.btn44);
        btn45 = (FancyButton)findViewById(R.id.btn45);
        btn46 = (FancyButton)findViewById(R.id.btn46);
        btn47 = (FancyButton)findViewById(R.id.btn47);
        btn48 = (FancyButton)findViewById(R.id.btn48);
        btn49 = (FancyButton)findViewById(R.id.btn49);
        btn50 = (FancyButton)findViewById(R.id.btn50);

        btn51 = (FancyButton)findViewById(R.id.btn51);
        btn52 = (FancyButton)findViewById(R.id.btn52);
        btn53 = (FancyButton)findViewById(R.id.btn53);
        btn54 = (FancyButton)findViewById(R.id.btn54);
        btn55 = (FancyButton)findViewById(R.id.btn55);
        btn56 = (FancyButton)findViewById(R.id.btn56);
        btn57 = (FancyButton)findViewById(R.id.btn57);
        btn58 = (FancyButton)findViewById(R.id.btn58);
        btn59 = (FancyButton)findViewById(R.id.btn59);
        btn60 = (FancyButton)findViewById(R.id.btn60);

        btn61 = (FancyButton)findViewById(R.id.btn61);
        btn62 = (FancyButton)findViewById(R.id.btn62);
        btn63 = (FancyButton)findViewById(R.id.btn63);
        btn64 = (FancyButton)findViewById(R.id.btn64);
        btn65 = (FancyButton)findViewById(R.id.btn65);
        btn66 = (FancyButton)findViewById(R.id.btn66);
        confirm = (FancyButton)findViewById(R.id.Confirm);


        //init paypal

        Intent intent = new Intent(Seat.this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);




        //initialize firebase
        database = FirebaseDatabase.getInstance();
        Booking = database.getReference("Booking");
        BookingUser = database.getReference("BookedAndReady");

        FirebaseDatabase db;
        db = FirebaseDatabase.getInstance();
        SeatsBooked = db.getReference("DisabledSeats");





        //get details of booked seats


        if (getIntent() != null) {

            MoviePrice = getIntent().getStringExtra("MoviePrice");
            MovieName = getIntent().getStringExtra("MovieName");
            Date = getIntent().getStringExtra("Spinner_Date");
            Time = getIntent().getStringExtra("Spinner_Time");
            Age = getIntent().getStringExtra("Spinner_Age");



            BookingUser = database.getReference("BookedAndReady");

            DisableSeats = database.getReference("DisabledSeats").child(MovieName).child(Date).child(Time).child("seatsnumber");

           // usersBooked = database.getReference("UsersBooked").child(Common.current_user.getPhone()).child(MovieName).child(Date).child(Time);

            getSeatDetails(MovieName,Date,Time);


        }








        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (getIntent() != null)

                    MoviePrice = getIntent().getStringExtra("MoviePrice");
                    MovieName = getIntent().getStringExtra("MovieName");
                    Date = getIntent().getStringExtra("Spinner_Date");
                    Time = getIntent().getStringExtra("Spinner_Time");
                    Age = getIntent().getStringExtra("Spinner_Age");


                    showalertDialog();



            }
        });



        btn1.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn1.setBackgroundColor(Color.RED);
                    clicked = true;
                    seats_picked.setText("");
                    String one = btn1.getText().toString();
                    seatsTaken.remove(new Integer(one));



                } else {
                    btn1.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn1.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }


            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn2.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn2.getText().toString();
                    seatsTaken.remove(new Integer(one));

                } else {
                    btn2.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String two = btn2.getText().toString();
                    btn2.isEnabled();
                    String five = btn2.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn3.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn3.getText().toString();
                    seatsTaken.remove(new Integer(one));

                } else {
                    btn3.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn3.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn4.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn4.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn4.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn4.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn5.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn5.getText().toString();
                    seatsTaken.remove(new Integer(one));

                } else {

                    btn5.setBackgroundColor(Color.WHITE);
                    clicked = false;

                    String five = btn5.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn6.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn6.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn6.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn6.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn7.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn7.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn7.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn7.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn8.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn8.getText().toString();
                    seatsTaken.remove(new Integer(one));



                } else {
                    btn8.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn8.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn9.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn9.getText().toString();
                    seatsTaken.remove(new Integer(one));



                } else {
                    btn9.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn9.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn10.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn10.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn10.getText().toString();
                    seatsTaken.remove(new Integer(one));



                } else {
                    btn10.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn10.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });

        btn11.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn11.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn11.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn11.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn11.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn12.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn12.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn12.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn12.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn12.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn13.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn13.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn13.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn13.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn13.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn14.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn14.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn14.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn14.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn14.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn15.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn15.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn15.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn15.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn15.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn16.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn16.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn16.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn16.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn16.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn17.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn17.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn17.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn17.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn17.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn18.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn18.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn18.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn18.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn18.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn19.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn19.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn19.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn19.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn19.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn20.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn20.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn20.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn20.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn20.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });

        btn21.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn21.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn21.getText().toString();
                    seatsTaken.remove(new Integer(one));



                } else {
                    btn21.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn21.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn22.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn22.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn22.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn22.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn22.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn23.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn23.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn23.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn23.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn23.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn24.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn24.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn24.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn24.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn24.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn25.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn25.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn25.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn25.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn25.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn26.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn26.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn26.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn26.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn26.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn27.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn27.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn27.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn27.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn27.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn28.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn28.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn28.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn28.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn28.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn29.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn29.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn29.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn29.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn29.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn30.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn30.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn30.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn30.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn30.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });

        btn31.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn31.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn31.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn31.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn31.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn32.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn32.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn32.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn32.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn32.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn33.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn33.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn33.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn33.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn33.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn34.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn34.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn34.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn34.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn34.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn35.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn35.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn35.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn35.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn35.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn36.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn36.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn36.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn36.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn36.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn37.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn37.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn37.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn37.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn37.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn38.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn38.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn38.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn38.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn38.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn39.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn39.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn39.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn39.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn39.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn40.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn40.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn40.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn40.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn40.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });


        btn41.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn41.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn41.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn41.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn41.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);


                }

            }
        });
        btn42.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn42.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn42.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn42.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn42.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn43.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn43.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn43.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn43.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn43.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn44.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn44.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn44.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn44.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn44.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn45.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn45.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn45.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn45.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn45.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn46.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn46.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn46.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn46.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn46.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn47.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn47.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn47.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn47.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn47.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn48.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn48.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn48.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn48.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn48.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn49.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn49.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn49.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn49.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn49.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn50.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn50.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn50.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn50.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn50.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });

        btn51.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn51.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn51.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn51.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn51.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn52.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn52.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn52.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn52.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn52.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn53.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn53.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn53.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn53.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn53.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn54.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn54.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn54.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn54.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn54.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn55.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn55.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn55.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn55.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn55.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn56.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn56.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn56.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn56.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn56.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn57.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn57.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn57.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn57.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn57.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn58.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn58.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn58.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn58.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn58.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn59.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn59.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn59.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn59.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn59.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn60.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn60.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn60.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn60.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn60.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });

        btn61.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn61.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn61.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn61.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn61.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn62.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn62.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn62.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn62.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn62.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn63.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn63.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn63.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn63.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn63.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn64.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn64.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn64.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn64.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn64.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn65.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn65.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn65.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn65.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn65.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });
        btn66.setOnClickListener(new View.OnClickListener() {
            boolean clicked = true;

            @Override
            public void onClick(View v) {

                if (!clicked) {
                    btn66.setBackgroundColor(Color.RED);
                    clicked = true;
                    String one = btn66.getText().toString();
                    seatsTaken.remove(new Integer(one));


                } else {
                    btn66.setBackgroundColor(Color.WHITE);
                    clicked = false;
                    String five = btn66.getText().toString();
                    int fivex = Integer.parseInt(five);
                    seatsTaken.add(fivex);

                }

            }
        });



    }

    private void getSeatDetails1(String movieName) {

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Checking Available seats...");
        dialog.setCancelable(false);
        dialog.show();

        SeatsBooked.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                dialog.dismiss();

//                bookingModel bkm = dataSnapshot.getValue(bookingModel.class);

                String bkm = (String) dataSnapshot.child(MovieName).getValue();

                System.out.println(bkm);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                System.out.println("The read failed"+databaseError);

            }
        });






    }

    private void getSeatDetails(String movieName,String date,String time) {

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Checking avalable seats");
        dialog.setCancelable(false);
        dialog.show();



        DisableSeats.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                dialog.dismiss();

                for(DataSnapshot child : dataSnapshot.getChildren()) {



                    //HashMap<List<Integer>, String> value = (HashMap<List<Integer>, String>) child.getValue();


                        String key = (String) child.getKey();

                        System.out.println(key);

                        TastyToast.makeText(getApplicationContext(),key,TastyToast.LENGTH_SHORT,TastyToast.SUCCESS).show();







                   // TastyToast.makeText(getApplicationContext(),key,TastyToast.LENGTH_SHORT,TastyToast.SUCCESS).show();


//                    DatabaseReference keyReference = FirebaseDatabase.getInstance().getReference().child("Booking").child(key);
//
//                        keyReference.addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//
//
//                                for (DataSnapshot child : dataSnapshot.getChildren()) {
//
//                                    HashMap<String, String> value = (HashMap<String, String>) child.getValue();
//
//                                    String key1 = (String) child.getKey();
//
//                                    System.out.println(key1);
//
//                                    TastyToast.makeText(getApplicationContext(), key1, TastyToast.LENGTH_SHORT, TastyToast.SUCCESS).show();
//
//                                }
//                            }
//
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//
//                            }
//                        });
//
//
//
//

                    }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });








  }


    public void showalertDialog() {


            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Booking Details");
            alert.setMessage("Confirm the details before you proceed");


            LayoutInflater inflater = this.getLayoutInflater();
            final View add_movie_layout = inflater.inflate(R.layout.booking_details_alert, null);




            movietitle = (TextView)add_movie_layout.findViewById(R.id.movieNameBooked);
            userbooked = (TextView)add_movie_layout.findViewById(R.id.phoneNumberBooked);
            totalPrice = (TextView)add_movie_layout.findViewById(R.id.AmountBooked);
            seats_picked = (TextView)add_movie_layout.findViewById(R.id.seatsBooked);
            timebooked = (TextView)add_movie_layout.findViewById(R.id.TimeBooked);
            datebooked = (TextView)add_movie_layout.findViewById(R.id.DateBooked);

            //            seats_picked

                for(int i=0; i < seatsTaken.size(); i++){

                    seats_picked.setText(seats_picked.getText() + " " + seatsTaken.get(i) + " , ");

                    int totalseats = seatsTaken.size();

                    String MovieP = String.valueOf(MoviePrice);

                    String MovieX = MovieP.toString()
                            .replace("$", "")
                            .replace(",", "");

                    int movieprice = Integer.parseInt(MovieX);

                    int totalAmount = totalseats * movieprice;

                    String pay = String.valueOf(totalAmount);

                    totalPrice.setText(pay);



                }





                Category category = new Category();

                movietitle.setText(MovieName);
                userbooked.setText(Common.current_user.getPhone());
                timebooked.setText(Time);
                datebooked.setText(Date);




            alert.setView(add_movie_layout);
            alert.setIcon(R.drawable.ic_movie_creator);

            alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    //create new cartegories
//                    if(newCategory != null){
//
//                        cartegory.push().setValue(newCategory);
//
//                        TastyToast.makeText(getActivity(), "New Movie" +newCategory.getName()+" was added successfully", TastyToast.LENGTH_SHORT,TastyToast.SUCCESS).show();
//
//
//                    }


                   //show paypal to payment

                    String formatAmount = totalPrice.getText().toString()
                            .replace("$", "")
                            .replace(",", "");

                    PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(formatAmount),
                            "USD",
                            "BLACK DYNASTY",
                            PayPalPayment.PAYMENT_INTENT_SALE);

                    Intent intent = new Intent(getBaseContext(), PaymentActivity.class);
                    intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
                    intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
                    startActivityForResult(intent, PAYPAL_REQUEST_CODE);
                    dialog.dismiss();
                }
            });

            alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alert.show();




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

                        bookingModel bookingMovovie = new bookingModel(
                                MovieName,
                                Common.current_user.getPhone(),
                                totalPrice.getText().toString(),
                                jsonObject.getJSONObject("response").getString("state"),
                                Date,
                                Time,
                                seatsTaken

                        );

                        bookingModel bkm = new bookingModel(

                                MovieName,
                                Date,
                                Time,
                                seatsTaken

                        );




                        //String order_number = String.valueOf(System.currentTimeMillis());

                        Booking.child(MovieName).child(Date).child(Time).child(Common.current_user.getPhone()).setValue(bookingMovovie);

                        BookingUser.child(Common.current_user.getPhone()).setValue(bookingMovovie);

                        SeatsBooked.child(MovieName).child(Date).child(Time).setValue(bkm);

                        if(SeatsBooked.child(MovieName).child(Date).child(Time) != null) {

                            SeatsBooked.child(MovieName).child(Date).child(Time).child("seatsnumber").setValue(seatsTaken);

                        }

                        //<String, List<Integer>> updateSeats = new HashMap<>();

                        //updateSeats.put("seatsnumber",seatsTaken);

                       //SeatsBooked.child(MovieName).child(Date).child(Time).push().setValue(updateSeats);











                        //make update in the database

//                        SeatsBooked.child(MovieName).child(Date).child(Time)
//                                .updateChildren(updateSeats)
//                                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//
//                                        //waitingDialog.dismiss();
//                                        TastyToast.makeText(getApplicationContext(), "seats has been Changed suceesfully",TastyToast.LENGTH_SHORT,TastyToast.SUCCESS).show();
//
//                                    }
//                                })
//                                .addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//
//                                       // waitingDialog.dismiss();
//                                        TastyToast.makeText(getApplicationContext(), e.getMessage(),TastyToast.LENGTH_SHORT,TastyToast.ERROR).show();
//                                    }
//                                });



                        //SeatsDisabled.child(MovieName).

//                        SendNotificationOrder(order_number);


                        TastyToast.makeText(getApplicationContext(), "Booking confirmed!", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS).show();


                        finish();



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

                    TastyToast.makeText(getApplicationContext(), "Payment Canceled!", TastyToast.LENGTH_SHORT, TastyToast.DEFAULT).show();
                } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {

                    TastyToast.makeText(getApplicationContext(), "Invalid Payment!", TastyToast.LENGTH_SHORT, TastyToast.ERROR).show();

                }


            }


        }

























































//        if (requestCode == PAYPAL_REQUEST_CODE) {
//
//            if (resultCode == Activity.RESULT_OK) {
//
//                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
//
//
//
//
//                if(confirmation !=null) {
//
//                    String state = confirmation.getProofOfPayment().getState();
//
//                    if(state.equals("approved")) {
//
//                        TastyToast.makeText(getApplicationContext(),"Movie Booked Successfully",TastyToast.LENGTH_SHORT,TastyToast.SUCCESS).show();
//                    }else {
//
//                        TastyToast.makeText(getApplicationContext(),"Failed...you do not have enough cash",TastyToast.LENGTH_SHORT,TastyToast.ERROR).show();
//
//                    }
//
//                }
//
//
//                } else if (resultCode == Activity.RESULT_CANCELED) {
//
//                    TastyToast.makeText(getApplicationContext(), "Payment Canceled!", TastyToast.LENGTH_SHORT, TastyToast.DEFAULT).show();
//                } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
//
//                    TastyToast.makeText(getApplicationContext(), "Invalid Payment!", TastyToast.LENGTH_SHORT, TastyToast.ERROR).show();
//
//                }
//
//
//            }


        }


    }



