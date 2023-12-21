package com.example.appointmentscheduling.superAdmin;

import android.os.Bundle;
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

public class NotificationFC extends Fragment {

    public static NotificationFC newInstance() {
        return new NotificationFC();
    }

    RecyclerView recyclerView;
    AdapterNotifyFC adapter;
    List<ModelNotifyFC> notesList;
    RelativeLayout relativeLayout;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.super_admin_notification_fc, container, false);

        recyclerView = view.findViewById(R.id.notification_fc_recycler_view);
        relativeLayout = view.findViewById(R.id.super_admin_notification_fc);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        notesList = new ArrayList<>();

        DatabaseReference df = FirebaseDatabase.getInstance().getReference();

        df.child("Request")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                        if(snapshot.exists()){

                            for(DataSnapshot snap : snapshot.getChildren()){

                                if(snap.child("Status").getValue().toString().equals("Pending")){
                                    String clgId = snap.child("ClgId").getValue().toString();
                                    String schId = snap.child("ScheduleId").getValue().toString();

                                    df.child("Schedule")
                                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                                    if(snapshot.exists()){
                                                        for(DataSnapshot snapSch : snapshot.getChildren()){
                                                            if(snapSch.child("ScheduleID").getValue().toString().equals(schId)){
                                                                ModelNotifyFC mv = new ModelNotifyFC(clgId,snapSch.child("Course").getValue().toString(),snapSch.child("Process").getValue().toString(),snapSch.child("StartDate").getValue().toString(),snapSch.child("EndDate").getValue().toString());
                                                                notesList.add(mv);
                                                            }
                                                            adapter = new AdapterNotifyFC(getContext(),NotificationFC.this , notesList);
                                                            recyclerView.setAdapter(adapter);
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
