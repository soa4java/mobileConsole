package com.yuchengtech.mobile.console.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.RollingFileAppender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yuchengtech.mobile.console.utils.DateUtil;
import com.yuchengtech.mobile.console.utils.ZipUtil;


@Controller
@RequestMapping(value="/system-log")
public class SystemLogAction {

 	

	private static String logFilePath = null;

	@RequestMapping(value="list")
	public String execute(HttpServletResponse response ){	
		String filePath = getLogFilePath();	
		String zipFilePath = getZipFilePath(filePath);
        ZipUtil.createZipFile(filePath, zipFilePath);
        
        File file = new File(zipFilePath);
        response.setContentType("application/x-zip-compressed"); 
        response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
        InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(file);
			out = response.getOutputStream();
			byte[] buffer = new byte[1024];
			int reader=0;
			while ((reader=in.read(buffer,0,1024))!=-1) {  
				out.write(buffer,0,reader);  
			}  
			out.flush();          				
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			if (in!=null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (out!=null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			file.delete();
		}	
		return null;
	}

	public static String getLogFilePath() {
	 	if (logFilePath==null) {
			Logger root = Logger.getRootLogger();
			RollingFileAppender appender = (RollingFileAppender)root.getAppender("R1");
			String logFile = appender.getFile();
			logFilePath = StringUtils.substringBeforeLast(logFile, "/");
		} 
		return "";
	}
	
	private String getZipFilePath(String filePath) {
		String f = filePath.substring(0, filePath.lastIndexOf("/")+1);		
		StringBuilder sb = new StringBuilder() ;
		sb.append(f);
		sb.append("logs_");
		sb.append(DateUtil.format("yyyyMMddhhmmss"));
		sb.append(".zip");
		return sb.toString();
	}
	
}