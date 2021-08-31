package com.itbl.ekmuthosodai;

public class OrderInfo {
    private String USERID, LOC_CODE, ADDRESS1, ADDRESS2, EMAIL, PHONE_NO,PAYMENT_TYPE;

    public OrderInfo(String USERID, String LOC_CODE, String ADDRESS1, String ADDRESS2, String EMAIL, String PHONE_NO, String PAYMENT_TYPE) {
        this.USERID = USERID;
        this.LOC_CODE = LOC_CODE;
        this.ADDRESS1 = ADDRESS1;
        this.ADDRESS2 = ADDRESS2;
        this.EMAIL = EMAIL;
        this.PHONE_NO = PHONE_NO;
        this.PAYMENT_TYPE = PAYMENT_TYPE;
    }

    public String getUSERID() {
        return USERID;
    }

    public void setUSERID(String USERID) {
        this.USERID = USERID;
    }

    public String getLOC_CODE() {
        return LOC_CODE;
    }

    public void setLOC_CODE(String LOC_CODE) {
        this.LOC_CODE = LOC_CODE;
    }

    public String getADDRESS1() {
        return ADDRESS1;
    }

    public void setADDRESS1(String ADDRESS1) {
        this.ADDRESS1 = ADDRESS1;
    }

    public String getADDRESS2() {
        return ADDRESS2;
    }

    public void setADDRESS2(String ADDRESS2) {
        this.ADDRESS2 = ADDRESS2;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL = EMAIL;
    }

    public String getPHONE_NO() {
        return PHONE_NO;
    }

    public void setPHONE_NO(String PHONE_NO) {
        this.PHONE_NO = PHONE_NO;
    }

    public String getPAYMENT_TYPE() {
        return PAYMENT_TYPE;
    }

    public void setPAYMENT_TYPE(String PAYMENT_TYPE) {
        this.PAYMENT_TYPE = PAYMENT_TYPE;
    }
}
