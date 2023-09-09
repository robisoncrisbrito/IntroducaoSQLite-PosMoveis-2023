package br.edu.utfpr.introducaosqlite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

        banco = DatabaseHandler( this )
        val cursor = banco.listarCursor()

        val adapter = MeuAdapter( this, cursor )

        lvRegistros = findViewById( R.id.lvRegistros )
        lvRegistros.adapter = adapter

    }

    /*fun recuperaArrayString( registros : MutableList<Pessoa> ) : MutableList<String> {

        val listaNome = mutableListOf<String>()

        for ( pessoa in registros ) {
            listaNome.add( pessoa.nome )
        }

        return listaNome
    }*/

}