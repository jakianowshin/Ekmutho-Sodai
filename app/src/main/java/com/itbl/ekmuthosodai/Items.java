package com.itbl.ekmuthosodai;

public class Items {
    private String itemid,itemdes,status;

    public Items(String itemid, String itemdes, String status) {
        this.itemid = itemid;
        this.itemdes = itemdes;
        this.status = status;
    }

    public Items(){

    }

    public Items(String itemid) {
        this.itemid = itemid;
    }

    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    public String getItemdes() {
        return itemdes;
    }

    public void setItemdes(String itemdes) {
        this.itemdes = itemdes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
