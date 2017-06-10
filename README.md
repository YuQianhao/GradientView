TabHorizontal
===================================
QQ：9166401618<br>
欢迎各位大佬前来补坑！<br><br>
### 实现了微信6.0+使用的底部导航的按钮跟随ViewPager滑动渐变的特效！<br>
#### 直接引用[app/src/main/com/yuqianhao/view]目录下的类即可，没有导出jar，直接Copy即可！注意，res文件values文件夹还有个attrs.xml也要捎走。。。。<br><br>
![](https://github.com/YuQianhao/GradientView/blob/master/0.gif)<br><br>
### 1、设置布局
```java
xmlns:yqh="http://schemas.android.com/apk/res/com.yuqianhao.myapplication"
<com.yuqianhao.view.GradientView
    android:id="@+id/button_0"
    android:layout_width="wrap_content"
    android:layout_height="match_parent"
    android:padding="5dp"
    android:text="TextView"
    yqh:achieveColor="#ff3700"
    yqh:src="@mipmap/ic_menu_start_conversation"
    yqh:text="测试"
    yqh:textSize="12sp" />
```
#### 自定义属性：
```java
src               :引用资源为控件的图标素材
text              :字符串资源为图标的文本内容
textSize          :字体大小，默认不写为12sp
achieveColor      :渐变的颜色
textColor         :文本的初始颜色
```
