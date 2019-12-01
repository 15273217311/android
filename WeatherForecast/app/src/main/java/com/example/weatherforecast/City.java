package com.example.weatherforecast;

public class City {
    private int id;
    private String cityName;
    private String cityCode;
    private String provinceName;
    City(){}

    City(int id,String cityName,String cityCode, String provinceName){
        this.id=id;
        this.cityName=cityName;
        this.cityCode=cityCode;
        this.provinceName=provinceName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", cityNmae='" + cityName + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", provinceName=" + provinceName +
                '}';
    }
}
