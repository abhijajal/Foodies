package com.example.bhkalyani.samplefirebase;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class TimePassActivity extends AppCompatActivity {

    private RecyclerView menuList;

    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_pass);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Menu");

        menuList = (RecyclerView) findViewById(R.id.Menu_list);
        menuList.setHasFixedSize(true);
        menuList.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<Menu,MenuViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Menu, MenuViewHolder>(
                Menu.class,
                R.layout.menu_row,
                MenuViewHolder.class,
                mDatabaseReference
        ) {
            @Override
            protected void populateViewHolder(MenuViewHolder viewHolder, Menu model, int position) {


                final String menu_key = getRef(position).getKey();
                viewHolder.setName(model.getName());
                viewHolder.setPrice(model.getPrice());
                viewHolder.setImage(getApplicationContext(),model.getImage());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(getApplicationContext(),menu_key,Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),ProductInfo.class);
                        intent.putExtra("ProductID",menu_key);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        };

        menuList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class MenuViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public MenuViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setName(String name)
        {
            TextView itemName = (TextView) mView.findViewById(R.id.itemName);
            itemName.setText(name);
        }

        public void setPrice(Long price)
        {
            TextView itemPrice = (TextView) mView.findViewById(R.id.itemPrice);
            itemPrice.setText(price.toString());
        }

        public void setImage(Context context,String image)
        {
            ImageView itemImage = (ImageView) mView.findViewById(R.id.itemPhoto);
            Picasso.with(context).load(image).into(itemImage);
        }
    }


}
