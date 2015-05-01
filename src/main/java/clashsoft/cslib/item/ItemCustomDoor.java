package clashsoft.cslib.item;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemCustomDoor extends Item
{
	public Block	doorBlock;
	
	public ItemCustomDoor(Block block)
	{
		super();
		this.doorBlock = block;
		this.maxStackSize = 1;
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (side != EnumFacing.UP)
			return false;
		pos = pos.offsetUp();
		
		if (!player.func_175151_a(pos, side, stack) || !player.func_175151_a(pos.offsetUp(), side, stack))
			return false;
		if (!this.doorBlock.canPlaceBlockAt(world, pos))
			return false;
		
		ItemDoor.func_179235_a(world, pos, EnumFacing.fromAngle(player.rotationYaw + 180D), this.doorBlock);
		
		stack.stackSize -= 1;
		return true;
	}
}
