package com.peerless2012.somehospital.data;

import java.util.List;


public class TypeInfo {

	private String name;
	
	List<HospitalInfo> hospitalInfos;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<HospitalInfo> getHospitalInfos() {
		return hospitalInfos;
	}

	public void setHospitalInfos(List<HospitalInfo> hospitalInfos) {
		this.hospitalInfos = hospitalInfos;
	}

	@Override
	public String toString() {
		return "TypeInfo [name=" + name + ", hospitalInfos=" + hospitalInfos
				+ "]";
	}
	
}
