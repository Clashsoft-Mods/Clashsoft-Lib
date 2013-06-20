package clashsoft.clashsoftapi;

import clashsoft.clashsoftapi.util.CSFontRenderer;
import clashsoft.clashsoftapi.util.CSItemRenderer;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy
{
	public static CSFontRenderer csFontRenderer = new CSFontRenderer(Minecraft.getMinecraft().gameSettings, "/font/default.png", Minecraft.getMinecraft().renderEngine, false);
	public static CSItemRenderer csItemRenderer = new CSItemRenderer();
	
	public void registerRenderers()
	{
		csFontRenderer = new CSFontRenderer(Minecraft.getMinecraft().gameSettings, "/font/default.png", Minecraft.getMinecraft().renderEngine, false);
		Minecraft.getMinecraft().fontRenderer = csFontRenderer;
	}
}
