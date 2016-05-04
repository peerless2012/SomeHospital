package com.peerless2012.somehospital.data;

public class HospitalInfo {

	private String name;
	
	private String tel;
	
	private String web;
	
	private String web2;

	private String desc;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	public String getWeb2() {
		return web2;
	}

	public void setWeb2(String web2) {
		this.web2 = web2;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		return "HospitalInfo [name=" + name + ", tel=" + tel + ", web=" + web
				+ ", web2=" + web2 + "]";
	}
	
	
}
