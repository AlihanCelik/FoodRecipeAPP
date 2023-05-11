package com.example.foodrecipe

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [recipe::class,category::class,category_items::class,meals_items::class,Meal::class], version =1, exportSchema = false )
@TypeConverters(CategoryListConverter::class,MealListConverter::class)
abstract class database :RoomDatabase(){
    companion object{
        var recipesDatabase:database?=null
        @Synchronized
        fun getDatabase(context: Context):database{
            if(recipesDatabase==null){
                recipesDatabase= Room.databaseBuilder(context,database::class.java,"recipe.db").build()
            }
            return recipesDatabase!!
        }


    }
    abstract fun recipeDao():RecipeDao

}