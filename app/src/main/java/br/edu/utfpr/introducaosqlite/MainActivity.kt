package br.edu.utfpr.introducaosqlite

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import br.edu.utfpr.introducaosqlite.database.DatabaseHandler
import br.edu.utfpr.introducaosqlite.entity.Pessoa
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var banco: DatabaseHandler
    private lateinit var etCod: EditText
    private lateinit var etNome: EditText
    private lateinit var etTelefone: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etCod = findViewById( R.id.etCodLanc )
        etNome = findViewById( R.id.etNomeLanc )
        etTelefone = findViewById( R.id.etTelefoneVenc )

        banco = DatabaseHandler( this )

    }

    fun btIncluirOnClick(view: View) {
        val registro = Pessoa( 0, etNome.text.toString(), etTelefone.text.toString())

        banco.incluir( registro )

        Toast.makeText( this, "Inclusão efetuada com sucesso", Toast.LENGTH_LONG ).show()
    }

    fun btListarOnClick(view: View) {
        val saida = banco.listar()

        Toast.makeText( this, saida.toString(), Toast.LENGTH_LONG ).show()
    }

    fun btAlterarOnClick(view: View) {
        val registro = Pessoa( etCod.text.toString().toInt(), etNome.text.toString(), etTelefone.text.toString())

        banco.alterar( registro )

        Toast.makeText( this, "Alteração efetuada com sucesso", Toast.LENGTH_LONG ).show()
    }

    fun btExcluirOnClick(view: View) {
        banco.excluir( etCod.text.toString().toInt()  )

        Toast.makeText( this, "Exclusão efetuada com sucesso", Toast.LENGTH_LONG ).show()
    }

    fun btPesquisarOnClick(view: View) {
        val registro = banco.pesquisar( etCod.text.toString().toInt() )


        if( registro != null  ) {
            etNome.setText( registro.nome )
            etTelefone.setText( registro.telefone )
        } else {
            Toast.makeText( this, "Registro não encontrado", Toast.LENGTH_LONG ).show()
        }
    }
}