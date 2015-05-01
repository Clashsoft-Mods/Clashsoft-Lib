package clashsoft.cslib.update.updater;

import java.util.Arrays;
import java.util.List;

import clashsoft.cslib.update.Update;
import clashsoft.cslib.update.reader.FileUpdateReader;
import clashsoft.cslib.update.reader.IUpdateReader;

public class ModUpdater extends URLUpdater
{
	protected String	name;
	protected String	acronym;
	protected String	version;
	
	public ModUpdater(String name, String acronym, String version, String url)
	{
		this(name, acronym, version, url, FileUpdateReader.instance);
	}
	
	public ModUpdater(String name, String acronym, String version, String url, IUpdateReader reader)
	{
		super(url, reader);
		this.name = name;
		this.acronym = acronym;
		this.version = version;
		this.url = url;
	}
	
	@Override
	public String getName()
	{
		return this.name;
	}
	
	@Override
	public String getVersion()
	{
		return this.version;
	}
	
	@Override
	public boolean keyEquals(String key)
	{
		if (this.name != null && this.name.equals(key))
			return true;
		if (this.acronym != null && this.acronym.equals(key))
			return true;
		return false;
	}
	
	@Override
	public Update newUpdate(String newVersion, List<String> notes, String updateURL)
	{
		return new Update(this, this.name, this.version, newVersion, notes, updateURL);
	}
	
	@Override
	public String toString()
	{
		return "ModUpdater [name=" + this.name + "; acronym=" + this.acronym + "; version=" + this.version + "; url=" + this.url + "; updateFile=" + Arrays.toString(this.updateFile) + "]";
	}
}
