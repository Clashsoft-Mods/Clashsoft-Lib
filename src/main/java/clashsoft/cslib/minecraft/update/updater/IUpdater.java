package clashsoft.cslib.minecraft.update.updater;

import java.util.List;

import clashsoft.cslib.minecraft.update.Update;
import clashsoft.cslib.minecraft.update.reader.IUpdateReader;

import net.minecraft.util.IChatComponent;

public interface IUpdater
{
	public String getName();
	
	public String getVersion();
	
	public String getURL();
	
	public IChatComponent getMOTD();
	
	public IUpdater setMOTD(IChatComponent motd);
	
	public String[] getUpdateFile();
	
	public IUpdater setUpdateFile(String[] updateFile);
	
	public IUpdateReader getUpdateReader();
	
	public void checkUpdate();
	
	public boolean reCheck();
	
	public boolean keyEquals(String key);
	
	public Update newUpdate(String newVersion, List<String> notes, String downloadURL);
}
