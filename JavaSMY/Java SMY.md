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
    + 如果不这样做的话，**就会违反Object.hashCode的通用约定**，**<font color=red>从而导致该类无法结合所有基于散列的集合一起正常运作</font>**，这样的集合包括***HashMap、HashSet和Hashtable。***
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

          任何一个累的对象，都以充当锁。

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