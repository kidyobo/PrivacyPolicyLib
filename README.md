# PrivacyPolicyLibrary
### 自用隐私协议库，提供默认实现及支持修改参数
#### library导入 ####
**第一步：在你的根目录build.gradle中添加如下代码：**

	allprojects {
		repositories {
			maven { url 'https://jitpack.io' }
		}
	}
**第二步：在你的app下build.gradle中添加如下代码：**

	dependencies {
	        implementation 'com.github.nanfeifei:PrivacyPolicyLibrary:1.0.1'
	}

**项目比较简单，直接贴使用示例**

### 代码

    PrivacyPolicyDialog privacyPolicyDialog = new PrivacyPolicyDialog.Builder(this)
                .setTitle("用户协议")   //设置标题
                .setMessage("请你认真阅读《用户协议》和《隐私协议》，确认即为同意我们的协议") //设置内容
                .setSecondTitle("确认不同意？")  //设置不同意后再次询问的标题
                .setSecondMessage("不同意就不让你用了")  //设置不同意后再次询问的内容
                .setPolicySpan("《用户协议》", "《隐私协议》")  //设置协议标题，用来做超链接识别，参考示例，截取字符串使用《》号
                .setPolicyUrl("http://www.baidu.com","http://www.baidu.com")  //设置协议跳转链接，与协议标题对应
                .setAffirmText("确认")  //设置确认文本
                .setCancelText("不同意")  //设置不同意文本
                .setSecondCancelText("我不用了")  //设置再次询问后还不同意的文本
                .setAskAgainEnable(true)  //设置是否开启再次询问，默认不开启
                .setListener(new DefaultPrivacyPolicyListener(this) {  //设置协议标题点击事件
                    @Override
                    public void onPolicyAgree() {
                       //同意协议后做什么
                    }
                })
                .show();

**识别点击范围和标色是通过《》，记得按照规则填入内容，有提供一套默认文本，如果默认文本满足需求直接使用默认文本即可，协议可传入单个，也可传入多个，协议标题和链接数量对应即可**
**默认实现示例**
### 代码
    PrivacyPolicyDialog privacyPolicyDialog = new PrivacyPolicyDialog.Builder(this)
                .setListener(new DefaultPrivacyPolicyListener(this) {  //设置协议标题点击事件
                    @Override
                    public void onPolicyAgree() { 
                       //同意协议后做什么
                    }
                })
                .show();

**DefaultPrivacyPolicyListener默认用户不同意则退出应用，点击超链接默认跳转外部浏览器打开，如需自定义请自行实现OnPrivacyPolicyListener**  

