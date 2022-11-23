package com.springbook.utils;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.file.Files;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet(urlPatterns = "/image/*")
public class LoadImageUtils extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private String imagePath;
	
	@Override
	public void init() throws ServletException {
		imagePath = System.getProperty("catalina.home")+File.separator+"uploads/image";
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//get image
		String requestImage = req.getPathInfo();
		
		if(requestImage==null) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return ;
			
		}
		//decode file
		File image = new File(imagePath, URLDecoder.decode(requestImage,"UTF-8"));
		
		if(!image.exists()) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return ;
		}
		
		//get content type by file name
		String contentType = getServletContext().getMimeType(image.getName());
		
		//check is a image
		if(contentType==null || !contentType.startsWith("image")) {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
			return ;
		}
		
		resp.reset();
		resp.setContentType(contentType);
		resp.setHeader("Content-Length", String.valueOf(image.length()));
		
		//write image content to response
		
		Files.copy(image.toPath(), resp.getOutputStream());
		
	}

}
