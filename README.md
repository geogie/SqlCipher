# SqlCipher

说明：  
1. 数据库加密：防止手机root后拿到数据库暴漏信息。
2. 如果开启混淆加入：  
   -keep  class net.sqlcipher.** {*;}  
   -keep  class net.sqlcipher.database.** {*;}
3. 自定义数据库路径

参考：  
[博客](http://www.cnblogs.com/whoislcj/archive/2016/07/30/5511522.html)  
[官方配置](https://www.zetetic.net/sqlcipher/sqlcipher-for-android/)  
[sqlcipher的git地址](https://github.com/sqlcipher/android-database-sqlcipher)

