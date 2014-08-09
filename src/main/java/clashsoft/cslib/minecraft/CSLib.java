package clashsoft.cslib.minecraft;

import clashsoft.cslib.config.CSConfig;
import clashsoft.cslib.logging.CSLog;
import clashsoft.cslib.minecraft.cape.Capes;
import clashsoft.cslib.minecraft.command.CSCommand;
import clashsoft.cslib.minecraft.command.CommandModUpdate;
import clashsoft.cslib.minecraft.common.CSLibProxy;
import clashsoft.cslib.minecraft.crafting.loader.FurnaceRecipeLoader;
import clashsoft.cslib.minecraft.init.ClashsoftMod;
import clashsoft.cslib.minecraft.item.CSItems;
import clashsoft.cslib.minecraft.network.CSLibNetHandler;
import clashsoft.cslib.minecraft.update.CSUpdate;
import clashsoft.cslib.minecraft.update.reader.SimpleUpdateReader;
import clashsoft.cslib.minecraft.update.updater.ModUpdater;
import clashsoft.cslib.minecraft.util.Log4JLogger;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import net.minecraft.command.ICommand;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;

@Mod(modid = CSLib.MODID, name = CSLib.NAME, version = CSLib.VERSION)
public class CSLib extends ClashsoftMod
{
	public static final String	MODID				= "cslib";
	public static final String	NAME				= "Clashsoft Lib";
	public static final String	ACRONYM				= "cslib";
	public static final String	VERSION				= "1.7.10-2.4.2";
	public static final String	DEPENDENCY			= "required-after:" + MODID;
	
	static
	{
		CSLog.logger = new Log4JLogger();
	}
	
	@Instance(MODID)
	public static CSLib			instance;
	
	public static CSLibProxy	proxy				= createProxy("clashsoft.cslib.minecraft.client.CSLibClientProxy", "clashsoft.cslib.minecraft.common.CSLibProxy");
	
	public static boolean		printUpdateNotes	= false;
	public static boolean		updateCheck			= true;
	public static boolean		autoUpdate			= true;
	public static boolean		enableMOTD			= true;
	
	public CSLib()
	{
		super(proxy, MODID, NAME, ACRONYM, VERSION);
		
		this.hasConfig = true;
		this.netHandler = new CSLibNetHandler();
		this.eventHandler = this;
		this.url = "https://github.com/Clashsoft/CSLib-Minecraft/wiki/";
	}
	
	public static CSLibNetHandler getNetHandler()
	{
		return (CSLibNetHandler) instance.netHandler;
	}
	
	@Override
	public void readConfig()
	{
		printUpdateNotes = CSConfig.getBool("updates", "Print Update Notes", printUpdateNotes);
		updateCheck = CSConfig.getBool("updates", "Update Check", "Disables update checks for ALL mods", updateCheck);
		autoUpdate = CSConfig.getBool("updates", "Auto Updates", "Disables automatic updates", autoUpdate);
		enableMOTD = CSConfig.getBool("updates", "Enable MOTD", enableMOTD);
	}
	
	@Override
	public void updateCheck()
	{
		final String url = "https://raw.githubusercontent.com/Clashsoft/Clashsoft-Lib/master/version.txt";
		CSUpdate.updateCheck(new ModUpdater(NAME, ACRONYM, VERSION, url, SimpleUpdateReader.instance));
	}
	
	@Override
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		super.preInit(event);
	}
	
	@Override
	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		super.init(event);
		
		FurnaceRecipeLoader.instance.load();
	}
	
	@Override
	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		super.postInit(event);
		
		CSItems.replaceRecipes();
	}
	
	@EventHandler
	public void serverStarted(FMLServerStartedEvent event)
	{
		ServerCommandManager manager = (ServerCommandManager) MinecraftServer.getServer().getCommandManager();
		if (CSCommand.commands != null)
		{
			for (ICommand cmd : CSCommand.commands)
			{
				manager.registerCommand(cmd);
			}
		}
		
		manager.registerCommand(new CommandModUpdate());
	}
	
	@SubscribeEvent
	public void playerJoined(EntityJoinWorldEvent event)
	{
		if (event.entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.entity;
			Capes.updateCape(player);
			
			if (event.world.isRemote)
			{
				CSUpdate.notifyAll(player);
			}
		}
	}
}
