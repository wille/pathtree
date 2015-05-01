package com.redpois0n.pathtree;

import iconlib.FileIconUtils;

import java.awt.Desktop;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class FileJTree extends PathJTree {
	
	private FileFilter filter;

	public FileJTree() {
		this(true);
	}
	
	public FileJTree(boolean openFile) {
		setDelimiter(File.separator);
		
		if (openFile) {
			addFileClickListener(new NodeClickListener() {
				@Override
				public void itemSelected(PathTreeNode node, String path) {
					File file = new File(path);

					try {
						Desktop.getDesktop().open(file);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
		
		addFolderClickListener(new NodeClickListener() {
			@Override
			public void itemSelected(PathTreeNode node, String path) {
				File file = new File(path);

				update(file, null);
			}
		});
	}

	public void add(String dir, ImageIcon icon) {
		setRootVisible(true);
		PathTreeNode root = new FolderTreeNode(dir, icon);
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
				if (filter != null && !filter.allow(file)) {
					continue;
				}
				
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
			
			PathTreeNode node;
			
			if (d.isDirectory()) {
				node = new FolderTreeNode(name, icon);
				if (d.listFiles().length > 0) {
					insertFakeNode(node);
				}
			} else {
				node = new FileTreeNode(name, icon);
			}
			
			getPathModel().insertNodeInto(node, parent, parent.getChildCount());		
		}				
	}

	public FileFilter getFilter() {
		return filter;
	}

	public void setFilter(FileFilter filter) {
		this.filter = filter;
	}

}
