package com.finalprojectkelompok6.ecommerceapp.users.pembelian.model;

public class Pembelian {

    private String namaBarang, totalBeli, totalHarga;

    public Pembelian() {
    }

    public Pembelian(String namaBarang, String totalBeli, String totalHarga) {
        this.namaBarang = namaBarang;
        this.totalBeli = totalBeli;
        this.totalHarga = totalHarga;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public String getTotalBeli() {
        return totalBeli;
    }

    public void setTotalBeli(String totalBeli) {
        this.totalBeli = totalBeli;
    }

    public String getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(String totalHarga) {
        this.totalHarga = totalHarga;
    }
}
