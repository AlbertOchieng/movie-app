package com.example.albertzkiki.movieapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.albertzkiki.movieapp.Common.Common;
import com.example.albertzkiki.movieapp.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.drawable.CheckBoxDrawable;
import com.rey.material.widget.CheckBox;
import com.sdsmdg.tastytoast.TastyToast;
import com.shashank.sony.fancytoastlib.FancyToast;

import io.paperdb.Paper;
import mehdi.sakout.fancybuttons.FancyButton;

public class Signin extends AppCompatActivity {

    EditText ed_phn, ed_pass;
    FancyButton btnLogin;
    CheckBox chb_remeber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        ed_phn =(EditText)findViewById(R.id.phnnumber);
        ed_pass = (EditText)findViewById(R.id.password);
        btnLogin = (FancyButton)findViewById(R.id.btnlogin);
        chb_remeber =(CheckBox)findViewById(R.id.ckb_remeber);

        //intialize paper

        Paper.init(this);


        //initialize firebase

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = firebaseDatabase.getReference("User");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Common.IsConnectedToInternet(getBaseContext())) {

                    //save username and password

                    if(chb_remeber.isChecked()){

                        Paper.book().write(Common.USER_KEY,ed_phn.getText().toString());
                        Paper.book().write(Common.PWD_KEY,ed_pass.getText().toString());

                    }


                    final ProgressDialog progressDialog = new ProgressDialog(Signin.this);
                    progressDialog.setMessage("Loging in...Please wait!");
                    progressDialog.show();

                    table_user.addListenerForSingleValueEvent(new ValueEventListener() {


                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //check if the user does not exist in the database

                            if (dataSnapshot.child(ed_phn.getText().toString()).exists()) {
                                //Get user Information
                                progressDialog.dismiss();

                                User user = dataSnapshot.child(ed_phn.getText().toString()).getValue(User.class);

                                user.setPhone(ed_phn.getText().toString());

                                if (user.getPassword().equals(ed_pass.getText().toString())) {

                                    TastyToast.makeText(getApplicationContext(), "Sign In Successfully!!!", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);

                                    Intent homeintent = new Intent(Signin.this, Home.class);
                                    Common.current_user = user;
                                    startActivity(homeintent);
                                    finish();

                                    table_user.removeEventListener(this);

                                } else {

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

                    TastyToast.makeText(Signin.this,"Please Check your Internet Connection",TastyToast.LENGTH_SHORT,TastyToast.INFO).show();
                    return;

                }

            }

        });

    }
}
