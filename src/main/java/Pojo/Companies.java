package Pojo;

import Util.KeyId;

/**
 * 公司
 */
public class Companies {
    private String ucompid;//主键
    private String ucustomerid;//公司ID
    private char utype;//公司类型 0为采购商 1为供应商
    private String uaddress;//公司所属地区
    private String ucompname;//公司名称
    private String ucomplast;//公司最后交易时间
    private String ucompbillnum;//公司总交易次数：提单数
    private String isContact;//是否有联系方式 0为无 1为有
    private String ucompwebsite;//公司官网
    private String ucompaddress;//公司通信地址

    {
        this.ucompid = KeyId.nextId();
    }

    public Companies() {
        super();
    }

    public Companies(String ucustomerid, char utype, String uaddress, String ucompname, String ucomplast, String ucompbillnum, String isContact) {
        super();
        this.ucustomerid = ucustomerid;
        this.utype = utype;
        this.uaddress = uaddress;
        this.ucompname = ucompname;
        this.ucomplast = ucomplast;
        this.ucompbillnum = ucompbillnum;
        this.isContact = isContact;
    }

    public String getUcompid() {
        return ucompid;
    }

    public void setUcompid(String ucompid) {
        this.ucompid = ucompid;
    }

    public String getUcustomerid() {
        return ucustomerid;
    }

    public void setUcustomerid(String ucustomerid) {
        this.ucustomerid = ucustomerid;
    }

    public char getUtype() {
        return utype;
    }

    public void setUtype(char utype) {
        this.utype = utype;
    }

    public String getUaddress() {
        return uaddress;
    }

    public void setUaddress(String uaddress) {
        this.uaddress = uaddress;
    }

    public String getUcompname() {
        return ucompname;
    }

    public void setUcompname(String ucompname) {
        this.ucompname = ucompname;
    }

    public String getUcomplast() {
        return ucomplast;
    }

    public void setUcomplast(String ucomplast) {
        this.ucomplast = ucomplast;
    }

    public String getUcompbillnum() {
        return ucompbillnum;
    }

    public void setUcompbillnum(String ucompbillnum) {
        this.ucompbillnum = ucompbillnum;
    }

    public String getIsContact() {
        return isContact;
    }

    public void setIsContact(String isContact) {
        this.isContact = isContact;
    }

    public String getUcompwebsite() {
        return ucompwebsite;
    }

    public void setUcompwebsite(String ucompwebsite) {
        this.ucompwebsite = ucompwebsite;
    }

    public String getUcompaddress() {
        return ucompaddress;
    }

    public void setUcompaddress(String ucompaddress) {
        this.ucompaddress = ucompaddress;
    }

    @Override
    public String toString() {
        return "Companies{" +
                "ucompid='" + ucompid + '\'' +
                ", ucustomerid='" + ucustomerid + '\'' +
                ", utype=" + utype +
                ", uaddress='" + uaddress + '\'' +
                ", ucompname='" + ucompname + '\'' +
                ", ucomplast='" + ucomplast + '\'' +
                ", ucompbillnum='" + ucompbillnum + '\'' +
                ", isContact='" + isContact + '\'' +
                ", ucompwebsite='" + ucompwebsite + '\'' +
                ", ucompaddress='" + ucompaddress + '\'' +
                '}';
    }
}
