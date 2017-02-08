package com.example.maitrayshah.ilovezappos.retrofit;

import android.content.Context;
import com.example.maitrayshah.ilovezappos.BuildConfig;
import com.example.maitrayshah.ilovezappos.model.ProductDetails;
import com.example.maitrayshah.ilovezappos.model.SearchResponse;
import com.example.maitrayshah.ilovezappos.model.ApiInterface;

import com.example.maitrayshah.ilovezappos.utils.ProductDataCallback;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by maitrayshah on 06/02/17.
 */

public class GetProducts {

    ApiInterface apiCall;
    Context context;
    ProductDataCallback callBack;

    public GetProducts(Context context) {
        apiCall = RetrofitClient.getClient().create(ApiInterface.class);
        this.context = context;
    }

    public void setUpdateListener(ProductDataCallback callBack) {
        this.callBack = callBack;
    }

    public void getProductsData(String query)
    {
        Call<SearchResponse> call = apiCall.getProductDetails(BuildConfig.KEY, query);
        call.enqueue(new Callback<SearchResponse>() {
                         @Override
                         public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                             List<ProductDetails> productData = response.body().getResults();
                             callBack.productCallback(productData);
                         }

                         @Override
                         public void onFailure (Call <SearchResponse> call, Throwable t){


                         }
                     }

        );
    }
}
