package com.view;
/**
 * 注册界面
 */
import javax.swing.*;

import com.basic.Message;
import com.basic.User;
import com.model.ClientUser;
import com.tools.CheckImage;

import java.awt.*;
import java.awt.event.*;

public class Register extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private JLabel r_label1,r_label2,r_yanzhengma,r_jb2,r_jb3;
	private JButton r_jb1;
	private JTextField textField;
	private JTextField textField_1;
	private JRadioButton r_male,r_female;
	private JComboBox<?> r_cbsheng,r_cbnation,r_cbmonth,r_cbmdate,r_cbyear;
	private boolean isDragged = false;
	private Point loc = null;  
    private Point tmp = null;
    private ImageIcon icon =null;
    private JPasswordField passwordField;
    private JPasswordField passwordField_1;
    private Color backcolor = new Color(248,253,255);
    
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Register(){
		
		this.setSize(790,570);
		this.getContentPane().setLayout(null);
		this.setLocationRelativeTo(null);
		//this.setIconImage(new ImageIcon("./images/wx.png").getImage());
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 790, 640);
		panel.setBackground(backcolor);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		r_label1 = new JLabel(new ImageIcon("image/register/Register.jpg"));
		r_label1.setBounds(0, 0, 790, 91);
		panel.add(r_label1);
		
		r_label2 = new JLabel(new ImageIcon("image/register/WeChat.jpg"));
		r_label2.setBounds(0, 91, 206, 76);
		panel.add(r_label2); 
		
		r_jb1 = new JButton(new ImageIcon("image/register/r.jpg"));
		r_jb1.setBounds(297, 486, 174, 46);
		panel.add(r_jb1);
		r_jb1.addActionListener(this);
		
		r_jb2 = new JLabel(new ImageIcon("image/exit.png"));
		r_jb2.setBounds(760, 0, 32, 32);
		r_jb2.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				new Login();
				Register.this.dispose();
			}
			public void mouseEntered(MouseEvent e) {
				r_jb2.setIcon(new ImageIcon("image/fexit.png"));
			}
			public void mouseExited(MouseEvent e) {
				r_jb2.setIcon(new ImageIcon("image/exit.png"));
			}
			public void mousePressed(MouseEvent e) {
				r_jb2.setIcon(new ImageIcon("image/pexit.png"));
			}
		});
		r_label1.add(r_jb2);
		
		r_jb3 = new JLabel(new ImageIcon("image/min.png"));
		r_jb3.setBounds(728, 0, 32, 32);
		r_jb3.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				setExtendedState(JFrame.ICONIFIED);	
			}
			public void mouseEntered(MouseEvent e) {
				r_jb3.setIcon(new ImageIcon("image/fmin.png"));
			}
			public void mouseExited(MouseEvent e) {
				r_jb3.setIcon(new ImageIcon("image/min.png"));
			}
			public void mousePressed(MouseEvent e) {
				r_jb3.setIcon(new ImageIcon("image/pmin.png"));
			}
		});
		r_label1.add(r_jb3);
		
		textField = new JTextField();
		textField.setBounds(269, 427, 110, 25);		//验证码框
		panel.add(textField);
		textField.setColumns(10);
		
		r_yanzhengma = new JLabel();
		icon = new ImageIcon(new CheckImage().getCheckImage());
		r_yanzhengma.setIcon(icon);
		r_yanzhengma.setBounds(387, 427, 110, 25);		//验证码
		panel.add(r_yanzhengma);
		r_yanzhengma.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				icon = new ImageIcon(new CheckImage().getCheckImage());
				r_yanzhengma.setIcon(icon);
			}
		});
		
		textField_1 = new JTextField();
		textField_1.setBounds(269, 204, 227, 25);		//昵称
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		r_male = new JRadioButton("男",true);
		r_male.setBounds(269, 332, 45, 23);
		r_male.setBackground(backcolor);
		r_male.setFocusable(false);
		panel.add(r_male);
		r_female = new JRadioButton("女");				//性别
		r_female.setBounds(336, 332, 45, 23);
		r_female.setBackground(backcolor);
		r_female.setFocusable(false);
		panel.add(r_female);
		ButtonGroup bg = new ButtonGroup();	
		bg.add(r_male);	
		bg.add(r_female);
		
		r_cbyear = new JComboBox();						//出生年月日
		r_cbyear.setBounds(269, 361, 77, 25);
		r_cbyear.setFocusable(false);
		this.disableFocusBackground(r_cbyear);
		r_cbyear.setModel(new DefaultComboBoxModel(new String[] {"\u5E74", "1990", "1991", "1992", "1993", "1994", "1995", "1996", "1997", "1998", "1999", "2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010", "2011", "2012", "2013", "2014"}));
		panel.add(r_cbyear);
		
		r_cbmonth = new JComboBox();
		r_cbmonth.setFocusable(false);
		this.disableFocusBackground(r_cbmonth);
		r_cbmonth.setModel(new DefaultComboBoxModel(new String[] {"月", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}));
		r_cbmonth.setBounds(360, 361, 48, 25);
		panel.add(r_cbmonth);
		
		r_cbmdate = new JComboBox();
		r_cbmdate.setFocusable(false);
		this.disableFocusBackground(r_cbmdate);
		r_cbmdate.setModel(new DefaultComboBoxModel(new String[] {"日", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30"}));
		r_cbmdate.setBounds(418, 361, 48, 25);
		panel.add(r_cbmdate);
		
		r_cbnation = new JComboBox();
		r_cbnation.setFocusable(false);
		this.disableFocusBackground(r_cbnation);
		r_cbnation.setModel(new DefaultComboBoxModel(new String[] {"中国"}));
		r_cbnation.setBounds(269, 394, 77, 25);
		panel.add(r_cbnation);
		
		r_cbsheng = new JComboBox();
		r_cbsheng.setFocusable(false);
		this.disableFocusBackground(r_cbsheng);
		r_cbsheng.setModel(new DefaultComboBoxModel(new String[] {"省份","北京","天津","上海","重庆","河北","山西","辽宁","吉林","黑龙江","江苏","浙江","安徽","福建","江西","山东","河南","湖北","湖南","广东","海南","四川","贵州","云南","陕西","甘肃","青海","台湾","内蒙古","广西","西藏","宁夏","新疆","香港","澳门"}));
		r_cbsheng.setBounds(360, 394, 75, 25);
		panel.add(r_cbsheng);
		
		JLabel label = new JLabel("\u6635\u79F0:");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setBounds(182, 207, 77, 18);
		panel.add(label);
		
		JLabel label_1 = new JLabel("\u5BC6\u7801:");
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setBounds(205, 252, 54, 18);
		panel.add(label_1);
		
		JLabel label_2 = new JLabel("\u786E\u8BA4\u5BC6\u7801:");
		label_2.setHorizontalAlignment(SwingConstants.RIGHT);
		label_2.setBounds(184, 298, 75, 18);
		panel.add(label_2);
		
		JLabel label_3 = new JLabel("\u6027\u522B:");
		label_3.setHorizontalAlignment(SwingConstants.RIGHT);
		label_3.setBounds(205, 334, 54, 18);
		panel.add(label_3);
		
		JLabel label_4 = new JLabel("\u51FA\u751F\u65E5\u671F:");
		label_4.setHorizontalAlignment(SwingConstants.RIGHT);
		label_4.setBounds(184, 367, 75, 18);
		panel.add(label_4);
		
		JLabel label_5 = new JLabel("\u6240\u5728\u5730:");
		label_5.setHorizontalAlignment(SwingConstants.RIGHT);
		label_5.setBounds(184, 399, 75, 18);
		panel.add(label_5);
		
		JLabel label_6 = new JLabel("\u9A8C\u8BC1\u7801:");
		label_6.setHorizontalAlignment(SwingConstants.RIGHT);
		label_6.setBounds(205, 432, 54, 18);
		panel.add(label_6);
		
		JLabel lblNewLabel = new JLabel(new ImageIcon("image/register/zhuce.png"));
		lblNewLabel.setBounds(208, 147, 606, 42);
		panel.add(lblNewLabel);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(269, 248, 227, 25);
		passwordField.setEchoChar('*');
		panel.add(passwordField);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(269, 294, 227, 25);
		passwordField_1.setEchoChar('*');
		panel.add(passwordField_1);
		this.setUndecorated(true);
		
		this.setVisible(true);
		// 为窗体添加鼠标事件  
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                isDragged = false;
            }  
            public void mousePressed(MouseEvent e) {  
            	if(e.getY()<91){
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
	@SuppressWarnings("rawtypes")
	private void disableFocusBackground(JComboBox combo){
		if (combo == null){
			return;
		}
		java.awt.Component comp = combo.getEditor().getEditorComponent();
		if (comp instanceof JTextField)
		{
			JTextField field = (JTextField) comp;
			field.setEditable(false);
			field.setSelectionColor(Color.white);
			combo.setEditable(true);
		}
	}
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==r_jb1)
		{
			if(!textField.getText().equals(CheckImage.Code)){
				JOptionPane.showMessageDialog(this,"验证码输入错误");
				icon = new ImageIcon(new CheckImage().getCheckImage());
				r_yanzhengma.setIcon(icon);
			}else if(textField_1.getText().isEmpty()){
				JOptionPane.showMessageDialog(this,"请填写昵称");
			}else if((String.valueOf(passwordField.getPassword()).isEmpty())||(String.valueOf(passwordField_1.getPassword()).isEmpty())){
				JOptionPane.showMessageDialog(this,"请填写密码");
			}else if(!((String.valueOf(passwordField.getPassword()).equals((String.valueOf(passwordField_1.getPassword())))))){
				JOptionPane.showMessageDialog(this,"两次密码不一样");
			}else if(r_cbyear.getSelectedItem().toString().equals("年")){
				JOptionPane.showMessageDialog(this,"请选择年份");
			}else if(r_cbmonth.getSelectedItem().toString().equals("月")){
				JOptionPane.showMessageDialog(this,"请选择月份");
			}else if(r_cbmdate.getSelectedItem().toString().equals("日")){
				JOptionPane.showMessageDialog(this,"请选择日份");
			}else if(r_cbsheng.getSelectedItem().toString().equals("省份")){
				JOptionPane.showMessageDialog(this,"请选择省份");
			}else{
				String mes = "";
				if(r_male.isSelected())
					mes += textField_1.getText().toString() +" " + String.valueOf(passwordField.getPassword())
						+" 男 "+r_cbyear.getSelectedItem().toString()+"-"+r_cbmonth.getSelectedItem().toString()
						+"-"+r_cbmdate.getSelectedItem().toString();
				else
					mes += textField_1.getText().toString() +" " + String.valueOf(passwordField.getPassword())
					+" 女 "+r_cbyear.getSelectedItem().toString()+"-"+r_cbmonth.getSelectedItem().toString()
					+"-"+r_cbmdate.getSelectedItem().toString();
				
				ClientUser clientUser=new ClientUser();
				User u=new User();
				u.setUserId("1");
				u.setUnload("1");
				u.setPasswd("2");
				u.setRegiste(mes);
				Message m= clientUser.Registe(u);
				JOptionPane.showMessageDialog(this,"您注册的帐号是 "+m.getCon()+" 请牢记");
				new Login();
				this.dispose();
			}
		}
	}
	
}
