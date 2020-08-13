### Java Collection SYM 

#### 集合框架

1. 集合，数组都是对多个数据进行存储操作的结构，简称Java容器
   + 这里的存储指的是内存层面的存储
2. 数组在存储多个数据方面的特点 / 缺点
   + 一旦初始化：长度就确定，元素类型也就确定了，可用方法少

**分类：Collection & Map**

---

### Collection接口：单列数据

+ **List：元素有序、可重复的集合**
+ **Set：元素无序、不可重复的集合**

![Screen Shot 2020-02-20 at 4.21.25 pm](/Users/Ethan/Desktop/Fannibals.github.io/JavaSMY/Screen Shot 2020-02-20 at 4.21.25 pm.png)



+ **Collection 接口的方法**

  + **Iterator 迭代器接口**

    > Iterator对象成为迭代器，主要用于遍历Collection集合中的元素
    >
    > 就是为了给容器遍历的

    + Collection interface extends **java.lang.Iterable** which has a method called iterator();

  + ```java
    Iterator iterator = coll.iterator();
    while (iterator.hasNext()){
    	System.out.println(iterator.next());
    }
    ```

  + 迭代器内部定义了remove()方法，可以在遍历的时候删除集合中的元素。此方法不同于集合直接调用remove().

  **注意点：**

  + 常见错误

    ```java
    while(coll.iterator().hasNext()){
    	System.out.println(coll.iterator().next());
    }
    ```

    + 集合对象每次调用iterator()方法都会得到一个全新的迭代器对象，默认cursor在集合的第一位
    + 指针最开始在最上边，所以如果直接调用remove（没有先next）会报错
      + <img src="/Users/Ethan/Desktop/Fannibals.github.io/JavaSMY/Screen Shot 2020-02-20 at 5.14.49 pm.png" alt="Screen Shot 2020-02-20 at 5.14.49 pm" style="zoom:83%;" />

#### Collection子接口之一：List接口

|                | 线程，效率                           | 底层存储             |
| -------------- | ------------------------------------ | -------------------- |
| **ArrayList**  | 线程不安全，效率高                   | Object[] elementData |
| **LinkedList** | 对于频繁插/删操作，效率高于ArrayList | 双向链表存储         |
| **Vector**     | 线程安全，效率低                     | Object[] elementData |



**面试题：ArrayList、LinkedList、Vector三者异同**

+ 相同：都是实现了List接口， 存储数据的特点相同：存储有序的、可重复的数据



#### ArrayList SourceCode Analyse

**jdk 7:**

```java
ArrayList list = new ArrayList(); 
// 底层创建了长度是10的Object[] elementData
list.add(123); // element[0] = new Integer(123);
list.add(11); // 如果此次添加导致底层elementData容量不够，则扩容（1.5）
// 结论： 建议开发时使用带参的构造器
```

**jdk 8:**

```java
ArrayList list = new ArrayList(); 
// 底层Object[] elementData 初始化为{}，
list.add(123); // 第一次调用add()的时候，底层才创建了长度为10的数组，并将数据123添加到elementData中
```

+ 懒加载，延迟了数组的创建，节省了内存

---

#### LinkedList 源码分析

```java
LinkedList list = new LinkedList();
// 内部声明了Node类型的first & last属性，默认值为null
list.add(123); // 将123封装到Node中，创建了Node对象

Node 定义为：
      private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }
```

#### **Vector**

jdk7 & 8 中通过Vector()构造器创建对象时，底层都创建了长度为10的数组。

在扩容方面，默认原来2倍；**<< 1**

List接口方法

+ **void add (int index, Object ele);** 
+ **Object remove (int index);**
+ **Object get (int index);**
+ **Object set (int index, Object ele);**

------

#### Collection子接口之二：Set接口

|                   | **线程，效率**                       | **存储** / 作用                      |
| ----------------- | ------------------------------------ | ------------------------------------ |
| **HashSet**       | 线程不安全                           | 可以存null值                         |
| **LinkedHashSet** | 作为HashSet的子类；                  | 遍历其内部时，可以按照添加的顺序遍历 |
| **TreeSet**       | 可以按照添加对象的指定属性，进行排序 |                                      |

##### Set ：存储无序的、不可重复的数据

1. **无序性：**不等于随机性(稳定)。
   + 存储的数据在底层数据中不按照index顺序add，depends on its hashcode.
2. **不可重复性**
   + 保证添加相同元素按照equals方法判断时，不能返回True，相同的元素只能添加一个

##### 添加元素的过程 以HashSet为例

> 我们向HashSet中添加元素a，首先调用元素a所在类的hashCode()方法，计算元素a的hash值，此hash值即为HashSet底层数组中的存放位置（index）
>
> 如果数组中该位置有其他元素 -->  比较hash值 --> 如果还是相同，看equals()
>
> 如果全是true，则不添加，否则就直接添加

1. Set接口中没有额外定义的新方法，-- Collection
2. 要求：**向Set中添加的数据，其所在的类一定要重写hashcode()和equals()**
   + **<font color=red>相等的对象一定要有相等的hash值</font>**



#### **LinkedHashSet**

+ 是Hashset的子类，在添加数据的同时，每个数据还维护了两个引用，记录此数据前一个和后一个的数据。
+ 目的：对于**频繁的遍历操作**，效率优于HashSet



#### TreeSet

+ 向TreeSet中添加的数据，要求是**相同的对象**。

+ 有两种排序方式：自然排序 & 定制排序
+ **自然排序：**
  + **TreeSet按照compareTo() 排序 --- Comparable**
  + **TreeSet和TreeMap采用<font color=red>红黑树</font>的存储结构**
    + **特点：有序，查询速度比List快**



+ **定制排序：**
  + **TreeSet按照compare() 排序 --- Comparator**



+ **TreeSet的排序不再是按照equals，而是通过比较器；**
+ **必须要实现两者之一，否则无法编译**
+ java.lang.ClassCastException: CollectionTest.IteratorTest$User cannot be cast to java.lang.Comparable



---

### Map接口：双列数据 (key-value)  函数

![Screen Shot 2020-02-20 at 4.22.12 pm](/Users/Ethan/Desktop/Fannibals.github.io/JavaSMY/Screen Shot 2020-02-20 at 4.22.12 pm.png)



|                   | **线程，效率**                          | **存储** / 作用                                            |
| ----------------- | --------------------------------------- | ---------------------------------------------------------- |
| **HashMap**       | 线程不安全,效率高                       | 可以存null的key & value                                    |
| **LinkedHashMap** | 对于**频繁的遍历操作**，效率优于HashMap | 保证在遍历map元素时，可以按照添加的顺序实现遍历 (一对指针) |
| **TreeMap**       | 自然/定制排序            底层使用红黑树 | 保证按照添加的K-V对进行排序，实现排序遍历(按照key来排)     |
| **HashTable**     | 线程安全,效率低                         | 不能存null的k，v                                           |
| **Properties**    | 常用来处理配置文件                      | K，V都是String类型                                         |

**HashMap的底层**

+ 数组 + 链表 + **红黑树（jdk8以后）**

#### 面试题

1. **HashMap的底层实现原理？**

2. HashMap vs. HashTable?
3. CurrentHashMap vs. HashTable?



**1. Map结构的理解**

+ Key：无序的、不可重复的，使用**Set**存储所有key
  + Key所在的类需要重写equals() & hashCode();
+ Value: 无序的、可重复的，使用**Collection**存储所有value
+ 一个键值对：**key-value consitutes an Entry Object**

+ Entry: 无序的、不可重复的，使用**Set**存储所有entry



#### **<font color=red>HashMap的底层实现原理 ?</font>** 

**<font color=red>Jdk7的情况</font>**

+ ```HashMap map = new HashMap();```

  + 实例化之后，底层创建了长度是16的一维数组 **Entry[] table**

+ ```map.put(key1,value1);```

  + 首先，调用key1所在类的hashCode()计算key1的hash值，再经过某种算法计算后，得到在Entry数组中的存放位置(index)。
  + 如果该index上数据**为空**，此时的key1-value1添加成功 ---- **Condition1**

  + 如果该index上数据为**不为空**，比较key1和改位置的数据(们)的hash值

    + 如果key1与其他数据的hash值都不相同，k1-v1添加成功  --- **C2**
    + 如果key1的hash值与某一个key的hash值相同时，继续比较：
      + 调用key1所在类的equals(key2)
        + False: k1-v1添加成功  --- **C3**
        + True: **使用value1 替换 value2**

  + **补充：**C2 & C3：此时K1-V1和原来的数据以链表的方式存储

  + **扩容：**默认的扩容方式：扩容为原来容量的2倍，并复制原数组

    

**<font color=red>Jdk8的情况：不同点</font>**

1. new HashMap()：底层没有创建一个长度为16的数组

2. **Entry[]  -->  Node[]** 底层数组为node

   + ```java
     transient Node<K,V>[] table;
     ```

3. 首次调用put()方法时，底层创建长度为16的数组

4. 底层结构：数组 + 链表 + **红黑树**

   + 当数组的某一个索引位置上的元素以链表形式存在的**数据个数 > 8** 

     **而且当前数组的长度 > 64时**，此时此索引位置上的所有数据改为使用**红黑树**存储



#### 面试题：

> 谈谈你对HashMap中put/get方法的认识？如果了解再谈谈HashMap的扩容机制？默认大小时多少？什么是负载因子？什么是阈值(threshold)？



**<font color=red>HashMap源码中的重要常量</font>**

| Var Name                     | Meaning                                                      |
| ---------------------------- | ------------------------------------------------------------ |
| **DEFAULT_INITIAL_CAPACITY** | **1 << 4 (2^4 = 16)**                                        |
| **MAXIMUM_CAPACITY**         | **1 << 30 (2^30)**                                           |
| **DEFAULT_LOAD_FACTOR**      | 0.75f                                                        |
| **TREEIFY_THRESHOLD**        | **Bucket中链表长度大于该默认值，转化为红黑树**               |
| **UNTREEIFY_THRESHOLD**      | **Bucket中Node小于该值，转为链表**                           |
| **MIN_TREEIFY_CAPACITY**     |                                                              |
| **Table**                    | 存储元素的数组，总是2的n次幂                                 |
| **entrySet**                 | 存储具体元素的集                                             |
| **size**                     | HashMap中存储的K-V对数量                                     |
| **modCount**                 | HashMap扩容和结构改变的次数                                  |
| **Threshold**                | The next size value at which to resize **(capacity * load factor).** 影响扩容 |
| **loadFactor**               |                                                              |



**HashMap JDK8 源码**

1. ```java
   Map<String,String> map = new HashMap<String,String>();
   ```

   + 开始new的时候**并没有创建数组**，仅仅是赋值了loadFactor

     ```java
     public HashMap() {
       this.loadFactor = DEFAULT_LOAD_FACTOR;}
     ```

2. **put method**

   + **当第一次put时，走put方法，put方法调用putVal方法**

   + ```java
     public V put(K key, V value) {
         return putVal(hash(key), key, value, false, true);
     }
     ```

#### M0: putVal（）

![Screen Shot 2020-02-21 at 8.57.17 pm](/Users/Ethan/Desktop/Fannibals.github.io/JavaSMY/Screen Shot 2020-02-21 at 8.57.17 pm.png)

#### M1: resize（）

<img src="/Users/Ethan/Desktop/Fannibals.github.io/JavaSMY/Screen Shot 2020-02-21 at 5.17.48 pm.png" alt="Screen Shot 2020-02-21 at 5.17.48 pm" style="zoom:60%;" />



#### M2: treeifyBin

![Screen Shot 2020-02-21 at 9.16.38 pm](/Users/Ethan/Desktop/Fannibals.github.io/JavaSMY/Screen Shot 2020-02-21 at 9.16.38 pm.png)





#### **loadFactor **-- 0.75f

+ 作用：希望数组中链表结构尽量少/短
  + HashMap扩容不像数组扩容，不一定会**“满”**，因此需要提前扩容



#### LinkedHashMap

+ 重写了newNode();

+ <img src="/Users/Ethan/Desktop/Fannibals.github.io/JavaSMY/Screen Shot 2020-02-21 at 9.27.41 pm.png" alt="Screen Shot 2020-02-21 at 9.27.41 pm" style="zoom:50%;" />



#### Map接口常用方法

![Screen Shot 2020-02-21 at 9.30.41 pm](/Users/Ethan/Desktop/Fannibals.github.io/JavaSMY/Screen Shot 2020-02-21 at 9.30.41 pm.png)



#### Map遍历

<img src="/Users/Ethan/Desktop/Fannibals.github.io/JavaSMY/Screen Shot 2020-02-21 at 9.42.37 pm.png" alt="Screen Shot 2020-02-21 at 9.42.37 pm" style="zoom:90%;" />



#### TreeMap 

+ 向TreeMap中添加K-V，要求key必须是由同一个类创建的对象
+ 自然排序、定制排序

---

### 泛型

**设计背景：**

+ 集合容器在设计/声明阶段不能确定到底存的什么类型的对象
  把元素的类型设计成一个参数，这个类型参数叫做泛型

**如果不用可能出现的问题**

+ **类型不安全**
+ **强转时**，可能出现ClassCastException

**泛型的使用：**

1. **在集合中使用泛型：**
   + 集合接口或集合类在jdk5.0时都修改为带泛型的结构
   + 在实例化集合类时，可以指明泛型类型

**泛型嵌套:**

+ ```Set<Map.Entry<String,Interger>> entry = map.entrySet();```
+ ```Iterator<Map.Entry<String,Interger>> iterator = entry.iterator();```

**自定义泛型结构：泛型类、泛型接口；泛型方法**

**自定义泛型类, 结构：**

1. 如果定义了泛型类，实例化没有指明类的泛型，则认为次泛型类型为Object类型
2. **继承了泛型类的时候：**
   + 如果指明了泛型类，则实例化子类对象时，不再需要指明泛型。
   + 如果未指明泛型类，则SubClass<T> 将仍然是泛型类

3. 如果有多个参数 --> <K,V>

4. 泛型类的构造器：

   ```java
   public GenericClass(){}; // True
   public GenericClass<E>(){}; //False
   ```

5.  不同的引用不能相互赋值
6. 泛型如果不指定，则按照Object处理
7. 泛型的指定中不能使用基本数据类型，可以使用包装类替换

8. 静态方法中不能使用类的泛型
  
+ 因为泛型的指定是在造对象的时候，而静态方法早于对象的创建
  
9. 不能使用new E[]

   ```java
   E[] elem = (E[]) new Object[capacity];
   ```

   

**自定义泛型方法**

1. 泛型方法所属的类是不是泛型类都没有关系。

2. 构造形式：

   ```java
   public <E> E[] foo(E[] arr){
   	return arr;
   }
   ```



**泛型在继承中的体现**

1. ```java
     Object obj = null;
     String str = null;
     obj = str; // 👌
      
     Object[] arr1 = null;
     String[] arr2 = null;
     arr1 = arr2; // 👌
      
     // list1 & 2的类型不具有子父类关系
     List<Object> list1 = null;
     List<String> list2 = null;
     list1 = list2; // 报错！❌
      	编译不通过
   ```

2. 类A是类B的父类，A<G> is the superclass of B<G>

   ```java
   List<String> list = new ArrayList<String>();
   ```

   

#### 通配符

> 通配符可用于代替单个或多个字符。通常地，星号“*”匹配0个或以上的字符，问号“?”匹配1个字符。

类A是类B的父类，G<A>和G<B>是没有关系的，两者共同的父类是：G<?>

```java
public class GenericTest {

    @Test
    public void test1(){
        List<Object> list1 = new ArrayList<>();
        list1.add(new Integer(123));
        List<String> list2 = new ArrayList<>();
        list2.add("123");
        List<?> list = null;
        list = list1;
        list = list2;

        print(list1);
        print(list2);
    }

    public void print(List<?> list){
        Iterator<?> iterator = list.iterator();
        while(iterator.hasNext()){
            Object obj = iterator.next();
            System.out.println(obj);
        }
    }
}
```

+ **对于List<?> 就不能向内部添加数据** / 可以加null

**list允许读取数据，读取的数据类型为Object**

```java
Object o = list.get(0);
System.out.println(o);
```

**有限制条件的通配符的使用**

+ **? extends A:** **>=**
  + **G<? extends A>** 可以作为G<A> 和G<B> 的父类，其中B是A的子类
+ **? super A:** **<=**
  + 可以作为G<A> 和G<B> 的父类，其中B是A的父类

