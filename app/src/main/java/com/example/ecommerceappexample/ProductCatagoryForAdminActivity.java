package com.example.ecommerceappexample;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ecommerceappexample.Interfaces.ProductItemRecyclerClickAction;
import com.example.ecommerceappexample.Model.Product;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductCatagoryForAdminActivity extends AppCompatActivity implements ProductItemRecyclerClickAction {
    private static final String TAG = "ProductCatagoryForAdmin";
    private ArrayList<Product> productsLists = new ArrayList<>();

    @BindView(R.id.catagoriesRecyclerView)
    RecyclerView catagoriesRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_catagory_for_admin);
        ButterKnife.bind(this);

        catagoriesRecyclerView.setAdapter(new CatagoryRecyclerAdapter(productsLists,this));
        catagoriesRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        addProduct();

    }

    private void addProduct() {

        productsLists.add(new Product("T-shirt", R.drawable.tshirts));
        productsLists.add(new Product("Sweater", R.drawable.sweather));
        productsLists.add(new Product("Sport T-shirt", R.drawable.sports));
        productsLists.add(new Product("Female Dress", R.drawable.female_dresses));
        productsLists.add(new Product("Shoe", R.drawable.shoess));
        productsLists.add(new Product("Watch", R.drawable.watches));
        productsLists.add(new Product("Female Purse", R.drawable.purses_bags));
        productsLists.add(new Product("Laptop", R.drawable.laptops));
        productsLists.add(new Product("Air-Phone", R.drawable.headphoness));

    }

    @Override
    public void onItemClicked(int position) {
        Log.d(TAG, "onItemClicked: "+position);
        startActivity(new Intent(ProductCatagoryForAdminActivity.this,AdminAddNewActivity.class));
    }
}
