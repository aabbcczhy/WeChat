package com.tools;
/**
 *验证码生成
 */
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class CheckImage {
	private final BufferedImage bim = new BufferedImage(68,30,BufferedImage.TYPE_INT_RGB);
	private Graphics g =null;
	private Random rm = new Random();	//产生随机数
	private int width=78;
	private int height=30;
	StringBuffer sbf = new StringBuffer();
	public static String Code=null;
	
	public BufferedImage getCheckImage() {
		g = bim.getGraphics();
		Color color = new Color(rm.nextInt(255),rm.nextInt(50)+200,rm.nextInt(255));
		g.setColor(color);
		g.fillRect(0, 0, width, height);
		for(int i=0;i<4;i++) {
			g.setColor(Color.black);
			g.setFont(new Font("隶书",Font.BOLD|Font.ITALIC,22));
			int n = rm.nextInt(10);
			sbf.append(n);
			g.drawString(""+n, i*15+5, 25);
		}
		Code=sbf.toString();
		g.dispose();
		return bim;
	}
}
