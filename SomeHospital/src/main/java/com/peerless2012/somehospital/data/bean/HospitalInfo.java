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
    private Geo geo;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String[] getPhones() {
        return phones;
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

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String[] getProofs() {
        return proofs;
    }
    public void setProofs(String[] proofs) {
        this.proofs = proofs;
    }

    public Geo getGeo() {
        return geo;
    }

    public void setGeo(Geo geo) {
        this.geo = geo;
    }

    @Override
    public String toString() {
        return "HospitalInfo [name=" + name + ", phones="
                + Arrays.toString(phones) + ", websets="
                + Arrays.toString(websets) + ", address=" + address
                + ", proofs=" + Arrays.toString(proofs) + "]";
    }
}
