/**
 * 这是一个管理用户聊天界面的类
 */
package com.tools;

import java.util.*;

import javax.swing.JFrame;

import com.view.WeChat;
import com.view.QunChat;

public class ManageWeChat {
	
	private static HashMap<String, JFrame> hm=new HashMap<String, JFrame>();
	//加入
	public static void addWeChat(String loginIdAndFriendId,WeChat wechatChat)
	{
		hm.put(loginIdAndFriendId, wechatChat);
	}
	//取出
	public static WeChat getWeChat(String loginIdAndFriendId )
	{
		return (WeChat)hm.get(loginIdAndFriendId);
	}
	public static void RemoveWeChat(String loginIdAndFriendId)
	{
		hm.remove(loginIdAndFriendId);
	}
	public static void addQunChat(String loginIdAndFriendId,QunChat weChat)
	{
		hm.put(loginIdAndFriendId, weChat);
	}
	//取出
	public static QunChat getQunChat(String loginIdAndFriendId )
	{
		return (QunChat)hm.get(loginIdAndFriendId);
	}
	public static void RemoveQunChat(String loginIdAndFriendId)
	{
		hm.remove(loginIdAndFriendId);
	}
	
}
