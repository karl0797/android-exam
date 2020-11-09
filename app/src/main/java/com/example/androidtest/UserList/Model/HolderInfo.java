package com.example.androidtest.UserList.Model;

public class HolderInfo {
    String fname, lname, bday, age, email, mnumber, address, cperson, cpersonnumber;

    public HolderInfo(String fname, String lname, String bday, String age, String email, String mnumber, String address, String cperson, String cpersonnumber) {
        this.fname = fname;
        this.lname = lname;
        this.bday = bday;
        this.age = age;
        this.email = email;
        this.mnumber = mnumber;
        this.address = address;
        this.cperson = cperson;
        this.cpersonnumber = cpersonnumber;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getBday() {
        return bday;
    }

    public void setBday(String bday) {
        this.bday = bday;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMnumber() {
        return mnumber;
    }

    public void setMnumber(String mnumber) {
        this.mnumber = mnumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCperson() {
        return cperson;
    }

    public void setCperson(String cperson) {
        this.cperson = cperson;
    }

    public String getCpersonnumber() {
        return cpersonnumber;
    }

    public void setCpersonnumber(String cpersonnumber) {
        this.cpersonnumber = cpersonnumber;
    }
}
