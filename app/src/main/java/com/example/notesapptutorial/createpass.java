package com.example.notesapptutorial;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class createpass extends AppCompatActivity {

    EditText mcreatetitleofpass,mcreatecontentofpass;
    FloatingActionButton msavepass;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;

    ProgressBar mprogressbarofcreatepass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createpass);



        msavepass=findViewById(R.id.savepass);
        mcreatecontentofpass=findViewById(R.id.createcontentofpass);
        mcreatetitleofpass=findViewById(R.id.createtitleofpass);

        mprogressbarofcreatepass=findViewById(R.id.progressbarofcreatepass);
        Toolbar toolbar=findViewById(R.id.toolbarofcreatepass);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();



        msavepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title=mcreatetitleofpass.getText().toString();
                String content=mcreatecontentofpass.getText().toString();
                if(title.isEmpty() || content.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"Both field are Require",Toast.LENGTH_SHORT).show();
                }
                else
                {

                    mprogressbarofcreatepass.setVisibility(View.VISIBLE);

                    DocumentReference documentReference=firebaseFirestore.collection("passwords").document(firebaseUser.getUid()).collection("myPasswords").document();
                    Map<String ,Object> pass= new HashMap<>();
                    pass.put("title",title);
                    pass.put("content",content);

                    documentReference.set(pass).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(),"Password Saved Succesffuly",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(createpass.this,passwordactivity.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),"Failed To Save Password",Toast.LENGTH_SHORT).show();
                            mprogressbarofcreatepass.setVisibility(View.INVISIBLE);
                           // startActivity(new Intent(createpass.this,passwordactivity.class));
                        }
                    });




                }
            }
        });






    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home)
        {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}