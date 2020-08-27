### Java SMY3

#### 多态性

> 理解多态性：一个事物的多种形态
>
> 何为多态性：对象的多态性 --》父类的引用只想子类的对象

**多态的使用：虚拟方法调用**

**多态性的使用前提：1）类的继承关系 2）方法的重写**

**多态性不适用于属性，只适用于方法**

**注意：<font color=red>多态是运行时行为, 动态绑定，编译时无法确定</font>**



#### <font color=red>重载(overload)与重写(override)</font>

+ 从编译和运行的角度看
+ 重载：指允许存在多个同名方法，参数不同。编译器根据方法不同的参数，对同名方法的名称作修饰。**<font color=red>调用地址在编译期就绑定了</font>**
  + **早绑定 / 静态绑定**

+ override-多态：只有等到方法调用的那一刻，编译器才会确定所要调用的具体方法
  + **晚绑定 / 动态绑定**
+ 具体规则：
  + 方法名、形参列表相同
  + 权限修饰符 （可以大一些）
  + 返回值（不能大于父类的返回值）
  + 异常

+ super & this
+ this本类构造器，super父类构造器
  + 都要写在首行，必须二选一

 

#### 向下转型

+ 使用原因：想要使用子类的独特方法/属性

+ 如何实现：使用**强转符：()**

+ 可能出现的问题：**ClassCastException** 

+ 怎么解决？

  + 在使用强转前使用```instanceof```来进行向下转型的判断

    ```
    if (p instanceof Man) {
    	Man m = (Man) p; 
    }
    ```

    

+ 注意：

  + **父类引用指向子类对象，而子类引用不能指向父类对象**

  ```java
  Father f1 = new Son(); // 这就叫 upcasting （向上转型)
  
  // 现在f1引用指向一个Son对象
  
  Son s1 = (Son)f1; // 这就叫 downcasting (向下转型)
  
  // 现在f1还是指向Son对象
  --------------------------------------------------
  Father f2 = new Father();
  
  Son s2 = (Son)f2; // 出错
  ```



![Screen Shot 2020-02-18 at 7.45.28 pm](/Users/Ethan/Desktop/Fannibals.github.io/JavaSMY/Screen Shot 2020-02-18 at 7.45.28 pm.png)

#### == 和 equals() 有何区别？

+ ```==``` :

  + 是一个运算符

  + 可以用于**基本数据类型和引用数据类型**, 

    对于**基本类型**就是**<font color=red>比较值</font>**，对于**引用类型**就是**<font color=red>比较内存地址</font>**

+ ```equals()```

  + 是一个方法
  + **只能使用在引用数据类型** 

  + 属于java.lang.Object类里面的方法，若未被override，默认为==

    + ```java
      public boolean equals(Object obj) {
      	return (this == obj);
      }
      ```

  + 被很多类（String，Date等）重写

#### 面试题：重写equals()方法

```java
class User{
	String name;
	int age;
  
  public boolean equals(Object obj){
    if (obj == this) return true;	// 引用相同否？
    if (obj instanceof User) {
      User u = (User) obj;
      return this.age == u.age &&
        this.name.equals(u.name);
    }else{
      return false; // 压根不是一个类，false
    }
	}
}
```



#### 基本数据类型

<img src="/Users/Ethan/Desktop/Fannibals.github.io/JavaSMY/Screen Shot 2020-02-18 at 7.56.54 pm.png" alt="Screen Shot 2020-02-18 at 7.56.54 pm" style="zoom:100%;" />



#### 面试题：基本数据类型、包装类和String三者之间如何转换？

**基本 <--> 包装 ：自动装箱、自动拆箱**

+ 自动装箱 替代了 **new** xxx() 构造器
+ 自动拆箱 替代了xxxValue()

**toString() & valueOf()**



#### Static

+ 可以用来修饰：属性、方法、代码块、内部类

1. 用Static修饰**属性** -- 静态变量
   + 静态属性 vs. 实例变量

+ **注意：**
	+ 静态变量随着类的加载而加载。可以通过“类.静态变量”的方式进行调用
  + 静态变量的加载要早于对象的创建
  + 由于类只会加载一次(加载到方法区)，静态变量在内存中也只会存在一份，存在方法区的静态域中。

2. **静态方法**

   + 随着类的加载而加载，可以通过“类.静态方法”的方式进行调用
   + 静态方法中，只能调用静态方法/属性
     + 主要是因为**生命周期**不同

+ 注意：

  + 静态方法内，**不能使用this/super 关键字**
    + 因为this&super 是操作实例对象的，而静态方法与类相关

  + **生命周期！** **生命周期！** **生命周期！**



#### 问题：开发中，如何确定一个属性/方法是否声明为static？

1. 属性是否声明为static？
   + 属性可以被多个对象共享，不会随着对象的不同而改变
2. 方法是否声明为static？
   + 操作静态属性的方法 
   + 工具类中的方法，习惯声明为static



#### 关于main()方法

+ 程序入口、static方法、可以作为我们与控制台交互的方式
+ **String[] args** 
+   值传递机制：
  + 基本数据类型：传数据值
  + 引用数据类型：传地址值



#### 类的成员：属性、方法、构造器、代码块

##### <font color=red>代码块</font> -- 初始化块

+ 作用：用来初始化类、对象的信息

+ **只可以被static 修饰** 

+ **静态代码块 vs. 非静态代码块**

+ | 静态代码块                                      | 非静态代码块                                     |
  | ----------------------------------------------- | ------------------------------------------------ |
  | 内部可以有输出语句                              | 内部可以有输出语句                               |
  | 随着**<font color=red>类的加载</font>**而执行。 | **随着<font color=red>对象的创建</font>而执行**  |
  | **只执行一次**                                  | 每次有新的对象创建都会执行一次                   |
  | 作用：初始化类的信息                            | 作用：可以在创建对象时，对对象的属性等进行初始化 |



#### final 关键字

+ 可以用来修饰的结构：类、方法、变量

  | final 修饰 | 类               | 方法              | 变量       |
  | ---------- | ---------------- | ----------------- | ---------- |
  | 继承       | 不能被其他类继承 | 不能被重写        | 变成“常量” |
  | ex.        | String类         | Object.getClass() |            |

+ **修饰变量**

  + 显式初始化、代码块中赋值/初始化、构造器中初始化

+ **static final**  来修饰变量 --> 全局常量

  ​								



#### <font color=red>抽象类 & 抽象方法</font>

> 如果一个类中没有包含足够的信息来描绘一个具体的对象，没有具体实例，这样的类就是抽象类。

1. abstract 可以用来修饰：**类和方法**

**abstract修饰类：抽象类**

+ **此类<font color=red>不能实例化</font>** 
+ 抽象类中一定有构造器，便于子类init
+ 开发中，都会提供抽象类的子类，让子类对象实例化



**abstract修饰方法：抽象方法**

+ 抽象方法只有**方法的声明，没有方法体**
+ 包含抽象方法的类，一定是一个抽象类，反之抽象类中可以没有抽象方法

+ **<font color=red>若子类重写了父类中的所有抽象方法后，此子类方可实例化</font>**

  **<font color=red>若子类没有重写了父类中的所有抽象方法，则此子类也是一个抽象类，需要使用abstract修饰</font>**
  + 这里的父类不仅仅只是直接父类，还有间接父类



**abstract使用上的注意点：**

+ 不能用来修饰：属性、构造器
+ **不能用来修饰：私有方法、静态方法、final方法**

+ 子类和父类中的同名同参数的方法要么都声明为非static的 / 要么都是static的

  + 只有都是非静态才算重写

  



#### 匿名对象

+ 理解：我们创建的对象，没有显式的赋给一个变量名，即为匿名对象
+ 特征：匿名对象只能调用一次
+ 使用：实际上还是在堆里创建了对象，赋给了parameter

+ 抽象类的匿名子类：



#### 接口

+ 理解：希望继承于多个类，但是java不支持多重继承
+ **接口的本质是契约、标准、规范**

+ **When (JDK <=7)**
  + 只能定义全局常量和抽象方法	
    + **public static final         public abstract**
    + 定义变量可简化为 **int NUM = 0; == public static final int NUM =  0;**

+ **(JDK >=8)**

  + 还可以定义**静态方法、默认方法**

  + ```java
    public interface CompareA {
      // 静态方法
    	public static void method1(){
        System.out.println("static method1");
      }
      // 默认方法 -- default这个关键字要写出来
      public default void method2(){
        System.out.println("default method2");
      }
      default void method3(){
        
        System.out.println("default method3");
      }
    }
    ```

+ **<font color=red>重点！!  ！</font>**

  + **<font color=blue>接口中定义的静态方法，只能通过借口来调用！！！</font>**
  + **<font color=purple>通过实现类的对象，可以调用接口的默认方法</font>**
  + 如果子类/实现类继承的父类和实现的接口中声明了同名同参数的方法
    + **类优先原则，默认使用父类的方法**
  + 如果实现类实现多个接口，而多个接口中定义了同名同参数的方法
    + **接口冲突**
    + 如何解决？ --> 重写 override

+ 接口中不能定义构造器

实现类只有实现接口的所有抽象方法，才可以实例化，否则还是一个抽象类。

+ 接口之间可以继承

+ 接口的使用可以体现多态性

---

1. **接口的应用**

+ **代理模式(proxy)**

  + 代理设计就是为其他对象提供一种代理以控制这个对象的访问

  + **应用场景：**

    + **安全代理：**
    + **远程代理： RMI**

    + **延迟加载：**

  + **分类：**

    + **静态代理：**
    + **动态代理**

+ **工厂模式()**





#### 内部类

> 当一个事物的内部，还有一个部分需要一个完整的结构进行描述，而这个内部的完整的结构有只为外部事物提供服务，那么这个内部的完整结构最好使用内部类

1. 内部类的分类：
   + 成员内部类 (静态、非静态) vs. 局部内部类（方法内、代码块内、构造器内）

2. 成员内部类

   + 一方面，**作为外部类的成员**
     + 调用外部类的结构 -- OutterClass.this.function/properties
     + 可以被static修饰
     + 可以被四种不同的权限修饰

   + 另一方面，**作为一个类**
     + 类内可以定义属性、方法、构造器等
     + 可以被final修饰
     + 可以被abstract修饰

3. 关注点

   1） 如何实例化

   + 静态成员内部类

     ```java
     Person.Inner inner = new Person.Inner();
     ```

   + 非静态成员内部类

     ```java
     Person p = new Person();
     Person.Inner2 inner2 = p.new Inner2();
     ```

   2） 如何在成员内部类中区分调用外部类的结构

   + ```java
     public void display(String name){
       System.out.println(name);
       System.out.println(this.name);
       System.out.println(Person.this.name);
     }
     ```

   + from top to bot : argument, inner class's name ; outer class's name

   3） 开发中局部内部类的使用

   + ```java
     // 返回一个实现了Comparable接口的类的对象
     public Comparable getComparable(){
         // 创建了一个实现了Comparable接口的类：局部内部类
         class MyCom implements Comparable{
           @Override
           public int compareTo(Object o) {
             return 0;
           }
         }
     
       	return new MyCom();
     }
     ```

     



#### String 

##### String 概念

1. String 声明为final的，不可被继承

2. String 实现了Serializable接口：表示字符串是支持序列化的	     

   ​					  Comparable接口：表示String可以比较大小

3. String内部定义了final char[] value 用于存储字符串数据

4. String：代表了不可变的字符序列 → 不可变性

   + 当字符串**重新赋值**时，需要重写指定内存区域赋值
   + 当对现有的字符串**进行连接操作**时，也需要重写指定内存区域赋值
   + 当调用**String的replace()方法**，同样

5. 通过字面量的方式（区别于new）给一个字符串赋值，此时的字符串值声明在**字符串常量池**中字符串常量池不会存放相同内容的字符串// String s3 = new String(char[] a); 



##### String 实例化

1. 字面量 String s1 = “javaEE”;
   + 此时s1的数据声明在方法区中的字符串常量池中
2. 通过new+构造器的方式 String s2 = new String(“javaEE”);
   + S2保存地址值，是数据在堆空间中开辟空间以后对应的地址值



###### String 拼接操作

1. 常量与常量的拼接结果在常量池。而且常量池中不会存在相同内容的常量
2. 只要其中一个是变量，结果就在堆中
3. 如果拼接的结果调用intern()方法，返回值就在常量池中

+ JDK 1.6 的时候字符串**常量池在方法区（永久代）**
+ JDK 1.7字符串**常量池在堆里**
+ **JDK 1.8**字符串常量池回到方法区（元空间）-- native memory



![Screen Shot 2020-02-20 at 1.40.02 pm](/Users/Ethan/Desktop/Fannibals.github.io/JavaSMY/Screen Shot 2020-02-20 at 1.40.02 pm.png)



**String ←→ byteString (byte[]) / getBytes()**

**UTF-8 : 一个汉字占三个字节**
**StringBuffer & StringBuilder**

**StringBuffer sb1 = new StringBuffer(); //new char[16];** // 长度为16的char array

**<font color=red>扩容问题：如果添加的数据地城数组放不下了，默认情况下扩容为原来容量的2倍 + 2，同时将原有数组的元素复制到新的数组中</font>**

**<font color=green>// （value.length << 1） +２</font>**



#### Java 比较器

> Java对象，在正常情况下，只能进行比较：== / !=。
>
> 如果想比较Java对象的大小，就要使用比较器
>
> 通过实现以下接口中的任意一个：**Comparable / Comparator**

1. **自然排序：Comparable -- java.lang.Comparable**

   + 像String, 包装类等实现了Comparable接口，重写了compareTo()
   + 重写compareTo(Object obj)：
     + 如果当前对象this > obj → return 正整数
     + 如果当前对象this < obj → return 负整数
     + else return 0；
   + 对于自定义类来说，如果需要排序，我们可以让自定义类实现Comparable接口，重写compareTo()  

2. **定制排序：Comparator -- java.util.Comparator**

   > 当元素的类型没有实现java.lang.Comparable接口且不方便修改代码
   >
   > 或想根据不同情况customize

   1. 重写compare(Object o1, Object o2)
      + o1 > o2 + / - / 0 同上

   + 可以用Comparator来控制某些数据结构(有序set / 有序映射)的顺序
   + Arrays.sort(are, new Comparator(){ --- })



3. **Comparable vs. Comparator**

+ Comparable接口的方式一旦一定，保证Comparable接口实现类的对象在任何位置都可以比较大小，而Comparator接口属于临时比较





#### 枚举类

> 类的对象只有有限个，确定的
>
> 当需要定义一组常量时，强烈建议使用枚举类

**实现：enum / 自定义枚举类**

+ 如果枚举只有一个对象，则可以作为一种单例模式的实现方式

属性：

+ 枚举类对象的属性不应允许被改动



**自定义：**

```java
enum Season{
    // 1. 创建当前枚举类的对象，多个对象之间用逗号隔开，末尾以；结束
    SPRING("Spring", "first season"),
    SUMMER("Summer", "second season"),
    AUTUMN("Autumn", "third season"),
    WINTER("Winter", "fourth season");

    private final String seasonName;
    private final String seasonDesc;

    private Season(String seasonName, String seasonDesc) {
        this.seasonName = seasonName;
        this.seasonDesc = seasonDesc;
    }
}
```

**主要方法**

1. toString(): 返回当前枚举类对象常量的名称

2. valueOf(String str)：可以把一个字符串转为对应的枚举类对象

   + 要求字符串必须是枚举类对象

3. values()：返回枚举类型的对象数组

   

##### 使用enum关键字定义的枚举类实现接口的情况

1. 实现接口，在enum类中实现抽象方法
2. 让枚举类的对象分别实现接口中的抽象方法

---

#### 注解 (Annotation)

> 代码里的特殊标记，可以在编译，类加载，运行时被读取，并执行相应的处理。

+ 可用于修饰：包、类、构造器、方法、成员变量、参数、局部变量的声明

+ **常见示例：JDK内置的三个基本注解**
  + ```@Override``` ：限定重写父类方法，该注解只能用于方法

  + ```@Deprecated``` ：过时

  + ```@SuppressWarnings``` ：抑制编译器警告

  + ```java
    @SuppressWarnings({"unused","rawtypes"})
    int num = 10;
    ```

+ **实现了替代配置文件的功能**

##### 自定义注解

+ 注解声明为：@interface
+ 内部定义成员，通常使用value表示
+ 可以指定成员的默认值，使用default定义
+ 如果自定义注解没有成员，表明是一个标识

+ 重点：
  + 自定义注解必须配上注解的信息处理流程(使用反射)才有意义





##### JDK四种元注解

> **用于修饰其他Annotation定义**

+ **Retention**
  + 只能用于修饰一个Annotation定义，**用于指定该Annotation的生命周期**
  + **RetentionPolicy**
    + **SOURCE：** be discarded by the compiler
    + **CLASS：** default: recorded in the class file by the compiler but need not be retained by the VM at run time.
    + **RUNTIME：** recorded in the class file by the compiler and retained by the VM at the run time.
+ **Target**: 用于指定被修饰的Annotation能用于修饰哪些程序元素
+ **Documented**: 表示所修饰的注解在被javadoc解析时，保留下来。
+ **Inherited**： 被它修饰的Annotation具有**继承性**



+ 通过反射来获取注解信息



**Java8新特性：可重复注解 @Repeatable**

 						  **类型注解** Type_parameter

```class Season< @MyAnnotation T>```





























