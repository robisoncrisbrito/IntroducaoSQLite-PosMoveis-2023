package br.edu.utfpr.introducaosqlite.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import br.edu.utfpr.introducaosqlite.entity.Venda
import java.lang.StringBuilder

class DatabaseHandler( context : Context ) : SQLiteOpenHelper ( context, DATABASE_NAME, null, DATABASE_VERSION ) {

    companion object {
        private val DATABASE_NAME = "dbfile.sqlite"
        private val DATABASE_VERSION = 1
        private val TABLE_NAME = "vendas"
        private val KEY_ID = "_id"
        private val KEY_QTD = "qtd"
        private val KEY_VALOR = "valor"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL( "CREATE TABLE IF NOT EXISTS ${TABLE_NAME} ( ${KEY_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${KEY_QTD} INTEGER, ${KEY_VALOR} REAL )")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL( "DROP TABLE ${TABLE_NAME}" )
        onCreate( db )
    }

    fun incluir( venda : Venda ) {
        val db = this.writableDatabase

        val registro = ContentValues()
        registro.put( KEY_QTD, venda.qtd )
        registro.put( KEY_VALOR, venda.valor )

        db.insert( TABLE_NAME, null, registro )
    }

    fun alterar( venda : Venda ) {
        val db = this.writableDatabase

        val registro = ContentValues()
        registro.put( KEY_QTD, venda.qtd )
        registro.put( KEY_VALOR, venda.valor )

        db.update( TABLE_NAME, registro, "${KEY_ID} = ${venda._id}", null  )
    }

    fun excluir( id : Int ) {
        val db = this.writableDatabase

        db.delete( TABLE_NAME, "${KEY_ID} = ${id}", null  )
    }

    fun pesquisar( id : Int ) : Venda? {
        val db = this.writableDatabase

        val registro = db.query( TABLE_NAME, null,
            "${KEY_ID} = ${id}",
            null, null, null, null );

        if( registro.moveToNext() ) {
            val venda = Venda( id, registro.getInt( 1 ).toString().toInt(), registro.getInt( 2 ).toString().toDouble() )
            return venda
        } else {
            return null
        }

    }

    fun listar() : String {
        val db = this.writableDatabase

        val registro = db.query( TABLE_NAME,
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

        return saida.toString()

    }



}