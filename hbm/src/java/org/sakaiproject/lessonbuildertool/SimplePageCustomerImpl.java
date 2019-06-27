package org.sakaiproject.lessonbuildertool;

public class SimplePageCustomerImpl implements SimplePageCustomer{
	private String idCustomer;
	private String namaCustomer;

	public String getIdCustomer(){
		return idCustomer;
	}
	public String getNamaCustomer(){
		return namaCustomer;
	}

	public void setIdCustomer(String idCustomer){
		this.idCustomer = idCustomer;
	}

	public void setNamaCustomer(String namaCustomer){
		this.namaCustomer = namaCustomer;
	}
}