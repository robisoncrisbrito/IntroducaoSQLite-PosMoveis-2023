package br.edu.utfpr.introducaosqlite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import br.edu.utfpr.introducaosqlite.adapter.MeuAdapter
import br.edu.utfpr.introducaosqlite.database.DatabaseHandler
import br.edu.utfpr.introducaosqlite.entity.Pessoa

class ListarActivity : AppCompatActivity() {

    private lateinit var banco: DatabaseHandler

    private lateinit var lvRegistros : ListView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listar)

        lvRegistros = findViewById( R.id.lvRegistros )

        banco = DatabaseHandler( this )

    }

    override fun onStart() {
        super.onStart()
        val cursor = banco.listarCursor()
        val adapter = MeuAdapter( this, cursor )
        lvRegistros.adapter = adapter
    }

    fun btIncluirOnClick(view: View) {
        val intent = Intent( this, MainActivity::class.java )
        startActivity( intent )
    }

    /*fun recuperaArrayString( registros : MutableList<Pessoa> ) : MutableList<String> {

        val listaNome = mutableListOf<String>()

        for ( pessoa in registros ) {
            listaNome.add( pessoa.nome )
        }

        return listaNome
    }*/

}