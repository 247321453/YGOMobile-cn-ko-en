package cn.garymb.ygomobile.ui.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.PersistableBundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.garymb.ygomobile.lite.R;
import cn.garymb.ygomobile.utils.FileLogUtil;


public class BaseActivity extends AppCompatActivity {

    //region 动态权限
    @Override
    public final void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doOnCreate(savedInstanceState);
        checkRequestPermissions();
    }

    /**
     * 初始化view
     */
    protected void doOnCreate(@Nullable Bundle savedInstanceState) {

    }

    private void checkRequestPermissions() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M || getApplicationInfo().targetSdkVersion < Build.VERSION_CODES.M) {
            initData(false);
            return;
        }
        String[] permissions = getRequestPermissions();
        if (permissions != null) {
            List<String> pers = new ArrayList<>();
            for (String permission : permissions) {
                if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, permission)) {
                    pers.add(permission);
                }
            }
            if (pers.size() > 0) {
                String[] ps = new String[pers.size()];
                ActivityCompat.requestPermissions(this, pers.toArray(ps), 0);
                return;
            }
        }
        initData(false);
    }

    protected String[] getRequestPermissions() {
        return null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (int rs : grantResults) {
            if (rs == PackageManager.PERMISSION_DENIED) {
                initData(true);
                return;
            }
        }
        initData(false);
    }

    /**
     * 初始化数据
     *
     * @param denied 是否禁止某个权限
     */
    protected void initData(boolean denied) {

    }
    //endregion

    //region 启动/退出动画
    @Override
    public void finish() {
        super.finish();
        initExitAnim();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        initEnterAnim();
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        initEnterAnim();
    }

    private void initEnterAnim() {
        if (isHasEnterAnim()) {
            overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
        }
    }

    private void initExitAnim() {
        if (isHasEnterAnim()) {
            overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
        }
    }

    protected boolean isHasExitAnim() {
        return true;
    }

    protected boolean isHasEnterAnim() {
        return true;
    }
    //endregion

    public BaseActivity getActivity() {
        return this;
    }

    public BaseActivity getContext() {
        return getActivity();
    }

    protected <T extends View> T $(@IdRes int id) {
        return (T) findViewById(id);
    }

    protected <T extends CheckBox> T bindCheck(int id, CompoundButton.OnCheckedChangeListener clickListener) {
        T t = findViewById(id);
        if (t != null && clickListener != null) {
            t.setOnCheckedChangeListener(clickListener);
        }
        return t;
    }

    //region toolbar
    public void enableBackHome() {
        setupActionBar();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    protected void onBackHome() {
        finish();
    }

    protected @IdRes int getToolbarId() {
        return R.id.toolbar;
    }

    protected void hideToolBar() {
        Toolbar toolbar = $(getToolbarId());
        if (toolbar != null) {
            toolbar.setVisibility(View.GONE);
        }
    }

    private boolean setupToolbar = false;

    protected void setupActionBar() {
        if (setupToolbar) {
            return;
        }
        setupToolbar = true;
        Toolbar toolbar = $(getToolbarId());
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    public void setActivityTitle(@StringRes int title) {
        setActivityTitle(getString(title));
    }

    public void setActivityTitle(String title) {
        if (TextUtils.isEmpty(title)) {
            return;
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }

    public void setActivitySubTitle(String title) {
        if (TextUtils.isEmpty(title)) {
            return;
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setSubtitle(title);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackHome();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //endregion

    //region toast
    private Toast mToast;

    @SuppressLint("ShowToast")
    private Toast makeToast() {
        if (mToast == null) {
            mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        }
        return mToast;
    }

    /**
     * Set how long to show the view for.
     *
     * @see android.widget.Toast#LENGTH_SHORT
     * @see android.widget.Toast#LENGTH_LONG
     */
    public void showToast(int id, int duration) {
        showToast(getString(id), duration);
    }

    public void showToast(CharSequence text) {
        showToast(text, Toast.LENGTH_SHORT);
    }

    public void showToast(int id) {
        showToast(getString(id));
    }

    /**
     * Set how long to show the view for.
     *
     * @see android.widget.Toast#LENGTH_SHORT
     * @see android.widget.Toast#LENGTH_LONG
     */
    public void showToast(CharSequence text, int duration) {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            runOnUiThread(() -> showToast(text, duration));
            return;
        }
        Toast toast = makeToast();
        toast.setText(text);
        toast.setDuration(duration);
        toast.show();
    }
    //endregion
}
