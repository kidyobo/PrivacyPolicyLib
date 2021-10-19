package com.nan.privacypolicy;

import android.content.Context;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.nan.privacypolicy.listener.OnPrivacyPolicyListener;


/**
 * nanfeifei
 * 2021/9/27 15:27
 *
 * @version 4.8.0
 */
public class TextClickableSpan extends ClickableSpan {
    private Context context;
    private String url;
    OnPrivacyPolicyListener listener;
    public TextClickableSpan(Context context, String url){
        this.url = url;
        this.context = context;
    }
    public TextClickableSpan(Context context, String url ,OnPrivacyPolicyListener listener){
        this.url = url;
        this.context = context;
        this.listener = listener;
    }
    @Override
    public void onClick(View view) {
        if(listener != null){
            listener.onPolicyClick(url);
        }
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        //点击事件去掉下划线
        super.updateDrawState(ds);
        ds.setColor(ContextCompat.getColor(context, R.color.blue));
        ds.setUnderlineText(false);
        ds.clearShadowLayer();
    }
}
