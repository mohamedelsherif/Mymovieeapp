package mymovieeapp.mohamedraafat.example.com.mymovieeapp;

import android.app.Activity;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().
                beginTransaction().
                replace(R.id.frgpl, new fragment_pop(), "frag 1")
                .commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        if(id==R.id.popular) {
getSupportFragmentManager().
        beginTransaction().
        replace(R.id.frgpl, new fragment_pop(), "frag 1")
        .commit();
        }

        if(id==R.id.top){

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frgpl,new fragment_top(),"frag 2")
                    .commit();
        }
        if(id==R.id.fav){

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frgpl,new frag_fav(),"frag 3")
                    .commit();
        }
        return  super.onOptionsItemSelected(item);
    }

}

