package com.finalprojectkelompok6.ecommerceapp.users.detail.model;

public class Handphone {
    private String id, nama, category, jumlah_barang, harga_barang, image_product;

    public Handphone() {

    }

    public Handphone(String nama, String category, String jumlah_barang, String harga_barang, String image_product) {
        this.nama = nama;
        this.category = category;
        this.jumlah_barang = jumlah_barang;
        this.harga_barang = harga_barang;
        this.image_product = image_product;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getJumlah_barang() {
        return jumlah_barang;
    }

    public void setJumlah_barang(String jumlah_barang) {
        this.jumlah_barang = jumlah_barang;
    }

    public String getHarga_barang() {
        return harga_barang;
    }

    public void setHarga_barang(String harga_barang) {
        this.harga_barang = harga_barang;
    }

    public String getImage_product() {
        return image_product;
    }

    public void setImage_product(String image_product) {
        this.image_product = image_product;
    }
}
