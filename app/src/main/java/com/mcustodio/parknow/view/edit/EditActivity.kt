package com.mcustodio.parknow.view.edit

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.mcustodio.parknow.R

class EditActivity : AppCompatActivity() {

    val parkingId by lazy { intent.getIntExtra(KEY_ID, -1) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigateUp(): Boolean {
        finish()
        return super.onNavigateUp()
    }




    companion object {
        const val KEY_ID = "KEY_ID"

        fun launchNew(context: Context) {
            val intent = Intent(context, EditActivity::class.java)
            context.startActivity(intent)
        }

        fun launchEdit(context: Context, id: Int) {
            val intent = Intent(context, EditActivity::class.java)
            intent.putExtra(KEY_ID, id)
            context.startActivity(intent)
        }
    }
}
