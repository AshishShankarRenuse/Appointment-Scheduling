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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class AdminRegister extends Fragment {

    Button submitAd;
    EditText clg_id,fname,emp_id,cont_num,clg_name,username,password,cfrm_password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_admin_register, container, false);


        clg_id = view.findViewById(R.id.admin_reg_clgId);
        clg_name = view.findViewById(R.id.admin_reg_clg_nm);
        emp_id = view.findViewById(R.id.admin_reg_empid);
        fname = view.findViewById(R.id.admin_reg_emp_name);
        cont_num = view.findViewById(R.id.admin_reg_contact);
        username = view.findViewById(R.id.admin_username);
        password = view.findViewById(R.id.admin_password);
        cfrm_password = view.findViewById(R.id.admin_conf_password);
        submitAd = view.findViewById(R.id.admin_reg_submit);


        submitAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!TextUtils.isEmpty(clg_id.getText().toString()) && !TextUtils.isEmpty(clg_name.getText().toString()) && !TextUtils.isEmpty(emp_id.getText().toString()) && !TextUtils.isEmpty(fname.getText().toString()) && !TextUtils.isEmpty(cont_num.getText().toString()) && !TextUtils.isEmpty(username.getText().toString()) && !TextUtils.isEmpty(password.getText().toString()) && !TextUtils.isEmpty(cfrm_password.getText().toString())){

                    if(cfrm_password.getText().toString().equals(password.getText().toString())){

                        HashMap<String,Object> map= new HashMap<>();
                        map.put("ClgId",clg_id.getText().toString());
                        map.put("EmpName",fname.getText().toString());
                        map.put("EmpId",emp_id.getText().toString());
                        map.put("Contact",cont_num.getText().toString());
                        map.put("ClgName",clg_name.getText().toString());
                        map.put("Username",username.getText().toString());
                        map.put("Password",password.getText().toString());
                        map.put("Status","Pending");

                        FirebaseDatabase.getInstance()
                                .getReference()
                                .child("Admin")
                                .child(clg_id.getText().toString())
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
                                                clg_id.setText("");
                                                clg_name.setText("");
                                                emp_id.setText("");
                                                fname.setText("");
                                                cont_num.setText("");
                                                username.setText("");
                                                password.setText("");
                                                cfrm_password.setText("");

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
                                cfrm_password.setText("");
                                dialog.cancel();
                            }
                        });
                        builder.show();
                    }



                }else {
                    Toast.makeText(getContext(), "Fill all the Details", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
}