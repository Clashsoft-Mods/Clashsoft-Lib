package clashsoft.cslib.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class GuiBuilder extends Gui
{
	public static final ResourceLocation	slots		= new ResourceLocation("cslib", "textures/gui/container/slots.png");
	public static final ResourceLocation	progress	= new ResourceLocation("cslib", "textures/gui/container/progress.png");
	public static final ResourceLocation	window		= new ResourceLocation("cslib", "textures/gui/container/window.png");
	public static final ResourceLocation	widgets		= new ResourceLocation("cslib", "textures/gui/container/widgets.png");
	
	public final GuiScreen					gui;
	public final Minecraft					mc;
	
	public GuiBuilder(GuiScreen gui)
	{
		this.gui = gui;
		this.mc = Minecraft.getMinecraft();
	}
	
	public void bind(ResourceLocation resourceLocation)
	{
		this.mc.renderEngine.bindTexture(resourceLocation);
	}
	
	public void drawFrame(int x, int y, int width, int height)
	{
		bind(window);
		drawFrame_(x, y, width, height);
	}
	
	public void drawFrame_(int x, int y, int width, int height)
	{
		boolean flag1 = width > 176;
		boolean flag2 = height > 166;
		
		int i = width - 4;
		int j = height - 4;
		int k = flag1 ? 172 : i;
		int l = flag2 ? 162 : j;
		
		this.drawTexturedModalRect(x, y, 0, 0, k, l);
		this.drawTexturedModalRect(x + i, y, 172, 0, 4, l);
		this.drawTexturedModalRect(x, y + j, 0, 162, k, 4);
		this.drawTexturedModalRect(x + i, y + j, 172, 162, 4, 4);
		
		if (flag1)
		{
			int x1 = x + 172;
			int k1 = i - 172;
			this.drawTexturedModalRect(x1, y, 4, 0, k1, l);
			this.drawTexturedModalRect(x1, y + j, 4, 162, k1, 4);
		}
		if (flag2)
		{
			int y1 = y + 162;
			int l1 = j - 162;
			this.drawTexturedModalRect(x, y1, 0, 4, k, l1);
			this.drawTexturedModalRect(x + i, y1, 172, 4, 4, l1);
			
			if (flag1)
			{
				this.drawTexturedModalRect(x + 172, y1, 4, 4, i - 172, l1);
			}
		}
	}
	
	public void drawPlayerBackgroundL(int x, int y)
	{
		drawWidget(x, y, 0, 0, 54, 72);
	}
	
	public void drawPlayerBackgroundS(int x, int y)
	{
		drawWidget(x, y, 54, 0, 34, 45);
	}
	
	public void drawWidget(int x, int y, int u, int v, int width, int height)
	{
		bind(widgets);
		drawWidget_(x, y, u, v, width, height);
	}
	
	public void drawWidget_(int x, int y, int u, int v, int width, int height)
	{
		this.drawTexturedModalRect(x, y, u, v, width, height);
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
		this.drawSlot_(x - 4, y - 4, type, locked);
	}
	
	public void drawSlot_(int x, int y, int type, boolean locked)
	{
		this.drawTexturedModalRect(x, y, type * 32, locked ? 32 : 0, 32, 32);
	}
}
