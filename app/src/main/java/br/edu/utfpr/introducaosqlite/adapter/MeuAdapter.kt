package br.edu.utfpr.introducaosqlite.adapter

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView
import br.edu.utfpr.introducaosqlite.MainActivity
import br.edu.utfpr.introducaosqlite.R
import br.edu.utfpr.introducaosqlite.entity.Pessoa

private const val KEY_ID = 0
private const val KEY_NOME = 1
private const val KEY_TELEFONE = 2

class MeuAdapter (val context : Context, val cursor : Cursor) : BaseAdapter() {

    override fun getCount(): Int {
        return cursor.count
    }

    override fun getItem(id: Int): Any {
        cursor.moveToPosition( id )

        val pessoa = Pessoa(
            cursor.getInt(KEY_ID),
            cursor.getString(KEY_NOME),
            cursor.getString(KEY_TELEFONE)
        )

        return pessoa
    }

    override fun getItemId( id : Int): Long {
        cursor.moveToPosition( id )
        return cursor.getInt( 0 ).toLong()
    }

    override fun getView(id: Int, p1: View?, p2: ViewGroup?): View {
        val inflater = context.getSystemService( Context.LAYOUT_INFLATER_SERVICE ) as LayoutInflater
        val elementoLista = inflater.inflate( R.layout.elemento_lista, null )

        val tvNomeElementoLista = elementoLista.findViewById<TextView>( R.id.tvNomeElementoLista )
        val tvTelefoneElementoLista = elementoLista.findViewById<TextView>( R.id.tvTelefoneElementoLista )
        val btEditarElementoLista = elementoLista.findViewById<ImageButton>( R.id.btEditarElementoLista )

        cursor.moveToPosition( id )

        tvNomeElementoLista.text = cursor.getString(KEY_NOME)
        tvTelefoneElementoLista.text = cursor.getString(KEY_TELEFONE)

        btEditarElementoLista.setOnClickListener{
            cursor.moveToPosition( id )

            val intent = Intent( context, MainActivity::class.java )
            intent.putExtra( "cod", cursor.getInt( KEY_ID ) )
            intent.putExtra( "nome", cursor.getString( KEY_NOME ) )
            intent.putExtra( "telefone", cursor.getString( KEY_TELEFONE ) )
            context.startActivity( intent )

        }

        return elementoLista
    }

}