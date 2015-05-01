package clashsoft.cslib.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockCustomFarmland extends BlockFarmland
{
	public IBlockState	dirt;
	
	public BlockCustomFarmland(IBlockState dirt)
	{
		this.dirt = dirt;
	}
	
	@Override
	public void onFallenUpon(World world, BlockPos pos, Entity entity, float fallDistance)
	{
		if (!world.isRemote && world.rand.nextFloat() < fallDistance - 0.5F)
		{
			if (!(entity instanceof EntityPlayer) && !world.getGameRules().getGameRuleBooleanValue("mobGriefing"))
				return;
			
			world.setBlockState(pos, this.dirt, 3);
		}
	}
	
	@Override
	public void onNeighborBlockChange(World world, BlockPos pos, IBlockState state, Block neighborBlock)
	{
		super.onNeighborBlockChange(world, pos, state, neighborBlock);
		Material material = world.getBlockState(pos.offsetUp()).getBlock().getMaterial();
		
		if (material.isSolid())
		{
			world.setBlockState(pos, this.dirt, 3);
		}
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random random, int fortune)
	{
		return this.dirt.getBlock().getItemDropped(state, random, fortune);
	}
	
	@Override
	public int damageDropped(IBlockState state)
	{
		return this.dirt.getBlock().damageDropped(state);
	}
	
	@Override
	public Item getItem(World world, BlockPos pos)
	{
		return Item.getItemFromBlock(this.dirt.getBlock());
	}
	
	@Override
	public int getDamageValue(World world, BlockPos pos)
	{
		return this.dirt.getBlock().damageDropped(world.getBlockState(pos));
	}
}
