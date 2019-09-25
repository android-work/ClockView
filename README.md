# ClockView
自定义的一个指针时钟的一个动画控件

## 1、导入仓库<br>
  allprojects {<br>
		repositories {<br>
			...<br>
			maven { url 'https://jitpack.io' }<br>
		}<br>
	}<br>
  
## 2、导入依赖<br>
  dependencies {<br>
	        implementation 'com.github.android-work:ClockView:v1.0.2'
	}<br>
  
  ### 相关的动画调用方法<br>
   ### 布局文件
   <com.ancroid.work.clockview.ClockView<br>
        android:layout_width="wrap_content"在布局文件中给控件宽高具体高度，否则使用默认300像素大小<br>
        android:layout_height="wrap_content"<br>
        android:id="@+id/clockview"/><br>
   ### 相关方法
   设置时钟的时分秒指针宽度，以及时钟轮廓园的宽度：setStrokeWidth(int strokeWidth)<br>
   设置时钟外廓园颜色：setClockColor(int clockColor)默认黑色<br>
   设置圆心点的半径：setCircleCenter(int circleCenter)<br>
   设置秒针的颜色：setSecondColor(int secondColor)默认红色<br>
   设置分针时针的颜色：setMinuteColor(int minuteColor)默认黑色<br>
   停止动画：stop()当页面销毁时停止动画，否则会内存泄露<br>
  
  
  ### 图片描述<br>
   https://github.com/android-work/ClockView/blob/master/IMAGE/B58ACD46-5106-4cdf-9D0A-45DFC99CB8C5.png
   
![图片描述](https://github.com/android-work/ClockView/blob/master/IMAGE/B58ACD46-5106-4cdf-9D0A-45DFC99CB8C5.png)
