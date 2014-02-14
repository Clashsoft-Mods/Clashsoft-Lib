package clashsoft.cslib.minecraft.update;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import clashsoft.cslib.util.CSLog;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;

/**
 * The class InstallUpdateThread.
 * 
 * @author Clashsoft
 */
public class InstallUpdateThread extends Thread
{
	/** The mod update to install. */
	private ModUpdate		update;
	
	/** The player used for chat message notifications. */
	private EntityPlayer	player;
	
	/**
	 * Instantiates a new install update thread.
	 * 
	 * @param update
	 *            the update
	 * @param player
	 *            the player
	 */
	public InstallUpdateThread(ModUpdate update, EntityPlayer player)
	{
		this.update = update;
		this.player = player;
	}
	
	/**
	 * Installs the mod update by downloading the file from {@link ModUpdate#updateUrl} and deleting
	 * old mod versions.
	 */
	@Override
	public void run()
	{
		String modName = this.update.modName;
		String newVersion = this.update.newVersion;
		String mod = this.update.getName();
		
		this.player.addChatMessage(new ChatComponentTranslation("update.install", modName, newVersion));
		
		File file;
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
		{
			file = Minecraft.getMinecraft().mcDataDir;
		}
		else
		{
			file = new File(MinecraftServer.getServer().getFolderName());
		}
		
		File mods = new File(file, "mods");
		
		try
		{
			File output = new File(mods, this.update.getDownloadedFileName());
			
			if (output.exists())
			{
				this.player.addChatMessage(new ChatComponentTranslation("update.install.skipping", mod));
				return;
			}
			
			for (File f : mods.listFiles())
			{
				String fileName = f.getName();
				if (fileName.startsWith(modName))
				{
					this.player.addChatMessage(new ChatComponentTranslation("update.install.oldversion", modName, fileName));
					f.delete();
				}
			}
			
			URL url = new URL(this.update.updateUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			InputStream in = connection.getInputStream();
			FileOutputStream out = new FileOutputStream(output);
			copy(in, out, 1024);
			out.close();
			
			this.player.addChatMessage(new ChatComponentTranslation("update.install.success", mod));
		}
		catch (Exception ex)
		{
			CSLog.error(ex);
			this.player.addChatMessage(new ChatComponentTranslation("update.install.failure", mod, ex.getMessage()));
		}
	}
	
	/**
	 * Copys the data from an input stream to an output stream.
	 * 
	 * @param input
	 *            the input
	 * @param output
	 *            the output
	 * @param bufferSize
	 *            the buffer size
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
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
