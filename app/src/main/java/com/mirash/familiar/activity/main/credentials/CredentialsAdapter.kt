package com.mirash.familiar.activity.main.credentials

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.mirash.familiar.databinding.CredentialsItemViewBinding
import com.mirash.familiar.model.credentials.CredentialsItem
import com.mirash.familiar.motion.ItemTouchHelperAdapter
import java.util.Collections

/**
 * @author Mirash
 */
@SuppressLint("NotifyDataSetChanged")
class CredentialsAdapter(
    credentialsItems: MutableList<CredentialsItem>, private val callback: CredentialsItemCallback
) : RecyclerView.Adapter<CredentialsItemHolder>(), ItemTouchHelperAdapter {
    var items: MutableList<CredentialsItem> = credentialsItems
        private set
    private var baseItems: MutableList<CredentialsItem> = credentialsItems
    private var filterQuery: String? = null
    val baseItemCount: Int
        get() = baseItems.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CredentialsItemHolder {
        return CredentialsItemHolder(
            CredentialsItemViewBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CredentialsItemHolder, position: Int) {
        val item = items[position]
        //title
        val title = item.title
        holder.binding.itemTitleText.visibility = if (title.isNullOrEmpty()) View.GONE else View.VISIBLE
        holder.binding.itemTitleText.text = title
        //link
        val link = item.link
        if (link.isNullOrEmpty()) {
            holder.binding.itemLinkText.visibility = View.GONE
            holder.binding.itemHeader.setOnClickListener(null)
        } else {
            holder.binding.itemLinkText.visibility = View.VISIBLE
            holder.binding.itemHeader.setOnClickListener { callback.onLinkClick(link) }
        }
        holder.binding.itemLinkText.text = link
        //details
        val details = item.details
        holder.binding.itemDetailsText.visibility = if (details.isNullOrEmpty()) View.GONE else View.VISIBLE
        holder.binding.itemDetailsText.text = details
        //login
        holder.binding.itemLoginSection.setItems(item.login, item.email, item.phone)
        //password
        var password = item.password
        if (password.isNullOrEmpty()) {
            holder.binding.itemPasswordSection.visibility = View.GONE
        } else {
            holder.binding.itemPasswordSection.visibility = View.VISIBLE
            if (!item.isPasswordVisible) {
                val builder = StringBuilder()
                for (i in password.indices) {
                    builder.append("•")
                }
                password = builder.toString()
            }
        }
        holder.binding.itemPasswordText.text = password
        //pin
        var pin = item.pin
        if (pin.isNullOrEmpty()) {
            holder.binding.itemPinSection.visibility = View.GONE
        } else {
            holder.binding.itemPinSection.visibility = View.VISIBLE
            if (!item.isPasswordVisible) {
                val builder = StringBuilder()
                for (i in pin.indices) {
                    builder.append("•")
                }
                pin = builder.toString()
            }
        }
        holder.binding.itemPinText.text = pin
        holder.binding.itemEditButton.setOnClickListener { callback.onEditClick(item) }
        holder.binding.itemPasswordVisibilityCheckbox.setOnCheckedChangeListener(null)
        holder.binding.itemPasswordVisibilityCheckbox.isChecked = item.isPasswordVisible
        holder.binding.itemPasswordVisibilityCheckbox.setOnCheckedChangeListener { _: CompoundButton, checked: Boolean ->
            items[position].isPasswordVisible = checked
            notifyItemChanged(position, checked)
        }
        //drag
        holder.itemView.setOnLongClickListener {
            if (filterQuery.isNullOrEmpty()) callback.onDragStart(holder)
            false
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setItems(credentials: MutableList<CredentialsItem>) {
        baseItems = credentials
        if (filterQuery.isNullOrEmpty()) {
            items = credentials
            notifyDataSetChanged()
        } else {
            filter()
        }
    }

    override fun onItemDismiss(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        Collections.swap(items, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
        callback.onOrderChanged(items)
        return true
    }

    fun setFilterQuery(query: String?) {
        filterQuery = query
        filter()
    }

    private fun filter() {
        val filteredItems: MutableList<CredentialsItem>
        val query = filterQuery
        if (query.isNullOrEmpty()) {
            filteredItems = baseItems
        } else {
            filteredItems = ArrayList(items.size)
            for (item in baseItems) {
                if (item.isAlike(query)) {
                    filteredItems.add(item)
                }
            }
        }
        items = filteredItems
        notifyDataSetChanged()
    }
}
