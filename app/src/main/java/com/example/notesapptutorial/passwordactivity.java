package com.example.notesapptutorial;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class passwordactivity extends AppCompatActivity {

    FloatingActionButton mcreatepass;
    private FirebaseAuth firebaseAuth;


    RecyclerView mrecyclerviewp;
    StaggeredGridLayoutManager staggeredGridLayoutManager;


    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;

    FirestoreRecyclerAdapter<firebasemodel,PassViewHolder> passAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passwordactivity);

        mcreatepass=findViewById(R.id.createpassb);
        firebaseAuth=FirebaseAuth.getInstance();

        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        firebaseFirestore=FirebaseFirestore.getInstance();

        getSupportActionBar().setTitle("Passwords");

        mcreatepass.setOnClickListener(v -> startActivity(new Intent(passwordactivity.this,createpass.class)));


        Query query=firebaseFirestore.collection("passwords").document(firebaseUser.getUid()).collection("myPasswords").orderBy("site",Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<firebasemodel> alluserpass= new FirestoreRecyclerOptions.Builder<firebasemodel>().setQuery(query,firebasemodel.class).build();

        passAdapter= new FirestoreRecyclerAdapter<firebasemodel, PassViewHolder>(alluserpass) {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            protected void onBindViewHolder(@NonNull PassViewHolder passViewHolder, int i, @NonNull firebasemodel firebasemodel) {


                ImageView popupbutton=passViewHolder.itemView.findViewById(R.id.menupopbuttonp);

                int colourcode=getRandomColor();
                passViewHolder.mpass.setBackgroundColor(passViewHolder.itemView.getResources().getColor(colourcode,null));

                passViewHolder.passtitle.setText(firebasemodel.getSite());

                passViewHolder.passcontent.setText(firebasemodel.getPassword());

                passViewHolder.passuserid.setText(firebasemodel.getUserid());

                String docIdp=passAdapter.getSnapshots().getSnapshot(i).getId();

                passViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //we have to open password detail activity


                        Intent intent=new Intent(v.getContext(),passdetails.class);
                        intent.putExtra("site",firebasemodel.getSite());
                        intent.putExtra("password",firebasemodel.getPassword());
                        intent.putExtra("uid", firebasemodel.getUserid());
                        intent.putExtra("passId",docIdp);

                        v.getContext().startActivity(intent);

                       // Toast.makeText(getApplicationContext(),"This is Clicked",Toast.LENGTH_SHORT).show();
                    }
                });


                popupbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        PopupMenu popupMenu=new PopupMenu(v.getContext(),v);
                        popupMenu.setGravity(Gravity.END);
                        popupMenu.getMenu().add("Edit").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                //details for edit password activity

                                Intent intent=new Intent(v.getContext(),editpassactivity.class);
                                intent.putExtra("site",firebasemodel.getSite());
                                intent.putExtra("password",firebasemodel.getPassword());
                                intent.putExtra("uid", firebasemodel.getUserid());
                                intent.putExtra("passId",docIdp);
                                v.getContext().startActivity(intent);
                                return false;
                            }
                        });

                        popupMenu.getMenu().add("Delete").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                //Toast.makeText(v.getContext(),"This password is deleted",Toast.LENGTH_SHORT).show();
                                DocumentReference documentReference=firebaseFirestore.collection("passwords").document(firebaseUser.getUid()).collection("myPasswords").document(docIdp);
                                documentReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(v.getContext(),"Password is deleted",Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(v.getContext(),"Failed To Delete",Toast.LENGTH_SHORT).show();
                                    }
                                });


                                return false;
                            }
                        });

                        popupMenu.show();
                    }
                });


            }

            @NonNull
            @Override
            public PassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
               View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pass_layout,parent,false);
               return new PassViewHolder(view);
            }
        };


        mrecyclerviewp=findViewById(R.id.recyclerviewp);
        mrecyclerviewp.setHasFixedSize(true);
        staggeredGridLayoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mrecyclerviewp.setLayoutManager(staggeredGridLayoutManager);
        mrecyclerviewp.setAdapter(passAdapter);


    }

    public class PassViewHolder extends RecyclerView.ViewHolder
    {
        private TextView passtitle;
        private TextView passcontent;
        private TextView passuserid;
        LinearLayout mpass;

        public PassViewHolder(@NonNull View itemView) {
            super(itemView);
            passtitle=itemView.findViewById(R.id.passtitle);
            passcontent=itemView.findViewById(R.id.passcontent);
            passuserid=itemView.findViewById(R.id.passuid);
            mpass=itemView.findViewById(R.id.pass);
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.logout:
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(passwordactivity.this,MainActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStart() {
        super.onStart();
        passAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
       if(passAdapter!=null)
       {
           passAdapter.stopListening();
       }
    }


    private int getRandomColor()
    {
        List<Integer> colorcode=new ArrayList<>();
        colorcode.add(R.color.gray);
        colorcode.add(R.color.pink);
        colorcode.add(R.color.lightgreen);
        colorcode.add(R.color.skyblue);
        colorcode.add(R.color.color1);
        colorcode.add(R.color.color2);
        colorcode.add(R.color.color3);

        colorcode.add(R.color.color4);
        colorcode.add(R.color.color5);
        colorcode.add(R.color.green);

        Random random=new Random();
        int number=random.nextInt(colorcode.size());
        return colorcode.get(number);
    }

}