package Pojo;

import Util.KeyId;

/**
 * 提关单
 */
public class Bill {
    private String ufreightblid;    //唯一提单ID
    private String ucustomerid;    //公司ID
    private String ublno;    //提单编号
    private String upurchaser;   //采购商
    private String usupplier;    //供应商
    private String utradedate;    //交易日期
    private String upurchasingarea;    //采购区域
    private String usupplyarea;    //供应区域
    private String uproductdesc;    //产品描述
    private String uweight;    //重量
    private String unumber;    //数量
    private String utotalamount;    //总金额
    private String uprice;    //单价
    private String ubxunit;    //箱/Unit
    private String ushipmentport;    //出货港口
    private String upurchaseport;    //进货港口
    private String ushippingtype;    //运输方式
    private String ucreatedate;    //采集日期

    {
        this.ufreightblid = KeyId.nextId();
    }

    public Bill() {
        super();
    }

    public Bill(String ucustomerid, String ublno, String upurchaser, String usupplier, String utradedate, String upurchasingarea,
                String usupplyarea, String uproductdesc, String uweight, String unumber, String utotalamount, String uprice,
                String ubxunit, String ushipmentport, String upurchaseport, String ushippingtype) {
        super();
        this.ucustomerid = ucustomerid;
        this.ublno = ublno;
        this.upurchaser = upurchaser;
        this.usupplier = usupplier;
        this.utradedate = utradedate;
        this.upurchasingarea = upurchasingarea;
        this.usupplyarea = usupplyarea;
        this.uproductdesc = uproductdesc;
        this.uweight = uweight;
        this.unumber = unumber;
        this.utotalamount = utotalamount;
        this.uprice = uprice;
        this.ubxunit = ubxunit;
        this.ushipmentport = ushipmentport;
        this.upurchaseport = upurchaseport;
        this.ushippingtype = ushippingtype;
    }

    public String getUfreightblid() {
        return ufreightblid;
    }

    public void setUfreightblid(String ufreightblid) {
        this.ufreightblid = ufreightblid;
    }

    public String getUcustomerid() {
        return ucustomerid;
    }

    public void setUcustomerid(String ucustomerid) {
        this.ucustomerid = ucustomerid;
    }

    public String getUblno() {
        return ublno;
    }

    public void setUblno(String ublno) {
        this.ublno = ublno;
    }

    public String getUpurchaser() {
        return upurchaser;
    }

    public void setUpurchaser(String upurchaser) {
        this.upurchaser = upurchaser;
    }

    public String getUsupplier() {
        return usupplier;
    }

    public void setUsupplier(String usupplier) {
        this.usupplier = usupplier;
    }

    public String getUtradedate() {
        return utradedate;
    }

    public void setUtradedate(String utradedate) {
        this.utradedate = utradedate;
    }

    public String getUpurchasingarea() {
        return upurchasingarea;
    }

    public void setUpurchasingarea(String upurchasingarea) {
        this.upurchasingarea = upurchasingarea;
    }

    public String getUsupplyarea() {
        return usupplyarea;
    }

    public void setUsupplyarea(String usupplyarea) {
        this.usupplyarea = usupplyarea;
    }

    public String getUproductdesc() {
        return uproductdesc;
    }

    public void setUproductdesc(String uproductdesc) {
        this.uproductdesc = uproductdesc;
    }

    public String getUweight() {
        return uweight;
    }

    public void setUweight(String uweight) {
        this.uweight = uweight;
    }

    public String getUnumber() {
        return unumber;
    }

    public void setUnumber(String unumber) {
        this.unumber = unumber;
    }

    public String getUtotalamount() {
        return utotalamount;
    }

    public void setUtotalamount(String utotalamount) {
        this.utotalamount = utotalamount;
    }

    public String getUprice() {
        return uprice;
    }

    public void setUprice(String uprice) {
        this.uprice = uprice;
    }

    public String getUbxunit() {
        return ubxunit;
    }

    public void setUbxunit(String ubxunit) {
        this.ubxunit = ubxunit;
    }

    public String getUshipmentport() {
        return ushipmentport;
    }

    public void setUshipmentport(String ushipmentport) {
        this.ushipmentport = ushipmentport;
    }

    public String getUpurchaseport() {
        return upurchaseport;
    }

    public void setUpurchaseport(String upurchaseport) {
        this.upurchaseport = upurchaseport;
    }

    public String getUshippingtype() {
        return ushippingtype;
    }

    public void setUshippingtype(String ushippingtype) {
        this.ushippingtype = ushippingtype;
    }

    public String getUcreatedate() {
        return ucreatedate;
    }

    public void setUcreatedate(String ucreatedate) {
        this.ucreatedate = ucreatedate;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "ufreightblid='" + ufreightblid + '\'' +
                ", ucustomerid='" + ucustomerid + '\'' +
                ", ublno='" + ublno + '\'' +
                ", upurchaser='" + upurchaser + '\'' +
                ", usupplier='" + usupplier + '\'' +
                ", utradedate='" + utradedate + '\'' +
                ", upurchasingarea='" + upurchasingarea + '\'' +
                ", usupplyarea='" + usupplyarea + '\'' +
                ", uproductdesc='" + uproductdesc + '\'' +
                ", uweight='" + uweight + '\'' +
                ", unumber='" + unumber + '\'' +
                ", utotalamount='" + utotalamount + '\'' +
                ", uprice='" + uprice + '\'' +
                ", ubxunit='" + ubxunit + '\'' +
                ", ushipmentport='" + ushipmentport + '\'' +
                ", upurchaseport='" + upurchaseport + '\'' +
                ", ushippingtype='" + ushippingtype + '\'' +
                ", ucreatedate='" + ucreatedate + '\'' +
                '}';
    }

}
