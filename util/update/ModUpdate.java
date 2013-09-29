package clashsoft.clashsoftapi.util.update;

import clashsoft.clashsoftapi.ClashsoftAPI;

import net.minecraft.entity.player.EntityPlayer;

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
		if (ClashsoftAPI.autoUpdate && isValid() && !updateUrl.isEmpty())
		{
			new InstallUpdateThread(this, player).start();
		}
	}
}
