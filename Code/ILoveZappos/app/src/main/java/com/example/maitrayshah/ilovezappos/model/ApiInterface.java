package com.example.maitrayshah.ilovezappos.model;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("Search")
    Call<com.example.maitrayshah.ilovezappos.model.SearchResponse> getProductDetails(@Query("key") String apiKey, @Query("term") String searchKey);
}
