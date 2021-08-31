package com.itbl.ekmuthosodai;

public class CartList {
    private  String itemId,itemName,price,shop,shopId,quantity,userId,shoptotal;

    public CartList(String itemId, String itemName, String price, String shop,String shopId, String quantity, String userId,String shoptotal) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.price = price;
        this.shop = shop;
        this.shopId = shopId;
        this.quantity = quantity;
        this.userId = userId;
        this.shoptotal = shoptotal;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }


    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getShopTotal() {
        return shoptotal;
    }

    public void setShoptotal(String shoptotal) {
        this.shoptotal = shoptotal;
    }
}
