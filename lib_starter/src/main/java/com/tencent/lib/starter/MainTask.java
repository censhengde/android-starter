package com.tencent.lib.starter;

/**
 * * Create by 岑胜德
 * * on 2019/4/16.
 * * com.emwit
 **/
public abstract class MainTask extends Task {

    @Override
    public boolean runOnMainThread() {
        return true;
    }
}
