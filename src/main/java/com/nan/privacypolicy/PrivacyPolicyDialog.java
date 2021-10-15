package com.chewawa.privacypolicy;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AlertDialog;

import com.chewawa.privacypolicy.listener.OnPrivacyPolicyListener;


/**
 * 隐私协议
 * nanfeifei
 * 2018/3/5 17:14
 *
 * @version 4.7.0
 */
public class PrivacyPolicyDialog extends AlertDialog {
    TextView tvAlertTitle;
    TextView tvAlertMessage;
    Button btnCancel;
    Button btnAffirm;
    OnPrivacyPolicyListener listener;
    private String[] policyUrl;
    private String[] policySpan;
    private CharSequence secondTitle;
    private CharSequence secondMessage;
    private CharSequence secondCancel;
    boolean askAgainEnable;

    public PrivacyPolicyDialog(@NonNull Context context) {
        this(context, 0);
    }

    public PrivacyPolicyDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        init();
    }

    protected void init() {
        this.show();
        this.cancel();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_privacy_policy_lay);
        setCanceledOnTouchOutside(false);
        setCancelable(true);
        tvAlertTitle = findViewById(R.id.tv_alert_title);
        tvAlertMessage = findViewById(R.id.tv_alert_message);
        btnAffirm = findViewById(R.id.btn_affirm);
        btnCancel = findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrivacyPolicyDialog.this.cancel();
                if(askAgainEnable){
                    showSecondView();
                }else {
                    if (listener != null) {
                        listener.onPolicyCancel();
                    }
                }
            }
        });
        btnAffirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onPolicyAgree();
                }
                PrivacyPolicyDialog.this.cancel();
            }
        });
    }
    public PrivacyPolicyDialog showDialog(){
        this.show();
        return this;
    }

    /**
     * 设置弹窗标题
     *
     * @param title
     */
    public PrivacyPolicyDialog setAlertTitle(CharSequence title) {
        if (tvAlertTitle == null) {
            return this;
        }
        tvAlertTitle.setText(title);
        return this;
    }
    /**
     * 设置弹窗内容
     *
     * @param message
     */
    public PrivacyPolicyDialog setAlertMessage(CharSequence message) {
        if (tvAlertMessage == null) {
            return this;
        }
        setClickableSpan(message);
        return this;
    }
    /**
     * 设置弹窗标题
     *
     * @param title
     */
    public PrivacyPolicyDialog setSecondAlertTitle(CharSequence title) {
        this.secondTitle = title;
        return this;
    }
    /**
     * 设置弹窗内容
     *
     * @param message
     */
    public PrivacyPolicyDialog setSecondAlertMessage(CharSequence message) {
        this.secondMessage = message;
        return this;
    }
    /**
     * 设置弹窗确认按钮文本
     *
     * @param title
     */
    public PrivacyPolicyDialog setAlertAffirm(CharSequence title) {
        if (btnAffirm == null) {
            return this;
        }
        btnAffirm.setText(title);
        return this;
    }

    /**
     * 设置弹窗取消按钮文本
     *
     * @param title
     */
    public PrivacyPolicyDialog setAlertCancel(CharSequence title) {
        if (btnCancel == null) {
            return this;
        }
        btnCancel.setText(title);
        return this;
    }
    /**
     * 设置弹窗取消按钮文本
     *
     * @param secondCancel
     */
    public PrivacyPolicyDialog setSecondAlertCancel(CharSequence secondCancel) {
        this.secondCancel = secondCancel;
        return this;
    }
    public PrivacyPolicyDialog setPolicyUrl(String... policyUrl) {
        this.policyUrl = policyUrl;
        return this;
    }
    public PrivacyPolicyDialog setPolicySpan(String... policySpan){
        this.policySpan = policySpan;
        return this;
    }

    /**
     * 是否再次询问同意协议
     * @param enable
     * @return
     */
    public PrivacyPolicyDialog setAskAgainEnable(boolean enable){
        this.askAgainEnable = enable;
        return this;
    }
    public PrivacyPolicyDialog setDefaultMessage() {
        setAlertTitle(getContext().getString(R.string.privacy_policy_alert_title));
        setAlertMessage(getContext().getString(R.string.privacy_policy_content
                , getContext().getString(R.string.app_name)));
        this.secondMessage = getContext().getString(R.string.privacy_policy_second_content);
        this.secondCancel = getContext().getString(R.string.privacy_policy_alert_second_cancel);
        return this;
    }
    private void showSecondView(){
        if(!TextUtils.isEmpty(secondTitle)){
            tvAlertTitle.setText(secondTitle);
        }
        setClickableSpan(secondMessage);
        if(!TextUtils.isEmpty(secondCancel)){
            btnCancel.setText(secondCancel);
        }
        show();
        askAgainEnable = false;
    }
    public void setClickableSpan(CharSequence message) {
        String[] spanText;
        if(policySpan != null && policySpan.length>0){
            spanText = policySpan;
        }else {
            spanText = new String[]{getContext().getString(R.string.privacy_policy_span)};
        }
        //需要显示的字串
        SpannableString spannedString = new SpannableString(message);
        if (policyUrl != null && policyUrl.length == spanText.length) {
            int[] indexArray = new int[spanText.length];
            TypedValue typedValue = new  TypedValue();
            getContext().getTheme().resolveAttribute(R.attr.colorAccent, typedValue, true);
            for (int i = 0; i < spanText.length; i++) {
                indexArray[i] = message.toString().indexOf(spanText[i]);
                spannedString.setSpan(new TextClickableSpan(getContext(), policyUrl[i], listener), indexArray[i]
                        , indexArray[i] + spanText[i].length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                spannedString.setSpan(new ForegroundColorSpan(typedValue.data)
                        , indexArray[i], indexArray[i] + spanText[i].length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            //设置点击后的颜色为透明，否则会一直出现高亮
            tvAlertMessage.setHighlightColor(Color.TRANSPARENT);
            //开始响应点击事件
            tvAlertMessage.setMovementMethod(LinkMovementMethod.getInstance());
        }
        tvAlertMessage.setText(spannedString);
    }

    /**
     * 设置确认/取消监听
     */
    public PrivacyPolicyDialog setOnPrivacyPolicyListener(OnPrivacyPolicyListener listener) {
        this.listener = listener;
        return this;
    }

}
