package com.itbl.ekmuthosodai;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.util.List;

public class OrderDetailAdapter extends ArrayAdapter<OrderDetails> {

    private List<OrderDetails> list;

    private LayoutInflater inflator;
    public OrderDetailAdapter(Activity context, List<OrderDetails> list) {
        super(context, R.layout.form_order_detail, list);
        this.list = list;
        inflator = context.getLayoutInflater();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        OrderDetailHoldder orderDetailHoldder;

        if(convertView == null){

            convertView= inflator.inflate(R.layout.form_order_detail,null);
            orderDetailHoldder = new OrderDetailHoldder();


            orderDetailHoldder.shopName = convertView.findViewById(R.id.shop_name);
            orderDetailHoldder.price = convertView.findViewById(R.id.pro_total);
            orderDetailHoldder.qty = convertView.findViewById(R.id.pro_qty);
            orderDetailHoldder.prodctName = convertView.findViewById(R.id.itemName);



            convertView.setTag(orderDetailHoldder);


            convertView.setTag(R.id.shop_name, orderDetailHoldder.shopName);
            convertView.setTag(R.id.pro_total, orderDetailHoldder.price);
            convertView.setTag(R.id.pro_qty, orderDetailHoldder.qty);
            convertView.setTag(R.id.itemName, orderDetailHoldder.prodctName);


        }

        else{
            orderDetailHoldder = (OrderDetailHoldder) convertView.getTag();
        }


        orderDetailHoldder.shopName.setText(list.get(position).getShopName());
        orderDetailHoldder.price.setText(list.get(position).getItemPrice());
        orderDetailHoldder.qty.setText(list.get(position).getQty());
        orderDetailHoldder.prodctName.setText(list.get(position).getItemName());





        return convertView;
    }


    static class OrderDetailHoldder{


        private EditText shopName;
        private EditText qty;
        private EditText price;
        private EditText prodctName;

    }


}
