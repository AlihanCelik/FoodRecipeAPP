package com.example.foodrecipe

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.foodrecipe.interfaces.GetDataService
import com.example.foodrecipe.retrofitclient.RetrofitClientInstance
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import retrofit2.Call

import retrofit2.Callback
import retrofit2.Response


class SplashScreen : BaseActivity() , EasyPermissions.RationaleCallbacks,EasyPermissions.PermissionCallbacks{
    private var READ_STORAGE_PERM=123
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        readStrogeTask()
        Start.setOnClickListener {
            val intent=Intent(this,HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    fun getCategories() {
        val service = RetrofitClientInstance.retrofitInstance!!.create(GetDataService::class.java)
        val call = service.getCategoryList()
        call.enqueue(object : Callback<category>{
            override fun onResponse(call: Call<category>, response: Response<category>) {
                for(arr in response.body()!!.categorieitems!!){
                    getMeal(arr.strcategory)
                }
                insertDataIntoRoomDb(response.body())
            }

            override fun onFailure(call: Call<category>, t: Throwable) {
                Toast.makeText(this@SplashScreen, "Something went wrong", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun getMeal(categoryName:String) {
        val service = RetrofitClientInstance.retrofitInstance!!.create(GetDataService::class.java)
        val call = service.getMealList(categoryName)
        call.enqueue(object : Callback<Meal>{
            override fun onResponse(call: Call<Meal>, response: Response<Meal>) {

                insertMealDataIntoRoomDb(categoryName,response.body())
            }

            override fun onFailure(call: Call<Meal>, t: Throwable) {
                progressbar.visibility= View.INVISIBLE
                Toast.makeText(this@SplashScreen, "Something went wrong", Toast.LENGTH_SHORT).show()
            }

        })
    }
    fun insertMealDataIntoRoomDb(categoryName: String,meal: Meal?) {
        launch {
            meal?.let {
                it.mealsItem?.forEach { arr ->
                    var mealItemModel=meals_items(arr.id,arr.idMeal,categoryName,arr.strMeal,arr.strMealThumb)

                    database.getDatabase(this@SplashScreen).recipeDao().insertMeal(mealItemModel)
                    Log.d("mealData",arr.toString())
                }
                Start.visibility = View.VISIBLE
                progressbar.visibility= View.INVISIBLE

            }
        }
    }
    fun insertDataIntoRoomDb(category: category?) {
        launch {
            category?.let {
                it.categorieitems?.forEach { arr ->
                    database.getDatabase(this@SplashScreen).recipeDao().insertCategory(arr)
                }
            }
        }
    }
    fun clearDataBase(){
        launch {
            this.let {
                database.getDatabase(this@SplashScreen).recipeDao().clearDb()
            }
        }
    }
    private fun hasReadStoragePermission():Boolean{
        return EasyPermissions.hasPermissions(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)
    }
    private fun readStrogeTask(){
        if(hasReadStoragePermission()){
            clearDataBase()
            getCategories()
        }else{
            EasyPermissions.requestPermissions(this,"This app needs access to your storage",
                READ_STORAGE_PERM,android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this)
    }
    override fun onRationaleAccepted(requestCode: Int) {

    }

    override fun onRationaleDenied(requestCode: Int) {

    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if(EasyPermissions.somePermissionPermanentlyDenied(this,perms)){
            AppSettingsDialog.Builder(this).build().show()
        }
    }
}