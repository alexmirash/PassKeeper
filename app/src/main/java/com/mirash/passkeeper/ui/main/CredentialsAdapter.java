package com.mirash.passkeeper.ui.main;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mirash.passkeeper.R;
import com.mirash.passkeeper.model.CredentialsItem;

import java.util.List;

/**
 * @author Mirash
 */

public class CredentialsAdapter extends RecyclerView.Adapter<CredentialsItemHolder> {
    private List<CredentialsItem> items;
    private final CredentialsItemCallback callback;

    public CredentialsAdapter(@NonNull List<CredentialsItem> items, CredentialsItemCallback callback) {
        this.items = items;
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
        holder.loginView.setText(item.getLogin());
        String password = item.getPassword();
        if (!item.isPasswordVisible()) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < password.length(); i++) {
                builder.append("•");
            }
            password = builder.toString();
        }
        holder.passwordView.setText(password);
        int id = item.getId();
        holder.editButton.setOnClickListener(view -> callback.onEditClick(id));
        holder.passwordVisibilityCheckBox.setOnCheckedChangeListener((compoundButton, checked) -> {
            items.get(position).setPasswordVisible(checked);
            notifyItemChanged(position, checked);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setItems(List<CredentialsItem> credentials) {
        items = credentials;
        notifyDataSetChanged();
    }
}
