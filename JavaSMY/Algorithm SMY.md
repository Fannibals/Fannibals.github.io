### Algorithm SMY (剑指Offer)

#### 1. 重建二叉树

>  题目描述：输入某二叉树的**前序遍历 (int[] pre)**和**中序遍历  (int[] in)**的结果，请重建出该**二叉树**。假设输入的前序遍历和中序遍历的结果中都不含重复的数字
>

+ **前置知识**
  + **前序遍历：**根 - 左 - 右
  + **中序遍历：**左 - 根 - 右
  + **后序遍历：**左 - 右 - 根

+ **思路：**

  1. 1) pre的第一个点为root 	 2) in中的root值划分了树的左右部分

  2. **算法使用思想：递归**

     + 终止条件：preStart > preEnd, 即pre数组被划分为空的数组
     + 先创建一个root，赋值为pre的第一个点，然后在in数组中找到该值并进行递归：

     ```java
     for (int i=inStart;i<=inEnd;i++){
       if (root.val == in[i]){
       	root.left = buildTree(pre,preStart+1,preStart+i-inStart,in,inStart,i-1);
       	root.right = buildTree(pre,preStart+i-inStart+1,preEnd,in,i+1,inEnd);
       }
     }
     ```

     + 最终return root；

  3. **注意：**
     
     + i 为root在in数组中的index**，i- inStart** 为左子树的长度





#### 2. 二维数组中的查找

> 在一个**二维数组**中（每个一维数组的长度相同），**每一行**都按照从**左到右递增**的顺序排序，**每一列**都按照从**上到下递增**的顺序排序。请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中**是否含有该整数**。

+ **前置知识：**

  + ```java
    int[][] arr = new int[row][column];
    ```

+ **思路：**

  + 因为每一行从左到右递增，从上到下递增

  + 因此：把target跟每一行的最右边（最大）的值进行对比

  + ```java
            if (array[row][column] == target){
                return true;
            }else if(array[row][column] > target){
                column--;
            }else{
                row++;
            }
    ```

+ **注意：**

  + **终止条件：row >= array.length / column < 0** 

  + ```while(row<array.length && column>=0){xxx}```

    

#### 3. 替换空格

> 请实现一个函数，将一个字符串中的**每个空格**<u>替换</u>成“%20”。例如，当字符串为We Are Happy.则经过替换之后的字符串为We%20Are%20Happy。

+ **思路：**
  
+ 先把str转成char array，然后用新建一个StringBuffer拼接，最后返回String类型
  
+ **注意：**

  + 原本给的str是StringBuffer类型

  + ```java
    char[] cArr = str.toString().toCharArray();
    ```

    

#### 4. 从尾到头打印链表

> 输入一个链表，按链表从尾到头的顺序返回一个ArrayList

```java
Collections.reverse(list);
```



#### 5. 用两个栈实现队列

> 用两个栈来实现一个队列，完成队列的Push和Pop操作。 队列中的元素为int类型

+ 思路：

  + push的时候放进stack1里

  + pop的时候由stack2pop

  + pop之前check一下, 如果有stack2空了则把stack1的东西放进来

    + ```java
          public void check(){
              if (stack2.isEmpty()){
                  while(!stack1.isEmpty()){
                      stack2.push(stack1.pop());
                  }
              }
          }
      ```

    

#### 6. 旋转数组的最小数字

> 把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。
> 输入一个非递减排序的数组的一个旋转，输出旋转数组的最小元素。
> 例如数组{3,4,5,1,2}为{1,2,3,4,5}的一个旋转，该数组的最小值为1。
> NOTE：给出的所有元素都大于0，若数组大小为0，请返回0。

+ **思路：**
  
  + 思路一：for循环找第一个左边大于右边的点
+ 思路二：二分法
  
+ **注意事项：**

  + 使用二分法解题时，需要考虑到

    ```java
    if (array[mid] < array[max] ) {
    		max = mid;
    }
    ```

  + 而不是max = mid-1；举例子的话可思考只有两个元素的情况



#### 7. 斐波那契数列 ，8.跳台阶

> 输入一个整数n，输出f(n),  n<=39

+ 思路：
  + 一：f(n) = f(n-1) + f(n-2);
  + 二：创建int数组，循环赋值

+ ```java
  int[] res = new int[n+1]; 
  res[0] = 1; res[1] = 1;
  for (int i=2; i<=n;i++){
    res[i] = res[i-1] + res[i-2];
  }
  return res[n];
  ```

+ 注意：

  + ```int[] res = new int[n+1]; ``` 0开始，所以n+1个



#### 9. 变态跳台阶

>  一个台阶总共有n级，如果一次可以跳1级，也可以跳2级……也可以跳n级。求总共有多少总跳法，并分析算法的时间复杂度。

+ 思路：
  + f(1) = 1
  + f(2) = f(2-1) + f(2-2)
  + ...
  + f(n-1) = f(0) + f(1) + ... + f(n-2)
  + f(n) = f(0) + f(1) + ... + f(n-2) + f(n-1)
    + **f(n) = 2*f(n-1)** when n>=2

 

#### 10. 矩形覆盖

> 我们可以用2\*1的小矩形横着或者竖着去覆盖更大的矩形。
> 请问用n个2\*1的小矩形无重叠地覆盖一个2*n的大矩形，
> 总共有多少种方法？

+ 思路：斐波那契数列变种

  + f(1) = 1; f(2) = 2

  + f(3) = f(1) + f(2);

  + f(n) = f(n-1) + f(n-2)

    

#### 11. 二进制中1的个数

> 输入一个整数，输出该数二进制表示中1的个数，负数用补码表示

+ 思路：
  + 1的二进制为 最后一位为1，前面全是0，每次左移一位
  + 使得flag的二进制表示中始终只有一个位为1，每次与n进行 & 
  + 相当于逐一对每一位进行1的检测
+ 注意：
  + ```>>>``` ```<<  >>```



#### 12. 数值的整数次方

> 给定一个double类型的浮点数base和int类型的整数exponent。求base的exponent次方。   保证base和exponent不同时为0

+ 思路：

  + 把exponent 转换成二进制表示

  + 比如 5 --> 101  ----- 2^5 = 2^4 * 2^1;



#### 13. 调整数组顺序使奇数位于偶数前面

> 输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有的奇数位于数组的前半部分，所有的偶数位于数组的后半部分，并保证奇数和奇数，偶数和偶数之间的相对位置不变。

+ 思路：

  + ```java
    public void reOrderArray(int[] array){
    	int m = array.length;
    	int k = 0;	// 记录已经摆好位置的奇数的个数
    	for(int i = 0; i < m; i++){
    		if(array[i] % 2 == 1){
          int j = i;	// 每次找到的奇数的index
         	
          while(j<k){	
            int tmp = array[i];
            array[j] = array[j-1];
            array[j-1] = tmp;
            j--;
          }
          k++;
        }
    	}
    }
    ```



#### 14. 链表中倒数第k个结点

> 输入一个链表，输出该链表中倒数第k个结点。

+ 思路：
  + 用两个指针指向链表头，第一个指针先走k步（相当于从原来的链表头走k-1步）
  + 然后两个指针一起走，直到第一个指针到了链表尾部

+ 注意事项：
  + 当k大于链表长度时返回null



####15.  



---

### 排序专题

#### 1. 快速排序

+ 思路：**选基、分割、递归**
  + 选基：挑选pivot用于对比，

  + 分割：分割数组

    ```java
        int pivot = arr[high];
        int i = low;	
        for (int j = low; j < high; ++j) {
          if (arr[j] < pivot) {
            swap(arr, i, j);	// j指向小的值
            ++i;	// i指向大的值
          }
        }
        swap(arr, i, high);
        return i;
    ```

    











