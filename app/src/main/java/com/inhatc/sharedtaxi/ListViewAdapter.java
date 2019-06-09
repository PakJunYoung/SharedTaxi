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

        final Context context = parent.getContext();
        mContext=this;

        /* 'listview_custom' Layout을 inflate하여 convertView 참조 획득 */
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_listview_item, parent, false);
        }

        /* 'listview_custom'에 정의된 위젯에 대한 참조 획득 */


        /* 각 리스트에 뿌려줄 아이템을 받아오는데 mMyItem 재활용 */
        final listview_item myItem = getItem(position);

        
        /* 각 위젯에 세팅된 아이템을 뿌려준다 */

        /* (위젯에 대한 이벤트리스너를 지정하고 싶다면 여기에 작성하면된다..)  */


        return convertView;
    }

    /* 아이템 데이터 추가를 위한 함수. 자신이 원하는대로 작성 */
    public void addItem(String uri, String name,String id) {

        listview_item mItem = new listview_item();

        /* MyItem에 아이템을 setting한다. */
        mItem.setBirth(uri);
        mItem.setName(name);
        mItem.setid(id);

        /* mItems에 MyItem을 추가한다. */
        mItems.add(mItem);

    }
}

