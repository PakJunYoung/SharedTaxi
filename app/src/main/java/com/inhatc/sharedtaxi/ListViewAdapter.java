package com.inhatc.sharedtaxi;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;
import java.util.Iterator;

public class ListViewAdapter extends BaseAdapter{

    public ListViewAdapter mContext;
    /* 아이템을 세트로 담기 위한 어레이 */
    private ArrayList<listview_item> mItems = new ArrayList<>();

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public listview_item getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        DatabaseReference db;
        db=FirebaseDatabase.getInstance().getReference();
        final Context context = parent.getContext();
        mContext=this;

        /* 'listview_custom' Layout을 inflate하여 convertView 참조 획득 */
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_listview_item, parent, false);
        }
        final TextView NumName =(TextView) convertView.findViewById(R.id.tv_NumName);
        final TextView people = (TextView) convertView.findViewById(R.id.tv_people);
        /* 'listview_custom'에 정의된 위젯에 대한 참조 획득 */


        /* 각 리스트에 뿌려줄 아이템을 받아오는데 mMyItem 재활용 */
        final listview_item myItem = getItem(position);
        db.child(myItem.getWhere()).child("roomList").child(myItem.getRoom_num()).child("owner").addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                NumName.setText("("+myItem.getRoom_num()+") "+ dataSnapshot.getValue().toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        } );
        db.child(myItem.getWhere()).child("roomList").child(myItem.getRoom_num()).child("member").addListenerForSingleValueEvent( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> child =dataSnapshot.getChildren().iterator();
                int cnt =0;
                while(child.hasNext()){
                    child.next();
                    cnt++;
                }
                people.setText("인원 수 : " + cnt + "/4");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        
        /* 각 위젯에 세팅된 아이템을 뿌려준다 */

        /* (위젯에 대한 이벤트리스너를 지정하고 싶다면 여기에 작성하면된다..)  */


        return convertView;
    }

    /* 아이템 데이터 추가를 위한 함수. 자신이 원하는대로 작성 */
    public void roomList(String num,String where){

        listview_item mItem = new listview_item();

        /* MyItem에 아이템을 setting한다. */
        mItem.setRoom_num(num);
        mItem.setWhere(where);
        /* mItems에 MyItem을 추가한다. */
        mItems.add(mItem);

    }
}

