package com.example.machinetest;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.helper.widget.Carousel;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.UpdateViewPager {
    ArrayList <UserList> arrayList;
    private ViewPager2 viewPager;
    ImageView swipeRight,swipeLeft;

    RecyclerView recyclerView;
    Boolean higlight=true;
    RecyclerViewAdapter adapter;
    NestedScrollView nestedScrollView;
    ProgressBar progressBar;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    float radius;
    ActionBarDrawerToggle actionBarDrawerToggle;
    Window window ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager=findViewById(R.id.viewPager);
        swipeRight=findViewById(R.id.swipeRight);
        swipeLeft=findViewById(R.id.swipeLeft);
        recyclerView=findViewById(R.id.recyclerView);
        nestedScrollView=findViewById(R.id.nestedScrollView);
        progressBar=findViewById(R.id.progressBar);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        drawerLayout=findViewById(R.id.drawerLayout);
        navigationView=findViewById(R.id.navigationView);
        drawerLayout.setScrimColor(getResources().getColor(android.R.color.transparent));

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.hamburger);
         radius= getResources().getDimension(R.dimen.dimen20);
        window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.light_grey));
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        MaterialShapeDrawable navViewBackground = (MaterialShapeDrawable) navigationView.getBackground();
        navViewBackground.setShapeAppearanceModel(
                navViewBackground.getShapeAppearanceModel()
                        .toBuilder()
                        .setTopRightCorner(CornerFamily.ROUNDED,radius)
                        .setBottomRightCorner(CornerFamily.ROUNDED,radius)
                        .setTopLeftCorner(CornerFamily.ROUNDED,radius)
                        .setBottomLeftCorner(CornerFamily.ROUNDED,radius)
                        .build());
        viewPager.setUserInputEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
         swipeRight.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 swipeRight.setEnabled(false);
                 viewPager.setCurrentItem(viewPager.getCurrentItem()-1);
                 if(viewPager.getCurrentItem()>=0) {
                     for(int i=0;i<arrayList.size();i++){
                         if(i==(viewPager.getCurrentItem())) {
                             arrayList.get(viewPager.getCurrentItem()).setHighLight(true);
                         }
                         else {
                             arrayList.get(i).setHighLight(false);

                         }

                     }
                     adapter.notifyDataSetChanged();

                     swipeRight.setEnabled(true);

                 }

             }
         });
        swipeLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swipeLeft.setEnabled(false);
                viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
                arrayList.get(0).setHighLight(false);

                for(int i=1;i<arrayList.size();i++){


                    if(i==(viewPager.getCurrentItem())) {
                        Log.d("position", String.valueOf(i));
                        Log.d("viewPager", String.valueOf(viewPager.getCurrentItem()));
                        arrayList.get(viewPager.getCurrentItem()).setHighLight(true);
                    }
                    else {
                        arrayList.get(i).setHighLight(false);

                    }
                    adapter.notifyDataSetChanged();

                    swipeLeft.setEnabled(true);
                }

            }
        });
        new UserLists().execute();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id=menuItem.getItemId();
                //it's possible to do more actions on several items, if there is a large amount of items I prefer switch(){case} instead of if()
                if (id==R.id.product){
                    SpannableString s = new SpannableString(menuItem.getTitle());
                    s.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.pink)), 0, s.length(), 0);
                    menuItem.setTitle(s);
                    Drawable yourdrawable = menuItem.getIcon();
                    yourdrawable.setColorFilter(getResources().getColor(R.color.pink), PorterDuff.Mode.SRC_IN);

                }
                else if(id==R.id.download)
                {
                    SpannableString s = new SpannableString(menuItem.getTitle());
                    s.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.pink)), 0, s.length(), 0);
                    menuItem.setTitle(s);
                    Drawable yourdrawable = menuItem.getIcon();
                    yourdrawable.setColorFilter(getResources().getColor(R.color.pink), PorterDuff.Mode.SRC_IN);
                }
                else if(id==R.id.pricing)
                {
                    SpannableString s = new SpannableString(menuItem.getTitle());
                    s.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getApplicationContext(), R.color.pink)), 0, s.length(), 0);
                    menuItem.setTitle(s);
                    Drawable yourdrawable = menuItem.getIcon();
                    yourdrawable.setColorFilter(getResources().getColor(R.color.pink), PorterDuff.Mode.SRC_IN);
                }

                //This is for closing the drawer after acting on it
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }


    @Override
    public void sendPosition(int position) {
        viewPager.setCurrentItem(position,true);
        for(int i=0;i<arrayList.size();i++){
            if(i==(position)) {
                arrayList.get(position).setHighLight(true);
            }
            else {
                arrayList.get(i).setHighLight(false);

            }
            adapter.notifyDataSetChanged();
            nestedScrollView.smoothScrollTo(0,0);

        }

    }

    public class UserLists extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] objects) {
            arrayList=new ArrayList<>();

            Call<JsonObject> call = RetrofitClient.getInstance().getMyApi().getUserList();
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    try {
                        if(response.isSuccessful()&&response.body()!=null) {
                            JsonObject object = response.body();
                            JsonArray jsonArray = object.getAsJsonArray("results");
                            for (int i = 0; i < jsonArray.size() - 1; i++) {
                                JsonObject jsonObject = (JsonObject) jsonArray.get(i);
                                String gender = jsonObject.get("gender").toString().replaceAll("\"", "");
                                JsonObject name = jsonObject.getAsJsonObject("name");
                                String title = name.get("title").toString().replaceAll("\"", "");
                                String firstName = name.get("first").toString().replaceAll("\"", "");
                                String lastName = name.get("last").toString().replaceAll("\"", "");
                                JsonObject location = jsonObject.getAsJsonObject("location");
                                JsonObject street = location.getAsJsonObject("street");
                                String number = street.get("number").toString().replaceAll("\"", "");
                                String streetName = street.get("name").toString().replaceAll("\"", "");
                                String state = location.get("state").toString().replaceAll("\"", "");
                                String city = location.get("city").toString().replaceAll("\"", "");
                                String country = location.get("country").toString().replaceAll("\"", "");
                                String postCode = location.get("postcode").toString().replaceAll("\"", "");
                                JsonObject timezone = location.getAsJsonObject("timezone");
                                String offset = timezone.get("offset").toString().replaceAll("\"", "");
                                String description = timezone.get("description").toString().replaceAll("\"", "");
                                String email = jsonObject.get("email").toString().replaceAll("\"", "");
                                String nat = jsonObject.get("nat").toString().replaceAll("\"", "");
                                JsonObject picture = jsonObject.getAsJsonObject("picture");
                                String large = picture.get("large").toString().replaceAll("\"", "");
                                arrayList.add(new UserList(large, firstName, lastName, title, email, nat, number, streetName, city, state, country, postCode, offset, description, gender, higlight));
                                higlight = false;
                            }
                            viewPager.setAdapter(new SliderAdapter(getApplicationContext(), arrayList));
                            viewPager.setClipToPadding(false);
                            viewPager.setClipChildren(false);
                            adapter = new RecyclerViewAdapter(getApplicationContext(), arrayList, MainActivity.this);
                            recyclerView.setAdapter(adapter);
                            progressBar.setVisibility(View.GONE);
                            nestedScrollView.setVisibility(View.VISIBLE);
                        }
                        else{
                            Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG).show();
                        }


                    }
                    catch (Exception ex){
                        ex.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();

                }
            });

            return null;
        }
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }


    }

}