package com.appdevelopersumankr.api_demo_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    adapter recycleradapter;
    List<wallpaper_model> wallpaper_modelList;
    int pagenumber=1;
    Boolean isscrolling =false;
    int totalitem,currentitem,scrollitem;
    String url="https://api.pexels.com/v1/curated/?page="+pagenumber+"&per_page=80";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );

        recyclerView=findViewById ( R.id.recyclerviewid);
        wallpaper_modelList=new ArrayList<> ();
        recycleradapter=new adapter ( this,wallpaper_modelList );

        recyclerView.setAdapter ( recycleradapter );
        GridLayoutManager gridLayoutManager=new GridLayoutManager ( this,2 );
        recyclerView.setLayoutManager ( gridLayoutManager );
        recyclerView.addOnScrollListener ( new RecyclerView.OnScrollListener () {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged ( recyclerView, newState );
                if (newState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                    isscrolling=true;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled ( recyclerView, dx, dy );
                currentitem=gridLayoutManager.getChildCount ();
                totalitem=gridLayoutManager.getItemCount ();
                scrollitem=gridLayoutManager.findFirstVisibleItemPosition ();
                if (isscrolling && (currentitem+scrollitem==totalitem)){
                    isscrolling=false;
                    fetchwallpaper ();
                }
            }
        } );




        fetchwallpaper ();

    }

    public  void fetchwallpaper(){
        StringRequest stringRequest=new StringRequest ( Request.Method.GET, url,
                new Response.Listener<String> () {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject=new JSONObject (response);
                            JSONArray jsonArray=jsonObject.getJSONArray ( "photos" );
                            int length=jsonArray.length ();
                            for (int i=0;i<length;i++){
                                JSONObject object=jsonArray.getJSONObject ( i );
                                int id=object.getInt ( "id" );
                                JSONObject objectimage=object.getJSONObject ( "src" );

                                String originalurl=objectimage.getString ( "original");
                                String mediumurl=objectimage.getString ( "medium");

                                wallpaper_model wall=new wallpaper_model (id,originalurl,mediumurl);
                                wallpaper_modelList.add ( wall);

                            }
                            recycleradapter.notifyDataSetChanged ();
                            pagenumber++;

                        }catch (JSONException e){

                        }

                    }
                }, new Response.ErrorListener () {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        } ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> prams=new HashMap<> ();
                prams.put ( "Authorization","563492ad6f917000010000013a6ef786d52c4c99b78735e375d735e4");
                return prams;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue ( getApplicationContext () );
        requestQueue.add ( stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater ().inflate ( R.menu.main,menu );
        return super.onCreateOptionsMenu ( menu );
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId ()){
            case R.id.searchid:
                Toast.makeText ( this, "hello", Toast.LENGTH_SHORT ).show ();

                AlertDialog.Builder alert=new AlertDialog.Builder ( this);
                final EditText editText=new EditText ( this);
                editText.setTextAlignment ( View.TEXT_ALIGNMENT_CENTER );
                alert.setMessage ( "Enter Category");
                alert.setTitle ( "wallpaper");
                alert.setView ( editText);
                alert.setPositiveButton ( "yes", new DialogInterface.OnClickListener () {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String query=editText.getText ().toString ().toLowerCase ();
                        url=  "https://api.pexels.com/v1/search?per_page="+pagenumber+"&query="+query;
                        wallpaper_modelList.clear ();
                        fetchwallpaper ();

                    }
                } );
                alert.setNegativeButton ( "No", new DialogInterface.OnClickListener () {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText ( MainActivity.this, "Wrong Option Selected", Toast.LENGTH_SHORT ).show ();

                    }
                } );
                alert.show ();

                break;
            case R.id.aboutusid:
                Toast.makeText ( this, "About us", Toast.LENGTH_SHORT ).show ();
                Intent intent=new Intent (MainActivity.this,Aboutus.class);
                startActivity ( intent );

                break;
            case R.id.exitid:
                Toast.makeText ( this, "Exit", Toast.LENGTH_SHORT ).show ();
                AlertDialog.Builder alert2=new AlertDialog.Builder ( this);

               // alert2.setMessage ( "Enter Category");
                alert2.setTitle ( "Are you Sure Want to Exit");

                alert2.setPositiveButton ( "yes", new DialogInterface.OnClickListener () {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        System.exit(0);


                    }
                } );
                alert2.setNegativeButton ( "No", new DialogInterface.OnClickListener () {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText ( MainActivity.this, "Welcome Again", Toast.LENGTH_SHORT ).show ();
                    }
                } );
                alert2.show ();
                break;

        }
        return super.onOptionsItemSelected ( item );
    }
}