package com.yuchengtech.mobile.console.utils;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
/**
 * 图片处理工具类
 * 
 * @author neo
 *
 *  2013-06-08
 */
public class ImageUtils {
	
	/**
	 * 调整大小
	 * @param srcImgPath
	 * @param destImgPath
	 * @param width
	 * @param height
	 * @throws IOException
	 */
	public static void resize(String srcImgPath, String destImgPath,int width, int height) throws IOException {   
		ImageInfo info = getImageInfo(srcImgPath);
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        graphics.drawImage(info.getImage(), 0,0,width,height,null);
        graphics.dispose();
        ImageIO.write(image, info.getSuffix(), new File(destImgPath));
    } 
	
	/**
	 * 剪切
	 * @param srcImgPath
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return
	 * @throws IOException
	 */
	public static BufferedImage cut(String srcImgPath,int x, int y,int width, int height) throws IOException {   
		ImageInfo info = getImageInfo(srcImgPath);
        BufferedImage image = info.getImage().getSubimage(x, y, width, height);
        return image;
    }  
	
	/**
	 * 剪切
	 * @param srcImgPath
	 * @param destImgPath
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @throws IOException
	 */
	public static void cut(String srcImgPath,String destImgPath,int x, int y,int width, int height) throws IOException {   
        BufferedImage image = cut(srcImgPath, x, y, width, height);
        String suffix = getFormatName(srcImgPath);
        ImageIO.write(image, suffix, new File(destImgPath));
    }  
	
	/**
	 * 根据高度自适应
	 * @param srcImgPath
	 * @param destImgPath
	 * @param height
	 * @throws IOException
	 */
	public static void autofitByHeight(String srcImgPath, String destImgPath,int height) throws IOException {  
		ImageInfo info = getImageInfo(srcImgPath);
		float times = (height*1.0f)/info.getHeight();
        int width = Math.round(info.getWidth()*times);
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        graphics.drawImage(info.getImage(), 0,0,width,height,null);
        graphics.dispose();
        ImageIO.write(image, info.getSuffix(), new File(destImgPath));
    }  
	
	/**
	 * 根据高度自适应
	 * @param temp
	 * @param dest
	 * @param formatName
	 * @param height
	 * @throws IOException
	 */
	public static void autofitByWidth(File temp, String formatName,String destImgPath,int width) throws IOException {
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(temp);
			BufferedImage image = ImageIO.read(inputStream); 
			float times = 1.0f;
			int height = 0;
			if (image.getWidth()<width) {
				width = image.getWidth();
				height = image.getHeight();
			}else {
				times = (width*1.0f)/image.getWidth();
				height = Math.round(image.getHeight()*times);
			}
			BufferedImage newImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
			Graphics graphics = newImage.getGraphics();
			graphics.drawImage(image, 0,0,width,height,null);
			graphics.dispose();
			File dest = new File(destImgPath);
			ImageIO.write(newImage, formatName, dest);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			inputStream.close();
		}
	}
	
	/**
	 * 根据宽度自适应
	 * @param srcImgPath
	 * @param destImgPath
	 * @param width
	 * @throws IOException
	 */
	public static void autofitByWidth(String srcImgPath, String destImgPath,int width) throws IOException {  
		ImageInfo info = getImageInfo(srcImgPath);
		float times =  (width*1.0f)/info.getWidth();
        int height = Math.round(info.getHeight()*times);
        BufferedImage image = new BufferedImage(width,height,info.getType());
        Graphics graphics = image.getGraphics();
        graphics.drawImage(info.getImage(), 0,0,width,height,null);
        graphics.dispose();
        ImageIO.write(image, info.getSuffix(), new File(destImgPath));
    } 
	
	 /**
     * 对图片进行缩放
     * @param originalImage 原始图片
     * @param times 缩放倍数
     * @return
     */
    public static BufferedImage  zoom(String  srcImgPath, float times){
    	ImageInfo info = getImageInfo(srcImgPath);
        int width = Math.round(info.getWidth()*times);
        int height = Math.round(info.getHeight()*times);
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.drawImage(info.getImage(), 0,0,width,height,null);
        g.dispose();
        return image;
    }
    
    /**
     * 对图片进行缩放
     * @param originalImage 原始图片
     * @param times 缩放倍数
     * @return
     * @throws IOException 
     */
    public static BufferedImage  zoom(String  srcImgPath, String  destImgPath,float times) throws IOException{
    	BufferedImage image =  zoom(srcImgPath, times);
    	String suffix = getFormatName(srcImgPath);
        ImageIO.write(image, suffix, new File(destImgPath));
        return image;
    }

	private static String getFormatName(String srcImgPath) {
		File imgFile = new File(srcImgPath);
		String name = imgFile.getName(); 
		String suffix = name.substring(name.lastIndexOf('.')+1);
		return suffix;
	}
	
	/**
	 * 是否是图片
	 * @param ext
	 * @return "jpg", "jpeg", "gif", "png", "bmp" 为文件后缀名者为图片
	 */
	public static boolean isValidImage(String ext) {
		if (ext==null) {
			return false;			
		}
		String validExt ="jpg|jpeg|gif|png|bmp";
		return ext.toLowerCase().matches(validExt);
	}

	/**
	 * 获取图片信息
	 * @param imgPath
	 * @return
	 */
	public static ImageInfo getImageInfo(String imgPath){
		File imgFile = new File(imgPath);
		String name = imgFile.getName(); 
		String suffix = name.substring(name.lastIndexOf('.')+1);
		if (imgFile.isDirectory()) {
			throw new RuntimeException("图片路径错误");
		}
		if (!isValidImage(suffix)) {
			throw new RuntimeException("无效的图片格式");
		}
		FileInputStream fis = null;
		ImageInfo imageInfo = new ImageInfo();
		try {
			fis = new FileInputStream(imgFile);
			BufferedImage image = ImageIO.read(fis); 
			imageInfo.setImage(image);
			imageInfo.setName(name);
			imageInfo.setSuffix(suffix);
			imageInfo.setSize(imgFile.length());
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if (fis!=null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return imageInfo;
	}
	
	static class ImageInfo{
		//图片
		private BufferedImage image;
		//名称
		private String name;
		//后缀
		private String suffix;
		//大小
		private long size;
		
		public BufferedImage getImage() {
			return image;
		}
		public void setImage(BufferedImage image) {
			this.image = image;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getSuffix() {
			return suffix;
		}
		public void setSuffix(String suffix) {
			this.suffix = suffix;
		}
		public int getType() {
			return image.getType();
		}
		public int getHeight() {
			return image.getHeight();
		}
		public int getWidth() {
			return image.getWidth();
		}	
		public long getSize() {
			return size;
		}
		public void setSize(long size) {
			this.size = size;
		}		
	
	}
	
	public static void main(String[] args) throws IOException {
		String srcImgPath = "d:/src.png";
		String destImgPath = "d:/dest.png";
//		resize(srcImgPath, distImgPath, 946, 200);
//		autofitByHeight(srcImgPath, distImgPath, 802);
//		autofitByWidth(srcImgPath, destImgPath, 880);
//		zoom(srcImgPath, destImgPath, 2.0f);
//		resize(srcImgPath, destImgPath, 280, 180);
//		cut(srcImgPath, destImgPath, 0, 0, 200, 200);
		autofitByWidth(new File(srcImgPath), "png", destImgPath, 440);
	}
}
