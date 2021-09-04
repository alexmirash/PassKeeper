package com.mirash.passkeeper.ui.main;

import android.graphics.Color;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mirash.passkeeper.R;
import com.mirash.passkeeper.drag.ItemTouchHelperViewHolder;


/**
 * @author Mirash
 */

class CredentialsItemHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder {
    final View headerView;
    final TextView titleView;
    final TextView linkView;
    final TextView loginView;
    final View passwordSectionView;
    final TextView passwordView;
    final View pinSectionView;
    final TextView pinView;
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
        passwordSectionView = itemView.findViewById(R.id.item_password_section);
        passwordView = passwordSectionView.findViewById(R.id.item_password_text);
        pinSectionView = itemView.findViewById(R.id.item_pin_section);
        pinView = pinSectionView.findViewById(R.id.item_pin_text);
        passwordVisibilityCheckBox = itemView.findViewById(R.id.item_password_visibility_checkbox);
    }

    @Override
    public void onItemSelected() {
        itemView.setBackgroundColor(Color.LTGRAY);
    }

    @Override
    public void onItemClear() {
        itemView.setBackgroundColor(0);
    }
}
