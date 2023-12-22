package com.example.laba1.Adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.laba1.db.entities.Recipe
import com.example.laba1.R
import com.example.laba1.fragment.Listener


class AdapterListRecipes (
    private val recipeList: List<Recipe>,
    private val activity: Activity,
    private val update: () -> Unit
): RecyclerView.Adapter<AdapterListRecipes.RecipeViewHolder>(), Listener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.recipe_card,parent,false)
        return  RecipeViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.recipe_dif.text = recipeList[position].Difficulty.toString()
        holder.recipe_cal.text = recipeList[position].Calorie.toString()
        holder.recipe_name.text = recipeList[position].Name
        holder.itemView.setOnClickListener{
            onClick(recipeList[position], it)
        }
        holder.itemView.setOnLongClickListener {
            onLongClick(recipeList[position], activity = activity)
            update()
            true
        }
    }

    override fun getItemCount(): Int {
        return recipeList.size
    }
    class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var recipe_name: TextView
        var recipe_dif: TextView
        var recipe_cal: TextView

        init {
            recipe_name= itemView.findViewById(R.id.name)
            recipe_dif = itemView.findViewById(R.id.Difficulty)
            recipe_cal = itemView.findViewById(R.id.Calorie)
        }
    }
}