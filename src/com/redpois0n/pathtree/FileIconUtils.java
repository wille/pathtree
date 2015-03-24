package com.redpois0n.pathtree;

import java.io.File;
import java.util.Random;

import javax.swing.Icon;
import javax.swing.filechooser.FileSystemView;

public class FileIconUtils {
	
	public static Icon getIconFromFile(File file) {
		return FileSystemView.getFileSystemView().getSystemIcon(file);
	}	
	
	public static Icon getFolderIcon() {
		File temp = new File(System.getProperty("java.io.tmpdir") + File.separator + "icon");
		temp.mkdirs();
		
		Icon icon = FileSystemView.getFileSystemView().getSystemIcon(temp);
		
		temp.delete();
		
		return icon;
	}

	public static Icon getIconFromExtension(String f) {
		try {

			File temp = File.createTempFile((new Random()).nextInt() + "", f.substring(f.lastIndexOf("."), f.length()));
			Icon icon = FileSystemView.getFileSystemView().getSystemIcon(temp);
			temp.delete();

			return icon;
		} catch (Exception ex) {
			return null;
		}
	}
}
