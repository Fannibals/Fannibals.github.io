### Java IO Stream

- File
- Principle and classification
- file stream
- cache stream
- Transfer stream
- standard io
- Print, data, obj, random .path ...



#### File Class

> **java.io.File** : **文件和文件目录**路径的抽象表示形式，与平台无关
>
> File 可以对文件和目录进行创建、删除等操作，但是无法访问内容，需要用到io流

1. **常用构造器**
   + **public File(String pathname);**	// 路径可以是绝对/相对
   + **public File(String parent, String child);**
   + **public File(File parent, String child);**

2. **windows & unix has different path separator**

   + Windows: \\\
   + Unix: /

   + File.separator

3. 常用方法

   + ![Screen Shot 2020-02-21 at 2.47.44 pm](/Users/Ethan/Desktop/Fannibals.github.io/JavaSMY/Screen Shot 2020-02-21 at 2.47.44 pm.png)
+ ![Screen Shot 2020-02-21 at 2.48.18 pm](/Users/Ethan/Desktop/Fannibals.github.io/JavaSMY/Screen Shot 2020-02-21 at 2.48.18 pm.png)
   + ![Screen Shot 2020-02-21 at 2.48.35 pm](/Users/Ethan/Desktop/Fannibals.github.io/JavaSMY/Screen Shot 2020-02-21 at 2.48.35 pm.png)



#### 一、流的分类

+ 按照**数据单位**：**字节流(8 bit)、字符流(16 bit)**

+ 按照**流向**：**输入流、输出流**

+ 角色：**节点流、处理流**

| 抽象基类   | 字节流           | 字符流     |
| ---------- | ---------------- | ---------- |
| **输入流** | **InputStream**  | **Reader** |
| **输出流** | **OutputStream** | **Writer** |



#### 二、流的体系结构

| 抽象基类     | 节点流(文件流)   | 缓冲流(处理流的一种) |
| ------------ | ---------------- | -------------------- |
| InputStream  | FileInputStream  | BufferedInputStream  |
| OutputStream | FileOutputStream | BufferedOutputStream |
| Reader       | FileReader       | BufferedReader       |
| Writer       | FileWriter       | BufferedWriter       |



#### 具体用法

##### FileReader

+ **readchar cbuf**

  ```java
  File file = new File("hello.txt");
  FileReader fr = new FileReader(file);
  
  // read(char[] cbuf): 返回每次读入cbuf数组中的字符的个数，末尾-1
  char[] cbuf = new char[5];
  int len;
  
  while((len = fr.read(cbuf)) != -1){
  // 方式一：
  // 错误的写法
  for (int i = 0; i < cbuf.length; i++) {
    System.out.print(cbuf[i]);
  }
  // 正确的写法
  for (int i = 0; i < len; i++) {
    System.out.print(cbuf[i]);
  }
  
  // 方式二：
  // 错误的写法
  String str = new String(cbuf);
  // 正确的写法
  String str1 = new String(cbuf,0,len);
  }
  
  ```

  

##### FileWriter

> 从内存中写出数据到硬盘的文件里

说明：

1. 输出操作，对应的File可以不存在的。并不会报异常

2. File对应的硬盘中的文件如果**不存在**，在输出的过程中，会自动创建此文件

   File对应的硬盘中的文件如果**存在**，

   - 如果流使用的构造器是：FileWriter(file,false) / FileWriter(file): 会覆盖原文件。如果用的是 FileWriter(file,true)，则不会覆盖原文件。

注意：

+ 不能使用字符流来处理图片等字节数据，反之亦然

---

### 处理流之一： 缓冲流

+ 作用：提供流的读取、写入的速度

+ 提升速度的原因：内部提供了缓冲区





### 处理流之二：转换流

+ **InputStreamReader：将一个字节的输入流转换为字符的输入流**
+ **OutputStreamWriter：将一个字符的输出流转换为字节的输出流**

+ 作用：提供字节流和字符流之间的转换



**字节、字节数组 ---> 字符数据、字符串 ： 解码（看不懂 --> 看得懂）**





#### 字符集

+ 常见的编码表
+ ASCII：美国标准信息交换码
+ UTF-8: 



标准流、数据流

### 数据流

+ 为了方便地操作Java语言的基本数据类型和String的数据，可以使用数据流



