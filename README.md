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
### 2、设置ViewPager滑动监听并设置View渐变
```java
private ArrayList<GradientView> mArrayLists;

/**初始化代码就不写了，可以打开MainActivity去看，mArrayLists装有3个实例化的GradientView引用*/

@Override
public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    if(positionOffset>0){
    /**
      * position：表示当前操作的View的Index，所以position+1就是欲滑到的View，即正在渐变的View，所以position表示当前
      *           正在反渐变的View；
      */
        GradientView left=mArrayLists.get(position);
        GradientView right=mArrayLists.get(position+1);
        //positionOffset：滑动的偏移量
        //当前正在反渐变的View跟随偏移量渐渐地透明
        left.setImageAlpha(1-positionOffset);
        //当前正在渐变的View跟随偏移量渐渐不透明
        right.setImageAlpha(positionOffset);
    }
}
```
#### API
```java
GradientView.setImageAlpha(float alpha);
设置当前引用View的透明度，0-1,0：全透明  1：不透明
```
#### 使用的Bitmap格式
![](https://github.com/YuQianhao/GradientView/blob/master/app/src/main/res/mipmap-xhdpi/ic_menu_start_conversation.png)
