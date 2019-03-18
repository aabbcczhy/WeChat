/**
 * 这是一个管理客户端和服务器保持通讯的线程类
 */
package com.tools;

import java.util.*;
public class ManageClientConServerThread {

	private static HashMap<String, ClientConServerThread> hm=new HashMap<String, ClientConServerThread>();
	
	//把创建好的ClientConServerThread放入到hm
	public static void addClientConServerThread(String wechatId,ClientConServerThread ccst)
	{
		hm.put(wechatId, ccst);
	}
	public static void RemoveClientConServerThread(String wechatId)
	{
		hm.remove(wechatId);
	}
	//可以通过wechatId取得该线程 
	public static ClientConServerThread getClientConServerThread(String wechatId)
	{
		return (ClientConServerThread)hm.get(wechatId);
	}
}
