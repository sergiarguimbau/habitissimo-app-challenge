package com.habitissimo.appchallenge;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface HabitissimoAPI {

    @GET("category/list")
    Call<List<Category>> getCategories();

    @GET("category/list/{id}")
    Call<List<Category>> getSubcategories(@Path("id") String id);
}
