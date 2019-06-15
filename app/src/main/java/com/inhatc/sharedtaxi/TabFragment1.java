package com.inhatc.sharedtaxi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;


public class TabFragment1 extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private FloatingActionButton fab_main, fab_sub1, fab_sub2,fab_sub3;
    private Animation fab_open, fab_close;
    private boolean isFabOpen = false;
    public static TabFragment1 mContext;
    public int num;
    public String user_name;
    public SwipeRefreshLayout mSwipeRefreshLayout;
    DatabaseReference db;
    private ListView mListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tab_fragment_1, container, false);
        // Inflate the layout for this fragment
        mContext =this;
        user_name = ((MainActivity)getActivity()).user_name();
        db = FirebaseDatabase.getInstance().getReference();
        fab_open = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_close);
        fab_main = (FloatingActionButton) v.findViewById(R.id.fab_main);
        fab_sub1 = (FloatingActionButton) v.findViewById(R.id.fab_sub1);
        fab_sub2 = (FloatingActionButton) v.findViewById(R.id.fab_sub2);
        fab_sub3 = (FloatingActionButton) v.findViewById(R.id.fab_sub3);
        mListView =(ListView) v.findViewById(R.id.lstv);



        final Intent roomIntent= new Intent(getActivity(),roomActivity.class);
        datasetting();
        // 방목록   방을 클릭하였을때
        mListView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final listview_item roomInfo = (listview_item) parent.getItemAtPosition( position );
                db.child(roomInfo.getWhere()).child("roomList").child(roomInfo.getRoom_num()).child("member").addListenerForSingleValueEvent( new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Iterator<DataSnapshot> child =dataSnapshot.getChildren().iterator();
                        int cnt =0;
                        while(child.hasNext()){
                            child.next();
                            cnt++;
                        }
                        if(cnt ==4){
                            android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(getActivity());
                            dialog.setTitle("알림!");
                            dialog.setMessage("인원이 꽉찼습니다.");
                            dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            });
                            dialog.show();
                        }else{
                            roomIntent.putExtra("roomId",roomInfo.getRoom_num());
                            roomIntent.putExtra("where",roomInfo.getWhere());
                            roomIntent.putExtra("userName",user_name);
                            startActivity(roomIntent);
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

            }
        } );
        mSwipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_layout);

        mSwipeRefreshLayout.setOnRefreshListener(this );

        fab_main.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFab();
            }
        } );
        fab_sub1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFab();
                CustomDialog dialog = new CustomDialog(getActivity());
                dialog.setTitle("탑승지");
                dialog.getWindow().setFlags( WindowManager.LayoutParams.FLAG_BLUR_BEHIND,WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                dialog.setContentView(R.layout.activity_itc_map);
                final RadioGroup rg = (RadioGroup)dialog.findViewById(R.id.radioGroup);
                final RadioButton rbt1 = (RadioButton)dialog.findViewById(R.id.radioButton1);
                final RadioButton rbt2 = (RadioButton)dialog.findViewById(R.id.radioButton2);
                MapView mMapView = (MapView)dialog.findViewById(R.id.map);
                MapsInitializer.initialize(getActivity());
                mMapView.onCreate(dialog.onSaveInstanceState());
                mMapView.onResume();
                mMapView.getMapAsync( new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(final GoogleMap googleMap) {
                        double latitude = 37.464660;
                        double longitude = 126.680085;
                        LatLng objLocation;
                        objLocation = new LatLng(latitude,longitude);
                        // Add a marker in Sydney and move the camera
                        googleMap.addMarker(new MarkerOptions().position(objLocation).title("주안역 탑승지"));
                        googleMap.moveCamera( CameraUpdateFactory.newLatLng(objLocation));
                        googleMap.moveCamera(CameraUpdateFactory.zoomTo(16));
                        rg.setOnCheckedChangeListener( new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                if(checkedId == R.id.radioButton1){
                                    double latitude = 37.464660;
                                    double longitude = 126.680085;
                                    LatLng objLocation;
                                    objLocation = new LatLng(latitude,longitude);
                                    // Add a marker in Sydney and move the camera
                                    googleMap.addMarker(new MarkerOptions().position(objLocation).title("주안역 탑승지"));
                                    googleMap.moveCamera( CameraUpdateFactory.newLatLng(objLocation));
                                    googleMap.moveCamera(CameraUpdateFactory.zoomTo(16));
                                }else{
                                    double latitude = 37.450750;
                                    double longitude = 126.657846;
                                    LatLng objLocation;
                                    objLocation = new LatLng(latitude,longitude);
                                    // Add a marker in Sydney and move the camera
                                    googleMap.addMarker(new MarkerOptions().position(objLocation).title("인하공전 탑승지"));
                                    googleMap.moveCamera( CameraUpdateFactory.newLatLng(objLocation));
                                    googleMap.moveCamera(CameraUpdateFactory.zoomTo(16));
                                }
                            }
                        } );
                    }
                } );
                dialog.show();
            }
        } );
        fab_sub2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFab();
            }
        } );
        fab_sub3.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFab();
                AlertDialog.Builder adialog = new AlertDialog.Builder( getActivity() );
                final String[] items = new String[]{"인하공전 → 주안역","주안역 → 인하공전"};
                final int[] selectedItem = {0};
                adialog.setTitle( "방만들기" )
                        .setSingleChoiceItems( items, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selectedItem[0] = which;
                            }
                        } )
                        .setPositiveButton( "만들기", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db.child("roomId").addListenerForSingleValueEvent( new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        long count = (long) dataSnapshot.getValue();
                                        db.child("roomId").setValue(++count);
                                        db.child(items[selectedItem[0]]).child("roomList").child(Long.toString(count)).child("owner").setValue(user_name);
                                        roomIntent.putExtra("roomId",Long.toString(count));
                                        roomIntent.putExtra("where",items[selectedItem[0]]);
                                        roomIntent.putExtra("userName",user_name);
                                        startActivity(roomIntent);
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        throw databaseError.toException();
                                    }
                                } );
                            }



                        } ).setNegativeButton( "취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText( getActivity(), Integer.toString( num ), Toast.LENGTH_LONG ).show();
                    }
                } );
                adialog.create();
                adialog.show();
            }
        } );

        return v;
    }
    @Override
    public void onRefresh() {
        // 새로고침 코드
        datasetting();
        // 새로고침 완료
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private void datasetting(){
        db.child("인하공전 → 주안역").child("roomList").addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> child =dataSnapshot.getChildren().iterator();
                final ListViewAdapter mMyAdapter = new ListViewAdapter();
                while(child.hasNext()){
                    String room_num=child.next().getKey();
                    mMyAdapter.roomList(room_num, "인하공전 → 주안역");
                }
                mListView.setAdapter(mMyAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    private void toggleFab() {

        if (isFabOpen) {
            fab_sub1.startAnimation(fab_close);
            fab_sub2.startAnimation(fab_close);
            fab_sub3.startAnimation(fab_close);
            fab_sub1.setClickable(false);
            fab_sub2.setClickable(false);
            fab_sub3.setClickable(false);

            isFabOpen = false;
        } else {
            fab_sub1.startAnimation(fab_open);
            fab_sub2.startAnimation(fab_open);
            fab_sub3.startAnimation(fab_open);
            fab_sub1.setClickable(true);
            fab_sub2.setClickable(true);
            fab_sub3.setClickable(true);

            isFabOpen = true;

        }
    }

}