package com.view;
/**
 * 添加好友界面
 */
import javax.swing.*;

import com.basic.Message;
import com.basic.MessageType;
import com.tools.ManageClientConServerThread;
import com.tools.ManageFriendList;

import java.awt.Color;
import java.awt.Panel;
import java.awt.event.*;
import java.io.ObjectOutputStream;
import java.util.Date;

public class AccepAdd extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JButton yes,no;
	private JLabel exit,min;
	private Message ms;
	
	public AccepAdd(Message m) {
		this.ms = m;
		
		Panel panel = new Panel();
		panel.setBackground(new Color(140,195,252));
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel(ms.getSender()+" 想加你为好友");
		lblNewLabel.setBounds(30, 53, 200, 33);
		panel.add(lblNewLabel);
		
		yes = new JButton("同意");
		yes.addActionListener(this);
		yes.setBounds(24, 96, 66, 23);
		yes.setOpaque(true);
		yes.setBackground(Color.white);
		panel.add(yes);
		
		no = new JButton("拒绝");
		no.addActionListener(this);
		no.setBounds(110, 96, 66, 23);
		no.setOpaque(true);
		no.setBackground(Color.white);
		panel.add(no);
		
		exit = new JLabel(new ImageIcon("image/exit.png"));
		exit.setBounds(168, 0, 32, 32);
		exit.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				AccepAdd.this.dispose();
			}
			public void mouseEntered(MouseEvent e) {
				exit.setIcon(new ImageIcon("image/fexit.png"));
			}
			public void mouseExited(MouseEvent e) {
				exit.setIcon(new ImageIcon("image/exit.png"));
			}
			public void mousePressed(MouseEvent e) {
				exit.setIcon(new ImageIcon("image/pexit.png"));
			}
		});
		panel.add(exit);
		
		min = new JLabel(new ImageIcon("image/min.png"));
		min.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				setExtendedState(JFrame.ICONIFIED);	
			}
			public void mouseEntered(MouseEvent e) {
				min.setIcon(new ImageIcon("image/fmin.png"));
			}
			public void mouseExited(MouseEvent e) {
				min.setIcon(new ImageIcon("image/min.png"));
			}
			public void mousePressed(MouseEvent e) {
				min.setIcon(new ImageIcon("image/pmin.png"));
			}
		});
		min.setBounds(136, 0, 32, 32);
		panel.add(min);
		
		this.getContentPane().add(panel);
		this.setUndecorated(true);
		this.setSize(200, 150);
		this.setLocationRelativeTo(null);
		
		this.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==yes)
		{
			FriendList list = ManageFriendList.getFriendList(ms.getGetter());
			if(list!=null)
				list.init_update(ms);
			//如果用户点击了同意按钮
			Message m=new Message();
			m.setMesType(MessageType.message_acceptadd);
			m.setSender(ms.getGetter());
			m.setGetter(ms.getSender());
			m.setCon("yes");
			m.setSendTime(new Date().toString());
			//发送给服务器.
			try {
				ObjectOutputStream oos=new ObjectOutputStream(ManageClientConServerThread.getClientConServerThread(ms.getGetter()).getS().getOutputStream());
				oos.writeObject(m);
			} catch (Exception e1) {
				e1.printStackTrace();
			}	
			this.dispose();
		}
		if(e.getSource()==no)
		{
			Message m=new Message();
			m.setMesType(MessageType.message_jujueadd);
			m.setSender(ms.getGetter());
			m.setGetter(ms.getSender());
			m.setCon("yes");
			m.setSendTime(new Date().toString());
			//发送给服务器.
			try {
				ObjectOutputStream oos=new ObjectOutputStream(ManageClientConServerThread.getClientConServerThread(ms.getGetter()).getS().getOutputStream());
				oos.writeObject(m);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			this.dispose();
		}
	}
}
