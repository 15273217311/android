package com.example.weatherforecast;

public class Province {
    private int id;
    private String provinceName;
    private String provinceCode;
    Province()
    {

    }

    Province(int id,String provinceName,String provinceCode)
    {
        this.id=id;
        this.provinceName=provinceName;
        this.provinceCode=provinceCode;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Province{" +
                "id=" + id +
                ", provinceName='" + provinceName + '\'' +
                ", provinceCode='" + provinceCode + '\'' +
                '}';
    }
}
