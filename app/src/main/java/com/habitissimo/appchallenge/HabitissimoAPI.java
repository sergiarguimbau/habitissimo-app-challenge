package com.habitissimo.appchallenge;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface HabitissimoAPI {

    @GET("category/list")
    Call<List<Category>> getCategories();
}
