package com.example.check24products.presentation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.check24products.presentation.constants.Constants;
import com.example.check24products.databinding.ProductItemBinding;
import com.example.check24products.data.model.Product;
import com.example.check24products.presentation.util.Utils;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {

    private List<Product> mProductList;
    private Context context;
    private String filter = "";
    private ProductListAdapterListener listener;

    public interface ProductListAdapterListener{
        void onLoadImage(String imageUrl, ImageView imageView);
    }


    public ProductListAdapter(Context context, ProductListAdapterListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductItemBinding view = ProductItemBinding.inflate(LayoutInflater.from(context), parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = null;
        if (filter.toUpperCase().equals(Constants.FILTER_AVAILABLE)) {
            if(mProductList.get(position).isAvailable()){
                product = mProductList.get(position);
            }

        } else if (filter.toUpperCase().equals(Constants.FILTER_PRE_MARKET)) {
            if(!mProductList.get(position).isAvailable()){
                product =mProductList.get(position);
            }

        } else {
            product = mProductList.get(position);
        }
        if (product == null) {
            holder.binding.productNameTextview.setVisibility(View.GONE);
            holder.binding.productDescriptionTextview.setVisibility(View.GONE);
            holder.binding.productPriceTextview.setVisibility(View.GONE);
            holder.binding.productReleaseDateTextview.setVisibility(View.GONE);
            holder.binding.productImageView.setVisibility(View.GONE);
            holder.binding.ratingBar.setVisibility(View.GONE);

        }else{
            holder.binding.productNameTextview.setText(product.getName());
            holder.binding.productDescriptionTextview.setText(product.getDescription());
            holder.binding.productPriceTextview.setText(Utils.getPriceWithCurrency(product.getPrice()));
            holder.binding.productReleaseDateTextview.setText(Utils.getFormattedDate(product.getReleaseDate()));
            holder.binding.ratingBar.setRating((float) product.getRating());
            if (product.getImageURL() != null && !product.getImageURL().isEmpty()) {
                listener.onLoadImage(product.getImageURL(), holder.binding.productImageView);
            }
        }
    }

    @Override
    public int getItemCount() {
        int size = 0;
        if (mProductList != null) {
            size = mProductList.size();
        }
        return size;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ProductItemBinding binding;

        public ViewHolder(ProductItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

    }

    public void setmProductList(List<Product> mProductList) {
        this.mProductList = mProductList;
    }

    public void setFilter(String filter) {
        this.filter = filter;
        notifyDataSetChanged();
    }


}
