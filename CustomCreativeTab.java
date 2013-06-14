package clashsoft.clashsoftapi;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class CustomCreativeTab extends CreativeTabs
{
	private ItemStack displayStack;
	
	public CustomCreativeTab(String label, ItemStack displayStack)
	{
		super(label);
		this.displayStack = displayStack;
	}
	
	/**
     * Get the ItemStack that will be rendered to the tab.
     */
    public ItemStack getIconItemStack()
    {
        return displayStack;
    }
    
    public CustomCreativeTab setIconItemStack(ItemStack par1ItemStack)
    {
    	displayStack = par1ItemStack;
    	return this;
    }

}
