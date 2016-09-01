package cn.thinkjoy.startup.config;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 配置文件解析类
 * 与ConfigKeyNode文件对应
 * @author apple
 *
 */
public class ConfigRead {
	//IP地址 address
	private String address;
	//数据库名字 database-name
	private String databaseName;
	//数据库版本号 database-version
	private int databaseVersion;
	//数据库表的JavaBean的包名
	private String beanPackageName;
	//应用程序包名
	private String pkName;

	//数据库表的JavaBean database-class
	private List<Class<?>> databaseClass;
	//配置文件对像
	private Properties properties;
	

	private Context mContext;
	private volatile static ConfigRead read = null;
	


	private ConfigRead(){}
	
	private ConfigRead(Context mContext){
		this.mContext = mContext;
	}
	
	public static synchronized ConfigRead getInstance(Context mContext){
		if (read == null) {
			synchronized (ConfigRead.class) {
				if (read == null) {
					read = new ConfigRead(mContext);
				}
			}
		}
		return read;
		/*if(read == null){
			read = new ConfigRead(mContext);
		}
		return read;*/
	}
	
	
	

	/**
	 * 解析配置文件，在程序运行的时候调用
	 * 配置文件必须保存在assets 文件
	 * 读书平台默认配置文件  qtssp.properties
	 * @throws IOException 
	 */
	public void parsingConfig() {
		try {
			AssetManager asset = mContext.getAssets();
			String[] locales = asset.list("");
			for (String string : locales) {
				if (string.endsWith(".properties")) {
					parsingConfig(string);
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 解析配置文件，在程序运行的时候调用
	 * 配置文件必须保存在assets 文件里
	 * @param configName 配置文件名
	 */
	public void parsingConfig(String configName){
		  properties = new Properties();
		  try {
	       PackageInfo packageInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), PackageManager.GET_CONFIGURATIONS);
		   properties.load(mContext.getAssets().open(configName));
		   address = properties.getProperty(ConfigKeyNode.address, ConfigKeyNode.DEFAULTVALUEADDRESS);
		   // port = Integer.valueOf(address.split(":")[1]);
		   // ip = address.split(":")[0];
		   databaseName = properties.getProperty(ConfigKeyNode.databaseName, "qtspp");
		   databaseVersion = Integer.valueOf(properties.getProperty(ConfigKeyNode.databaseVersion, "1"));
		   beanPackageName = properties.getProperty(ConfigKeyNode.beanPackageName);
		   pkName = packageInfo.packageName;
		   String temp = "";
		   databaseClass = new ArrayList<Class<?>>();
		   for(int i = 1;i< 100;i++){
			   temp = properties.getProperty(ConfigKeyNode.databaseClass+i, "");
			   if(!"".equals(temp)){
				   parsingDataClassList(temp); 
			   }else{
				  return; 
			   }
			}
		  } catch (Exception e) {
		   e.printStackTrace();
		  }
	}
	
	private void parsingDataClassList(String dataList) throws ClassNotFoundException{
		if("".equals(dataList)){
			return;
		}
		String[] temp = dataList.split(",");
		for (String string : temp) {
			databaseClass.add(Class.forName(beanPackageName+"."+string));
		}
	}
	

	public String getBeanPackageName() {
		return beanPackageName;
	}

	public void setBeanPackageName(String beanPackageName) {
		this.beanPackageName = beanPackageName;
	}
	/**
	 * 
	* @Title: getTimeoutFile 
	* @param    
	* @return int   返回为 毫秒数
	* @throws
	 */

	public String getPkName() {
		return pkName;
	}

	public void setPkName(String pkName) {
		this.pkName = pkName;
	}


	/**
	 * 
	* @Title: getTimeoutData 
	* @param    
	* @return int   返回为 毫秒数
	* @throws
	 */

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}


	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public int getDatabaseVersion() {
		return databaseVersion;
	}

	public void setDatabaseVersion(int databaseVersion) {
		this.databaseVersion = databaseVersion;
	}

	public List<Class<?>> getDatabaseClass() {
		return databaseClass;
	}

	public void setDatabaseClass(List<Class<?>> databaseClass) {
		this.databaseClass = databaseClass;
	}



	@Override
	public String toString() {
		return "ConfigRead [address=" + address + ", databaseName=" + databaseName
				+ ", databaseVersion=" + databaseVersion + ", beanPackageName="
				+ beanPackageName + ", databaseClass=" + databaseClass + "]";
	}
}
