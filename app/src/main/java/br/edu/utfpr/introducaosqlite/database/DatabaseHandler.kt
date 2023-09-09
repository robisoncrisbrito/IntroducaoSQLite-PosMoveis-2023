package br.edu.utfpr.introducaosqlite.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import br.edu.utfpr.introducaosqlite.entity.Pessoa
import java.lang.StringBuilder

class DatabaseHandler( context : Context ) : SQLiteOpenHelper ( context, DATABASE_NAME, null, DATABASE_VERSION ) {

    companion object {
        private val DATABASE_NAME = "banco"
        private val DATABASE_VERSION = 3
        private val TABLE_NAME = "pessoa"
        private val KEY_ID = "_id"
        private val KEY_NOME = "nome"
        private val KEY_TELEFONE = "telefone"
    }



    fun incluir( pessoa : Pessoa ) {
        val db = this.writableDatabase

        val registro = ContentValues()
        registro.put( KEY_NOME, pessoa.nome )
        registro.put( KEY_TELEFONE, pessoa.telefone )

        db.insert( TABLE_NAME, null, registro )
    }

    fun alterar( pessoa : Pessoa ) {
        val db = this.writableDatabase

        val registro = ContentValues()
        registro.put( KEY_NOME, pessoa.nome )
        registro.put( KEY_TELEFONE, pessoa.telefone )

        db.update( TABLE_NAME, registro, "${KEY_ID} = ${pessoa._id}", null  )
    }

    fun excluir( id : Int ) {
        val db = this.writableDatabase

        db.delete( TABLE_NAME, "${KEY_ID} = ${id}", null  )
    }

    fun pesquisar( id : Int ) : Pessoa? {
        val db = this.writableDatabase

        val registro = db.query( TABLE_NAME, null,
            "${KEY_ID} = ${id}",
            null, null, null, null );

        if( registro.moveToNext() ) {
            val pessoa = Pessoa( id, registro.getString( 1 ).toString(), registro.getInt( 2 ).toString() )
            return pessoa
        } else {
            return null
        }

    }

    fun listar() : MutableList<Pessoa> {

        val db = this.writableDatabase

        val registro = db.query( TABLE_NAME,
            null, null, null, null, null, null )

        var saida = StringBuilder()

        val registros = mutableListOf<Pessoa>()

        while( registro.moveToNext() ) {
            val pessoa = Pessoa( registro.getInt( 0 ), registro.getString( 1 ), registro.getString( 2 ) )
            registros.add( pessoa )
        }

        return registros

    }

    fun listarCursor() : Cursor {
        val db = this.writableDatabase

        val registros = db.query( TABLE_NAME,
            null, null, null, null, null, null )

        return registros

    }

    override fun onCreate(db: SQLiteDatabase?) {

        db?.execSQL( "CREATE TABLE IF NOT EXISTS ${TABLE_NAME} ( ${KEY_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${KEY_NOME} TEXT, ${KEY_TELEFONE} TEXT )")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL( "DROP TABLE ${TABLE_NAME}" )
        onCreate( db )
    }

}