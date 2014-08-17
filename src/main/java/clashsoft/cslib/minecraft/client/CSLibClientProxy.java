package clashsoft.cslib.minecraft.client;

import clashsoft.cslib.minecraft.cape.CapeHelper;
import clashsoft.cslib.minecraft.cape.Capes;
import clashsoft.cslib.minecraft.cape.EmptyCape;
import clashsoft.cslib.minecraft.client.gui.GuiModUpdates;
import clashsoft.cslib.minecraft.client.renderer.block.RenderBlock2D;
import clashsoft.cslib.minecraft.client.renderer.block.RenderBlockBush;
import clashsoft.cslib.minecraft.common.CSLibProxy;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class CSLibClientProxy extends CSLibProxy
{
	@Override
	public void init(FMLInitializationEvent event)
	{
		BLOCK2D_RENDER_ID = RenderingRegistry.getNextAvailableRenderId();
		CUSTOMBUSH_RENDER_ID = RenderingRegistry.getNextAvailableRenderId();
		
		RenderingRegistry.registerBlockHandler(BLOCK2D_RENDER_ID, new RenderBlock2D());
		RenderingRegistry.registerBlockHandler(CUSTOMBUSH_RENDER_ID, new RenderBlockBush());
		
		Capes.setLocalCape("AdrianKunz", new ResourceLocation("cslib", "textures/capes/cape_clashsoft.png"));
		Capes.noCape = Capes.addCape(new EmptyCape("no_cape"));
	}
	
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		return null;
	}
	
	@Override
	public void openMUScreen()
	{
		Minecraft mc = Minecraft.getMinecraft();
		mc.displayGuiScreen(new GuiModUpdates(mc.currentScreen));
	}
	
	@Override
	public void openGUI(Object gui)
	{
		Minecraft.getMinecraft().displayGuiScreen((GuiScreen) gui);
	}
	
	@Override
	public void setCape(EntityPlayer player, String capeName)
	{
		CapeHelper.updateCape(player, Capes.getCape(capeName));
	}
	
	@Override
	public boolean isClient()
	{
		return true;
	}
	
	@Override
	public World getClientWorld()
	{
		return Minecraft.getMinecraft().theWorld;
	}
	
	@Override
	public EntityPlayer getClientPlayer()
	{
		return Minecraft.getMinecraft().thePlayer;
	}
}
