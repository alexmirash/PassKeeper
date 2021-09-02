package com.mirash.passkeeper.ui.main;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mirash.passkeeper.R;
import com.mirash.passkeeper.db.Credentials;

import java.util.List;

/**
 * @author Mirash
 */

public class CredentialsAdapter extends RecyclerView.Adapter<CredentialsItemHolder> {
    private List<Credentials> items;
    private final CredentialsItemCallback callback;

    public CredentialsAdapter(@NonNull List<Credentials> items, CredentialsItemCallback callback) {
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
        Credentials item = items.get(position);
        holder.titleView.setText(item.getTitle());
        String link = item.getLink();
        holder.linkView.setVisibility(TextUtils.isEmpty(link) ? View.GONE : View.VISIBLE);
        if (TextUtils.isEmpty(link)) {
            holder.linkView.setVisibility(View.GONE);
            holder.linkView.setOnClickListener(null);
            holder.titleView.setOnClickListener(null);
        } else {
            holder.linkView.setVisibility(View.VISIBLE);
            View.OnClickListener listener = view -> callback.onLinkClick(link);
            holder.linkView.setOnClickListener(listener);
            holder.titleView.setOnClickListener(listener);
        }
        holder.linkView.setText(link);
        holder.loginView.setText(item.getLogin());
        holder.passwordView.setText(item.getPassword());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setItems(List<Credentials> credentials) {
        items = credentials;
        notifyDataSetChanged();
    }
}
