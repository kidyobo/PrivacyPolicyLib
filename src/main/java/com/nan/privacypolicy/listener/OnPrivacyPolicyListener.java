package com.chewawa.privacypolicy.listener;

/**
 * @class describe
 * @anthor nanfeifei email:18600752302@163.com
 * @time 2021/10/13 15:50
 */
public interface OnPrivacyPolicyListener {
    void onPolicyClick(String url);
    void onPolicyAgree();
    void onPolicyCancel();
}
