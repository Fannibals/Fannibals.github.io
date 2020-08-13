###《深入理解JVM》笔记

1- 类型转换指令中会产生的小问题

- ```java
  public class Test{
      public static void main(String[] args) {
          int hour = 24;
          long mi = hour * 60 * 60 * 1000;
          long mic = hour * 60 * 60 * 1000 * 1000;
          System.out.println(mic/mi);
      }
  }
  ```

- 这里的值为5，原因是mic 的这个值在完成最后一个乘1000之后已经超过了int的range，然而因为hour是int类型，所以之前一直按照int自乘

  ```
  	0: bipush        24 
    	...
    27: sipush        1000
    30: imul
    31: i2l
  ```

  + 然后因为此时imul之后已经超出int类型range，因而进行类型转换指令 ```i2l``` 

- 如果把hour 设置成long

- 则

  ```
  0: ldc2_w        #2                  // long 24l
  ```

  可以看出不再是比 bipush了