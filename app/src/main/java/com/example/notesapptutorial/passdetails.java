package com.example.notesapptutorial;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class passdetails extends AppCompatActivity {


    private TextView msiteofpassdetail,mpasswordofpassdetail, musernameofpassdetail;
    FloatingActionButton mgotoeditpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passdetails);
        msiteofpassdetail=findViewById(R.id.siteofpassdetail);
        mpasswordofpassdetail=findViewById(R.id.passwordofpassdetail);
        mgotoeditpass=findViewById(R.id.gotoeditpass);
        musernameofpassdetail=findViewById(R.id.usernameofpassdetail);
        Toolbar toolbar=findViewById(R.id.toolbarofpassdetail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent data=getIntent();

        mgotoeditpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),editpassactivity.class);
                intent.putExtra("site",data.getStringExtra("site"));
                intent.putExtra("password",data.getStringExtra("password"));
                intent.putExtra("passId",data.getStringExtra("passId"));
                intent.putExtra("userId",data.getStringExtra("userId"));
                v.getContext().startActivity(intent);
            }
        });

        mpasswordofpassdetail.setText(data.getStringExtra("password"));
        musernameofpassdetail.setText(data.getStringExtra("userId"));
        msiteofpassdetail.setText(data.getStringExtra("site"));
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