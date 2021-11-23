package com.example.silkrode_implementation_test.ui.mine

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.silkrode_implementation_test.databinding.MineFragmentBinding
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MineFragment : Fragment() {

    private var _binding: MineFragmentBinding? = null
    private val viewModel: MineViewModel by inject { parametersOf(MineApi()) }

    private val binding: MineFragmentBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MineFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.data.observe(viewLifecycleOwner, {
            binding.data = it
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}