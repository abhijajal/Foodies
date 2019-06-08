package com.example.bhkalyani.samplefirebase;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.Map;

public class ProductInfo extends AppCompatActivity {


    TextView itemPrice,itemName;
    ImageView itemImage;

    private Firebase mRef;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_info);
        String ProductKey;
        ProductKey = getIntent().getExtras().getString("ProductID");

        itemPrice = (TextView) findViewById(R.id.ItemPrice);
        itemName = (TextView) findViewById(R.id.ItemName);
        itemImage = (ImageView) findViewById(R.id.ItemImage);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Menu").child(ProductKey);
        mRef = new Firebase("https://abcd-6191c.firebaseio.com/Menu/" + ProductKey);
        databaseReference.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                //Map<String,String> map = dataSnapshot.getValue(Map.class);
                Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                String name = (String) map.get("Name");
                //Long price = Long.valueOf((String) map.get("Price"));
                String price = Long.toString((Long) map.get("Price"));
                String Image = (String) map.get("Image");

                itemName.setText(name);
                itemPrice.setText(price);
                Picasso.with(getApplicationContext()).load(Image).into(itemImage);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
      /*  mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String,String> map = dataSnapshot.getValue(Map.class);
                String name = map.get("Name");
                Long price = Long.valueOf(map.get("Price"));
                String Image = map.get("Image");

                Log.v("Items","Name:- " + name );
                Log.v("Items","Price:- "+ price);
                //itemName.setText(name);
                //itemPrice.setText(price.toString());

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });*/

        //Toast.makeText(getApplicationContext(),ProductKey, Toast.LENGTH_SHORT).show();


    }
}
