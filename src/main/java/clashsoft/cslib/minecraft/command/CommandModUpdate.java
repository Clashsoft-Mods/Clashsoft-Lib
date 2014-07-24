package clashsoft.cslib.minecraft.command;

import clashsoft.cslib.minecraft.init.CSLib;
import clashsoft.cslib.minecraft.update.CSUpdate;
import cpw.mods.fml.common.network.internal.FMLNetworkHandler;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;

public class CommandModUpdate extends CommandBase
{
	@Override
	public String getCommandName()
	{
		return "modupdates";
	}
	
	@Override
	public String getCommandUsage(ICommandSender sender)
	{
		return "/modupdates [view|update|updateall]";
	}
	
	@Override
	public void processCommand(ICommandSender sender, String[] args)
	{
		if (args.length >= 1)
		{
			if ("update".equals(args[0]))
			{
				if (args.length >= 2)
				{
					String mod = args[1];
					CSUpdate.update((EntityPlayer) sender, mod);
				}
			}
			else if ("updateall".equals(args[0]))
			{
				CSUpdate.updateAll((EntityPlayer) sender);
			}
			else if ("view".equals(args[0]))
			{
				FMLNetworkHandler.openGui((EntityPlayer) sender, CSLib.instance, 0, sender.getEntityWorld(), 0, 0, 0);
			}
		}
		else
		{
			FMLNetworkHandler.openGui((EntityPlayer) sender, CSLib.instance, 0, sender.getEntityWorld(), 0, 0, 0);
		}
	}
	
	@Override
	public int compareTo(Object o)
	{
		return 0;
	}
}
