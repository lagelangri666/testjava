package com.xlsx.java;

/**
 * @Auther:HuangZhiwen
 * @Date: 2020/11/11 - 11 - 18:54
 * @Description: com.xlsx.java
 * @version: 1.0
 */
public class ExcelDataVO {
    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 区站号
     */
    private Integer id;

    /**
     * 纬度
     */
    private Integer w;
    /**
     * 经度
     */
    private Integer h;
    /**
     * 海拔
     */
    private Integer high;
    /**
     * 年
     */
    private Integer year;
    /**
     * 月
     */
    private Integer month;
    /**
     * 日
     */
    private Integer day;
    /**
     * 平均气温
     */
    private double atemp;
    /**
     * 日最高气温
     */
    private double maxtemp;
    /**
     * 日最低气温
     */
    private double mintemp;


    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getW() {
        return w;
    }

    public void setW(Integer w) {
        this.w = w;
    }
    public Integer getH() {
        return h;
    }

    public void setH(Integer h) {
        this.h = h;
    }
    public Integer getHigh() {
        return high;
    }

    public void setHigh(Integer high) {
        this.high = high;
    }
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.w = w;
    }
    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }
    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }
    public double getAtemp() {
        return atemp;
    }

    public void setAtemp(Double atemp) {
        this.atemp = atemp;
    }
    public double getMaxtemp() {
        return maxtemp;
    }

    public void setMaxtemp(Double maxtemp) {
        this.maxtemp = maxtemp;
    }
    public double getMintemp() {
        return mintemp;
    }

    public void setMintemp(Double mintemp) {
        this.mintemp = mintemp;
    }
}
