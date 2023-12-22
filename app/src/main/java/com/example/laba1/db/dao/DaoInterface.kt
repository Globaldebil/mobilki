package com.example.laba1.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.laba1.db.entities.Recipe
import com.example.laba1.db.entities.User
import com.example.laba1.db.entities.UsersRecipesJunction

@Dao
interface DaoInterface {
    @Query("SELECT * FROM recipes")
    fun getAllRecipes(): List<Recipe>
    @Query("SELECT COUNT(*) FROM recipes")
    fun getCountRecipes(): Int
    @Insert
    fun insertRecipes(r: Recipe)
    @Insert
    fun createUser(newUser: User)
    @Query("SELECT * FROM users WHERE login = :login")
    fun getUserByLogin(login: String): User
    @Query("SELECT * FROM users WHERE idUser = :id")
    fun getUserById(id: Int): User
    @Query("SELECT COUNT(*) FROM users WHERE login = :login")
    fun checkUserExists(login: String): Boolean
    @Query("SELECT recipes.* FROM recipes " +
            "INNER JOIN users_recipes_junction ON recipes.idRecipe = users_recipes_junction.idRecipe " +
            "WHERE users_recipes_junction.idUser = :userId")
    fun getFavoriteRecipesByUserId(userId: Int): List<Recipe>
    @Insert
    fun insertToFavoriteRecipes(r: UsersRecipesJunction)
    @Query("SELECT COUNT(*) FROM users_recipes_junction WHERE idUser = :userId AND idRecipe = :recipeId")
    fun checkFavoriteRecipeExist(userId: Int, recipeId: Int): Boolean
    @Query("DELETE FROM users_recipes_junction WHERE idUser = :userId AND idRecipe = :recipeId")
    fun deleteFavoriteRecipesByUserId(userId: Int, recipeId: Int)
}