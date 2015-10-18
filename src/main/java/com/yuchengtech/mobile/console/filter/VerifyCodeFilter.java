package com.yuchengtech.mobile.console.filter;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.google.common.collect.MapMaker;

public class VerifyCodeFilter implements Filter {
	
	private String failureUrl;
	private String paramName;	
	private String imgExtName = ".jpg";
	public ConcurrentMap<String, String> cache;
	private int minLength = 4;
	private int maxLength = 4;
	private int fontSize = 20;
	private int imageWidth = 60;
	private int imageHeight = 20;

	public void init(FilterConfig config) throws ServletException {
		failureUrl = config.getInitParameter("failureUrl");
		if (StringUtils.isBlank(failureUrl)) {
			throw new IllegalArgumentException("VerifyCodeFilter缺少failureUrl参数");
		}
		paramName = config.getInitParameter("paramName");
		if (StringUtils.isBlank(paramName)) {
			throw new IllegalArgumentException("VerifyCodeFilter缺少paramName参数");
		}
		cache = new MapMaker().concurrencyLevel(32).expiration(3, TimeUnit.MINUTES).makeMap();
	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		if (StringUtils.endsWith(request.getRequestURI(),imgExtName)) {
			genernateImage(request, response);
		}else {
			boolean validated = validate(request);
			if (validated) {
				chain.doFilter(request, response);
			} else {
				redirectFailureUrl(request, response);
			}			
		}
	}

	private boolean validate(final HttpServletRequest request) {
		String sessionID = request.getSession().getId();
		String verifyCode = request.getParameter(paramName);
		boolean validate = false;
		if (null!=verifyCode) {
			validate = verifyCode.equalsIgnoreCase(cache.get(sessionID));
		}
		return validate;
	}
	
	protected void redirectFailureUrl(final HttpServletRequest request, final HttpServletResponse response)
			throws IOException {
		response.sendRedirect(request.getContextPath() + failureUrl);
	}
	
	private void genernateImage(final HttpServletRequest request, final HttpServletResponse response)
			throws IOException {
		response.setContentType("image/jpeg");
		response.setHeader("Pragma","No-cache");
		response.setHeader("Cache-Control","no-cache");
		response.setDateHeader("Expires", 0);
		BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
		String verifyCode = drawGraphics(image);
		String sessionID = request.getSession().getId();
		cache.put(sessionID, verifyCode);	
		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(image, "JPEG", out);
		IOUtils.closeQuietly(out);
	}

	private String drawGraphics(BufferedImage image) {
		Graphics g = image.getGraphics();
		Random random = new Random();	
		g.setColor(getRandomColor(random,200,250));
		g.fillRect(0, 0, imageWidth, imageHeight);
		g.setFont(new Font("Times New Roman", Font.PLAIN, fontSize));
		g.drawRect(0,0,imageWidth-1,imageHeight-1);
		g.setColor(getRandomColor(random,150, 200));
		for (int i = 0; i < 120; i++) {
			int x = random.nextInt(imageWidth);
			int y = random.nextInt(imageHeight);
			int xl = random.nextInt(12);
			int yl = random.nextInt(12);
			g.drawLine(x, y, x + xl, y + yl);
		}
		char[] digits = {'0','1','2','3','4','5','6','7','8','9'};
		char[] letters = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
				'a','b','c','d','e','f','j','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
		int length = minLength+random.nextInt(maxLength-minLength+1);
		int digitsLen = digits.length;
		int lettersLen = letters.length;
		StringBuilder builder = new StringBuilder();
		char c = 0;
		for (int i = 0; i < length; i++) {
			switch (random.nextInt(2)) {
			case 0:
				c=digits[random.nextInt(digitsLen)];
				break;
			case 1:
				c=letters[random.nextInt(lettersLen)];
				break;
			default:
				break;
			}
			g.setColor(getRandomColor(random, 30, 90));
			g.drawString(String.valueOf(c), 13 * i + 6, 16);
			builder.append(c);
		}
		g.dispose();
		return builder.toString();
	}	
	
	private Color getRandomColor(Random random, int start,int end){
	    if(start>255) start=255;
	    if(end>255) end=255;
	    int d= end-start;
	    int r=start+random.nextInt(d);
	    int g=start+random.nextInt(d);
	    int b=start+random.nextInt(d);
	    return new Color(r,g,b);
	}

	public void destroy() {
		cache.clear();
		cache = null;
	}
	
}
