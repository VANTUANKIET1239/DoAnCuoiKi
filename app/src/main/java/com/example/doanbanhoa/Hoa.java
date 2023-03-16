package com.example.doanbanhoa;
public class Hoa {
    private int ID_Hoa;
    private String Image_Hoa;
    private String TenHoa;
    private String LoaiHoa;
    private int Gia;
    private String MoTa;
    private int HangDanhGia;
    private int SoLuongDanhGia;
    private  boolean IsDelete;
    private String ID_DanhMuc;

    public Hoa(int ID_Hoa, String image_Hoa, String tenHoa, String loaiHoa, int gia, String moTa, int hangDanhGia, int soLuongDanhGia, boolean isDelete, String ID_DanhMuc) {
        this.ID_Hoa = ID_Hoa;
        Image_Hoa = image_Hoa;
        TenHoa = tenHoa;
        LoaiHoa = loaiHoa;
        Gia = gia;
        MoTa = moTa;
        HangDanhGia = hangDanhGia;
        SoLuongDanhGia = soLuongDanhGia;
        IsDelete = isDelete;
        this.ID_DanhMuc = ID_DanhMuc;
    }
    public Hoa() {
        this.ID_Hoa = 0;
        Image_Hoa = "";
        TenHoa = "";
        LoaiHoa = "";
        Gia = 0;
        MoTa = "";
        HangDanhGia = 0;
        SoLuongDanhGia = 0;
        IsDelete = true;
        this.ID_DanhMuc = "";
    }


    public int getID_Hoa() {
        return ID_Hoa;
    }

    public void setID_Hoa(int ID_Hoa) {
        this.ID_Hoa = ID_Hoa;
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

    public int getHangDanhGia() {
        return HangDanhGia;
    }

    public void setHangDanhGia(int hangDanhGia) {
        HangDanhGia = hangDanhGia;
    }

    public int getSoLuongDanhGia() {
        return SoLuongDanhGia;
    }

    public void setSoLuongDanhGia(int soLuongDanhGia) {
        SoLuongDanhGia = soLuongDanhGia;
    }

    public boolean isDelete() {
        return IsDelete;
    }

    public void setDelete(boolean delete) {
        IsDelete = delete;
    }

    public String getID_DanhMuc() {
        return ID_DanhMuc;
    }

    public void setID_DanhMuc(String ID_DanhMuc) {
        this.ID_DanhMuc = ID_DanhMuc;
    }
}
