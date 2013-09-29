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

public class ModUpdate
{	
	public String	modName;
	public String	modInitials;
	public String	version;
	public String	newVersion;
	public String	updateNotes;
	public String	updateUrl;
	
	public ModUpdate(String modName, String modInitials, String version, String newVersion, String updateNotes, String updateUrl)
	{
		this.modName = modName;
		this.modInitials = modInitials;
		this.version = version;
		this.newVersion = newVersion;
		this.updateNotes = updateNotes;
		this.updateUrl = updateUrl;
	}
	
	public boolean isValid()
	{
		return !version.equals(newVersion);
	}
	
	public void install(final EntityPlayer player)
	{
		if (!isValid() || updateUrl.isEmpty())
			return;
		
		new Thread(new Runnable()
		{
			public void run()
			{
				File file;
				if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
					file = Minecraft.getMinecraft().mcDataDir;
				else
					file = new File(MinecraftServer.getServer().getFolderName());
				
				File mods = new File(file, "mods");
				
				File output = new File(mods, updateUrl.substring(updateUrl.lastIndexOf('/')).replace('+', ' '));
				
				if (output.exists())
				{
					player.addChatMessage(EnumChatFormatting.GREEN + "Latest Mod version found - Skipping download.");
					return;
				}
				
				try
				{
					for (File f : mods.listFiles())
					{
						if (f.getName().startsWith(modName))
						{
							player.addChatMessage(EnumChatFormatting.YELLOW + "Old Mod version detected (" + f.getName() + "). Deleting.");
							f.delete();
						}
					}
					
					URL url = new URL(updateUrl);
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
		}).start();
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
