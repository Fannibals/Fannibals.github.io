### Java 并发

#### 1. 并发的优势与风险

- **优势**
  + **速度、设计、资源利用**
- **风险**
  - **安全性、活跃性、性能(CPU)**

#### 2. CPU 多级缓存

- 时间局部性
- 空间局部性

#### 3. 线程状态

![Screen Shot 2020-02-26 at 12.12.31 pm](/Users/Ethan/Desktop/Fannibals.github.io/JavaSMY/Screen Shot 2020-02-26 at 12.12.31 pm.png)

**3.1 ThreadGroup**

> ThreadGroup可以对一批线程进行分类管理。线程组可以管理线程，也可以管理线程组，之间的关系类似于树形的结构。

**3.2 Daemon Thread**

> Daemon thread is a **low priority thread** that runs in background to perform tasks such as **garbage collection.**

+ 用来服务于**用户线程(我们平常所创建的普通线程)**

+ 当程序**只剩下守护线程的时候**，**JVM就退出了**

  ```java
  Demo1 d1 = new Demo1("first-thread");
  d1.setDaemon(true);
  ```

**3.3 线程的中断**

+ interrupt()

+ **如果直接interrupt会失败：**

  ```java
      @Override
      public void run() {
          while (true){
              System.out.println(getName()+"线程执行了");
              try {
                  sleep(200);
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
          }
      }
  ------------------
   		thread1.start();
  		thread1.interrupt();
  ```

  + **失败原因：**
    + sleep() 方法被中断后会清除中断标记，所以循环会继续运行。。
    + **需要处理interrupt sleep之后的InterruptedException**

+ **修改之后：**

  ```java
  while (!interrupted()){	// 判断是否终止
    	//   return currentThread().isInterrupted(true);
      System.out.println(getName()+"线程执行了");
      try {
          sleep(200);
      } catch (InterruptedException e) {
          e.printStackTrace();
          interrupt();	// 处理的时候执行中断
      }
  }
  ```



**3.4 多种创建线程的方式**

1. **继承Thread类**

2. **实现Runnable接口**

3. **匿名内部类的方式**

   + 问：是打印new Runnable 还是 new Thread？

   ```java
   new Thread(new Runnable() {
       @Override
       public void run() {
           System.out.println("new Runnable");
       }
   }){
       @Override
       public void run() {
           System.out.println("new Thread");
       }
   }.start();
   ```

   + 答：打印new Thread，虽然Thread的run方法里调用的是target(runnable)的run，但是这种情况下，**Thread override 了 父类方法（根据多态），走子方法的实现**

4. **带返回值的线程** -- Callable

   **FutureTask** -- implements **RunnableFuture** --- extends **Runnable**

   ```java
   Demo2 d = new Demo2(); // Demo2 implements Callable<T>
   FutureTask<Integer> task = new FutureTask<>(d);
   new Thread(task).start();
   System.out.println(task.get());
   ```

5. **定时器**

   ```java
   new Timer().schedule(new TimerTask() {
       @Override
       public void run() {
           System.out.println("Timertask is running");
       }
   },0,1000);
   ```

6. **线程池的实现** 

   ```java
   Executor threadPool = Executors.newFixedThreadPool(10);
   
   for (int i = 0; i < 20; i++) {
       threadPool.execute(new Runnable() {
           @Override
           public void run() {
            		// print ThreadName
           }
       });
   }
   ((ExecutorService) threadPool).shutdown();
   ```

7. **Lambda表达式实现**



**3.5 线程带来的风险**

+ 线程安全性问题
+ 活跃性问题
+ 性能问题



##### 1 - 活跃性问题

+ **死锁**
+ **饥饿**
  + 高优先级吞噬所有低优先级的CPU时间片
  + 线程被永久堵塞在一个等待进入同步块的状态
  + 等待的线程永远不被唤醒
+ **活锁**

**如何避免饥饿问题：**

+ 设置合理的优先级
+ 使用ReentrantLock代替synchronized 



##### 2- 线程安全性问题

+ **从Java字节码的角度去看的话：**
  + 如果两个线程同时进行（iadd）& （putField）操作
  + 如果没有任何安全机制，很可能当第一个线程add后没进行赋值，资源被第二个线程抢走然后也进行加一操作，而后有同时进行putfield操作
  + 本来因为进行了两次加一，总数应该加二，但是因为两次putfield都是put +1，因为总数最终只是加了一 
+ **线程安全性问题产生原因：**
  + 多线程环境下
  + 多个线程共享一个资源
  + 对资源进行非原子性的操作



##### 3- Synchronized原理以及使用

+ 内置锁：互斥的
  + Java中每个对象都可以用作同步的锁，这些锁可以被用作内部锁
+ synchronized **放在普通的方法**上，**内置锁就是当前类的实例**
+ synchronized **放在静态的方法**上，内置锁就是当前的Class字节码的对象
  + XXX.class
+ synchronized **在修饰代码块的时候**

任何对象都可以作为锁，锁信息则存在对象头中



**3.6 内存可见性**

<img src="/Users/Ethan/Desktop/Fannibals.github.io/JavaSMY/Screen Shot 2020-02-26 at 4.26.05 pm.png" alt="Screen Shot 2020-02-26 at 4.26.05 pm" style="zoom:67%;" />

+ 案例里因为while(true)太快了，main线程无法从主存当中去读最新的数据

  

**3.7 volatile 关键字**

> 当多个线程进行操作共享数据时，可以保证内存中的数据可见

+ 相较于synchronized是一种较为轻量级的同步策略。

**注意！！！**

+ **volatile 不具备互斥性**

+ **<font color=red>volatile 不能保证变量的''原子性“</font>**

  + 原因在于，i的读取和赋值是原子性的

  

##### i++的原子性问题：

+ ```java
  int i = 10; i = i++; // 10
  ```

  + int temp = i;
  + i = i+1;
  + i = temp;



**3.8 原子变量** 

+ Jdk1.5 之后，java.util.concurrent.atomic 包下提供了常用的原子变量
  + volatile 保证了内存**可见性**
    + **<font color=red>可见：一个线程修改了这个变量之后，在另外一个线程中能读到这个修改后的值。</font>**
  + **CAS (Compare And Swap) 算法保证了数据的原子性**

+ **CAS包含了三个操作数**
  + 内存值 V 		-- 第一次读
  + 预估值 A         -- 第二次从内存中读
  + 更新值 B
  + 当且仅当 V == A时，V = B，否则，将不做任何操作

+ 效率比同步锁要高：因为如果失败了可以立即再进行尝试

+ 怎么用？

  ```java
  private AtomicInteger ai = new AtomicInteger();
  ai.getAndIncrement(); // => i=i++;
  // 内部调用 unsafe.getAndAddInt(this,valueOffset,1);
  ```

#### AtomicInteger 为什么可以实现原子性？

+ AtomicInteger.getAndIncrement();
  + **内部调用 unsafe.getAndAddInt(this,valueOffset,1);**

+ 那么**Unsafe**类是什么？

#### Unsafe

> **是CAS的核心类**，由于Java是无法直接访问底层系统，**需要用native方法访问,**
>
> Unsafe相当于一个后门，基于该类可以直接操作特定内存的数据

+ Unsafe类中的所有方法都是native修饰的，也就是说Unsafe类中的方法都直接调用操作系统底层资源执行相应任务。

##### 变量valueOffset

+ 表示该变量值在内存中的偏移地址，因为Unsafe就是根据内存偏移地址来**获取数据的**，valueOffset字段表示内存位置

##### 变量value

+ 用volatile修饰，保证了多线程之间的内存可见性



**<font color=red>CAS是一条CPU并发愿与，依赖于硬件，因为原语的执行必须是连续的，在执行过程中不允许被中断，也就是说不会造成数据不一致的问题（原子指令）</font>**



**CAS 的缺点**

+ **循环时间长消耗大（while(!this.compareAndSwapInt())）**
  + 如果CAS失败，会一直进行尝试，如果长时间不成功，消耗有点大

+ 只能保证**一个共享变量**的原子性操作

+ **ABA问题**



**AtomicInteger的ABA问题**

+ ABA：狸猫换太子	--> 比较之间有时间差，可能会导致数据的变化
+ 以为没人动过，实际上动过（虽让recentvalue 和exceptedValue相等，但是其实中间做了一些操作）



**如何解决ABA问题？？？**



**AtomicReference<T>**

+ it provides an object reference variable which can be read and written atomically.

**AtomicStampedReference<T>**

+ 增加版本号控制

---

**3.9 重入锁、死锁、自旋锁**

**重入锁** （ 递归锁 ）

> 可冲入锁指的是**<font color=red>同一线程外层函数获取锁之后，内层递归函数自动获取锁</font>**。也就是线程能进入**任何一个它已经拥有的锁所同步着的代码块**。

+ ReentrantLock 和 synchronized 都是可重入锁。

+ **可重入锁最大的作用用来避免死锁。**



**自旋锁**

> 自旋锁是指尝试获取锁的**线程不会立即阻塞**，而是**采用循环的方式尝试获取锁**。好处是**减少线程上下文切换的消耗**，缺点是循环时会消耗CPU资源。

```java
public void myLock(){
  Thread thread = Thread.currentThread();
  // 自旋 ==》 
  while(!atomicReference.compareAndSet(null,thread)){};
}

public void myUnlock(){
	Thread thread = Thread.currentThread();
	atomicReference.compareAndSet(thread,null);
}
```



**3.10 公平锁 vs 非公平锁**

**ReetranLock(true);	-- 公平锁**

+ 按照申请的顺序来获取锁，先来后到
+ 在并发环境中，每个线程在获取锁时会先查看此锁维护的等待队列，如果为空/队列第一个就占有锁，否则加入queue中，FIFO

**ReetranLock() / ReetranLock(false);**- 相反，有可能会发生饥饿现象 



**3.11 独占锁(写锁)、共享锁(读锁)**

##### 独占锁

> 指该锁一次只能被一个线程所持有。
>
> 对ReentrantLock和Synchronized而言都是独占锁

**共享锁：指该锁可被多个线程所持有**

对ReentrantReadWriteLock**其读锁是共享锁，其写锁是独占锁**。



**java.util.concurrent**	--》 读写锁的实现

- 更细粒度的并发包

- ```java
  private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
  // 写锁
  rwLock.writeLock().lock();
  rwLock.writeLock().unlock();
  // 读锁
  rwLock.readLock().lock();
  rwLock.readLock().unlock();
  ```

  

**3.12 乐观锁 vs 悲观锁**

**<font color=red>乐观锁</font>**

> 顾名思义就是在操作时很乐观，认为操作不会产生并发问题, 因此不会上锁。
>
> 但是在更新时会判断其他线程在此期间内有没有对数据进行修改

+ **实现方式**

  + **版本号** 	

    + // couchdb
    + **AtomicStampedReference**

  + **CAS(compare and swap)**

    + 当多个线程尝试使用CAS同时更新同一个变量，只有其中一个线程能更新变量的值，而其他线程都失败。失败的线程并不会被挂起，而是被告知这次竞争中失败，并可以再次尝试。
    + **三个操作数**
      + **需要读写的内存位置`V`**
      + **进行比较的预期原值`A`**
      + **拟写入的新值`B`**

  + ```java
    atomicReference.compareAndSet(V expect, V update) ;
    ```

**Concurrent包实现CAS算法（乐观锁）**

仔细分析`concurrent`包的源代码实现，会发现一个通用化的实现模式：

1. 首先，声明共享变量为`volatile`；　　
2. 然后，使用CAS的原子条件更新来实现线程之间的同步；
3. 同时，配合以`volatile`的读/写和`CAS`所具有的`volatile`读和写的内存语义来实现线程



+ **缺点：**
  + ABA问题，循环时间开销大、只能保证一个共享变量的原子操作



**<font color=red>悲观锁</font>**

**java: synchronised 关键字**

**总结：**

**读的多，冲突几率小，乐观锁。**
**写的多，冲突几率大，悲观锁。**



**3.13 锁的升级**



---

## 数据库相关

### 1. 数据库事务的四大特性	- ACID

- **Atomicity（原子性）**
  - 整个事务中的所有操作要么全部提交成功，要么全部失败回滚
- **Consistency（一致性）**
  + 数据库总是从一个一致性状态转换到另一个一致性状态
  + 如果事务进行到一半时突然宕机，那么所有的事务都应该回滚到原来的状态
- **Isolation (隔离性)**
  - 通常来说，一个事务在最终提交之前，对其他事物是不可见的。
  - 关于事务的隔离性，数据库提供了多种隔离级别。
- **Durability (持久性)**
  - 一旦事务提交，则其所做的修改就会永久保存到数据库中。



### 2. 并发事务的问题

+ **脏读**
  + T1 正在修改事务，在中途T2 也进入事务进行修改（此时T2读到的数据是并不是最终版，T1有可能继续修改或者撤销事务）
+ **不可重复读  --- 修改**
  + 一个事务在读取某些数据后的某个时间，再次读取以前读过的数据，却发现其读出的数据已经发生了变更、或者某些记录已经被删除了

+ **幻读  ---  删除**
  + 一个事务**按相同的查询条件重新读取以前检索过的数据**，却发现**其它事务插入了满足其查询条件的新数据**。

**不可重复读 vs 幻读**

+ **不可重复读重点在于<font color=red>update和delete</font>，而幻读的重点在于<font color=red>insert</font>**
+ 可重复读中，该sql第一次读取到数据后，就将这些数据加锁，其它事务无法修改这些数据，就可以实现可重复读了。
+ 但这种方法却无法锁住insert的数据，所以当事务A先前读取了数据，或者修改了全部数据，事务B还是可以insert数据提交，这时事务A就会 发现莫名其妙多了一条之前没有的数据，这就是幻读，不能通过行锁来避免。<font color=red>**需要Serializable隔离级别** **，读用读锁，写用写锁，读锁和写锁互斥**</font>，这么做可以有效的避免幻读、不可重复读、脏读等问题，但会极大的降低数据库的并发能力。

### 3. 事务隔离级别

+ **Read Uncommited**
  + 所有事务都可以看到其他未提交事务的执行结果
+ **Read Commited**
  + 一个事务只能看到已经提交的事务所做的变更
+ **Repeatable Read**
  + 确保同一个事务的多个实例在并发读取数据时会看到相同的数据行
+ **Serializable**
  + 完全串行化读，每次读都需要获得表级别的共享锁，读写相互阻塞

| 隔离级别            | 脏读   | 不可重复读 | 幻读   |
| ------------------- | ------ | ---------- | ------ |
| **Read Uncommited** | YES    | YES        |        |
| **Read Commited**   | **NO** | YES        | YES    |
| **Repeatable Read** | **NO** | **NO**     | YES    |
| **Serializable**    | **NO** | **NO**     | **NO** |

- 避免不可重复读需要锁行就行
- 避免幻影读则需要锁表

### 4. 并发事务解决方案

- 当多个用户/进程/线程同时对数据库进行操作时，会出现3种冲突情形：

  1. 读-读，不存在任何问题

  2. 读-写，有隔离性问题，可能遇到脏读（会读到未提交的数据） ，幻影读等。

  3. 写-写，可能丢失更新

1. **锁机制**

   > 解决**写-写**冲突问题。在读取数据前，对其加锁，防止其它事务对该数据进行修改。

   + 悲观锁
   + 乐观锁

2. **MVCC多版本并发控制**

   >  解决**读-写**冲突问题。不用加锁，通过一定机制生成一个数据请求时间点时的一致性数据快照， 并用这个快照来提供一定级别 （语句级或事务级） 的一致性读取。这样在读操作的时候不需要阻塞写操作，写操作时不需要阻塞读操作。



### 5. CAP定理

1. 该定理指出，分布式计算机系统 **<font color=red>无法同时保证</font>**这三个属性：

   - **(Consistency) 一致性** 

     - 所有节点在同一时间看到相同的数据。

   - **(Availability) 可用性**

     -  保证每个请求都收到有关成功还是失败的响应。

   - **(Partition tolerance) 分区容错性**

     -  即便系统的任一部分丢失或发生故障，系统也能继续运行。

       

### 6. MVCC  - Multi-Version Concurrency Control

#### 1. Intro

+ **Writers don''t block readers**
+ **Readers don't block writers**

1. Read-only txns can read a **consistent snapshot** without acquiring locks
   + Use timestamps to determine visibility

2. Easily support **time-travel** queries



#### 2. Snap Isolation （SI）

> When a txn starts, it sees a **<font color=red>consistent snapshot</font>** of the db that existed when the txn started 

**Write Skew Anomaly**

+ ![Screen Shot 2020-03-01 at 3.49.00 pm](/Users/Ethan/Desktop/Fannibals.github.io/JavaSMY/Screen Shot 2020-03-01 at 3.49.00 pm.png)

+ it is not serializable, let's see what will happen if it is serialisable:
  + ![Screen Shot 2020-03-01 at 3.49.13 pm](/Users/Ethan/Desktop/Fannibals.github.io/JavaSMY/Screen Shot 2020-03-01 at 3.49.13 pm.png)

#### 3. MVCC Design Decisions

+ **Concurrency Control Protocol**
  + **Approach #1: Timestamp Ordering**
  + **Approach #2: Optimistic Concurrency Control**
  + **Approach #3: Two-Phase Locking**
+ Version Storage
+ Garbage Collection
+ Index Management



### 7. CouchDB 

**7.1 Big Data** 

+ 虽然关系型数据库可以很好的保证一致性问题，但是在大数据以及分布式环境下效率不高
+ NoSQL(NoSQL = Not Only SQL )，意即"不仅仅是SQL"。

**7.2 CouchDB**

- **Document-Oriented DBMS --- JSON**

+ **Cluster Architecture**

  <img src="/Users/Ethan/Desktop/Fannibals.github.io/JavaSMY/Screen Shot 2020-03-01 at 4.21.02 pm.png" alt="Screen Shot 2020-03-01 at 4.21.02 pm" style="zoom:40%;" />

  + All nodes answer requests (R/W) at the same time 
  + Sharding (splitting of data across nodes) is done at every node
  + when a node does not contain a document, the node requests it from another node, and return it to the client
  + **3 nodes, 4 shards and a replica of 2**

**7.3 CAP**

C: same answer

A: an answer

P: operating on rest

![Screen Shot 2020-03-01 at 4.27.04 pm](/Users/Ethan/Desktop/Fannibals.github.io/JavaSMY/Screen Shot 2020-03-01 at 4.27.04 pm.png)

**Consistency & Availability : Two Phase Commit**

<img src="/Users/Ethan/Desktop/Screen Shot 2020-03-01 at 4.31.41 pm.png" alt="Screen Shot 2020-03-01 at 4.31.41 pm" style="zoom:30%;" />

+ Start --> Prepare --> **if all return yes,** then commit ; else absort--> commit --> done 

  

####  Sharding 

> the partitionning of a DB **"horizontally"**

+ i.e. the db rows are partitioned into subsets that are stored on different servers.

+ **优点**
  + 分散computing load across nodes
  + easier to move data files around 

#### Replication

+ the action of storing the same row on different nodes 



#### MapReduce Algorithms

