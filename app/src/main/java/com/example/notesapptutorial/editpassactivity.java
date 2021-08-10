package com.example.notesapptutorial;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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

public class editpassactivity extends AppCompatActivity {

    Intent data;
    EditText meditsiteofpass,meditpasswordofpass,medituseridofpass;
    FloatingActionButton msaveeditpass;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editpassactivity);
        meditsiteofpass=findViewById(R.id.editsiteofpass);
        meditpasswordofpass=findViewById(R.id.editpasswordofpass);
        msaveeditpass=findViewById(R.id.saveeditpass);
        medituseridofpass=findViewById(R.id.edituseridofpass);

        data=getIntent();

        firebaseFirestore=FirebaseFirestore.getInstance();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();


        Toolbar toolbar=findViewById(R.id.toolbarofeditpass);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        msaveeditpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"savebuton click",Toast.LENGTH_SHORT).show();

                String newsite=meditsiteofpass.getText().toString();
                String newpassword=meditpasswordofpass.getText().toString();
                String newuserid=medituseridofpass.getText().toString();

                if(newsite.isEmpty()||newpassword.isEmpty()||newuserid.isEmpty())
                {
                    Toast.makeText(getApplicationContext(),"All fields necessary",Toast.LENGTH_SHORT).show();
                    return;
                }
                else
                {
                    DocumentReference documentReference=firebaseFirestore.collection("passwords").document(firebaseUser.getUid()).collection("myPasswords").document(data.getStringExtra("passId"));
                    Map<String,Object> pass=new HashMap<>();
                    pass.put("site",newsite);
                    pass.put("password",newpassword);
                    pass.put("userid",newuserid);
                    documentReference.set(pass).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getApplicationContext(),"Password is updated",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(editpassactivity.this,passwordactivity.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(),"Failed To update",Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });


        String passSite=data.getStringExtra("site");
        String passPassword=data.getStringExtra("password");
        String passUserid=data.getStringExtra("userid");
        meditpasswordofpass.setText(passPassword);
        medituseridofpass.setText(passUserid);
        meditsiteofpass.setText(passSite);
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