package com.example.appointmentscheduling.admin;


import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appointmentscheduling.R;
import com.example.appointmentscheduling.superAdmin.ViewSchedule;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AdminMyAppliedFc extends Fragment {

    public static AdminMyAppliedFc newInstance() {
        return new AdminMyAppliedFc();
    }

    RecyclerView recyclerView;
    AdapterMyFC adapter;
    List<ModelMyFC> notesList;
    RelativeLayout relativeLayout;

    static String usernameFinal;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.admin_my_applied_fc_fragment, container, false);

        recyclerView = view.findViewById(R.id.my_applied_fc_recycler_view);
        relativeLayout = view.findViewById(R.id.admin_my_applied_fc);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        notesList = new ArrayList<>();

        DatabaseReference df = FirebaseDatabase.getInstance().getReference();
        df.child("Admin")
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot snap : snapshot.getChildren()){
                        if(snap.child("Username").getValue().toString().equals(usernameFinal)){
                            String clgId = snap.child("ClgId").getValue().toString();

                            df.child("Request")
                                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                                            for(DataSnapshot snap : snapshot.getChildren()){
                                                if(snap.child("ClgId").getValue().toString().equals(clgId)){
                                                    String schId = snap.child("ScheduleId").getValue().toString();
                                                    String reqId = snap.child("RequestId").getValue().toString();


                                                    df.child("Schedule")
                                                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                                                    for(DataSnapshot snapSch : snapshot.getChildren()){

                                                                            if(snapSch.child("ScheduleID").getValue().toString().equals(schId)){

                                                                                df.child("Request")
                                                                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                                                                            @Override
                                                                                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                                                                                for(DataSnapshot snap : snapshot.getChildren()){

                                                                                                        if(snap.child("RequestId").getValue().toString().equals(reqId)){
                                                                                                            ModelMyFC mv = new ModelMyFC(snapSch.child("ScheduleID").getValue().toString(),snapSch.child("Course").getValue().toString(), snapSch.child("Process").getValue().toString(), snapSch.child("StartDate").getValue().toString(), snapSch.child("EndDate").getValue().toString(), snap.child("Status").getValue().toString());
                                                                                                            notesList.add(mv);

                                                                                                        }

                                                                                                }
                                                                                                adapter = new AdapterMyFC(getContext(),AdminMyAppliedFc.this , notesList);
                                                                                                recyclerView.setAdapter(adapter);
                                                                                            }

                                                                                            @Override
                                                                                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                                                                            }
                                                                                        });
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

                                        @Override
                                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                        }
                                    });
                        }
                    }
                }else{
                    Toast.makeText(getContext(),"No Data",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


        return view;
    }

    public void setUsername(String usernameFinal){
        this.usernameFinal = usernameFinal;
    }
}