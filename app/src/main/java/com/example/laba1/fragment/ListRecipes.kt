package com.example.laba1.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.laba1.Adapter.AdapterListRecipes
import com.example.laba1.R
import com.example.laba1.api.Common
import com.example.laba1.db.DataBase
import com.example.laba1.db.entities.Recipe
import com.example.laba1.db.entities.UsersRecipesJunction
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread

class ListRecipes: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_list_recipes, container, false)
        lateinit var list: List<Recipe>
        val db = DataBase.getDatabase(requireContext())
        Common.retrofitService.getRecipes().enqueue(object :
            Callback<List<Recipe>> {
            override fun onFailure(call: Call<List<Recipe>>, t: Throwable) {}
            override fun onResponse(call: Call<List<Recipe>>, response: Response<List<Recipe>>) {
                println("Успех ${response.body()}")
                list = response.body()!!
                thread {
                    if (db.getDao().getCountRecipes() == 0) {
                        list.forEach {
                            db.getDao().insertRecipes(it)
                        }
                    }
                    val listFromDb = db.getDao().getAllRecipes()
                    val rv = view.findViewById<RecyclerView>(R.id.list_recieps_rv)
                    lateinit var adapter: AdapterListRecipes

                    adapter = AdapterListRecipes(listFromDb, requireActivity()) { adapter.notifyDataSetChanged() }
                    requireActivity().runOnUiThread {
                        rv.layoutManager =
                            GridLayoutManager(requireContext(), 2)
                        rv.adapter = adapter
                    }
                }
            }
        })
        return view
    }
}

interface Listener{
    fun onClick(recipe: Recipe, view: View){
        val bundle = Bundle()
        bundle.putString("name", recipe.Name)
        bundle.putString("Calorie", recipe.Calorie.toString())
        bundle.putString("Difficulty", recipe.Difficulty.toString())
        bundle.putString("Time", recipe.Time.toString())
        bundle.putString("Ingredients", recipe.Ingredients)

        Navigation.findNavController(view).navigate(R.id.selected, bundle)
    }

    fun onLongClick(recipe: Recipe, activity: Activity){
        thread {
            val id = activity.getSharedPreferences("user", Context.MODE_PRIVATE).getInt("id",0)
            val dao = DataBase.getDatabase(activity).getDao()
            if (!dao.checkFavoriteRecipeExist(id, recipe.idRecipe!!)){
                activity.runOnUiThread {
                    AlertDialog.Builder(activity).setCancelable(true)
                    .setMessage(recipe.Name)
                    .setTitle("Добавить рецепт в список любимых рецептов?")
                    .setNegativeButton("отмена") { _, _ -> }
                    .setPositiveButton("в избранное") { _, _ ->
                        thread { dao.insertToFavoriteRecipes(UsersRecipesJunction(id, recipe.idRecipe))
                        println(dao.getFavoriteRecipesByUserId(id)) }
                    }
                    .create().show()
                }
            }
            else{
                activity.runOnUiThread {
                    AlertDialog.Builder(activity).setCancelable(true)
                    .setMessage(recipe.Name)
                    .setTitle("Удалить рецепт из списка любимых рецептов?")
                    .setNegativeButton("отмена") { _, _ -> }
                    .setPositiveButton("удалить") { _, _ ->
                        thread {  dao.deleteFavoriteRecipesByUserId(id, recipe.idRecipe) }
                    }.create().show()
                }
            }
        }
    }

}