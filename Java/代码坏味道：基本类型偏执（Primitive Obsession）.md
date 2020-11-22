## 代码坏味道：基本类型偏执（Primitive Obsession）

### 1. 现象

- Coding的时候总喜欢用基本的类型（基本数据类型，String等），而非使用对象来实现简单任务



### 2. 原因

- 诞生于类初建的时候，为了实现简单任务，直接使用基本类型去表示一种事物，但是随着该事物逻辑的增加，特性的增多，基本数据类型也越来越多

  

### 3. 常见情况与解决方法

1. **一些基本类型的数值存在一定的关联**
   
   - 使用 **<font color=red>Replace Data Value with Object 以对象取代数据值</font>** 将原本独立存在的数据值包装成对象并替换
2. **需要替换的数值是类型码，且本身并不影响行为**
   
   - 使用 **<font color=red>Replace Type Code with Class 以类取代类型码</font>**将其替换
   
3. **替换类型码时存在与之相关的条件表达式**

   - 使用 **<font color=red>Replace Type Code with SubClass 以子类取代类型码</font>**将其替换
     - repeated switches中的例子
4. **存在一组应该总是被放在一起的字段**

   - 使用 **<font color=red>Extract Class 提炼类</font>** 将这些字段提炼至新类

5. **在方法的参数列中看到基本数据类型**

   - 使用 **<font color=red>Introduce Parameter Object 引入参数对象</font>** 将其替换

6. **需要从数组中挑选数据**

   - 使用 **<font color=red>Replace Array with Object 以对象取代数组</font>** 将数组直接转换为对象

   ```java
   // 你有一个数组，其中的元素各自代表不同的东西。
   String[] row = new String[3];
   row[0] = "Liverpool";
   row[1] = "15";
   ```

   ```java
   // 以对象替换数组。对于数组中的每个元素，以一个字段来表示。
   Performance row = new Performance();
   row.setName("Liverpool");
   row.setWins("15");
   ```

   

---

**Replace Data Value with Object:**

- 下面有一个代表「定单」的Order class，其中以一个字符串记录定单客户的名称。

- 现在，我希望改以一个对象来表示客户信息，这样我就有充裕的弹性保存客户地址、信用 等级等等信息，也得以安置这些信息的操作行为。Order class最初如下：

   ```java
   class Order {
     private String customer;
     public Order(String customer) {
       this.customer = customer;
     }
     public String getCustomer() {
       return cuctomer;
     }
     public void setCustomer(String cus){
       customer = cus;
     }
   }
   ```

- Order类的客户代码的一种方法

   ```java
   private static int numberOfOrdersFor(List<Order> orders, String customer) {
     int result = 0;
     Iterator iter = orders.iterator();
     while(iter.hasNExt()){
       Order each = (Order) iter.next();
       if (each.getCustomerName().equals(customer)){
         result++;
       }
     }
     return result;
   }
   ```

- 创建Customer对象并建立final值域，用来保存一个String

  ```java
  class Customer {
  	private final String name;
  	public Customer(String name) {
  		this.name = name;
  	}
    public String getName() {
      return name;
    }
  }
  ```

- 然后把Order中的customer值修改为Customer实例

  - 并修改其参数名，使其更加符合本意
  - 实值对象应该是不可修改的，这样可以避免一些别名错误

  ```java
   class Order {
     private Customer customer;
     public Order (String customerName) {
         this.customer = new Customer(customer);
     }
     public String getCustomerName() {
         return customer.getName();
     }
     // 实值对象应该是不可修改的，这样可以避免一些别名错误
     public void setCustomer(String customerName) {
         customer = new Customer(customerName);
     }   
   }
  ```

  https://www.kancloud.cn/sstd521/refactor/194284
