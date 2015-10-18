1 修改log4j.properties，日志级别，路径等
2 修改applicationContext.xml ，数据源名称等
3 修改weblogic的setDomainEnv.sh，增加行
PRE_CLASSPATH="${PRE_CLASSPATH}/app/mobileApps/mobileServer/WEB-INF/lib/hibernate-jpa-2.0-api-1.0.1.Final.jar"

ver.jardesc打包ver.jar用到，打包ver.jar前将Apk,WebResource,WebResourceIncr,SimpleHibernateDao这几个实体查询缓存注释去掉
