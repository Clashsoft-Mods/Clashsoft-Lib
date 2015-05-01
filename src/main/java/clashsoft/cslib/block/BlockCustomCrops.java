package clashsoft.cslib.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCustomCrops extends BlockCustomPlant implements IGrowable
{
	public ItemStack	crop;
	public int			fullGrownMetadata;
	
	public BlockCustomCrops(int fullGrownMetadata)
	{
		super(DEFAULT_NAMES);
		this.setTickRandomly(true);
		this.setBlockBounds(0F, 0F, 0F, 1F, 0.25F, 1F);
		this.setHardness(0.0F);
		this.setStepSound(Block.soundTypeGrass);
		this.disableStats();
		
		this.fullGrownMetadata = fullGrownMetadata;
	}
	
	public BlockCustomCrops setCrop(ItemStack crop)
	{
		this.crop = crop;
		return this;
	}
	
	public ItemStack getCropItem()
	{
		return this.crop.copy();
	}
	
	@Override
	public boolean isValidSoil(World world, BlockPos pos, IBlockState state)
	{
		BlockPos down = pos.offsetDown(1);
		IBlockState soil = world.getBlockState(down);
		return soil.getBlock().canSustainPlant(world, down, EnumFacing.UP, this);
	}
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random random)
	{
		super.updateTick(world, pos, state, random);
		
		if (world.getLight(pos.offsetUp(1)) >= 9)
		{
			int metadata = (int) state.getValue(age);
			if (metadata < this.fullGrownMetadata)
			{
				float growthRate = this.getGrowthRate(world, pos, state);
				
				if (random.nextInt((int) (25.0F / growthRate) + 1) == 0)
				{
					world.setBlockState(pos, state.withProperty(age, metadata + 1), 2);
				}
			}
		}
	}
	
	@Override
	public boolean isStillGrowing(World world, BlockPos pos, IBlockState state, boolean flag)
	{
		return (int) state.getValue(age) < this.fullGrownMetadata;
	}
	
	@Override
	public boolean canUseBonemeal(World world, Random random, BlockPos pos, IBlockState state)
	{
		return true;
	}
	
	@Override
	public void grow(World world, Random random, BlockPos pos, IBlockState state)
	{
		world.setBlockState(pos, state.withProperty(age, this.fullGrownMetadata));
	}
	
	private float getGrowthRate(World world, BlockPos pos, IBlockState state)
	{
		float f = 1.0F;
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		IBlockState block1 = world.getBlockState(new BlockPos(x, y, z - 1));
		IBlockState block2 = world.getBlockState(new BlockPos(x, y, z + 1));
		IBlockState block3 = world.getBlockState(new BlockPos(x - 1, y, z));
		IBlockState block4 = world.getBlockState(new BlockPos(x + 1, y, z));
		IBlockState block5 = world.getBlockState(new BlockPos(x - 1, y, z - 1));
		IBlockState block6 = world.getBlockState(new BlockPos(x + 1, y, z - 1));
		IBlockState block7 = world.getBlockState(new BlockPos(x + 1, y, z + 1));
		IBlockState block8 = world.getBlockState(new BlockPos(x - 1, y, z + 1));
		boolean neighbors1 = block1.equals(state) || block2.equals(state) || block3.equals(state) || block4.equals(state);
		boolean neighbors2 = block5.equals(state) || block6.equals(state) || block7.equals(state) || block8.equals(state);
		
		for (int x1 = x - 1; x1 <= x + 1; ++x1)
		{
			for (int z1 = z - 1; z1 <= z + 1; ++z1)
			{
				int y1 = y - 1;
				BlockPos pos1 = new BlockPos(x1, y1, z1);
				Block block = world.getBlockState(pos1).getBlock();
				float growthRate = 0.0F;
				
				if (block.canSustainPlant(world, pos1, EnumFacing.UP, this))
				{
					if (block.isFertile(world, pos1))
					{
						growthRate = 0.75F;
					}
					else
					{
						growthRate = 0.25F;
					}
				}
				
				if (x1 == x && z1 == z)
				{
					growthRate *= 4.0F;
				}
				
				f += growthRate;
			}
		}
		
		if (neighbors1 && neighbors2)
		{
			f /= 2.0F;
		}
		
		return f;
	}
	
	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		List<ItemStack> ret = super.getDrops(world, pos, state, fortune);
		int metadata = (int) state.getValue(age);
		
		if (metadata >= this.fullGrownMetadata)
		{
			for (int i = 0; i < 3 + fortune; i++)
			{
				if (RANDOM.nextInt(this.fullGrownMetadata * 2) <= metadata)
				{
					ret.add(super.drops[(int) state.getValue(typeProperty)]);
				}
			}
		}
		
		return ret;
	}
	
	@Override
	public Item getItem(World world, BlockPos pos)
	{
		return super.getItemDropped(world.getBlockState(pos), RANDOM, 0);
	}
	
	@Override
	public int getDamageValue(World world, BlockPos pos)
	{
		return super.damageDropped(world.getBlockState(pos));
	}
}
