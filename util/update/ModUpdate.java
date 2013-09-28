package clashsoft.clashsoftapi.util.update;

public class ModUpdate
{
	public String version;
	public String newVersion;
	public String updateNotes;
	
	public ModUpdate(String version, String newVersion, String updateNotes)
	{
		this.version = version;
		this.newVersion = newVersion;
		this.updateNotes = updateNotes;
	}
	
	public boolean isValid()
	{
		return version != newVersion;
	}
}
