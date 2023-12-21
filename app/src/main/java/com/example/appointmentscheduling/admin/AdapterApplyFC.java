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
import com.example.appointmentscheduling.superAdmin.AdapterVS;
import com.example.appointmentscheduling.superAdmin.ModelVS;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

public class AdapterApplyFC extends RecyclerView.Adapter<AdapterApplyFC.MyViewHolder>{

    Context context;
    static String username;
    Fragment fragment;
    List<ModelApplyFC> notesList;
    static int idCount = 100;

    public AdapterApplyFC(Context context, Fragment fragment, List<ModelApplyFC> notesList) {
        this.context = context;
        this.fragment = fragment;
        this.notesList = notesList;
    }

    @NonNull
    @NotNull
    @Override
    public AdapterApplyFC.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.apply_fc_recycler_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterApplyFC.MyViewHolder holder, int position) {
        holder.schId.setText(notesList.get(position).getId());
        holder.startdate.setText(notesList.get(position).getStart_date());
        holder.enddate.setText(notesList.get(position).getEnd_date());
        holder.process.setText(notesList.get(position).getProcess());
        holder.course.setText(notesList.get(position).getCourse());


        holder.btnApplyFC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference df = FirebaseDatabase.getInstance().getReference();

                df.child("Admin")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                for(DataSnapshot snap : snapshot.getChildren()){
                                    if(snap.child("Username").getValue().toString().equals(username)){
                                        String clgId = snap.child("ClgId").getValue().toString();

                                        HashMap<String,Object> map= new HashMap<>();
                                        map.put("RequestId",++idCount);
                                        map.put("ClgId",clgId);
                                        map.put("ScheduleId",holder.schId.getText().toString());
                                        map.put("Status","Pending");

                                        df.child("Request")
                                                .child(String.valueOf(idCount))
                                                .setValue(map)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull @NotNull Task<Void> task) {
                                                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                                                        builder.setCancelable(true);
                                                        builder.setMessage("Applied Successfully !!!");
                                                        builder.show();
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
        });

    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView startdate,enddate,process,course,schId;
        RelativeLayout layout;
        Button btnApplyFC;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            startdate = itemView.findViewById(R.id.apply_fc_startDate);
            enddate = itemView.findViewById(R.id.apply_fc_endDate);
            process = itemView.findViewById(R.id.apply_fc_process);
            course = itemView.findViewById(R.id.apply_fc_course);
            schId = itemView.findViewById(R.id.apply_fc_schId);
            btnApplyFC = itemView.findViewById(R.id.apply_fc_btnApply);
            layout = itemView.findViewById(R.id.apply_fc_recycler);
        }
    }
}

