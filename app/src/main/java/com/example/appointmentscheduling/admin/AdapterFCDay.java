package com.example.appointmentscheduling.admin;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appointmentscheduling.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterFCDay extends RecyclerView.Adapter<AdapterFCDay.MyViewHolder>{

    Context context;
    static String username;
    Fragment fragment;
    List<ModelFCDay> notesList;

    public AdapterFCDay(Context context, Fragment fragment, List<ModelFCDay> notesList) {
        this.context = context;
        this.fragment = fragment;
        this.notesList = notesList;
    }

    @NonNull
    @NotNull
    @Override
    public AdapterFCDay.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_applicant_recycler_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterFCDay.MyViewHolder holder, int position) {
        holder.appId.setText(notesList.get(position).getAppId());
        holder.studName.setText(notesList.get(position).getStudName());
        holder.process.setText(notesList.get(position).getProcess());
        holder.course.setText(notesList.get(position).getCourse());
        holder.date.setText(notesList.get(position).getDate());
        holder.slot.setText(notesList.get(position).getSlot());

        holder.btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                DatabaseReference df = FirebaseDatabase.getInstance().getReference();

                df.child("Student")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                for(DataSnapshot snap : snapshot.getChildren()){

                                    if(snap.child("AppId").getValue().toString().equals(holder.appId.getText().toString())){
                                        snap.child(holder.process.getText().toString()).getRef().setValue("Done");
                                    }

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });

            }
        });


        holder.btnReschedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference df = FirebaseDatabase.getInstance().getReference();

                df.child("Student")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                for(DataSnapshot snap : snapshot.getChildren()){

                                    if(snap.child("AppId").getValue().toString().equals(holder.appId.getText().toString())){
                                        snap.child(holder.process.getText().toString()).getRef().setValue("Not Booked");
                                    }

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                            }
                        });

            }
        });
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView appId,studName,course,process,date,slot;
        RelativeLayout layout;
        Button btnDone,btnReschedule;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            process = itemView.findViewById(R.id.view_applicant_process);
            course = itemView.findViewById(R.id.view_applicant_course);
            appId = itemView.findViewById(R.id.view_applicant_id);
            studName = itemView.findViewById(R.id.view_applicant_name);
            date = itemView.findViewById(R.id.view_applicant_date);
            slot = itemView.findViewById(R.id.view_applicant_slot);

            btnDone = itemView.findViewById(R.id.view_applicant_btnDone);
            btnReschedule = itemView.findViewById(R.id.view_applicant_btnRescheduke);

            layout = itemView.findViewById(R.id.view_applicant_recycler);
        }
    }
}
