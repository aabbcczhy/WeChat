package com.view;

import javax.swing.*;

import com.basic.Message;
import com.basic.MessageType;
import com.tools.ManageClientConServerThread;

import java.awt.*;
import java.awt.event.*;
import java.io.ObjectOutputStream;
import java.util.Date;

public class SearchFriend extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private boolean isDragged = false;
	private Point loc = null;
	private JLabel sf_b1,sf_b2;
	private JButton sf_b3;
    private Point tmp = null;
    private String ownerId;
	public SearchFriend(String ownerId) {
		this.setSize(400,200);
		this.ownerId=ownerId;
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0,162,232));
		getContentPane().add(panel);
		panel.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(22, 86, 313, 32);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel sf_label1 = new JLabel("添加好友");
		sf_label1.setBounds(10, 0, 100, 32);
		sf_label1.setForeground(Color.white);
		panel.add(sf_label1);
		
		sf_b1 = new JLabel(new ImageIcon("image/exit.png"));
		sf_b1.setBounds(368, 0, 32, 32);
		sf_b1.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				SearchFriend.this.dispose();
			}
			public void mouseEntered(MouseEvent e) {
				sf_b1.setIcon(new ImageIcon("image/fexit.png"));
			}
			public void mouseExited(MouseEvent e) {
				sf_b1.setIcon(new ImageIcon("image/exit.png"));
			}
			public void mousePressed(MouseEvent e) {
				sf_b1.setIcon(new ImageIcon("image/pexit.png"));
			}
		});
		panel.add(sf_b1);
		
		sf_b2 = new JLabel(new ImageIcon("image/min.png"));
		sf_b2.setBounds(336, 0, 32, 32);
		sf_b2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				setExtendedState(JFrame.ICONIFIED);	
			}
			public void mouseEntered(MouseEvent e) {
				sf_b2.setIcon(new ImageIcon("image/fmin.png"));
			}
			public void mouseExited(MouseEvent e) {
				sf_b2.setIcon(new ImageIcon("image/min.png"));
			}
			public void mousePressed(MouseEvent e) {
				sf_b2.setIcon(new ImageIcon("image/pmin.png"));
			}
		});
		panel.add(sf_b2);
		
		sf_b3 = new JButton(new ImageIcon("image/search/search.png"));
		sf_b3.setBounds(335, 86, 38, 32);
		sf_b3.addActionListener(this);
		panel.add(sf_b3);
		
		this.setVisible(true);
        // 为窗体添加鼠标事件  
        this.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                isDragged = false;
            }
            public void mousePressed(MouseEvent e) {
                tmp = new Point(e.getX(), e.getY());
                isDragged = true;
            }
        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            // 鼠标按键在组件上按下并拖动时调用
            public void mouseDragged(MouseEvent e) {
                if (isDragged) {
                    loc = new Point(getLocation().x + e.getX() - tmp.x,getLocation().y + e.getY() - tmp.y);
                    setLocation(loc);
                }
            }
        });
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==sf_b3)
		{
			//如果用户点击了添加按钮
			Message m=new Message();
			m.setMesType(MessageType.message_ret_addFriend);
			m.setSender(this.ownerId);
			m.setGetter(textField.getText());
			m.setCon("我是...");
			m.setSendTime(new Date().toString());
			JOptionPane.showMessageDialog(this,"好友请求已发送");
			//发送给服务器
			try {
				ObjectOutputStream oos=new ObjectOutputStream(ManageClientConServerThread.getClientConServerThread(ownerId).getS().getOutputStream());
				oos.writeObject(m);
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(this,"服务器异常，发送失败！");
				e1.printStackTrace();
			}
			this.dispose();
		}
	}
}
