package com.example.budgetlist

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class taulukko_Acti : AppCompatActivity() {

    private lateinit var deletedTapahtuma: Tapahtuma
    private lateinit var tapahtumat : List<Tapahtuma>
    private lateinit var oldtapahtumat : List<Tapahtuma>
    private lateinit var tapahtumaAdapter: TapahtumaAdapter
    private lateinit var linearlayoutManager: LinearLayoutManager
    private lateinit var db : AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_taulukko)

        title = "Taulukko"

        tapahtumat = arrayListOf()

        tapahtumaAdapter = TapahtumaAdapter(tapahtumat)
        linearlayoutManager = LinearLayoutManager(this)

        db = Room.databaseBuilder(this,
        AppDatabase::class.java,
        "tapahtumat").build()

        findViewById<RecyclerView>(R.id.recyclerview).apply {
            adapter = tapahtumaAdapter
            layoutManager = linearlayoutManager
        }

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                deleteTapahtuma(tapahtumat[viewHolder.adapterPosition])
            }

        }

        val swipeHelper = ItemTouchHelper(itemTouchHelper)
        swipeHelper.attachToRecyclerView(findViewById(R.id.recyclerview))

        findViewById<FloatingActionButton>(R.id.lisaaBtn).setOnClickListener {
            val intent = Intent(this, lisaaTapahtuma_Acti::class.java)
            startActivity(intent)
        }


        val bottomNavigation : BottomNavigationView = findViewById(R.id.bottom_navigation_View)
        bottomNavigation.setOnNavigationItemSelectedListener{ item ->

            when(item.itemId){

                R.id.navi_taulukko -> {
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

    private fun fetchAll(){
        GlobalScope.launch {
            tapahtumat = db.tapahtumaDao().getAll()

            runOnUiThread{
                updateNakyma()
                tapahtumaAdapter.setData(tapahtumat)
            }
        }
    }

    private fun updateNakyma(){
        val totalAmount = tapahtumat.map {it.amount}.sum()
        val budjettiAmount = tapahtumat.filter { it.amount>0 }.map{it.amount}.sum()
        val kustannuksetAmount = totalAmount - budjettiAmount

        findViewById<TextView>(R.id.saldo).text = "€ %.2f".format(totalAmount)
        findViewById<TextView>(R.id.budjetti).text = "€ %.2f".format(budjettiAmount)
        findViewById<TextView>(R.id.kustannukset).text = "€ %.2f".format(kustannuksetAmount)
    }

    private fun peruDelete(){
        GlobalScope.launch {
            db.tapahtumaDao().insertAll(deletedTapahtuma)

            tapahtumat = oldtapahtumat

            runOnUiThread{
                tapahtumaAdapter.setData(tapahtumat)
                updateNakyma()

            }
        }
    }

    private fun showSnackbar(){
        val view: View = findViewById<View>(R.id.coordinator)
        val snackbar = Snackbar.make(view, "Tapahtuma poistettu!",Snackbar.LENGTH_LONG)
        snackbar.setAction("Peru"){
            peruDelete()
        }
            .setActionTextColor(ContextCompat.getColor(this, R.color.red))
            .setTextColor(ContextCompat.getColor(this, R.color.white))
            .show()
    }
    
    private fun deleteTapahtuma(tapahtuma: Tapahtuma){
        deletedTapahtuma = tapahtuma
        oldtapahtumat = tapahtumat

        GlobalScope.launch {
            db.tapahtumaDao().delete(tapahtuma)

            tapahtumat = tapahtumat.filter { it.id != tapahtuma.id }
            runOnUiThread{
                updateNakyma()
                tapahtumaAdapter.setData(tapahtumat)
                showSnackbar()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        fetchAll()
    }
}