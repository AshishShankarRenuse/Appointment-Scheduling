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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterReqAdmin extends RecyclerView.Adapter<AdapterReqAdmin.MyViewHolder> {

    Context context;
    Fragment fragment;
    List<ModelReqAdmin> notesList;

    public AdapterReqAdmin(Context context, Fragment fragment, List<ModelReqAdmin> notesList) {
        this.context = context;
        this.fragment = fragment;
        this.notesList = notesList;
    }

    @NonNull
    @NotNull
    @Override
    public AdapterReqAdmin.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_admin_recycler_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AdapterReqAdmin.MyViewHolder holder, int position) {
        holder.clgId.setText(notesList.get(position).getClgId());
        holder.clgName.setText(notesList.get(position).getClgName());
        holder.empId.setText(notesList.get(position).getEmpId());
        holder.empName.setText(notesList.get(position).getEmpName());
        holder.contact.setText(notesList.get(position).getContact());
        holder.username.setText(notesList.get(position).getUsername());

        holder.grant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseReference df = FirebaseDatabase.getInstance().getReference();

                df.child("Admin")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                                for(DataSnapshot snap : snapshot.getChildren()){
                                    if(holder.clgId.getText().toString().equals(snap.child("ClgId").getValue().toString())){
                                        snap.getRef().child("Status").setValue("Granted");
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

                df.child("Admin")
                        .addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                                for(DataSnapshot snap : snapshot.getChildren()){
                                    if(holder.clgId.getText().toString().equals(snap.child("ClgId").getValue().toString())){
                                        snap.getRef().child("Status").setValue("Denied");
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
        TextView clgId,clgName,empId,empName,contact,username;
        RelativeLayout layout;
        Button grant,deny;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            clgId = itemView.findViewById(R.id.request_admin_inst_id);
            clgName = itemView.findViewById(R.id.request_admin_inst_name);
            empId = itemView.findViewById(R.id.request_admin_empId);
            empName = itemView.findViewById(R.id.request_admin_empName);
            contact = itemView.findViewById(R.id.request_admin_contact);
            username = itemView.findViewById(R.id.request_admin_username);
            grant = itemView.findViewById(R.id.request_admin_btnGrant);
            deny = itemView.findViewById(R.id.request_admin_btnDeny);
            layout = itemView.findViewById(R.id.request_admin_recycler);
        }
    }

}

