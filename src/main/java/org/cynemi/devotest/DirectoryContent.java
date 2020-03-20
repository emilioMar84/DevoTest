package org.cynemi.devotest;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

/**
 * Manage the content of a directory
 * @author em_m0
 *
 */
public class DirectoryContent 
{
	
	public class DirectoryContentItem
	{
		public String fileName;
		public String conntent;
		
		DirectoryContentItem(String fileName, String content)
		{
			this.fileName = fileName;
			this.conntent = content;
		}
	}
	
	private Map<String, Boolean> containFile = new HashMap<>();
	private List<DirectoryContentItem> contentList = new ArrayList<>();
	private List<DirectoryContentItem> newContentList = new ArrayList<>();
	
	private String dir;
	
	public List<DirectoryContentItem> getContentList()
	{
		return contentList;
	}
	
	public List<DirectoryContentItem> getNewContentList()
	{
		return newContentList;
	}
	
	public DirectoryContent(String dir) 
	{
		this.dir = dir;
		getDirContent(dir);
	}
	
	public void refresh()
	{
		getDirContent(dir);
	}

	private void getDirContent(String dir)
	{
		File folder = new File(dir);
		File[] listOfFiles = folder.listFiles(new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {
				// TODO Auto-generated method stub
				return pathname.getName().contains(".txt");
			}
		});

		newContentList.clear();
		for (int i = 0; i < listOfFiles.length; i++) 
		{
		  File file = listOfFiles[i];
		  if (file.isFile() && file.getName().endsWith(".txt")) 
		  {
			try 
			{
				Boolean checkContainFile = containFile.get(file.getName());
				if (checkContainFile == null)
				{
					String content = FileUtils.readFileToString(file, (Charset)null);
					DirectoryContentItem directoryContentItem = new DirectoryContentItem(file.getName(), content);
					contentList.add(directoryContentItem);
					newContentList.add(directoryContentItem);
					
					containFile.put(file.getName(), true);
				}
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		  } 
		}
	}
}
