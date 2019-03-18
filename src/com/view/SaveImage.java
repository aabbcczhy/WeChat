package com.view;
/**
 * 图片保存界面
 */
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class SaveImage {

	public static int doSave(BufferedImage img){
		try{
			JFileChooser chooser=new JFileChooser();
			//添加过滤器
			FileNameExtensionFilter jpgFilter=new FileNameExtensionFilter("JPG/JPEG - JPG文件","jpg");
			FileNameExtensionFilter gifFilter=new FileNameExtensionFilter("GIF - Compuserve GIF","gif");
			chooser.addChoosableFileFilter(gifFilter);
			chooser.addChoosableFileFilter(jpgFilter);
			//打开保存对话框
			int result=chooser.showSaveDialog(null);
			if(result==JFileChooser.APPROVE_OPTION){//保存
				File file=chooser.getSelectedFile();
				String about="jpg";//文件类型
				String pathname=file.toString().toLowerCase();
				FileNameExtensionFilter filter=(FileNameExtensionFilter )chooser.getFileFilter();
				String ext=filter.getExtensions()[0];//文件后缀名
				if("jpg".equals(ext)){
					if(!pathname.endsWith(".jpg")){
						pathname+=".jpg";
						file=new File(pathname);
						about="jpg";
					}
				}else if("gif".equals(ext)){
					if(!pathname.endsWith(".gif")){
						pathname+=".gif";
						file=new File(pathname);
						about="gif";
					}
				}
				
				if(ImageIO.write(img, about, file)){
					JOptionPane.showMessageDialog(null, "保存成功");
					return 1;
				}else{
					JOptionPane.showMessageDialog(null, "保存失败!");
					return 0;
				}
			}
		}catch(Exception e){
		}
		return 0;
	}
}

