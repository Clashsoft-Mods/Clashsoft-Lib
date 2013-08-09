package clashsoft.clashsoftapi;

import clashsoft.clashsoftapi.util.CSUtil;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;

import net.minecraftforge.common.Configuration;

@Mod(modid = "ClashsoftAPI", name = "Clashsoft API", version = CSUtil.CURRENT_VERION)
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class ClashsoftMod
{	
	@Instance("ClashsoftAPI")
	public static ClashsoftMod instance;
	
	@SidedProxy(modId = "ClashsoftAPI", clientSide = "clashsoft.clashsoftapi.ClientProxy", serverSide = "clashsoft.clashsoftapi.CommonProxy")
	public static CommonProxy proxy;
	
	public static boolean csFont = true;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{	
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		
		csFont = config.get("Options", "CS Font Render", true).getBoolean(true);
		
		config.save();
		
		NetworkRegistry.instance().registerGuiHandler(instance, proxy);
		proxy.registerRenderers();
	}
}
