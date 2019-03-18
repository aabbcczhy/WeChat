package com.view;
/**
 * 登录界面
 */
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.basic.*;
import com.model.ClientUser;
import com.tools.ManageClientConServerThread;
import com.tools.ManageFriendList;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.ImageIcon;

public class Login extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel exit,min;
	private JTextField jtx1;
	private JPasswordField jtx2;
	private boolean isDragged=true;
	private Point tmp,loc;

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			new Login();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	public Login() {
		this.setResizable(false);
		this.setSize(406, 280);
		this.setUndecorated(true);
		this.getContentPane().setLayout(null);
		this.setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBounds(0, -5, 406, 290);
		
		JLabel pic = new JLabel();
		ImageIcon icon = new ImageIcon("image/1.jpg");
		pic.setIcon(icon);
		contentPane.add(pic);
		
		JPanel panel = new JPanel() {
			private static final long serialVersionUID = 1L;
			private float transparency = .5f;
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D graphics2d = (Graphics2D) g.create();
		        graphics2d.setComposite(AlphaComposite.SrcOver.derive(transparency));
		        graphics2d.setColor(Color.black);
		        graphics2d.fillRect(0, 0, 330, 210);
		        graphics2d.dispose();
			}
		};
		panel.setBounds(38, 45, 330, 210);
		panel.setOpaque(false);
		panel.setLayout(null);
		pic.add(panel);
		
		JLabel biaoti = new JLabel("We Chat");
		biaoti.setBounds(10,0,100,32);
		biaoti.setFont(new Font("微软雅黑",0,16));
		pic.add(biaoti);
		
		exit = new JLabel(new ImageIcon("image/exit.png"));
		exit.setBounds(374, 0, 32, 32);
		exit.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
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
		pic.add(exit);
		
		min = new JLabel(new ImageIcon("image/min.png"));
		min.setBounds(342, 0, 32, 32);
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
		pic.add(min);
		
		JButton btn = new JButton("登 录");
		//btn.setIcon(new ImageIcon("image/loginbutton.png"));
		btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {	//点击登录按钮以后
				ClientUser wechatClientUser=new ClientUser();
				User u=new User();
				u.setUserId(jtx1.getText().trim());
				u.setPasswd(new String(jtx2.getPassword()));
				u.setUnload("2");
				u.setRegiste("1");
				InetAddress address = null;
				try {
					address=InetAddress.getLocalHost();
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				}
				u.setAddress(address);
				Message ms = wechatClientUser.checkUser(u);
				if(ms.getMesType().equals("1"))
				{
					try {
						//把创建好友列表的语句提前.
						ms.setMesType(MessageType.message_ret_onLineFriend);
						FriendList list=new FriendList(u.getUserId(),ms);
						ManageFriendList.addFriendList(u.getUserId(), list);
						
						//发送一个要求返回在线好友的请求包.
						ObjectOutputStream oos=new ObjectOutputStream(ManageClientConServerThread.getClientConServerThread(u.getUserId()).getS().getOutputStream());
						
						Message m=new Message();
						m.setMesType(MessageType.message_get_onLineFriend);
						//指明所需要的是这个账号的好友情况.
						m.setSender(u.getUserId());
						oos.writeObject(m);
					} catch (Exception e) {
						e.printStackTrace();
					}
					//关闭掉登录界面
					Login.this.dispose();
				}else if(ms.getMesType().equals("3"))
				{
					JOptionPane.showMessageDialog(Login.this,"该用户已登录");
				}
				else{
					JOptionPane.showMessageDialog(Login.this,"用户名密码错误");
				}
			}
		});
		btn.setToolTipText("登录");
		btn.setBounds(65, 165, 200, 32);
		btn.setFocusPainted(false);
		btn.setOpaque(false);
		panel.add(btn);
		
		jtx1 = new JTextField();
		jtx1.setBounds(100, 20, 200, 31);
		panel.add(jtx1);
		jtx1.setColumns(10);

		JLabel lb1 = new JLabel("No WeChat？Register now");
		lb1.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lb1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lb1.setText("<html><u>No WeChat？Register now</u></html>");
				lb1.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lb1.setText("No WeChat？Register now");
			}
			@Override
			public void mouseClicked(MouseEvent arg0) {
				new Register();
				Login.this.dispose();
			}
		});
		lb1.setForeground(Color.white);
		lb1.setBounds(20, 130, 200, 20);
		panel.add(lb1);
		
		jtx2 = new JPasswordField();
		jtx2.setBounds(100, 80, 200, 31);
		jtx2.setEchoChar('*');
		panel.add(jtx2);

		JLabel user = new JLabel("WeChat");
		user.setFont(new Font("微软雅黑", 0, 18));
		user.setForeground(Color.white);
		user.setBounds(20, 20, 80, 31);
		panel.add(user);
		
		JLabel pwd = new JLabel("Password");
		pwd.setFont(new Font("微软雅黑", 0, 18));
		pwd.setForeground(Color.white);
		pwd.setBounds(13, 80, 100, 31);
		panel.add(pwd);
		
		this.add(contentPane);
		this.setVisible(true);
		
		this.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				isDragged = false;
			}  
			public void mousePressed(MouseEvent e) {  
				if(e.getY()<280){
					tmp = new Point(e.getX(), e.getY());  
					isDragged = true;
				}  
			}
		});  
		this.addMouseMotionListener(new MouseMotionAdapter() {
        // 鼠标按键在组件上按下并拖动时调用。  
			public void mouseDragged(MouseEvent e) {
				if (isDragged) {  
					loc = new Point(getLocation().x + e.getX() - tmp.x, getLocation().y + e.getY() - tmp.y); 
					setLocation(loc);  
				}  
			}
		});
	}
}