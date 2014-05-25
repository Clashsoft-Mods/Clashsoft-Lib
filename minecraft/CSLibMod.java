package clashsoft.cslib.minecraft;

import clashsoft.cslib.minecraft.init.CSLib;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;

@Mod(modid = CSLib.MODID, name = CSLib.NAME, version = CSLib.VERSION)
public class CSLibMod
{
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		CSLib.libPreInit(event);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		CSLib.libInit(event);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		CSLib.libPostInit(event);
	}
	
	@EventHandler
	public void serverStarted(FMLServerStartedEvent event)
	{
		CSLib.libServerStart(event);
	}
}
