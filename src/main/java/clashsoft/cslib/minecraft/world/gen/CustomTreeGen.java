package clashsoft.cslib.minecraft.world.gen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
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
	protected final int flags;
	
	/** The minimum height of a generated tree. */
	public int		minTreeHeight	= 4;
	
	/** True if this tree should grow Vines. */
	public boolean	vinesGrow		= false;
	
	public Block	logBlock		= Blocks.log;
	public Block	leafBlock		= Blocks.leaves;
	public Block	vineBlock		= Blocks.vine;
	
	/** The metadata value of the wood to use in tree generation. */
	public int		logMetadata		= 0;
	
	/** The metadata value of the leaves to use in tree generation. */
	public int		leafMetadata	= 0;
	
	public CustomTreeGen(boolean blockUpdates, int minTreeHeight, Block log, Block leaf)
	{
		this(blockUpdates, minTreeHeight, log, leaf, 0, 0);
	}
	
	public CustomTreeGen(boolean blockUpdates, int minTreeHeight, Block log, Block leaf, int woodMetadata, int leavesMetadata)
	{
		this(blockUpdates, minTreeHeight, log, leaf, woodMetadata, leavesMetadata, false);
	}
	
	public CustomTreeGen(boolean blockUpdates, int minTreeHeight, Block log, Block leaf, int woodMetadata, int leavesMetadata, boolean vinesGrow)
	{
		super(blockUpdates);
		this.flags = blockUpdates ? 3 : 2;
		this.minTreeHeight = minTreeHeight;
		this.logBlock = log;
		this.leafBlock = leaf;
		this.logMetadata = woodMetadata;
		this.leafMetadata = leavesMetadata;
		this.vinesGrow = vinesGrow;
	}
	
	@Override
	public boolean generate(World world, Random random, int x, int y, int z)
	{
		int height = random.nextInt(3) + this.minTreeHeight;
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
							if (this.isReplaceable(world, x1, y1, z1))
							{
								continue;
							}
						}
						flag = false;
					}
				}
			}
			
			if (!flag)
			{
				return false;
			}
			
			if (y < 256 - height - 1)
			{
				byte b0 = 3;
				byte b1 = 0;
				
				for (int k1 = y - b0 + height; k1 <= y + height; ++k1)
				{
					int i3 = k1 - (y + height);
					int l1 = b1 + 1 - i3 / 2;
					
					for (int i2 = x - l1; i2 <= x + l1; ++i2)
					{
						int j2 = i2 - x;
						
						for (int k2 = z - l1; k2 <= z + l1; ++k2)
						{
							int l2 = k2 - z;
							
							if (Math.abs(j2) != l1 || Math.abs(l2) != l1 || random.nextInt(2) != 0 && i3 != 0)
							{
								this.setBlock(world, i2, k1, k2, this.leafBlock, this.leafMetadata);
							}
						}
					}
				}
				
				for (int k1 = 0; k1 < height; ++k1)
				{
					Block block = world.getBlock(x, y + k1, z);
					
					if (block.isAir(world, x, y + k1, z) || block.isLeaves(world, x, y + k1, z))
					{
						this.setBlockAndNotifyAdequately(world, x, y + k1, z, this.logBlock, this.logMetadata);
						
						if (this.vinesGrow && k1 > 0)
						{
							if (random.nextInt(3) > 0 && world.isAirBlock(x - 1, y + k1, z))
							{
								this.setBlockAndNotifyAdequately(world, x - 1, y + k1, z, Blocks.vine, 8);
							}
							if (random.nextInt(3) > 0 && world.isAirBlock(x + 1, y + k1, z))
							{
								this.setBlockAndNotifyAdequately(world, x + 1, y + k1, z, Blocks.vine, 2);
							}
							if (random.nextInt(3) > 0 && world.isAirBlock(x, y + k1, z - 1))
							{
								this.setBlockAndNotifyAdequately(world, x, y + k1, z - 1, Blocks.vine, 1);
							}
							if (random.nextInt(3) > 0 && world.isAirBlock(x, y + k1, z + 1))
							{
								this.setBlockAndNotifyAdequately(world, x, y + k1, z + 1, Blocks.vine, 4);
							}
						}
					}
				}
				
				if (this.vinesGrow)
				{
					for (int k1 = y - 3 + height; k1 <= y + height; ++k1)
					{
						int i3 = k1 - (y + height);
						int l1 = 2 - i3 / 2;
						
						for (int i2 = x - l1; i2 <= x + l1; ++i2)
						{
							for (int j2 = z - l1; j2 <= z + l1; ++j2)
							{
								if (world.getBlock(i2, k1, j2).isLeaves(world, i2, k1, j2))
								{
									if (random.nextInt(4) == 0 && world.getBlock(i2 - 1, k1, j2).isAir(world, i2 - 1, k1, j2))
									{
										this.growVines(world, i2 - 1, k1, j2, 8);
									}
									if (random.nextInt(4) == 0 && world.getBlock(i2 + 1, k1, j2).isAir(world, i2 + 1, k1, j2))
									{
										this.growVines(world, i2 + 1, k1, j2, 2);
									}
									if (random.nextInt(4) == 0 && world.getBlock(i2, k1, j2 - 1).isAir(world, i2, k1, j2 - 1))
									{
										this.growVines(world, i2, k1, j2 - 1, 1);
									}
									if (random.nextInt(4) != 0 || !world.getBlock(i2, k1, j2 + 1).isAir(world, i2, k1, j2 + 1))
									{
										continue;
									}
									this.growVines(world, i2, k1, j2 + 1, 4);
								}
							}
							
						}
						
					}
					
					if (random.nextInt(5) == 0 && height > 5)
					{
						for (int k1 = 0; k1 < 2; ++k1)
						{
							for (int i3 = 0; i3 < 4; ++i3)
							{
								if (random.nextInt(4 - k1) == 0)
								{
									int l1 = random.nextInt(3);
									this.setBlockAndNotifyAdequately(world, x + net.minecraft.util.Direction.offsetX[net.minecraft.util.Direction.rotateOpposite[i3]], y + height - 5 + k1, z + net.minecraft.util.Direction.offsetZ[net.minecraft.util.Direction.rotateOpposite[i3]], Blocks.cocoa, l1 << 2 | i3);
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
	
	private void growVines(World world, int x, int y, int z, int metadata)
	{
		this.setBlockAndNotifyAdequately(world, x, y, z, this.vineBlock, metadata);
		int i1 = 4;
		while (true)
		{
			--y;
			
			if (world.isAirBlock(x, y, z) || i1 <= 0)
			{
				return;
			}
			
			this.setBlockAndNotifyAdequately(world, x, y, z, this.vineBlock, metadata);
			--i1;
		}
	}
	
	public void generateRoot(World world, int x, int y, int z, int width, int height)
	{
		if (width == 1)
		{
			for (int i = 0; i < height; i++)
			{
				this.setBlockAndNotifyAdequately(world, x, y + i, z, this.logBlock, this.logMetadata);
			}
		}
		else if (width == 2)
		{
			for (int i = 0; i < height; i++)
			{
				this.setBlockAndNotifyAdequately(world, x, y + i, z, this.logBlock, this.logMetadata);
				this.setBlockAndNotifyAdequately(world, x + 1, y + i, z, this.logBlock, this.logMetadata);
				this.setBlockAndNotifyAdequately(world, x, y + i, z + 1, this.logBlock, this.logMetadata);
				this.setBlockAndNotifyAdequately(world, x + 1, y + i, z + 1, this.logBlock, this.logMetadata);
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
						this.setBlockAndNotifyAdequately(world, x + i, y + k, z + j, this.logBlock, this.logMetadata);
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
						this.setBlock(world, x + x0, y, z + z0, this.leafBlock, this.leafMetadata);
					}
				}
			}
		}
	}
	
	protected void setBlock(World world, int x, int y, int z, Block block, int meta)
	{
		Block cur = world.getBlock(x, y, z);
		if (cur.isAir(world, x, y, z) || cur == this.leafBlock)
		{
			world.setBlock(x, y, z, block, meta, this.flags);
		}
	}
}
