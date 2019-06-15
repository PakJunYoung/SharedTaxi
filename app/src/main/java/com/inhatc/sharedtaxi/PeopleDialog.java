package com.inhatc.sharedtaxi;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

public class PeopleDialog extends Dialog {
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    public String roomId;
    public String where;
    public TextView lblPerson1;
    public TextView lblPerson2;
    public TextView lblPerson3;
    public TextView lblPerson4;

    public PeopleDialog( Context context,String roomId, String where) {
        super( context );
        this.roomId = roomId;
        this.where = where;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);
        setContentView(R.layout.activity_people_dialog);

        lblPerson1 = (TextView)findViewById(R.id.lblPerson1);
        lblPerson2 = (TextView)findViewById(R.id.lblPerson2);
        lblPerson3 = (TextView)findViewById(R.id.lblPerson3);
        lblPerson4 = (TextView)findViewById(R.id.lblPerson4);

        databaseReference.child(where).child("roomList").child(roomId).child("member").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> child =dataSnapshot.getChildren().iterator();
                int cnt = 0;
                while (child.hasNext()){
                    cnt++;
                    switch (cnt){
                        case 1:
                            lblPerson1.setText(child.next().getKey());
                            break;
                        case 2:
                            lblPerson2.setText(child.next().getKey());
                            break;
                        case 3:
                            lblPerson3.setText(child.next().getKey());
                            break;
                        case 4:
                            lblPerson4.setText(child.next().getKey());
                            break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
