package ru.mmcs.pdcheckermobile.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.mmcs.pdcheckermobile.R
import ru.mmcs.pdcheckermobile.ui.main.judge.JudgeFragment
import ru.mmcs.pdcheckermobile.ui.main.student.StudentFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, when(intent.getStringExtra("role")){
                    "judge" -> JudgeFragment.newInstance()
                    "student" -> StudentFragment.newInstance()
                    else -> throw IllegalArgumentException("There are 2 roles only!")
                })
                .commitNow()
        }
    }
}