package com.example.appointmentscheduling.superAdmin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appointmentscheduling.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AcceptedCenters extends Fragment {

    RecyclerView recyclerView;
    AdapterAcceptCenters adapter;
    List<ModelAcceptCenters> notesList;
    RelativeLayout relativeLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.super_admin_accepted_centers, container, false);

        recyclerView = view.findViewById(R.id.accepted_centers_recycler_view);
        relativeLayout = view.findViewById(R.id.super_admin_accepted_centers);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        notesList = new ArrayList<>();

        DatabaseReference df = FirebaseDatabase.getInstance().getReference();

        df.child("Request")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            for(DataSnapshot snap : snapshot.getChildren()){
                                if(snap.child("Status").getValue().toString().equals("Granted")){
                                    String clgId = snap.child("ClgId").getValue().toString();
                                    String schId = snap.child("ScheduleId").getValue().toString();

                                    df.child("Schedule")
                                            .addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                                    if(snapshot.exists()){
                                                        for(DataSnapshot snapSch : snapshot.getChildren()){
                                                            if(snapSch.child("ScheduleID").getValue().toString().equals(schId)){
                                                                String course = snapSch.child("Course").getValue().toString();
                                                                String process = snapSch.child("Process").getValue().toString();

                                                                df.child("Admin")
                                                                        .addValueEventListener(new ValueEventListener() {
                                                                            @Override
                                                                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                                                                if(snapshot.exists()){
                                                                                    for(DataSnapshot snapAd : snapshot.getChildren()){
                                                                                        if(snapAd.child("ClgId").getValue().toString().equals(clgId)){
                                                                                            String clgId = snapAd.child("ClgId").getValue().toString();
                                                                                            String clgName = snapAd.child("ClgName").getValue().toString();
                                                                                            String contact = snapAd.child("Contact").getValue().toString();

                                                                                            String[] clg = clgName.split(",",2);

                                                                                            ModelAcceptCenters mv = new ModelAcceptCenters(clgId,clg[0],clg[1],contact,course,process);
                                                                                            notesList.add(mv);


                                                                                        }
                                                                                    }
                                                                                }
                                                                                adapter = new AdapterAcceptCenters(getContext(),AcceptedCenters.this , notesList);
                                                                                recyclerView.setAdapter(adapter);
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



        return view;
    }
}
