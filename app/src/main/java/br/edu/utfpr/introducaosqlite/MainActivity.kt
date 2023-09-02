package br.edu.utfpr.introducaosqlite

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var banco: SQLiteDatabase
    private lateinit var etCod: EditText
    private lateinit var etQtd: EditText
    private lateinit var etValor: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etCod = findViewById( R.id.etCodLanc )
        etQtd = findViewById( R.id.etQtdLanc )
        etValor = findViewById( R.id.etValorLanc )

        banco = SQLiteDatabase.openOrCreateDatabase(
            this.getDatabasePath( "dbfile.sqlite"),
            null
        )


        banco.execSQL( "CREATE TABLE IF NOT EXISTS vendas ( _id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " qtd INTEGER, valor REAL )" )

    }

    fun btIncluirOnClick(view: View) {
        val registro = ContentValues()
        registro.put( "qtd", etQtd.text.toString() )
        registro.put( "valor", etValor.text.toString() )

        banco.insert( "vendas", null, registro )

        Toast.makeText( this, "Inclusão efetuada com sucesso", Toast.LENGTH_LONG ).show()
    }

    fun btListarOnClick(view: View) {
        val registro = banco.query( "vendas",
            null, null, null, null, null, null );

        var saida = StringBuilder()

        while( registro.moveToNext() ) {
            saida.append( registro.getInt( 0 ) )
            saida.append( " " )
            saida.append( registro.getInt( 1 ) )
            saida.append( " " )
            saida.append( registro.getInt( 2 ) )
            saida.append( "\n" )
        }

        Toast.makeText( this, saida.toString(), Toast.LENGTH_LONG ).show()
    }

    fun btAlterarOnClick(view: View) {
        val registro = ContentValues()
        registro.put( "qtd", etQtd.text.toString() )
        registro.put( "valor", etValor.text.toString() )

        banco.update( "vendas", registro, "_id = ${etCod.text.toString()}", null  )

        Toast.makeText( this, "Alteração efetuada com sucesso", Toast.LENGTH_LONG ).show()
    }

    fun btExcluirOnClick(view: View) {
        banco.delete( "vendas", "_id = ${etCod.text.toString()}", null  )

        Toast.makeText( this, "Alteração efetuada com sucesso", Toast.LENGTH_LONG ).show()
    }

    fun btPesquisarOnClick(view: View) {
        val registro = banco.query( "vendas", null,
            "_id = ${etCod.text.toString()}",
            null, null, null, null );

        if( registro.moveToNext() ) {
            etQtd.setText( registro.getInt( 1 ).toString() )
            etValor.setText( registro.getInt( 2 ).toString() )
        } else {
            Toast.makeText( this, "Registro não encontrado", Toast.LENGTH_LONG ).show()
        }
    }
}