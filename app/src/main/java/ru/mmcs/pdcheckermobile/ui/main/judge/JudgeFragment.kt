package ru.mmcs.pdcheckermobile.ui.main.judge

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.mmcs.pdcheckermobile.R

class JudgeFragment : Fragment() {

    companion object {
        fun newInstance() = JudgeFragment()
    }

    private lateinit var viewModel: JudgeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(JudgeViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_judge, container, false)
    }

}