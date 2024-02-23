package com.example.springsecurity_jjwt.login.dto;

public class ErroDTO {

	private int status;
	private String messsage;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMesssage() {
		return messsage;
	}
	public void setMesssage(String messsage) {
		this.messsage = messsage;
	}
	public ErroDTO(int status, String messsage) {
		super();
		this.status = status;
		this.messsage = messsage;
	}
	public ErroDTO() {
		super();
	}
	
	
	
	
		
}
