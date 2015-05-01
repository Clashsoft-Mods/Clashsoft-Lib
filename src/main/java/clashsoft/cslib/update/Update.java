package clashsoft.cslib.update;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;
import clashsoft.cslib.update.updater.IUpdater;

/**
 * The class Update.
 * <p>
 * This class stores mod update data and installs updates.
 * 
 * @author Clashsoft
 */
public class Update
{
	private static final int	NOT_CHECKED			= -999;
	private static final int	INVALID				= -998;
	private static final int	OTHER_MC_VERSION	= -997;
	
	public final IUpdater		updater;
	
	public String				modName;
	
	protected String			version;
	protected String			newVersion;
	
	protected IChatComponent	notification;
	protected List<String>		updateNotes;
	protected String			url;
	
	protected int				compare				= NOT_CHECKED;
	protected int				installStatus;
	
	public Update(IUpdater updater, String modName, String version, String newVersion, List<String> updateNotes, String updateUrl)
	{
		this.updater = updater;
		this.modName = modName;
		this.version = version;
		this.newVersion = newVersion;
		this.updateNotes = updateNotes;
		this.url = updateUrl;
	}
	
	public void setMod(String name, String version)
	{
		this.modName = name;
		this.version = version;
	}
	
	public void setNotification(IChatComponent notification)
	{
		this.notification = notification;
	}
	
	public int validate()
	{
		if (this.compare == NOT_CHECKED)
		{
			if (this.version != null && this.newVersion != null)
			{
				String[] split = CSUpdate.splitVersion(this.version);
				String[] newSplit = CSUpdate.splitVersion(this.newVersion);
				String mc = null;
				String newMC = null;
				String version;
				String newVersion;
				
				if (split.length == 2)
				{
					mc = split[0];
					version = split[1];
				}
				else
				{
					version = split[0];
				}
				
				if (newSplit.length == 2)
				{
					newMC = newSplit[0];
					newVersion = newSplit[1];
				}
				else
				{
					newVersion = newSplit[0];
				}
				
				if (mc != null && newMC != null && !mc.equals(newMC))
				{
					this.compare = OTHER_MC_VERSION;
					return this.compare;
				}
				
				this.compare = CSUpdate.compareVersion(version, newVersion);
			}
			else
			{
				this.compare = INVALID;
			}
		}
		return this.compare;
	}
	
	public String getModName()
	{
		return this.modName == null ? "[Unknown]" : this.modName;
	}
	
	public String getVersion()
	{
		return this.version == null ? "[Unknown]" : this.version;
	}
	
	public String getNewVersion()
	{
		return this.newVersion == null ? this.getVersion() : this.newVersion;
	}
	
	public String getName()
	{
		String modName = this.modName == null ? "Unknown Mod" : this.modName;
		return modName + " " + this.getNewVersion();
	}
	
	public String getVersionChanges()
	{
		if (this.compare == 0)
			return this.getVersion();
		return this.getVersion() + " -> " + this.getNewVersion();
	}
	
	public IChatComponent getNotification()
	{
		if (this.notification == null)
		{
			this.notification = new ChatComponentTranslation("update.notification", this.getModName(), this.getNewVersion());
		}
		return this.notification;
	}
	
	public String getUpdateURL()
	{
		return this.url == null ? "[No URL]" : this.url;
	}
	
	public List<String> getUpdateNotes()
	{
		return this.updateNotes;
	}
	
	public boolean isValid()
	{
		int compare = this.validate();
		return compare == -1;
	}
	
	public boolean isCurrent()
	{
		return this.validate() == 0;
	}
	
	public boolean hasDownload()
	{
		return this.url != null && !this.url.isEmpty();
	}
	
	public String getDownloadedFileName()
	{
		if (this.url == null)
			return null;
		
		int i = this.url.lastIndexOf('/');
		if (i == -1)
			return null;
		
		return this.url.substring(i + 1, this.url.length());
	}
	
	public boolean canInstall()
	{
		return this.isValid() && this.installStatus == 0;
	}
	
	public String getStatus()
	{
		this.validate();
		if (this.compare == INVALID)
			return "update.invalid";
		else if (this.compare == OTHER_MC_VERSION)
			return "update.other_mc_version";
		else if (this.compare == 1)
			return "update.older_version";
		else if (this.compare == 0)
			return "update.current_version";
		else if (this.installStatus == -1)
			return "update.list.install.error";
		else if (this.installStatus == 0)
			return "update.list.install.notstarted";
		else if (this.installStatus == 1)
			return "update.list.install.installing";
		else if (this.installStatus == 2)
			return "update.list.install.installed";
		return "Unknown";
	}
	
	public void install(EntityPlayer player)
	{
		if (this.canInstall())
		{
			new InstallUpdateThread(this, player).start();
		}
	}
}
