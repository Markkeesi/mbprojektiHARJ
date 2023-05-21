package com.example.budgetlist

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import androidx.room.Room
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class lisaaTapahtuma_Acti : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lisaa_tapahtuma)

        findViewById<ConstraintLayout>(R.id.rootView).setOnClickListener {
            this.window.decorView.clearFocus()

            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }

        findViewById<TextInputEditText>(R.id.otsikkoInput).addTextChangedListener{
            if (it!!.isNotEmpty())
                findViewById<TextInputLayout>(R.id.otsikkoLayout).error = null
        }

        findViewById<TextInputEditText>(R.id.maaraInput).addTextChangedListener{
            if (it!!.isNotEmpty())
                findViewById<TextInputLayout>(R.id.maaraLayout).error = null
        }

        findViewById<Button>(R.id.lisaaTapahtumaBtn).setOnClickListener {
            val otsikko = findViewById<TextInputEditText>(R.id.otsikkoInput).text.toString()
            val kuvaus = findViewById<TextInputEditText>(R.id.kuvausInput).text.toString()
            val maara = findViewById<TextInputEditText>(R.id.maaraInput).text.toString().toDoubleOrNull()

            if(otsikko.isEmpty())
                findViewById<TextInputLayout>(R.id.otsikkoLayout).error = "Anna otsikko"

            else if(maara == null)
                findViewById<TextInputLayout>(R.id.maaraLayout).error = "Syötä määrä"

            else{
                val tapahtuma = Tapahtuma(0, otsikko, maara, kuvaus)
                insert(tapahtuma)
            }

        }

        findViewById<ImageButton>(R.id.closeBtn).setOnClickListener {
            finish()
        }
    }

    private fun insert(tapahtuma: Tapahtuma){
        val db = Room.databaseBuilder(this,
            AppDatabase::class.java,
            "tapahtumat").build()

        GlobalScope.launch {
            db.tapahtumaDao().insertAll(tapahtuma)
            finish()
        }
    }

}