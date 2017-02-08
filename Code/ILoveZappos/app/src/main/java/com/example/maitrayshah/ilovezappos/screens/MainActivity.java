package com.example.maitrayshah.ilovezappos.screens;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.maitrayshah.ilovezappos.R;
import com.example.maitrayshah.ilovezappos.databinding.ActivityMainBinding;
import com.example.maitrayshah.ilovezappos.model.ProductDetails;
import com.example.maitrayshah.ilovezappos.retrofit.GetProducts;
import com.example.maitrayshah.ilovezappos.utils.CheckNetwork;
import com.example.maitrayshah.ilovezappos.utils.ProductDataCallback;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    GetProducts getProducts;
    Toolbar toolbar;
    ImageView share;
    List<ProductDetails> data;
    TextView init_text;
    TextView productName;
    TextView brandName;
    TextView price;
    FloatingActionButton fab;
    CoordinatorLayout rootView;
    boolean addCartFlag = false;
    private static final AccelerateInterpolator ACCELERATE_INTERPOLATOR = new AccelerateInterpolator();
    private static final OvershootInterpolator OVERSHOOT_INTERPOLATOR = new OvershootInterpolator(2);
    CheckNetwork checkNetwork;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        final Typeface face_bold = Typeface.createFromAsset(getAssets(), "Raleway-Bold.ttf");
        Typeface face_semi = Typeface.createFromAsset(getAssets(), "Raleway-SemiBold.ttf");

        //Toolbar animation - slide in effect.
        Animation slide = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide);
        toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        toolbar.startAnimation(slide);

        //Binding different components.
        rootView = binding.rootView;
        share = binding.prodcutPage.share;
        init_text = binding.prodcutPage.initText;
        productName = binding.prodcutPage.productName;
        brandName = binding.prodcutPage.brandName;
        price = binding.prodcutPage.price;
        fab = binding.fab;

        //Setting fonts for different components.
        init_text.setTypeface(face_bold);
        productName.setTypeface(face_bold);
        price.setTypeface(face_bold);
        brandName.setTypeface(face_semi);
        fab.setVisibility(View.INVISIBLE);

        getProducts = new GetProducts(getApplicationContext());
        getProducts.setUpdateListener(new ProductDataCallback() {
            @Override
            public void productCallback(List<ProductDetails> searchResponse) {
                data = searchResponse;
                if (searchResponse.size() > 0) {
                    fab.setVisibility(View.VISIBLE);
                    addCartFlag = true;
                    binding.prodcutPage.setProduct(searchResponse.get(0));
                } else {
                    fab.setVisibility(View.INVISIBLE);
                    addCartFlag = false;
                    binding.prodcutPage.setProduct(null);
                    Snackbar.make(rootView, "Please, search for something more exciting", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        //Intent for share functionality.
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_SUBJECT, "Check this product out");
                share.putExtra(Intent.EXTRA_TEXT, "http://www.zappos.com/product/" + data.get(0).getProductId());
                startActivity(Intent.createChooser(share, "Invite to.."));
            }
        });

        //Add to cart and setting animation - if needed the product also can be stored in some db or file.
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (addCartFlag) {
                    // Animator Set for animating the FAB for add to cart.
                    AnimatorSet animatorSet = new AnimatorSet();
                    ObjectAnimator rotationAnim = ObjectAnimator.ofFloat(fab, "rotation", 0f, 360f);
                    rotationAnim.setDuration(350);
                    rotationAnim.setInterpolator(ACCELERATE_INTERPOLATOR);
                    ObjectAnimator bounceAnimX = ObjectAnimator.ofFloat(fab, "scaleX", 1.2f, 1f);
                    bounceAnimX.setDuration(350);
                    bounceAnimX.setInterpolator(OVERSHOOT_INTERPOLATOR);
                    ObjectAnimator bounceAnimY = ObjectAnimator.ofFloat(fab, "scaleY", 1.2f, 1f);
                    bounceAnimY.setDuration(350);
                    bounceAnimY.setInterpolator(OVERSHOOT_INTERPOLATOR);
                    bounceAnimY.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            fab.setImageResource(R.drawable.cart_filled);

                        }
                    });

                    animatorSet.play(bounceAnimX).with(bounceAnimY).after(rotationAnim);
                    animatorSet.start();
                    Snackbar.make(view, "Added To Cart", Snackbar.LENGTH_SHORT).show();
                    addCartFlag = false;
                } else {
                    // Animator Set for animating the FAB for removing from cart.
                    AnimatorSet animatorSet = new AnimatorSet();
                    ObjectAnimator rotationAnim = ObjectAnimator.ofFloat(fab, "rotation", 360f, 0f);
                    rotationAnim.setDuration(350);
                    rotationAnim.setInterpolator(ACCELERATE_INTERPOLATOR);
                    ObjectAnimator bounceAnimX = ObjectAnimator.ofFloat(fab, "scaleX", 1.2f, 1f);
                    bounceAnimX.setDuration(350);
                    bounceAnimX.setInterpolator(OVERSHOOT_INTERPOLATOR);
                    ObjectAnimator bounceAnimY = ObjectAnimator.ofFloat(fab, "scaleY", 1.2f, 1f);
                    bounceAnimY.setDuration(350);
                    bounceAnimY.setInterpolator(OVERSHOOT_INTERPOLATOR);
                    bounceAnimY.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationStart(Animator animation) {
                            fab.setImageResource(R.drawable.cart);

                        }
                    });

                    animatorSet.play(bounceAnimX).with(bounceAnimY).after(rotationAnim);
                    animatorSet.start();
                    Snackbar.make(view, "Removed From Cart", Snackbar.LENGTH_SHORT).show();
                    addCartFlag = true;
                }
            }
        });

        //detect external intent
        if (getIntent().getData() != null) {
            Uri data = getIntent().getData();
            String shareid = data.getLastPathSegment();
            if (checkNetwork.isInternetAvailable(getApplicationContext()))
                getProducts.getProductsData(shareid);
            else
                Snackbar.make(rootView, "Internet Not Available", Snackbar.LENGTH_LONG).show();


        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Code to display search in toolbar.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.menuSearch);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("onSearchQuerySubmitted", query);
                //make an API call to get Products related to the search query.
                if (checkNetwork.isInternetAvailable(getApplicationContext()))
                    getProducts.getProductsData(query);
                else
                    Snackbar.make(rootView, "Internet Not Available", Snackbar.LENGTH_LONG).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("onSearchQueryChange", newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);

    }
}
