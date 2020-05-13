package com.hub.engineering;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.Button;
import android.widget.CheckBox;
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


public class Main2Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    ImageView logo;
    View headerLayout;
    LinearLayout noInternet;
    Dialog dialog;
    WebService apiInterface;
    Button enquire;

    String id,id2="",categoryName,enquiry_type;
    LinearLayout progressLinear;
    SharedPrefrence sharedPrefrence;
    CheckBox repair,manufacturing;
    TextView marquee,companyName,tt;
    private ArrayList<SuitCaseCategory> categories = new ArrayList<>();
    private ArrayList<SuitCaseCategory> categories2 = new ArrayList<>();

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    RecyclerViewSubCategory recyclerViewCategory;
    RecyclerView recyclerView;
    CircularProgressBar progressBar;
    int status=0;
    ShimmerLayout shimmerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        sharedPrefrence = new SharedPrefrence(this);
        shimmerLayout =findViewById(R.id.shimmerLayout);
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_enquiry);
        Objects.requireNonNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        tt = dialog.findViewById(R.id.tt);
        noInternet = findViewById(R.id.noInternet);

        toolbar = findViewById(R.id.main_toolbar);
        manufacturing = dialog.findViewById(R.id.manufacturing);
        repair = dialog.findViewById(R.id.repair);

        apiInterface = ApiClient.getClient().create(WebService.class);


        final Intent intent = getIntent();
        id = intent.getStringExtra("id");
        categoryName = intent.getStringExtra("categoryName");

        toolbar.setTitle(categoryName);


        LayoutAnimationController animationController = AnimationUtils.loadLayoutAnimation(this,R.anim.layout_anim);


        marquee = findViewById(R.id.marquee);
        marquee.setText(sharedPrefrence.read_areas());
        //shimmerLayout = (ShimmerFrameLayout)findViewById(R.id.shimmer_layout);

        marquee.setSelected(true);




        recyclerView = findViewById(R.id.recyclerView);
        toolbar = findViewById(R.id.main_toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(0).setChecked(true);
        setSupportActionBar(toolbar);
        shimmerLayout.startShimmerAnimation();
        headerLayout = navigationView.getHeaderView(0);
        logo = headerLayout.findViewById(R.id.logo);
        companyName = headerLayout.findViewById(R.id.companyName);
       /* companyName.setText(sharedPrefrence.read_name());*/


        Call<CategoryRespose> categoryCall = apiInterface.getSubCategory(id);
        categoryCall.enqueue(new Callback<CategoryRespose>() {
            @Override
            public void onResponse(Call<CategoryRespose> call, Response<CategoryRespose> response) {
                CategoryRespose subCategory = response.body();
                List<CategoryRespose.dataClass.ArrayCategory.SubCategory> subCategorys = subCategory.data.arr_category.get(0).sub_category;
                Glide.with(logo.getContext())
                        .load(subCategory.data.base_url+subCategory.data.app_logo+subCategory.data.accountSetting.site_logo)
                        .into(logo);
                for(CategoryRespose.dataClass.ArrayCategory.SubCategory data : subCategorys){
                    id2 = id2+data.category_id;
                    AddData(data.category_id,data.category_name,subCategory.data.base_url+subCategory.data.category_image+data.category_image,data.category_slug,data.category_root_id,
                            data.category_servicing,data.category_manufacturing,data.manufacturing_text,data.servicing_text);

                }
                Log.d("successk2","yes"+id2);

                shimmerLayout.stopShimmerAnimation();
                shimmerLayout.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<CategoryRespose> call, Throwable t) {
                Log.d("successk","no"+t.fillInStackTrace());
            }
        });
        manufacturing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPrefrence.addEnquiryDetails(sharedPrefrence.read_id(),"2");
                dialog.dismiss();
                status=0;
                Intent intent = new Intent(getApplicationContext(), EnquireForm.class);
                startActivity(intent);
            }
        });
        repair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPrefrence.addEnquiryDetails(sharedPrefrence.read_id(),"2");
                dialog.dismiss();
                status=0;
                Intent intent = new Intent(getApplicationContext(), help.class);
                startActivity(intent);
            }
        });

        /*radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                enquire.setBackgroundResource(R.drawable.confirm_btn);
                enquire.setTextColor(getResources().getColor(R.color.colorText));
                RadioButton rb = (RadioButton) radioGroup.findViewById(i);

                if(rb!=null&& rb.getText().toString().equals("Manufacturing")){

                    sharedPrefrence.addEnquiryDetails(sharedPrefrence.read_id(),"2");
                    status = 2;
                }else if(rb!=null && rb.getText().toString().equals("Servicing")){
                    sharedPrefrence.addEnquiryDetails(sharedPrefrence.read_id(),"1");
                    status =1;


                }else {
                    enquire.setBackgroundResource(R.drawable.disable_confirm_btn);
                    enquire.setTextColor(getResources().getColor(R.color.fadeColor));

                }

            }
        });*/

       /* enquire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(status==2){
                    dialog.dismiss();
                    status=0;
                    Intent intent = new Intent(getApplicationContext(), EnquireForm.class);
                    startActivity(intent);
                }
                else if(status==1){
                    dialog.dismiss();
                    status=0;
                    Intent intent = new Intent(getApplicationContext(), ContactUs.class);
                    startActivity(intent);
                }
                else
                    Toast.makeText(getApplicationContext(),"Select Option..",Toast.LENGTH_LONG).show();

            }
        });*/

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;


        recyclerViewCategory = new RecyclerViewSubCategory(this,categories,width/3);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new Main2Activity.GridSpacingItemDecoration(4, dpToPx(15), true));
        recyclerView.setAdapter(recyclerViewCategory);
        recyclerView.setLayoutAnimation(animationController);
        recyclerViewCategory.setOnItemClickListener(new RecyclerViewSubCategory.OnItemClickListener() {
            @Override
            public void onItemClick(final int position, View view) {
                repair.setText(categories.get(position).servicing_text);
                manufacturing.setText(categories.get(position).manufacturing_text);
                if(repair.isChecked())
                    repair.setChecked(false);
                if(manufacturing.isChecked())
                    manufacturing.setChecked(false);
                Animation animation = new AlphaAnimation(1f,0.5f);
                id2 = categories.get(position).id;
                sharedPrefrence.addEnquiryDetails(categories.get(position).id,sharedPrefrence.read_enquiry_type());
                categoryName = categories.get(position).categoryName;
                view.setAnimation(animation);
                view.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {

                        status=0;
                        if(categories.get(position).categoryManufacturing.equals("1"))
                            manufacturing.setVisibility(View.VISIBLE);
                        else
                            manufacturing.setVisibility(View.GONE);
                        if(categories.get(position).categoryService.equals("1"))
                            repair.setVisibility(View.VISIBLE);
                        else
                            repair.setVisibility(View.GONE);
                        tt.setText(categoryName);
                        dialog.show();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout,toolbar,R.string.openNavDrawer,
                R.string.claoseNavDrawer);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);



    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    public void AddData(String id, String categoryName, String url, String slug, String rootId,
                        String categoryService, String categoryManufacturing,String manufacturing_text,String servicing_text){
        SuitCaseCategory suitCaseCategory = new SuitCaseCategory();
        suitCaseCategory.id = id;
        suitCaseCategory.categoryName = categoryName;
        suitCaseCategory.url = url;
        suitCaseCategory.slug = slug;
        suitCaseCategory.rootId = rootId;
        suitCaseCategory.categoryService = categoryService;
        suitCaseCategory.categoryManufacturing = categoryManufacturing;
        suitCaseCategory.manufacturing_text = manufacturing_text;
        suitCaseCategory.servicing_text = servicing_text;
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

        else if(id==R.id.home){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
            }, 200);
        }

        return true;


    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    public void finishs() {
        try {
            Main2Activity.this.finish();
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
        else
            super.onBackPressed();
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
}
