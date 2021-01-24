package kr.ac.ync.game_forum.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class Utils {
	
	public static void deleteFile(String filePath) {
		File deleteFile = new File(filePath);
		if(deleteFile.exists()) {
			deleteFile.delete();
		}
	}
	
	public static String fileSave(String path, MultipartFile file, String read) {
		if (file.isEmpty())
			if (read == null)
				return "empty";
			else
				return read;
		
		byte[] imgByte;
		try {
			imgByte = new byte[file.getInputStream().available()];
			file.getInputStream().read(imgByte);
			
			FileOutputStream stream = new FileOutputStream(path);
			stream.write(imgByte);
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return path;
	}
}
