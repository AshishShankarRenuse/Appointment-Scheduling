package com.example.appointmentscheduling;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class StudentRegister extends Fragment{

    EditText studAppId,studName,studContact,studPassword,confirmPassword,studStream;
    Button submitStud;
    Spinner spinnerStream;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_register, container, false);

        submitStud = view.findViewById(R.id.stud_reg_submit);

        studAppId = view.findViewById(R.id.stud_reg_appId);
        studName = view.findViewById(R.id.stud_reg_name);
        studContact = view.findViewById(R.id.stud_reg_number);
        studPassword = view.findViewById(R.id.stud_reg_password);
        confirmPassword = view.findViewById(R.id.stud_confirm_password);
        spinnerStream = view.findViewById(R.id.spinnerStream);


        spinnerStream.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Toast.makeText(requireContext(), "Please select stream", Toast.LENGTH_LONG).show();
                    //return;
                }
                String selectedItem = (String) spinnerStream.getSelectedItem();
                Toast.makeText(requireContext(), selectedItem, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        submitStud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!TextUtils.isEmpty(studAppId.getText().toString()) && !TextUtils.isEmpty(studName.getText().toString()) && !TextUtils.isEmpty(studContact.getText().toString()) && !TextUtils.isEmpty(studPassword.getText().toString()) && !TextUtils.isEmpty(confirmPassword.getText().toString())){

                    if(studPassword.getText().toString().equals(confirmPassword.getText().toString())){
                        HashMap<String,Object> map = new HashMap<>();
                        map.put("AppId",studAppId.getText().toString());
                        map.put("Name",studName.getText().toString());
                        map.put("Contact",studContact.getText().toString());
                        map.put("Password",studPassword.getText().toString());
                        map.put("Stream",spinnerStream.getSelectedItem().toString());
                        map.put("FC","Not Booked");
                        map.put("ARC","Not Booked");
                        map.put("FCDate","0");
                        map.put("FCSlot","0");
                        map.put("ARCDate","0");
                        map.put("ARCSlot","0");
                        map.put("RequestId","0");



                        FirebaseDatabase.getInstance()
                                .getReference()
                                .child("Student")
                                .child(studAppId.getText().toString())
                                .setValue(map)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                        builder.setCancelable(true);
                                        builder.setMessage("Registered Successfully !!!");
                                        builder.setNegativeButton("Okay", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                studAppId.setText("");
                                                studName.setText("");
                                                studContact.setText("");
                                                studPassword.setText("");
                                                confirmPassword.setText("");

                                                dialog.cancel();
                                            }
                                        });
                                        builder.show();
                                    }
                                })

                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getContext(),"Something went wrong \n Try again Later.....",Toast.LENGTH_LONG);
                                    }
                                });
                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setCancelable(true);
                        builder.setMessage("Your password doesn't match !!!");
                        builder.setNegativeButton("Okay", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                confirmPassword.setText("");
                                dialog.cancel();
                            }
                        });
                        builder.show();
                    }
                }else{
                    Toast.makeText(getContext(), "Fill all the Details", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }
}