package com.yctu.bbs;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.yctu.bbs.fragment.Mainpage;
import com.yctu.bbs.fragment.Message;
import com.yctu.bbs.fragment.Myhome;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationBar bottomNavigationBar;
    private Mainpage mainpage;
    private Myhome myhome;
    private Message message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.Tab);

        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);

        bottomNavigationBar .addItem(new BottomNavigationItem(R.mipmap.lyb,"主页").setActiveColor("#03a9f4"))
                .addItem(new BottomNavigationItem(R.mipmap.cggs,"关注").setActiveColor("#03a9f4"))
                .addItem(new BottomNavigationItem(R.mipmap.gd,"我").setActiveColor("#03a9f4"))
                .initialise();

        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener(){
            @Override public void onTabSelected(int position) { setfragment(position);}
            @Override public void onTabUnselected(int position) { }
            @Override public void onTabReselected(int position) { }
        });

        setDefaultFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.ab_search){
            resetLoginInfo();
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setDefaultFragment()      //设置默认frag
    {
        FragmentManager fm = getFragmentManager();
        android.app.FragmentTransaction transaction = fm.beginTransaction();
        mainpage = new Mainpage();
        transaction.replace(R.id.fragments, mainpage);
        transaction.commit();
    }

    private void setfragment(int id){     //切换fragment
        FragmentManager fm = getFragmentManager();  // 开启Fragment事务
        android.app.FragmentTransaction transaction = fm.beginTransaction();
        hideFragments(transaction);
        switch (id) {
            case 0: {
                if (mainpage == null) {
                    mainpage = new Mainpage();
                    transaction.add(R.id.fragments, mainpage);
                }else{
                    transaction.show(mainpage);
                }
                break;
            }
            case 1: {
                if (message == null) {
                    message = new Message();
                    transaction.add(R.id.fragments, message);
                }else {
                    transaction.show(message);
                }
                break;
            }
            case 2: {
                if (myhome == null) {
                    myhome = new Myhome();
                    transaction.add(R.id.fragments, myhome);
                }else {
                    transaction.show(myhome);
                }
                break;
            }
        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (mainpage != null) {
            transaction.hide(mainpage);
        }
        if (myhome != null) {
            transaction.hide(myhome);
        }
        if (message != null) {
            transaction.hide(message);
        }
    }

    public void resetLoginInfo(){
        SharedPreferences preferences = getSharedPreferences("qibbs",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("account","1");
        editor.commit();
    }
}
