package com.example.doanbanhoa.Models;

import java.util.List;

public class Item {

        private Hoa hoa;
        private int soLuong;
        private int tongGia;

        public Hoa getHoa() {
            return hoa;
        }

        public void setHoa(Hoa hoa) {
            this.hoa = hoa;
        }

        public int getSoLuong() {
            return soLuong;
        }

        public void setSoLuong(int soLuong) {
            this.soLuong = soLuong;
        }

        public int getTongGia() {
            return tongGia;
        }

        public void setTongGia(int tongGia) {
            this.tongGia = tongGia;
        }

        public Item(Hoa hoa, int soLuong) {
            this.hoa = hoa;
            this.soLuong = soLuong;
            this.tongGia = hoa.getGia() * soLuong;
        }
}


