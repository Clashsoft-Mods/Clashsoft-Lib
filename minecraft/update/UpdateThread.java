package clashsoft.cslib.minecraft.update;

import net.minecraft.entity.player.EntityPlayer;

final class UpdateThread extends Thread
{
	private EntityPlayer	player;
	private String			modInitials;
	private String[]		updateFile;
	private String			version;
	private String			modName;
	
	public UpdateThread(EntityPlayer player, String modInitials, String[] updateFile, String version, String modName)
	{
		this.player = player;
		this.modInitials = modInitials;
		this.updateFile = updateFile;
		this.version = version;
		this.modName = modName;
	}
	
	@Override
	public void run()
	{
		ModUpdate update = CSUpdate.checkForUpdate(this.modName, this.modInitials, this.version, this.updateFile);
		CSUpdate.notifyUpdate(this.player, this.modName, update);
	}
}