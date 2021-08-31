package com.itbl.ekmuthosodai;

public class OrderProduct {

    private String orderAmount,lotNo,condId,dlv_user_id,dlv_assgn,userId,orderId,shopId;

    public OrderProduct(String orderAmount, String lotNo, String condId, String dlv_user_id, String dlv_assgn,
                 String userId,String shopId) {
        super();

        this.orderAmount = orderAmount;
        this.lotNo = lotNo;
        this.condId = condId;
        this.dlv_user_id = dlv_user_id;
        this.dlv_assgn = dlv_assgn;
        this.userId = userId;
        this.shopId = shopId;
    }
    public OrderProduct(String orderId,String shopId){
        super();
        this.orderId=orderId;
        this.shopId = shopId;
    }


    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getLotNo() {
        return lotNo;
    }

    public void setLotNo(String lotNo) {
        this.lotNo = lotNo;
    }

    public String getCondId() {
        return condId;
    }

    public void setCondId(String condId) {
        this.condId = condId;
    }

    public String getDlv_user_id() {
        return dlv_user_id;
    }

    public void setDlv_user_id(String dlv_user_id) {
        this.dlv_user_id = dlv_user_id;
    }

    public String getDlv_assgn() {
        return dlv_assgn;
    }

    public void setDlv_assgn(String dlv_assgn) {
        this.dlv_assgn = dlv_assgn;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
