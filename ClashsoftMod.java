package clashsoft.clashsoftapi;

import clashsoft.clashsoftapi.util.CSUtil;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;

@Mod(modid = "ClashsoftAPI", name = "Clashsoft API", version = CSUtil.CURRENT_VERION)
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class ClashsoftMod
{	
	@Instance("ClashsoftAPI")
	public static ClashsoftMod instance;
	
	@SidedProxy(modId = "ClashsoftAPI", clientSide = "clashsoft.clashsoftapi.ClientProxy", serverSide = "clashsoft.clashsoftapi.CommonProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{	
		NetworkRegistry.instance().registerGuiHandler(instance, proxy);
		proxy.registerRenderers();
	}
}
