package com.itbl.ekmuthosodai;

public class LastItem {
    private String itemCode,siteId;


    public LastItem(String itemCode, String siteId) {
        this.itemCode = itemCode;
        this.siteId = siteId;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }
}
