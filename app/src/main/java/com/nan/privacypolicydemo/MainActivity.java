package com.nan.privacypolicydemo;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.nan.privacypolicy.listener.DefaultPrivacyPolicyListener;
import com.nan.privacypolicydemo.databinding.ActivityMainBinding;
import com.nan.privacypolicy.PrivacyPolicyDialog;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    PrivacyPolicyDialog privacyPolicyDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
//        PrivacyPolicyDialog privacyPolicyDialog = new PrivacyPolicyDialog.Builder(this)
//                .setTitle("用户协议")   //设置标题
//                .setMessage("请你认真阅读《用户协议》和《隐私协议》，确认即为同意我们的协议") //设置内容
//                .setSecondTitle("确认不同意？")  //设置不同意后再次询问的标题
//                .setSecondMessage("不同意就不让你用了")  //设置不同意后再次询问的内容
//                .setPolicySpan("《用户协议》", "《隐私协议》")  //设置协议标题，用来做超链接识别，参考示例，截取字符串使用《》号
//                .setPolicyUrl("http://www.baidu.com","http://www.baidu.com")  //设置协议跳转链接，与协议标题对应
//                .setAffirmText("确认")  //设置确认文本
//                .setCancelText("不同意")  //设置不同意文本
//                .setSecondCancelText("我不用了")  //设置再次询问后还不同意的文本
//                .setAskAgainEnable(true)  //设置是否开启再次询问，默认不开启
//                .setListener(new DefaultPrivacyPolicyListener(this) {  //设置协议标题点击事件
//                    @Override
//                    public void onPolicyAgree() {
//
//                    }
//                })
//                .show();
        PrivacyPolicyDialog privacyPolicyDialog = new PrivacyPolicyDialog.Builder(this)
                .setListener(new DefaultPrivacyPolicyListener(this) {  //设置协议标题点击事件

                    @Override
                    public void onPolicyAgree() {

                    }
                })
                .show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}