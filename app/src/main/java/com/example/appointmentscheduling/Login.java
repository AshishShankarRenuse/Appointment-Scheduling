package com.example.appointmentscheduling;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appointmentscheduling.admin.AdminDraw;
import com.example.appointmentscheduling.admin.ViewApplicant;
import com.example.appointmentscheduling.student.StudentBookAppointment;
import com.example.appointmentscheduling.student.ViewAppointment;
import com.example.appointmentscheduling.superAdmin.SuperAdminDraw;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Login extends Fragment {

    Button login;
    EditText username,password;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        login = v.findViewById(R.id.button_login);
        username = v.findViewById(R.id.login_username);
        password = v.findViewById(R.id.login_password);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                char[] uname =  username.getText().toString().toCharArray();



                if(!TextUtils.isEmpty(username.getText().toString()) || !TextUtils.isEmpty(password.getText().toString())){
                    if((username.getText().toString().trim().equals("super")) && (password.getText().toString().trim().equals("super"))){
                        Intent intent = new Intent(getContext(), SuperAdminDraw.class);
                        startActivity(intent);
                    }
                    else if((uname[0] >= 65 && uname[0] <= 90) || (uname[0] >= 97 && uname[0] <= 122)){

                        FirebaseDatabase.getInstance()
                                .getReference()
                                .child("Admin")
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                                        for(DataSnapshot snap : snapshot.getChildren()){
                                            if(username.getText().toString().equals(snap.child("Username").getValue().toString()) && password.getText().toString().equals(snap.child("Password").getValue().toString()) && "Granted".equals(snap.child("Status").getValue().toString())){
                                                Intent intent = new Intent(getContext(), AdminDraw.class);
                                                intent.putExtra("username",username.getText().toString());
                                                startActivity(intent);
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                    }
                    else if(uname[0] >= 48 && uname[0] <= 57){

                        FirebaseDatabase.getInstance()
                                .getReference()
                                .child("Student")
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                                        for(DataSnapshot snap : snapshot.getChildren()){
                                            if(username.getText().toString().equals(snap.child("AppId").getValue().toString()) && password.getText().toString().equals(snap.child("Password").getValue().toString())){

                                                if(snap.child("FC").getValue().toString().equals("Booked") || snap.child("ARC").getValue().toString().equals("Booked")){
                                                    Intent intent = new Intent(getContext(), ViewAppointment.class);
                                                    intent.putExtra("username",username.getText().toString());
                                                    startActivity(intent);
                                                }else{
                                                    Intent intent = new Intent(getContext(), StudentBookAppointment.class);
                                                    intent.putExtra("username",username.getText().toString());
                                                    startActivity(intent);
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
                else{
                    Toast.makeText(getContext(), "Fill all details", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }
}