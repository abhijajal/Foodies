package com.example.bhkalyani.samplefirebase;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class tab1 extends Fragment
{
    private View view;
    private RecyclerView menuList;
    private DatabaseReference mDatabaseReference;

    public tab1()
    {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.tab1, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Menu");
        mDatabaseReference.keepSynced(true);
        menuList = (RecyclerView) view.findViewById(R.id.ItemList);
        menuList.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));
        menuList.setHasFixedSize(true);
        menuList.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerAdapter<Menu,MenuViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Menu, MenuViewHolder>(
                Menu.class,
                R.layout.menu_row,
                MenuViewHolder.class,
                mDatabaseReference
        ) {
            @Override
            protected void populateViewHolder(final MenuViewHolder viewHolder, Menu model, int position) {


                final String menu_key = getRef(position).getKey();
                viewHolder.setName(model.getName());
                viewHolder.setPrice(model.getPrice());
                viewHolder.setImage(getContext(),model.getImage());

                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(getApplicationContext(),menu_key,Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(),ProductInfo.class);
                        intent.putExtra("ProductID",menu_key);
                        startActivity(intent);

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

        public void setImage(Context context, String image)
        {
            ImageView itemImage = (ImageView) mView.findViewById(R.id.itemPhoto);
            Picasso.with(context).load(image).into(itemImage);
        }
    }
}
