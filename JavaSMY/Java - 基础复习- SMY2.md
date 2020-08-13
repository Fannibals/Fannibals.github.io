### Java - 基础复习- SMY2

1. **bit & byte**
   + 二进制：0 /1 bit，是计算机中最小的存储单位
   + 1 **byte** = 8 bits, 是计算机中最基本的存储单元

2. **JDK & JRE**

   + Java Development Kit 
     + 开发工具，如javac jar，包含JRE，

   + Java Runtime Environment
     + 包括JVM & 核心类库等

   + **JDK = JRE + 开发工具类**
   + **JRE = JVM + JavaSE标准类库**

3. 环境变量
4. **进制间转换**
   + **二进制 (binary)**    
     + 0 & 1，满二进一，以0b / 0B开头
   + **十进制 (decimal)**
   + **八进制 (octal)**
     + 0-7，以数字0开头表示
   + **十六进制 (hex)**
     + 0-9 & A-F，满16进1，以0x / 0X开头表示，A-F不区分大小写

5. **原码，反码，补码**

   + 反码 = 原码 -- 除了符号位外，各个位置取反
   + 补码 = 反码 + 1

   + **计算机底层都以补码的方式来存储数据**

6. **十进制 --> 二进制**
   + **除2取余的逆**  
7. **单例的设计模式**

> 保证在整个软件系统中，对某个类只能存在一个对象实例，并且有提供一个取得该类实例的方法。

**<font color=red>优点：因为只生产了一个实例，减少了系统性能开销</font>**

应用场景：数据库连接池



##### 如何实现？

**第一种 ：饿汉式**

```java
class Bank {
  // 1. 私有化类的构造器
  private Bank(){}
 
  // 2. 内部创建类的对象，声明为静态
  private static Bank instance = new Bank();
  
  // 3. 提供公共的静态方法，返回类的对象
  public static Bank getInstance(){
    return instance;
	}
}
```

**第二种：懒汉式**

```java
class Singleton {
  // 1. 私有化类的构造器
  private Singleton(){}
 
  // 2. 内部创建类的对象，声明为静态
  private static Singleton single;
  
  // 3. 提供公共的静态方法，返回类的对象
  public static Bank getInstance(){
    if (single == null) {
      single = new Singleton();
    }
    return single;
	}
}
```

**对比：**

+ 饿汉式：
  + Pros: long loading time 
  + Cons: Thread-safety
+ lazy
  + pros: postpone the creation of the instance
  + cons: should consider the problem of thread safety

8. **设计模式：代理模式**
   + https://www.youtube.com/watch?v=hNHutM_k7EM&list=PLmOn9nNkQxJH0qBIrtV6otI0Ep4o2q67A&index=353
   + https://www.jianshu.com/p/2f518a4a4c2b

9. **Java 内存结构**
   + **Stack 栈**
     + 局部变量: 方法内变量均为局部变量
   + **heap 堆**
     + new出来的结构：对象，数组
     + 类的成员变量
   + **方法区**
     + 常量池 + 静态域

+ <img src="/Users/Ethan/Desktop/Fannibals.github.io/JavaSMY/Screen Shot 2020-02-18 at 11.36.59 am.png" alt="Screen Shot 2020-02-18 at 11.36.59 am" style="zoom:50%;" />

----

#### 数组

> 数组（Array）是由多个相同类型数据按一定顺序排列的集合，并使用一个名字命名，index来管理

+ **特点：**
  + 有序排列
  + 创建数组对象会在内存中开辟一整块连续的空间
  + 数组长度一旦确定，无法修改

+ **声明与初始化：**

  + **静态初始化：**

    + ```java
      int[] ids;
      ids = new int[]{1001,1002,1003};
      int ids = {1001,1002,1003}; // same as above
      ```

  + **动态初始化：**

    + ```java
      String[] names = new String[5];
      ```

##### 二维数组：

​	数组属于引用数据类型，```int[][] arr2 = new int[5][];```

+ **声明与初始化：**

  + **静态初始化：**

    + ```java
      int[][] arr1 = new int[][]{{1,2,3},{3,4,5},{4,5,6}};
      ```

  + **动态初始化：**

    + ```java
      String[][] arr2 = new String[3][2];
      String[][] arr3 = new String[3][];
      ```

+ **默认初始化值**

  + 二维数组分为**外层数组元素 & 内层数组元素**

  + ```java
    String[][] arr2 = new String[3][2];
    // 外层：地址值
    // 内层：与一维数组相同
    String[][] arr3 = new String[3][];
    // 外层：null
    // 内层：不能调用
    ```

**Arrays工具类**

+ equals, toString, fill, sort, binarySearch

#### 数据结构

1. 数据与数据之间的逻辑关系：集合、一对一、一对多、多对多

2. **数据的存储结构：**

   + **线性表**：一对一关系
     + 顺序表（数组）、链表、栈、队列

   + **树形结构：**
     + 二叉树
   + **图形结构**



#### 十大内部排序算法

+ 选择排序

+ 交换排序

  + **冒泡排序(n^2)：稳定**
    + 思路：依次比较相邻元素的排序码，

  + **快速排序(n*logn)：不稳定**

    + ```java
      public void quickSort(int[] nums,int low,int high){
          if (low < high){
              int k = partition(nums,low,high);
              quickSort(nums,low,k-1);
              quickSort(nums,k+1,high);
          }
      }
      
      public int partition(int[] nums,int low,int high){
          int pivot = nums[high];
          int i = low;
          for (int j=low;j<high;j++){
              if (nums[j] < pivot){
                 swap(nums,i,j);
                  ++i;
              }
          }
          swap(nums,i,high);
          return i;
      }    
      ```

+ 插入排序

+ **归并排序**

+ 桶排序

+ 基数排序





#### 类 CLASS

1. **属性(成员变量) vs. 局部变量** 

   + **相同点**

     + 定义变量的格式
     + 都有其作用域

   + **不同点**

     1. **声明位置不同：**

        + **属性：直接定义在类的{}内；**

        + **局部变量：声明在方法内，方法形参，代码块内，构造器parm & 内部var**

     2. **权限修饰符的不同**

        + 属性可以使用修饰符，local var不可以

     3. **有无初始值**
        + 属性有初始值，局部变量没有
     4. **内存**
        + 属性：（非static）加载到堆空间中
        + 局部变量：加载到栈空间中



#### 封装性的体现，需要权限修饰符的配合

+ 4种权限修饰符

  + private, default ,protected, public

  + | Modifier    | Class | package | Subclass (diff pack) | World |
    | ----------- | ----- | ------- | -------------------- | ----- |
    | Private     | +     |         |                      |       |
    | no modifier | +     | +       |                      |       |
    | Protected   | +     | +       | +                    |       |
    | Public      | +     | +       | +                    | +     |

    可以用来修饰：属性、方法、构造器、内部类

  + 修饰类的话：没有 / public

+ 构造器
  + 作用：创建对象，初始化对象的信息

+ **this关键词**

  + 使用：用来修饰：属性、方法、构造器
  + **this理解为：当前对象 或 正在创建的对象（构造器）**
  + 一般都省略：除非属性和形参名字相同

  + **this可以调用构造器** --> 此时必须声明在当前构造器首行

    ```java
    public class Person{
      String name;
      int age;
    	public Person(){
        
      }
      
      public Person(String name){
        this();
        this.name = name;
      }
      
      public Person(String name, int age){
        this(name);
        this.age = age;
      }
    }
    ```



#### Package & import关键字

+ package
  + 为了更好的管理项目中类，提出的概念
  + 相同包里不能命名相同的借口、类
+ JDK中主要的包
  + java.lang / java.util
  + java.net/ java.io / 





#### MVC， MVP，MVVM 设计模式

##### MVC

> 将整个程序分为三层：Model， View，Controller
>
> model：主要处理数据
>
> view：显示数据
>
> controller：处理业务逻辑

<img src="/Users/Ethan/Desktop/Fannibals.github.io/JavaSMY/Screen Shot 2020-02-18 at 4.42.58 pm.png" alt="Screen Shot 2020-02-18 at 4.42.58 pm" style="zoom:60%;" />

![Screen Shot 2020-02-18 at 4.33.12 pm](/Users/Ethan/Desktop/Fannibals.github.io/JavaSMY/Screen Shot 2020-02-18 at 4.33.12 pm.png)

Sometimes, view directly communicates with model

+ **pros**
  + **No business logic in UI**
  + **Easier to unit test**
+ **Cons**
  + **Doesn't scale, separates UI but not model**
  + **Controller often grows too big**

##### MVP

> Model : 数据处理部分
>
> View：界面显示部分
>
> Presenter：逻辑处理部分

![Screen Shot 2020-02-18 at 4.36.58 pm](/Users/Ethan/Desktop/Fannibals.github.io/JavaSMY/Screen Shot 2020-02-18 at 4.36.58 pm.png)

![Screen Shot 2020-02-18 at 4.38.34 pm](/Users/Ethan/Desktop/Fannibals.github.io/JavaSMY/Screen Shot 2020-02-18 at 4.38.34 pm.png)



#### Junit 单元测试

+ Java类：public
  + 提供公共的无参的构造器
+ 此类中声明单元测试方法
  + 无返回值，无形参
+ 方法上面写上注解@Test