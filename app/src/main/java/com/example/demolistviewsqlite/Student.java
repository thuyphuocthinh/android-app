package com.example.demolistviewsqlite;

import android.util.Log;
import android.widget.DatePicker;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.text.ParseException;

public class Student implements Serializable {
    public String HoSV;
    public String HolotSV;
    public String TenSV;
    public String Lop;
    public int NamsinhSV;
    public String birthday;
    public String MaSV;
    public String address;
    public String phoneNumber;
    public Student(){

    }

    public Student(String hoSV, String holotSV, String tenSV, String lop, int namsinhSV, String birthdayStr, String maSV, String address, String phoneNumber) throws ParseException {
        HoSV = hoSV;
        HolotSV = holotSV;
        TenSV = tenSV;
        Lop = lop;
        NamsinhSV = namsinhSV;
        this.birthday = birthdayStr;
        MaSV = maSV;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setHo(String ho){
        this.HoSV = ho;
    }
    public String getHo(){
        return this.HoSV;
    }

    public void setHolot(String holot){
        this.HolotSV = holot;
    }
    public String getHolot(){
        return this.HolotSV;
    }

    public void setTen(String ten){
        this.TenSV = ten;
    }
    public String getTen(){
        return this.TenSV;
    }

    public void setLop(String lop){
        this.Lop = lop;
    }
    public String getLop(){
        return this.Lop;
    }

    public String getHoSV() {
        return HoSV;
    }

    public void setHoSV(String hoSV) {
        HoSV = hoSV;
    }

    public String getHolotSV() {
        return HolotSV;
    }

    public void setHolotSV(String holotSV) {
        HolotSV = holotSV;
    }

    public String getTenSV() {
        return TenSV;
    }

    public void setTenSV(String tenSV) {
        TenSV = tenSV;
    }

    public int getNamsinhSV() {
        return NamsinhSV;
    }

    public void setNamsinhSV(int namsinhSV) {
        NamsinhSV = namsinhSV;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setNamsinh(int ns){
        this.NamsinhSV = ns;
    }
    public int getNamsinh(){
        return this.NamsinhSV;
    }

    public void setMaSV(String masv){
        this.MaSV = masv;
    }
    public String getMaSV(){
        return this.MaSV;
    }

    public String getFullName (){
        String fullname = this.HoSV + " " + this.HolotSV + " " + this.TenSV;
        fullname.trim();
        fullname.replace("  ", " ");
        return this.HoSV + " " + this.HolotSV + " " + this.TenSV;
    }
    public int getTuoi() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        return year - this.NamsinhSV;
    }

}
