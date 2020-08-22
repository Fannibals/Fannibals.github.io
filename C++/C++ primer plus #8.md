## C++ primer plus #8

### Reference Variables

1. &

- &这个符号的第一个用法就是表示一个变量的地址
- & 还可以被用来声明引用

比如：

```c++
int rats;
int & rodents = rats;	// make rodents an alias for rats
```

```int &``` means reference-to-int.

⚠️：必须在声明引用变量时进行初始化

```c++
int a = 1;
int& b = a;
cout << "a = " << a << " a:ads->" << &a << endl;
cout << "b = " << b << " b:ads->" << &b << endl;
cout << "-------------" << endl;
int c = 2;
b = c;
cout << "a = " << a << " a:ads->" << &a << endl;
cout << "b = " << b << " b:ads->" << &b << endl;
```

**<font color=red>Output:</font>**

```c++
a = 1 a:address->0x7ffee376a748
b = 1 b:address->0x7ffee376a748
-------------
a = 2 a:address->0x7ffee376a748
b = 2 b:address->0x7ffee376a748
```





**Pointer vs. Reference**

- A **pointer** in C++ is a variable that holds the memory address of another variable.
- A **reference** is an alias for an already existing variable. Once a reference is initialized to a variable, it cannot be changed to refer to another variable. Hence, a reference is similar to a **const pointer**.

![Screen Shot 2020-08-15 at 3.07.10 pm](/Users/Ethan/Desktop/Fannibals.github.io/C++/pic/Screen Shot 2020-08-15 at 3.07.10 pm.png)