package cn.thinkjoy.startup.config;

/**
 * 配置文件节点
 * 对应到每个项目下的qtssp.properties文件
 *
 */
public final  class ConfigKeyNode {
	
		/**
		 * IP地址 address
		 */
		public static final String address="address";
		/**
		 * 数据库名字 database-name
		 */
		public static final String databaseName="database-name";
		/**
		 * 数据库版本号 database-version
		 */
		public static final String databaseVersion="database-version";
		/**
		 * 数据库表的JavaBean database-class
		 */
		public static final String databaseClass="database-class.";
		/**
		 * 数据库表JavaBean 的包名
		 */
		public static final String beanPackageName = "database-class-package";
		/**
		 * IP地址默认地址
		 */
		public static final String DEFAULTVALUEADDRESS = "127.0.0.1:8080";

}
