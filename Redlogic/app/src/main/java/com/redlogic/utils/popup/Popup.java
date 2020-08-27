package com.redlogic.utils.popup;

import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.redlogic.R;
import com.redlogic.databinding.DialogCustomPopupBinding;
import com.redlogic.utils.image.ImageUtils;

public class Popup {

    public static void showPopup(AppCompatActivity context, String text, String title, final OnClipPopup onClipPopup) {
        AlertDialog.Builder builder;
        LayoutInflater inflater = context.getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.dialog_custom_popup, null);
        DialogCustomPopupBinding mBinding = DataBindingUtil.bind(dialogLayout.findViewById(R.id.liItems));
        if (mBinding == null) return;
        builder = new AlertDialog.Builder(context, R.style.Theme_Dialog);
        ImageUtils.setRoundedBackground(context, Color.WHITE, 8, mBinding.liItem);
        mBinding.tvMessage.setText(text);
        mBinding.tvTitle.setText(title);
        AlertDialog dialog = builder.create();
        setDialogTheme(dialog);
        dialog.setView(dialogLayout);
        dialog.show();
        mBinding.tvOk.setOnClickListener(view -> {
            if (onClipPopup != null)
                onClipPopup.onSelection(true);
            dialog.dismiss();
        });
        mBinding.tvCancel.setOnClickListener(view -> dialog.dismiss());
    }

    private static void setDialogTheme(AlertDialog dialog) {
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(lp);
    }

    public interface OnClipPopup {
        void onSelection(boolean isPress);
    }
}
