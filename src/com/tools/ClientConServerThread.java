/**
 * 这是客户端和服务器端保持通讯的线程.
 */
package com.tools;

import com.view.*;
import java.awt.Component;
import java.io.*;
import java.net.*;
import javax.swing.JOptionPane;
import com.basic.*;

public class ClientConServerThread extends Thread {

	private static final Component NULL = null;
	private Socket s;
    public static final MessageFrame msf = new MessageFrame();
	//构造函数
	public ClientConServerThread(Socket s)
	{
		this.s=s;
	}
	@Override
	public void run()
	{
		new Thread(msf).start();	//消息提示框闪动效果启动
		while(true)
		{
			//不停的读取从服务器端发来的消息
			try {
				ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
				Message m=(Message)ois.readObject();
				if(m.getMesType().equals(MessageType.message_comm_mes)){	//处理普通信息
					//把从服务器获得消息，显示到该显示的聊天界面
					WeChat weChat=ManageWeChat.getWeChat(m.getGetter()+" "+m.getSender());
					//显示
					if(weChat!=null)
						weChat.showMessage(m);
					if(weChat==null)	//如果当前聊天窗口不存在就给出消息提醒
					{
						if(m!=null)
						{
							msf.setLocation(ManageFriendList.getFriendList(m.getGetter()).getLocation().x, ManageFriendList.getFriendList(m.getGetter()).getLocation().y+630);
							msf.setMs(m);
							msf.addJLabel(m.getGetter(),m.getSender());
							msf.setVisible(true);
						}
					}
				}else if(m.getMesType().equals(MessageType.message_ret_onLineFriend))
				{
					String getter=m.getGetter();
					//修改相应的好友列表.
					FriendList friendList=ManageFriendList.getFriendList(getter);
					//更新在线好友.
					if(friendList!=null)
						friendList.updateFriend(m);	//更新好友列表
				}
				else if(m.getMesType().equals(MessageType.messgae_ret_unloadFriends))
				{
					String getter=m.getGetter();
					//修改相应的好友列表.
					FriendList friendList=ManageFriendList.getFriendList(getter);
					//更新在线好友.
					if(friendList!=null)
					{
						friendList.updateunloadFriend(m);;
					}
				}
				else if(m.getMesType().equals(MessageType.message_ret_addFriend))
				{
					String con=m.getCon();
					if(con == null)
						JOptionPane.showMessageDialog(NULL, "您添加的好友不存在！");
				}
				else if(m.getMesType().equals(MessageType.message_acceptadd))
				{
					FriendList List = ManageFriendList.getFriendList(m.getGetter());
					if(List!=null){
						List.init_update(m);	//更新好友列表
					}
				}
				else if(m.getMesType().equals(MessageType.message_jujueadd))
				{
					JOptionPane.showMessageDialog(NULL, m.getSender()+"拒绝了你的好友请求");
				}
				else if(m.getMesType().equals(MessageType.message_ret_oldMessage))
				{
					//把从服务器获得消息，显示到该显示的聊天界面
					WeChat weChat=ManageWeChat.getWeChat(m.getSender()+" "+m.getGetter());
					//显示
					weChat.showoldMessage(m);
				}
				else if(m.getMesType().equals(MessageType.message_ret_outlineMessage))
				{
					WeChat weChat=ManageWeChat.getWeChat(m.getSender()+" "+m.getGetter());
					//显示离线之后接受到的信息
					weChat.showMessage(m);
				}
				else if(m.getMesType().equals(MessageType.message_ret_pointMessage))
				{
					if(m!=null)
					{
						msf.setLocation(ManageFriendList.getFriendList(m.getSender()).getLocation().x, ManageFriendList.getFriendList(m.getSender()).getLocation().y+630);
						msf.setMs(m);
						msf.addJLabel(m.getSender(),m.getGetter());
						msf.setVisible(true);
					}
				}
				else if(m.getMesType().equals(MessageType.message_addpoint))
				{
					if(m!=null)
					{
						msf.setLocation(ManageFriendList.getFriendList(m.getGetter()).getLocation().x, ManageFriendList.getFriendList(m.getGetter()).getLocation().y+630);
						msf.setMs(m);
						msf.addJLabel(m.getGetter(),m.getSender());
						msf.setVisible(true);
					}
				}
				else if(m.getMesType().equals(MessageType.message_hasfriendask))
				{
					if(m!=null)
					{
						msf.setLocation(ManageFriendList.getFriendList(m.getGetter()).getLocation().x, ManageFriendList.getFriendList(m.getGetter()).getLocation().y+630);
						msf.setMs(m);
						msf.addJLabel(m.getGetter(),m.getSender());
						msf.setVisible(true);
					}
				}
				else if(m.getMesType().equals(MessageType.message_shake))
				{
					//获得振动接收方的聊天窗口
					WeChat weChat=ManageWeChat.getWeChat(m.getGetter()+" "+m.getSender());
					//执行振动函数
					if(weChat!=null)
						weChat.shake(m);
					if(weChat==null)	//如果当前聊天窗口不存在就创建窗口执行振动函数
					{
						if(m!=null)
						{
							WeChat weChat1 = new WeChat(m.getGetter(),m.getSender(), m.getSender());
							ManageWeChat.addWeChat(m.getGetter() + " " + m.getSender(), weChat1);
							weChat1.shake(m);
						}
					}
				}
				else if(m.getMesType().equals(MessageType.message_sendfile)){
					new ReceivedFileFrame(m);
				}
				else if(m.getMesType().equals(MessageType.message_returnfileinfo)) 
				{
					WeChat wechat=ManageWeChat.getWeChat(m.getGetter()+" "+m.getSender());
					if(wechat!=null)
						wechat.showfileinfoMsg(m, "");
				}
				//返回个人资料
				else if(m.getMesType().equals(MessageType.message_data))
				{
					PersonalInfo personalinfo=new PersonalInfo(m.getSender());
					personalinfo.setTexts(m.getCon());
					personalinfo.setVisible(true);
				}
				else if(m.getMesType().equals(MessageType.message_fridata)) {
					FriendPersonalInfo personalinfo=new FriendPersonalInfo(m.getSender());
					personalinfo.setTexts(m.getCon());
					personalinfo.setVisible(true);
				}
				else if (m.getMesType().equals(MessageType.message_Qun_mes)) {
					QunChat qunChat = ManageWeChat.getQunChat(m.getGetter()+"  "+ "100000");
					// 显示
					if (qunChat != null)
						qunChat.showMessage(m);
					if (qunChat == null) {
						if (m != null) {
							msf.setLocation(ManageFriendList.getFriendList(m.getGetter()).getLocation().x, ManageFriendList.getFriendList(m.getGetter()).getLocation().y+630);
							msf.setMs(m);
							msf.addJLabel(m.getGetter(), "100000");
							msf.setVisible(true);
						}
					}
				}
				else if (m.getMesType().equals(MessageType.message_Qun_oldmes))
				{
					QunChat qunChat = ManageWeChat.getQunChat(m.getSender()+"  "+m.getGetter());
					// 显示
					qunChat.showoldMessage(m);
				}
				else if (m.getMesType().equals(MessageType.message_getQunPeople))
				{
					QunChat qunChat = ManageWeChat.getQunChat(m.getSender()+"  "+m.getGetter());
					qunChat.addJLabel(m.getGetter(), m.getCon());
				}
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "服务器开小差了，请重新登录！", "警告", JOptionPane.WARNING_MESSAGE);
				System.exit(1);
			}
		}
	}
	public Socket getS() {
		return s;
	}
	public void setS(Socket s) {
		this.s = s;
	}
}
