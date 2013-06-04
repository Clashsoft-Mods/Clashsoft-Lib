package clashsoft.clashsoftapi;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;

public class ItemCustomBlock extends ItemBlock
{	
    private CustomBlock theBlock;

    public ItemCustomBlock(int par1, Block par2Block)
    {
        super(par1);
        this.theBlock = (CustomBlock)par2Block;
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @SideOnly(Side.CLIENT)

    /**
     * Gets an icon index based on an item's damage value
     */
    public Icon getIconFromDamage(int par1)
    {
        return this.theBlock.getIcon(2, par1);
    }

    /**
     * Returns the metadata of the block which this Item (ItemBlock) can place
     */
    public int getMetadata(int par1)
    {
        return par1;
    }
    
    @Override
    public String getLocalizedName(ItemStack par1ItemStack)
    {
    	return StatCollector.translateToLocal(theBlock.getUnlocalizedName() + "." + par1ItemStack.getItemDamage());
    }
    
    @Override
    /**
     * Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have
     * different names based on their damage or NBT.
     */
    public String getUnlocalizedName(ItemStack par1ItemStack)
    {
        return theBlock.getUnlocalizedName() + "." + par1ItemStack.getItemDamage();
    }

    @Override
    /**
     * Returns the unlocalized name of this item.
     */
    public String getUnlocalizedName()
    {
        return theBlock.getUnlocalizedName() + ".0";
    }
}
