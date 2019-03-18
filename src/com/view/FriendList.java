/**
 * 我的好友列表
 */
package com.view;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

import com.basic.Message;
import com.basic.MessageType;
import com.basic.User;
import com.model.ClientUser;
import com.tools.ClientConServerThread;
import com.tools.ManageClientConServerThread;
import com.tools.ManageWeChat;

import java.awt.*;
import java.awt.event.*;
import java.io.ObjectOutputStream;
import java.util.Date;

public class FriendList extends JFrame {
	private static final long serialVersionUID = 1L;
	private boolean isDragged = false;
	private Point loc = null;
	private JPanel cp;
	private JLabel lbl_1=null;
    private Point tmp = null;
    private JTextField textField;
    static String con = null,con1 = null;
    private Message moutline;
    private String flag = "";
    private DefaultMutableTreeNode liebiao[];
    private JTree jtree; 
    DefaultMutableTreeNode root;
    private DefaultTreeModel treeModel=null;
    private String owner;
    private String myname;
    private JLabel btn1,btn2;
    private JButton btn_1,btn_2,btn_3,btn_4;
	//更新在线的好友情况
    private DefaultMutableTreeNode top = new DefaultMutableTreeNode("WeChat群");
    private DefaultMutableTreeNode qun;
	private int count=0;
    public void updateFriend(Message m){
		if(con1 != null)
		{
			if(con1.contains(m.getCon()))
			{
				int i = con1.indexOf(m.getCon());
				String s1 = con1.substring(0, i-1);
				String s2 = con1.substring(i+m.getCon().length());
				con1 = s1+s2;
			}
		}
		con = con +" "+m.getCon();
		m.setCon(con);
		m.setMesType(MessageType.message_get_onLineFriend);
		this.jtree.setCellRenderer(new MyDefaultTreeCellRenderer(this.myname,m));
	}
	public void updateunloadFriend(Message m){
		con1 = con1 +" "+m.getCon();
		m.setCon1(m.getCon());
		m.setCon(con);
		m.setCon1(con1);
		m.setMesType(MessageType.messgae_ret_unloadFriends);
		this.jtree.setCellRenderer(new MyDefaultTreeCellRenderer(this.myname,m));
	}
	public void init_update(Message m){
		//获取选中节点  
        DefaultMutableTreeNode selectedNode  = liebiao[0];  
        //如果节点为空，直接返回  
        if (selectedNode == null) return;  
        //获取该选中节点的父节点  
        DefaultMutableTreeNode parent  = liebiao[0];  
        //如果父节点为空，直接返回  
        if (parent == null) return;  
        //创建一个新节点  
        DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(m.getFriname());  
        //获取选中节点的选中索引  
        int selectedIndex = parent.getIndex(selectedNode);  
        //在选中位置插入新节点  
        treeModel.insertNodeInto(newNode, parent, selectedIndex + 1);  
	}
	public void createTree (DefaultMutableTreeNode root,String s[][]) 
	{
		DefaultMutableTreeNode liebiao = null;
		for(int i = 0;i<s.length;i++)
		{
			if(s[i][1].equals(root.getUserObject().toString()))
			{
				liebiao = new DefaultMutableTreeNode(s[i][0]);
				root.add(liebiao);
			}
		}
	} 
	//构造方法开始
	public FriendList(String OwnId, Message s) {
		this.setAlwaysOnTop(true);
		this.owner = OwnId;
		this.setSize(277,630);
		this.setLocation(Toolkit.getDefaultToolkit().getScreenSize().width*5/6, (Toolkit.getDefaultToolkit().getScreenSize().height-660)/2);
		this.myname = s.getMyname();
		this.addWindowListener(new WindowAdapter() {
			@SuppressWarnings("deprecation")
			public void windowClosing(WindowEvent e) {
				ClientUser clientUser=new ClientUser();
				User u=new User();
				u.setUserId(owner);
				u.setUnload("1234567");
				u.setPasswd("2");
				u.setRegiste("1");
				clientUser.unload(u);	//向服务器发送用户离线报文
				ManageClientConServerThread.getClientConServerThread(FriendList.this.owner).stop();	//得到该用户线程并终止
				ManageClientConServerThread.RemoveClientConServerThread(FriendList.this.owner);		//客户端线程中移除该用户
				System.exit(0);		//安全终止程序
			}
		});
		JPanel panel = new JPanel();
		
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JLabel label = new JLabel(new ImageIcon("image/friendlist/background.jpg"));
		label.setBounds(0, 0, 277, 155);
		panel.add(label);
		
		btn1 = new JLabel(new ImageIcon("image/exit.png"));
		btn1.setBounds(245, 0, 32, 32);
		btn1.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("deprecation")
			public void mouseClicked(MouseEvent e) {
				ClientUser clientUser=new ClientUser();
				User u=new User();
				u.setUserId(owner);
				u.setUnload("1234567");
				u.setPasswd("2");
				u.setRegiste("1");
				clientUser.unload(u);
				ManageClientConServerThread.getClientConServerThread(FriendList.this.owner).stop();
				ManageClientConServerThread.RemoveClientConServerThread(FriendList.this.owner);
				System.exit(0);
			}
			public void mouseEntered(MouseEvent e) {
				btn1.setIcon(new ImageIcon("image/fexit.png"));
			}
			public void mouseExited(MouseEvent e) {
				btn1.setIcon(new ImageIcon("image/exit.png"));
			}
			public void mousePressed(MouseEvent e) {
				btn1.setIcon(new ImageIcon("image/pexit.png"));
			}
		});
		label.add(btn1);
		
		btn2 = new JLabel(new ImageIcon("image/min.png"));
		btn2.setBounds(213, 0, 32, 32);
		btn2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				setExtendedState(JFrame.ICONIFIED);	
			}
			public void mouseEntered(MouseEvent e) {
				btn2.setIcon(new ImageIcon("image/fmin.png"));
			}
			public void mouseExited(MouseEvent e) {
				btn2.setIcon(new ImageIcon("image/min.png"));
			}
			public void mousePressed(MouseEvent e) {
				btn2.setIcon(new ImageIcon("image/pmin.png"));
			}
		});
		label.add(btn2);
		
		textField = new JTextField();
		textField.setBounds(0, 132, 247, 23);
		label.add(textField);
		textField.setColumns(10);
		
		btn_1 = new JButton(new ImageIcon("image/friendlist/search.png"));
		btn_1.setFocusable(false);
		btn_1.setBounds(247, 132, 30, 23);
		label.add(btn_1);
		
		JLabel lblNewLabel_3 = new JLabel(this.myname);
		lblNewLabel_3.setBounds(110, 32, 180, 21);
		label.add(lblNewLabel_3);
	
		lbl_1 = new JLabel(new ImageIcon("image/friendlist/touxiang.jpg"));
		lbl_1.setBounds(10, 40, 78, 78);
		lbl_1.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				if(e.getSource()==lbl_1)
				{
					Message m=new Message();
					m.setMesType(MessageType.message_data);
					m.setSender(owner);
					try {
						ObjectOutputStream oos=new ObjectOutputStream(ManageClientConServerThread.getClientConServerThread(owner).getS().getOutputStream());
						oos.writeObject(m);
					} catch (Exception e1) {
						e1.printStackTrace();
					}			
				}
			}
			public void mouseEntered(MouseEvent e) {
				lbl_1.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		});
		label.add(lbl_1);
		
		JPanel bottom = new JPanel();
		bottom.setBounds(0, 604, 277, 25);
		bottom.setBackground(new Color(203,233,247));
		bottom.setLayout(null);
		panel.add(bottom);
		
		btn_2 = new JButton(new ImageIcon("image/friendlist/message.png"));
		btn_2.setBounds(10, 0, 28, 24);
		btn_2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(count==0) {
					ClientConServerThread.msf.setLocation(FriendList.this.getLocation().x,FriendList.this.getLocation().y+630);
					ClientConServerThread.msf.setVisible(true);
					count=1;
				}else {
					ClientConServerThread.msf.setVisible(false);
					count=0;
				}
			}
		});
		bottom.add(btn_2);
		
		btn_3 = new JButton(new ImageIcon("image/friendlist/file.png"));
		btn_3.setBounds(40, 0, 28, 24);
		bottom.add(btn_3);
		
		btn_4 = new JButton(new ImageIcon("image/friendlist/search1.png"));
		btn_4.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				new SearchFriend(FriendList.this.owner);
			}
		});
		btn_4.setBounds(114, 2, 50, 22);
		bottom.add(btn_4);
		
		cp = new JPanel();
		cp.setLayout(new BorderLayout());
		cp.setBounds(0, 155, 277, 449);
		liebiao = new DefaultMutableTreeNode[Integer.parseInt(s.getFenzucount())];
		String sa = s.getCon();
		String friend[]=sa.split(" ");
		String friend1[][] = new String[friend.length][];
		for(int i =0; i < friend.length; i++)
		{
			friend1[i] = friend[i].split("-");
		}
		int p = 0;
		for(int i = 0;i<friend1.length;i++)
		{
			int j;
			for( j = 0; j<i; j++)
			{
				if(friend1[j][1].equals(friend1[i][1]))
					break;
			}
			if(j == i)
				liebiao[p++] = new DefaultMutableTreeNode(friend1[i][1]);
		}   
		root=new DefaultMutableTreeNode(""); 
		 
		for(int i = 0;i<liebiao.length;i++)
        {
			root.add(liebiao[i]);
			createTree(liebiao[i], friend1);
		}
		qun = new DefaultMutableTreeNode("软件工程(100000)");
		top.add(qun);
		root.add(top);
		
		jtree = new JTree(root);
		jtree.setEditable(true);
		treeModel=(DefaultTreeModel)jtree.getModel();
		JScrollPane scrollPane=new JScrollPane(); 
		scrollPane.setViewportView(jtree);
		
        jtree.setCellRenderer(new MyDefaultTreeCellRenderer(this.myname,s));  
        jtree.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e) {
        		if(e.getClickCount()==2&&!((DefaultMutableTreeNode)(((JTree)e.getSource()).getLastSelectedPathComponent())).toString().equals("软件工程(100000)"))
        		{		
        			JTree tree = (JTree)e.getSource();  
        			//获取目前选取的节点  
        			DefaultMutableTreeNode selectionNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent(); 
        			if(selectionNode.isLeaf()) {
        				//得到该好友的编号
        				WeChat Chat;
        				String friendNo = selectionNode.toString(); 
        				int x = friendNo.indexOf("(");
        		        friendNo = friendNo.substring(x+1, friendNo.length()-1);
        				Chat = ManageWeChat.getWeChat(FriendList.this.owner+" "+friendNo);
        				if(Chat == null) {
        					WeChat weChat=new WeChat(FriendList.this.owner,FriendList.this.myname,friendNo);
        					//把聊天界面加入到管理类
        					ManageWeChat.addWeChat(FriendList.this.owner+" "+friendNo, weChat);
        				}
        				if(flag.indexOf(friendNo)==-1) {
        					Message m=new Message();
        					m.setMesType(MessageType.message_ret_outlineMessage);
        					m.setSender(FriendList.this.owner);
        					m.setGetter(friendNo);
        					m.setSendTime(new Date().toString());
        					//发送给服务器
        					try {
        						ObjectOutputStream oos=new ObjectOutputStream(ManageClientConServerThread.getClientConServerThread(owner).getS().getOutputStream());
        						oos.writeObject(m);
        					} catch (Exception e1) {
        						e1.printStackTrace();
        					}
        					flag+=friendNo;
        				}
        			}
        		}
        		if(e.getClickCount()==2&&((DefaultMutableTreeNode)(((JTree)e.getSource()).getLastSelectedPathComponent())).toString().equals("软件工程(100000)"))
        			{
        				JTree tree = (JTree)e.getSource();  
        				//获取目前选取的节点  
        				DefaultMutableTreeNode selectionNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent(); 
        				if(selectionNode.isLeaf()) {
        					QunChat qunChat;
        					String QunId = selectionNode.toString(); 
        					int x = QunId.indexOf("(");
        					QunId = QunId.substring(x+1, QunId.length()-1);
        					qunChat = ManageWeChat.getQunChat(FriendList.this.owner+"  "+QunId);
        					if(qunChat == null) {
        						QunChat qunchat=new QunChat(FriendList.this.owner,FriendList.this.myname,QunId);
        						//把聊天界面加入到管理类
        						ManageWeChat.addQunChat(FriendList.this.owner+"  "+QunId, qunchat);
        						Message m=new Message();
        						m.setMesType(MessageType.message_getQunPeople);
        						m.setSender(FriendList.this.owner);
        						m.setGetter(QunId);
        						m.setSendTime(new Date().toString());
        						//发送给服务器.
        						try {
        							ObjectOutputStream oos=new ObjectOutputStream(ManageClientConServerThread.getClientConServerThread(owner).getS().getOutputStream());
        							oos.writeObject(m);
        						} catch (Exception e1) {
        							e1.printStackTrace();
        						}	
        					}
        					else
        					{
        						qunChat.setVisible(true);
        					}
        				 }
        			}
        	}
        	public void mouseEntered(MouseEvent arg0) {
        		JTree tree = (JTree)arg0.getSource();  
        		//创建节点绘制对象  
                DefaultTreeCellRenderer cellRenderer = (DefaultTreeCellRenderer)tree.getCellRenderer();  
                //设置字体  
                cellRenderer.setForeground(Color.red);
                cellRenderer.setBackgroundNonSelectionColor(Color.white);  
                cellRenderer.setBackgroundSelectionColor(Color.yellow);  
                cellRenderer.setBorderSelectionColor(Color.red); 
        	}
        	public void mouseExited(MouseEvent arg0) {
        		JTree tree = (JTree)arg0.getSource();  
        		//创建节点绘制对象  
                DefaultTreeCellRenderer cellRenderer = (DefaultTreeCellRenderer)tree.getCellRenderer();  
        		 cellRenderer.setForeground(Color.black);
        	}
        });
        cp.add(scrollPane,BorderLayout.CENTER);
		panel.add(cp);
		this.setUndecorated(true);
		this.setVisible(true);
        // 为窗体添加鼠标事件
        this.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
            	ClientConServerThread.msf.setLocation(FriendList.this.getLocation().x,FriendList.this.getLocation().y+630);
                isDragged = false;
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }  
	        public void mousePressed(MouseEvent e) {
	        	if(e.getY()<32) {
	                tmp = new Point(e.getX(), e.getY());
	                isDragged = true; 
	        	}
	        }
        }); 
        this.addMouseMotionListener(new MouseMotionAdapter() {  
            // 鼠标按键在组件上按下并拖动时调用。  
            public void mouseDragged(MouseEvent e) {
                if (isDragged){
                    loc = new Point(getLocation().x + e.getX() - tmp.x,getLocation().y + e.getY() - tmp.y);
                    setLocation(loc);  
                }
            }
        });
        //查询是否有好友请求
        moutline=new Message();
        moutline.setMesType(MessageType.message_hasfriendask);
        moutline.setSender(this.owner);
        moutline.setSendTime(new Date().toString());
        //发送给服务器.
        try {
        	ObjectOutputStream oos=new ObjectOutputStream(ManageClientConServerThread.getClientConServerThread(owner).getS().getOutputStream());
        	oos.writeObject(moutline);
        } catch (Exception e) {
        	e.printStackTrace();
        }
        moutline=new Message();
        moutline.setMesType(MessageType.message_ret_pointMessage);
        moutline.setSender(this.owner);
        moutline.setSendTime(new Date().toString());
        //发送给服务器.
        try {
        	ObjectOutputStream oos=new ObjectOutputStream(ManageClientConServerThread.getClientConServerThread(owner).getS().getOutputStream());
        	oos.writeObject(moutline);
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}
}