package com.example.foodrecipe

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecipeDao {
    @Query("SELECT * FROM category_items ORDER BY id DESC")
    suspend fun getAllCategory():List<category_items>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(categoryItems: category_items?)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(mealsItems: meals_items?)
    @Query("Delete From category_items")
    suspend fun clearDb()
    @Query("SELECT * FROM Meal_items WHERE categoryName = :categoryName ORDER BY id DESC")
    suspend fun getSpecificMealList(categoryName:String):List<meals_items>


}