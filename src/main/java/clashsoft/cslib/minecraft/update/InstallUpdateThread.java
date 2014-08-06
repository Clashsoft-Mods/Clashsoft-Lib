package clashsoft.cslib.minecraft.update;

import java.io.File;

import clashsoft.cslib.io.CSWeb;
import clashsoft.cslib.logging.CSLog;
import clashsoft.cslib.util.CSString;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;

/**
 * The class InstallUpdateThread.
 * 
 * @author Clashsoft
 */
public class InstallUpdateThread extends Thread
{
	/** The mod update to install. */
	private Update			update;
	
	/** The player used for chat message notifications. */
	private EntityPlayer	player;
	
	public InstallUpdateThread(Update update, EntityPlayer player)
	{
		this.update = update;
		this.player = player;
	}
	
	/**
	 * Extracts the mod name from the file name. This is used to find existing
	 * mod jars.
	 * <p>
	 * Example: "ClashsoftLib-1.0.0-0.0.0" -> "ClashsoftLib"
	 * 
	 * @param modName
	 * @param fileName
	 * @return
	 */
	private static String getModFileName(String modName, String fileName)
	{
		for (int i = 0; i < fileName.length(); i++)
		{
			char c = fileName.charAt(i);
			if (!CSString.isLetter(c))
			{
				return fileName.substring(0, i);
			}
		}
		return modName;
	}
	
	@Override
	public void run()
	{
		if (this.update.isValid() && this.update.hasDownload())
		{
			this.update.installStatus = 1;
			
			String modName = this.update.getModName();
			String fileName = this.update.getDownloadedFileName();
			String modFileName = getModFileName(modName, fileName);
			String newVersion = this.update.getNewVersion();
			String mod = this.update.getName();
			
			this.player.addChatMessage(new ChatComponentTranslation("update.install", modName, newVersion));
			
			File modsDir = new File("mods");
			
			try
			{
				File output = new File(modsDir, fileName);
				
				if (output.exists())
				{
					this.player.addChatMessage(new ChatComponentTranslation("update.install.skipping", mod));
					this.update.installStatus = 2;
					return;
				}
				
				for (File f : modsDir.listFiles())
				{
					String fileName1 = f.getName();
					if (fileName1.startsWith(modName) || fileName1.startsWith(modFileName))
					{
						this.player.addChatMessage(new ChatComponentTranslation("update.install.oldversion", modName, fileName1));
						f.delete();
					}
				}
				
				CSWeb.download(this.update.getUpdateURL(), output);
				
				this.player.addChatMessage(new ChatComponentTranslation("update.install.success", mod));
				this.update.installStatus = 2;
			}
			catch (Exception ex)
			{
				CSLog.error(ex);
				this.player.addChatMessage(new ChatComponentTranslation("update.install.failure", mod, ex.getMessage()));
				this.update.installStatus = -1;
			}
		}
	}
}
