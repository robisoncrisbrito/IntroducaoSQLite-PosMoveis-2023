package br.edu.utfpr.introducaosqlite

import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import br.edu.utfpr.introducaosqlite.database.DatabaseHandler
import br.edu.utfpr.introducaosqlite.entity.Pessoa
import java.lang.StringBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var banco: DatabaseHandler

    private lateinit var etCod: EditText
    private lateinit var etNome: EditText
    private lateinit var etTelefone: EditText
    private lateinit var btExcluir: Button
    private lateinit var btPesquisar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etCod = findViewById( R.id.etCodLanc )
        etNome = findViewById( R.id.etNomeLanc )
        etTelefone = findViewById( R.id.etTelefoneVenc )
        btExcluir = findViewById( R.id.btExcluir )
        btPesquisar = findViewById( R.id.btPesquisar )

        if ( intent.getIntExtra( "cod", 0 )  != 0 ) {
            etCod.setText( intent.getIntExtra( "cod", 0 ).toString() )
            etNome.setText( intent.getStringExtra( "nome" ).toString() )
            etTelefone.setText( intent.getStringExtra( "telefone" ).toString() )
        } else {
            btExcluir.visibility = View.GONE
            btPesquisar.visibility = View.GONE
        }

        banco = DatabaseHandler( this )

    }

    fun btAlterarOnClick(view: View) {

        if ( etCod.text.toString().isEmpty() ) {
            val registro = Pessoa( 0, etNome.text.toString(), etTelefone.text.toString())
            banco.incluir( registro )
            Toast.makeText( this, "Inclusão efetuada com sucesso", Toast.LENGTH_LONG ).show()
        } else {
            val registro = Pessoa( etCod.text.toString().toInt(), etNome.text.toString(), etTelefone.text.toString() )
            banco.alterar(registro)
            Toast.makeText(this, "Alteração efetuada com sucesso", Toast.LENGTH_LONG).show()
        }

        finish()
    }

    fun btExcluirOnClick(view: View) {
        banco.excluir( etCod.text.toString().toInt()  )
        Toast.makeText( this, "Exclusão efetuada com sucesso", Toast.LENGTH_LONG ).show()
        finish()
    }

    fun btPesquisarOnClick(view: View) {

        val etCodPesquisar = EditText( this )

        val builder = AlertDialog.Builder( this )
        builder.setTitle( "Código da Pessoa" )
        builder.setView( etCodPesquisar )
        builder.setCancelable( false )
        builder.setNegativeButton( "Fechar", null )
        builder.setPositiveButton( "Pesquisar",
            DialogInterface.OnClickListener{ dialogInterface, i ->
                val registro = banco.pesquisar( etCodPesquisar.text.toString().toInt() )

                if( registro != null  ) {
                    etCod.setText( etCodPesquisar.text.toString() )
                    etNome.setText( registro.nome )
                    etTelefone.setText( registro.telefone )
                } else {
                    Toast.makeText( this, "Registro não encontrado", Toast.LENGTH_LONG ).show()
                }
            }
        )

        builder.show()



    }


}