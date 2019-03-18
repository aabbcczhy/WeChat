package com.tools;
/**
 * 查看好友个人资料
 */
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class FriendPersonalInfo extends JFrame{
	private static final long serialVersionUID = 1L;
	private boolean isDragged = false;
	private Point loc = null;  
    private Point tmp = null;
	//顶部控件
    private JLabel p_qianming,p_name,p_image,p_close,p_min;
	private JPanel panel_1,panel_2;
	//中部控件
	private JLabel p_nicheng,p_wechatnumber,p_personal,p_guxiang,p_location,p_phone,p_mphone,p_zhuye,p_email,p_qyear,p_rname,p_xueli,p_school;
	private JLabel label,lblWeChat,label_1,label_2,label_3,label_4,label_5,label_6,label_7,lbl,label_8,label_9,label_10;
	private JPanel panel;
	private String[] strs;
	
	public FriendPersonalInfo(String own){
		this.setSize(458,679);
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(0, 0, 458, 252);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		p_name = new JLabel("1");
		p_name.setBounds(102, 93, 107, 30);
		panel.add(p_name);
		
		p_image = new JLabel(new ImageIcon("image/friendlist/touxiang.jpg"));
		p_image.setBounds(10,92, 78, 78);
		panel.add(p_image);
		
		p_qianming = new JLabel("签名");
		p_qianming.setBounds(100, 156, 226, 15);
		panel.add(p_qianming);
		
		p_close = new JLabel(new ImageIcon("image/exit.png"));
		p_close.setBounds(426, 0, 32, 32);
		p_close.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				FriendPersonalInfo.this.dispose();
			}
			public void mouseEntered(MouseEvent e) {
				p_close.setIcon(new ImageIcon("image/fexit.png"));
			}
			public void mouseExited(MouseEvent e) {
				p_close.setIcon(new ImageIcon("image/exit.png"));
			}
			public void mousePressed(MouseEvent e) {
				p_close.setIcon(new ImageIcon("image/pexit.png"));
			}
		});
		panel.add(p_close);
		
		p_min = new JLabel(new ImageIcon("image/min.png"));
		p_min.setBounds(394, 0, 32, 32);
		p_min.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				setExtendedState(JFrame.ICONIFIED);	
			}
			public void mouseEntered(MouseEvent e) {
				p_min.setIcon(new ImageIcon("image/fmin.png"));
			}
			public void mouseExited(MouseEvent e) {
				p_min.setIcon(new ImageIcon("image/min.png"));
			}
			public void mousePressed(MouseEvent e) {
				p_min.setIcon(new ImageIcon("image/pmin.png"));
			}
		});
		panel.add(p_min);
		
		JLabel background = new JLabel(new ImageIcon("image/Info/beijing.png"));
		background.setBounds(0, 0, 458, 214);
		panel.add(background);
		JLabel background1 = new JLabel(new ImageIcon("image/Info/white.png"));
		background1.setBounds(0, 214, 458,38);
		panel.add(background1);
		
		panel_1 = new JPanel();
		panel_1.setBounds(0, 603, 458, 76);
		getContentPane().add(panel_1);
		
		panel_2 = new JPanel();
		panel_2.setBounds(0, 253, 458, 349);
		getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		p_nicheng = new JLabel("1");
		p_nicheng.setBounds(110, 10, 227, 20);
		panel_2.add(p_nicheng);
		p_wechatnumber = new JLabel("1");
		p_wechatnumber.setBounds(110, 35, 227, 20);
		panel_2.add(p_wechatnumber);
		p_personal = new JLabel("1");
		p_personal.setBounds(110, 60, 227, 20);
		panel_2.add(p_personal);
		p_guxiang = new JLabel("1");
		p_guxiang.setBounds(110, 85, 227, 20);
		panel_2.add(p_guxiang);
		p_location = new JLabel("1");
		p_location.setBounds(110, 110, 227, 20);
		panel_2.add(p_location);
		p_phone = new JLabel("1");
		p_phone.setBounds(110, 135, 227, 20);
		panel_2.add(p_phone);
		panel_2.add(p_phone);
		p_mphone = new JLabel("1");
		p_mphone.setBounds(110, 160, 227, 20);
		panel_2.add(p_mphone);
		p_zhuye = new JLabel("1");
		p_zhuye.setBounds(110, 185, 227, 20);
		panel_2.add(p_zhuye);
		p_email = new JLabel("1");
		p_email.setBounds(110, 210, 227, 20);
		panel_2.add(p_email);
		p_qyear = new JLabel("1");
		p_qyear.setBounds(110, 235, 227, 20);
		panel_2.add(p_qyear);
		p_rname = new JLabel("1");
		p_rname.setBounds(110, 260, 227, 20);
		panel_2.add(p_rname);
		p_xueli = new JLabel("1");
		p_xueli.setBounds(110, 285, 227, 20);
		panel_2.add(p_xueli);
		p_school = new JLabel("1");
		p_school.setBounds(110, 310, 227, 20);
		panel_2.add(p_school);
		
		label = new JLabel("\u6635\u79F0");
		label.setBounds(28, 13, 54, 15);
		panel_2.add(label);
		
		lblWeChat = new JLabel("WeChat");
		lblWeChat.setBounds(28, 38, 54, 15);
		panel_2.add(lblWeChat);
		
		label_1 = new JLabel("\u4E2A\u4EBA");
		label_1.setBounds(28, 63, 54, 15);
		panel_2.add(label_1);
		
		label_2 = new JLabel("\u6545\u4E61");
		label_2.setBounds(28, 85, 54, 15);
		panel_2.add(label_2);
		
		label_3 = new JLabel("\u6240\u5728\u5730");
		label_3.setBounds(28, 113, 54, 15);
		panel_2.add(label_3);
		
		label_4 = new JLabel("\u7535\u8BDD");
		label_4.setBounds(28, 138, 54, 15);
		panel_2.add(label_4);
		
		label_5 = new JLabel("\u624B\u673A");
		label_5.setBounds(28, 163, 54, 15);
		panel_2.add(label_5);
		
		label_6 = new JLabel("\u4E3B\u9875");
		label_6.setBounds(28, 188, 54, 15);
		panel_2.add(label_6);
		
		label_7 = new JLabel("\u90AE\u7BB1");
		label_7.setBounds(28, 213, 54, 15);
		panel_2.add(label_7);
		
		lbl = new JLabel("Chat\u9F84");
		lbl.setBounds(28, 238, 54, 15);
		panel_2.add(lbl);
		
		label_8 = new JLabel("\u59D3\u540D");
		label_8.setBounds(28, 263, 54, 15);
		panel_2.add(label_8);
		
		label_9 = new JLabel("\u5B66\u5386");
		label_9.setBounds(28, 288, 54, 15);
		panel_2.add(label_9);
		
		label_10 = new JLabel("\u5B66\u6821");
		label_10.setBounds(28, 313, 54, 15);
		panel_2.add(label_10);
		JLabel background2 = new JLabel(new ImageIcon("image/Info/beijing.png"));
		background2.setBounds(0, 603, 458, 76);
		panel_1.add(background2);
		
		this.setVisible(true); 
        // 为窗体添加鼠标事件  
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                isDragged = false;
                // 为指定的光标设置光标图像  
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            public void mousePressed(MouseEvent e) {
            	if(e.getY()<216)
            	{
	                tmp = new Point(e.getX(), e.getY());
	                isDragged = true;  
            	}  
            }
        });
        this.addMouseMotionListener(new MouseMotionAdapter() {
            // 鼠标按键在组件上按下并拖动时调用
            public void mouseDragged(MouseEvent e) {
                if (isDragged) {
                    loc = new Point(getLocation().x + e.getX() - tmp.x , getLocation().y + e.getY() - tmp.y);  
                    setLocation(loc);
                }
            }
        });
	}
	public void setTexts(String str)
	{
		strs=str.split(";");
		p_name.setText(strs[0]+"("+strs[1]+")");
		p_nicheng.setText(strs[1]);
		p_wechatnumber.setText(strs[0]);
		p_personal.setText(strs[0]+"  "+strs[4]+" "+strs[5]);
		p_guxiang.setText(strs[14]);
		p_location.setText(strs[15]);
		p_phone.setText(strs[11]);
		p_mphone.setText(strs[11]);
		p_zhuye.setText(strs[13]);
		p_email.setText(strs[12]);
		p_qyear.setText("1");
		p_rname.setText(strs[1]);
		p_xueli.setText(strs[9]);
		p_school.setText(strs[10]);
	}
}

