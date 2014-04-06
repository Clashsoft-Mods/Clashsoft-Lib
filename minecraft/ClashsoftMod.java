package clashsoft.cslib.minecraft;

import java.util.Arrays;
import java.util.List;

import clashsoft.cslib.minecraft.network.CSNetHandler;
import clashsoft.cslib.minecraft.update.CSUpdate;

public abstract class ClashsoftMod<N extends CSNetHandler> extends BaseMod<N>
{
	public static final List<String> AUTHORS_CLASHSOFT = Arrays.asList("Clashsoft");
	
	public ClashsoftMod(String modID, String name, String version)
	{
		super(modID, name, version);
		this.authors = AUTHORS_CLASHSOFT;
	}
	
	public ClashsoftMod(String modID, String name, String acronym, String version)
	{
		super(modID, name, acronym, version);
		this.authors = AUTHORS_CLASHSOFT;
	}
	
	@Override
	public void updateCheck()
	{
		CSUpdate.updateCheckCS(this.name, this.acronym, this.version);
	}
}
