package clashsoft.cslib.minecraft.update.reader;

import java.util.ArrayList;
import java.util.List;

import clashsoft.cslib.minecraft.update.CSUpdate;
import clashsoft.cslib.minecraft.update.Update;
import clashsoft.cslib.minecraft.update.updater.IUpdater;

import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

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
		
		IChatComponent motd = null;
		
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
			else if (line.startsWith("motd="))
			{
				String s = line.substring(5);
				if (motd == null)
					motd = new ChatComponentText(s);
				else
					motd.appendText(s);
			}
			else if (line.startsWith("motdcolor="))
			{
				EnumChatFormatting color = EnumChatFormatting.getValueByName(line.substring(10));
				if (motd == null)
					motd = new ChatComponentText("");
				motd.getChatStyle().setColor(color);
			}
			else if (line.startsWith("motdjson="))
			{
				String s = line.substring(9);
				try
				{
					motd = IChatComponent.Serializer.func_150699_a(s);
				}
				catch (Exception ex)
				{
				}
			}
			else
			{
				notes.add(line);
			}
		}
		
		if (motd != null)
		{
			updater.setMOTD(motd);
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
