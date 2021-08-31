package com.itbl.ekmuthosodai;

public class ShopDetail {
    private String shopId, shopTotal;

    public ShopDetail(String shopId, String shopTotal) {
        this.shopId = shopId;
        this.shopTotal = shopTotal;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopTotal() {
        return shopTotal;
    }

    public void setShopTotal(String shopTotal) {
        this.shopTotal = shopTotal;
    }
}
