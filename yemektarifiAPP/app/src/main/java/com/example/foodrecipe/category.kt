package com.example.foodrecipe

import androidx.room.*
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "category")

data class category (
    @PrimaryKey(autoGenerate = true)
    var id:Int,

    @ColumnInfo(name = "category_items")
    @Expose
    @SerializedName("categories")
    @TypeConverters(CategoryListConverter::class)
    var categorieitems: List<category_items>? = null
)