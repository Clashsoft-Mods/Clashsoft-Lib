package clashsoft.cslib.minecraft.hud;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface ICustomHUDBlock
{
	public ItemStack getDisplayStack(int metadata, World world, int x, int y, int z);
	
	public void addInformation(int metadata, World world, int x, int y, int z, List<String> list);
}
