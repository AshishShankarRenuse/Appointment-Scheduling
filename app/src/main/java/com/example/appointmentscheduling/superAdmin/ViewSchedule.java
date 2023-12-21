package com.example.appointmentscheduling.superAdmin;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appointmentscheduling.R;

import java.util.ArrayList;
import java.util.List;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class ViewSchedule extends Fragment {

    public static ViewSchedule newInstance() {
        return new ViewSchedule();
    }

    RecyclerView recyclerView;
    AdapterVS adapter;
    List<ModelVS> notesList;
    RelativeLayout relativeLayout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.super_admin_view_schedule, container, false);

        recyclerView = view.findViewById(R.id.vs_recycler_view);
        relativeLayout = view.findViewById(R.id.sup_adm_vs);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        notesList = new ArrayList<>();

        DatabaseReference df = FirebaseDatabase.getInstance().getReference().child("Schedule");
        df.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if(snapshot.exists()){

                    for(DataSnapshot snap : snapshot.getChildren()){
                        ModelVS mv = new ModelVS(snap.child("ScheduleID").getValue().toString(),snap.child("Course").getValue().toString(), snap.child("Process").getValue().toString(), snap.child("StartDate").getValue().toString(), snap.child("EndDate").getValue().toString());
                        notesList.add(mv);
                    }

                    adapter = new AdapterVS(getContext(),ViewSchedule.this , notesList);
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
}