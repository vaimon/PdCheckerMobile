package ru.mmcs.pdcheckermobile.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.mmcs.pdcheckermobile.R
import ru.mmcs.pdcheckermobile.ui.main.judge.JudgeFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, JudgeFragment.newInstance())
                .commitNow()
        }
    }
}