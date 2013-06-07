package clashsoft.clashsoftapi;

import net.minecraft.client.Minecraft;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.event.FMLInitializationEvent;

@Mod(modid = "ClashsoftAPI", name = "Clashsoft API", version = CSUtil.CURRENT_VERION)
public class ClashsoftMod
{	
	protected static CSFontRenderer csFontRenderer = new CSFontRenderer(Minecraft.getMinecraft().gameSettings, "/font/default.png", Minecraft.getMinecraft().renderEngine, false);
	
	@Init
	public void init(FMLInitializationEvent event)
	{
		csFontRenderer = new CSFontRenderer(Minecraft.getMinecraft().gameSettings, "/font/default.png", Minecraft.getMinecraft().renderEngine, false);
		Minecraft.getMinecraft().fontRenderer = csFontRenderer;
	}
}
