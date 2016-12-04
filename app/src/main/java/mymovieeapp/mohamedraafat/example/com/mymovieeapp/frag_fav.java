package mymovieeapp.mohamedraafat.example.com.mymovieeapp;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;


public class frag_fav extends Fragment {


    db database;
    Cursor cr;
    ArrayList<movie_item> myArrlist=new ArrayList<movie_item>();
    boolean check;
    @Override
    public void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle SavedInstanceState){
        View v= inflater.inflate(R.layout.favfrag,container,false);
        final GridView mygrid=(GridView)v.findViewById(R.id.gridView);
        database =new db(getContext());
        cr=database.selcetfavourit(database);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            check= bundle.getBoolean("check");
        }
        if (cr.moveToFirst()){
            do {

                myArrlist.add(new movie_item(cr.getString(0), cr.getString(1), cr.getString(2), cr.getDouble(3), cr.getString(4),cr.getString(5),cr.getInt(6),cr.getString(7),cr.getString(8)));

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
    public void newInstance(boolean check) {
        this.check=check;
    }
}


