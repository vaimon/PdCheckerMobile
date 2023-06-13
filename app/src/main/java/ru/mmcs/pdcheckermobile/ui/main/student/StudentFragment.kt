package ru.mmcs.pdcheckermobile.ui.main.student

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.toolbox.Volley
import ru.mmcs.pdcheckermobile.R
import ru.mmcs.pdcheckermobile.databinding.FragmentStudentBinding

class StudentFragment : Fragment() {
    private lateinit var viewModel: StudentViewModel
    private var _binding: FragmentStudentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStudentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this,
            StudentViewModel.StudentViewModelFactory(Volley.newRequestQueue(context))
        ).get(StudentViewModel::class.java)

        _binding?.viewModel = viewModel
        _binding?.lifecycleOwner = this
        setupBinding()
        return _binding!!.root
    }

    private fun setupBinding() {
        _binding?.rvGrades?.apply {
            adapter = viewModel.gradeRvAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}