## C++ primer plus

### 第九章 内存模型和名称空间

#### 9.1 单独编译

header file: including struct declaration and prototype of these functions

source file: contains codes that related to struct functions

⚠️ 请不要将函数定义或变量声明放到头文件中。

**头文件中常包含的内容：**

- 函数原型
- ‘#define’ 或 const 定义的符号常量
- 结构、类、模版声明

+ 内联函数 （inline -- 编译时替换内容）



⚠️ #include 后面 “xxx.h” vs. <xxx.h> 

- ““: 编译器将首先查找**当前的工作目录或源代码目录**
- <>: c++编译器将在**存储标准头文件的主机系统**的文件系统中查找



**头文件管理**

- 在同一个文件中只能将同一个头文件包含一次。

- ```c++
  #ifndef COORDIN_H_			// if not defined
  #define COORDIN_H_
  	// contents
  #endif
  ```

- 如果头文件没有被读取过，即没有被定义过，则去查看中间的内容，否则忽略。



#### 9.2 存储持续性、作用域和链接性

> c++11 使用了4种不同的方案来存储数据，区别在于数据保留在内存中的时间。

| **自动存储持续性** | 在函数定义中声明的变量。生命周期在函数/代码块内       |
| ------------------ | ----------------------------------------------------- |
| **静态存储持续性** | **static / 函数定义外的变量**                         |
| **线程存储持续性** |                                                       |
| **动态存储持续性** | **用new分配的内存将一直存在，直到使用delete将其释放** |

**9.2.1 作用域和链接**

- **作用域(scope)**：名称在文件的多大范围内可见。

  - 全局
  - 局部
  - c++函数的作用域可以是整个类/名称空间，但不能是局部的

- **链接性(linkage)**：名称如何在不同单元间共享。

  - 外部：文件间共享

  - 内部：一个文件中的函数共享

  - 自动变量没有链接性，因此无法共享

    

![Screen Shot 2020-08-11 at 5.11.05 pm](/Users/Ethan/Desktop/Fannibals.github.io/C++/pic/Screen Shot 2020-08-11 at 5.11.05 pm.png)



1. **静态持续性、外部链接性**

- 作用域解析运算符

  ```c++
  cout << "But global warming = " << ::warming;
  ```

  - 这里的warming将使用全局版本

- **<font color=red>单定义规则：变量只能有一次定义</font>**



⚠️：```const char * const months[3]``` 看一下其中**const的作用**

**<font color=purple>以下三种表达方式有何不同呢？</font>**

```c++
const char * const m1[3] = {"Jan","Feb","Mar"};  // 1
const char * m2[3] = {"Jan","Feb","Mar"};  // 2
char * const m3[3] = {"Jan","Feb","Mar"};  // 3
```

2 & 3 是 1的拆解，我们理解了2和3，也就会理解1了。

**<font color=green>以*为分界，从右向左解读：</font>**

- 2) : m2 is an array includes pointers to const chars
- 3) : m3 is an array includes const pointers to chars.

由此可见，m2 中的指针是可变的，而其指向的char是不可变的。

相反，m3中的指针是不可变的，但是其指向的char是可变的。

下面我们来验证一下：

```c++
  const char * m2[3] = {
  "Jan", "Feb", "Mar"
  };

  char temp[4] = "def";
  m2[0] = temp;   // temp指向字符d
  cout << m2[0] << endl;  // Output：def
```

```c++
  char * const m3[3] = {
  "Jan", "Feb", temp
  };
  // m3[0] =  temp;  // invalid
  temp[0] = 'T';
  cout << m3[2] << endl;	// Output: Tef
```



2. **静态持续性、无链接性**

> 将static限定符用于在代码块中定义的变量
>
> 即使在代码块不处于活动状态时仍然存在



**P315: static.cpp**

```c++
...
int main()
{
    using namespace std; char input[ArSize]; char next;
    cin.get(input,ArSize);
    while(cin)			// == while(!cin.fail())
    {
        cin.get(next);
        while(next != '\n') {			// 解决多余的字符
            cin.get(next);
        }
        strcount(input);
				...
    }
		...
}

void strcount(const char * str)
{		...
    static int total = 0;
    int count = 0;
		...
    while(*str++){		// 这里这么做ok是因为字符串最后有'\0'这个结束符号
        count++;
    }
		...
}

```



**C/C++中 0， ‘0’， “0”， “/0”和NULL的区别**

- 0：数值常量

- '0'：字符常量， 在内存中的值是0x30.

- "0"：字符串常量， 实际存储的时候是一个0x30, 一个\0。

- "\0"：字符串结束符， 

- NULL：空指针， 在C++中值为0，在C中定义为(void *)0. 



**说明符 & 限定符**

- 存储说明符（storage class specifier）
  - auto(not in C++11 )
  - register
  - static
  - extern
  - thread_local (new in C++11)
  - mutable

1. cv-限定符

- const : 内存被初始化后，程序便不能再对它进行修改

- volatile：即使程序代码没有对内存单元进行修改，其值也可能发生变化

  - volatile 关键字是一种类型修饰符，用它声明的类型变量表示可以被某些编译器未知的因素更改，比如：**操作系统、硬件或者其它线程等**。遇到这个关键字声明的变量，编译器对访问该变量的代码就不再进行优化，从而可以提供对特殊地址的稳定访问。声明时语法：**int volatile vInt;** **当要求使用 volatile 声明的变量的值的时候，<font color=red>系统总是重新从它所在的内存读取数据，即使它前面的指令刚刚从该处读取过数据。而且读取的数据立刻被保存。</font>**

  - ```c++
    volatile int i=10;
    int a = i;
    ...
    // 其他代码，并未明确告诉编译器，对 i 进行过操作
    int b = i;
    ```

2. mutable

   > 即使结构/类变量为const，其某个成员也可以被修改

   ```c++
   struct data
   {
       char name[30];
       mutable int accesses;
   };
   
   int main()
   {
       const data veep = {"Clayton",0};
       strcpy(veep.name, "Jonny"); // not allowed
       veep.accesses++;            // allowed
       return 0;
   }
   ```

3. const

   - const用于修饰全局变量时，链接性为内部，相当于加了static

   - 应用之一就是在头部文件中，**防止破坏单定义规则**



**函数和链接性：**

- all functions automatically have static storage duration.
- 默认链接性为外部（可共享），可以加static来设置为内部

- The one definition rule extends to non-inline functions,



**定位new运算符**

- 可以制定要使用的位置, need to **include new header file** 

- 原本的：p1 = new chaff;
- placement new: p2 = new (buffer1) chaff;

⚠️：delete关键字只能用于这样的指针：指向常规new运算符分配的**堆内存**。

​		因此如果delete 一个静态内存，则会引发运行时错误



#### 9.3 名称空间

> 名称可以是变量、函数、结构、枚举、类以及类和结构的成员。当随着项目的增大，名称相互冲突的可能性也将增加，因此名称空间被创造出来以便更好地控制名称的作用域。

需要注意的两个词：

- **declarative region**
- **potential scope**



命名空间可以是全局的，也可以位于另一个名称空间中，但不能位于代码块中。

**可以用作用域解析运算符<font color=red>::</font>显式的限定：**

```
namespace1::hello =12.34;
namespace2::fetch();
```

但是我们不希望每次都限定，因此c++提供了using声明 & using编译指令.

```c++
using Jill::fetch;  // a using declaration, only fetch

using namespace Jack; // make all the names in Jack aval
```

