package Pojo;

import Util.KeyId;

/**
 * 联系方式
 */
public class Contact {
    private String uconttactid;//联系方式ID
    private String ucustomerid;//公司ID
    private String ucontactperson;//联系人
    private String ucontactphone;//联系电话
    private String ucontactfax;//传真电话
    private String ucontactmail;//联系邮箱

    {
        this.uconttactid = KeyId.nextId();
    }

    public Contact() {
        super();
    }

    public Contact(String ucustomerid, String ucontactperson, String ucontactphone, String ucontactfax, String ucontactmail) {
        super();
        this.ucustomerid = ucustomerid;
        this.ucontactperson = ucontactperson;
        this.ucontactphone = ucontactphone;
        this.ucontactfax = ucontactfax;
        this.ucontactmail = ucontactmail;
    }

    public String getUconttactid() {
        return uconttactid;
    }

    public void setUconttactid(String uconttactid) {
        this.uconttactid = uconttactid;
    }

    public String getUcustomerid() {
        return ucustomerid;
    }

    public void setUcustomerid(String ucustomerid) {
        this.ucustomerid = ucustomerid;
    }

    public String getUcontactperson() {
        return ucontactperson;
    }

    public void setUcontactperson(String ucontactperson) {
        this.ucontactperson = ucontactperson;
    }

    public String getUcontactphone() {
        return ucontactphone;
    }

    public void setUcontactphone(String ucontactphone) {
        this.ucontactphone = ucontactphone;
    }

    public String getUcontactfax() {
        return ucontactfax;
    }

    public void setUcontactfax(String ucontactfax) {
        this.ucontactfax = ucontactfax;
    }

    public String getUcontactmail() {
        return ucontactmail;
    }

    public void setUcontactmail(String ucontactmail) {
        this.ucontactmail = ucontactmail;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "uconttactid='" + uconttactid + '\'' +
                ", ucustomerid=" + ucustomerid +
                ", ucontactperson='" + ucontactperson + '\'' +
                ", ucontactphone='" + ucontactphone + '\'' +
                ", ucontactfax='" + ucontactfax + '\'' +
                ", ucontactmail='" + ucontactmail + '\'' +
                '}';
    }
}
