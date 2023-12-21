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

public class ViewInstitutes extends Fragment {


    public static ViewInstitutes newInstance() {
        return new ViewInstitutes();
    }

    RecyclerView recyclerView;
    AdapterAllClg adapter;
    List<ModelAllClg> notesList;
    RelativeLayout relativeLayout;




    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.super_admin_view_institutes, container, false);

        recyclerView = view.findViewById(R.id.view_inst_recycler_view);
        relativeLayout = view.findViewById(R.id.super_admin_view_institute);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        notesList = new ArrayList<>();

        DatabaseReference df = FirebaseDatabase.getInstance().getReference().child("Admin");
        df.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                if(snapshot.exists()){
                    for(DataSnapshot snap : snapshot.getChildren()){
                        if(snap.child("Status").getValue().toString().equals("Granted")){
                            String clg = snap.child("ClgName").getValue().toString();
                            String[] clgnm = clg.split(",",2);

                            ModelAllClg mv = new ModelAllClg(snap.child("ClgId").getValue().toString(),clgnm[0],clgnm[1]);
                            notesList.add(mv);
                        }
                    }

                    adapter = new AdapterAllClg(getContext(),ViewInstitutes.this , notesList);
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
