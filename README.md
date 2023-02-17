# 项目名称：超级锁
站在reddison和spring boot这两位大佬肩膀上诞生出来的组件

# 设计思想
使用注解实现锁的自动开启和关闭，让开发者将更多的精力放在业务层面上。

# 设计流程概述
1. 在业务层方法使用自定义注解@Lock(@MultiLock、@RedLock)
2. 在LockAop拦截进行锁的开启和关闭

# 使用示例
1. 查看 application.yml 查看相关配置
2. 测试用例 com.superhero.lock.lock.LockApplicationTests