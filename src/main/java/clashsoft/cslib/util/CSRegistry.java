package clashsoft.cslib.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import net.minecraft.util.RegistryNamespaced;

import org.apache.commons.io.FileUtils;

public class CSRegistry extends RegistryNamespaced
{
	protected BitSet	availabilityMap	= new BitSet();
	
	public int getFreeID()
	{
		return this.availabilityMap.nextClearBit(0);
	}
	
	public int getIDByName(String name)
	{
		int i = this.getIDForObject(this.getObject(name));
		if (i == -1)
		{
			i = this.getFreeID();
		}
		return i;
	}
	
	@Override
	public void register(int id, Object name, Object object)
	{
		super.register(id, name, object);
		this.availabilityMap.set(id);
	}
	
	public void writeToFile(File file)
	{
		List<String> lines = new ArrayList();
		for (Object o : this)
		{
			int id = this.getIDForObject(o);
			Object name = this.getNameForObject(o);
			lines.add(id + "=" + name);
		}
		
		try
		{
			FileUtils.writeLines(file, lines);
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void readFromFile(File file)
	{
		try
		{
			List<String> lines = FileUtils.readLines(file);
			
			for (String s : lines)
			{
				if (s.startsWith("#"))
				{
					continue;
				}
				int i = s.indexOf('=');
				if (i != -1)
				{
					int id = Integer.parseInt(s.substring(0, i));
					String name = s.substring(i + 1);
					this.register(id, name, null);
				}
			}
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}
}
