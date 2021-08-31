package com.itbl.ekmuthosodai;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static com.itbl.ekmuthosodai.CartActivity.Total;
import static com.itbl.ekmuthosodai.CartActivity.TotalwithDel;
import static com.itbl.ekmuthosodai.CartActivity.cartPrice;
import static com.itbl.ekmuthosodai.CartActivity.grandTotal;
import static com.itbl.ekmuthosodai.SingleProduct.tv;

public class CartAdapter extends  RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private CartAdapter mCartAdapter;
    private ArrayList<CartList> items;





    @NonNull
    @Override
    public CartAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.form_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CartViewHolder holder, final int position) {
        holder.cartName.setText(items.get(position).getItemName());
        holder.cartQuantity.setText(items.get(position).getQuantity());
        holder.carttotalPrice.setText((items.get(position).getPrice()) + " Tk.");
        holder.cartshop.setText(items.get(position).getShop());



        holder.cartDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartList item = items.get(position);
                EkmuthoSodaiDbAdapter dbAdapter = new EkmuthoSodaiDbAdapter(v.getContext());
                dbAdapter.open();


                if (items!=null && items.size()!=0){
                    dbAdapter.deleteFromDB(items.get(position).getItemId());
                    items.remove(item);
                    notifyItemRemoved(position);
                    notifyItemChanged(position,items.size());
                }
                dbAdapter.close();




                    TotalwithDel=grandTotal(items);
                    cartPrice.setText(Double.toString(TotalwithDel)+ " Tk.");
                    Total = String.valueOf(TotalwithDel);




                SharedPreferences sharedPreferences = v.getContext().getSharedPreferences("pref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("cartPrice",Total );
                editor.apply();


                if (tv != null && items != null)
                    tv.setText(Integer.toString(items.size()));


            } });


    }



    @Override
    public int getItemCount() {
        return items.size();
    }


    public class CartViewHolder extends RecyclerView.ViewHolder {

        TextView cartName;
        TextView carttotalPrice;
        TextView cartshop;
        TextView cartQuantity;
        ImageButton cartDelete;

        public CartViewHolder(View itemView) {
            super(itemView);

            cartName = itemView.findViewById(R.id.cart_item_name);
            carttotalPrice = itemView.findViewById(R.id.cart_item_price);
            cartQuantity = itemView.findViewById(R.id.cart_item_quantity);
            cartDelete = itemView.findViewById(R.id.cart_book_delete);
            cartshop = itemView.findViewById(R.id.cart_shop_name);

        }
    }

    public CartAdapter(Context context,ArrayList<CartList> books){
        this.context = context;
        this.items =  books;

    }


}
