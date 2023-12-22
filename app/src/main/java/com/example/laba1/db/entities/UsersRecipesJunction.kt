package com.example.laba1.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "Users_recipes_junction",
    primaryKeys = ["idUser", "idRecipe"],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["idUser"],
            childColumns = ["idUser"]
        ),
        ForeignKey(
            entity = Recipe::class,
            parentColumns = ["idRecipe"],
            childColumns = ["idRecipe"]
        )
    ]
)
data class UsersRecipesJunction(
    val idUser: Int,
    val idRecipe: Int
)
