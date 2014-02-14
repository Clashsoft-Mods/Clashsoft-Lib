package clashsoft.cslib.minecraft.client.gui;

import java.util.List;

import clashsoft.cslib.minecraft.lang.I18n;
import clashsoft.cslib.minecraft.update.CSUpdate;
import clashsoft.cslib.minecraft.update.ModUpdate;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class GuiModUpdates extends GuiScreen
{
	public final GuiScreen		parent;
	
	public GuiModUpdatesSlot	slots;
	public GuiButton			buttonShowInvalidUpdates;
	
	public List<ModUpdate>		updates;
	public ModUpdate			update;
	public boolean				showInvalidUpdates;
	
	private String				title				= I18n.getString("update.list.title");
	private String				updates_showinvalid	= I18n.getString("update.list.showinvalid");
	private String				options_on			= I18n.getString("options.on");
	private String				options_off			= I18n.getString("options.off");
	
	public GuiModUpdates(GuiScreen parent)
	{
		this.parent = parent;
		this.updateList();
	}
	
	@Override
	public void initGui()
	{
		this.buttonShowInvalidUpdates = new GuiButton(1, 10, this.height - 38, 140, 20, getShowInvalidUpdates());
		
		this.buttonList.add(new GuiButton(0, this.width / 2 - 20, this.height - 38, I18n.getString("gui.done")));
		this.buttonList.add(this.buttonShowInvalidUpdates);
		this.slots = new GuiModUpdatesSlot(this);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTickTime)
	{
		if (this.slots != null)
		{
			this.slots.drawScreen(mouseX, mouseY, partialTickTime);
		}
		
		this.drawCenteredString(fontRendererObj, title, this.width / 2, 14, 0xFFFFFF);
		
		ModUpdate update = this.update;
		
		if (update != null)
		{
			boolean valid = update.isValid();
			int color1 = valid ? 0xFF0000 : 0x00FF00;
			int color2 = valid ? 0x00FF00 : 0xFF0000;
			
			this.drawString(fontRendererObj, update.getName(), 160, 38, 0xFFFFFF);
			this.drawString(fontRendererObj, "Mod Name:", 160, 50, 0xFFFFFF);
			this.drawString(fontRendererObj, "Current Version:", 160, 60, 0xFFFFFF);
			this.drawString(fontRendererObj, "New Version:", 160, 70, 0xFFFFFF);
			this.drawString(fontRendererObj, "Download URL:", 160, 80, 0xFFFFFF);
			this.drawString(fontRendererObj, "Update Notes:", 160, 95, 0xFFFFFF);
			
			this.drawString(fontRendererObj, update.modName, 250, 50, 0xFFFFFF);
			this.drawString(fontRendererObj, update.version, 250, 60, color1);
			this.drawString(fontRendererObj, update.newVersion, 250, 70, color2);
			this.drawString(fontRendererObj, update.updateUrl, 250, 80, 0xFFFFFF);
			
			int i = 107;
			for (String line : update.updateNotes)
			{
				char c = line.charAt(0);
				int color = c == '+' ? 0x00FF00 : (c == '*' ? 0xFFFF00 : (c == '-' ? 0xFF0000 : 0xFFFFFF));
				
				this.drawString(fontRendererObj, line, 160, i, color);
				
				i += 10;
			}
		}
		
		super.drawScreen(mouseX, mouseY, partialTickTime);
	}
	
	@Override
	protected void actionPerformed(GuiButton button)
	{
		if (button.id == 0)
		{
			this.mc.displayGuiScreen(this.parent);
		}
		else if (button.id == 1)
		{
			this.showInvalidUpdates = !this.showInvalidUpdates;
			button.displayString = getShowInvalidUpdates();
			
			this.updateList();
		}
	}
	
	protected void updateList()
	{
		this.updates = CSUpdate.getUpdates(this.showInvalidUpdates);
		this.update = this.updates.isEmpty() ? null : this.updates.get(0);
		
		if (this.slots != null)
		{
			this.slots.selectedIndex = 0;
		}
	}
	
	private String getShowInvalidUpdates()
	{
		return updates_showinvalid + ": " + (this.showInvalidUpdates ? options_on : options_off);
	}
}
