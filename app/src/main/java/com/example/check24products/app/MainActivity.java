package com.example.check24products.app;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.check24products.R;
import com.example.check24products.app.adapter.ProductListAdapter;
import com.example.check24products.databinding.ActivityMainBinding;
import com.example.check24products.data.model.Header;
import com.example.check24products.data.model.Product;
import com.example.check24products.app.presenter.MainPresenter;
import com.example.check24products.app.presenter.contract.MainView;
import com.google.android.material.tabs.TabLayout;

import java.util.List;


public class MainActivity extends AppCompatActivity implements MainView {

    private ActivityMainBinding binding;

    private ProductListAdapter mAdapter;

    private MainPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initView();
        mPresenter = new MainPresenter(this);
        mPresenter.getProducts(false);

    }

    private void initView() {
        mAdapter = new ProductListAdapter(getApplicationContext(), new ProductListAdapter.ProductListAdapterListener() {
            @Override
            public void onLoadImage(String imageUrl, ImageView imageView) {
                Glide.with(getApplicationContext()).load(imageUrl).placeholder(R.drawable.ic_launcher_background).into(imageView);
            }
        });
        binding.refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.getProducts(true);
            }
        });
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        binding.productListRv.setLayoutManager(llm);
        binding.productListRv.setAdapter(mAdapter);
        binding.productListRv.addItemDecoration(itemDecoration);

        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                String filterName = tab.getText().toString();
                mPresenter.getFilteredProduct(filterName);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    @Override
    public void loadHeader(Header header) {
        binding.headerTitleTextview.setText(header.getHeaderTitle());
        binding.headerDescriptionTextview.setText(header.getHeaderDescription());
    }

    @Override
    public void loadFilters(List<String> filters) {
        for (int i = 0; i < filters.size(); i++) {
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText(filters.get(i)));
        }
    }

    @Override
    public void loadProducts(List<Product> productList) {
        mAdapter.setmProductList(productList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showProgressBar() {
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        binding.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void refresh(List<Product> productList) {
        mAdapter.setmProductList(productList);
        mAdapter.notifyDataSetChanged();
        TabLayout.Tab tab = binding.tabLayout.getTabAt(0);
        tab.select();

    }

    @Override
    public void onError(String errMsg) {
        Toast.makeText(this, errMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFilterProdcutList(String filterName) {
        mAdapter.setFilter(filterName);
    }
}