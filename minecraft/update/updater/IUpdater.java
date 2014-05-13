package clashsoft.cslib.minecraft.update.updater;

import java.util.List;

import clashsoft.cslib.minecraft.update.Update;
import clashsoft.cslib.minecraft.update.reader.IUpdateReader;

public interface IUpdater
{
	public String getName();
	
	public String getVersion();
	
	public String getURL();
	
	public IUpdater setUpdateFile(String[] updateFile);
	
	public String[] getUpdateFile();
	
	public IUpdateReader getUpdateReader();
	
	public void checkUpdate();
	
	public boolean keyMatches(String key);
	
	public Update newUpdate(String newVersion, List<String> notes, String downloadURL);
	
	public boolean reCheck();
}
