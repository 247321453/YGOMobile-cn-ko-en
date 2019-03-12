package cn.garymb.ygomobile.ui.activities;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.Toast;

import cn.garymb.ygomobile.AppsSettings;
import cn.garymb.ygomobile.YGOStarter;
import cn.garymb.ygomobile.lite.R;
import cn.garymb.ygomobile.ui.home.MainActivity;

public class LogoActivity extends BaseActivity {
    Handler handler;
    Runnable runnable;

    @Override
    protected String[] getRequestPermissions() {
        return new String[]{//
                // Manifest.permission.RECORD_AUDIO,
                Manifest.permission.READ_PHONE_STATE,
//            Manifest.permission.SYSTEM_ALERT_WINDOW,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,};
    }

    @Override
    protected void doOnCreate(@Nullable Bundle savedInstanceState) {
        super.doOnCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        if (AppsSettings.get().isOnlyGame()) {
            YGOStarter.startGame(this, null);
            finish();
            return;
        }
        if (!isTaskRoot()) {
            finish();
            return;
        }

    }

    @Override
    protected void initData(boolean denied) {
        super.initData(denied);
        if(denied){
            finish();
        }else{
            //   if (BuildConfig.DEBUG) {
            //       startActivity(new Intent(LogoActivity.this, MainActivity.class));
            //       finish();
            //   } else {
            handler = new Handler();
            runnable = new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(LogoActivity.this, MainActivity.class));
                    finish();
                }
            };
            handler.postDelayed(runnable, 1000);
            Toast.makeText(LogoActivity.this, R.string.logo_text, Toast.LENGTH_SHORT).show();
            //   }
        }
    }

  /*  @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        if(BuildConfig.DEBUG){
            startActivity(new Intent(LogoActivity.this, MainActivity.class));
            finish();
            return;
        }
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_logo);
        ImageView image = (ImageView) $(R.id.logo);
        AlphaAnimation anim = new AlphaAnimation(0.1f, 1.0f);
        anim.setDuration(Constants.LOG_TIME);
        anim.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation arg0) {
                Toast.makeText(LogoActivity.this, R.string.logo_text, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {

            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                startActivity(new Intent(LogoActivity.this, MainActivity.class));
                finish();
            }
        });
        image.setAnimation(anim);
        anim.start();
    }*/
}

