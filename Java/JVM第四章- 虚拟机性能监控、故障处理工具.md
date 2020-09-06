## JVM第四章- 虚拟机性能监控、故障处理工具

### 1. 基础故障修理工具

#### 1.1 jps：JVM Process Status Tool

>  虚拟机进程状况工具

1. **功能**

   - 列出正在运行的虚拟机进程，并显示JVM执行主类(Main Class) 的名称以及这些进程的本地虚拟机唯一ID (LVMID: Local VM Identifier)

2. **命令格式**

   ```
   jps [options] [hostid]
   ```

3. **主要选项**

   | 选项 | 作用                                           |
   | ---- | ---------------------------------------------- |
   | -q   | 只输出LVMID，省略主类的名称                    |
   | -m   | 输出虚拟机进程启动时传递给主类mian()函数的参数 |
   | -l   | 输出主类的全名                                 |
   | -v   | 输出虚拟机进程启动时的JVM参数                  |

   

#### 1.2 jstat: JVM Statistics Monitoring Tool

> 虚拟机统计信息监视工具

1. **功能**

   - 用于监视虚拟机各种运行状态信息的命令行工具
     - 如：类加载、内存、GC、即时编译等运行时数据

2. **命令格式**

   ```
   jstat [option vmid [interval[s|ms] [count]]
   ```

   interval : 查询间隔

   count ：查询次数

   ```
   jstat -gc 2764 250 20
   ```

   - 代表：以每250毫秒查询一次进程2764的gc情况，一共查询20次

3. 主要选项

   | 选项      | 作用                                                  |
   | --------- | ----------------------------------------------------- |
   | -class    | 监视类加载、卸载数量、总空间&类加载所耗费的时间       |
   | -gc       | 监视Java堆情况，包括Eden区、S0,S1, Old and meta space |
   | -gcutil   | Same as -gc, but print as percentages                 |
   | -compiler | 输出即时编译器编译过的方法、耗时等                    |

4. 举个例子：

    S0   S1   E   O   M   CCS  YGC   YGCT  FGC  FGCT   GCT  

    93.76  0.00  0.00  1.21 67.69 70.03   2  0.002   0  0.000  0.002

   - E 表示 Eden， O 表示Old，M表示Meta，后面的C代表Cost，T代表Time

#### 1.3 jinfo：Configuration Info for Java

> Java配置信息工具

1. 功能

   - 实时查看和调整虚拟机的各项参数。

2. 命令格式

   ```
   jinfo [option] pid
   ```

3. 主要选项

   | 选项               | 作用                                    |
   | ------------------ | --------------------------------------- |
   | -flag <name>       | to print the value of the named VM flag |
   | -flag [+\|-]<name> | to enable or disable the named VM flag  |
   | -flags             | to print VM flags                       |
   | -syspros           | to print Java system properties         |

   

#### 1.4 jmap: Memory Map for Java

> Java 内存映像工具

1. 功能
   - 用于生成堆转储快照（headdump / dump文件)
   - 查询finalize执行队列、Java堆和方法区的详细信息

2. 命令格式

   ```
   jmap [option] vmid
   ```

3. 主要选项

   | 选项           | 作用                                                   |
   | -------------- | ------------------------------------------------------ |
   | -dump          | 生成Java堆转dump                                       |
   | -finalizerinfo | 显示在F-Queue中等待Finalizer线程执行finalize方法的对象 |
   | -heap          | 显示Java堆详细信息                                     |
   | -histo         | 显示堆中对象统计信息                                   |



#### 1.5 jhat: JVM heap Analysis Tool

> 虚拟机堆转储快照分析工具

1. 功能
   - jhat与jmap搭配使用，用来分析jmap生成的dump文件
2. 不过很繁琐，更推荐其他的如VisualVM等工具





#### 1.6 jstack：Stack Trace for Java

> Java堆栈跟踪工具

1. **功能**
   - 用于生成虚拟机当前时刻的线程快照（一般称为threaddump or javacore文件）
   - 线程快照就是当前虚拟机内每一条线程正在执行的方法堆栈的集合。
     - **<font color=red>目的通常是定位线程出现长时间停顿的原因，如死锁、死循环等</font>**

2. **命令格式**

   ```
   jstack [option] vmid
   ```

3. 主要选项

   | 选项 | 作用                                         |
   | ---- | -------------------------------------------- |
   | -F   | 当正常输出的请求不被响应时，强制输出线程堆栈 |
   | -l   | 除堆栈外，显示关于锁的附加信息               |
   | -m   | 如果调用到本地方法的话，可以显示C/C++的堆栈  |

---

⚠️：在真正使用时发现，像jmap这类的命令无法直接使用，需要配合jhsdb一起使用：

```
jhsdb jmap --heap --pid 1562
```





