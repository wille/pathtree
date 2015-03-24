package com.redpois0n.pathtree;

import java.io.File;
import java.util.Random;

import javax.swing.Icon;
import javax.swing.filechooser.FileSystemView;

public class FileIconUtils {
	

	public static Icon getFolderIcon() {
		return getFileIconFromExtension("", true);
	}
	
	public static Icon getFileIcon(File file) {
		return getFileIconFromExtension(file.getAbsolutePath(), false);
	}

	public static Icon getFileIconFromExtension(String f, boolean dir) {
		try {
			Icon icon;
			
			if (dir) {
				File temp = new File(System.getProperty("java.io.tmpdir") + "icon");
				temp.mkdirs();
				icon = FileSystemView.getFileSystemView().getSystemIcon(temp);
				temp.delete();
			} else {
				File temp = File.createTempFile((new Random()).nextInt() + "", f.substring(f.lastIndexOf("."), f.length()));
				icon = FileSystemView.getFileSystemView().getSystemIcon(temp);
				temp.delete();
			}	
			
			return icon;
		} catch (Exception ex) {
			return null;
		}
	}
}
