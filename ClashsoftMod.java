package clashsoft.clashsoftapi;

import clashsoft.clashsoftapi.datatools.ItemDataAxe;
import clashsoft.clashsoftapi.datatools.ItemDataHoe;
import clashsoft.clashsoftapi.datatools.ItemDataPickaxe;
import clashsoft.clashsoftapi.datatools.ItemDataSpade;
import clashsoft.clashsoftapi.datatools.ItemDataSword;
import clashsoft.clashsoftapi.util.CSFontRenderer;
import clashsoft.clashsoftapi.util.CSItemRenderer;
import clashsoft.clashsoftapi.util.CSUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.Configuration;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = "ClashsoftAPI", name = "Clashsoft API", version = CSUtil.CURRENT_VERION)
public class ClashsoftMod
{	
	@SidedProxy(clientSide = "clashsoft.clashsoftapi.ClientProxy", serverSide = "clashsoft.clashsoftapi.CommonProxy")
	public static CommonProxy proxy;
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event)
	{
	}
	
	@Init
	public void init(FMLInitializationEvent event)
	{	
		proxy.registerRenderers();
	}
	
	@PostInit
	public void postInit(FMLPostInitializationEvent event)
	{
		
	}
}
