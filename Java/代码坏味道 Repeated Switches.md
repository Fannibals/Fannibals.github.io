### 代码坏味道: Repeated Switches

#### 1. 现象

- 在第一版中，把Switch Statement定义成了代码坏味道
  - 并提到: 一看到switch，救应该考虑以多态替换
- 第二版中进行了修改，改为Repeated Switches



#### 2. 根因

- **重复代码**
  - 相同或相似的switch语句散布在不同的地方，导致修改时如果想添加一个新的case字句，需要找到所有语句并修改
- **忽视了多态的使用** 


#### 3. 解决

##### 3.1 出现重复的switch代码，并且应用于多个场景(类)

1. 先用Extract Method把switch语句提炼出来
2. 用Move Method搬移到需要多态的那个类里

3. 用子类取代类型码
4. 以多态取代条件表达式

https://cloud.tencent.com/developer/article/1637187

##### 3.2 只是在单一函数中做选择

- 如果条件分支并不多并且它们使用不同参数调用相同的函数，多态就没必要了。在这种情况下，你可以运用 以明确函数取代参数(Replace Parameter with Explicit Methods) 。
- 如果你的选择条件之一是 null，可以运用 引入 Null 对象(Introduce Null Object)

