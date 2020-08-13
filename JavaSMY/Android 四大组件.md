### Android 四大组件

#### 1. Activity

> 活动、展示型组件

**1.1 作用：显示界面 & 与用户进行交互**

**1.2 生命周期**



**1.3 启动方式**

1. **显式Intent**

   ```java
   // 1. 使用构造函数 传入 Class对象 
   Intent intent = new Intent(this, SecondActivity.class); 
   startActivity(intent);
   ```

2. **隐式Intent**

   ```java
   // 通过Category、Action设置
   Intent intent = new Intent(); 
   intent.addCategory(Intent.CATEGORY_DEFAULT); 
   intent.addCategory("com.hc.second"); 
   intent.setAction("com.hc.action"); 
   startActivity(intent);
   ```

   

```xml
<activity android:name=".MainActivity">
  <intent-filter>
    <action android:name="android.intent.action.MAIN" />

    <category android:name="android.intent.category.LAUNCHER" />
  </intent-filter>
</activity>
```



**1.4 Intent**

>  描述的是应用的动作 & 其对应的数据
>
> Activity, Service, BroadcastReceive 之间的通信载体

1. **指定当前组件要完成的动作**

   + 显式、隐式

2. **在 `Android` 不同组件间 传递数据**

   + `putExtra（）`、`Bundle`方式

   + 可传递的数据类型
     a. **8种基本数据类型**（boolean byte char short int long float double）、String
     b. **Intent、Bundle**
     c. **Serializable对象、Parcelable及其对应数组、CharSequence 类型**
     d. ArrayList，泛型参数类型为：<Integer>、<? Extends Parcelable>、<Charsequence>、<String>

**传递数据方式**

+  **putExtra()**

  ```java
  intent.putExtra("data","I come from FirstActivity");
  ----
  Intent intent = getIntent();
  // 注意数据类型 与 传入时保持一致
  String data = intent.getStringExtra("data");
  
  ```

+ **Bundle()**

  ```java
  // d. 创建Bundle对象并将Bundle放入到Intent中
  intent.putExtras(bundle);
---
  Intent intent = getIntent();
  Bundle bundle = intent.getExtras();
  
  // c. 通过bundle获取数据传入相应的键名，就可得到传来的数据
  // 注意数据类型 与 传入时保持一致
  String nameString = bundle.getString("name");
  int age = bundle.getInt("age");
  ```
  
+ **bundle更适用于两种情况：**

  + **连续传递数据**
    若需实现连续传递：Activity A -> B -> C；若使用putExtra（），则需写两次intent = A->B先写一遍 + 在B中取出来 & 再把值重新写到Intent中再跳到C；若使用 Bundle，则只需取出 & 传入 Bundle对象即可
  + **可传递的值：对象**
    `putExtra（）`无法传递对象，而 `Bundle`则可通过 `putSerializable`传递对象



#### Activity卡顿原因

+ 内存泄漏
+ 加载大图片
+ UI线程做耗时操作
+ UI视图过度绘制



#### 加速启动的方式

+ 减少onCreate()的时间
+ 优化布局文件

+ 提高Adapter、AdapterView的效率
+ 减少主线程的阻塞时间 -- ANR



#### 状态保存

···刚进入A1
I/TEST: Activity1----->onCreate
I/TEST: Activity1----->onStart
I/TEST: Activity1----->onResume
**<font color=red>------------------- 旋转屏幕</font>**
I/TEST: Activity1----->onPause
I/TEST: Activity1----->onStop
**<font color=red>I/TEST: Activity1----->onSaveInstanceState    在Destroy之前</font>**
I/TEST: Activity1----->onDestroy

<font color=red>**-------------------重新Create**</font>

I/TEST: Activity1----->onCreate
I/TEST: Activity1----->onStart
**<font color=red>I/TEST: Activity1----->onRestoreInstanceState	在Resume之前</font>**
I/TEST: Activity1----->onResume



#### Activity & Fragment 交互

<img src="/Users/Ethan/Desktop/Fannibals.github.io/JavaSMY/Screen Shot 2020-02-23 at 12.01.45 pm.png" alt="Screen Shot 2020-02-23 at 12.01.45 pm" style="zoom:50%;" />

**Application**

![Screen Shot 2020-02-23 at 12.03.31 pm](/Users/Ethan/Desktop/Fannibals.github.io/JavaSMY/Screen Shot 2020-02-23 at 12.03.31 pm.png)



-------



#### 2. Service

> 计算型组件，提供需在后台运行的服务

**Service有两种启动方式**

**1. Start -- 不可通信 **

start方式启动的Service的**生命周期**如下：
`onCreate()`--->`onStartCommand()` ---> `onDestory()`

+ 如果服务已经开启，则不会重复的执行onCreate()
+ 

**2.Bind --  可通信**

此方式启动的Service的**生命周期**如下：
`onCreate()` --->`onBind()`--->`onunbind()`--->`onDestory()`



**按照运行地点分类：**

+ **本地服务：localService**：
  + 运行在主线程，节约资源，易于通信（同一进程内） 音乐播放
+ **远程服务：RemoteService**
  + 运行在独立进程，不受其他Activity的影响
  + IPC复杂



#### 问：Service 和 Thread的异同

+ 相同点：

  + 执行异步操作

+ 不同点：

  + 运行线程不同：主线程 vs 工作线程

  + 运行范围：不依赖UI / Activity vs 依赖



#### 远程服务 （AIDL & IPC）

> 多个应用程序共享同一个后台服务(远程服务)
>
> 即一个远程Service与多个应用程序的组件（四大组件）进行**跨进程通信**

**实现方法**

1. IPC：Inter-Process Communication，即跨进程通信
2. AIDL：Android Interface Definition Language，即Android接口定义语言



**具体实例：**

**服务器端**

**Step1: 新建一个AIDL文件** 

**Step2: 在新建AIDL文件里定义Service需要与Activity进行通信的内容（方法），并进行编译（Make Project）**

**Step3: 在Service子类中实现AIDL中定义的接口方法，并定义生命周期的方法（onCreat、onBind()、blabla）**

**Step4: 在AndroidMainfest.xml中注册服务 & 声明为远程服务**

```xml
<service
android:name=".MyService"
android:process=":remote"  //将本地服务设置成远程服务
android:exported="true"      //设置可被其他进程调用>

  //此处Intent的action必须写成“服务器端包名.aidl文件名”
  <intent-filter>
    <action android:name="scut.carson_ho.service_server.AIDL_Service1"/>
  </intent-filter>

</service>
```

**C端**

在MainActivity.java里

- 使用Stub.asInterface接口获取服务器的Binder；
- 通过Intent指定服务端的服务名称和所在包，进行Service绑定；
- 根据需要调用服务提供的接口方法

```java
//设置绑定服务的按钮
bindService.setOnClickListener(new View.OnClickListener() {
  @Override
  public void onClick(View v) {
                    
    //通过Intent指定服务端的服务名称和所在包，与远程Service进行绑定
    //参数与服务器端的action要一致,即"服务器包名.aidl接口文件名"
    Intent intent = new Intent("scut.carson_ho.service_server.AIDL_Service1");
                    
    //Android5.0后无法只通过隐式Intent绑定远程Service
    //需要通过setPackage()方法指定包名
    intent.setPackage("scut.carson_ho.service_server");

    //绑定服务,传入intent和ServiceConnection对象
    bindService(intent, connection, Context.BIND_AUTO_CREATE);

  }
});
```





---

### Binder机制原理

> **机制上讲：Binder是一种安卓实现IPC的方式**
>
> **结构上讲**：Binder是一种虚拟的物理设备驱动，连接了Service进程，Client进程和Service Manager进程
>
> **代码上来讲：**Binder是一个实现了IBinder接口的类

**传统的跨进程通信：**

![img](https://lh4.googleusercontent.com/N2e-8Vl4FxOObpN1NfzNx4M8nhugItw-OiqBRPL4KuzeTRhSuhqcgs90C_a8n3MF9c7OPln21YQ9CRFLkrX2lHvUgN9BwFEEQXQje7q9iGeZQZ4cL_COB0us73KzcMXqNbKC-YKX)

+ 缺点：效率低下，拷贝两次

**Binder机制的跨进程通信**

+ 使用了内存映射，使得只需要一次拷贝操作



**Binder跨进程通信的模型**

1. Server进程注册服务 --> Binder驱动

2. Client进程获取服务，使用服务 <----> Binder驱动

3. ServiceManager进程：管理Service注册与查询

   

**Binder Driver 如何在内核空间中做到一次拷贝的？**

当Client向Server发送数据时，Client会先从自己的进程空间把通信数据拷贝到内核空间**，因为Server和内核共享数据，**所以不再需要重新拷贝数据，而是直接通过内存地址的偏移量直接获取到数据地址。总体来说只拷贝了一次数据。

Server和内核空间之所以能够共享一块空间数据主要是通过**binder_mmap来实现的**。它的主要功能是在内核的虚拟地址空间申请一块和用户虚拟内存相同大小的内存，然后再申请一个page大小的内存，将它映射到内核虚拟地址空间和用户虚拟内存空间，从而实现了用户空间缓冲和内核空间缓冲同步的功能。







------



### ContentProvider

> 进程间 进行**数据交互 & 共享**，即跨进程通信



<img src="/Users/Ethan/Desktop/Fannibals.github.io/JavaSMY/Screen Shot 2020-02-23 at 2.11.35 pm.png" alt="Screen Shot 2020-02-23 at 2.11.35 pm" style="zoom:80%;" />

#### 具体使用

1. **URI** 

   外界进程通过 `URI` 找到对应的`ContentProvider` & 其中的数据，再进行数据操作

   + Schema + Authority + Path + ID

   + "content://com.carson.provider/User/1"

2.  **MIME数据类型**
   + 指定某个扩展名的文件用某种应用程序来打开
3. **ContentProvider类**
   + 表格形式组织数据
   + 主要方法：**增(insert)删(delete)改(update)查(query)**

4. **ContentResolver类**

   + 统一管理不同 `ContentProvider`间的操作

   1. 即通过 `URI` 即可操作 不同的`ContentProvider` 中的数据
   2. 外部进程通过 `ContentResolver`类 从而与`ContentProvider`类进行交互



#### 优点：

+ **安全**
  + 允许把自己的应用数据根据需求开放给 其他应用 进行 **增、删、改、查**，而不用担心因为直接开放数据库权限而带来的安全问题
+ **访问简单 & 高效**
  + 如一开始数据存储方式 采用 `SQLite` 数据库，后来把数据库换成 `MongoDB`，也不会对上层数据`ContentProvider`使用代码产生影响







### BroadcastReceiver

> 是一个全局的监听器，有两个角色：广播发送者、广播接收者

#### 1. 作用

监听/接收 App发出的广播消息，并作出响应



#### 2. 应用场景

+ Android不同组件间的通信
+ 多线程通信



#### 3. 实现原理

+ 采用观察者模式

![Screen Shot 2020-02-23 at 3.48.15 pm](/Users/Ethan/Desktop/Fannibals.github.io/JavaSMY/Screen Shot 2020-02-23 at 3.48.15 pm.png)







#### 4. 分类

1. 普通广播（`Normal Broadcast`）

   + 完全异步执行的广播

2. 系统广播（`System Broadcast`）

   ```java
   private IntentFilter intentFilter;
   private mBCReceiver mBCReceiver;
   
   @Override
   protected void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
     setContentView(R.layout.activity_bc);
   
     intentFilter = new IntentFilter();
     		intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
     mBCReceiver = new mBCReceiver();
     registerReceiver(mBCReceiver,intentFilter);
   }
   
   @Override
   protected void onDestroy() {
     super.onDestroy();
     unregisterReceiver(mBCReceiver);
   }
   ```

   注：当使用系统广播时，只需要在注册广播接收者时定义相关的action即可，并不需要手动发送广播，当系统有相关操作时会自动进行系统广播

3. 有序广播（`Ordered Broadcast`）

   + 同步执行的广播，可以被拦截

   + **广播接受者接收广播的顺序规则**（同时面向静态和动态注册的广播接受者）

     1. 按照Priority属性值从大-小排序；

     2. Priority属性相同者，动态注册的广播优先

     3. ```xml
        <intent-filter android:priority="100">
            <action android:name="android.net.conn.CONNECTIVITY_CHANGE" />
        </intent-filter>
        ```

   + ```undefined
     sendOrderedBroadcast(intent);
     ```

4. 粘性广播（`Sticky Broadcast`）

5. App应用内广播（`Local Broadcast`）

   Android中的广播可以跨App直接通信（exported对于有intent-filter情况下默认值为true）

   





















