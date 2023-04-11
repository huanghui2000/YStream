Skip to content
Search or jump to…
Pull requests
Issues
Codespaces
Marketplace
Explore
 
@huanghui2000 
huanghui2000
/
YStream
Public
Cannot fork because you own this repository and are not a member of any organizations.
Code
Issues
Pull requests
Actions
Projects
Wiki
Security
Insights
Settings
Create YStream.md
2023-4-11  简要的文档说明
 master
@huanghui2000
huanghui2000 committed now 
1 parent 5fdc7ab
commit db2dc3f
Showing 1 changed file with 57 additions and 0 deletions.
 57  
YStream.md
@@ -0,0 +1,57 @@
# YStream使用文档

- #### 配置流处理的触发策略：

  其中类注解：

  ```java
  @StreamMarker 
  ```

  ​       扫描器只会注册存在该注解的类，且策略类必须在启动类的子包或者同级位置

  已有的策略注解：

  ```java
  @StreamLen(len= '触发流的长度')	
  @StreamOperation(type='策略类型',content='触发的关键词')
  ```

  策略函数示例：

  ```java
  @StreamLen(len = 3)
  public static String handle1(String str) {
      return "这个字符串长度为3";
  }
  
  @StreamOperation(type = OperationType.*INCLUDE*, content = "123")
  public static String handle2(String str) {
      return "这个字符串包含123";
  }
  ```

  ​	其中，策略不能为私有方法，且入参一定要为String（后续会重新修缮数据支持类型）

- #### 加载YStream中心启动器：

  ```java
  YStreamApplication.run(Main.class);
  ```

  ​	   其中,同Spring所有框架一样，该启动类不可以为默认（default）架包下的类

- #### 实例化信道，进行消息处理：

  ```
   Scanner in = new Scanner(System.in);
   YChannel channel = new YChannel();
   while (true) {
        String msg = in.next();
        channel.process(msg);
        System.out.println("处理结果：" + channel.getResult());
   }
  ```
  ![YStream](https://github.com/huanghui2000/YStream/blob/master/YStream.png)
