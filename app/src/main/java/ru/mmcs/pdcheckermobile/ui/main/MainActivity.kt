package ru.mmcs.pdcheckermobile.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.mmcs.pdcheckermobile.R
import ru.mmcs.pdcheckermobile.databinding.ActivityMainBinding
import ru.mmcs.pdcheckermobile.ui.login.LoginActivity
import ru.mmcs.pdcheckermobile.ui.main.judge.JudgeFragment
import ru.mmcs.pdcheckermobile.ui.main.student.StudentFragment


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        supportActionBar!!.setDisplayShowTitleEnabled(true)


        val targetFragment: Fragment = when(intent.getStringExtra("role")){
            "judge" -> {
                supportActionBar?.title = "Панель жюри"
                JudgeFragment()
            }
            "student" -> {
                supportActionBar?.title = "Панель студента"
                StudentFragment()
            }
            else -> throw IllegalArgumentException("There are 2 roles only!")
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, targetFragment)
                .commitNow()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.menu_item_logout -> {
                // I have no energy to make it right
                baseContext
                    .getSharedPreferences("ru.mmcs.pdchecker", Context.MODE_PRIVATE)
                    .edit()
                    .remove("jwt")
                    .remove("role")
                    .apply()
                startActivity(Intent(baseContext, LoginActivity::class.java))
                finish()
            }
            else -> {}
        }
        return true
    }
}