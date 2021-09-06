package com.mirash.passkeeper.ui.main;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.mirash.passkeeper.R;
import com.mirash.passkeeper.drag.ItemTouchHelperAdapter;
import com.mirash.passkeeper.model.CredentialsItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Mirash
 */

@SuppressLint("NotifyDataSetChanged")
public class CredentialsAdapter extends RecyclerView.Adapter<CredentialsItemHolder> implements ItemTouchHelperAdapter {
    private List<CredentialsItem> items;
    private List<CredentialsItem> baseItems;
    private final CredentialsItemCallback callback;
    private String filterQuery;

    public CredentialsAdapter(@NonNull List<CredentialsItem> items, CredentialsItemCallback callback) {
        this.items = items;
        this.baseItems = items;
        this.callback = callback;
    }

    @NonNull
    @Override
    public CredentialsItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowItem = LayoutInflater.from(parent.getContext()).inflate(getListItemLayoutId(), parent, false);
        return new CredentialsItemHolder(rowItem);
    }

    protected int getListItemLayoutId() {
        return R.layout.credentials_item_view;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull CredentialsItemHolder holder, int position) {
        CredentialsItem item = items.get(position);
        String title = item.getTitle();
        if (TextUtils.isEmpty(title)) {
            holder.titleView.setVisibility(View.GONE);
        } else {
            holder.titleView.setVisibility(View.VISIBLE);
        }
        holder.titleView.setText(title);
        String link = item.getLink();
        if (TextUtils.isEmpty(link)) {
            holder.linkView.setVisibility(View.GONE);
            holder.headerView.setOnClickListener(null);
        } else {
            holder.linkView.setVisibility(View.VISIBLE);
            holder.headerView.setOnClickListener(view -> callback.onLinkClick(link));
        }
        holder.linkView.setText(link);
        String details = item.getDetails();
        if (TextUtils.isEmpty(details)) {
            holder.detailsView.setVisibility(View.GONE);
        } else {
            holder.detailsView.setVisibility(View.VISIBLE);
        }
        holder.detailsView.setText(details);
        holder.loginView.setText(item.getLogin());
        String password = item.getPassword();
        if (TextUtils.isEmpty(password)) {
            holder.passwordSectionView.setVisibility(View.GONE);
        } else {
            holder.passwordSectionView.setVisibility(View.VISIBLE);
            if (!item.isPasswordVisible()) {
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < password.length(); i++) {
                    builder.append("•");
                }
                password = builder.toString();
            }
        }
        holder.passwordView.setText(password);
        String pin = item.getPin();
        if (TextUtils.isEmpty(pin)) {
            holder.pinSectionView.setVisibility(View.GONE);
        } else {
            holder.pinSectionView.setVisibility(View.VISIBLE);
            if (!item.isPasswordVisible()) {
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < pin.length(); i++) {
                    builder.append("•");
                }
                pin = builder.toString();
            }
        }
        holder.pinView.setText(pin);
        holder.editButton.setOnClickListener(view -> callback.onEditClick(item));
        holder.passwordVisibilityCheckBox.setOnCheckedChangeListener((compoundButton, checked) -> {
            items.get(position).setPasswordVisible(checked);
            notifyItemChanged(position, checked);
        });
        holder.shareButton.setOnClickListener(view -> callback.onShare(item));
        //drag
        holder.itemView.setOnLongClickListener(view -> {
            if (isFilterEmpty()) callback.onDragStart(holder);
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public int getBaseItemCount() {
        return baseItems.size();
    }

    public void setItems(List<CredentialsItem> credentials) {
        baseItems = credentials;
        if (isFilterEmpty()) {
            items = credentials;
            notifyDataSetChanged();
        } else {
            filter();
        }
    }

    @Override
    public void onItemDismiss(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(items, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        callback.onOrderChanged(items);
        return true;
    }

    public List<CredentialsItem> getItems() {
        return items;
    }

    public void setFilterQuery(@Nullable String query) {
        filterQuery = query;
        filter();
    }

    private boolean isFilterEmpty() {
        return TextUtils.isEmpty(filterQuery);
    }

    private void filter() {
        List<CredentialsItem> filteredItems;
        if (isFilterEmpty()) {
            filteredItems = baseItems;
        } else {
            filteredItems = new ArrayList<>(items.size());
            for (CredentialsItem item : baseItems) {
                if (item.isAlike(filterQuery)) {
                    filteredItems.add(item);
                }
            }
        }
        items = filteredItems;
        notifyDataSetChanged();
    }
}
