package clashsoft.cslib.minecraft.update;

import java.util.Collections;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;

/**
 * The class ModUpdate.
 * <p>
 * This class stores mod update data and installs updates.
 * 
 * @author Clashsoft
 */
public class ModUpdate
{
	/** The mod name. */
	public String		modName;
	
	/** The mod initials. */
	public String		modInitials;
	
	/** The current mod version. */
	public String		version;
	
	/** The new mod version. (May be the same as {@link ModUpdate#version}) */
	public String		newVersion;
	
	/** The update notes. (May be empty) */
	public List<String>		updateNotes;
	
	/** The update url. */
	public String		updateUrl;
	
	protected boolean	valid;
	
	/**
	 * Instantiates a new mod update.
	 * 
	 * @param modName
	 *            the mod name
	 * @param modInitials
	 *            the mod initials
	 * @param version
	 *            the current mod version
	 * @param newVersion
	 *            the new mod version
	 * @param updateNotes
	 *            the update notes
	 * @param updateUrl
	 *            the update url
	 */
	public ModUpdate(String modName, String modInitials, String version, String newVersion, List<String> updateNotes, String updateUrl)
	{
		this.modName = modName;
		this.modInitials = modInitials;
		this.version = version;
		this.newVersion = newVersion;
		this.updateNotes = updateNotes;
		this.updateUrl = updateUrl;
		
		Collections.sort(updateNotes);
		
		this.valid = newVersion.startsWith(CSUpdate.CURRENT_VERSION) && CSUpdate.compareVersion(version, newVersion) == -1;
	}
	
	public String getName()
	{
		return this.modName + " " + this.version;
	}
	
	public String getVersionChanges()
	{
		if (this.isValid())
		{
			return this.version + " -> " + this.newVersion;
		}
		else
		{
			return this.version;
		}
	}
	
	/**
	 * Checks if this is a valid update (New version and same Minecraft version).
	 * 
	 * @return true, if the new version is higher than the current version and the Minecraft version
	 *         is the same, false otherwise.
	 */
	public boolean isValid()
	{
		return this.valid;
	}
	
	public String getDownloadedFileName()
	{
		return this.updateUrl.substring(this.updateUrl.lastIndexOf('/')).replace('+', ' ');
	}
	
	/**
	 * Installs the mod update using {@link InstallUpdateThread}.
	 * 
	 * @param player
	 *            the player used for chat message notifications
	 */
	public void install(EntityPlayer player)
	{
		if (this.isValid() && !this.updateUrl.isEmpty())
		{
			new InstallUpdateThread(this, player).start();
		}
	}
}
