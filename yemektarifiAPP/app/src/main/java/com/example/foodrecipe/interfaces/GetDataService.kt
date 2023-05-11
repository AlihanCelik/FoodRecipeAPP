package com.example.foodrecipe.interfaces



import com.example.foodrecipe.Meal
import com.example.foodrecipe.MealResponse
import com.example.foodrecipe.category
import retrofit2.Call

import retrofit2.http.GET
import retrofit2.http.Query

interface GetDataService {
    @GET("categories.php")
    fun getCategoryList(): Call<category>

    @GET("filter.php")
    fun getMealList(@Query("c") category: String): Call<Meal>

    @GET("lookup.php")
    fun getSpecificItem(@Query("i") id:String): Call<MealResponse>
}