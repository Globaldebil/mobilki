package com.example.laba1

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.KeyEvent
import android.widget.Button
import android.widget.EditText
import com.example.laba1.db.DataBase
import com.example.laba1.db.entities.User
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private lateinit var loginFlied: EditText
    private lateinit var passwordFlied: EditText
    private lateinit var button: Button
    private lateinit var db: DataBase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db = DataBase.getDatabase(this)
        thread {
            db.getDao()
                .createUser(User(login = "vasya_pupkin@gmail.com", password = "qwerty"))
            db.getDao()
                .createUser(User(login = "vsem_hello@gmail.com", password = "kyky"))
            db.getDao()
                .createUser(User(login = "artem3232@mail.ru", password = "art3m"))
            db.getDao()
                .createUser(User(login = "vovan_V_tanke@gmail.com", password = "KV1"))
            db.getDao()
                .createUser(User(login = "s1mple@gmail.com", password = "123456"))
        }
        loginFlied = findViewById(R.id.login)
        passwordFlied = findViewById(R.id.password)
        button = findViewById(R.id.btn)
        // При обновлении значения логина цвет текста логина и пароля должен быть черным
        loginFlied.setOnKeyListener { v, keyCode, event ->
            loginFlied.setTextColor(Color.BLACK)
            passwordFlied.setTextColor(Color.BLACK)
            true
        }

        // При обновлении значения пароля цвет текста логина и пароля должен быть черным
        passwordFlied.setOnKeyListener { v, keyCode, event ->
            loginFlied.setTextColor(Color.BLACK)
            passwordFlied.setTextColor(Color.BLACK)
            if(event.action == KeyEvent.ACTION_DOWN &&
                (keyCode == KeyEvent.KEYCODE_ENTER))
            {
                login()
            }
            true
        }

        button.setOnClickListener{
            login()
        }
    }

    private fun login(){
        // Ищем подходящего юзера




        thread {
            if (db.getDao().checkUserExists(loginFlied.text.toString())) {
                if (db.getDao()
                        .getUserByLogin(loginFlied.text.toString()).password == passwordFlied.text.toString()
                ) {
                    runOnUiThread {
                        Log.d("Success", "Success login")
                        val intent = Intent(this, BottomNavigationActivity::class.java)
                        startActivity(intent)
                    }
                } else {
                    runOnUiThread {
                        passwordFlied.setTextColor(Color.RED) // Неверный пароль
                    }
                }
            } else {
                runOnUiThread {
                    loginFlied.setTextColor(Color.RED) // Неверный логин
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("login", loginFlied.text.toString())
        outState.putString("password", passwordFlied.text.toString())
    }


    override fun onRestoreInstanceState(
        savedInstanceState: Bundle?,
        persistentState: PersistableBundle?
    ) {
        super.onRestoreInstanceState(savedInstanceState, persistentState)
        loginFlied.setText(savedInstanceState?.getString("login"))
        passwordFlied.setText(savedInstanceState?.getString("password"))

    }
}