package clashsoft.cslib.minecraft.update.reader;

import java.util.ArrayList;
import java.util.List;

import clashsoft.cslib.minecraft.update.CSUpdate;
import clashsoft.cslib.minecraft.update.IUpdateReader;
import clashsoft.cslib.minecraft.update.Update;
import clashsoft.cslib.minecraft.update.updater.IUpdater;

public class SimpleUpdateReader implements IUpdateReader
{
	public static final SimpleUpdateReader	instance	= new SimpleUpdateReader();
	
	@Override
	public void readFile(IUpdater updater, String[] lines)
	{
		String modName = null;
		String version = null;
		String url = null;
		List<String> notes = new ArrayList(lines.length);
		
		for (String line : lines)
		{
			if (line.startsWith("version="))
			{
				version = line.substring(8);
			}
			else if (line.startsWith("modname="))
			{
				modName = line.substring(8);
			}
			else if (line.startsWith("url="))
			{
				url = line.substring(4);
			}
			else
			{
				notes.add(line);
			}
		}
		
		if (version != null)
		{
			Update update = updater.newUpdate(version, notes, url);
			if (modName != null && update.modName == null)
			{
				update.modName = modName;
			}
			CSUpdate.addUpdate(update);
		}
	}
}
