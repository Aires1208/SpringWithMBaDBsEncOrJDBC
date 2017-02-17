JDBC(Java Database Connectivity)代表Java编程语言与数据库连接的标准API,然而JDBC只是接口,JDBC驱动才是真正的接口实现,没有驱动无法完成数据库连接. 每个数据库厂商都有自己的驱动,用来连接自己公司的数据库(如Oricle, MySQL, DB2, MS SQLServer). 

数据库连接池

前面通过DriverManger获得Connection, 一个Connection对应一个实际的物理连接,每次操作都需要打开物理连接, 使用完后立即关闭;这样频繁的打开/关闭连接会造成不必要的数据库系统性能消耗.
数据库连接池提供的解决方案是:当应用启动时,主动建立足够的数据库连接,并将这些连接组织成连接池,每次请求连接时,无须重新打开连接,而是从池中取出已有连接,使用完后并不实际关闭连接,而是归还给池.
JDBC数据库连接池使用javax.sql.DataSource表示, DataSource只是一个接口, 其实现通常由服务器提供商(如WebLogic, WebShere)或开源组织(如DBCP,C3P0和HikariCP)提供.

    数据库连接池的常用参数如下:
        数据库初始连接数;
        连接池最大连接数;
        连接池最小连接数;
        连接池每次增加的容量;
事务

事务是由一步/几步数据库操作序列组成的逻辑执行单元, 这些操作要么全部执行, 要么全部不执行.

    注: MySQL事务功能需要有InnoDB存储引擎的支持, 详见MySQL存储引擎InnoDB与Myisam的主要区别.

ACID特性

    原子性(A: Atomicity): 事务是不可再分的最小逻辑执行体;
    一致性(C: Consistency): 事务执行的结果, 必须使数据库从一个一致性状态, 变为另一个一致性状态.
    隔离性(I: Isolation): 各个事务的执行互不干扰, 任意一个事务的内部操作对其他并发事务都是隔离的(并发执行的事务之间不能看到对方的中间状态,不能互相影响)
    持续性(D: Durability): 持续性也称持久性(Persistence), 指事务一旦提交, 对数据所做的任何改变都要记录到永久存储器(通常指物理数据库).


commons-dbutils是Apache Commons组件中的一员,提供了对JDBC的简单封装,以简化JDBC编程;使用dbutils需要在pom.xml中添加如下依赖:

MyBatis是对JDBC的封装,使开发人员只需关注SQL本身,而不需花费过多的精力去处理如注册驱动、设置参数、创建Connection/Statement、解析结果集等JDBC过程性代码.MyBatis基于XML/注解的方式配置Statement,执行SQL,并将执行结果映射成Java对象, 大大降低了数据库开发的难度.

实现MyBatis与Spring整合之后,可以使用Spring来管理SqlSessionFactory和mapper接口,Spring自动使用SqlSessionFactory创建SqlSession,并将实现好DAO接口注册到Spring容器中, 供@Autowired使用.


缓存作用是提升系统整体性能(不是提升数据库性能:因为缓存将数据库中的数据存放到内存,下次查询同样内容时直接从内存读取,减轻数据库压力,而且直接从内存中读取数据要比从数据库检索快很多,因此可以提升系统整体性能).

    缓存数据更新:当一个作用域(一级缓存为SqlSession/二级缓存为namespace)进行了C/U/D操作后,默认该作用域下所有缓存都被清空.

一级缓存

MyBatis默认开启了一级缓存.一级缓存是基于org.apache.ibatis.cache.impl.PerpetualCache的HashMap本地缓存,其存储作用域为SqlSession,同一个SqlSession几次执行相同SQL,后面的查询会直接从缓存中加载,从而提高查询效率/减轻数据库压力.当SqlSession经flush/close后,该SqlSession中的所有Cache数据被清空.
二级缓存

与一级缓存机制类似,MyBatis二级缓存默认也是采用PerpetualCache的HashMap存储,不同在于二级缓存存储作用域为namespace/mapper,并且可以自定义缓存实现,如Ehcache.