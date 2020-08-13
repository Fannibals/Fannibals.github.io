## Java并发编程实战 - 读书笔记

### 第一章：简介

#### 1. 线程与进程

- **进程 process**
- A *process* is **an executing program**. One or more threads run in the context of the process. 
- **线程 thread**
- A *thread* is **the basic unit to which the operating system allocates processor time**. A thread can execute any part of the process code, including parts currently being executed by another threa

#### 2. 线程的优势

- 发挥多处理器的强大能力
  - 如在双处理器系统上，单线程的程序只能使用一半的CPU资源
  - 如果一个线程在等待I/O操作完成，另一个线程可以继续运行，使程序可以在I/O阻塞期间继续运行
- 建模的简单性
- 异步事件的简化处理
- 响应更灵敏的用户界面(可以思考Android的GUI)



#### 3. 线程带来的风险

##### 3.1 安全性问题

- 常见的递增运算```value++;``` 并非是单个操作
- 实际上包含了三个独立的操作:
  - 读取value
  - 将value+1
  - 并将计算结果写入value
- 因此在多线程操作的环境下可能导致不同线程的调用返回了相同的数值。                                                      

##### 3.2 活跃性问题

- 死锁，饥饿，活锁

##### 3.3 性能问题

- 性能问题包括多个方面，如服务时间过长，响应不灵敏，吞吐率过低等等。
- 线程总会带来某种程度的运行时开销。在多线程程序中，当**线程调度器临时挂起活跃线程并转而运行另一个线程的时候**，会频繁出现**上下文切换操作(Context Switch)**

---

### 第二章 线程安全性

> 要编写线程安全的代码，其核心在于要对状态访问操作进行管理，特别是对**<font color=red>Shared & Mutable 状态</font>**的访问

#### 1. 什么是线程安全性

> 在多个线程访问某个类时，不管运行时环境采用何种调度方式或者这些线程将如何交替执行，并且在主调代码中不需要任何额外的同步或协同，这个类都能表现出正确的行为。

- **无状态（Stateless）**对象一定是线程安全的。

#### 2. 原子性

##### 2.1 竞价条件

- 当某个计算的正确性取决于多个线程的交替执行时序时，就会发生竞价条件 --> Check-Then-Act

- 有一种情况就是:延迟初始化: LAZY INIT

- ```java
  public class LazyInitRace {
  	private ExpObject instance = null;
  	
  	public ExpObject getInstance() {
  		if (instance == null)
  			instance = new ExpObject();
  		return instance;
  	}
  }
  ```

- 上面这种情况下，假设有两个线程A，B。A check之后发现instance为空，准备new一个新的对象。如果B在中间插入进来，同样发现instance为空，则会出现创建多次的情况。

##### 2.2 复合操作

- Atomic类

  - 在```java.util.concurrent.atomic``` 包中包含了一些原子变量类，用于实现在数值和对象引用上的院子状态转换。

  - ``` java
    private final AtomicLong count = 
    	new AtomicLong(0);
    ```

  - 在实际情况中，应该尽可能的使用现有的线程安全对象（例如 AtomicLong）来管理类的状态。

##### 2.3 加锁机制

- 要保持状态的一致性，就需要在单个原子操作中更新所有相关的状态变量。

**2.3.1 内置锁**

- Synchronized Block
- 每个Java对象都可以用做一个实现同步的锁，这些锁被称为内置锁（Intrinsic Lock）或者Monitor Lock。
- 线程在进入同步代码块之前会自动获得锁，并且在推出同步代码块时自动释放锁。

**2.3.2 重入**

> 持有锁的不是方法，是线程！

- 当某个线程请求一个由其他线程持有的锁时，发出请求的线程就会阻塞。
- 然而，由于内置锁是可以重入的，因此如果某个线程试图获得一个已经由它自己持有的锁，那么这个请求就会成功。
- 所以在java内部，同一线程在调用自己类中其他synchronized方法/块或调用父类的synchronized方法/块都不会阻碍该线程的执行，就是说同一线程对同一个对象锁是可重入的，而且同一个线程可以获取同一把锁多次，也就是可以多次重入。因为java线程是基于“每线程（per-thread）”，而不是基于“每调用（per-invocation）”的（java中线程获得对象锁的操作是以每线程为粒度的，per-invocation互斥体获得对象锁的操作是以每调用作为粒度的）



##### 2.4 用锁来保护状态

> **错误**：只有在写入共享变量时才需要使用同步

```java
if (!vector.contains(element)){
  vector.add(element);
}
```

+ **注意：虽然contains 和 add方法都是原子方法，但是中间仍存在竞态条件**

- 因此，虽然synchronized方法可以确保单个操作的原子性，**<font color=red>但是如果要把多个操作合并为一个复合操作，还是需要额外的加锁机制</font>**



##### 2.5 活跃性与性能

> 滥用synchronized会导致不良并发

- 即并发变成了串行，没了多线程的性能优势

- 当执行时间较长的计算或者可能无法快速完成的操作时（网络I/O 或者 控制台I/O），一定不要持有锁。

---

### 第三章 对象的共享

#### 3.1 可见性

> 当一个线程修改了对象状态之后，其他线程能够看到发生的状态变化。

**Difference between sleep() and yield()**

- ```sleep()``` causes the thread to **definitely** **stop executing** for a given amount of time, if no other thread or process needs to be run, the CPU will be idle.

- ```yield()``` basically means that the thread **is not doing anything particularly important** and if any other threads or processes need to be run, they should.

  Otherwise, the current thread will continue to run.

**以下是书中的一个反例：**

```java
public class NoVisibility {
    private static boolean ready;
    private static int number;

    private static class ReaderThread extends Thread {
        public void run() {
            while (!ready)
                Thread.yield();
            System.out.println(number);
        }
    }

    public static void main(String[] args) {

        new ReaderThread().start();
        number = 42;
        ready = true;
    }
}
```

书本上说上述可能出现错误的结果。错误的结果有下面两种情况（我重现不到下面的结果）：

1. NoVisibility 可能会一直保持循环，因为对读线程来说，主线程写给 ready 的值可能永远对读线程不可见。
2. NoVisibility 可能会打印0，因为早在对 number 赋值之前，主线程就已经写入 ready 并使之对读线程可见，这是一种重排序。

**即可亲测没有发生，但是可能会发生。为了防止这种现象的发生，只能通过对共享变量进行恰当的同步。**



**非原子的64位操作**

- **<font color=red>在多线程中使用共享的可变的 long 和 double 类型变量时不安全的</font>**，除非用关键字 volatile 来声明他们，或者用锁保护起来。



**加锁与可见性**

<img src="/Users/Ethan/Desktop/Fannibals.github.io/JavaSMY/Screen Shot 2020-06-15 at 3.26.12 pm.png" alt="Screen Shot 2020-06-15 at 3.26.12 pm" style="zoom:40%;" />

- **为何要求所有线程在同一个锁上同步？**
  - 加锁的含义不仅仅局限于**互斥行为**，还包括**内存可见性**。为了确保所有线程都能看到共享变量的最新值，所有执行读操作或者写操作都必须在同一个锁上同步



#### Volatile变量

> 一种相对于Synchronized稍弱的同步机制，用来确保将变量的更新操作通知到其他线程。把变量声明为volatile后，不再会被与其他内存操作一起重排序

- 加锁机制可以同时确保可见性和原子性
- volatile变量只能确保可见性

**何时使用？**

- 当且仅当满足以下所有条件时：
  - 对变量的写入操作不依赖变量的当前值 / 只有单个线程在更新
  - 该变量不会与其他状态变量一起纳入不变性条件中
  - 在访问变量时**不需要加锁**



#### 3.2 Publish & Escape

> Publish一个对象指：使对象能够在当前作用域之外的代码中使用。
>
> 当某个不应该发布的对象被发布时，这种情况被称为Escape

- 不要在构造过程中使用this引用逸出。

**常见错误：**

- 在constructor中启动一个线程
- 在构造函数中调用一个可改写的实例方法
  - **这个使用this引用指向的对象在还没有完全构造完就调用其实例方法改变属性，可能出现问题。**比如外部类初始化的时候对一个int类型的参数赋值，然后doSomething(e)方法对该参数进行加1操作。如果onEvent()方法触发在外部类完成初始化之前怎么办？就会很尴尬。因为this引用提前被EventListener实例对象拿到。这个提前的拿到就是this引用的逸出。

**如何防止呢？**

- 使用工厂方法 (Factory Method) :

  - 详细代码见ThisEscape.java & SafeListener.java

- ```java
  public class SafeListener {
      private final EventListener listener;
  
      private SafeListener() {
          listener = new EventListener() {
              public void onEvent(Event e) {
                  doSomething(e);
              }
          };
      }
  
      public static SafeListener newInstance(EventSource source) {
          SafeListener safe = new SafeListener();	// 1
          source.registerListener(safe.listener); // 2
          return safe;
      }
    // ...
  }
  ```

- newInstance()**强制使得this引用无法提前被EventListener实例对象拿到**。因为registerListener是在new SafeListener()之后。



#### 3.3 线程封闭

> 一种实现线程安全性的最简单方式就是不共享数据，数据均为线程私有。

**3.3.1 Ad-hoc线程封闭**

- 指维护线程封闭性的职责完全由程序实现来承担



**3.3.2 栈封闭**

- 例如在方法内部声明且仅在内部使用的基本类型（局部变量）

  ```java
  public int load(){
  	int num = 0;
    num++;
  }
  ```



**3.3.3 ThreadLocal类**

> 用于维持线程封闭性的一种方法，使得线程中的某个值与保存值的对象关联起来

- 与```synchronized```的对照
  - synchronized是利用锁的机制，使变量或代码块在某一时刻仅被一个线程访问
  - ThreadLocal**为每个线程都提供了变量的副本**，使得每个线程在某一个时间访问到的并非同一个对象

```java
public class ThreadLocalExample {
    public static void main(String[] args) {

        MyRunnable sharedRunnableInstance = new MyRunnable();

        Thread thread1 = new Thread(sharedRunnableInstance);
        Thread thread2 = new Thread(sharedRunnableInstance);

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
class MyRunnable implements Runnable {
    private ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
    @Override
    public void run() {
        threadLocal.set((int) (Math.random() * 100D));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(threadLocal.get());
    }
}
```



#### 3.4 不变性

- 不可变对象一定是线程安全的

- 满足以下条件时，对象才是不可变的

  1. 对象创建以后状态就不能修改

  2. 对象的所有域都是final类型的

  3. 对象是正确创建的（this引用没有逸出）



#### 3.5 安全发布

- 要安全地发布一个对象，对象的引用以及对象的状态必须同时对其他线程可见。
- **一个正确构造的对象可以通过以下方式来安全地发布**：
  1. 在静态初始化函数中初始化一个对象引用
  2. 将对象的引用保存到volatile类型的域或者AtomicReference对象中
  3. 将对象的引用保存到某个正确构造对象的final类型域中
  4. 将对象的引用保存到一个由锁保护的域中



- 通常，要发布一个静态构造的对象，最简单安全的方式是使用静态的constructor：

  - ```java
    public static Holder holder = new Holder();
    ```

  - static constructor 由JVM在类的初始化阶段执行，由于JVM内部存在同步机制，因此可以安全发布

---

**几种懒汉式单例方法的安全性分析：**

1. 两种反例：

   ```java
         private static SingletonExample instance = null;
   			/** 私有构造器，不暴漏给外部*/
         private SingletonExample() {
         }
   			 /**
           * 基础版懒汉式单例 - 线程不安全
           */
          public static SingletonExample getInstance() {
              if (instance == null) {			// （1）
                  instance = new SingletonExample();		// （2）
              }
              return instance;
          }
      
          /**
           * 双重检查机制版懒汉式单例 - 仍然线程不不安全
           */
          public static SingletonExample getInstance1() {
              if (instance == null) {  // （3）
                  synchronized (SingletonExample.class){
                      if (instance == null) {  // （4）
                        		// (5)
                          instance = new SingletonExample();
                      }
                  }
              }
              return instance;
          }
   ```

- 对于基础版比较好理解，因为（1）和（2）之间多线程还会存在**竞价条件**，比如线程A完成（1）还没执行（2）时线程B也进来了，则（1）仍为true，因而会进行多次初始化操作

- 对于双重检查版来说，(5) 这个操作分为三步执行：

  - ```bash
    1.memory = allocate()   // 分配对象的内存空间
    2.ctorInstance()   // 初始化对象
    3.instance = memory   // 设置instance指向刚分配的内存
    ```

  - 但是多线程下，JVM可能会对上述操作进行指令重排：

    ```bash
    1.memory = allocate()   // 分配对象的内存空间
    3.instance = memory    // 设置instance指向刚分配的内存
    2.ctorInstance()    // 初始化对象
    ```

  - 这会导致一个问题，假设线程A进行到了 instance = memory这个操作，那么此时instance就非空，如果恰好此时线程B来到（3）处，**发现instance非空则会直接返回未初始化的instance**

**解决方法：**

1. volatile

```java
/**
 * 单例对象，使用 volatile 关键字禁止指令重排
 */
 private volatile static Singleton instance = null;
```

2. 使用静态内部类来实现单例

```java
public class SingletonExample2 {
    private SingletonExample2() {
    }

    public static SingletonExample2 getInstance() {
        return LazyHolder.INSTANCE;
    }
    
    private static class LazyHolder {
        private static final SingletonExample2 INSTANCE = new 	      
          SingletonExample2();
    }
}
```

- 从外部无法访问静态内部类LazyHolder，只有当调用Singleton.getInstance方法的时候，才能得到单例对象INSTANCE。
- INSTANCE对象初始化的时机并不是在单例类Singleton被加载的时候，而是在调用getInstance方法，使得静态内部类LazyHolder被加载的时候。因此这种实现方式是利用classloader的加载机制来实现懒加载，并保证构建单例的线程安全。

3. 枚举类
   - **<font color=orange>回看</font>**

---



### 第四章 对象的组合

 #### 4.1 实例封闭

> 若某对象不是线程安全的，那么可以通过多种技术使其在多线程程序中安全的使用
>
> - 确保该对象只能由单个线程访问（线程封闭）
> - 用一个锁来保护对该对象的所有访问

##### 使用实例封闭保证类安全性的例子

```java
public class PersonSet {

    private final Set<Person> mySet = new HashSet<Person>();

    public synchronized void addPerson(Person p) {
        mySet.add(p);
    }

    public synchronized boolean containsPerson(Person p) {
        return mySet.contains(p);
    }

    interface Person {}
}
```

- 这里我们假设Person是一个线程安全的类
- PersonSet的状态由HashSet来管理，而HashSet本身非线程安全，但是是私有的且不会Escape，因此HashSet被封闭在PersonSet中。
- 唯二可以访问mySet的两个代码都上了锁，因此执行他们时都要获得PersonSet上的锁。因此PersonSet是一个线程安全的类。



**4.1.1 Java监视器模式**

举一个TrackingVehicle的例子，想实现线程安全可以：

- 使用线程安全的Tracker + 线程不安全的MutablePoint
- 使用不安全的Tracker + 不可变的Point

**详细见VehicleTracker的代码：**

- <font color=red>前者保证了内部一致性，但是无法保证时时更新的需要，后者相反</font>



#### 4.2 线程安全性的委托

