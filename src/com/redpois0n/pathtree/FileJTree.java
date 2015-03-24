package com.redpois0n.pathtree;

import java.awt.Desktop;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.tree.DefaultMutableTreeNode;

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
		expandAll();
		setRootVisible(false);
	}
	
	public void update(File dir, DefaultMutableTreeNode root) {		
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
				return;
			}

			DefaultMutableTreeNode node = root != null ? root : (DefaultMutableTreeNode) getNodeFromPath(dir.getAbsolutePath());
			PathTreeNode insertedNode = new PathTreeNode(name, d.isDirectory() ? FileIconUtils.getFolderIcon() : FileIconUtils.getFileIcon(d));
			getPathModel().insertNodeInto(insertedNode, node, node.getChildCount());		
			
			if (d.isDirectory()) {
				getPathModel().insertNodeInto(new PlaceHolderTreeNode(), insertedNode, 0);
			}
		}				
	}

}
