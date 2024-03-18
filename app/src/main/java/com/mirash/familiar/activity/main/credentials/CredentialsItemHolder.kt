package com.mirash.familiar.activity.main.credentials

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mirash.familiar.R
import com.mirash.familiar.databinding.CredentialsItemViewBinding
import com.mirash.familiar.motion.ItemTouchStateCallback

/**
 * @author Mirash
 */
class CredentialsItemHolder(val binding: CredentialsItemViewBinding) : RecyclerView.ViewHolder(binding.root),
    ItemTouchStateCallback {

    override fun onItemSelectStateChanged(selected: Boolean) {
        itemView.setBackgroundColor(
            if (selected) ContextCompat.getColor(
                itemView.context,
                R.color.item_drag
            ) else 0
        )
    }

    override fun onItemClear() {
        itemView.setBackgroundColor(0)
    }
}
