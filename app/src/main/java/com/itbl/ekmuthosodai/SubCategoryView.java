package com.itbl.ekmuthosodai;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class SubCategoryView   extends AppCompatActivity {

    SubCategoryAdapter subCategoryAdapter;
    ListView listView;

    ArrayList<Category> subcategoryLists=new ArrayList<>();
    ArrayList<Items> parentIds=new ArrayList<>();


    EditText inputSearch;
    String categoriesID,subcatName= "";
    String subcatId="";
    Bundle bundle=null;
    String parentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_sub_category);
        listView = findViewById(R.id.table_catlist);
        inputSearch = findViewById(R.id.search_tname);
        subCategoryAdapter=new SubCategoryAdapter(this,subcategoryLists);



        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {

                String text = inputSearch.getText().toString().toLowerCase(Locale.getDefault());
                subCategoryAdapter.getFilter().filter(text);

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {


            }

            @Override

            public void afterTextChanged(Editable arg0) {

            }
        });

        bundle = getIntent().getExtras();
        if(bundle!=null){
            categoriesID = bundle.getString("catId");
            Display task = new Display(SubCategoryView.this);
            task.execute();
        }








    }

    public class Display extends AsyncTask<Void, Void, String> {
        @SuppressWarnings("unused")
        private Activity context;

        @SuppressWarnings("unused")
        ProgressDialog pd=null;

        public Display(Activity context){
            this.context = context;
        }

        @Override
        protected void onPreExecute(){
            pd = ProgressDialog.show(SubCategoryView.this, " Processing",
                    "Please wait...");
        }

        @Override
        protected String doInBackground(Void... params){
            String result = "";

            try{

                int count = 0;



                try {
                    String response = CustomHttpClientGet.execute("http://192.168.1.118:8003/subCategory/"+categoriesID);
                    result = response.toString();


                }
                catch (Exception e) {
                    Log.e("log_tag", "Error in http connection!!" + e.toString());
                }

                JSONArray jsonArray = new JSONArray(result.toString());

                while (count < jsonArray.length()){

                    JSONObject JO = jsonArray.getJSONObject(count);


                    subcatName = JO.getString("subCatName");
                    subcatId = JO.getString("subCatId");


                    Category subcategoryList = new Category(subcatId,subcatName);
                    subcategoryLists.add(subcategoryList);
                    count++;
                }


            }

            catch (Exception e) {
                Log.e("log_tag", "Error in http connection!!" + e.toString());
            }

            return result;
        }




        @Override
        protected void onPostExecute(String result) {
            pd.dismiss();

            subCategoryAdapter =new SubCategoryAdapter(SubCategoryView.this,subcategoryLists);
            listView.setAdapter(subCategoryAdapter);




            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                        subcatId=subcategoryLists.get(position).getCat_Id();


                    ParentId task = new ParentId(SubCategoryView.this);
                    task.execute();
                    Intent intent = new Intent(SubCategoryView.this, ItemListView.class);
                    startActivity(intent);


                }
            });

        }}

        public class ParentId extends AsyncTask<Void, Void, String> {
            @SuppressWarnings("unused")
            private Activity context;

            @SuppressWarnings("unused")
            ProgressDialog pd=null;

            public ParentId(Activity context){
                this.context = context;
            }

            @Override
            protected void onPreExecute(){
                pd = ProgressDialog.show(SubCategoryView.this, " Processing",
                        "Please wait...");
            }

            @Override
            protected String doInBackground(Void... params){
                String result = "";

                try{

                    int count = 0;



                    try {
                        String response = CustomHttpClientGet.execute("http://192.168.1.118:8003/parentId/"+subcatId);
                        result = response.toString();


                    }
                    catch (Exception e) {
                        Log.e("log_tag", "Error in http connection!!" + e.toString());
                    }
                    parentId=" ";

                    JSONArray jsonArray = new JSONArray(result.toString());


                    while (count < jsonArray.length()){

                        JSONObject JO = jsonArray.getJSONObject(count);




                        parentId = JO.getString("item_ID");


                        Items getId = new Items(parentId);
                        parentIds.add(getId);
                        count++;
                    }


                }

                catch (Exception e) {
                    Log.e("log_tag", "Error in http connection!!" + e.toString());
                }

                return result;
            }




            @Override
            protected void onPostExecute(String result) {
                pd.dismiss();

                SharedPreferences sharedPreferences = getSharedPreferences("pref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("parentId",parentId);
                editor.apply();











    }






}
    @Override
    protected void onStart() {
        super.onStart();

    }
    @Override
    protected void onResume() {
        super.onResume();

    }
    @Override
    protected void onPause() {
        super.onPause();

    }
    @Override
    protected void onStop() {
        super.onStop();

    }
    @Override
    protected void onRestart() {
        super.onRestart();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }}
