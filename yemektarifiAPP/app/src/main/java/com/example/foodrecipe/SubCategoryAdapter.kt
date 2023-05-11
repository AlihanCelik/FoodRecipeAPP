package com.example.foodrecipe

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_category.view.*

class SubCategoryAdapter:RecyclerView.Adapter<SubCategoryAdapter.RecipeViewHolder>() {
    var listener: SubCategoryAdapter.OnItemClickListener?=null
    var context:Context?=null
    var arrSubCategory=ArrayList<meals_items>()
    class RecipeViewHolder(view: View):RecyclerView.ViewHolder(view){

    }
    fun setData(arrData:List<meals_items>){
        arrSubCategory=arrData as ArrayList<meals_items>
    }
    fun setClickListener(listener1: SubCategoryAdapter.OnItemClickListener){
        listener=listener1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        context=parent.context
        return RecipeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_categorysub,parent,false))
    }

    override fun getItemCount(): Int {
        return arrSubCategory.size
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.itemView.tv_dish_name.text=arrSubCategory[position].strMeal
        Glide.with(context!!).load(arrSubCategory[position].strMealThumb).into(holder.itemView.imageFood)
        holder.itemView.rootView.setOnClickListener{
            listener!!.onClicked(arrSubCategory[position].idMeal)
        }
    }
    interface OnItemClickListener{
        fun onClicked(id:String)
    }
}