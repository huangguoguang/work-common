package com.huangguang.work.properties.util.test;

import com.huangguang.work.properties.util.SDKConfig;

public class TestSDKConfig {
	public static void main(String[] args) {
		//SDKConfig.getConfig().loadPropertiesFromPath("D:\\work-common");
		SDKConfig.getConfig().loadPropertiesFromPath("");
		String path = SDKConfig.class.getClassLoader().getResource("test").getPath();
		SDKConfig.getConfig().loadPropertiesFromPath(path);
		
		System.out.println(SDKConfig.getConfig().getTest());
	}

}
