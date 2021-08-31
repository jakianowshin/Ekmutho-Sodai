package com.itbl.ekmuthosodai;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemAdapter  extends BaseAdapter {

    private ArrayList<Items> list;

    Context context;

    private LayoutInflater inflator;
    private ArrayList<Items> newlist = null;
    private ItemFilter mFilter = new ItemFilter();
    public ItemAdapter(Context applicationContext, ArrayList<Items> newlist) {
        this.context=applicationContext;
        this.newlist = newlist;
        this.list = new ArrayList<Items>();
        this.list.addAll(newlist);


    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Items getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

      ItemNameHolder itemNameHolder;

        if(convertView == null){
            inflator = (LayoutInflater.from(context));


            convertView= inflator.inflate(R.layout.form_item,null);
            itemNameHolder = new ItemNameHolder();

            itemNameHolder.tx_item_name = (TextView)convertView.findViewById(R.id.item_name);

            convertView.setTag(itemNameHolder);



        }

        else{
            itemNameHolder = (ItemNameHolder) convertView.getTag();

        }





        itemNameHolder.tx_item_name.setText(list.get(position).getItemdes());


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

            final ArrayList<Items> orglist = newlist;

            int count = orglist.size();
            final ArrayList<Items> nlist = new ArrayList<Items>(count);

            String filterableString;

            for (int i = 0; i < count; i++) {
                filterableString = orglist.get(i).getItemdes();



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
            list = (ArrayList<Items>) results.values;
            notifyDataSetChanged();
        }

    }

    private class ItemNameHolder{

        TextView tx_item_name;



    }
}
