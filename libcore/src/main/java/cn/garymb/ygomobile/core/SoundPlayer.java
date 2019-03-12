package cn.garymb.ygomobile.core;

import android.content.Context;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SoundPlayer {
    private final SoundPool mSoundEffectPool;
    private final Map<String, Integer> mSoundIdMap;
    private boolean mInitSoundEffectPool = false;
    private Context mContext;

    public SoundPlayer(Context context) {
        this.mContext = context;
        mSoundIdMap = new HashMap<String, Integer>();
        mSoundEffectPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
    }

    public void Init() {
        if (mInitSoundEffectPool) return;
        mInitSoundEffectPool = true;
        loadSounds();
    }

    public void playSoundEffect(String path) {
        Integer id = mSoundIdMap.get(path);
        if (id != null) {
            mSoundEffectPool.play(id, 0.5f, 0.5f, 2, 0, 1.0f);
        }
    }

    protected void loadSounds() {
        AssetManager am = mContext.getAssets();
        String[] sounds;
        try {
            sounds = am.list("sound");
            if(sounds != null) {
                for (String sound : sounds) {
                    String path = "sound" + File.separator + sound;
                    mSoundIdMap.put(path, mSoundEffectPool.load(am.openFd(path), 1));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isInitSoundEffectPool() {
        return mInitSoundEffectPool;
    }

    public void Close() {

    }
}
