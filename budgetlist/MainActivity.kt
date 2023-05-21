package com.example.budgetlist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_taulukko)

        val bottomNavigation : BottomNavigationView = findViewById(R.id.bottom_navigation_View)
        val menuItem = bottomNavigation.menu.findItem(R.id.navi_taulukko)

        menuItem.isChecked = true
        bottomNavigation.setOnNavigationItemSelectedListener{ item ->

            when(item.itemId){

                R.id.navi_taulukko -> {
                    startActivity(Intent(this, taulukko_Acti::class.java))
                    true
                }

                R.id.navi_tili -> {
                    startActivity(Intent(this, tili_Acti::class.java))
                    true
                }
                else -> false
            }
        }
    }
}