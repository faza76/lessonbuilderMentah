package org.sakaiproject.lessonbuildertool;

public class PenjualanImpl implements Penjualan{
	
	int kode_produk;
	String nama_produk;
	int harga;
	int stok;
	String deskripsi;
	String user_id;
	
	public PenjualanImpl() {}
	
	public int getKode_produk() {
		return kode_produk;
	}
	public void setKode_produk(int kode_produk) {
		this.kode_produk = kode_produk;
	}
	public String getNama_produk() {
		return nama_produk;
	}
	public void setNama_produk(String nama_produk) {
		this.nama_produk = nama_produk;
	}
	public int getHarga() {
		return harga;
	}
	public void setHarga(int harga) {
		this.harga = harga;
	}
	public int getStok() {
		return stok;
	}
	public void setStok(int stok) {
		this.stok = stok;
	}
	public String getDeskripsi() {
		return deskripsi;
	}
	public void setDeskripsi(String deskripsi) {
		this.deskripsi = deskripsi;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
}
