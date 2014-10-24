package clashsoft.cslib.minecraft.item.block;

import clashsoft.cslib.minecraft.block.BlockCustomSlab;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemCustomSlab extends ItemBlock
{
	private boolean			isDoubleSlab;
	private BlockCustomSlab	singleSlab;
	private BlockCustomSlab	doubleSlab;
	
	public ItemCustomSlab(Block block)
	{
		super(block);
		this.setHasSubtypes(true);
		
		if (block instanceof BlockCustomSlab)
		{
			BlockCustomSlab slab = (BlockCustomSlab) block;
			BlockCustomSlab otherSlab = slab.otherSlab;
			
			this.isDoubleSlab = block.isOpaqueCube();
			this.singleSlab = this.isDoubleSlab ? otherSlab : slab;
			this.doubleSlab = this.isDoubleSlab ? slab : otherSlab;
		}
	}
	
	@Override
	public IIcon getIconFromDamage(int metadata)
	{
		return this.field_150939_a.getIcon(2, metadata);
	}
	
	@Override
	public int getMetadata(int metadata)
	{
		return metadata;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return this.singleSlab.getUnlocalizedName(stack);
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if (this.isDoubleSlab)
		{
			return super.onItemUse(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
		}
		else if (stack.stackSize == 0)
		{
			return false;
		}
		else if (!player.canPlayerEdit(x, y, z, side, stack))
		{
			return false;
		}
		
		Block block = world.getBlock(x, y, z);
		int metadata = world.getBlockMetadata(x, y, z);
		int type = metadata & 7;
		boolean upper = (metadata & 8) != 0;
		
		if ((side == 1 && !upper || side == 0 && upper) && block == this.singleSlab && type == stack.getItemDamage())
		{
			if (world.checkNoEntityCollision(this.doubleSlab.getCollisionBoundingBoxFromPool(world, x, y, z)) && world.setBlock(x, y, z, this.doubleSlab, type, 3))
			{
				world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, this.doubleSlab.stepSound.func_150496_b(), (this.doubleSlab.stepSound.getVolume() + 1.0F) / 2.0F, this.doubleSlab.stepSound.getPitch() * 0.8F);
				--stack.stackSize;
			}
			
			return true;
		}
		
		return this.placeSlab(stack, player, world, x, y, z, side) ? true : super.onItemUse(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
	}
	
	@Override
	public boolean func_150936_a(World world, int x, int y, int z, int side, EntityPlayer player, ItemStack stack)
	{
		int i1 = x;
		int j1 = y;
		int k1 = z;
		Block block = world.getBlock(x, y, z);
		int metadata = world.getBlockMetadata(x, y, z);
		int type = metadata & 7;
		boolean upper = (metadata & 8) != 0;
		
		if ((side == 1 && !upper || side == 0 && upper) && block == this.singleSlab && type == stack.getItemDamage())
		{
			return true;
		}
		
		if (side == 0)
		{
			--y;
		}
		else if (side == 1)
		{
			++y;
		}
		else if (side == 2)
		{
			--z;
		}
		else if (side == 3)
		{
			++z;
		}
		else if (side == 4)
		{
			--x;
		}
		else if (side == 5)
		{
			++x;
		}
		
		block = world.getBlock(x, y, z);
		metadata = world.getBlockMetadata(x, y, z);
		type = metadata & 7;
		return block == this.singleSlab && type == stack.getItemDamage() ? true : super.func_150936_a(world, i1, j1, k1, side, player, stack);
	}
	
	private boolean placeSlab(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side)
	{
		if (side == 0)
		{
			--y;
		}
		else if (side == 1)
		{
			++y;
		}
		else if (side == 2)
		{
			--z;
		}
		else if (side == 3)
		{
			++z;
		}
		else if (side == 4)
		{
			--x;
		}
		else if (side == 5)
		{
			++x;
		}
		
		Block block = world.getBlock(x, y, z);
		int metadata = world.getBlockMetadata(x, y, z);
		int type = metadata & 7;
		
		if (block == this.singleSlab && type == stack.getItemDamage())
		{
			if (world.checkNoEntityCollision(this.doubleSlab.getCollisionBoundingBoxFromPool(world, x, y, z)) && world.setBlock(x, y, z, this.doubleSlab, type, 3))
			{
				world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, this.doubleSlab.stepSound.func_150496_b(), (this.doubleSlab.stepSound.getVolume() + 1.0F) / 2.0F, this.doubleSlab.stepSound.getPitch() * 0.8F);
				--stack.stackSize;
			}
			
			return true;
		}
		
		return false;
	}
}
