package clashsoft.cslib.minecraft;

import clashsoft.cslib.config.CSConfig;
import clashsoft.cslib.logging.CSLog;
import clashsoft.cslib.minecraft.block.CSBlocks;
import clashsoft.cslib.minecraft.block.ore.BlockOre2;
import clashsoft.cslib.minecraft.block.ore.BlockRedstoneOre2;
import clashsoft.cslib.minecraft.block.ore.OreBase;
import clashsoft.cslib.minecraft.cape.Capes;
import clashsoft.cslib.minecraft.command.CSCommand;
import clashsoft.cslib.minecraft.command.CommandModUpdate;
import clashsoft.cslib.minecraft.common.CSLibProxy;
import clashsoft.cslib.minecraft.crafting.loader.FurnaceRecipeLoader;
import clashsoft.cslib.minecraft.entity.CSEntities;
import clashsoft.cslib.minecraft.init.ClashsoftMod;
import clashsoft.cslib.minecraft.item.CSItems;
import clashsoft.cslib.minecraft.item.block.ItemCustomBlock;
import clashsoft.cslib.minecraft.network.CSLibNetHandler;
import clashsoft.cslib.minecraft.update.CSUpdate;
import clashsoft.cslib.minecraft.update.reader.SimpleUpdateReader;
import clashsoft.cslib.minecraft.update.updater.ModUpdater;
import clashsoft.cslib.minecraft.util.Log4JLogger;
import clashsoft.cslib.minecraft.world.gen.CustomCaveGen;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;

import net.minecraft.block.Block;
import net.minecraft.command.ICommand;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.event.terraingen.InitMapGenEvent.EventType;

@Mod(modid = CSLib.MODID, name = CSLib.NAME, version = CSLib.VERSION)
public class CSLib extends ClashsoftMod
{
	public static final String	MODID				= "cslib";
	public static final String	NAME				= "Clashsoft Lib";
	public static final String	ACRONYM				= "cslib";
	public static final String	VERSION				= "1.7.10-2.6.0";
	public static final String	DEPENDENCY			= "required-after:" + MODID;
	
	static
	{
		CSLog.logger = new Log4JLogger();
	}
	
	@Instance(MODID)
	public static CSLib			instance;
	
	public static CSLibProxy	proxy				= createProxy("clashsoft.cslib.minecraft.client.CSLibClientProxy", "clashsoft.cslib.minecraft.common.CSLibProxy");
	
	public static Block			coalOre2			= new BlockOre2(OreBase.TYPE_OVERWORLD).setHardness(1.5F).setResistance(2.5F).setStepSound(Block.soundTypePiston).setBlockName("oreCoal").setBlockTextureName("cslib:coal_overlay");
	public static Block			ironOre2			= new BlockOre2(OreBase.TYPE_OVERWORLD).setHardness(1.5F).setResistance(2.5F).setStepSound(Block.soundTypePiston).setBlockName("oreIron").setBlockTextureName("cslib:iron_overlay");
	public static Block			goldOre2			= new BlockOre2(OreBase.TYPE_OVERWORLD).setHardness(1.5F).setResistance(2.5F).setStepSound(Block.soundTypePiston).setBlockName("oreGold").setBlockTextureName("cslib:gold_overlay");
	public static Block			diamondOre2			= new BlockOre2(OreBase.TYPE_OVERWORLD).setHardness(1.5F).setResistance(2.5F).setStepSound(Block.soundTypePiston).setBlockName("oreDiamond").setBlockTextureName("cslib:diamond_overlay");
	public static Block			emeraldOre2			= new BlockOre2(OreBase.TYPE_OVERWORLD).setHardness(1.5F).setResistance(2.5F).setStepSound(Block.soundTypePiston).setBlockName("oreEmerald").setBlockTextureName("cslib:emerald_overlay");
	public static Block			redstoneOre2		= new BlockRedstoneOre2(OreBase.TYPE_OVERWORLD, false).setHardness(1.5F).setResistance(2.5F).setStepSound(Block.soundTypePiston).setBlockName("oreRedstone").setBlockTextureName("cslib:redstone_overlay");
	public static Block			litRedstoneOre2		= new BlockRedstoneOre2(OreBase.TYPE_OVERWORLD, true).setLightLevel(0.625F).setHardness(1.5F).setResistance(2.5F).setStepSound(Block.soundTypePiston).setBlockName("oreRedstone").setBlockTextureName("cslib:redstone_overlay");
	public static Block			lapisOre2			= new BlockOre2(OreBase.TYPE_OVERWORLD).setHardness(1.5F).setResistance(2.5F).setStepSound(Block.soundTypePiston).setBlockName("oreLapis").setBlockTextureName("cslib:lapis_overlay");
	public static Block			quartzOre2			= new BlockOre2(OreBase.TYPE_NETHER).setHardness(1.5F).setResistance(2.5F).setStepSound(Block.soundTypePiston).setBlockName("netherquartz").setBlockTextureName("cslib:quartz_overlay");
	
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
		this.description = "Clashsoft's Minecraft Library adds many useful Classes and APIs for modders to use.";
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
		
		CSBlocks.replaceBlock(Blocks.coal_ore, coalOre2, new ItemCustomBlock(coalOre2));
		CSBlocks.replaceBlock(Blocks.iron_ore, ironOre2, new ItemCustomBlock(ironOre2));
		CSBlocks.replaceBlock(Blocks.gold_ore, goldOre2, new ItemCustomBlock(goldOre2));
		CSBlocks.replaceBlock(Blocks.diamond_ore, diamondOre2, new ItemCustomBlock(diamondOre2));
		CSBlocks.replaceBlock(Blocks.emerald_ore, emeraldOre2, new ItemCustomBlock(emeraldOre2));
		CSBlocks.replaceBlock(Blocks.redstone_ore, redstoneOre2, new ItemCustomBlock(redstoneOre2));
		CSBlocks.replaceBlock(Blocks.lit_redstone_ore, litRedstoneOre2);
		CSBlocks.replaceBlock(Blocks.lapis_ore, lapisOre2, new ItemCustomBlock(lapisOre2));
		CSBlocks.replaceBlock(Blocks.quartz_ore, quartzOre2, new ItemCustomBlock(quartzOre2));
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
	public void entityJoined(EntityJoinWorldEvent event)
	{
		if (event.entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.entity;
			Capes.updateCape(player);
		}
		
		CSEntities.loadProperties(event.entity);
	}
	
	@SubscribeEvent
	public void playerLogin(PlayerEvent.PlayerLoggedInEvent event)
	{
		if (proxy.isClient())
		{
			CSUpdate.notifyAll(event.player);
		}
	}
	
	@SubscribeEvent
	public void getModdedMapGen(InitMapGenEvent event)
	{
		if (event.type == EventType.CAVE)
		{
			event.newGen = new CustomCaveGen();
		}
	}
}
