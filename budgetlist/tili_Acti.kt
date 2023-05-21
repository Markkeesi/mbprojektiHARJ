package com.example.budgetlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView

class tili_Acti : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tili)

        val bottomNavigation : BottomNavigationView = findViewById(R.id.bottom_navigation_View)
        bottomNavigation.setOnNavigationItemSelectedListener{ item ->

            when(item.itemId){

                R.id.navi_taulukko -> {
                    startActivity(Intent(this, taulukko_Acti::class.java))
                    true
                }

                R.id.navi_tili -> {
                    true
                }
                else -> false
            }
        }
    }
}