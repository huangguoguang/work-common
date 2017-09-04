package com.huangguang.work.properties.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.huangguang.work.util.LogUtil;

/**
 * 读取配置文件工具类
 * @author huangguang
 *
 */
public class SDKConfig {
	
	public static final String FILE_NAME = "acp_sdk.properties";
	
	//饿汉式单例模式
	public static SDKConfig config = new SDKConfig();
	
	private SDKConfig () {
		super();
	}
	
	public static SDKConfig getConfig () {
		return config;
	}
	
	private Properties properties;
	
	private String test;
	
	public void loadPropertiesFromPath (String rootPath) {
		if (StringUtils.isNoneBlank(rootPath)) {
			LogUtil.writeLog("从路径读取配置文件：" + rootPath + File.separator + FILE_NAME);
			File file = new File(rootPath + File.separator + FILE_NAME);
			InputStream in = null;
			if (file.exists()) {
				try {
					in = new FileInputStream(file);
					properties = new Properties();
					properties.load(in);
					loadProperties(properties);
				} catch (FileNotFoundException e) {
					LogUtil.writeErrorLog(e.getMessage(), e);
				} catch (IOException e) {
					LogUtil.writeErrorLog(e.getMessage(), e);
				} finally {
					if (null != in) {
						try {
							in.close();
						} catch (IOException e) {
							LogUtil.writeErrorLog(e.getMessage(), e);
						}
					}
				}
			} else {
				LogUtil.writeErrorLog(rootPath + FILE_NAME + "不存在，加载参数失败");
			}
		} else {
			loadPropertiesFromSrc();
		}
	}
	
	/**
	 * 从classpath路径下加载配置参数
	 */
	public void loadPropertiesFromSrc() {
		LogUtil.writeLog("从classpath: " +SDKConfig.class.getClassLoader().getResource("").getPath()+" 获取属性文件"+FILE_NAME);
		InputStream in = null;
		try {
			in = SDKConfig.class.getClassLoader().getResourceAsStream(FILE_NAME);
			if (null != in) {
				properties = new Properties();
				try {
					properties.load(in);
				} catch (IOException e) {
					throw e;
				}
			}
			loadProperties(properties);
		} catch (IOException e) {
			LogUtil.writeErrorLog(e.getMessage(), e);
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
					LogUtil.writeErrorLog(e.getMessage(), e);
				}
			}
		}
	}

	/**
	 * 根据传入的 {@link #load(java.util.Properties)}对象设置配置参数
	 * 
	 * @param pro
	 */
	public void loadProperties(Properties pro) {
		LogUtil.writeLog("开始从属性文件中加载配置项");
		String value = null;
		
		value = pro.getProperty("test");
		
		this.test = value;
		
	}

	public String getTest() {
		return test;
	}

	public void setTest(String test) {
		this.test = test;
	}
	
	



}
