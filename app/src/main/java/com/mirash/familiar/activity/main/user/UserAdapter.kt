package com.mirash.familiar.activity.main.user

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mirash.familiar.databinding.UserItemViewBinding
import com.mirash.familiar.model.user.UserItem

/**
 * @author Mirash
 */
@SuppressLint("NotifyDataSetChanged")
class UserAdapter(
    users: List<UserItem>, private val callback: UserItemCallback
) : RecyclerView.Adapter<UserItemHolder>() {
    var items: List<UserItem> = users
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserItemHolder {
        return UserItemHolder(
            UserItemViewBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: UserItemHolder, position: Int) {
        val item = items[position]
        holder.binding.userItemText.text = item.name
        holder.binding.userItemText.setTypeface(null, if (item.isChecked) Typeface.BOLD else Typeface.NORMAL)
        holder.binding.userItemEditButton.setOnClickListener {
            callback.onEditClick(item)
        }
        holder.binding.userItemText.setOnClickListener {
            callback.onChecked(item)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setCheckedItem(id: Long) {
        for ((index, item) in items.withIndex()) {
            val check = item.id == id
            if (item.isChecked != check) {
                item.isChecked = check
                notifyItemChanged(index)
            }
        }
    }
}
