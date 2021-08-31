package com.itbl.ekmuthosodai;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class CategoryView  extends AppCompatActivity {

    CategoryAdapter categoryAdapter;
    ListView listView;

    ArrayList<Category> categoryLists=new ArrayList<>();

    EditText inputSearch;
    String catName,userId= "";
    String catId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_category);
        listView = findViewById(R.id.table_catlist);
        inputSearch = findViewById(R.id.search_tname);
        categoryAdapter=new CategoryAdapter(this,categoryLists);
        SharedPreferences Preferences1 = getSharedPreferences("pref", MODE_PRIVATE);
        userId = Preferences1.getString("USER_ID","");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);





        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {

                String text = inputSearch.getText().toString().toLowerCase(Locale.getDefault());
                categoryAdapter.getFilter().filter(text);

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {


            }

            @Override

            public void afterTextChanged(Editable arg0) {

            }
        });

        Display task = new Display(CategoryView.this);
        task.execute();


    }

    public class Display extends AsyncTask<Void, Void, String> {
        @SuppressWarnings("unused")
        private Activity context;

        @SuppressWarnings("unused")
        ProgressDialog pd = null;

        public Display(Activity context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(CategoryView.this, " Processing",
                    "Please wait...");
        }

        @Override
        protected String doInBackground(Void... params) {
            String result = "";

            try {

                int count = 0;


                try {
                    String response = CustomHttpClientGet.execute("");
                    result = response.toString();


                } catch (Exception e) {
                    Log.e("log_tag", "Error in http connection!!" + e.toString());
                }

                JSONArray jsonArray = new JSONArray(result.toString());

                while (count < jsonArray.length()) {

                    JSONObject JO = jsonArray.getJSONObject(count);


                    catName = JO.getString("cat_NAME");
                    catId = JO.getString("cat_ID");


                    Category categoryList = new Category(catId, catName);
                    categoryLists.add(categoryList);
                    count++;
                }


            } catch (Exception e) {
                Log.e("log_tag", "Error in http connection!!" + e.toString());
            }

            return result;
        }


        @Override
        protected void onPostExecute(String result) {
            pd.dismiss();

            categoryAdapter = new CategoryAdapter(CategoryView.this, categoryLists);
            listView.setAdapter(categoryAdapter);


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(CategoryView.this, SubCategoryView.class);
                    intent.putExtra("catId", categoryLists.get(position).getCat_Id());
                    startActivity(intent);


                }
            });


        }}

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            if (userId != "") {
                inflater.inflate(R.menu.menu_cat_after_login, menu);
            } else {

                inflater.inflate(R.menu.menu_for_category, menu);
            }

            return true;

        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();

            if (id == R.id.cart_btnnn) {
                Intent intent = new Intent(CategoryView.this, CartActivity.class);
                startActivity(intent);
            }
            if (id == R.id.cart_btn22) {
                Intent intent = new Intent(CategoryView.this, CartActivity.class);
                startActivity(intent);
            }
            if (id == R.id.login_btnnn1) {
                Intent intent = new Intent(CategoryView.this, Login.class);
                startActivity(intent);
            }
            if (id == R.id.logout_btttn22) {
                SharedPreferences preferences = getSharedPreferences("pref", 0);
                preferences.edit().remove("USER_ID").commit();
                Intent intent = new Intent(CategoryView.this, Login.class);
                startActivity(intent);
                EkmuthoSodaiDbAdapter dbAdapter = new EkmuthoSodaiDbAdapter(this);
                dbAdapter.open();
                dbAdapter.removeAll();
                dbAdapter.close();


            }


            return super.onOptionsItemSelected(item);
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

    }




}
