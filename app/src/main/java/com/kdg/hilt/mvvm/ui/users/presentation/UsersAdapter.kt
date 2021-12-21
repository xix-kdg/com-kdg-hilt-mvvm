package com.kdg.hilt.mvvm.ui.users.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kdg.hilt.mvvm.R
import com.kdg.hilt.mvvm.data.remote.domain.model.User
import com.kdg.hilt.mvvm.databinding.ItemUserBinding
import com.kdg.hilt.mvvm.ui.extension.loadImage

class UsersAdapter(
    private val userList: ArrayList<User>,
    private val onUserClickListener: OnUserClickListener
) : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersAdapter.ViewHolder {
        return ViewHolder(
            ItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UsersAdapter.ViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun addItems(list: List<User>) {
        val insertIndex = userList.size
        userList.addAll(insertIndex, list)
        notifyItemRangeChanged(insertIndex, list.size)
    }

    fun clearItems() {
        userList.clear()
    }

    interface OnUserClickListener {
        fun onUserClick(login: String)
    }

    inner class ViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.imageUser.loadImage(user.avatarUrl, R.drawable.ic_default_avatar)
            binding.textUserName.text = user.login.replaceFirstChar { it.uppercase() }
            binding.textUserDetail.text =
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris purus lorem, pretium tempus augue vitae, congue faucibus ante."

            binding.layoutContent.setOnClickListener { onUserClickListener.onUserClick(user.login) }
        }
    }
}
