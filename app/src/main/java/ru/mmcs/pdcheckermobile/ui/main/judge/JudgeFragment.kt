package ru.mmcs.pdcheckermobile.ui.main.judge

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.volley.toolbox.Volley
import ru.mmcs.pdcheckermobile.R
import ru.mmcs.pdcheckermobile.databinding.FragmentJudgeBinding
import ru.mmcs.pdcheckermobile.ui.login.viewmodels.LoginViewModel

class JudgeFragment : Fragment() {
    private lateinit var viewModel: JudgeViewModel
    private var _binding: FragmentJudgeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJudgeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this,
            JudgeViewModel.JudgeViewModelFactory(Volley.newRequestQueue(context))
        ).get(JudgeViewModel::class.java)

        _binding?.viewModel = viewModel
        _binding?.lifecycleOwner = this
        setupBinding()
        return _binding!!.root
    }

    private fun setupBinding() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}