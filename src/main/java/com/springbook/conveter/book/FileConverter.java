package com.springbook.conveter.book;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
@Component
public class FileConverter {

	String saveFile(MultipartFile file) {
		String name ="";
		//check file is ready
		if (file != null && !file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				String rootPath = System.getProperty("catalina.home");
				// tao folder
				File dir = new File(rootPath + File.separator + "uploads/image");
				if (!dir.exists()) {
					dir.mkdir();
				}
				// Create file
				name = String.valueOf(new Date().getTime() + ".jpg");
				File serverFile = new File(dir.getAbsoluteFile() + File.separator + name);
				//
				System.out.println("Path of image" + serverFile.getPath());

				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				
			} catch (IOException e) {
				System.out.println("Error upload file" + e.getMessage());
			}

		} else {
			System.out.println("File not found");
		}
		
		return name;

	}
	
	
}
