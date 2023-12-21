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

public class AdminRequest extends Fragment {

    RecyclerView recyclerView;
    AdapterReqAdmin adapter;
    List<ModelReqAdmin> notesList;
    RelativeLayout relativeLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.super_admin_request_admin, container, false);

        recyclerView = view.findViewById(R.id.admin_request_recycler_view);
        relativeLayout = view.findViewById(R.id.super_admin_request_admin);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        notesList = new ArrayList<>();

        DatabaseReference df = FirebaseDatabase.getInstance().getReference();

        df.child("Admin")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                        if(snapshot.exists()){

                            for(DataSnapshot snap : snapshot.getChildren()){

                                if(snap.child("Status").getValue().toString().equals("Pending")) {
                                    ModelReqAdmin mv = new ModelReqAdmin(snap.child("ClgId").getValue().toString(),snap.child("ClgName").getValue().toString(),snap.child("EmpId").getValue().toString(),snap.child("EmpName").getValue().toString(),snap.child("Contact").getValue().toString(),snap.child("Username").getValue().toString());
                                    notesList.add(mv);
                                }
                            }
                            adapter = new AdapterReqAdmin(getContext(),AdminRequest.this , notesList);
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
