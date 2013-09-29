package clashsoft.clashsoftapi.util.update;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumChatFormatting;

public class InstallUpdateThread extends Thread
{
	private ModUpdate update;
	private EntityPlayer player;
	
	public InstallUpdateThread(ModUpdate update, EntityPlayer player)
	{
		this.update = update;
		this.player = player;
	}
	
	@Override
	public void run()
	{
		player.addChatMessage("Installing " + update.modName + " version " + update.newVersion);
		
		File file;
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
			file = Minecraft.getMinecraft().mcDataDir;
		else
			file = new File(MinecraftServer.getServer().getFolderName());
		
		File mods = new File(file, "mods");
		
		try
		{
			File output = new File(mods, update.updateUrl.substring(update.updateUrl.lastIndexOf('/')).replace('+', ' '));
			
			if (output.exists())
			{
				player.addChatMessage(EnumChatFormatting.GREEN + "Latest Mod version found - Skipping download.");
				return;
			}
			
			for (File f : mods.listFiles())
			{
				if (f.getName().startsWith(update.modName))
				{
					player.addChatMessage(EnumChatFormatting.YELLOW + "Old Mod version detected (" + f.getName() + "). Deleting.");
					f.delete();
				}
			}
			
			URL url = new URL(update.updateUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			InputStream in = connection.getInputStream();
			FileOutputStream out = new FileOutputStream(output);
			copy(in, out, 1024);
			out.close();
			
			player.addChatMessage(EnumChatFormatting.GREEN + "Update installed. Restart the game to apply changes.");
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			player.addChatMessage(EnumChatFormatting.RED + "Error while installing update: " + ex.getMessage());
		}
	}
	
	private static void copy(InputStream input, OutputStream output, int bufferSize) throws IOException
	{
		byte[] buf = new byte[bufferSize];
		int n = input.read(buf);
		while (n >= 0)
		{
			output.write(buf, 0, n);
			n = input.read(buf);
		}
		output.flush();
	}
}
