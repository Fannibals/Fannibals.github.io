### Java SMY

#### 1. **Java特性 -- 多态**

+ 多态面向对象的三大**特性**：**封装，继承，多态**
+ 多态的**定义**
  + 允许**不同类的对象对同一消息作出响应**
  + 即同一个消息可以根据发送对象的不同而采取多种不同的行为方式。
  + 作用：消除类型之间的耦合关系
+ **多态存在的三个<font color=red>必要条件</font>**
  + 1 - 要有**继承**
  + 2 - 要有**重写**
  + 3 - **父类引用指向子类对象**  ```Father f = new Son();```
    + 向上转型的缺点，就是不能调用子类中有但是父类中没有的方法
    + 会调用重写的方法



#### 2. String 分析

1. https://www.jianshu.com/p/2f209af80f84 回看
2. **String immutable**
   + 不可变对象：一个对象的状态在被创建之后就不再变化
   + 是对字符串对象的引用，如 s = s + ''123" 返回的就是一个新的对象

3. **String vs. StringBuffer vs. StringBuilder**

   + 共同点：都是final类，不允许被继承，在本质上都是字符数组

   + 不同点：

     + **String的长度是不可变的而后两者长度可变**，
     + 在进行连接操作时，**String每次返回一个新的String实例**，而StringBuffer和StringBuilder的**append方法直接返回this**，所以当进行大量的字符串连接操作时，不推荐使用String，因为它会产生大量的中间String对象。

     + StringBuffer和StringBuilder的一个**区别是**，StringBuffer在append方法前增加了一个**synchronized**修饰符，以起到同步的作用，为此也降低了执行效率

#### 3. primitive types

+ int 4 bytes	- > -2^31 ~ 2^31-1
+ long, double 8 bytes

+ **int vs. Integer**
  + int初始值为0， Integer初始值为null
  +  ①无论如何，Integer与new Integer不会相等。不会经历拆箱过程，i3的引用指向堆，而i4指向专门存放他的内存（常量池），他们的内存地址不一样，所以为false // **回看**
     ②两个都是非new出来的Integer，如果数在-128到127之间，则是true,否则为false
     java在编译Integer i2 = 128的时候,被翻译成-> Integer i2 = Integer.valueOf(128);而valueOf()函数会对-128到127之间的数进行缓存
     ③两个都是new出来的,都为false
     ④int和integer(无论new否)比，都为true，因为会把Integer自动拆箱为int再去比



#### 4. ==, equals, hashcode

+ **==** 比较的是内存存放地址

+ 默认（未被覆盖）的情况下equals方法都是调用Object类的equals方法

  + 主要用于判断**<font color=red>对象的内存地址引用是不是同一个地址 </font>**--> 封装 == 

+ **hashcode**

  + 方法返回的就是一个数值 hash码
  + 用处 -- 例如set中判定元素是否重复
    + 难点：当一个元素进入一个有1000个元素的集合中，如何判定新进来的元素是否跟已有元素们重复？
    + 解决方法：先调用该元素的hashcode方法，定位到放置的位置上，如果没有元素则不冲突，否则再调用equals判定

+ **equals & hashcode**

  + 如果两个对象equals，Java运行时环境会认为他们的hashcode一定相等。
    
    + 不equals也可能相等
+ 如果两个对象hashcode相等，他们不一定equals。(不等则一定不equals)
  
  + **<font color=red>重写 equals 方法要重写 hashCode</font>**
    
    > 如果不这样做的话，**就会违反Object.hashCode的通用约定**，**<font color=red>从而导致该类无法结合所有基于散列的集合一起正常运作</font>**，这样的集合包括***HashMap、HashSet和Hashtable。***
  + hash算法是利用**数组寻下标访问速度高效的特点**（见下点）。将存储的元素和数组下标关联起来。来达到高查找效率的目标

#### 5. 线程 & 进程

1. **进程**
   + 是程序的**一次执行过程**，或是**正在进行的一个程序**
   + 进程作为**资源分配的单位**
2. **线程**
  + 进程中执行运算的最小单位，是进程中的一个实体
  + **易于调度，提高并发**
3. **线程 & 进程的关系**
  + 一个线程只属于一个进程，一个进程有 >= 1个线程
  + 进程是拥有资源的一个独立单位，线程不拥有系统资源，但可以访问隶属于进程的资源.
  + 每个线程，拥有自己**独立**的：**(虚拟机)栈， 程序计数器**
  + 多个线程，**共享**同一个进程中的结构：**方法区，堆**
4. **单核CPU & 多核CPU**

   + 单核 --> 假的多线程
   + 一个Java程序java.exe has at least 3 threads: **main(), gc() & exception threads**
5. **concurrent & parallel** 
   1. two or more calculations happen within the same time frame
   2. two or more calculations happen simultaneously
6. **创建多线程的两种方式**
   + **extends Thread**
     + 创建一个继承Thread类的子类 --> 重写run() --> 创建Thread子类对象 start（）
     + start（）: 启动当前线程并调用当前线程的run()
   + **implements Runnable**
     + new Thread(new runnable()); // runnable implements Runnable

#### 6. 深入线程，多线程

1. **线程的状态 Thread.State**

   + **NEW** : A thread that has not yet started in this state;
   + **RUNNABLE**: A thread executing in the JVM is in this state;
   + **BLOCKED**: A thread that is blocked **waiting for <font color=red>a monitor lock</font>** is in this state;
   + **WAITING**: A thread that is waiting indefinitely for another thread to perform a particular action
   + **TIMED_WAITING**: A thread that is waiting for another 
   + **TERMINATED**
2. **线程通信**

   + wait(), notify(), notifyAll() : 这三个方法定义在Object类中

3. **对象的机制**

   + **synchronised, wait, notify** 是任何对象都具有的同步工具
   + **monitor : java中每个对象都有一个监视器，来监测并发代码的重入**

4. **线程安全问题**

   + 主要是有**共享数据:(多个线程共同操作的变量)**

   + 例子：卖票问题：出现重票，错票

   + 原因：当某个线程操作车票的过程中，尚未完成操作时，其他线程也参与进来，也操作车票。

   + **解决方案：**每次只有一个线程可以操作

     + **<font color=red>方式一：同步代码块</font>**

       ```
       synchronized(同步监视器--monitor){
       	// 需要被同步的代码
       }
       ```

       1. **同步监视器（Monitor）**，俗称锁。

          任何一个类的对象，都以充当锁。

       2. 这个监视器有要求：**多个线程必须要共用同一把锁**, 有多种写法，但是一定要去看是否唯一

          ```static Object obj = new Object();```

          ```synchronized(this){}```

          this慎用，可以考虑使用

     + **<font color=red>方式二：同步方法 </font>** 

       +  同步方法仍然涉及到同步监视器，只是不需要我们显式的声明。
       + 非静态的同步方法，同步监视器是：this
       + 静态的同步方法，同步监视器是：当前类本身

5. 单例模式 (线程安全版)

6. **<font color=red>死锁问题</font>**  

   + 不同的线程分别占用对方需要的同步资源不放弃，都在等待对方放弃自己需要的同步资源。

   + 例子：

     + **第一个线程拿着s1 等 s2**
     + **第二个线程拿着s2 等 s1**

     ```java
     StringBuffer s1 = new StringBuffer();
     StringBuffer s2 = new StringBuffer();
     
     new Thread(){
       @Override
       public void run() {
         synchronized (s1){
           s1.append("a");
           s2.append("1");
     
           try {
             Thread.sleep(100);
           } catch (InterruptedException e) {
             e.printStackTrace();
           }
     
           synchronized (s2){
             s1.append("b");
             s2.append("2");
     
             System.out.println(s1);
             System.out.println(s2);
           }
         }
       }
     }.start();
     
     new Thread(new Runnable() {
       @Override
       public void run() {
         synchronized (s2){
           s1.append("c");
           s2.append("3");
     
           synchronized (s1){
             s1.append("d");
             s2.append("4");
     
             System.out.println(s1);
             System.out.println(s2);
           }
         }
       }
     }).start();
     ```

     

   + 解决方案

     + 算法，注意

7. **<font color=red>线程的同步</font>**
   + JDK5.0开始，Java提供了更强大的线程同步机制
   
   + 显式**定义同步锁对象**来实现同步，**同步锁使用Lock对象来充当**
   
   + **java.util.concurrent.locks.Lock --> 接口**
     
     + 是控制多个线程对共享资源进行访问的工具
     
   + **<font color=red>ReentrantLock</font>** 实现了**Lock**，拥有与**synchronized** 相同的并发性和内存语义
   
     + 用法：
   
       ```java
       private int ticket = 100;
       
       // 1. init ReentranLock
       private ReentrantLock lock = new ReentrantLock();
       try{
         // 2. 调用lock()
         lock.lock();
         if (ticket > 0){
       
           try {
             Thread.sleep(100);
           } catch (InterruptedException e) {
             e.printStackTrace();
           }
           --ticket;
         }
       }finally {
       
         // 3. 调用unlock()
         lock.unlock();
       }
       ```
   
   8. **面试题：ReentranLock vs. Synchronised**
   
      + **相同点**
        + 两者都可以解决线程安全问题
      + **不同点**
        + Lock (显式锁) --> 手动锁定 & 手动解锁，更灵活
        + sync (隐式锁) --> 执行完之后自定释放锁
   
      + **建议使用顺序**
        + Lock --> 同步代码块 --> 同步方法
   
      + **如何解决线程安全问题？**
   
        1. **ReentranLock**
   
        2. **Synchronized (code block & function)**
   
   9. **<font color=red>线程的通信</font>**
   
      + **wait() :** 一旦执行，当前线程就会进入阻塞状态，**并释放同步监视器**
      + **notify():** 一旦执行，就会唤醒被wait的一个线程。如果有多个线程被wait，就会唤醒优先级高的线程
      + **notifyAll():** 一旦执行，就会唤醒所有被wait的线程
   
      + 说明：
   
        1. **wait(),notify(),notifyAll() 都必须使用在同步代码块或同步方法中**
        2. 方法调用者必须是同步代码块 or 同步方法中的同步监视器，否则：
           +  **java.lang.IllegalMonitorStateException**
   
        3. 都是定义在java.lang.Object 类中
   
   10. **面试题：sleep() and wait() 的异同**
   
       + **相同点：**一旦执行该方法，都可以使得当前的线程进入阻塞状态。
   
       + **不同点：**
   
         + 1) 两个方法声明的**<font color=red>位置</font>**不同：**Thread类中声明 sleep()**， Object类**中声明wait()**
   
         + 2) **<font color=red>调用的要求</font>**不同，**sleep可以在任何需要**的场景下调用，**wait必须在同步代码块/方法中使用**
   
         + 3) **<font color=red>是否释放锁 (monitor) </font>**
   
           + 如果两个方法都是用在同步代码块/方法中：
   
             **<font color=red>sleep 不会释放锁</font>**
   
             **<font color=red>wait 会释放锁</font>**
   
   11. **<font color=red>JDK5.0 新增的线程的创建方式</font>**
       1. **实现Callable接口**
          + 相比于Runnable，更加强大
            + 相比run(), 可以有返回值
            + 可以抛出异常
            + 支持泛型的返回值
            + 需要借助FutureTask类，比如获取返回结果
            
          + <img src="/Users/Ethan/Desktop/Fannibals.github.io/JavaSMY/Screen Shot 2020-02-15 at 10.26.43 pm.png" alt="Screen Shot 2020-02-15 at 10.26.43 pm" style="zoom:50%;" />
          
            
          
       2. **新增方式二：使用线程池**
       
          + 提前创建好多个线程，放入线程池中，使用时直接获取，用完放回
          + 好处：提高响应速度，降低资源消耗，便于线程管理
       
          + 相关API：**<font color=red>ExecutorService & Executors</font>**
       
            + **ExecutorService:** 真正的线程池接口 --> 常见子类**ThreadPoolExecutor**
              + **execute** 
              + **submit**
              + **Shutdown**
       
            + **Executors:** 工具类，线程池的工厂类，用于创建并返回不同类型的线程池

---

### JVM (Java Virtual Machine)

#### 1- JVM 体系结构

1. 位置

   + **JVM <----> OS <----> Hardware**

   + JVM运行在操作系统之上，与硬件没有直接交互

2. **JVM体系结构概述 /Figure1/**

   + <img src="/Users/Ethan/Desktop/Fannibals.github.io/JavaSMY/Screen Shot 2020-02-16 at 2.24.55 pm.png" alt="Screen Shot 2020-02-16 at 2.24.55 pm" style="zoom:35%;" />

   + **橙色区有gc，灰色区无gc**
     + 99% gc都在heap里
   + **java文件(我们写的)** -->通过编译器编译--> **class文件** --> **class loader** --> 加载class 文件到runtime data area **(加载到jvm内存中)**
   + **Execution Engine** ：翻译--执行jvm发出的指令，操作硬件来实施

   + **<font color=red>方法区和堆</font>**生命期较长，需要gc 垃圾回收

3. **ClassLoader -- 类加载器**

   + **负责加载class文件**，<u>只负责加载</u>，至于**是否运行由Execution Engine决定**

   + 四种

     + **虚拟机自带的加载器：****Bootstrap(C++)，Extension(Java)，AppClassLoader(Java)**

     + **用户自定义加载器：**Java.lang.ClassLoader的子类

4. **Java 机制**

   - **双亲委派机制**

   > 双亲委派模型的式作过程是：如果一个类加载器收到了类加载的请求，它首先不会自己去尝试加载这个类，而是把这个请求委派给父类加载器去完成，每一个层次的类加载器都是如此，因此所有的加载请求最终都应该传送到顶层的启动类加载器中，只有当父加载器反馈自己无法完全这个加载请求时，子加载器才会尝试自己去加载。

   - **沙箱机制**

   > 沙箱机制是由基于双亲委派机制上采取的一种JVM的**自我保护机制**, 假设你要写一个java.lang.String 的类,由于双亲委派机制的原理,此请求会先交给Bootstrap试图进行加载,但是Bootstrap在加载类时首先通过包和类名查找rt.jar中有没有该类,有则**优先加载rt.jar包中的类**,因此就保证了java的运行机制不会被破坏.

5. **Native方法**

   + 本地方法 -- private native void start0(); **只有方法头，没有方法体**
   + 跟语言无关了，**<font color=red>需要操作系统来实现</font>**

   + **Native Method Stack**
     + **登记native方法，在Execution Engine执行时加载本地方法库**
   + **本地方法接口** Native Interface
     + 作用：融合不同的编程语言为Java所用，初衷是融合C/C++程序
   + **本地方法库**
     + 调接口的时候如果需要第三方支持，调用此库

---

6. **程序计数器 Program Counter Register**

   > 每个线程都有一个**计数器**，为**线程私有**，就是一个指针，指向方法区中的方法字节码 (**用来存储指向下一条指令的地址**，也就是将要执行的指令代码)，由Execution Engine 读取下一条指令，是一个非常小的内存空间，几乎可忽略不计。

7. **方法区 Method Area** 

   > **<font color=red>被所有线程共享</font>**, 所有定义的方法的信息都保存在该区域 -->**共享区间** 

   + **static constant var + ** **constant var** + **class info**(constructor/interface) + runtime **constant pool** 
   + 类信息 + 常量 + 编译器编译之后的代码 + 常量池
   + 虽然是堆的一个逻辑部分，但是理应分开
   
8. **Java栈 Stack**

   > 栈，也叫栈内存，**主管Java程序的运行，**是在程序创建时创建，其生命期是跟随线程的生命期，线程结束即释放内存。因此**无gc。线程私有**

	+ **基本类型的变量 + 对象的引用变量 + 实例方法都在函数的栈内存中分配**
	+ **栈帧**
	  + 本地变量 local var: 输入参数，输出参数 & 方法内变量
	  + 栈操作 Operand Stack: 记录出 & 入栈的操作
	  + 栈帧数据 Frame Data: 类文件，方法等
	+ **java.lang.StackOverflowError**
	  + 一直在入栈，没有出栈 
---

9. **Heap 堆**

  > **Java 8之前 (<=7) 的Heap结构**
>
   > **逻辑上分为：新生区 + 养老区 + 永久区**

   + **伊甸区(Eden Space) :** 刚new的那些object先来到这里
   + **Survivor 0 Space :** Eden Space 经历过一次gc
   + **Survivor 1 Space :** S0 经历过一次gc，来到这里之后：
     + 1) 如果又一次经历gc活下来了，返回S0
     + 2) S0-> S1->S0 反复15次gc后来到下面
   + **注意点：S0,S1 交替成为from/to区：复制必交换，谁空谁是to**
   + **Tenure Generation Space:** 这里也有gc -- full gc
     + 池对象：连接池，线程池 -- 都会进到养老区
     + 如果满了，full gc之后还是满了，不会到Per.   manent Space 而是会报错：**java.lang.OutOfMemoryError** 即 **<font color=red>OOM</font>**
     + **两种原因：**
       + Java虚拟机的堆内存设置不够，可以通过参数-Xms -Xmx来调整
       + 代码中创建了大量的大对象，并且长时间不能被gc收集(存在被引用)
   
   + **Permanent Space**
   
     + 对应**Method Area**，常驻内存区域，用于存放JDK自带class，interface的元数据
     + 就是jvm运行的环境，无gc
     + **Method Area** is the **interface** and is implemented by **Perm space**
     
     + **<font color=red>Jdk1.8之后：无永久代，constant pool 在元空间</font>**

> **Java8 之后（>=8）**
>
> **堆内存 逻辑上 分为三部分：新生+ + 养老+ + 元空间**
>
> **堆：新生 + 养老**

+ 元空间的本质和永久代类似，都是对JVM规范中方法区的实现。不过元空间与永久代之间最大的区别在于：元空间并不在虚拟机中，而是使用本地内存。



10. **堆参数调优入门**

+ ```-Xms``` **设置JVM初始内存大小，默认为物理内存的 1 / 64**
+ ```-Xmx``` **设置JVM最大分配内存，默认为物理内存的 1 / 4**

+ ```-XX:+PrintGCDetails``` : **输出详细的GC处理日志**



11. **Java堆的应急措施**
    + 如果伊甸区里已经满了，无法放进S0区，则会直接进入养老区(Tenure Gen)



#### 2- Garbage Collection

1. 面试题

   > + **JVM内存模型以及分区，需要详细到每个分区**
   > + 堆里面的分区：Eden，survival from, to 老年代，各自特点
   >   + 老年代最大，Eden其次（存活率低）
   > + GC三种搜集方法，什么时候用？
   > + **Minor GC** 与 **Full GC** 分别发生在什么时候
   >   + Minor GC -- 新生区
   >     + **Eden和From区 --> To区 --> swap(to,from);**
   >   + Full GC -- 养老区

2. **GC （分代搜集算法）**
   + 次数上频繁搜集Young区
   + 较少搜集Old区
   + 基本不动Perm区



3. **GC四大算法**

   + **1- 引用计数法** （jvm不用）

     + **原理：**有引用+1，释放-1，为0回收

     + **<font color=red>缺点</font>**：每次对对象赋值时均要维护引用计数器，切计数器本身也有消耗

       ​			**较难处理循环引用**

   + **2- 复制算法 （Copying）**
     + **<font color=red>应用区域：年轻代</font>**中使用Minor GC，采用Copying
     + Young = Eden + S0 + S1 --> eden to S0, S0 <->S1 相互转化均用复制
     + **原理：**从根集合(GC root)开始，通过Tracing从From中找到**存活对象**，copy到To
     + **<font color=red>优点</font>：复制扫描一次，效率高 ； 没有内存碎片**
     + **<font color=red>缺点</font>：需要双倍空间,浪费内存**

   + **3- 标记算法 （Mark-Sweep）**
     
     + **<font color=red>应用区域：老年代</font>** （标记清除 &/ 标记整理）
     + **原理：**扫描一遍，标记存货对象（Mark）-- 扫描整个内存空间，回收未标记对象
     + **<font color=red>优点</font>：不需要额外空间**
   + **<font color=red>缺点</font>：两次扫描，高耗时(进行gc时，jvm不运行) ；会产生内存碎片**
     
   + **4- 标记压缩 （Mark-Compact）**

     + **<font color=red>应用区域：老年代</font>**

     + **原理：标记 + 压缩**

     + **<font color=red>优点</font>：空间连续了**
     + **<font color=red>缺点</font>：效率不高，耗时长，不仅进行了Mark,还要移动**

   + **5- Mark-Sweep-Compact**
     + **<font color=red>应用区域：老年代</font>**
     + **原理：Mark-Sweep，进行多次GC后才Compact**
     + **优点：减少移动对象的成本**

4. **简要对比**

   + 内存效率
     + Copy > Mark-Sweep > Mark-Compact	

   + 整齐度
     + Copy == Mark-Compact > Mark-Sweep
   + 利用率
     + Mark-Compact ==  Mark-Sweep > Copy

5. **分代收集算法**

   + **年轻代** 
     + **特点：区域较小，对象存活率低。**
     + **使用：复制算法 （快）**

   + **老年代** 
     + **特点：区域较大，对象存活率高。**
     + **使用：Mark-Sweep-Compact**