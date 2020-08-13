### Java OOM异常 & Java Object Life Cycle

> 除了程序计数器外，虚拟机内存的其他姐运行时区域都有发生OutOfMemoryError(OOM) 的可能

**本次测试所使用的VM arguments:**

- ```-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:+HeapDumpOnOutOfMemoryError```

  + 启动参数
    + -verbose:gc（输出每次GC的相关情况）
  + -Xms & -Xmx (初始堆大小 & 最大堆大小)

  + -Xmn (年轻代大小)
  + -XX:+PrintGCDetails （打印GC细节）

  + -XX:SurvivorRatio （Eden / Survivor）
  +  -XX:+HeapDumpOnOutOfMemoryError （出现OOM时snapshot）

<a href="https://plumbr.io/blog/garbage-collection/understanding-garbage-collection-logs">Understanding GC logs</a>

1. **Heap OOM**

- CODE

  ```java
  public class HeapOOM {
      static class OOMObject{
      }
      public static void main(String[] args) {
          List<OOMObject> list= new ArrayList<OOMObject>();
          while (true){
              list.add(new OOMObject());
          }
      }
  }
  ```

+ Above code will cause <font color=red>java.lang.OutOfMemoryError: Java heap space</font>
  + 意思：堆空间溢出。**老年代**区域剩余的内存，已经无法满足将要晋升到老年代区域的对象大小，会报此错。

+ To solve this Error, the first thing is to check it is caused by One of Them:
  + Memory Leak 
  + Memory Overflow



2. **Stack OOM**

- 异常情况：
  - 如果线程请求的栈深度 > 虚拟机所允许的最大深入 --> StackOverflowError
  - 若虚拟机栈内存可动态扩展，但是无法申请到足够内存时 --> OOM
- HotSpot虚拟机不支持扩展，因而不会产生OOM。

+ 如果是开启过多线程导致的OOM，应通过减少最大堆 & 减少栈容量来换取更多的线程。



3. **Method Area & Constant Pool OOM**

- ```String::intern()``` is a native function
  - It returns the canonical representation of string.
  - It can be used to return string from memory, if it is created by new keyword. It creates exact copy of heap string object in string constant pool.



**永久代 & 元空间**

+ 两者都是方法区的实现

- 对于Java8， HotSpots取消了永久代，那么是不是也就没有方法区了呢？当然不是，**方法区是一个规范**，规范没变，它就一直在。那么取代永久代的就是元空间。它可永久代有什么不同的？
  - **存储位置不同**，**永久代物理是是堆的一部分，和新生代，老年代地址是连续的**，而元空间属于**本地内存**；
  - **存储内容不同**，元空间存储类的元信息，静态变量和常量池等并入堆中。相当于永久代的数据被分到了堆和元空间中。

---

### Java对象的生命周期

1. **Created**
   - allocate memory for the new object
   - create the object
   - init static vars (from super to sub classes)
   - init vars (from super to sub classes)
2. **In Use**
   - the object is held by at least one strong refs
3. **Invisible ???**
   - 执行已经超出了该对象的作用域了
4. **Unreachable**
   - the object is not held by any strong refs any more
5. **Collected**
6. **Finalized**
   - 当对象执行完finalize()方法后仍然处于不可达状态时，则该对象进入终结阶段。在该阶段是**等待垃圾回收器对该对象空间进行回收**。
7. **De-allocated**



**关于WeakReference**

+ ```java
  byte[] cacheData = new byte[100 * 1024 * 1024];
  WeakReference<byte[]> cacheRef = new WeakReference<>(cacheData);
  cacheData = null;
  System.gc();
  ```

+ 弱引用关联的对象是否回收取决于这个对象有没有其他强引用指向它。

+ 这个确实很难理解，既然弱引用关联对象的存活周期和强引用差不多，那直接用强引用好了，干嘛费用弄出个弱引用呢？其实弱引用存在必然有他的应用场景。

+ **WeakHashMap**

  + A **hashtable-based** implementation of the map interface, with keys that are of a **WeakReference** type.
  + An entry in a **WeakHashMap** will automatically be removed when its key is no longer in ordinary use.

+ 

