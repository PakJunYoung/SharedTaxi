package com.inhatc.sharedtaxi;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private Context mContext;
    private ViewPager viewPager;
    private String user_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();
        Intent intent=getIntent();
        user_name = intent.getStringExtra("user");
        Toast.makeText( this, user_name, Toast.LENGTH_SHORT ).show();

        //TabLayout
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("인하공전 → 주안역"));
        tabs.addTab(tabs.newTab().setText("주안역 → 인하공전"));
        tabs.setTabGravity(tabs.GRAVITY_FILL);

        //어답터설정
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        PagerAdapter myPagerAdapter = new com.inhatc.sharedtaxi.PagerAdapter( getSupportFragmentManager(), tabs.getTabCount() );
        viewPager.setAdapter(myPagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        //탭메뉴를 클릭하면 해당 프래그먼트로 변경-싱크화
        tabs.addOnTabSelectedListener( new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem( tab.getPosition() );
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        } );


    }
    public String user_name(){
        return user_name;
    }
}