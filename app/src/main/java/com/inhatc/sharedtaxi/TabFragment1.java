package com.inhatc.sharedtaxi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;


public class TabFragment1 extends Fragment implements View.OnClickListener{

    private FloatingActionButton fab_main, fab_sub1, fab_sub2,fab_sub3;
    private Animation fab_open, fab_close;
    private boolean isFabOpen = false;
    public static TabFragment1 mContext;
    public int num;
    public String user_name;
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

        datasetting();
        // 방목록   방을 클릭하였을때
        mListView.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        } );

        fab_main.setOnClickListener(this);
        fab_sub1.setOnClickListener(this);
        fab_sub2.setOnClickListener(this);
        fab_sub3.setOnClickListener(this);

        return v;
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
    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.fab_main:
                toggleFab();
                break;

            case R.id.fab_sub1:
                toggleFab();
                Toast.makeText( getActivity(), "Camera Open-!", Toast.LENGTH_SHORT ).show();
                break;

            case R.id.fab_sub2:
                toggleFab();
                Toast.makeText( getActivity(), "Map Open-!", Toast.LENGTH_SHORT ).show();
                break;

            case R.id.fab_sub3:
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
                Toast.makeText( getActivity(), "Map Open-!", Toast.LENGTH_SHORT ).show();
                break;
        }
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