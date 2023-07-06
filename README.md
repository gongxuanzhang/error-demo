# 这是在项目中遇到的一个错误demo
>当执行Cluster类的main方法时，程序会阻塞在 latch.await()上，且jconsole工具没有检测出死锁

CompletableFuture里面的异步方法不会调用  

CompletableFuture的线程池是为了方便jconsole查看线程状态，两个线程均没有阻塞


## 两种方法可以正常运行：
1. 把单例变成懒加载
2. 把构造方法中的   list = List.of("a", "b"); 注释掉


##  我的问题：

我猜测的方向是因为类加载的锁导致的。 但是原理还是想不通

想让博哥帮我解惑

