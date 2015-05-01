package clashsoft.cslib.world.gen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockVine;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenTrees;

/**
 * The class CustomTreeGenerator
 * 
 * @author Clashsoft
 */
public class CustomTreeGen extends WorldGenTrees
{
	protected final int	flags;
	
	/** The minimum height of a generated tree. */
	public int			minTreeHeight	= 4;
	
	/** True if this tree should grow Vines. */
	public boolean		vinesGrow		= false;
	
	public IBlockState	logBlock;
	public IBlockState	leavesBlock;
	public Block		vineBlock		= Blocks.vine;
	
	public CustomTreeGen(boolean blockUpdates, int minTreeHeight, IBlockState log, IBlockState leaves)
	{
		this(blockUpdates, minTreeHeight, log, leaves, false);
	}
	
	public CustomTreeGen(boolean blockUpdates, int minTreeHeight, IBlockState log, IBlockState leaves, boolean vinesGrow)
	{
		super(blockUpdates);
		this.flags = blockUpdates ? 3 : 2;
		this.minTreeHeight = minTreeHeight;
		this.logBlock = log;
		this.leavesBlock = leaves;
		this.vinesGrow = vinesGrow;
	}
	
	@Override
	public boolean generate(World world, Random random, BlockPos pos)
	{
		int height = random.nextInt(3) + this.minTreeHeight;
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		int y2 = y + height + 1;
		boolean flag = true;
		
		if (y >= 1 && y + height < 256)
		{
			for (int y1 = y + 1; y1 <= y2; ++y1)
			{
				byte off = 1;
				
				if (y1 == y)
				{
					off = 0;
				}
				if (y1 >= y + height - 1)
				{
					off = 2;
				}
				
				for (int x1 = x - off; flag && x1 <= x + off; ++x1)
				{
					for (int z1 = z - off; flag && z1 <= z + off; ++z1)
					{
						if (y1 >= 0 && y1 < 256)
						{
							if (this.isReplaceable(world, new BlockPos(x1, y1, z1)))
							{
								continue;
							}
						}
						flag = false;
					}
				}
			}
			
			if (!flag)
				return false;
			
			if (y < 256 - height - 1)
			{
				byte b0 = 3;
				byte b1 = 0;
				
				for (int y1 = y - b0 + height; y1 <= y + height; ++y1)
				{
					int i3 = y1 - (y + height);
					int l1 = b1 + 1 - i3 / 2;
					
					for (int x1 = x - l1; x1 <= x + l1; ++x1)
					{
						int j2 = x1 - x;
						
						for (int z1 = z - l1; z1 <= z + l1; ++z1)
						{
							int l2 = z1 - z;
							
							if (Math.abs(j2) != l1 || Math.abs(l2) != l1 || random.nextInt(2) != 0 && i3 != 0)
							{
								this.setBlock(world, x1, y1, z1, this.leavesBlock);
							}
						}
					}
				}
				
				for (int y1 = 0; y1 < height; ++y1)
				{
					BlockPos upN = pos.offsetUp(y1);
					Block block2 = world.getBlockState(upN).getBlock();
					
					if (block2.isAir(world, upN) || block2.isLeaves(world, upN) || block2.getMaterial() == Material.vine)
					{
						world.setBlockState(pos.offsetUp(y1), this.logBlock, this.flags);
						
						if (this.vinesGrow && y1 > 0)
						{
							if (random.nextInt(3) > 0 && world.isAirBlock(pos.add(-1, y1, 0)))
							{
								this.func_175905_a(world, pos.add(-1, y1, 0), this.vineBlock, BlockVine.field_176275_S);
							}
							
							if (random.nextInt(3) > 0 && world.isAirBlock(pos.add(1, y1, 0)))
							{
								this.func_175905_a(world, pos.add(1, y1, 0), this.vineBlock, BlockVine.field_176271_T);
							}
							
							if (random.nextInt(3) > 0 && world.isAirBlock(pos.add(0, y1, -1)))
							{
								this.func_175905_a(world, pos.add(0, y1, -1), this.vineBlock, BlockVine.field_176272_Q);
							}
							
							if (random.nextInt(3) > 0 && world.isAirBlock(pos.add(0, y1, 1)))
							{
								this.func_175905_a(world, pos.add(0, y1, 1), this.vineBlock, BlockVine.field_176276_R);
							}
						}
					}
				}
				
				if (this.vinesGrow)
				{
					for (int y1 = y - 3 + height; y1 <= y + height; ++y1)
					{
						int i3 = y1 - (y + height);
						int l1 = 2 - i3 / 2;
						
						for (int x1 = x - l1; x1 <= x + l1; ++x1)
						{
							for (int z1 = z - l1; z1 <= z + l1; ++z1)
							{
								BlockPos blockpos3 = new BlockPos(x1, y1, z1);
								
								if (world.getBlockState(blockpos3).getBlock().isLeaves(world, blockpos3))
								{
									BlockPos blockpos4 = blockpos3.offsetWest();
									BlockPos east = blockpos3.offsetEast();
									BlockPos blockpos5 = blockpos3.offsetNorth();
									BlockPos blockpos2 = blockpos3.offsetSouth();
									
									if (random.nextInt(4) == 0 && world.getBlockState(blockpos4).getBlock().isAir(world, blockpos4))
									{
										this.func_175905_a(world, blockpos4, this.vineBlock, BlockVine.field_176275_S);
									}
									
									if (random.nextInt(4) == 0 && world.getBlockState(east).getBlock().isAir(world, east))
									{
										this.func_175905_a(world, east, this.vineBlock, BlockVine.field_176271_T);
									}
									
									if (random.nextInt(4) == 0 && world.getBlockState(blockpos5).getBlock().isAir(world, blockpos5))
									{
										this.func_175905_a(world, blockpos5, this.vineBlock, BlockVine.field_176272_Q);
									}
									
									if (random.nextInt(4) == 0 && world.getBlockState(blockpos2).getBlock().isAir(world, blockpos2))
									{
										this.func_175905_a(world, blockpos2, this.vineBlock, BlockVine.field_176276_R);
									}
								}
							}
						}
					}
				}
				
				return true;
			}
		}
		
		return false;
	}
	
	public void generateRoot(World world, int x, int y, int z, int width, int height)
	{
		if (width == 1)
		{
			for (int i = 0; i < height; i++)
			{
				world.setBlockState(new BlockPos(x, y + i, z), this.logBlock, this.flags);
			}
		}
		else if (width == 2)
		{
			for (int i = 0; i < height; i++)
			{
				world.setBlockState(new BlockPos(x, y + i, z), this.logBlock, this.flags);
				world.setBlockState(new BlockPos(x + 1, y + i, z), this.logBlock, this.flags);
				world.setBlockState(new BlockPos(x, y + i, z + 1), this.logBlock, this.flags);
				world.setBlockState(new BlockPos(x + 1, y + i, z + 1), this.logBlock, this.flags);
			}
		}
		else
		{
			int sqradius = width * width;
			for (int i = -width; i <= width; i++)
			{
				for (int j = -width; j <= width; j++)
				{
					if (i * i + j * j > sqradius)
					{
						continue;
					}
					
					for (int k = 0; k < height; k++)
					{
						world.setBlockState(new BlockPos(x + i, y + k, z + j), this.logBlock, this.flags);
					}
				}
			}
		}
	}
	
	public void generateLeafCircles(World world, Random random, int x, int y, int z, int height, double radius, double decrease)
	{
		this.generateLeafCircles(world, random, x, y, z, height, radius, decrease, false);
	}
	
	public void generateLeafCircles(World world, Random random, int x, int y, int z, int height, double radius, double decrease, boolean rand)
	{
		for (int i = 0; i < height; i++)
		{
			this.generateLeafCircle(world, random, x, y + i, z, radius, rand);
			radius -= decrease;
		}
	}
	
	public void generateLeafCircle(World world, Random random, int x, int y, int z, double radius)
	{
		this.generateLeafCircle(world, random, x, y, z, radius, false);
	}
	
	public void generateLeafCircle(World world, Random random, int x, int y, int z, double radius, boolean rand)
	{
		double radius1 = radius * radius;
		double radius2 = rand ? (radius - 1) * (radius - 1) : radius1;
		int x1 = MathHelper.floor_double(-radius);
		int z1 = MathHelper.floor_double(-radius);
		int x2 = MathHelper.ceiling_double_int(radius);
		int z2 = MathHelper.ceiling_double_int(radius);
		
		for (int x0 = x1; x0 <= x2; x0++)
		{
			for (int z0 = z1; z0 <= z2; z0++)
			{
				double d = x0 * x0 + z0 * z0;
				
				if (d <= radius1)
				{
					if (!rand || d <= radius2 || random.nextInt(2) == 0)
					{
						this.setBlock(world, x + x0, y, z + z0, this.leavesBlock);
					}
				}
			}
		}
	}
	
	protected void setBlock(World world, int x, int y, int z, IBlockState state)
	{
		BlockPos pos = new BlockPos(x, y, z);
		IBlockState current = world.getBlockState(pos);
		if (current.getBlock().isAir(world, pos) || current.equals(this.leavesBlock))
		{
			world.setBlockState(pos, state, this.flags);
		}
	}
}
