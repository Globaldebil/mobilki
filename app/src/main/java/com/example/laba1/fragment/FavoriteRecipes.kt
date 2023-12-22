package com.example.laba1.fragment

import android.content.Context
import android.os.Bundle
import android.service.notification.Condition
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.laba1.Adapter.AdapterListRecipes
import com.example.laba1.R
import com.example.laba1.db.DataBase
import kotlin.concurrent.thread

class FavoriteRecipes: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_favorite_recipes, container, false)
        thread {
            val list = DataBase.getDatabase(requireContext()).getDao().getFavoriteRecipesByUserId(
                requireActivity().getSharedPreferences("user", Context.MODE_PRIVATE).getInt("id", 0)
            )
            lateinit var adapter: AdapterListRecipes

            adapter = AdapterListRecipes(list, requireActivity()) { adapter.notifyDataSetChanged() }
            requireActivity().runOnUiThread {
                view!!.findViewById<RecyclerView>(R.id.favoriteRV).adapter = adapter
            }
        }
        return view
    }
}