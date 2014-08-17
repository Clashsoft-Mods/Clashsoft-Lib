package clashsoft.cslib.minecraft.update.updater;

import java.util.Arrays;
import java.util.List;

import clashsoft.cslib.io.CSWeb;
import clashsoft.cslib.minecraft.update.Update;
import clashsoft.cslib.minecraft.update.reader.FileUpdateReader;
import clashsoft.cslib.minecraft.update.reader.IUpdateReader;
import clashsoft.cslib.util.CSString;

import net.minecraft.util.IChatComponent;

public class URLUpdater implements IUpdater
{
	protected String			url;
	protected String[]			updateFile;
	public IUpdateReader		reader;
	
	protected IChatComponent	motd;
	
	public URLUpdater(String url)
	{
		this(url, FileUpdateReader.instance);
	}
	
	public URLUpdater(String url, IUpdateReader reader)
	{
		this.url = url;
		this.reader = reader;
	}
	
	@Override
	public String getName()
	{
		return null;
	}
	
	@Override
	public String getVersion()
	{
		return null;
	}
	
	@Override
	public String getURL()
	{
		return this.url;
	}
	
	@Override
	public IChatComponent getMOTD()
	{
		return this.motd;
	}
	
	@Override
	public URLUpdater setMOTD(IChatComponent motd)
	{
		this.motd = motd;
		return this;
	}
	
	@Override
	public boolean keyEquals(String key)
	{
		return true;
	}
	
	@Override
	public Update newUpdate(String newVersion, List<String> notes, String updateURL)
	{
		return new Update(this, null, null, newVersion, notes, updateURL);
	}
	
	@Override
	public void checkUpdate()
	{
		this.getUpdateReader().readFile(this, this.getUpdateFile());
	}
	
	@Override
	public boolean reCheck()
	{
		return false;
	}
	
	@Override
	public IUpdater setUpdateFile(String[] updateFile)
	{
		this.updateFile = updateFile;
		return this;
	}
	
	@Override
	public String[] getUpdateFile()
	{
		if (this.updateFile == null)
		{
			this.updateFile = CSString.lineArray(CSWeb.tryReadWebsite(this.getURL()));
		}
		return this.updateFile;
	}
	
	@Override
	public IUpdateReader getUpdateReader()
	{
		return this.reader;
	}
	
	@Override
	public String toString()
	{
		return "URLUpdater [url=" + this.getURL() + "; file=" + Arrays.toString(this.getUpdateFile()) + "]";
	}
}
