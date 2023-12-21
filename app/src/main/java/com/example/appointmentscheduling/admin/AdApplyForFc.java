package com.example.appointmentscheduling.admin;

import android.os.Bundle;
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
import com.example.appointmentscheduling.superAdmin.AdapterVS;
import com.example.appointmentscheduling.superAdmin.ModelVS;
import com.example.appointmentscheduling.superAdmin.ViewSchedule;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AdApplyForFc extends Fragment {

    public static AdApplyForFc newInstance() {
        return new AdApplyForFc();
    }

    RecyclerView recyclerView;
    AdapterApplyFC adapter;
    List<ModelApplyFC> notesList;
    RelativeLayout relativeLayout;

    static String usernameFinal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.admin_apply_for_fc_fragment, container, false);

        recyclerView = view.findViewById(R.id.apply_fc_recycler_view);
        relativeLayout = view.findViewById(R.id.admin_apply_fc);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        notesList = new ArrayList<>();

        AdapterApplyFC.username = usernameFinal;


        DatabaseReference df = FirebaseDatabase.getInstance().getReference().child("Schedule");
        df.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    for(DataSnapshot snap : snapshot.getChildren()){
                        ModelApplyFC mv = new ModelApplyFC(snap.child("ScheduleID").getValue().toString(),snap.child("Course").getValue().toString(), snap.child("Process").getValue().toString(), snap.child("StartDate").getValue().toString(), snap.child("EndDate").getValue().toString());
                        notesList.add(mv);
                    }

                    adapter = new AdapterApplyFC(getContext(),AdApplyForFc.this , notesList);
                    recyclerView.setAdapter(adapter);

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
