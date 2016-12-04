package mymovieeapp.mohamedraafat.example.com.mymovieeapp;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class fragment_top extends Fragment {
    ArrayList<movie_item> myArrlist=new ArrayList<movie_item>();
    Context mycont= getActivity();;
    RequestQueue rq;
    db database;
    Cursor cr;
    boolean check;
    String urll="http://api.themoviedb.org/3/movie/top_rated?api_key=839399034d750762c56fd8559bf77eff";
    @Override
    public void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle SavedInstanceState){
        View v= inflater.inflate(R.layout.topfrag,container,false);
        GridView mygrid=(GridView)v.findViewById(R.id.gridView2);
        database =new db(getContext());
        request_u();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            check= bundle.getBoolean("check");
        }
        cr=database.selcet_all_Rated(database);
        if (cr.moveToFirst()){
            do {
                if (cr.getString(0).isEmpty()){
                    Toast.makeText(getContext(), "No Data to view , please connect to wifi", Toast.LENGTH_LONG).show();
                }else {
                    myArrlist.add(new movie_item(cr.getString(0), cr.getString(1), cr.getString(2), cr.getDouble(3), cr.getString(4),cr.getString(5),cr.getInt(6),cr.getString(7),cr.getString(8)));
                }
            }while (cr.moveToNext());
        }
        mygrid.setAdapter(new firstadapter(myArrlist, getContext()));
        mygrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if (check == false) {
                    Intent intent = new Intent(getActivity(), activitydetails.class);
                    intent.putExtra("tit", myArrlist.get(i).title);
                    intent.putExtra("back", myArrlist.get(i).backdrop_path);
                    intent.putExtra("relase date", myArrlist.get(i).release_date);
                    intent.putExtra("vote", myArrlist.get(i).user_rating);
                    intent.putExtra("overview", myArrlist.get(i).overview);
                    intent.putExtra("id", myArrlist.get(i).id);
                    intent.putExtra("adult", myArrlist.get(i).adult);
                    intent.putExtra("poster", myArrlist.get(i).poster_image);
                    intent.putExtra("org title", myArrlist.get(i).original_title);
                    startActivity(intent);
                } else {

                }


            }
        });

        return  v;
    }
    void request_u(){
        rq= Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                urll, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean flag = false;
                            JSONArray jsonArrxx = response.getJSONArray("results");
                            for (int i = 0; i < jsonArrxx.length(); i++) {
                                JSONObject im = jsonArrxx.getJSONObject(i);
                                String original_titlex = im.getString("original_title");
                                String posterx = im.getString("poster_path");
                                String overviewx = im.getString("overview");
                                double vote_averagex = im.getDouble("vote_average");
                                String release_datex = im.getString("release_date");
                                String adultx=im.getString("adult");
                                int idx=im.getInt("id");
                                String titlex=im.getString("title");
                                String backdrop_pathx=im.getString("backdrop_path");
                                cr=database.selcet_all_Rated(database);
                                if (cr.moveToFirst()){
                                    do {
                                        if (!cr.getString(0).isEmpty()){
                                            if (cr.getString(0).equals(original_titlex)&& cr.getString(1).equals(posterx)&&cr.getString(2).equals(overviewx)&& cr.getDouble(3)==vote_averagex && cr.getString(4).equals(release_datex)&&cr.getString(5).equals(adultx)&&cr.getInt(6)==idx && cr.getString(7).equals(titlex) &&cr.getString(8).equals(backdrop_pathx) ){
                                                flag=true;
                                            }
                                        }
                                    }while (cr.moveToNext());
                                }
                                if (flag==false) {
                                    database.insert_Rated(original_titlex, posterx, overviewx, vote_averagex, release_datex,adultx,idx,backdrop_pathx,titlex);
                                }


                                //
                            }
                            //Toast.makeText(getApplicationContext(),"title" , Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError e) {
                e.printStackTrace();

            }
        });
        rq.add(jsonObjReq);
    }
    public void newInstance(boolean check) {
        this.check=check;
    }}


