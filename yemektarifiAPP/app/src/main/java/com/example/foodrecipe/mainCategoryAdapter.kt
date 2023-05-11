package com.example.foodrecipe

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_category.view.imageFood
import kotlinx.android.synthetic.main.item_category.view.tv_dish_name


class mainCategoryAdapter:RecyclerView.Adapter<mainCategoryAdapter.RecipeViewHolder>() {
    var listener:OnItemClickListener?=null
    var context:Context?=null;
    var arrMAinCategory=ArrayList<category_items>()
    class RecipeViewHolder(view:View):RecyclerView.ViewHolder(view){

    }
    fun setData(arrData:List<category_items>){
        arrMAinCategory=arrData as ArrayList<category_items>
    }
    fun setClickListener(listener1:OnItemClickListener){
        listener=listener1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        context=parent.context
        return RecipeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_category,parent,false))
    }

    override fun getItemCount(): Int {
        return arrMAinCategory.size
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.itemView.tv_dish_name.text=arrMAinCategory[position].strcategory
        Glide.with(context!!).load(arrMAinCategory[position].strcategorythumb).into(holder.itemView.imageFood)
        holder.itemView.rootView.setOnClickListener{
            listener!!.onClicked(arrMAinCategory[position].strcategory)
        }
    }
    interface OnItemClickListener{
        fun onClicked(categoryName:String)
    }
}