### Android 总结

#### 1. Handler

> 消息传递机制

**1.1 作用**

* **将工作线程中需要更新UI的操作信息传递到UI主线程**

+ **多个线程并发更新UI的同时 保证线程安全**

**1.2 流程**

![Screen Shot 2020-02-23 at 7.42.45 pm](/Users/Ethan/Desktop/Fannibals.github.io/JavaSMY/Screen Shot 2020-02-23 at 7.42.45 pm.png)



**1.3 工作流程图**

https://www.jianshu.com/p/f0b23ee5a922



**1.4 Thread vs Looper vs Handler**

![Screen Shot 2020-02-23 at 5.32.56 pm](/Users/Ethan/Desktop/Fannibals.github.io/JavaSMY/Screen Shot 2020-02-23 at 5.32.56 pm.png)



**1.5 Handler 核心类**

![Screen Shot 2020-02-23 at 5.40.28 pm](/Users/Ethan/Desktop/Fannibals.github.io/JavaSMY/Screen Shot 2020-02-23 at 5.40.28 pm.png)



**1.6 内存泄漏**

> 内存泄露的定义：本该被回收的对象不能被回收而停留在堆内存中

**内存泄露出现的原因：**当一个对象已经不再被使用时，本该被回收但却因为有另外一个正在使用的对象持有它的引用从而导致它不能被回收。 这就导致了内存泄漏



**1.7 对比**

![Screen Shot 2020-02-23 at 7.49.09 pm](/Users/Ethan/Desktop/Fannibals.github.io/JavaSMY/Screen Shot 2020-02-23 at 7.49.09 pm.png)



---

### Android性能优化

#### 1. 内存泄漏、内存溢出



#### 2. Android内存管理机制

+ 管理：内存分配+释放

  

**2.1 针对进程的内存策略**

**内存分配**：ActivityManagerService集中管理所有进程的内存分配

**内存回收：按照优先级高低**

+ 从高到低：因此从下到上回收
  + 前台进程(与用户正在交互的进程)
  + 可见进程
  + 服务进程
  + 后台进程
  + 空进程
+ 然后有linux内核真正回收具体进程



**2.2 针对对象、变量的内存策略**

同Java



#### 3. 常见的内存问题 & 优化方案

1. **Memory leak 内存泄漏**

   > 程序在申请内存后，当该内存不需再使用 **但 却无法被释放 & 归还给 程序**的现象

   

+ **原因：<font color=red>持有引用者的生命周期 > 被引用者的生命周期，从而当后者结束生命周期销毁时，无法被正确回收</font>**

+ **常见内存泄露原因**

  1. **集合类**

     + 集合类在添加元素之后，仍然引用着集合的元素对象，导致该集合元素对象不可被回收，从而导致内存泄漏

     + **解决方案**
       集合类 添加集合元素对象 后，在使用后必须从集合中删除
       由于1个集合中有许多元素，故最简单的方法 = 清空集合对象 & 设置为null

       ```
       objectList.clear();
       objectList=null;
       ```

  2. **`Static`关键字修饰的成员变量**

     +  **private static Context mContext;**

     + **解决方案**

       1. 尽量避免 `Static` 成员变量引用资源耗费过多的实例（如 `Context`）

          > 若需引用 `Context`，则尽量使用`Applicaiton`的`Context`
          >
          > 2. 使用 弱引用`（WeakReference）` 代替 强引用 持有实例

  3. **非静态内部类 / 匿名类**

     + 内部引用了静态对象

  4. **资源对象使用后未关闭**

     **泄露原因**

     + 对于资源的使用（如 广播BraodcastReceiver、文件流File、数据库游标Cursor、图片资源Bitmap等），若在Activity销毁时无及时关闭 / 注销这些资源，则这些资源将不会被回收，从而造成内存泄漏

     **解决方案**

     + 在Activity销毁时 及时关闭 / 注销资源
       

2. **图片资源**

   + 图片资源bitmap非常消耗内存 --》 OOM

   ![Screen Shot 2020-02-23 at 8.06.24 pm](/Users/Ethan/Desktop/Fannibals.github.io/JavaSMY/Screen Shot 2020-02-23 at 8.06.24 pm.png)

+ **BitmapFactory.Options options = new BitmapFactory.Options();**
  **options.inJustDecodeBounds = true;**
  **BitmapFactory.decodeResource(context.getResources(), resId, options);**



#### Java 引用机制

|                 | gc                                                           | example                      |      |
| --------------- | ------------------------------------------------------------ | ---------------------------- | ---- |
| StrongReference | gc绝对不会回收                                               | Object sr= **new** Object(); |      |
| SoftReference   | 软引用对象是在jvm内存不够的时候才会被回收                    |                              |      |
| WeakReference   | 只具有**弱引用**的对象拥有**更短暂**的**生命周期**（相比于soft） |                              |      |
|                 |                                                              |                              |      |

**注意点：**

1. **StrongReference**

+ 在一个**方法的内部**有一个**强引用**，这个引用保存在`Java`**栈**中，而真正的引用内容(`Object`)保存在`Java`**堆**中。 当这个**方法运行完成**后，就会退出**方法栈**，则引用对象的**引用数**为`0`，这个对象会被回收。
+ 但是如果这个`strongReference`是**全局变量**时，就需要在不用这个对象时赋值为`null`，因为**强引用**不会被垃圾回收。

![Screen Shot 2020-02-23 at 8.34.12 pm](/Users/Ethan/Desktop/Fannibals.github.io/JavaSMY/Screen Shot 2020-02-23 at 8.34.12 pm.png)

+ **软引用可用来实现内存敏感的高速缓存，比如在图片加载框架中，通过软引用来实现内存缓存**

