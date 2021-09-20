package com.mirash.familiar.activity.edit;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mirash.familiar.R;
import com.mirash.familiar.activity.pin.view.LoginView;
import com.mirash.familiar.motion.ItemTouchStateCallback;


/**
 * @author Mirash
 */

class CredentialsItemHolder extends RecyclerView.ViewHolder implements ItemTouchStateCallback {
    final View headerView;
    final LoginView loginView;
    final View passwordSectionView;
    final View pinSectionView;
    final TextView titleView;
    final TextView linkView;
    final TextView detailsView;
    final TextView passwordView;
    final TextView pinView;
    final View editButton;
    final View shareButton;
    final CheckBox passwordVisibilityCheckBox;


    CredentialsItemHolder(View itemView) {
        super(itemView);
        headerView = itemView.findViewById(R.id.item_header);
        titleView = headerView.findViewById(R.id.item_title_text);
        linkView = headerView.findViewById(R.id.item_link_text);
        detailsView = headerView.findViewById(R.id.item_details_text);
        loginView = itemView.findViewById(R.id.item_login_section);
        passwordSectionView = itemView.findViewById(R.id.item_password_section);
        passwordView = passwordSectionView.findViewById(R.id.item_password_text);
        pinSectionView = itemView.findViewById(R.id.item_pin_section);
        pinView = pinSectionView.findViewById(R.id.item_pin_text);
        passwordVisibilityCheckBox = itemView.findViewById(R.id.item_password_visibility_checkbox);
        editButton = itemView.findViewById(R.id.item_edit_button);
        shareButton = itemView.findViewById(R.id.item_share_button);
    }

    @Override
    public void onItemSelected() {
        itemView.setBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.item_drag));
    }

    @Override
    public void onItemClear() {
        itemView.setBackgroundColor(0);
    }
}
