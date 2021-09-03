package com.mirash.passkeeper.ui.main;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mirash.passkeeper.R;


/**
 * @author Mirash
 */

class CredentialsItemHolder extends RecyclerView.ViewHolder {
    final View headerView;
    final TextView titleView;
    final TextView linkView;
    final TextView loginView;
    final TextView passwordView;
    final View editButton;
    final CheckBox passwordVisibilityCheckBox;
    final TextView details = null;


    CredentialsItemHolder(View itemView) {
        super(itemView);
        headerView = itemView.findViewById(R.id.item_header);
        titleView = headerView.findViewById(R.id.item_title_text);
        linkView = headerView.findViewById(R.id.item_link_text);
        editButton = itemView.findViewById(R.id.item_edit_button);
        loginView = itemView.findViewById(R.id.item_login_text);
        passwordView = itemView.findViewById(R.id.item_password_text);
        passwordVisibilityCheckBox = itemView.findViewById(R.id.item_password_visibility_checkbox);
    }
}
