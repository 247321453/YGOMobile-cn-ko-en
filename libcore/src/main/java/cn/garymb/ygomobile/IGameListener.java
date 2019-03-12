package cn.garymb.ygomobile;

import android.app.Activity;

public interface IGameListener {
    void OnGameReady(Activity activity);

    void OnGameStarted(Activity activity);

    void OnGameFullscreen(Activity activity);
}
