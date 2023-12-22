package com.example.laba1.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.laba1.R

class SelectedRecipes: Fragment() {

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_selected_recipes, container, false)
        if (arguments?.getString("Difficulty") == null) {
            view.findViewById<TextView>(R.id.name).text = "Рецепт не выбран"
            view.findViewById<TextView>(R.id.difficulty).text =
                "Уровень сложности: " + "Рецепт не выбран"
            view.findViewById<TextView>(R.id.calorie).text =
                "Калорийность: " + "Рецепт не выбран"
            view.findViewById<TextView>(R.id.ingridient).text =
                "Ингредиенты: " + "Рецепт не выбран"
            view.findViewById<TextView>(R.id.time).text =
                "Время приготовления: " + "Рецепт не выбран"
        } else {
            view.findViewById<TextView>(R.id.name).text = arguments?.getString("name")
            view.findViewById<TextView>(R.id.difficulty).text =
                "Уровень сложности: " + arguments?.getString("Difficulty")
            view.findViewById<TextView>(R.id.calorie).text =
                "Калорийность: " + arguments?.getString("Calorie")
            view.findViewById<TextView>(R.id.ingridient).text =
                "Ингредиенты: " + arguments?.getString("Ingredients")
            view.findViewById<TextView>(R.id.time).text =
                "Время приготовления: " + arguments?.getString("Time")
        }
        return view
    }

}