package com.hub.engineering;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.budiyev.android.circularprogressbar.CircularProgressBar;
import com.bumptech.glide.Glide;
import com.hub.engineering.ResponseDataClass.CategoryRespose;
import com.hub.engineering.permissions.Permission;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.supercharge.shimmerlayout.ShimmerLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ShimmerLayout shimmerLayout;
    String areas;
    String version;
    ImageView logo,logotoolImage;
    TextView companyName,update,no_thanks;
    private Toolbar toolbar;
    View headerLayout;
    LinearLayout noInternet;
    AlertDialog builder;
    Dialog dialog;
    LinearLayout progressLinear;
    TextView marquee;
    LinearLayout privacy_policy;
    private ArrayList<SuitCaseCategory> categories = new ArrayList<>();
    private ArrayList<SuitCaseCategory> categories2 = new ArrayList<>();
    SharedPrefrence sharedPrefrence;
    SharedPreferences sharedPreferences;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    RecyclerViewCategory recyclerViewCategory;
    RecyclerView recyclerView;
    CircularProgressBar progressBar;
    WebService apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        shimmerLayout =findViewById(R.id.shimmerLayout);
        shimmerLayout.startShimmerAnimation();
        noInternet = findViewById(R.id.noInternet);
        sharedPrefrence = new SharedPrefrence(this);

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.update_dialog);
        Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        update = dialog.findViewById(R.id.update);
        no_thanks = dialog.findViewById(R.id.no_thanks);

        /*progressBar = findViewById(R.id.progress_bar);
        progressBar.setProgress(30f);*/
        LayoutAnimationController animationController = AnimationUtils.loadLayoutAnimation(this,R.anim.layout_anim);

        marquee = findViewById(R.id.marquee);
        //shimmerLayout = (ShimmerFrameLayout)findViewById(R.id.shimmer_layout);

        marquee.setSelected(true);
        sharedPreferences = getApplicationContext().getSharedPreferences("Jjj",Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();


        recyclerView = findViewById(R.id.recyclerView);
        toolbar = findViewById(R.id.main_toolbar);


        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        headerLayout = navigationView.getHeaderView(0);
        logo = headerLayout.findViewById(R.id.logo);
        companyName = headerLayout.findViewById(R.id.companyName);
        version = sharedPrefrence.read_version();

        navigationView.getMenu().getItem(0).setChecked(true);
        setSupportActionBar(toolbar);

        setCheckConnection();
        noInternet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation anim = new AlphaAnimation(0.0f, 1.0f);
                anim.setDuration(50); //You can manage the blinking time with this parameter
                anim.setStartOffset(20);
                anim.setRepeatMode(Animation.REVERSE);
                anim.setRepeatCount(2);
                noInternet.startAnimation(anim);
                setCheckConnection();
                AlertDialog alertDialog = new AlertDialog.Builder(view.getContext())
                        .setMessage("You need to have Mobile Data or Wifi to access this application.")
                        .setTitle("No internet Connection")
                        .setIcon(R.drawable.ic_no_wifi)
                        .setPositiveButton("OK", null)
                        .show();
            }
        });
        no_thanks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                sharedPrefrence.versionChange(version);
                Uri uri = Uri.parse("https://play.google.com/store/apps/"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });



        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        apiInterface = ApiClient.getClient().create(WebService.class);

        Call<CategoryRespose> call = apiInterface.getBrand();
        call.enqueue(new Callback<CategoryRespose>() {
            @Override
            public void onResponse(Call<CategoryRespose> call, Response<CategoryRespose> response) {
                CategoryRespose categoryRespose = response.body();
                assert categoryRespose != null;

                if(!sharedPrefrence.read_version().equals(categoryRespose.data.accountSetting.app_version)){

                            dialog.show();
                }
                version = categoryRespose.data.accountSetting.app_version;
                areas = categoryRespose.data.accountSetting.delivery_area;
                sharedPrefrence.addArea(areas);
                marquee.setText(areas);
                List<CategoryRespose.dataClass.ArrayCategory> arrayCategories = categoryRespose.data.arr_category;
                Log.d("address",categoryRespose.data.accountSetting.site_address);
                Glide.with(logo.getContext())
                        .load(categoryRespose.data.base_url+categoryRespose.data.app_logo+categoryRespose.data.accountSetting.site_logo)
                        .into(logo);



                sharedPrefrence.add_info(categoryRespose.data.accountSetting.site_email,categoryRespose.data.accountSetting.site_name,categoryRespose.data.accountSetting.site_number,categoryRespose.data.accountSetting.site_address,
                        categoryRespose.data.base_url+categoryRespose.data.app_logo+categoryRespose.data.accountSetting.site_logo,categoryRespose.data.accountSetting.site_help_number,categoryRespose.data.accountSetting.latitude,
                        categoryRespose.data.accountSetting.longitude,categoryRespose.data.accountSetting.director_name);
                for(CategoryRespose.dataClass.ArrayCategory data : arrayCategories){
                    if(data.category_root_id.equals("0"))
                        AddData(data.category_id,data.category_name,categoryRespose.data.base_url+categoryRespose.data.category_image+data.category_image,data.category_slug,data.category_root_id,
                            data.category_servicing,data.category_manufacturing);


                }


                Log.d("successk","yes");

                shimmerLayout.stopShimmerAnimation();
                shimmerLayout.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<CategoryRespose> call, Throwable t) {
                shimmerLayout.stopShimmerAnimation();
                shimmerLayout.setVisibility(View.GONE);
                Log.d("successk","no"+t.fillInStackTrace());

            }
        });



        recyclerViewCategory = new RecyclerViewCategory(this,categories,dpToPx(width/3));

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        //recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(15), true));
        recyclerView.setAdapter(recyclerViewCategory);
        recyclerView.setLayoutAnimation(animationController);
        /*recyclerViewCategory.setOnItemClickListener(new RecyclerViewCategory.OnItemClickListener() {
            @Override
            public void onItemClick(final int position, View view) {
                Animation animation = new AlphaAnimation(1f,0.5f);
                view.setAnimation(animation);
                view.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                        intent.putExtra("id",categories.get(position).id);
                        intent.putExtra("categoryName",categories.get(position).categoryName);
                        startActivity(intent);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });


            }
        });*/
        navigationView.bringToFront();

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout,toolbar,R.string.openNavDrawer,
                R.string.claoseNavDrawer);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);



    }


    public void AddData(String id, String categoryName,String url,String slug,String rootId,String categoryService, String categoryManufacturing){
        SuitCaseCategory suitCaseCategory = new SuitCaseCategory();
        suitCaseCategory.id = id;
        suitCaseCategory.categoryName = categoryName;
        suitCaseCategory.url = url;
        suitCaseCategory.slug = slug;
        suitCaseCategory.rootId = rootId;
        suitCaseCategory.categoryService = categoryService;
        suitCaseCategory.categoryManufacturing = categoryManufacturing;
        categories.add(suitCaseCategory);
    }


    @Override
    protected void onResume() {
        super.onResume();
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        drawerLayout.closeDrawers();
        if(id==R.id.about_us){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(), AboutUs.class);
                    startActivity(intent);
                }
            }, 200);

        }
        else if(id==R.id.contact_us){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(), ContactUs.class);
                    startActivity(intent);
                }
            }, 200);

        }
        else if(id==R.id.term_condition) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(), TermsConditions.class);
                    startActivity(intent);
                }
            }, 200);

        }
        else if(id==R.id.privacy_policy) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(), PrivacyPolicy.class);
                    startActivity(intent);
                }
            }, 200);
        }
        else if(id==R.id.help){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(), help.class);
                    startActivity(intent);
                }
            }, 200);
        }




        return true;

    }

    public void setCheckConnection() {

        Permission permissions = new Permission();
        if (permissions.checkInternetConnection(this)) {
            noInternet.setVisibility(View.GONE);

        } else {
            noInternet.setVisibility(View.VISIBLE);

        }

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setCheckConnection();
            }
        }, 1000);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    public void finishs() {
        try {
            MainActivity.this.finish();
        } catch (Exception e) {

        }

    }


    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            final AlertDialog builder = new AlertDialog.Builder(this)
                    .setMessage("Are you sure. You want to Exit")
                    .setTitle("Confirm Exit..!!")
                    .setCancelable(false)
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            MainActivity.super.onBackPressed();
                        }
                    })
                    .setIcon(R.drawable.ic_exit_icon)
                    .show();
        }


    }
}

