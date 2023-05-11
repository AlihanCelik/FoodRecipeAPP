package com.example.foodrecipe

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CategoryListConverter {
    @TypeConverter
    fun fromCategoryList(category: List<category_items>):String?{
        if(category==null){
            return (null)
        }else{
            val gson= Gson()
            val type=object : TypeToken<category_items>(){

            }.type
            return gson.toJson(category,type)
        }
    }
    @TypeConverter
    fun toCategoryList(categoryString: String):List<category_items>?{
        if(categoryString==null){
            return (null)
        }else{
            val gson=Gson()
            val type=object :TypeToken<category_items>(){

            }.type
            return gson.fromJson(categoryString,type)


        }
    }
}