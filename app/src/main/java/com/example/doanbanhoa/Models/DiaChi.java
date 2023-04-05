package com.example.doanbanhoa.Models;

public class DiaChi {
   // private String id;
    private String HoTen;
    private String SDT;
    private String DiaChi;

    public DiaChi(String hoTen, String SDT, String diaChi) {
   //     this.id = id;
        HoTen = hoTen;
        this.SDT = SDT;
        DiaChi = diaChi;
    }
    public DiaChi() {
        //     this.id = id;
        HoTen = "";
        this.SDT = "";
        DiaChi = "";
    }

//    public String getId() {
//        return id;
//    }

//    public void setId(String id) {
//        this.id = id;
//    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String diaChi) {
        DiaChi = diaChi;
    }
}
