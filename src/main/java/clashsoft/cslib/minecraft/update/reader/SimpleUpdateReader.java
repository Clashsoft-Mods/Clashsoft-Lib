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
		List<String> notes = new ArrayList(lines.length > 3 ? lines.length - 3 : lines.length);
		
		IChatComponent motd = null;
		IChatComponent notification = null;
		
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
				motd = textComponent(motd, line.substring(5));
			}
			else if (line.startsWith("motd_color="))
			{
				motd = colorComponent(motd, line.substring(11));
			}
			else if (line.startsWith("motd_json="))
			{
				motd = jsonComponent(motd, line.substring(10));
			}
			else if (line.startsWith("version_motd="))
			{
				notification = textComponent(notification, line.substring(13));
			}
			else if (line.startsWith("version_motd_color="))
			{
				notification = colorComponent(notification, line.substring(19));
			}
			else if (line.startsWith("version_motd_json="))
			{
				notification = jsonComponent(notification, line.substring(18));
			}
			else if (!line.isEmpty())
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
			update.setNotification(notification);
			if (modName != null && update.modName == null)
			{
				update.modName = modName;
			}
			CSUpdate.addUpdate(update);
		}
	}
	
	private static IChatComponent textComponent(IChatComponent component, String text)
	{
		if (component == null)
		{
			component = new ChatComponentText(text);
		}
		else
		{
			component.appendText(text);
		}
		return component;
	}
	
	private static IChatComponent jsonComponent(IChatComponent component, String json)
	{
		try
		{
			IChatComponent component1 = IChatComponent.Serializer.func_150699_a(json);
			if (component == null)
			{
				return component1;
			}
			else
			{
				component.appendSibling(component1);
			}
		}
		catch (Exception ex)
		{
		}
		return component;
	}
	
	private static IChatComponent colorComponent(IChatComponent component, String color)
	{
		EnumChatFormatting format = EnumChatFormatting.getValueByName(color);
		if (component == null)
		{
			component = new ChatComponentText("");
		}
		component.getChatStyle().setColor(format);
		return component;
	}
}
