package com.example.appointmentscheduling.superAdmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appointmentscheduling.R;
import com.example.appointmentscheduling.admin.AdapterApplyFC;
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

public class AdapterNotifyFC extends RecyclerView.Adapter<AdapterNotifyFC.MyViewHolder> {

    Context context;
    Fragment fragment;
    List<ModelNotifyFC> notesList;
    String collegeID,scheduleID,courseName,processName;

    public AdapterNotifyFC(Context context, Fragment fragment, List<ModelNotifyFC> notesList) {
        this.context = context;
        this.fragment = fragment;
        this.notesList = notesList;
    }

    @NonNull
    @NotNull
    @Override
    public AdapterNotifyFC.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_fc_recycler_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterNotifyFC.MyViewHolder holder, int position) {
        holder.clgId.setText(notesList.get(position).getClgId());
        holder.course.setText(notesList.get(position).getCourse());
        holder.process.setText(notesList.get(position).getProcess());
        holder.startDate.setText(notesList.get(position).getStartDate());
        holder.endDate.setText(notesList.get(position).getEndDate());

        holder.grant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference df = FirebaseDatabase.getInstance().getReference();

                df.child("Schedule")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                for(DataSnapshot snapSch : snapshot.getChildren()){
                                    if(snapSch.child("Course").getValue().toString().equals(holder.course.getText().toString()) && snapSch.child("Process").getValue().toString().equals(holder.process.getText().toString())){
                                        String schId = snapSch.child("ScheduleID").getValue().toString();

                                        df.child("Request")
                                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                                                        for(DataSnapshot snap : snapshot.getChildren()){
                                                            if(holder.clgId.getText().toString().equals(snap.child("ClgId").getValue().toString()) && schId.equals(snap.child("ScheduleId").getValue().toString())){
                                                                snap.getRef().child("Status").setValue("Granted");
                                                                appointmentMaintain(holder.clgId.getText().toString(),schId);
                                                                
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
        });


        holder.deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference df = FirebaseDatabase.getInstance().getReference();

                df.child("Schedule")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                for(DataSnapshot snapSch : snapshot.getChildren()){
                                    if(snapSch.child("Course").getValue().toString().equals(holder.course.getText().toString()) && snapSch.child("Process").getValue().toString().equals(holder.process.getText().toString())){
                                        String schId = snapSch.child("ScheduleID").getValue().toString();

                                        df.child("Request")
                                                .addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                                                        for(DataSnapshot snap : snapshot.getChildren()){
                                                            if(holder.clgId.getText().toString().equals(snap.child("ClgId").getValue().toString()) && schId.equals(snap.child("ScheduleId").getValue().toString())){
                                                                snap.getRef().child("Status").setValue("Denied");
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
        });
    }


    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView clgId,course,process,startDate,endDate;
        RelativeLayout layout;
        Button grant,deny;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            clgId = itemView.findViewById(R.id.notification_fc_inst_id);
            course = itemView.findViewById(R.id.notification_fc_course);
            process = itemView.findViewById(R.id.notification_fc_process);
            startDate = itemView.findViewById(R.id.notification_fc_startDate);
            endDate = itemView.findViewById(R.id.notification_fc_endDate);
            deny = itemView.findViewById(R.id.notification_fc_btnDeny);
            grant = itemView.findViewById(R.id.notification_fc_btnGrant);

            layout = itemView.findViewById(R.id.notification_fc_recycler);
        }
    }

    public void appointmentMaintain(String clg, String sch){
        String clgId = clg;
        String schId = sch;

        DatabaseReference df = FirebaseDatabase.getInstance().getReference();

        df.child("Request")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                        for(DataSnapshot snap : snapshot.getChildren()){
                            if(snap.child("Status").getValue().toString().equals("Granted") && snap.child("ClgId").getValue().toString().equals(clgId) && snap.child("ScheduleId").getValue().toString().equals(schId)){
                                collegeID = snap.child("ClgId").getValue().toString();
                                scheduleID = snap.child("ScheduleId").getValue().toString();

                                df.child("Schedule")
                                        .addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                for(DataSnapshot snap : snapshot.getChildren()){
                                                    if(snap.child("ScheduleID").getValue().toString().equals(scheduleID)){
                                                        courseName = snap.child("Course").getValue().toString();
                                                        processName = snap.child("Process").getValue().toString();
                                                        String startDate = snap.child("StartDate").getValue().toString();
                                                        String endDate = snap.child("EndDate").getValue().toString();

                                                        String[] firstDate = startDate.split("-",2);
                                                        int dateStart = Integer.parseInt(firstDate[0]);

                                                        String[] lastDate = endDate.split("-",2);
                                                        int dateEnd = Integer.parseInt(lastDate[0]);

                                                        int allDate[] = new int[(dateEnd-dateStart)+1];

                                                        for (int i = dateStart,j = 0;i <= dateEnd;i++,j++){
                                                            allDate[j] = i;
                                                        }

                                                        String[] datesFinal = new String[allDate.length];

                                                        for(int i=0;i < datesFinal.length;i++){
                                                            datesFinal[i] = allDate[i] +"-0"+ firstDate[1];
                                                        }


                                                        HashMap<String,Object> map = new HashMap<>();
                                                        map.put("09:00 am - 10:00 am","10");
                                                        map.put("10:00 am - 11:00 am","10");
                                                        map.put("11:00 am - 12:00 am","10");
                                                        map.put("12:00 pm - 01:00 pm","10");
                                                        map.put("02:00 pm - 03:00 pm","10");
                                                        map.put("03:00 pm - 04:00 pm","10");
                                                        map.put("04:00 pm - 05:00 pm","10");


                                                        for(int i=0;i < datesFinal.length;i++){

                                                            df.child("Appointment")
                                                                    .child(collegeID)
                                                                    .child(courseName)
                                                                    .child(processName)
                                                                    .child(datesFinal[i])
                                                                    .setValue(map)
                                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<Void> task) {
                                                                            //Toast.makeText(FCRequest.this,"Appoinment Table Created",Toast.LENGTH_LONG).show();
                                                                        }
                                                                    });



                                                        }

                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

}
