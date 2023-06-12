package ru.mmcs.pdcheckermobile.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import ru.mmcs.pdcheckermobile.R
import ru.mmcs.pdcheckermobile.databinding.ActivityMainBinding
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
                StudentFragment.newInstance()
            }
            else -> throw IllegalArgumentException("There are 2 roles only!")
        }

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, targetFragment)
                .commitNow()
        }
    }
}