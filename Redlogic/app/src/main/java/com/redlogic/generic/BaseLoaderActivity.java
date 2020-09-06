package com.redlogic.generic;

import android.content.Intent;
import android.net.Uri;
import android.os.CountDownTimer;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akexorcist.roundcornerprogressbar.CenteredRoundCornerProgressBar;
import com.redlogic.R;
import com.redlogic.dashboard.driver.notification.NotificationActivity;
import com.redlogic.dashboard.driver.schedule.ScheduleActivity;
import com.redlogic.databinding.ItemMenuItemBinding;
import com.redlogic.language.LanguageActivity;
import com.redlogic.login.LoginActivity;
import com.redlogic.utils.PrefConstants;
import com.redlogic.utils.popup.Popup;

import net.idik.lib.slimadapter.SlimAdapter;
import net.idik.lib.slimadapter.SlimInjector;
import net.idik.lib.slimadapter.viewinjector.IViewInjector;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Sarath on 9/12/18.
 */
public class BaseLoaderActivity extends BaseActivity {

    protected ViewDataBinding viewDataBinding;
    private View dialogContent;
    private ViewGroup subActivityContent;
    private View toolbar, menu;
    private ImageView imBack;
    private TextView tvTitle;
    private View imNotification;
    private View imCal;
    private RecyclerView recyclerView;
    private View relContent;
    public static String sosMobileNumber="";

    @Override
    public void setContentView(int view) {
        ViewGroup fullLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.activity_base_relative, null);
        subActivityContent = fullLayout.findViewById(R.id.contentFrame);
        relContent = fullLayout.findViewById(R.id.relContent);
        dialogContent = fullLayout.findViewById(R.id.contentLoading);
        toolbar = fullLayout.findViewById(R.id.include);
        recyclerView = fullLayout.findViewById(R.id.recyclerViewMenu);
        menu = fullLayout.findViewById(R.id.include2);
        imBack = fullLayout.findViewById(R.id.imBack);
        imNotification = fullLayout.findViewById(R.id.imNotification);
        imCal = fullLayout.findViewById(R.id.imCal);
        tvTitle = fullLayout.findViewById(R.id.tvTitle);
        getLayoutInflater().inflate(view, subActivityContent, true);
        viewDataBinding = DataBindingUtil.bind(subActivityContent.getChildAt(0));
        setAdapter();
        super.setContentView(fullLayout);
    }


    public void hideActionBar() {
        toolbar.setVisibility(View.GONE);
    }

    public void hideBack() {
        imBack.setVisibility(View.GONE);
    }

    public void setBurger() {
        imBack.setImageResource(R.mipmap.hambergmenu);
    }

    public void hideNotification() {
        imNotification.setVisibility(View.GONE);
    }

    public void hideSchedules() {
        imCal.setVisibility(View.GONE);
    }

    public void setTitle(String title) {
        tvTitle.setText(title);
    }

    public void showDialog() {
        dialogContent.setVisibility(View.VISIBLE);
        relContent.setVisibility(View.GONE);
        timer.start();
    }

    public void hideDialog() {
        dialogContent.setVisibility(View.GONE);
        relContent.setVisibility(View.VISIBLE);
        timer.cancel();
    }

    public void toggleMenu() {
        if (menu.getVisibility() == View.VISIBLE) {
            menu.setVisibility(View.GONE);
        } else {
            menu.setVisibility(View.VISIBLE);
        }
    }

    public void call(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        startActivity(intent);
    }

    private void logout() {
        Popup.showPopup(this, "Would you like to logout?"
                , "Logout", isPress -> {
                    appPrefes.saveData(PrefConstants.token, "");
                    LoginActivity.startClear(this);
                });
    }

    public void onSos(View view) {
        if (!sosMobileNumber.isEmpty()){
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", sosMobileNumber, null));
            startActivity(intent);
        }else {
            showToast("SOS phone number not loaded");
        }
    }

    public void onCalender(View view) {
        ScheduleActivity.start(this);
    }

    public void onNotification(View view) {
        NotificationActivity.start(this);
    }

    private void setAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SlimAdapter slimAdapter = SlimAdapter.create()
                .register(R.layout.item_menu_item, new SlimInjector<String>() {

                    @Override
                    public void onInject(@NonNull final String data, @NonNull IViewInjector injector) {
                        ItemMenuItemBinding mBinding = DataBindingUtil.bind(injector.findViewById(R.id.liItem));
                        if (mBinding == null) return;
                        mBinding.tvName.setText(data);
                        if (data.equals("Logout")) {
                            mBinding.viewBottom1.setVisibility(View.GONE);
                            mBinding.viewBottom2.setVisibility(View.VISIBLE);
                            mBinding.viewTop.setVisibility(View.VISIBLE);
                        }
                        mBinding.liItem.setOnClickListener(v -> {
                            toggleMenu();
                            switch (data) {
                                case "Profiles":

                                    break;
                                case "Logout":
                                    logout();
                                    break;
                                    case "Dashboard":
                                    dashBoard();
                                    break;

                                    case "Schedules":
                                        schedue();
                                    break;

                                    case "Settings":
//                                        language();
                                    break;
                            }
                        });
                    }
                })
                .attachTo(recyclerView);
        List<String> list = new ArrayList<>();
        list.add("Profiles");
        list.add("Dashboard");
        list.add("Settings");
        list.add("Schedules");
        list.add("Logout");
        slimAdapter.updateData(list);
    }

    private void language() {
        LanguageActivity.start(this);
    }

    private void schedue() {
        ScheduleActivity.start(this);
    }

    private void dashBoard() {
//        DashboardActivity.start(this);
    }

    public void goToLogin() {
        appPrefes.saveData(PrefConstants.token, "");
        LoginActivity.startClear(this);
    }

    private CountDownTimer timer = new CountDownTimer(1000, 80) {
        @Override
        public void onTick(long l) {
            CenteredRoundCornerProgressBar progressBar = findViewById(R.id.progressBar);
                progressBar.setProgress(progressBar.getProgress() + 5);
        }

        @Override
        public void onFinish() {

        }
    };
}
