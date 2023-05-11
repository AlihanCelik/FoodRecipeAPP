package com.example.foodrecipe

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MealListConverter {
    @TypeConverter
    fun fromCategoryList(category: List<meals_items>):String?{
        if(category==null){
            return (null)
        }else{
            val gson= Gson()
            val type=object : TypeToken<meals_items>(){

            }.type
            return gson.toJson(category,type)
        }
    }
    @TypeConverter
    fun toCategoryList(categoryString: String):List<meals_items>?{
        if(categoryString==null){
            return (null)
        }else{
            val gson= Gson()
            val type=object : TypeToken<meals_items>(){

            }.type
            return gson.fromJson(categoryString,type)


        }
    }
}