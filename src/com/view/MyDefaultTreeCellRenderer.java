package com.view;

/** 
 * 自定义树描述类,将树的每个节点设置成不同的图标
 */

import java.awt.Component;  
  



import com.basic.*;

import javax.swing.ImageIcon;  
import javax.swing.JTree;  
import javax.swing.tree.DefaultMutableTreeNode;  
import javax.swing.tree.DefaultTreeCellRenderer;  

public class MyDefaultTreeCellRenderer extends DefaultTreeCellRenderer {
    private static final long serialVersionUID = 1L;  
    private Message m;
    private String own;
    String onLineFriend[] = null;

    public MyDefaultTreeCellRenderer(String own,Message m) {
    	this.m = m;
    	this.own = own;
    }
    public Component getTreeCellRendererComponent(JTree tree, Object value,boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {  
        //执行父类原型操作  
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);  
        setText(value.toString());  
        if (sel) {  
            setForeground(getTextSelectionColor());  
        }  
        else {  
            setForeground(getTextNonSelectionColor());  
        }  
        //得到每个节点的TreeNode  
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value; 
        //得到每个节点的text 
        String str = node.toString();  
        if(str.length()>5){
        	int x = str.indexOf("(");
        	str = str.substring(x+1, str.length()-1);
        }
        //判断是哪个文本的节点设置对应的值（这里如果节点传入的是一个实体,则可以根据实体里面的一个类型属性来显示对应的图标）  
        onLineFriend=m.getCon().split(" ");
        
        if(m.getMesType() == MessageType.message_get_onLineFriend){
        	if (node.isLeaf()&&str.equals(this.own)){ 
        		this.setIcon(new ImageIcon("image/online.png"));  
        	}
        	else if(node.isLeaf()){
        		this.setIcon(new ImageIcon("image/outline.png"));
        		for(int i=0;i<onLineFriend.length;i++){					
        			if(str.equals(onLineFriend[i]))
        				this.setIcon(new ImageIcon("image/online.png")); 
        		}
        	}
        	else
        		this.setIcon(new ImageIcon("image/lie.png")); 
        }
        if(m.getMesType() == MessageType.messgae_ret_unloadFriends){
        	String onunloadFriend[]=m.getCon1().split(" ");
        	if (node.isLeaf()&&str.equals(this.own)){  
        		this.setIcon(new ImageIcon("image/online.png"));  
        	}
        	else if(node.isLeaf()){
        		this.setIcon(new ImageIcon("image/outline.png"));
        		for(int i=0;i<onLineFriend.length;i++){
        			if(str.equals(onLineFriend[i]))
        				this.setIcon(new ImageIcon("image/online.png"));       				
        		}
        		for(int i=0;i<onunloadFriend.length;i++){		 
        			if(str.equals(onunloadFriend[i]))
        				this.setIcon(new ImageIcon("image/outline.png"));        				
        		}
        	}
        	else
        		this.setIcon(new ImageIcon("image/lie.png")); 
        }
        return this;  
    }
}  