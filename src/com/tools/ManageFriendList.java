/**
 * 管理好友列表类
 */
package com.tools;

import java.util.*;

import com.view.FriendList;
public class ManageFriendList {

	private static HashMap<String, FriendList> hm=new HashMap<String, FriendList>();
	
	public static void addFriendList(String wechatid,FriendList friendList){
		
		hm.put(wechatid, friendList);
	}
	public static FriendList getFriendList(String wechatId)
	{
		return (FriendList)hm.get(wechatId);
	}
}
