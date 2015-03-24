package com.redpois0n.pathtree;

import java.awt.Desktop;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class FileJTree extends PathJTree {

	public FileJTree() {
		setDelimiter(File.separator);
		addPathListener(new PathListener() {
			@Override
			public void pathSelected(String path) {
				File file = new File(path);
				
				if (file.isDirectory()) {
					update(file, null);
				} else {
					try {
						Desktop.getDesktop().open(file);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
	
	public void add(String dir, ImageIcon icon) {
		setRootVisible(true);
		PathTreeNode root = new PathTreeNode(dir, icon);
		addRoot(root);
		update(new File(dir), root);
		expandRow(0);
		setRootVisible(false);
	}
	
	public void update(File dir, PathTreeNode root) {		
		List<File> dirs = new ArrayList<File>();
		List<File> files = new ArrayList<File>();
		
		if (dir.isDirectory()) {
			for (File file : dir.listFiles()) {
				if (file.isDirectory()) {					
					dirs.add(file);
				} else {
					files.add(file);
				}
			}
		}
		
		dirs.addAll(files);
		
		for (File d : dirs) {
			String name = d.getName();
			if (exists(dir.getAbsolutePath() + File.separator + name)) {
				break;
			}

			PathTreeNode parent;
			
			if (root == null) {
				parent = (PathTreeNode) getNodeFromPath(dir.getAbsolutePath());
			} else {
				parent = root;
			}
			
			Icon icon = FileIconUtils.getIconFromFile(d);
			
			PathTreeNode node = new PathTreeNode(name, icon);
			getPathModel().insertNodeInto(node, parent, parent.getChildCount());		
			
			if (d.isDirectory()) {
				insertFakeNode(node);
			}
		}				
	}

}
