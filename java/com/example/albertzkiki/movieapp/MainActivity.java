package com.example.albertzkiki.movieapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.albertzkiki.movieapp.Common.Common;
import com.example.albertzkiki.movieapp.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sdsmdg.tastytoast.TastyToast;

import at.markushi.ui.CircleButton;
import io.paperdb.Paper;
import mehdi.sakout.fancybuttons.FancyButton;

public class MainActivity extends AppCompatActivity {


    FancyButton btnsignup;
    FancyButton btnsignin;

    TextView slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnsignin = (FancyButton) findViewById(R.id.btnSignIn);
        btnsignup = (FancyButton) findViewById(R.id.btnSignUp);
        slogan = (TextView) findViewById(R.id.slogan);


        //font setup

        Typeface face = Typeface.createFromAsset(getAssets(),"fonts/DJ2TRIAL.ttf");
        slogan.setTypeface(face);

        //inti Paper

        Paper.init(this);



        btnsignin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this,Signin.class);
                startActivity(intent);

            }

        });

        btnsignup.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                Intent intent = new Intent(MainActivity.this,signup.class);
                startActivity(intent);

            }


        });

        //Check Remeber

        String user = Paper.book().read(Common.USER_KEY);
        String pwd = Paper.book().read(Common.PWD_KEY);

        if(user != null && pwd != null){

            if(!user.isEmpty() && !pwd.isEmpty()){

                login(user,pwd);
            }

        }

    }

            //login when Remember me check box is Checked

            public void login(final String phone, final String pass ){


                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                final DatabaseReference table_user = firebaseDatabase.getReference("User");

                if (Common.IsConnectedToInternet(getBaseContext())) {

                    final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                    progressDialog.setMessage("Loging in...Please wait!");
                    progressDialog.show();

                    table_user.addValueEventListener(new ValueEventListener() {


                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //check if the user does not exist in the database

                            if (dataSnapshot.child(phone).exists()) {
                                //Get user Information
                                progressDialog.dismiss();

                                User user = dataSnapshot.child(phone).getValue(User.class);

                                user.setPhone(phone);

                                if (user.getPassword().equals(pass)){
                                    TastyToast.makeText(getApplicationContext(), "Sign In Successfully!!!", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);

                                    Intent homeintent = new Intent(MainActivity.this, Home.class);
                                    Common.current_user = user;
                                    startActivity(homeintent);

                                }else {

                                    TastyToast.makeText(getApplicationContext(), "Phone number/password is incorrect!", TastyToast.LENGTH_SHORT, TastyToast.ERROR);
                                }

                            } else {
                                progressDialog.dismiss();
                                // Toast.makeText(Signin.this, "User does not exist", Toast.LENGTH_SHORT).show();
                                // FancyToast.makeText(Signin.this,"User does not exis!",FancyToast.LENGTH_SHORT,FancyToast.INFO,true);
                                TastyToast.makeText(getApplicationContext(), "User does not exis", TastyToast.LENGTH_SHORT, TastyToast.WARNING);


                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else {

                    TastyToast.makeText(MainActivity.this,"Please Check your Internet Connection",TastyToast.LENGTH_SHORT,TastyToast.INFO).show();
                    return;

                }


            }

            //    button Animation
            public void didTapButton(View v){
                CircleButton button = (CircleButton) findViewById(R.id.btnNotUser);

                final Animation myAnime = AnimationUtils.loadAnimation(this, R.anim.bounce);

                MyBounceInterpolator interpolator = new MyBounceInterpolator(0.2, 20);
                myAnime.setInterpolator(interpolator);

                button.startAnimation(myAnime);

                button.setOnClickListener(new View.OnClickListener(){



                    @Override
                    public void onClick(View view){

                        Intent mainActivity1 = new Intent(MainActivity.this, MainActivity1.class);
                        startActivity(mainActivity1);

                    }

                });

            }

}
