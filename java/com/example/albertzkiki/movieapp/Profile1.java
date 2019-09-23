package com.example.albertzkiki.movieapp;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.example.albertzkiki.movieapp.Common.Common;
import com.example.albertzkiki.movieapp.model.Category;
import com.example.albertzkiki.movieapp.model.Upload;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.sdsmdg.tastytoast.TastyToast;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import dmax.dialog.SpotsDialog;
import io.paperdb.Paper;
import mehdi.sakout.fancybuttons.FancyButton;

import static android.app.Activity.RESULT_OK;


public class Profile1 extends Fragment {


    CircleImageView pic;

    private static final int IMAGE_REQUEST = 1;

    Uri ImageUri;

    ProgressBar progressBar;

    MaterialEditText username,email,phone,paswword,newPassword,repreatPassword;
    FancyButton btnedit,btnChangePass;

    FirebaseDatabase database;
    StorageReference firebaseStorage;
    DatabaseReference databaseReference,user,profilpic;

    String UserId = "";



    StorageTask mUploadTask;

    public Profile1() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile1, container, false);

        pic = (CircleImageView)view.findViewById(R.id.setPic);
        username =(MaterialEditText)view.findViewById(R.id.et_usernameP);
        email = (MaterialEditText)view.findViewById(R.id.et_mailP);
        phone = (MaterialEditText)view.findViewById(R.id.et_phoneP);
        btnedit = (FancyButton)view.findViewById(R.id.btnEditP);
        btnChangePass = (FancyButton)view.findViewById(R.id.btnChnagePass);




        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);

        firebaseStorage = FirebaseStorage.getInstance().getReference("Uploads");
        databaseReference = FirebaseDatabase.getInstance().getReference("Uploads");

        database = FirebaseDatabase.getInstance();
        user = database.getReference("User");
        profilpic = database.getReference("Uploads");

        //UserId = getActivity().getIntent().getStringExtra(Common.current_profile.getmName());

       // getProfilePic();
//        Upload upload = new Upload();
//
//        if(username.getText().toString().equals(upload.getmName()))
//
//            profilpic.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Upload upload = dataSnapshot.getValue(Upload.class);
//
//                //set Image
//                Picasso.with(getContext()).load(upload.getmImageUrl())
//                        .into(pic);
//
//                username.setText(upload.getmName());
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });




        btnedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setEditTextEditable();
            }
        });

        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangePasswordDialong();
            }
        });



        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openFileChoose();

//                if(mUploadTask != null){
//
//                    TastyToast.makeText(getContext(), "Upload is in progress!!",TastyToast.LENGTH_SHORT,TastyToast.INFO);
//
//                }else {
//
//                    uploadFile();
//
//                }

            }
        });


        return view;
    }




    private void getProfilePic() {

        user.child(Common.current_profile.getmName()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Upload upload = dataSnapshot.getValue(Upload.class);

                Picasso.with(getContext()).load(upload.getmImageUrl())
                        .into(pic);

                username.setText(upload.getmName());


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void openFileChoose(){

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null
                &&   data.getData() != null  ){


            ImageUri = data.getData();

//            Picasso.with(getContext()).load(ImageUri).into(pic);
//            Glide.with(getContext()).load(ImageUri).into(pic);

            pic.setImageURI(ImageUri);
        }

        uploadFile();

    }

    private String getFileExtension(Uri uri){

        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(uri));

    }

    public void uploadFile(){

        if(ImageUri != null){

            StorageReference fileReference = firebaseStorage.child(System.currentTimeMillis()
                    + "." + getFileExtension(ImageUri) );

            mUploadTask = fileReference.putFile(ImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {

                                    progressBar.setProgress(0);

                                }
                            }, 500);

                            TastyToast.makeText(getContext(),"Profile pic Uploaded Successfully!",TastyToast.LENGTH_LONG,TastyToast.SUCCESS);


                            Upload upload = new Upload(username.getText().toString().trim(),taskSnapshot.getDownloadUrl().toString());

                            String uploadId = Common.current_user.getName();
//                            String uploadId = databaseReference.push().getKey();
                            databaseReference.child(uploadId).setValue(upload);

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            TastyToast.makeText(getContext(), e.getMessage(),TastyToast.LENGTH_SHORT,TastyToast.ERROR);

                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            double progress  = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressBar.setProgress((int)progress);


                        }
                    });

        }else{

            TastyToast.makeText(getContext(),"Please select a new picture", TastyToast.LENGTH_SHORT, TastyToast.INFO);

        }




    }

    private void setEditTextEditable() {


            email.setEnabled(true);
            phone.setEnabled(true);
            username.setEnabled(true);

            btnedit.setText("Save");




    }

    private void showChangePasswordDialong() {

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("CHANGE PASSWORD");
        alert.setMessage("Please fill in all the information");


        LayoutInflater inflater = this.getLayoutInflater();
        final View add_movie_layout = inflater.inflate(R.layout.changepassword, null);

        paswword = (MaterialEditText)add_movie_layout.findViewById(R.id.edPaswordP);
        newPassword = (MaterialEditText)add_movie_layout.findViewById(R.id.newPasswordP);
        repreatPassword = (MaterialEditText)add_movie_layout.findViewById(R.id.RepeatPasswordP);


        alert.setView(add_movie_layout);
        alert.setIcon(R.drawable.ic_security);

        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

               final AlertDialog waitingDialog = new SpotsDialog(getContext());
               waitingDialog.show();

               if(paswword.getText().toString().equals(Common.current_user.getPassword())){

                    if(newPassword.getText().toString().equals(repreatPassword.getText().toString())){

                        Map<String,Object> updatePassword = new HashMap<>();
                        updatePassword.put("password",newPassword.getText().toString());

                        //make update in the database

                        user.child(Common.current_user.getPhone())
                                .updateChildren(updatePassword)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        waitingDialog.dismiss();
                                        TastyToast.makeText(getContext(), "Password has been Changed suceesfully",TastyToast.LENGTH_SHORT,TastyToast.SUCCESS).show();

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                        waitingDialog.dismiss();
                                        TastyToast.makeText(getContext(), e.getMessage(),TastyToast.LENGTH_SHORT,TastyToast.ERROR).show();
                                    }
                                });

                    }else{
                        waitingDialog.dismiss();
                        TastyToast.makeText(getContext(), "New Password dont Match",TastyToast.LENGTH_SHORT,TastyToast.ERROR).show();

                    }
               }else{

                   waitingDialog.dismiss();
                   TastyToast.makeText(getContext(), "wrong Password",TastyToast.LENGTH_SHORT,TastyToast.ERROR).show();
               }

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


}
