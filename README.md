# ValueAnimation
属性动画demo

http://www.jianshu.com/p/2412d00a0ce4

###### 1.是android 3.0(API11)后的新特性
  是对帧动画和补间动画的优化，不再局限于四种动画（平移等..），可以自定义各种动画
  它的作用对象不只是view，甚至没有对象都可以

###### 2.工作原理：
  一段时间内不断对属性值进行改变，并不断的赋给对象的属性，从而实现该对象在该属性上的动画效果

###### 3.两个重要的类ValueAnimator(手动赋值给对象属性)和ObjectAnimator（自动赋值给对象属性）

	* ValueAnimator 类是先改变值，然后 手动赋值 给对象的属性从而实现动画；是 间接 对对象属性进行操作；
	* ObjectAnimator 类是先改变值，然后 自动赋值 给对象的属性从而实现动画；是 直接 对对象属性进行操作

- ValueAnimator

 ValueAnimator.ofInt（int values）//将初始值以整形的方式过渡到结束值
 用整形估值器：IntEvaluator

 ValueAnimator.ofFloat（float values）
 用浮点型估值器：FloatEvaluator

 ValueAnimator.ofObject（int values）//将初始值以对象的形式过度到结束
 用对象估值器：TypeEvaluator(写一个类实现TypeEvaluator)
