/**
 * 这是群聊天的界面
 * 因为客户端，要处于读取的状态，因此我们把它做成一个线程
 */
package com.view;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.basic.Message;
import com.basic.MessageType;
import com.tools.ManageClientConServerThread;
import com.tools.ManageWeChat;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.*;
import java.io.*;
import java.text.DateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.awt.*;

public class QunChat extends JFrame implements ActionListener,MouseListener {
	private static final long serialVersionUID = 1L;
	private JButton cd_send,cd_close,cd_sendpic,cd_jietu,cd_yymessage,cd_diange,cd_dgninput,cd_zhendong;
	private JButton cd_word,cd_magic,cd_biaoqing;
	private JLabel cd_sharedisplay,cd_yuyin,cd_shipin,cd_transfile,cd_createteam,cd_yuancheng,cd_touxiang,cd_exit,cd_min;
	private JButton cd_searchjilu,cd_jiacu,cd_xiahuaxian,cd_color,cd_xieti;
	private JComboBox<?> cd_ziti,cd_size;
	private boolean isDragged = false;
	private Point loc = null;
	private JPanel panel_1,panel_2,panel_3,panel_4;
    private Point tmp = null;
    private CardLayout Cardjilu;
    private JTextPane textPane_1,textPane;
    private JTextPane textPane_2 ;
    private String ownerId;
    private String myname;
    private String QunId;
	private QunPicsJWindow picWindow;
	private List<PicInfo> myPicInfo = new LinkedList<PicInfo>();
	private List<PicInfo> receivdPicInfo = new LinkedList<PicInfo>();
	private List<PicInfo> receivdoldPicInfo = new LinkedList<PicInfo>();
	private StyledDocument docChat = null;
	private StyledDocument docMsg = null;
	private StyledDocument oldMsg = null;
	private DataInputStream fis = null;
	private JTextPane k=new JTextPane();
	private JPanel Qunpane=new JPanel();
	int x,y,higth=0,record=0;
	private JLabel[] jla=new JLabel[20];
	private Color Text_color=new Color(255,255,255);
	private JScrollPane scrollPane_1,scrollPane_2;
	class PicInfo {
		/* 图片信息*/
		int pos;
		String val;
		public PicInfo(int pos,String val){
			this.pos = pos;
			this.val = val;
		}
		public int getPos() {
			return pos;
		}
		public void setPos(int pos) {
			this.pos = pos;
		}
		public String getVal() {
			return val;
		}
		public void setVal(String val) {
			this.val = val;
		}
	}
	/**
	 * 插入图片
	 */
	public void insertSendPic(ImageIcon imgIc) {
		textPane_1.insertIcon(imgIc); // 插入图片
		d=true;
	}
	public JButton getPicBtn(){
		return cd_biaoqing;
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public QunChat(String ownerId,String myname,String QunId) {
		Text_color = Color.BLACK;
		this.ownerId=ownerId;
		this.myname = myname;
		this.QunId=QunId;
		this.setSize(728,533);
		this.setLocationRelativeTo(null);
		this.setUndecorated(true);
		this.getContentPane().setBackground(Color.white);
		getContentPane().setLayout(null);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				ManageWeChat.RemoveQunChat(QunChat.this.ownerId+" "+QunChat.this.QunId);
			}
		});
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 729, 92);
		panel.setBackground(new Color(140,195,252));
		getContentPane().add(panel);
		panel.setLayout(null);
		
		cd_touxiang = new JLabel(new ImageIcon("image/dialogimage/group-pic.png"));
		cd_touxiang.setBounds(10, 10, 42, 42);
		panel.add(cd_touxiang);
		
		cd_exit = new JLabel(new ImageIcon ("image/exit.png"));
		cd_exit.setAlignmentX(Component.CENTER_ALIGNMENT);
		cd_exit.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				ManageWeChat.RemoveQunChat(QunChat.this.ownerId+"  "+QunChat.this.QunId);
				QunChat.this.dispose();	
			}
			public void mouseEntered(MouseEvent e) {
				cd_exit.setIcon(new ImageIcon("image/fexit.png"));
			}
			public void mouseExited(MouseEvent e) {
				cd_exit.setIcon(new ImageIcon("image/exit.png"));
			}
			public void mousePressed(MouseEvent e) {
				cd_exit.setIcon(new ImageIcon("image/pexit.png"));
			}
		});
		cd_exit.setBounds(696, 0, 32, 32);
		panel.add(cd_exit);
		
		JLabel qunname = new JLabel("软件工程(100000)");
		qunname.setBounds(62, 10, 150, 20);
		qunname.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		panel.add(qunname);
		
		cd_min = new JLabel(new ImageIcon ("image/min.png"));
		cd_min.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				setExtendedState(JFrame.ICONIFIED);	
			}
			public void mouseEntered(MouseEvent e) {
				cd_min.setIcon(new ImageIcon("image/fmin.png"));
			}
			public void mouseExited(MouseEvent e) {
				cd_min.setIcon(new ImageIcon("image/min.png"));
			}
			public void mousePressed(MouseEvent e) {
				cd_min.setIcon(new ImageIcon("image/pmin.png"));
			}
		});
		cd_min.setBounds(664, 0, 32, 32);
		panel.add(cd_min);
		
		cd_yuyin = new JLabel(new ImageIcon("image/dialogimage/yuyin.png"));
		cd_yuyin.setBounds(10, 60, 36, 30);
		panel.add(cd_yuyin);
		
		cd_shipin = new JLabel(new ImageIcon("image/dialogimage/shipin.png"));
		cd_shipin.setBounds(56, 58, 36, 30);
		panel.add(cd_shipin);
		
		cd_transfile = new JLabel(new ImageIcon("image/dialogimage/tranfile.png"));
		cd_transfile.setBounds(102, 60, 36, 30);
		cd_transfile.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				JFileChooser fileDialog=new JFileChooser();
				fileDialog.showOpenDialog(QunChat.this);
				File file=fileDialog.getSelectedFile();
				Message m=new Message();
				m.setMesType(MessageType.message_Qunfile);
				m.setSender(QunChat.this.ownerId);
				m.setGetter(QunChat.this.QunId);
				m.setFilename(file.getName());
				String info=null;
				try {
					fis=new DataInputStream(new FileInputStream(file));
					int size=fis.available();
					byte[] by=new byte[size];
					fis.read(by);
					fis.close();
					m.setFilebyte(by, size);
				    m.setSendTime(new Date().toString());
				    ObjectOutputStream oos=new ObjectOutputStream
					(ManageClientConServerThread.getClientConServerThread(ownerId).getS().getOutputStream());
					oos.writeObject(m);
					info="您已成功上传文件\""+file.getName()+"\""+"\r\n";
				    } catch (Exception e1) {
				    	info="服务器连接超时，文件上传失败\r\n";		    
				    }
					if(!info.equals(null)&&!info.equals(""))
						QunChat.this.showfileinfoMsg(m, info);
			}
		});
		panel.add(cd_transfile);
		
		cd_createteam = new JLabel(new ImageIcon("image/dialogimage/createteam.png"));
		cd_createteam.setBounds(148, 60, 36, 30);
		panel.add(cd_createteam);
		
		cd_yuancheng = new JLabel(new ImageIcon("image/dialogimage/yuancheng.png"));
		cd_yuancheng.setBounds(194, 60, 36, 30);
		panel.add(cd_yuancheng);
		
		cd_sharedisplay = new JLabel(new ImageIcon("image/dialogimage/sharedisplay.png"));
		cd_sharedisplay.setBounds(240, 60, 36, 30);
		panel.add(cd_sharedisplay);
		
		JLabel cd_yingyong = new JLabel(new ImageIcon("image/dialogimage/yingyong.png"));
		cd_yingyong.setBounds(286, 60, 36, 30);
		panel.add(cd_yingyong);
		
		panel_1 = new JPanel();
		panel_1.setBounds(0, 92, 446, 284);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		textPane = new JTextPane();
		textPane.setBounds(0, 92, 446, 284);
		
		scrollPane_1 = new JScrollPane(textPane);
		scrollPane_1.setBounds(-1, -1, 448, 286);
		scrollPane_1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panel_1.add(scrollPane_1);
		
		panel_2 = new JPanel();
		panel_2.setBounds(0, 401, 446, 132);
		getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		textPane_1 = new JTextPane();
		textPane_1.setBounds(0, 35, 446, 60);
		panel_2.add(textPane_1);
		
		docMsg = textPane_1.getStyledDocument();
		docChat = textPane.getStyledDocument();
		
		cd_searchjilu = new JButton(new ImageIcon("image/dialogimage/jilu.png"));
		Cardjilu = new CardLayout(2, 2);
		cd_searchjilu.addActionListener(this);
		cd_searchjilu.setBounds(353, 7, 92, 23);
		panel_2.add(cd_searchjilu);
		
		picWindow = new QunPicsJWindow(this);
		
		panel_4 = new JPanel();
		panel_4.setVisible(false);
		panel_4.setBounds(0, 376, 446, 25);
		panel_4.setBackground(Color.white);
		getContentPane().add(panel_4);
		panel_4.setLayout(null);
		
		cd_size = new JComboBox();
		cd_size.setModel(new DefaultComboBoxModel(new String[] {"16", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"}));
		cd_size.setBounds(276, 0, 42, 21);
		cd_size.addActionListener(this);
		panel_4.add(cd_size);
		
		cd_jiacu = new JButton(new ImageIcon("image/dialogimage/jiacu.jpg"));
		cd_jiacu.setBounds(328, 1, 22, 20);
		panel_4.add(cd_jiacu);
		
		cd_xieti = new JButton(new ImageIcon("image/dialogimage/xieti.jpg"));
		cd_xieti.setBounds(360, 1, 22, 20);
		panel_4.add(cd_xieti);
		
		cd_xiahuaxian = new JButton(new ImageIcon("image/dialogimage/xiahuaxian.jpg"));
		cd_xiahuaxian.setBounds(393, 1, 22, 20);
		panel_4.add(cd_xiahuaxian);
		
		cd_color = new JButton(new ImageIcon("image/dialogimage/color.jpg"));
		cd_color.setBounds(424, 1, 20, 20);
		cd_color.addActionListener(this );
		panel_4.add(cd_color);
		
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String script[] = ge.getAvailableFontFamilyNames();
		cd_ziti = new JComboBox();
		cd_ziti.setModel(new DefaultComboBoxModel(script));
		cd_ziti.addActionListener(this);
		cd_ziti.setBounds(178, 0, 88, 21);
		panel_4.add(cd_ziti);
		 
		cd_word = new JButton(new ImageIcon("image/dialogimage/word.png"));
		cd_word.addActionListener(this);
				
		cd_word.setBounds(10, 3, 30, 30);
		panel_2.add(cd_word);
		
		cd_biaoqing = new JButton(new ImageIcon("image/dialogimage/biaoqing.png"));
		cd_biaoqing.setBounds(39, 3, 30, 30);
		cd_biaoqing.addMouseListener(this);
		panel_2.add(cd_biaoqing);
		
		cd_magic = new JButton(new ImageIcon("image/dialogimage/magic.jpg"));
		cd_magic.setBounds(68, 3, 30, 30);
		panel_2.add(cd_magic);
		
		cd_zhendong = new JButton(new ImageIcon("image/dialogimage/zhendong.jpg"));
		cd_zhendong.setBounds(97, 3, 30, 30);
		cd_zhendong.addActionListener(this);
		panel_2.add(cd_zhendong);
		
		cd_yymessage = new JButton(new ImageIcon("image/dialogimage/yymessage.jpg"));
		cd_yymessage.setBounds(126, 3, 30, 30);
		panel_2.add(cd_yymessage);
		
		cd_dgninput = new JButton(new ImageIcon("image/dialogimage/dgninput.jpg"));
		cd_dgninput.setBounds(155, 3,30, 30);
		panel_2.add(cd_dgninput);
		
		cd_sendpic = new JButton(new ImageIcon("image/dialogimage/sendimage.jpg"));
		cd_sendpic.setBounds(184, 3, 30, 30);
		panel_2.add(cd_sendpic);
		
		cd_diange = new JButton(new ImageIcon("image/dialogimage/diange.jpg"));
		cd_diange.setBounds(214, 3, 30, 30);
		panel_2.add(cd_diange);
		
		cd_jietu = new JButton(new ImageIcon("image/dialogimage/jietu.jpg"));
		cd_jietu.setBounds(244, 3, 30, 30);
		cd_jietu.addActionListener(this);
		panel_2.add(cd_jietu);
		
		cd_close = new JButton(new ImageIcon("image/dialogimage/close.jpg"));
		cd_close.setBounds(305, 102, 64, 24);
		cd_close.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				ManageWeChat.RemoveQunChat(QunChat.this.ownerId+"  "+QunChat.this.QunId);
				QunChat.this.dispose();	
			}
		});
		panel_2.add(cd_close);
		
		cd_send = new JButton(new ImageIcon("image/dialogimage/send.jpg"));
		cd_send.addActionListener(this);
		cd_send.setBounds(379, 102, 64, 24);
		panel_2.add(cd_send);
		
		panel_3 = new JPanel();
		panel_3.setBounds(446, 91, 283, 442);
		getContentPane().add(panel_3);
		panel_3.setLayout(Cardjilu);
		
		textPane_2 = new JTextPane();
		textPane_2.setBounds(0, 0, 283, 442);
		
		scrollPane_2 = new JScrollPane(textPane_2);
		scrollPane_2.setBounds(0, 0, 283, 442);
		scrollPane_2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane_2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panel_3.add(scrollPane_2, "name_3333");
		panel_3.add(Qunpane,"1");
		panel_3.add(scrollPane_2,"2");
		
		oldMsg = textPane_2.getStyledDocument();
		
		this.addComponentListener(new ComponentAdapter(){
			@Override
			public void componentResized(ComponentEvent e) {
				QunChat.this.picWindow.dispose();
			}
			@Override
			public void componentMoved(ComponentEvent e) {
				QunChat.this.picWindow.dispose();
			}
			@Override
			public void componentHidden(ComponentEvent e) {
				QunChat.this.picWindow.dispose();
			}
		});
		cd_searchjilu.addActionListener(this);
		
		this.setVisible(true);
        // 为窗体添加鼠标事件  
        this.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                isDragged = false;
                // 为指定的光标设置光标图像 
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            public void mousePressed(MouseEvent e) {
            	if(e.getY()<92)
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
                    loc = new Point(getLocation().x + e.getX() - tmp.x,getLocation().y + e.getY() - tmp.y);
                    setLocation(loc);
                }
            }
        });
        drag();
	}
	public Color recolor(String s) {
		String friend[]=s.split(",");
		String friend1[]= new String[friend.length];
		int j;
		for(int i =0; i < friend.length; i++) {
			j = friend[i].lastIndexOf("=");
			friend1[i] = friend[i].substring(j+1);
		}
		friend1[2] = friend1[2].substring(0, friend1[2].length()-1);
		return new Color(Integer.parseInt(friend1[0]),Integer.parseInt(friend1[1]),Integer.parseInt(friend1[2]));
	}
	/*
	 * 重组收到的表情信息串
	 */
	public void receivedPicInfo(String picInfos) {
		String[] infos = picInfos.split("[+]");
		for(int i = 0 ; i < infos.length ; i++){
			String[] tem = infos[i].split("[&]");
			if(tem.length==2){
				PicInfo pic = new PicInfo(Integer.parseInt(tem[0]),tem[1]);
				receivdPicInfo.add(pic);
			}
		}
	}
	public void receivedoldPicInfo(String picInfos){
		String[] infos = picInfos.split("[+]");
		for(int i = 0 ; i < infos.length ; i++){
			String[] tem = infos[i].split("[&]");
			if(tem.length==2){
				PicInfo pic = new PicInfo(Integer.parseInt(tem[0]),tem[1]);
				receivdoldPicInfo.add(pic);
			}
		}
	}
	//写一个方法，用于显示系统提醒的消息
	public void showfileinfoMsg(Message m,String info) {
		SimpleAttributeSet attrset = new SimpleAttributeSet();
        StyleConstants.setFontSize(attrset,14);
        if(m.getCol()!=null) {
        	StyleConstants.setForeground(attrset, Color.red);
        }else {
        	StyleConstants.setForeground(attrset, Color.gray);
        }
       
        StyleConstants.setFontFamily(attrset, "宋体");
        Document docs = textPane.getDocument();
        try {
            docs.insertString(docs.getLength(), info.equals("")||info.equals(null)?m.getCon():info, attrset);
            textPane.setCaretPosition(docChat.getLength()); // 设置滚动到最下边
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
	}
	//写一个方法，让它显示消息
	public void showMessage(Message m) {
		String info=m.getSender()+" "+m.getSendTime()+"\n";
		SimpleAttributeSet attrset = new SimpleAttributeSet();
        StyleConstants.setFontSize(attrset,14);
        StyleConstants.setForeground(attrset, Color.blue);
        StyleConstants.setFontFamily(attrset, "宋体");
        Document docs = textPane.getDocument();
        try {
            docs.insertString(docs.getLength(), info, attrset);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        info=m.getCon();
        int index = info.lastIndexOf("*");
        StyleConstants.setFontSize(attrset,m.getSize());
        StyleConstants.setForeground(attrset, recolor(m.getCol()));
        StyleConstants.setFontFamily(attrset, m.getFont());
        docs = textPane.getDocument();
        pos1 = docs.getLength();
        if(index>0 && index < info.length()-1){
			try {
	            docs.insertString(docs.getLength(), info.substring(0,index)+"\r\n", attrset);
	            textPane.setCaretPosition(docChat.getLength()); // 设置滚动到最下边
	        } catch (BadLocationException e) {
	            e.printStackTrace();
	        }
			receivedPicInfo(info.substring(index+1,info.length()));
			insertPics(true);
		}else{
			try {
	            docs.insertString(docs.getLength(), info.substring(0,index)+"\r\n", attrset);
	            textPane.setCaretPosition(docChat.getLength()); // 设置滚动到最下边
	        } catch (BadLocationException e) {
	            e.printStackTrace();
	        }
		}
	}
	//写一个方法，让它显示消息
	public void showoldMessage(Message m) {
		String info=m.getSender()+" "+m.getSendTime()+"\n";
		SimpleAttributeSet attrset = new SimpleAttributeSet();
        StyleConstants.setFontSize(attrset,14);
        StyleConstants.setForeground(attrset, Color.green);
        StyleConstants.setFontFamily(attrset, "宋体");
        Document docs = textPane_2.getDocument();
        try {
            docs.insertString(docs.getLength(), info, attrset);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        info=m.getCon();
        int index = info.lastIndexOf("*");
        StyleConstants.setFontSize(attrset,m.getSize());
        StyleConstants.setForeground(attrset, recolor(m.getCol()));
        StyleConstants.setFontFamily(attrset, m.getFont());
        docs = textPane_2.getDocument();
        pos3 = docs.getLength();
        if(index>0 && index < info.length()-1){
			try {
	            docs.insertString(docs.getLength(), info.substring(0,index)+"\r\n", attrset);
	            textPane.setCaretPosition(docChat.getLength()); // 设置滚动到最下边
	        } catch (BadLocationException e) {
	            e.printStackTrace();
	        }
			receivedoldPicInfo(info.substring(index+1,info.length()));
			insertoldPics();
		}else{
			try {
	            docs.insertString(docs.getLength(), info.substring(0,index)+"\r\n", attrset);
	            textPane.setCaretPosition(docChat.getLength()); // 设置滚动到最下边
	        } catch (BadLocationException e) {
	            e.printStackTrace();
	        }
		}
	}
	public void showownMessage(Message m) {
		String info=m.getSender()+" "+m.getSendTime()+"\n";
		SimpleAttributeSet attrset = new SimpleAttributeSet();
        StyleConstants.setFontSize(attrset,14);
        StyleConstants.setForeground(attrset, Color.green);
        StyleConstants.setFontFamily(attrset, "宋体");
        Document docs = textPane.getDocument();
        try {
            docs.insertString(docs.getLength(), info, attrset);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        info=m.getCon()+"\r\n";
        StyleConstants.setFontSize(attrset,m.getSize());
        StyleConstants.setForeground(attrset, recolor(m.getCol()));
        StyleConstants.setFontFamily(attrset, m.getFont());
        docs = textPane.getDocument();
        pos2 = docs.getLength();
        try {
            docs.insertString(docs.getLength(), info, attrset);
            textPane.setCaretPosition(docChat.getLength()); // 设置滚动到最下边
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        insertPics(false);
	}
	public void shake(Message m){
		String info=m.getCon()+"\r\n";
		SimpleAttributeSet attrset = new SimpleAttributeSet();
        StyleConstants.setFontSize(attrset,m.getSize());
        StyleConstants.setForeground(attrset, recolor(m.getCol()));
        StyleConstants.setFontFamily(attrset, m.getFont());
        Document docs = textPane.getDocument();
        docs = textPane.getDocument();
        pos2 = docs.getLength();
        try {
            docs.insertString(docs.getLength(), info, attrset);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
		setExtendedState(Frame.NORMAL);
		setVisible(true);
		new Thread(){
			long begin = System.currentTimeMillis();
			long end = System.currentTimeMillis();
			Point p = QunChat.this.getLocationOnScreen();
			public void run(){
				int i = 1;
				while((end-begin)/1000<2){
					QunChat.this.setLocation(new Point((int)p.getX()-5*i,(int)p.getY()+5*i));
					end = System.currentTimeMillis();
					try {
						Thread.sleep(5);
						i=-i;
						QunChat.this.setLocation(p);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}
	/**
	 * 插入图片
	 */
	int pos1;
	int pos2;
	private void insertPics(boolean isFriend) {
		if(isFriend){
			if(this.receivdPicInfo.size()<=0){
				return;
			}else{
				for(int i = 0 ; i < receivdPicInfo.size() ; i++){
					PicInfo pic = receivdPicInfo.get(i);
					String fileName;
					textPane.setCaretPosition(pos1+pic.getPos()); /*设置插入位置*/
		            fileName= "face/"+pic.getVal()+".gif";/*修改图片路径*/ 
		            textPane.insertIcon(new  ImageIcon(PicsJWindow.class.getResource(fileName))); /*插入图片*/
				}
				receivdPicInfo.clear();
			}
		}else{
			if(myPicInfo.size()<=0){
				return;
			}else{
				for(int i = 0 ; i < myPicInfo.size() ; i++){
					PicInfo pic = myPicInfo.get(i);
					
					textPane.setCaretPosition(pos2+pic.getPos()); /*设置插入位置*/
					String fileName;
		            fileName= "face/"+pic.getVal()+".gif";/*修改图片路径*/ 
		            textPane.insertIcon(new  ImageIcon(PicsJWindow.class.getResource(fileName))); /*插入图片*/
				}
				myPicInfo.clear();
			}
		}
		textPane.setCaretPosition(docChat.getLength()); /*设置滚动到最下边*/
	}
	int pos3;
	private void insertoldPics() {
			if(this.receivdoldPicInfo.size()<=0){
				return;
			}else{
				for(int i = 0 ; i < receivdoldPicInfo.size() ; i++){
					PicInfo pic = receivdoldPicInfo.get(i);
					String fileName;
					textPane_2.setCaretPosition(pos3+pic.getPos()); /*设置插入位置*/
		            fileName= "face/"+pic.getVal()+".gif";/*修改图片路径*/ 
		            textPane_2.insertIcon(new  ImageIcon(PicsJWindow.class.getResource(fileName))); /*插入图片*/
				}
				receivdoldPicInfo.clear();
			}
			textPane_2.setCaretPosition(oldMsg.getLength()); /*设置滚动到最下边*/
	}
	/**
	 * 重组发送的表情信息
	 * @return 重组后的信息串格式为   位置|代号+位置|代号+……
	 */
	private boolean d=true;
	private String buildPicInfo(){
		StringBuilder sb = new StringBuilder("");
		//遍历JTextPane找出所有的图片信息封装成指定格式
		  for(int i = 0; i < this.textPane_1.getText().length(); i++){ 
              if(docMsg.getCharacterElement(i).getName().equals("icon")){
            	  Icon icon = StyleConstants.getIcon(textPane_1.getStyledDocument().getCharacterElement(i).getAttributes());
            	  ChatPic cupic = (ChatPic)icon;
            	  PicInfo picInfo= new PicInfo(i,cupic.getIm()+"");
            	  myPicInfo.add(picInfo);
            	  sb.append(i+"&"+cupic.getIm()+"+");
             } 
          }
		  return sb.toString();
	}
	public void mouseClicked(MouseEvent e) {
		if(d) {
			picWindow.setVisible(true);
			d=false;
		}else {
			picWindow.setVisible(false);
			d=true;
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
	boolean c = true;
	int count=1;
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==cd_word) {
			if(c){
				panel_4.setVisible(true);
				c = false;
				}else{
					panel_4.setVisible(false);
					c = true;					
				}
		}
		if(e.getSource()==cd_searchjilu) {
			if(count==1) {
				panel_3.setBounds(446, 91, 283, 442);
				Cardjilu.show(panel_3, "2");
				count+=1;
				Message m=new Message();
				m.setMesType(MessageType.message_Qun_oldmes);
				m.setSender(this.ownerId);
				m.setGetter(this.QunId);
				m.setSendTime(new Date().toString());
				//发送给服务器.
				try {
					ObjectOutputStream oos=new ObjectOutputStream
					(ManageClientConServerThread.getClientConServerThread(ownerId).getS().getOutputStream());
					oos.writeObject(m);
				} catch (Exception e1) {
					e1.printStackTrace();
				}			
			}
			else if(count == 2)
			{
				count+=1;
			}
			else if(count == 3)
			{
				count+=1;
			}
			else if(count==4)
			{
				panel_3.setBounds(450, 90, 280, higth);
				Cardjilu.show(panel_3, "1");
				count=1;
				textPane_2.setText("");
			}
		}
		if(e.getSource()==cd_zhendong) {
			Message m=new Message();
			m.setMesType(MessageType.message_shake);
			m.setSender(this.ownerId);
			m.setGetter(this.QunId);
			m.setCol(Color.red.toString());
			m.setFont("黑体");
			m.setSize(14);
			DateFormat ddtf = DateFormat.getDateTimeInstance();  
			m.setSendTime(ddtf.format(new Date()));
			m.setCon(this.ownerId+" 发送了一个窗口抖动！");
			String info="\r\n你发送了一个窗口抖动！\r\n";
			SimpleAttributeSet attrset = new SimpleAttributeSet();
	        StyleConstants.setFontSize(attrset,14);
	        StyleConstants.setForeground(attrset, Color.green);
	        StyleConstants.setFontFamily(attrset, "宋体");
	        Document docs = textPane.getDocument();
	        try {
	            docs.insertString(docs.getLength(), info, attrset);
	        } catch (BadLocationException e3) {
	            e3.printStackTrace();
	        }
			//发送给服务器.
			try {
				ObjectOutputStream oos=new ObjectOutputStream
				(ManageClientConServerThread.getClientConServerThread(ownerId).getS().getOutputStream());
				oos.writeObject(m);
			} catch (Exception e1) {
				e1.printStackTrace();
			}		
		}
		if(e.getSource()==cd_close) {
			//如果用户点击了关闭按钮
			ManageWeChat.RemoveQunChat(QunChat.this.ownerId+" "+QunChat.this.QunId);
			QunChat.this.dispose();	
		}
		if(e.getSource()==cd_send) {
			//如果用户点击了发送按钮
			String pic = buildPicInfo();
			Message m=new Message();
			m.setMesType(MessageType.message_Qun_mes);
			m.setSender(this.ownerId);
			m.setGetter(this.QunId);
			m.setMyname(this.myname);
			m.setCon(textPane_1.getText());
			m.setCol(this.Text_color.toString());
			m.setFont(cd_ziti.getSelectedItem().toString());
			m.setSize(Integer.parseInt(cd_size.getSelectedItem().toString()));
			DateFormat ddtf = DateFormat.getDateTimeInstance();  
			m.setSendTime(ddtf.format(new Date()));
			this.showownMessage(m);
			m.setCon(textPane_1.getText()+"*"+pic);
			this.textPane_1.setText("");
			//发送给服务器
			try {
				ObjectOutputStream oos=new ObjectOutputStream
				(ManageClientConServerThread.getClientConServerThread(ownerId).getS().getOutputStream());
				oos.writeObject(m);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		if(e.getSource()==cd_ziti ||e.getSource()==cd_size) {
			textPane_1.setFont(new Font(cd_ziti.getSelectedItem().toString(), Font.PLAIN, Integer.parseInt(cd_size.getSelectedItem().toString())));
		}
		if(e.getSource()==cd_color) {
			Text_color=JColorChooser.showDialog(null,"请选择你喜欢的颜色" ,Text_color);  
			textPane_1.setForeground(Text_color);
		}
		if(e.getSource()==cd_jietu) {
			ScreenCapture.screenShot();
		}
	}
	//拖放上传文件功能
	public void drag()//定义拖拽方法
    {
        //Panel表示要接受拖拽的控件
        new DropTarget(textPane_1, DnDConstants.ACTION_COPY_OR_MOVE, new DropTargetAdapter()
        {
            @SuppressWarnings("unchecked")
			public void drop(DropTargetDropEvent dtde)//重写适配器的drop方法
            {
                try
                {
                    if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor))//如果拖入的文件格式受支持
                    {
                        dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);//接收拖拽来的数据
                        List<File> list =  (List<File>) (dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor));
                        String filepath="";
                        for(File file:list)
                        	filepath+=file.getAbsolutePath();                                         
                        dtde.dropComplete(true);//指示拖拽操作已完成
                        if(JOptionPane.showConfirmDialog(null, "确认要发送该文件吗？", "提示", JOptionPane.YES_NO_OPTION)==0) {
	            			File file=new File(filepath);
	            			Message m=new Message();
	            			m.setMesType(MessageType.message_Qunfile);
	            			m.setSender(ownerId);
	            			m.setGetter(QunId);
	            			m.setFilename(file.getName());
	            			String info=null;
	            			try {
	            				fis=new DataInputStream(new FileInputStream(file));
	            				int size=fis.available();
	            				byte[] by=new byte[size];
	            				fis.read(by);
	            				fis.close();
	            				m.setFilebyte(by, size);
	            			    m.setSendTime(new Date().toString());
	            			    ObjectOutputStream oos=new ObjectOutputStream
	            				(ManageClientConServerThread.getClientConServerThread(ownerId).getS().getOutputStream());
	            				oos.writeObject(m);
	            				info="您已成功上传文件\""+file.getName()+"\""+"\r\n";
	        			    } catch (Exception e) {
	        			    	info="服务器连接超时，文件发送失败\r\n";	    
	        			    }
	            			if(!info.equals(null)&&!info.equals(""))
								QunChat.this.showfileinfoMsg(m, info);
                        }
                    }else {
                        dtde.rejectDrop();//否则拒绝拖拽来的数据
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
	public void setjiluText(String str) {
		k.setText(str);
	}
	public void addJLabel(String text,String text1) {
		higth+=30;
		record++;
		boolean flag=false;
		for(int i=0;i<record-1;i++) {
			flag=jla[i].getText().equals(text1);
		}
		if(!flag)
		{
			jla[record-1]=new JLabel(text1,new ImageIcon("mm.jpg"),JLabel.LEFT);
			jla[record-1].addMouseListener(new MouseListener(){
				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount()==2)
					{
						String friendId=((JLabel)e.getSource()).getText();
						WeChat wechat=ManageWeChat.getWeChat(ownerId+" "+friendId);
						if(wechat==null) {
							WeChat weChat=new WeChat(ownerId,myname,friendId);
							//把聊天界面加入到管理类
							ManageWeChat.addWeChat(ownerId+" "+friendId, weChat);
							weChat.setVisible(true);
						}
						else {
							wechat.setVisible(true);
						}
					}
				}
				public void mousePressed(MouseEvent e) {					
				}
				public void mouseReleased(MouseEvent e) {		
				}
				public void mouseEntered(MouseEvent e) {				
				}
				public void mouseExited(MouseEvent e) {				
				}});
			Qunpane.add(jla[record-1],"1");
		}
		else {
			higth-=30;
			record--;	
		}
		y=700-higth;
		Qunpane.setLayout(new GridLayout(record,1));
		panel_3.add(Qunpane,"1");
		panel_3.setLayout(Cardjilu);
		panel_3.setBounds(450, 90, 280, higth);
		Cardjilu.show(panel_3, "1");
		Qunpane.setBackground(Color.white);
		Qunpane.setBounds(0,0, 280, higth);
	}
}
