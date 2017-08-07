# source
后台管理系统

提供便捷的j2ee开发架构。

本项目采用：
		springmvc + shiro + mybatis-plus + jsp
	后端:
		SpringBoot 1.5.3.RELEASE
		MyBatis-Plus 2.0.8
		MyBatis 3.4.4
		Spring 4.3.8.RELEASE
		hibernate-validator 5.3.5.Final
		Ehcache 3.3.1
		Fastjson 1.2.31
		Shiro 1.4.0
	

1）如何启动项目
配置项目热部署启动：
第一步：
配置Project-->Preferences--->java  选择配置的JDK -->edite-->defaults VM arguments 
配置： -Dmaven.multiModuleProjectDirectory=$M2_HOME
保存
第二步： 选择项目source 右键Run as --->Maven install 

第三步： 选择项目 source-admin 右键Run as --->Maven build... ---> 在Goals 中输入：	
	 	tomcat7:run ---->run
	 
访问主页：http://localhost:8888/ 
用户名：admin
密码：admin