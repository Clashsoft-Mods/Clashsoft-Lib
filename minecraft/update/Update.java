package clashsoft.cslib.minecraft.update;

import java.util.List;

import clashsoft.cslib.minecraft.lang.I18n;
import clashsoft.cslib.minecraft.update.updater.IUpdater;

import net.minecraft.entity.player.EntityPlayer;

/**
 * The class Update.
 * <p>
 * This class stores mod update data and installs updates.
 * 
 * @author Clashsoft
 */
public class Update
{
	private static final int	INVALID		= -2;
	private static final int	NOT_CHECKED	= -999;
	
	public final IUpdater updater;
	
	public String				modName;
	
	protected String			version;
	protected String			newVersion;
	
	protected List<String>		updateNotes;
	protected String			url;
	
	protected int				compare		= NOT_CHECKED;
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
	
	public int validate()
	{
		if (this.compare == NOT_CHECKED)
		{
			if (this.version != null && this.newVersion != null && this.newVersion.startsWith(CSUpdate.CURRENT_VERSION))
			{
				this.compare = CSUpdate.compareVersion(this.version, this.newVersion);
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
		return this.modName == null ? "[unknown]" : this.modName;
	}
	
	public String getVersion()
	{
		return this.version == null ? "[unknown]" : this.version;
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
	
	public String getUpdateURL()
	{
		return this.url == null ? "[none]" : this.url;
	}
	
	public List<String> getUpdateNotes()
	{
		return this.updateNotes;
	}
	
	public boolean isValid()
	{
		int compare = this.validate();
		return compare != INVALID && compare < 0;
	}
	
	public boolean isCurrent()
	{
		return this.validate() == 0;
	}
	
	public boolean hasDownload()
	{
		return this.url != null && !this.url.isEmpty();
	}
	
	private String getFileType()
	{
		if (this.url == null)
		{
			return "zip";
		}
		int i = this.url.lastIndexOf('.');
		return i == -1 ? "zip" : this.url.substring(i + 1);
	}
	
	public String getDownloadedFileName()
	{
		return String.format("%s %s.%s", this.modName, this.newVersion, this.getFileType());
	}
	
	public String getStatus()
	{
		if (!this.isValid())
		{
			return I18n.getString("update.invalid");
		}
		else if (this.installStatus == -1)
		{
			return I18n.getString("update.list.install.error");
		}
		else if (this.installStatus == 0)
		{
			return I18n.getString("update.list.install.notstarted");
		}
		else if (this.installStatus == 1)
		{
			return I18n.getString("update.list.install.installing");
		}
		else if (this.installStatus == 2)
		{
			return I18n.getString("update.list.install.installed");
		}
		return "Unknown";
	}
	
	public void install(EntityPlayer player)
	{
		new InstallUpdateThread(this, player).start();
	}
}
