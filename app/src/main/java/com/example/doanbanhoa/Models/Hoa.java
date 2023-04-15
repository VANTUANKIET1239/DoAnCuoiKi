package com.example.doanbanhoa.Models;

import java.io.Serializable;

public class Hoa implements Serializable {

    private String Id;
    private String Image_Hoa;
    private String TenHoa;
    private String LoaiHoa;
    private int Gia;
    private String MoTa;
    private Float HangDanhGia;
    private int SoLuongDanhGia;
    private String ID_DanhMuc;

    public Hoa(String ID_Hoa, String Image_Hoa, String TenHoa, String LoaiHoa, int Gia, String MoTa, Float HangDanhGia, int SoLuongDanhGia, String ID_DanhMuc) {
        this.Id = ID_Hoa;
        this.Image_Hoa = Image_Hoa;
        this.TenHoa = TenHoa;
        this.LoaiHoa = LoaiHoa;
        this.Gia = Gia;
        this.MoTa = MoTa;
        this.HangDanhGia = HangDanhGia;
        this.SoLuongDanhGia = SoLuongDanhGia;
        this.ID_DanhMuc = ID_DanhMuc;
    }
    public Hoa(Hoa k) {
        Image_Hoa = k.getImage_Hoa();
        TenHoa = k.getTenHoa();
        LoaiHoa = k.getLoaiHoa();
        Gia = k.getGia();
        MoTa = k.getMoTa();
        HangDanhGia = k.getHangDanhGia();
        SoLuongDanhGia = k.getSoLuongDanhGia();
        this.ID_DanhMuc = k.getID_DanhMuc();
    }

    public void LayHoa(Hoa k) {
        this.Id = k.Id;
        this.Image_Hoa = k.getImage_Hoa();
        this.TenHoa = k.getTenHoa();
        this.LoaiHoa = k.getLoaiHoa();
        this.Gia = k.getGia();
        this. MoTa = k.getMoTa();
        this.HangDanhGia = k.getHangDanhGia();
        this.SoLuongDanhGia = k.getSoLuongDanhGia();
        this.ID_DanhMuc = k.getID_DanhMuc();
    }
    public Hoa() {
        this.Id = "";
        Image_Hoa = "";
        TenHoa = "";
        LoaiHoa = "";
        Gia = 0;
        MoTa = "";
        HangDanhGia = 0f;
        SoLuongDanhGia = 0;
        this.ID_DanhMuc = "";
    }


//    public int getID_Hoa() {
//        return ID_Hoa;
//    }
//
//    public void setID_Hoa(int ID_Hoa) {
//        this.ID_Hoa = ID_Hoa;
//    }


    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getImage_Hoa() {
        return Image_Hoa;
    }

    public void setImage_Hoa(String image_Hoa) {
        Image_Hoa = image_Hoa;
    }

    public String getTenHoa() {
        return TenHoa;
    }

    public void setTenHoa(String tenHoa) {
        TenHoa = tenHoa;
    }

    public String getLoaiHoa() {
        return LoaiHoa;
    }

    public void setLoaiHoa(String loaiHoa) {
        LoaiHoa = loaiHoa;
    }

    public int getGia() {
        return Gia;
    }

    public void setGia(int gia) {
        Gia = gia;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String moTa) {
        MoTa = moTa;
    }

    public Float getHangDanhGia() {
        return HangDanhGia;
    }

    public void setHangDanhGia(Float hangDanhGia) {
        HangDanhGia = hangDanhGia;
    }

    public int getSoLuongDanhGia() {
        return SoLuongDanhGia;
    }

    public void setSoLuongDanhGia(int soLuongDanhGia) {
        SoLuongDanhGia = soLuongDanhGia;
    }

    public String getID_DanhMuc() {
        return ID_DanhMuc;
    }

    public void setID_DanhMuc(String ID_DanhMuc) {
        this.ID_DanhMuc = ID_DanhMuc;
    }

    @Override
    public String toString() {
        return "Hoa{" +
                "Id='" + Id + '\'' +
                ", Image_Hoa='" + Image_Hoa + '\'' +
                ", TenHoa='" + TenHoa + '\'' +
                ", LoaiHoa='" + LoaiHoa + '\'' +
                ", Gia=" + Gia +
                ", MoTa='" + MoTa + '\'' +
                ", HangDanhGia=" + HangDanhGia +
                ", SoLuongDanhGia=" + SoLuongDanhGia +
                ", ID_DanhMuc='" + ID_DanhMuc + '\'' +
                '}';
    }
}
