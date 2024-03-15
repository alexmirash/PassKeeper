package com.mirash.familiar.activity.main.user

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mirash.familiar.R
import com.mirash.familiar.databinding.UserItemViewBinding
import com.mirash.familiar.model.user.UserItem
import com.mirash.familiar.tool.listener.IScrollProvider

/**
 * @author Mirash
 */
@SuppressLint("NotifyDataSetChanged")
class UserAdapter(
    users: List<UserItem>, private val callback: UserItemCallback
) : RecyclerView.Adapter<UserItemHolder>(), IScrollProvider {
    var items: List<UserItem> = users
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override var isScrollToBottom: Boolean = false

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

        if (item.isChecked) {
            holder.binding.userItemText.setTypeface(null, Typeface.BOLD)
            holder.binding.root.foreground = ColorDrawable(
                ContextCompat.getColor(
                    holder.binding.root.context,
                    R.color.user_item_color_selected
                )
            )
        } else {
            holder.binding.userItemText.setTypeface(null, Typeface.NORMAL)
            holder.binding.root.foreground = null
        }
        holder.binding.userItemEditButton.setOnClickListener {
            callback.onEditClick(item)
        }
        holder.binding.root.setOnClickListener {
            callback.onItemClick(item)
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
