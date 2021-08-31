package com.itbl.ekmuthosodai;

public class OrderDetails {
    private String orderId,itemName,itemPrice,shopId,qty,userId,shopTotal,shopName,itemId;

    public OrderDetails(String orderId, String itemName,
                        String itemPrice, String shopId, String qty, String userId, String shopTotal,String shopName
    ,String itemId) {
        this.orderId = orderId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.shopId = shopId;
        this.qty = qty;
        this.userId = userId;
        this.shopTotal = shopTotal;
        this.shopName = shopName;
        this.itemId = itemId;
    }


    public OrderDetails( String shopName,String itemPrice,
                          String qty,String itemName)
            {
                this.shopName = shopName;
                this.itemPrice = itemPrice;
                this.qty = qty;
                this.itemName = itemName;

    }



    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getShopTotal() {
        return shopTotal;
    }

    public void setShopTotal(String shopTotal) {
        this.shopTotal = shopTotal;
    }
}
