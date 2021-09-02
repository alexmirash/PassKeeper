package com.mirash.passkeeper.ui.main;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mirash.passkeeper.R;


/**
 * @author Mirash
 */

class CredentialsItemHolder extends RecyclerView.ViewHolder {
    final TextView titleView;
    final TextView linkView;
    final TextView loginView;
    final TextView passwordView;
    final TextView details = null;


    CredentialsItemHolder(View itemView) {
        super(itemView);
        titleView = itemView.findViewById(R.id.item_title_text);
        linkView = itemView.findViewById(R.id.item_link_text);
        loginView = itemView.findViewById(R.id.item_login_text);
        passwordView = itemView.findViewById(R.id.item_password_text);
    }
}
