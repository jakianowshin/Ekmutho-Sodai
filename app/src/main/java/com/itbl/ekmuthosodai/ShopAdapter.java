package com.itbl.ekmuthosodai;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;

public class ShopAdapter extends BaseAdapter {

    private ArrayList<ShopList> list;

    Context context;

    private LayoutInflater inflator;
    private ArrayList<ShopList> newlist = null;
    private ItemFilter mFilter = new ItemFilter();
    public ShopAdapter(Context applicationContext, ArrayList<ShopList> newlist) {
        this.context=applicationContext;
        this.newlist = newlist;
        this.list = new ArrayList<ShopList>();
        this.list.addAll(newlist);


    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public ShopList getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

     ShopNameHolder shopNameHolder;

        if(convertView == null){
            inflator = (LayoutInflater.from(context));


            convertView= inflator.inflate(R.layout.form_shop,null);
            shopNameHolder = new ShopNameHolder();


            shopNameHolder.tx_shop_name = (TextView)convertView.findViewById(R.id.shop_name);

            convertView.setTag(shopNameHolder);



        }

        else{
            shopNameHolder = (ShopNameHolder) convertView.getTag();

        }






        shopNameHolder.tx_shop_name.setText(list.get(position).getDB_POINT_DESCR());


        return convertView;

    }


    public Filter getFilter() {
        return mFilter;
    }



    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final ArrayList<ShopList> orglist = newlist;

            int count = orglist.size();
            final ArrayList<ShopList> nlist = new ArrayList<ShopList>(count);

            String filterableString;

            for (int i = 0; i < count; i++) {
                filterableString = orglist.get(i).getDB_POINT_DESCR();



                if (filterableString.toLowerCase().contains(filterString)) {


                    nlist.add(orglist.get(i));
                }
            }


            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list = (ArrayList<ShopList>) results.values;
            notifyDataSetChanged();
        }

    }

    private class ShopNameHolder{


        TextView tx_shop_name;



    }
}
