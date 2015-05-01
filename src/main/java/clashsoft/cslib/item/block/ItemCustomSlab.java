package clashsoft.cslib.item.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import clashsoft.cslib.block.BlockCustomSlab;

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
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (this.isDoubleSlab)
			return super.onItemUse(stack, player, world, pos, side, hitX, hitY, hitZ);
		if (stack.stackSize == 0)
			return false;
		if (!player.func_175151_a(pos, side, stack))
			return false;
		
		Object itemType = this.singleSlab.func_176553_a(stack);
		IProperty property = this.singleSlab.func_176551_l();
		
		IBlockState blockState = world.getBlockState(pos);
		Comparable blockType = blockState.getValue(property);
		boolean upper = blockState.getValue(BlockSlab.HALF_PROP) == BlockSlab.EnumBlockHalf.TOP;
		
		if ((side == EnumFacing.UP && !upper || side == EnumFacing.DOWN && upper) && this.block == this.singleSlab && blockType == itemType)
		{
			if (world.checkNoEntityCollision(this.doubleSlab.getCollisionBoundingBox(world, pos, blockState)))
			{
				IBlockState iblockstate1 = this.doubleSlab.getDefaultState().withProperty(property, blockType);
				if (world.setBlockState(pos, iblockstate1))
				{
					world.playSoundEffect(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, this.doubleSlab.stepSound.getPlaceSound(), (this.doubleSlab.stepSound.getVolume() + 1.0F) / 2.0F, this.doubleSlab.stepSound.frequency * 0.8F);
					--stack.stackSize;
				}
			}
			
			return true;
		}
		
		pos = pos.offset(side, 1);
		blockState = world.getBlockState(pos);
		blockType = blockState.getValue(property);
		
		if (this.block == this.singleSlab && blockType == itemType)
		{
			if (world.checkNoEntityCollision(this.doubleSlab.getCollisionBoundingBox(world, pos, blockState)))
			{
				IBlockState blockState2 = this.doubleSlab.getDefaultState().withProperty(property, blockType);
				if (world.setBlockState(pos, blockState2))
				{
					world.playSoundEffect(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, this.doubleSlab.stepSound.getPlaceSound(), (this.doubleSlab.stepSound.getVolume() + 1.0F) / 2.0F, this.doubleSlab.stepSound.frequency * 0.8F);
					--stack.stackSize;
				}
			}
			
			return true;
		}
		
		return super.onItemUse(stack, player, world, pos, side, hitX, hitY, hitZ);
	}
	
	@Override
	public boolean canPlaceBlockOnSide(World world, BlockPos pos, EnumFacing side, EntityPlayer player, ItemStack stack)
	{
		Object object = this.singleSlab.func_176553_a(stack);
		
		IBlockState blockState = world.getBlockState(pos);
		IProperty property = this.singleSlab.func_176551_l();
		Comparable type = blockState.getValue(property);
		boolean upper = blockState.getValue(BlockSlab.HALF_PROP) == BlockSlab.EnumBlockHalf.TOP;
		
		if ((side == EnumFacing.UP && !upper || side == EnumFacing.DOWN && upper) && this.block == this.singleSlab && type == object)
			return true;
		
		BlockPos pos1 = pos.offset(side, 1);
		
		IBlockState state = world.getBlockState(pos1);
		return state.getBlock() == this.singleSlab && object == state.getValue(property) ? true : super.canPlaceBlockOnSide(world, pos, side, player, stack);
	}
}
