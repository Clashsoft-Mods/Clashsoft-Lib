package clashsoft.cslib.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class GuiBuilder extends Gui
{
	public static final ResourceLocation slots = new ResourceLocation("cslib", "textures/gui/container/slots.png");
	public static final ResourceLocation progress = new ResourceLocation("cslib", "textures/gui/container/progress.png");
	public static final ResourceLocation window = new ResourceLocation("cslib", "textures/gui/container/window.png");
	public static final ResourceLocation widgets = new ResourceLocation("cslib", "textures/gui/container/widgets.png");
	
	public final GuiScreen gui;
	public final Minecraft mc;
	
	public GuiBuilder(GuiScreen gui)
	{
		this.gui = gui;
		this.mc = Minecraft.getMinecraft();
	}
	
	public void bind(ResourceLocation resourceLocation)
	{
		this.mc.renderEngine.bindTexture(resourceLocation);
	}
	
	public void drawSlots(int[] x, int[] y)
	{
		this.bind(slots);
		for (int i = 0; i < x.length; i++)
		{
			this.drawSlot_(x[i], y[i], 0, false);
		}
	}
	
	public void drawSlots(int[] x, int[] y, int[] type)
	{
		this.bind(slots);
		for (int i = 0; i < x.length; i++)
		{
			this.drawSlot_(x[i], y[i], type[i], false);
		}
	}
	
	public void drawSlots(int[] x, int[] y, int[] type, boolean[] locked)
	{
		this.bind(slots);
		for (int i = 0; i < x.length; i++)
		{
			this.drawSlot_(x[i], y[i], type[i], locked[i]);
		}
	}
	
	public void drawSlot(int x, int y)
	{
		this.drawSlot(x, y, 0);
	}
	
	public void drawSlot(int x, int y, int type)
	{
		this.drawSlot(x, y, type, false);
	}
	
	public void drawSlot(int x, int y, int type, boolean locked)
	{
		this.bind(slots);
		this.drawSlot_(x, y, type, locked);
	}
	
	public void drawSlot_(int x, int y, int type, boolean locked)
	{
		this.drawTexturedModalRect(x, y, locked ? 32 : 0, type * 32, 32, 32);
	}
}
