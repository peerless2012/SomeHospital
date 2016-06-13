package com.peerless2012.somehospital.data.bean;

import java.util.Arrays;
/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/6/13 21:31
 * @Version V1.0
 * @Description :
 */
public class HospitalInfo {
    private String name;
    private String[] phones;
    private String[] websets;
    private String address;
    private String[] proofs;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String[] getPhones() {
        return phones;
    }

    public void addPhone(String phone){
        if (phones == null) {
            phones = new String[5];
        }
        for (int i = 0; i < phones.length; i++) {
            if (phones[i] == null) {
                phones[i] = phone;
                break;
            }
        }
    }

    public void setPhones(String[] phones) {
        this.phones = phones;
    }
    public String[] getWebsets() {
        return websets;
    }

    public void setWebsets(String[] websets) {
        this.websets = websets;
    }

    public void addWebset(String webset){
        if (websets == null) {
            websets = new String[5];
        }
        for (int i = 0; i < websets.length; i++) {
            if (websets[i] == null) {
                websets[i] = webset;
                break;
            }
        }
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public void addProof(String proof){
        if (proofs == null) {
            proofs = new String[5];
        }
        for (int i = 0; i < proofs.length; i++) {
            if (proofs[i] == null) {
                proofs[i] = proof;
                break;
            }
        }
    }

    public String[] getProofs() {
        return proofs;
    }
    public void setProofs(String[] proofs) {
        this.proofs = proofs;
    }
    @Override
    public String toString() {
        return "HospitalInfo [name=" + name + ", phones="
                + Arrays.toString(phones) + ", websets="
                + Arrays.toString(websets) + ", address=" + address
                + ", proofs=" + Arrays.toString(proofs) + "]";
    }
}
