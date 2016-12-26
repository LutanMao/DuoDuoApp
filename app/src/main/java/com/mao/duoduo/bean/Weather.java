package com.mao.duoduo.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Mao on 16-12-26.
 */
@Entity
public class Weather {

    @Id
    private Long id;
    private int weaid;
    private String citynm;
    private String cityno;
    private Long cityid;
    private String area_1;
    private String area_2;
    private String area_3;
    @Generated(hash = 2070024509)
    public Weather(Long id, int weaid, String citynm, String cityno, Long cityid,
            String area_1, String area_2, String area_3) {
        this.id = id;
        this.weaid = weaid;
        this.citynm = citynm;
        this.cityno = cityno;
        this.cityid = cityid;
        this.area_1 = area_1;
        this.area_2 = area_2;
        this.area_3 = area_3;
    }
    @Generated(hash = 556711069)
    public Weather() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getWeaid() {
        return this.weaid;
    }
    public void setWeaid(int weaid) {
        this.weaid = weaid;
    }
    public String getCitynm() {
        return this.citynm;
    }
    public void setCitynm(String citynm) {
        this.citynm = citynm;
    }
    public String getCityno() {
        return this.cityno;
    }
    public void setCityno(String cityno) {
        this.cityno = cityno;
    }
    public Long getCityid() {
        return this.cityid;
    }
    public void setCityid(Long cityid) {
        this.cityid = cityid;
    }
    public String getArea_1() {
        return this.area_1;
    }
    public void setArea_1(String area_1) {
        this.area_1 = area_1;
    }
    public String getArea_2() {
        return this.area_2;
    }
    public void setArea_2(String area_2) {
        this.area_2 = area_2;
    }
    public String getArea_3() {
        return this.area_3;
    }
    public void setArea_3(String area_3) {
        this.area_3 = area_3;
    }

}
