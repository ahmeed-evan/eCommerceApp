package com.example.ecommerceappexample;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceappexample.Interfaces.ProductItemRecyclerClickAction;
import com.example.ecommerceappexample.Model.Product;

import java.util.ArrayList;

public class CatagoryRecyclerAdapter extends RecyclerView.Adapter<CatagoryRecyclerAdapter.ViewHolder>{

    private ArrayList<Product>products=new ArrayList<>();
    private ProductItemRecyclerClickAction productItemRecyclerClickAction;

    public CatagoryRecyclerAdapter(ArrayList<Product> products,ProductItemRecyclerClickAction productItemRecyclerClickAction) {
        this.products = products;
        this.productItemRecyclerClickAction=productItemRecyclerClickAction;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_catagory_list,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemImageView.setImageResource(products.get(position).getItemImageView());
        holder.itemTextView.setText(products.get(position).getItemTextView());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView itemImageView;
        private TextView itemTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImageView=itemView.findViewById(R.id.itemImageView);
            itemTextView=itemView.findViewById(R.id.itemTextView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    productItemRecyclerClickAction.onItemClicked(getAdapterPosition());
                }
            });
        }
    }
}
