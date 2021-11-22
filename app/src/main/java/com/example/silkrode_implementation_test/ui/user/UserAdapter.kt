package com.example.silkrode_implementation_test.ui.user

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.silkrode_implementation_test.R
import com.example.silkrode_implementation_test.databinding.UserItemBinding
import com.example.silkrode_implementation_test.model.User
import com.example.silkrode_implementation_test.util.LogUtil

class UserAdapter(private val itemOnClick: ItemOnClick) :
    PagingDataAdapter<User, UserAdapter.UserViewHolder>(diffCallback) {
    private val dTag by lazy { UserViewModel::class.java.simpleName }

    interface ItemOnClick{
        fun onClock(data:User)
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                // Id is unique.
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }
        }
    }

    inner class UserViewHolder(private val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: User) {
            LogUtil.d(dTag, "${data.id}")
            binding.data = data
            binding.onClick = View.OnClickListener {
                itemOnClick.onClock(data)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserViewHolder = UserViewHolder(DataBindingUtil.inflate(LayoutInflater
    .from(parent.context), R.layout.user_item, parent, false))

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = getItem(position)
        // Note that item may be null. ViewHolder must support binding a
        // null item as a placeholder.
        item!!.let { holder.bind(item) }
    }
}
