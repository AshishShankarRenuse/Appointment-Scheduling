package com.example.appointmentscheduling.superAdmin;

public class ModelReqAdmin {

    public String clgId;
    public String clgName;
    public String empId;
    public String empName;
    public String contact;
    public String username;

    public ModelReqAdmin(String clgId,String clgName,String empId,String empName,String contact,String username){
        this.clgId = clgId;
        this.clgName = clgName;
        this.empId = empId;
        this.empName = empName;
        this.contact = contact;
        this.username = username;
    }

    public String getClgId() {
        return clgId;
    }

    public void setClgId(String clgId) {
        this.clgId = clgId;
    }

    public String getClgName() {
        return clgName;
    }

    public void setClgName(String clgName) {
        this.clgName = clgName;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
