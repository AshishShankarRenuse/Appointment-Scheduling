package com.example.appointmentscheduling.admin;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

public class ViewApplicant extends Fragment {
    static String username,process,course,date,slot;
    static String clgIdFinal,schIdFinal,reqIdFinal;

    RecyclerView recyclerView;
    AdapterFCDay adapter;
    List<ModelFCDay> notesList;
    RelativeLayout relativeLayout;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.admin_view_applicant, container, false);

        recyclerView = view.findViewById(R.id.view_applicant_recycler_view);
        relativeLayout = view.findViewById(R.id.admin_view_applicants);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        notesList = new ArrayList<>();



        DatabaseReference df = FirebaseDatabase.getInstance().getReference();

        Log.d("username", username);


        df.child("Admin")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        for(DataSnapshot snap : snapshot.getChildren()){
                            if(snap.child("Username").getValue().toString().equals(username)){
                                clgIdFinal = snap.child("ClgId").getValue().toString();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });

        df.child("Schedule")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        for(DataSnapshot snap : snapshot.getChildren()){
                            if(snap.child("Course").getValue().toString().equals(course) && snap.child("Process").getValue().toString().equals(process)){
                                schIdFinal = snap.child("ScheduleID").getValue().toString();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });


        df.child("Request")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        for(DataSnapshot snap : snapshot.getChildren()){
                            if(snap.child("ClgId").getValue().toString().equals(clgIdFinal) && snap.child("ScheduleId").getValue().toString().equals(schIdFinal)){
                                reqIdFinal = snap.child("RequestId").getValue().toString();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });

        df.child("Student")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        for(DataSnapshot snap : snapshot.getChildren()){

                            if(snap.child("FC").getKey().equals(process) && snap.child("FC").getValue().toString().equals("Booked") && snap.child("FCDate").getValue().toString().equals(date) && snap.child("FCSlot").getValue().toString().equals(slot)){
                                String id = snap.child("AppId").getValue().toString();
                                String name = snap.child("Name").getValue().toString();
                                String cour = snap.child("Stream").getValue().toString();
                                String proc = snap.child("FC").getKey();
                                String date = snap.child("FCDate").getValue().toString();
                                String slot = snap.child("FCSlot").getValue().toString();

                                ModelFCDay mv = new ModelFCDay(id,name,cour,proc,date,slot);
                                notesList.add(mv);
                            }

                            if(snap.child("ARC").getKey().equals(process) && snap.child("ARC").getValue().toString().equals("Booked") && snap.child("ARCDate").getValue().toString().equals(date) && snap.child("ARCSlot").getValue().toString().equals(slot)){
                                String id = snap.child("AppId").getValue().toString();
                                String name = snap.child("Name").getValue().toString();
                                String cour = snap.child("Stream").getValue().toString();
                                String proc = snap.child("ARC").getKey();
                                String date = snap.child("ARCDate").getValue().toString();
                                String slot = snap.child("ARCSlot").getValue().toString();

                                ModelFCDay mv = new ModelFCDay(id,name,cour,proc,date,slot);
                                notesList.add(mv);
                            }

                            adapter = new AdapterFCDay(getContext(),ViewApplicant.this , notesList);
                            recyclerView.setAdapter(adapter);

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {

                    }
                });


        return view;
    }
}
