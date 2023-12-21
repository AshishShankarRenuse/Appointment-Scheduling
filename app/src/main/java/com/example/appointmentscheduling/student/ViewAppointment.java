package com.example.appointmentscheduling.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.example.appointmentscheduling.MainHomeActivity;
import com.example.appointmentscheduling.R;
import com.example.appointmentscheduling.superAdmin.AdapterAllClg;
import com.example.appointmentscheduling.superAdmin.ViewInstitutes;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ViewAppointment extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterViewApp adapter;
    List<ModelViewApp> notesList;
    RelativeLayout relativeLayout;
    static String uname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appointment);

        Bundle extra = getIntent().getExtras();
        uname = extra.getString("username");

        recyclerView = findViewById(R.id.view_appointment_recycler_view);
        relativeLayout = findViewById(R.id.student_view_appointment_);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        notesList = new ArrayList<>();

        DatabaseReference df = FirebaseDatabase.getInstance().getReference();

        df.child("Student")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            for(DataSnapshot snapStud : snapshot.getChildren()){
                                if(snapStud.child("AppId").getValue().toString().equals(uname) && Integer.parseInt(snapStud.child("RequestId").getValue().toString()) > 0){
                                    String reqId = snapStud.child("RequestId").getValue().toString();

                                    df.child("Request")
                                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                                    if(snapshot.exists()){
                                                        for(DataSnapshot snapReq : snapshot.getChildren()){
                                                            if(snapReq.child("RequestId").getValue().toString().equals(reqId)){
                                                                String clgId = snapReq.child("ClgId").getValue().toString();

                                                                df.child("Admin")
                                                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                                                            @Override
                                                                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                                                                if(snapshot.exists()){
                                                                                    for(DataSnapshot snapAd : snapshot.getChildren()){
                                                                                        if(snapAd.child("ClgId").getValue().toString().equals(clgId)){
                                                                                            String clg = snapAd.child("ClgName").getValue().toString();


                                                                                            df.child("Student")
                                                                                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                                        @Override
                                                                                                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                                                                                            if(snapshot.exists()){
                                                                                                                for(DataSnapshot snap : snapshot.getChildren()){
                                                                                                                    if((snap.child("FC").getValue().toString().equals("Booked") || snap.child("FC").getValue().toString().equals("Done")) && snap.child("AppId").getValue().toString().equals(uname)){
                                                                                                                        String process = snap.child("FC").getKey();
                                                                                                                        String date = snap.child("FCDate").getValue().toString();
                                                                                                                        String slot = snap.child("FCSlot").getValue().toString();
                                                                                                                        String status = snap.child("FC").getValue().toString();

                                                                                                                        ModelViewApp mv = new ModelViewApp(process,clg,date,slot,status);
                                                                                                                        notesList.add(mv);
                                                                                                                    }

                                                                                                                    if(snap.child("ARC").getValue().toString().equals("Booked")){
                                                                                                                        String process = snap.child("ARC").getKey();
                                                                                                                        String date = snap.child("ARCDate").getValue().toString();
                                                                                                                        String slot = snap.child("ARCSlot").getValue().toString();
                                                                                                                        String status = snap.child("ARC").getValue().toString();

                                                                                                                        ModelViewApp mv = new ModelViewApp(process,clg,date,slot,status);
                                                                                                                        notesList.add(mv);
                                                                                                                    }
                                                                                                                }

                                                                                                                adapter = new AdapterViewApp(notesList);
                                                                                                                recyclerView.setAdapter(adapter);
                                                                                                            }
                                                                                                        }

                                                                                                        @Override
                                                                                                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                                                                                        }
                                                                                                    });
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }

                                                                            @Override
                                                                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                                                            }
                                                                        });
                                                            }
                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                                }
                                            });
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_appointment_menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.view_appointment_action_logout:
                Intent i = new Intent(ViewAppointment.this, MainHomeActivity.class);
                startActivity(i);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}