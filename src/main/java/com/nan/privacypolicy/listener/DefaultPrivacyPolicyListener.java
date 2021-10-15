package com.chewawa.privacypolicy.listener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2021/10/13 15:52
 */
public abstract class DefaultPrivacyPolicyListener implements OnPrivacyPolicyListener{
    public Context context;
    public DefaultPrivacyPolicyListener(Context context){
        this.context = context;
    }
    @Override
    public void onPolicyClick(String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

    @Override
    public void onPolicyCancel() {
        if(context instanceof Activity){
            ((Activity)context).finish();
        }
    }
}
