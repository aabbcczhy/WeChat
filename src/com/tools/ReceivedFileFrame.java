package com.tools;
/**
 * 接收文件的窗口
 */
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.basic.*;
import com.view.WeChat;

public class ReceivedFileFrame extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private boolean isDragged = false;
	private Point tmp = null;
	private Point loc = null;  
	private JButton save,reject;
	private JLabel exit;
	private File file;
	private JFileChooser fileDialog;
	private Message ms=null;
	boolean isReceived=false;
	private DataOutputStream dos;
	
	public ReceivedFileFrame(Message m){
		this.ms=m;
		this.setAlwaysOnTop(true);
		this.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width-230, Toolkit.getDefaultToolkit().getScreenSize().height-180, 230, 180);
		this.setUndecorated(true);
		
		Panel panel = new Panel();
		panel.setBackground(Color.white);
		panel.setLayout(null);
		
		Panel top = new Panel();
		top.setBackground(new Color(100,100,100));
		top.setLayout(null);
		top.setBounds(0, 0, 230, 32);
		panel.add(top);
		
		exit = new JLabel(new ImageIcon ("image/exit.png"));
		exit.setAlignmentX(Component.CENTER_ALIGNMENT);
		exit.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				ReceivedFileFrame.this.rejectfile();
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
		exit.setBounds(198, 0, 32, 32);
		top.add(exit);
		
		JLabel file_pic = new JLabel(new ImageIcon("image/receivefile/file-pic.png"));
		file_pic.setBounds(10, 45, 64, 64);
		panel.add(file_pic);
		
		JLabel sender = new JLabel(ms.getMyname()+"");
		if(ms.getMyname()==null)
			sender.setText("软件工程(100000)");
		sender.setBounds(84, 45, 180, 20);
		panel.add(sender);
		
		JLabel sendmsg = new JLabel("向你发送文件");
		if(ms.getMyname()==null)
			sendmsg.setText("有新的群文件");
		sendmsg.setBounds(84, 70, 120, 20);
		panel.add(sendmsg);
		
		JLabel filename = new JLabel(ms.getFilename()+"");
		filename.setBounds(84, 95, 180, 20);
		panel.add(filename);
		
		save=new JButton("接收");
		if(ms.getMyname()==null)
			save.setText("下载");
		save.setBounds(25, 145, 80, 25);
		save.addActionListener(this);
		panel.add(save);
		
		reject=new JButton("拒绝");
		if(ms.getMyname()==null)
			reject.setText("关闭");
		reject.setBounds(125, 145, 80, 25);
		reject.addActionListener(this);
		panel.add(reject);
		
		this.add(panel);
		this.setVisible(true);
		top.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                isDragged = false;
                // 为指定的光标设置光标图像
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
            public void mousePressed(MouseEvent e) {
            	if(e.getY()<32){
	                tmp = new Point(e.getX(), e.getY());
	                isDragged = true;
            	}
            }
        });
        top.addMouseMotionListener(new MouseMotionAdapter() {
            // 鼠标按键在组件上按下并拖动时调用
            public void mouseDragged(MouseEvent e) {
                if (isDragged) {
                    loc = new Point(getLocation().x + e.getX() - tmp.x , getLocation().y + e.getY() - tmp.y);
                    setLocation(loc);
                }
            }
        });
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==save)	{
			//用户点击了接收按钮
			this.setVisible(false);
			fileDialog=new JFileChooser();  
			fileDialog.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); 
			fileDialog.showSaveDialog(null);
			file=new File(fileDialog.getSelectedFile().getPath()+"\\~"+ms.getFilename());
			try {
				dos=new DataOutputStream(new FileOutputStream(file.getPath()));
				byte[] by=ms.getFilebyte();
				dos.write(by);
				dos.close();
				isReceived=true;
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			if(isReceived) {
				Message m = new Message();
				m.setMesType(MessageType.message_returnfileinfo);
				m.setGetter(ms.getSender());
				m.setSender(ms.getGetter());
				m.setCon("对方已经成功接收了你发送的文件\""+ms.getFilename()+"\"");
				try {
					ObjectOutputStream oos=new ObjectOutputStream(ManageClientConServerThread.getClientConServerThread(ms.getGetter()).getS().getOutputStream());
					oos.writeObject(m);
				}catch (Exception e1) {
					e1.printStackTrace();
				}
				WeChat wechat=ManageWeChat.getWeChat(ms.getGetter()+" "+ms.getSender());
				if(wechat!=null)
					wechat.showfileinfoMsg(ms, "你已经成功接收对方发送的文件\""+ms.getFilename()+"\"\r\n");
			}
		this.dispose();
		}
		if(e.getSource()==reject) {
			this.rejectfile();
		}
	}
	public void rejectfile() {
		WeChat wechat=ManageWeChat.getWeChat(ms.getGetter()+" "+ms.getSender());
		if(wechat!=null)
			wechat.showfileinfoMsg(ms, "你已拒绝对方向你发送的文件\""+ms.getFilename()+"\"\r\n");
		Message m = new Message();
		m.setMesType(MessageType.message_returnfileinfo);
		m.setGetter(ms.getSender());
		m.setSender(ms.getGetter());
		m.setCon("对方拒绝接收了你发送的文件\""+ms.getFilename()+"\"\r\n");
		m.setCol("red");
		try {
			ObjectOutputStream oos=new ObjectOutputStream(ManageClientConServerThread.getClientConServerThread(ms.getGetter()).getS().getOutputStream());
			oos.writeObject(m);
		}catch (Exception e1) {
			e1.printStackTrace();
		}
		this.dispose();
	}
}
