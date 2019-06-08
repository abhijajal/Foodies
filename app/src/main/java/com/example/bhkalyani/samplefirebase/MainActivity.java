package com.example.bhkalyani.samplefirebase;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import static android.R.attr.data;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private CheckBox Margarita,ThinCrust,CheeseBurst;
    private TextView Total;
    private Button Bookbtn;
    private ImageView imageView;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private int price=0;
    String order="Order is";
    SliderLayout sliderLayout;
    private TabLayout tabLayout;
    private FirebaseStorage firebaseStorage;
    //This is our viewPager
    private ViewPager viewPager;
    String url = "hey bro";
    Uri ur = Uri.parse("");
    private TextView faltu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseStorage = FirebaseStorage.getInstance();
       // faltu = (TextView)findViewById(R.id.faltu);
        imageView = (ImageView)findViewById(R.id.ImageLoaded);
        sliderLayout= (SliderLayout) findViewById(R.id.slider);
        HashMap<String,String> image= new HashMap<String,String>();
    //    Picasso.with(getApplicationContext()).load(url).resize(250,200).into(imageView);
        /*image.put("img1",R.drawable.picture1);
        image.put("img2",R.drawable.picture2);
        image.put("img3",R.drawable.picture3);*/
        image.put("Pizza","https://firebasestorage.googleapis.com/v0/b/abcd-6191c.appspot.com/o/download.jpeg?alt=media&token=9a9e10ea-3760-43ce-bb35-04f098a81edd");
        image.put("Burger","https://firebasestorage.googleapis.com/v0/b/abcd-6191c.appspot.com/o/img1.jpeg?alt=media&token=23484f9f-f96a-494e-9398-4d2a823578c6");
        image.put("Subway Sandwich","https://firebasestorage.googleapis.com/v0/b/abcd-6191c.appspot.com/o/img2.jpeg?alt=media&token=4944fad4-8598-4c0e-aa0f-8ffc0cd91e54");
        image.put("Paneer Kababs","https://firebasestorage.googleapis.com/v0/b/abcd-6191c.appspot.com/o/img3.jpeg?alt=media&token=c70bba98-6d02-49be-9c2b-eca046d68b59");
        for(String name: image.keySet()){
            DefaultSliderView sliderView=new DefaultSliderView(this);
            sliderView.image(image.get(name));
            sliderLayout.addSlider(sliderView);
        }
        sliderLayout.setDuration(3000);
     //   url = url + ur.toString();

        Toast.makeText(getApplicationContext(),url,Toast.LENGTH_SHORT).show();
        Margarita = (CheckBox) findViewById(R.id.checkBox);
        CheeseBurst = (CheckBox) findViewById(R.id.checkBox2);
        ThinCrust = (CheckBox) findViewById(R.id.checkBox3);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        Total = (TextView) findViewById(R.id.textView3);

        Bookbtn = (Button) findViewById(R.id.button2);
        Bookbtn.setOnClickListener(this);

       // Picasso.with(getApplicationContext()).load(ur.toString()).resize(200,250).into(imageView);
        //addurl();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.mainmenu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId())
        {
            case R.id.Logout:
                LogoutFunction();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void LogoutFunction() {
        firebaseAuth.signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        //intent.putExtra("finish", true);
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK); // To clean up all activities

        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.button2:
                BookOrder();
        }

    }

    private void BookOrder()
    {
        if(Margarita.isChecked())
        {
            price = price + 100 ;
           // order.concat(" Margarita ");
            order=order+" Margarita ";
        }
        if(CheeseBurst.isChecked())
        {
            price = price + 150 ;
           // order.concat(" CheeseBurst ");
            order=order+" CheeseBurst ";
        }
        if(ThinCrust.isChecked())
        {
            price = price + 200 ;
           // order.concat(" ThinCrust ");
            order=order+" ThinCrust ";

        }
        String user_id = firebaseAuth.getCurrentUser().getUid();
        DatabaseReference current_user_db = databaseReference.child(user_id);
        DatabaseReference databaseReference7 = current_user_db.child("Total");
        //DatabaseReference databaseReference1 = current_user_db.child("Order");

        databaseReference7.setValue(price);
        databaseReference7 = current_user_db.child("Order");
        databaseReference7.setValue(order);


        //databaseReference1.setValue(order);
        Toast.makeText(getApplicationContext(), "Order Placed",
                Toast.LENGTH_LONG).show();
        Total.setText("Total is :"+price);


    }
}
