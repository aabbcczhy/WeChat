package com.tools;
/**
 * 消息接收窗口及响应，闪动效果是线程的功劳
 */
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Panel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.ObjectOutputStream;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;

import com.view.*;
import com.basic.*;

public class MessageFrame extends JFrame implements MouseListener,Runnable{
	private static final long serialVersionUID = 1L;
	private JLabel head;
	private JLabel[] jlb=new JLabel[15];
	private Panel panel;
	private String owner,friend;
	int x,y,width,height=30,record=0;
	private Message ms=new Message();
	Font font = new Font("微软雅黑",0,14);
	
	public MessageFrame(){
		x=1100;
		y=700-height;
		width=100;
		this.setAlwaysOnTop(true);
		this.setSize(width, height);
		this.setUndecorated(true);
		
		panel = new Panel();
		panel.setLayout(null);
		
		head = new JLabel("当前暂无消息");
		head.setFont(font);
		head.setBounds(10, 0, 90, 30);
		panel.add(head);
		
		this.add(panel);
		this.setVisible(false);
	}
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 1) {
			if (ms.getMesType().equals(MessageType.message_ret_pointMessage)) {
				String friendNo = ((JLabel) e.getSource()).getText();
				WeChat weChat = new WeChat(this.owner,this.friend,friendNo);
				ManageWeChat.addWeChat(this.owner + " " + friendNo, weChat);
				Message m = new Message();
				m.setMesType(MessageType.message_ret_outlineMessage);
				m.setSender(this.owner);
				m.setGetter(friendNo);
				m.setSendTime(new Date().toString());
				// 发送给服务器
				try {
					ObjectOutputStream oos = new ObjectOutputStream(ManageClientConServerThread.getClientConServerThread(owner).getS().getOutputStream());
					oos.writeObject(m);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			if (ms.getMesType().equals(MessageType.message_comm_mes)) {
				WeChat weChat = ManageWeChat.getWeChat(this.owner + " " + ms.getSender());
				if(weChat==null) {
					weChat = new WeChat(this.owner,this.friend, ms.getSender());
					ManageWeChat.addWeChat(this.owner + " " + ms.getSender(), weChat);
					weChat.showMessage(ms);
				}else {
					weChat.showMessage(ms);
				}
			}
			if (ms.getMesType().equals(MessageType.message_addpoint)) {
				new AccepAdd(ms);
			}
			if (ms.getMesType().equals(MessageType.message_hasfriendask)) {
				new AccepAdd(ms);
			}
			if (ms.getMesType().equals(MessageType.message_Qun_mes)) {
                QunChat qunChat = new QunChat(ms.getGetter(),ms.getFriname(), "100000");
				ManageWeChat.addQunChat(this.owner + "  100000", qunChat);
				Message m=new Message();
				m.setMesType(MessageType.message_getQunPeople);
				m.setSender(ms.getGetter());
				m.setGetter("100000");
				m.setSendTime(new Date().toString());
				//发送给服务器.
				try {
					ObjectOutputStream oos=new ObjectOutputStream(ManageClientConServerThread.getClientConServerThread(owner).getS().getOutputStream());
					oos.writeObject(m);
				} catch (Exception e1) {
					e1.printStackTrace();
				}	
				qunChat.showMessage(ms);
			}
			deleteJLabel(e.getComponent());
		}
	}
	public void mousePressed(MouseEvent e) {
		
	}
	public void mouseReleased(MouseEvent e) {
		
	}
	public void mouseEntered(MouseEvent e) {
		
	}
	public void mouseExited(MouseEvent e) {
		
	}
	public void addJLabel(String getter,String sender) {
		this.height+=30;
		this.record++;
		this.owner=getter;
		this.friend=sender;
		boolean flag=false;
		for(int i=0;i<record-1;i++)
		{
			flag=jlb[i].getText().equals(sender);
		}
		if(!flag){
			jlb[record-1]=new JLabel(sender);
			jlb[record-1].addMouseListener(this);
			jlb[record-1].setFont(font);
			jlb[record-1].setBounds(10, 30*record, 100, 30);
			panel.add(jlb[record-1]);
		}else{
			height-=30;
			record--;
		}
		head.setText(record+" 条新消息");
		y=700-height;
		panel.setBounds(0, 0, width, height);
		this.setSize(width, height);
	}
	public void deleteJLabel(Component comp)
	{
		height-=30;
		record--;
		head.setText(record+" 条新消息");
		if(record==0) {
			this.setVisible(false);
		}
		this.remove(comp);
		y=700-height;
		this.setSize(width, height);
	}
	public void setMs(Message m) {
		ms=m;
	}
	@Override
	public void run() {
		while(true) {
			this.panel.setBackground(Color.orange);
			try {
				Thread.sleep(400);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.panel.setBackground(Color.white);
			try {
				Thread.sleep(400);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
