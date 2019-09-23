package com.example.albertzkiki.movieapp;

import android.app.ProgressDialog;
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
import com.sdsmdg.tastytoast.TastyToast;

import mehdi.sakout.fancybuttons.FancyButton;


public class signup extends AppCompatActivity {


    EditText regusername,regemail,regconfirmPassord,regpassword,regphone;
    FancyButton regbtnsignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        regusername =(EditText) findViewById(R.id.et_regusername);
        regpassword=(EditText)findViewById(R.id.et_regpassword);
        regphone = (EditText)findViewById(R.id.et_regphnnumber);
        regemail = (EditText)findViewById(R.id.et_regEmail);
        regconfirmPassord = (EditText)findViewById(R.id.et_regCpassword);
        regbtnsignUp = (FancyButton) findViewById(R.id.btnRegSignUp);


        //initialize firebase

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");


        regbtnsignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Common.IsConnectedToInternet(getBaseContext())) {

                    final ProgressDialog progressDialog = new ProgressDialog(signup.this);
                    progressDialog.setMessage("Registering....Please wait");
                    progressDialog.show();

                    table_user.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            //check if the phone number exists in the database

                            if (dataSnapshot.child(regphone.getText().toString()).exists()) {

                                progressDialog.dismiss();
                                //Toast.makeText(signup.this, "Phone number has already been registered", Toast.LENGTH_SHORT).show();
                                TastyToast.makeText(getApplicationContext(), "Phone number has already been registered", TastyToast.LENGTH_SHORT, TastyToast.ERROR);

                            } else {


                                String username = regusername.getText().toString();
                                String pass = regpassword.getText().toString();
                                String Cpass = regconfirmPassord.getText().toString();
                                String email = regemail.getText().toString();
                                //String phn = regphone.getText().toString();

                                progressDialog.dismiss();

                                if (pass.equals(Cpass)) {

                                    User user = new User(username, email, pass, Cpass);
                                    table_user.child(regphone.getText().toString()).setValue(user);

                                    //Toast.makeText(signup.this, "User Registed Successfully", Toast.LENGTH_SHORT).show();
                                    TastyToast.makeText(signup.this, "User Registed Successfully", TastyToast.LENGTH_SHORT, TastyToast.SUCCESS);

                                    finish();

                                } else {

                                    TastyToast.makeText(getApplicationContext(), "Passwords dont match", Toast.LENGTH_SHORT, TastyToast.ERROR);

                                }
                            }

                            finish();

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }else{

                    TastyToast.makeText(signup.this,"Please Check your Internet Connection",TastyToast.LENGTH_SHORT,TastyToast.INFO).show();
                    return;
                }

                }


        });


    }
}
