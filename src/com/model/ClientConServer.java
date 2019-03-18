/**
 * 这是客户端连接服务器的后台代码
 */
package com.model;
import com.tools.*;

import java.net.*;
import java.util.Date;

import javax.swing.JOptionPane;

import java.io.*;

import com.basic.*;
public class ClientConServer {
	
	public Socket s;
	private static String IP_STRING;
	//发送第一次请求
	public ClientConServer() {
		super();
		if(IP_STRING==null)
			IP_STRING=JOptionPane.showInputDialog("请输入服务端IP:");
	}
	public Message sendLoginInfoToServer(Object o) {
		Message m = null;
		try {
			//s=new Socket("127.0.0.1",8888);
			s=new Socket(IP_STRING,8888);
			ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(o);
			ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
			Message ms=(Message)ois.readObject();
			//这里就是验证用户登录的地方
			if(ms.getMesType().equals("1"))
			{
				//就创建一个该Wechat和服务器端保持通讯连接得线程
				ClientConServerThread ccst=new ClientConServerThread(s);
				//启动该通讯线程
				ccst.start();
				ManageClientConServerThread.addClientConServerThread
				(((User)o).getUserId(), ccst);
				m = ms;
			}
			else{
				//关闭Socket
				if(s!=null)
					s.close();
				m =ms;
			}
		} catch (ConnectException e) {
			JOptionPane.showMessageDialog(null, "服务器连接超时！", "警告", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		return m;
	}
	public boolean sendunLoadInfoToServer(Object o) {
		boolean b=false;
		try {
			//s=new Socket("127.0.0.1",8888);
			s=new Socket(IP_STRING,8888);
			ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(o);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}
	public Message sendRegisteInfoToServer(Object o) {
		Message m = null;
		try {
			//s=new Socket("127.0.0.1",8888);
			s=new Socket(IP_STRING,8888);
			ObjectOutputStream oos=new ObjectOutputStream(s.getOutputStream());
			oos.writeObject(o);
			ObjectInputStream ois=new ObjectInputStream(s.getInputStream());
			
			Message ms=(Message)ois.readObject();
			//注册成功
			if(ms.getMesType().equals("4"))
			{
				m = ms;
			}
			else{
				//关闭Socket
				if(s!=null)
					s.close();
				m =ms;
			}
		} catch (ConnectException e) {
			JOptionPane.showMessageDialog(null, "服务器连接超时！", "警告", JOptionPane.WARNING_MESSAGE);
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		return m;
	}
}
