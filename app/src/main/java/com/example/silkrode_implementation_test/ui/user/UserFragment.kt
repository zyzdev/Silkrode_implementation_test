package com.example.silkrode_implementation_test.ui.user

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.silkrode_implementation_test.databinding.HomeFragmentBinding
import com.example.silkrode_implementation_test.model.User
import com.example.silkrode_implementation_test.ui.userinfo.UserInfoFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class UserFragment : Fragment() {

    private val dTag by lazy { UserViewModel::class.java.simpleName }

    private val homeViewModel: UserViewModel by inject()
    private var _binding: HomeFragmentBinding? = null
    private var isInitState = true
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val itemOnClick = object: UserAdapter.ItemOnClick {
        override fun onClock(data: User) {
            if(isInitState) return
            val transaction: FragmentTransaction =
                requireActivity().supportFragmentManager.beginTransaction()
            UserInfoFragment.newInstance(data.login!!).apply {
                show(transaction, "${data.id}")
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val userAdapter = UserAdapter(itemOnClick)
        binding.list.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = userAdapter
            userAdapter.addLoadStateListener {loadStates ->
                Log.d(dTag, "${loadStates.source}")
                val isLoading = loadStates.refresh is LoadState.Loading || loadStates.append is LoadState.Loading
                binding.loading = isLoading
                if(!isLoading && isInitState) isInitState = false
            }
        }

        lifecycleScope.launch {
            userAdapter.submitData(PagingData.from(listOf(User(), User(), User(),User(), User(), User(), User(),User(),User())))
            homeViewModel.users.collectLatest { pagedData ->
                userAdapter.submitData(pagedData)
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}