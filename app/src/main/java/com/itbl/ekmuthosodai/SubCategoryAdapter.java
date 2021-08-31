package com.itbl.ekmuthosodai;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;

public class SubCategoryAdapter extends BaseAdapter {

    private ArrayList<Category> list;

    Context context;

    private LayoutInflater inflator;
    private ArrayList<Category> newlist = null;
    private ItemFilter mFilter = new ItemFilter();
    public SubCategoryAdapter(Context applicationContext, ArrayList<Category> newlist) {
        this.context=applicationContext;
        this.newlist = newlist;
        this.list = new ArrayList<Category>();
        this.list.addAll(newlist);


    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Category getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

   CategoryNameHolder categoryNameHolder;

        if(convertView == null){
            inflator = (LayoutInflater.from(context));


            convertView= inflator.inflate(R.layout.form_sub_category,null);
            categoryNameHolder = new CategoryNameHolder();

            categoryNameHolder.tx_category_name = (TextView)convertView.findViewById(R.id.cat_name);

            convertView.setTag(categoryNameHolder);



        }

        else{
            categoryNameHolder = (CategoryNameHolder)convertView.getTag();

        }





        categoryNameHolder.tx_category_name.setText(String.valueOf(list.get(position).getCat_name()));


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

            final ArrayList<Category> orglist = newlist;

            int count = orglist.size();
            final ArrayList<Category> nlist = new ArrayList<Category>(count);

            String filterableString;

            for (int i = 0; i < count; i++) {
                filterableString = orglist.get(i).getCat_name();



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
            list = (ArrayList<Category>) results.values;
            notifyDataSetChanged();
        }

    }

    private class CategoryNameHolder{

        TextView tx_category_name;



    }
}
