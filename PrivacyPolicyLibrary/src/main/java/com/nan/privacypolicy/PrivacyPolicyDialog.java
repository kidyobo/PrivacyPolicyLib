package com.nan.privacypolicy;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
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

import com.nan.privacypolicy.listener.OnPrivacyPolicyListener;

import java.util.ArrayList;
import java.util.List;


/**
 * 隐私协议
 * nanfeifei
 * 2018/3/5 17:14
 *
 * @version 4.7.0
 */
public class PrivacyPolicyDialog extends AlertDialog {

    Button btnCancel;
    Button btnAffirm;
    private TextView tvAlertTitle;
    private TextView tvAlertMessage;
    private OnPrivacyPolicyListener listener;
    private String[] policyUrl;
    private String[] policySpan;
    private boolean askAgainEnable;
    private CharSequence title;
    private CharSequence message;
    private CharSequence affirmText;
    private CharSequence cancelText;
    private CharSequence secondTitle;
    private CharSequence secondMessage;
    private CharSequence secondCancelText;
    private PrivacyPolicyDialog(@NonNull Context context) {
        this(context, 0);
    }

    private PrivacyPolicyDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected void init(Builder builder){
        this.title = builder.title;
        this.message = builder.message;
        this.secondTitle = builder.secondTitle;
        this.secondMessage = builder.secondMessage;
        this.secondCancelText = builder.secondCancelText;
        this.askAgainEnable = builder.askAgainEnable;
        this.policyUrl = builder.policyUrl;
        this.policySpan = builder.policySpan;
        this.affirmText = builder.affirmText;
        this.cancelText = builder.cancelText;
        this.listener = builder.listener;
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
        setData();
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
    private void setData(){
        setAlertTitle(title);
        setAlertMessage(message);
        setAlertAffirm(affirmText);
        setAlertCancel(cancelText);
    }

    /**
     * 设置弹窗标题
     *
     * @param title
     */
    private void setAlertTitle(CharSequence title) {
        if (tvAlertTitle == null||TextUtils.isEmpty(title)) {
            return;
        }
        tvAlertTitle.setText(title);
    }
    /**
     * 设置弹窗内容
     *
     * @param message
     */
    private void setAlertMessage(CharSequence message) {
        if (tvAlertMessage == null||TextUtils.isEmpty(message)) {
            return;
        }
        setClickableSpan(message);
    }
    /**
     * 设置弹窗确认按钮文本
     *
     * @param title
     */
    private void setAlertAffirm(CharSequence title) {
        if (btnAffirm == null||TextUtils.isEmpty(title)) {
            return;
        }
        btnAffirm.setText(title);
    }

    /**
     * 设置弹窗取消按钮文本
     *
     * @param title
     */
    private void setAlertCancel(CharSequence title) {
        if (btnCancel == null||TextUtils.isEmpty(title)) {
            return;
        }
        btnCancel.setText(title);
    }
    private void showSecondView(){
        if(!TextUtils.isEmpty(secondTitle)){
            tvAlertTitle.setText(secondTitle);
        }
        setClickableSpan(secondMessage);
        if(!TextUtils.isEmpty(secondCancelText)){
            btnCancel.setText(secondCancelText);
        }
        show();
        askAgainEnable = false;
    }
    private void setClickableSpan(CharSequence message) {
        //需要显示的字串
        SpannableString spannedString = new SpannableString(message);
        if (policySpan != null ) {
            List<Integer> indexArray = new ArrayList<>();
            TypedValue typedValue = new  TypedValue();
            getContext().getTheme().resolveAttribute(R.attr.colorAccent, typedValue, true);
            for (int i = 0; i < policySpan.length; i++) {
                int pos = message.toString().indexOf(policySpan[i]);
                while (pos > -1){
                    indexArray.add(pos);
                    pos = message.toString().indexOf(policySpan[i], pos+1);
                }
                for(int j = 0; j < indexArray.size(); j++){
                    if(policyUrl != null && policyUrl.length == policySpan.length){
                        spannedString.setSpan(new TextClickableSpan(getContext(), policyUrl[i], listener), indexArray.get(j)
                                , indexArray.get(j) + policySpan[i].length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                    }
                    spannedString.setSpan(new ForegroundColorSpan(typedValue.data)
                            , indexArray.get(j), indexArray.get(j) + policySpan[i].length(),Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
            //设置点击后的颜色为透明，否则会一直出现高亮
            tvAlertMessage.setHighlightColor(Color.TRANSPARENT);
            //开始响应点击事件
            tvAlertMessage.setMovementMethod(LinkMovementMethod.getInstance());
        }
        tvAlertMessage.setText(spannedString);
    }

    public static class Builder{
        private OnPrivacyPolicyListener listener;
        private String[] policyUrl;
        private String[] policySpan;
        private CharSequence title;
        private CharSequence message;
        private CharSequence affirmText;
        private CharSequence cancelText;
        private CharSequence secondTitle;
        private CharSequence secondMessage;
        private CharSequence secondCancelText;
        private boolean askAgainEnable;
        private Context mContext;
        private int themeResId;
        public Builder(@NonNull Context context) {
            this(context, 0);
        }

        public Builder(@NonNull Context context, int themeResId) {
            this.mContext = context;
            this.themeResId = themeResId;
            setDefaultData();
        }
        private void setDefaultData(){
            this.title = getContext().getString(R.string.privacy_policy_alert_title);
            this.message = getContext().getString(R.string.privacy_policy_content
                    , getContext().getString(R.string.app_name));
            this.affirmText = getContext().getString(R.string.privacy_policy_alert_affirm);
            this.cancelText = getContext().getString(R.string.privacy_policy_alert_cancel);
            this.secondTitle = getContext().getString(R.string.privacy_policy_alert_title);;
            this.secondMessage = getContext().getString(R.string.privacy_policy_second_content, getContext().getString(R.string.app_name));;
            this.secondCancelText = getContext().getString(R.string.privacy_policy_alert_second_cancel);
            this.askAgainEnable = false;
            this.policySpan = new String[]{getContext().getString(R.string.privacy_policy_span)};
            this.themeResId = R.style.PrivacyPolicyStyle;

        }
        private Context getContext(){
            return mContext;
        }
        public Builder setListener(OnPrivacyPolicyListener listener) {
            this.listener = listener;
            return this;
        }
        public Builder setTitle(CharSequence title) {
            this.title = title;
            return this;
        }

        public Builder setMessage(CharSequence message) {
            this.message = message;
            return this;
        }
        public Builder setPolicyUrl(String... policyUrl) {
            this.policyUrl = policyUrl;
            return this;
        }

        public Builder setPolicySpan(String... policySpan) {
            this.policySpan = policySpan;
            return this;
        }

        public Builder setSecondTitle(CharSequence secondTitle) {
            this.secondTitle = secondTitle;
            return this;
        }

        public Builder setSecondMessage(CharSequence secondMessage) {
            this.secondMessage = secondMessage;
            return this;
        }
        public Builder setAffirmText(CharSequence affirmText){
            this.affirmText = affirmText;
            return this;
        }
        public Builder setCancelText(CharSequence cancelText){
            this.cancelText = cancelText;
            return this;
        }
        /**
         * 设置弹窗取消按钮文本
         *
         * @param secondCancelText
         */
        public Builder setSecondCancelText(CharSequence secondCancelText) {
            this.secondCancelText = secondCancelText;
            return this;
        }
        /**
         * 是否再次询问同意协议
         * @param askAgainEnable
         * @return
         */
        public Builder setAskAgainEnable(boolean askAgainEnable) {
            this.askAgainEnable = askAgainEnable;
            return this;
        }

        public PrivacyPolicyDialog create(){
            final PrivacyPolicyDialog dialog = new PrivacyPolicyDialog(mContext, themeResId);
            dialog.init(this);
            return dialog;
        }
        public PrivacyPolicyDialog show() {
            final PrivacyPolicyDialog dialog = create();
            dialog.show();
            return dialog;
        }
    }

}
