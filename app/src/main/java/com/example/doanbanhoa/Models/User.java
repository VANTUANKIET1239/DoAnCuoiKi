package com.example.doanbanhoa.Models;

public class User {
    private String Imagea;
    private String HoTen;
    private String Email;
    private String SDT;
    private String NgaySinh;

    public User( String imagea, String hoTen, String email, String SDT, String ngaySinh) {

        this.Imagea = imagea;
        this.HoTen = hoTen;
        this.Email = email;
        this.SDT = SDT;
        this.NgaySinh = ngaySinh;
    }
    public User() {

        this.Imagea = "";
        this.HoTen = "";
        this.Email = "";
        this.SDT = "";
        this.NgaySinh = "";
    }



    public String getImagea() {
        return Imagea;
    }

    public void setImagea(String imagea) {
        Imagea = imagea;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(String ngaySinh) {
        NgaySinh = ngaySinh;
    }
}
