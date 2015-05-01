package clashsoft.cslib.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

public class BlockCustomBush extends BlockCustomPlant implements IGrowable, IShearable
{
	/** The metadata value this block has to reach to by fully grown. */
	public int		fullGrownMetadata;
	protected int	tickRate;
	protected float	growChance;
	
	public float	bushMinX	= 0.125F;
	public float	bushMinY	= 0F;
	public float	bushMinZ	= 0.125F;
	public float	bushMaxX	= 0.875F;
	public float	bushMaxY	= 0.875F;
	public float	bushMaxZ	= 0.875F;
	
	public BlockCustomBush()
	{
		super(DEFAULT_NAMES);
		this.setTickRandomly(true);
		this.fullGrownMetadata = 3;
		this.setTicksToGrow(180 * 20);
	}
	
	public BlockCustomBush setFullGrownMetadata(int metadata)
	{
		this.fullGrownMetadata = metadata;
		return this;
	}
	
	public BlockCustomBush setTicksToGrow(int ticks)
	{
		this.growChance = 1F / (this.fullGrownMetadata + 1F);
		this.tickRate = (int) (ticks * this.growChance);
		return this;
	}
	
	public BlockCustomBush setBushBounds(float minX, float minY, float minZ, float maxX, float maxY, float maxZ)
	{
		this.bushMinX = minX;
		this.bushMinY = minY;
		this.bushMinZ = minZ;
		this.bushMaxX = maxX;
		this.bushMaxY = maxY;
		this.bushMaxZ = maxZ;
		return this;
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos)
	{
		if ((int) world.getBlockState(pos).getValue(age) >= this.fullGrownMetadata)
		{
			this.setBlockBounds(this.bushMinX, this.bushMinY, this.bushMinZ, this.bushMaxX, this.bushMaxY, this.bushMaxZ);
		}
		else
		{
			this.setBlockBounds(0.3F, 0F, 0.3F, 0.7F, 0.9F, 0.7F);
		}
	}
	
	@Override
	public float getBlockHardness(World world, BlockPos pos)
	{
		return (int) world.getBlockState(pos).getValue(age) >= this.fullGrownMetadata ? 0.6F : 0.2F;
	}
	
	@Override
	public int tickRate(World world)
	{
		return this.tickRate;
	}
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random random)
	{
		super.updateTick(world, pos, state, random);
		
		int i = (int) state.getValue(age);
		if (i < this.fullGrownMetadata && random.nextFloat() < this.growChance)
		{
			world.setBlockState(pos, state.withProperty(age, i + 1), 2);
		}
	}
	
	@Override
	public boolean shouldSideBeRendered(IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		return true;
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	public boolean canUseBonemeal(World world, Random random, BlockPos pos, IBlockState state)
	{
		return true;
	}
	
	@Override
	public boolean isStillGrowing(World world, BlockPos pos, IBlockState state, boolean flag)
	{
		return (int) state.getValue(age) < this.fullGrownMetadata;
	}
	
	@Override
	public void grow(World world, Random random, BlockPos pos, IBlockState state)
	{
		world.setBlockState(pos, state.withProperty(age, this.fullGrownMetadata));
	}
	
	@Override
	public boolean isShearable(ItemStack stack, IBlockAccess world, BlockPos pos)
	{
		return true;
	}
	
	@Override
	public List<ItemStack> onSheared(ItemStack stack, IBlockAccess world, BlockPos pos, int fortune)
	{
		ArrayList<ItemStack> list = new ArrayList();
		list.add(new ItemStack(this, 1, 0));
		return list;
	}
}
