### Java - String 	2019.8.17

---

#### 1. Java中String的了解 
https://www.jianshu.com/p/2f209af80f84 

了解JVM之后再回来看看



#### 2. String，Stringbuffer，Stringbuilder三者的区别 
http://blog.csdn.net/kingzone_2008/article/details/9220691 



**2.1 String** 

```java
/** The value is used for character storage. */
private final char value[];
 
/** The offset is the first index of the storage that is used. */
private final int offset;
 
/** The count is the number of characters in the String. */
private final int count;
```

+ 由此可见，用于存放字符的数组被声明为final的
  + **因此只能赋值一次，不可再更改**

**2.2 StringBuffer**

**StringBuffer就是为了解决大量拼接字符串时产生很多中间对象问题而提供的一个类**

只是StringBuffer 中的方法大都采用了 **<font color=red>synchronized</font>** 关键字进行修饰，因此是线程安全的，而 StringBuilder 没有这个修饰，可以被认为是线程不安全的



**2.3 StringBuilder**

它和StringBuffer本质上没什么区别，就是去掉了保证线程安全的那部分，减少了开销。



**Differences:**

+ String 类型和StringBuffer的主要性能区别

  + String是不可变的对象, 因此在每次对String 类型进行改变的时候，都会生成一个新的 String 对象，然后将指针指向新的 String 对象，所以经常改变内容的字符串最好不要用 String ，因为每次生成对象都会对系统性能产生影响，特别当内存中无引用对象多了以后， JVM 的 GC 就会开始工作，性能就会降低。

+ ```java
  String s1 = “This is only a” + “ simple” + “ test”;
  StringBuffer Sb = new StringBuilder(“This is only a”).append(“ simple”).append(“ test”);
  ```

  + 上面这种情况其实是编译器把：

    + String 对象的字符串拼接其实是被 Java Compiler 编译成了 StringBuffer 对象的拼接，所以这些时候 String 对象的速度并不会比 StringBuffer 对象慢。

  + **如果拼接的字符串来自另外的String对象的话，Java Compiler就不会自动转换了**

    ```java
    String s2 = “This is only a”;
    String s3 = “ simple”;
    String s4 = “ test”;
    String s1 = s2 + s3 + s4;
    ```

    

+ （1）基本原则：如果要操作少量的数据，用String ；单线程操作大量数据，用StringBuilder ；多线程操作大量数据，用StringBuffer。

  （2）不要使用String类的"+"来进行频繁的拼接，因为那样的性能极差的，应该使用StringBuffer或StringBuilder类，这在Java的优化上是一条比较重要的原则。

  

#### 3. String为什么要设计成不可变的？ 
https://www.jianshu.com/p/16480390a847 
https://www.jianshu.com/p/48b011688edc 



String is an immutable class:

**Case 1:** 

```java
public static void main(String[] args) {
    String s = "ABCDEF";
    System.out.println("s = " + s); // s = ABCDEF
    
    s = "123456";
    System.out.println("s = " + s);	// s = 123456
}
```

+ s ，仅仅是一个String对象的引用，并不是对象本身。对象在内存中是一块内存区，而s只是一个引用，它指向了一个具体的对象。

+ 在创建String对象的时候，s指向的是内存中的"ABDCEF",当执行语句`s = "123456"`后，其实又创建了一个**新的对象**"123456",而s重新指向了这个新的对象，同**时原来的"ABCDEF"并没有发生改变，仍保存在内存中。**

  

**Case 2:**

```java
public static void main(String[] args) {
    String s = "ABCDEF";
    System.out.println("s = " + s);	// s = ABCDEF
    
    s.replace("A", "a");
    System.out.println("s = " + s);	// s = ABCDEF
}
```

+ s还是指向第一个创建的对象，虽然aBCDEF已经创建了。

**Case 3:**

```java
public static void main(String[] args) {
    String s = "ABCDEF";
    System.out.println("s = " + s); // s = "ABCDEF"
    
    s = s.replace("A", "a");
    System.out.println("s = " + s); // s = "aBCDEF"
}
```

+ s指向了第二个创建的对象

#### 不可变对象的好处

1. 不可变对象更容易构造，测试与使用；
2. 真正不可变对象都是线程安全的；
3. 不可变对象的使用没有副作用（没有保护性拷贝）；
4. 对象变化的问题得到了避免；
5. 不可变对象的失败都是原子性的；
6. 不可变对象更容易缓存，且可以避免null引用；
7. 不可变对象可以避免时间上的耦合；

#### Note: 并非真的不可变，比如用反射 reactField

+ <font color=orange>**这里不懂，回来再看**</font>



#### 4. string 转换成 integer的方式及原理 
http://blog.csdn.net/sinat_20259781/article/details/52024763 

**1- Interger.toString():**

```java
  public static String toString(int i) {
  	if (i == Integer.MIN_VALUE)
    	return "-2147483648";
    int size = (i < 0) ? stringSize(-i) + 1 : stringSize(i);
    char[] buf = new char[size];
    getChars(i, size, buf);
    return new String(buf, true);
  }
```



**2- Integer.parseInt()：**

+ 正常判断null，进制范围，length
+ 判断第一个字符是否是符号位
+ 循环遍历 =》 十进制
+ 判断是否为负值，返回结果



### 说说 Final

```Final``` 修饰符 用于控制 成员，方法，或者一个类时候可以被重写或者继承等

+ 如果一个类被声明为final，则该类无法派生出新的子类，不能作为父类被继承
+ 如果 var / function <- final , 可以保持他们在使用中不被改变

两个地方可以进行初始化：

+ 一是在定义处，

  + ```java
    final String str= "final";
    ```

+ 二是在构造函数中,

  + ```java
    public class ex {
        final String str;
    
        ex(String str) {
            this.str = str;
        }
    ```

+ **要么在定义处直接给其赋值，要么在构造函数中给值，并且在以后的引用中，只能读取，不可修改。被声明为final的方法也同样只能使用，不能重写。**

