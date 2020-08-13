## Java Network SMY

### 1. 对象流

> **ObjectInputStream & ObjectOutputStream** 
>
> 用于存储和读取**基本数据类型**数据或**对象**的处理流
>
> Object <--> 数据源

####1.1 序列化 & 反序列化

+ **序列化**：用ObjectOutputStream类**保存**基本数据类型或对象的机制

+ **反序列化：**用ObjectInputStream类**读取**基本数据类型或对象的机制

  

#### 1.2 对象流的使用

要想一个java对象是可序列化的，需要满足相应的要求。

**第一步：需要实现以下两个接口之一：**

+ **Serializable**
+ **Externalizable**



**第二步：提供serialVersionUID**

**凡是实现Serializable接口的类都有一个表示序列化版本标识符的静态变量**

+ 此UID用来表明类的不同版本之间的兼容性。简言之，目的是以序列化对象进行版本控制。

如果不显式的声明的话，如果序列化之后对类进行了改变，有可能步伐反序列化（因为UID跟着变了）

+ **<font color=red>简单来说，Java序列化机制是通过在运行时判断类的serializableUID来验证版本的一致性。在反序列化时，JVM会把传来的字节流中的UID与本地UID进行对比，如果相同则没问题，可以反序列化，否则不可以（InvalidCastException）。</font>**



#### NIO

> new IO / Non-Blocking IO

NIO 支持面向缓冲区 --> 更高效的读写

**NIO.2 JDK 7**



**java.nio.channels.Channel**

+ **FileChannel**
+ **SocketChannnel**
+ **ServerSocketChannel**

---



### Java 网络编程

+ IP：唯一的标识Internet上的计算机
+ 在Java中使用InetAddress类来代表IP
+ IP分类：IPV4，IPV6；万维网、局域网 

+ 127.0.0.1: 



