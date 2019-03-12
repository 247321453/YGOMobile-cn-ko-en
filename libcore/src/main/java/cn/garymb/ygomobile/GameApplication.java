package cn.garymb.ygomobile;

import android.app.Application;

import cn.garymb.ygomobile.core.IrrlichtBridge;
import cn.garymb.ygomobile.core.SoundPlayer;


public abstract class GameApplication extends Application implements IrrlichtBridge.IrrlichtApplication,IGameListener {

    private static GameApplication sGameApplication;
    private SoundPlayer mSoundPlayer;

    protected SoundPlayer createSoundPlayer() {
        return new SoundPlayer(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mSoundPlayer = createSoundPlayer();
        sGameApplication = this;
    }

    public static GameApplication get() {
        return sGameApplication;
    }

    ///onTerminate 内存少也不释放音效

    /**
     * 游戏启动参数
     */
    public abstract NativeInitOptions getNativeInitOptions();

    public abstract float getSmallerSize();

    /**
     * 宽度比例
     */
    public abstract float getXScale();

    /**
     * 高度比例
     */
    public abstract float getYScale();

    /**
     * 锁定横屏方向
     */
    public abstract boolean isLockSreenOrientation();

    /**
     * @deprecated
     */
    public abstract boolean isSensorRefresh();

    /***
     * 隐藏底部导航栏
     */
    public abstract boolean isImmerSiveMode();


    /**
     * @deprecated
     */
    public boolean canNdkCash() {
        return true;
    }

    /**
     * 初始化音效
     */
    public boolean isInitSoundEffectPool() {
        return mSoundPlayer.isInitSoundEffectPool();
    }

    public void initSoundEffectPool() {
        //init有防止重复初始化
        mSoundPlayer.Init();
    }

    @Override
    public void playSoundEffect(String path) {
        if(mSoundPlayer != null) {
            mSoundPlayer.playSoundEffect(path);
        }
    }
}
