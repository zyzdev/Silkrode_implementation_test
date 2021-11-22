package com.example.silkrode_implementation_test.ui.userinfo

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.silkrode_implementation_test.R
import com.example.silkrode_implementation_test.databinding.UserInfoFragmentBinding
import com.example.silkrode_implementation_test.model.ApiHandler
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf


class UserInfoFragment : DialogFragment() {

    companion object {
        const val KEY_NAME = "NAME"
        fun newInstance(name:String) = UserInfoFragment().apply {
            arguments = Bundle().apply { putString(KEY_NAME, name) }
        }
    }

    private val name :String
        get() = arguments?.let {
            it.getString(KEY_NAME) ?: ""
        } ?: ""
    private var _binding: UserInfoFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UserInfoViewModel by inject {
        parametersOf(UserInfoApi(), name)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = UserInfoFragmentBinding.inflate(inflater)
        binding.close.setOnClickListener {
            dismiss()
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.data.observe(viewLifecycleOwner, {data ->
            binding.data = data
        })
    }

    /** The system calls this only when creating the layout in a dialog. */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    override fun getTheme(): Int {
        return R.style.FullScreenDialog
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}