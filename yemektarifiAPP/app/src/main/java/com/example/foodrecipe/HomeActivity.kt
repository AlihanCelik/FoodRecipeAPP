package com.example.foodrecipe


import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_home.*

import kotlinx.coroutines.launch



class HomeActivity : BaseActivity() {

    var arrMainCategory=ArrayList<category_items>()
    var arrSubCategory=ArrayList<meals_items>()
    var mainCategoryAdapter=mainCategoryAdapter()
    var subCategoryAdapter=SubCategoryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        getDataFromDb()

        mainCategoryAdapter.setClickListener(onClicked)
        subCategoryAdapter.setClickListener(onClickedsub)


    }
    private val onClicked=object : mainCategoryAdapter.OnItemClickListener{
        override fun onClicked(categoryName: String) {
            getMealDataFromDb(categoryName)
        }

    }
    private val onClickedsub=object : SubCategoryAdapter.OnItemClickListener{
        override fun onClicked(id:String) {
            var intent= Intent(this@HomeActivity,DetailActivity::class.java)
            intent.putExtra("id",id)
            startActivity(intent)
        }

    }
    private fun getDataFromDb(){
        launch {
            this.let {
                var cat=database.getDatabase(this@HomeActivity).recipeDao().getAllCategory()
                arrMainCategory = cat as ArrayList<category_items>
                arrMainCategory.reverse()
                getMealDataFromDb(arrMainCategory[0].strcategory)
                mainCategoryAdapter.setData(arrMainCategory)
                category.layoutManager=LinearLayoutManager(this@HomeActivity,LinearLayoutManager.HORIZONTAL,false)
                category.adapter=mainCategoryAdapter

            }

        }

    }
    private fun getMealDataFromDb(categoryName:String){
        categoryText.text="$categoryName Category"
        launch {
            this.let {
                var cat=database.getDatabase(this@HomeActivity).recipeDao().getSpecificMealList(categoryName)
                arrSubCategory = cat as ArrayList<meals_items>
                subCategoryAdapter.setData(arrSubCategory)
                categorysub.layoutManager=LinearLayoutManager(this@HomeActivity,LinearLayoutManager.HORIZONTAL,false)
                categorysub.adapter=subCategoryAdapter

            }

        }

    }




}