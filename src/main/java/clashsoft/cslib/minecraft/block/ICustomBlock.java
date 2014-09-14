package clashsoft.cslib.minecraft.block;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface ICustomBlock
{
	public static final String[]	DEFAULT_NAMES	= new String[] { null };
	
	public String getUnlocalizedName(ItemStack stack);
	
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> list);
}
