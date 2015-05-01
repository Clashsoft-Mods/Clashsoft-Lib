package clashsoft.cslib.world.gen;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public abstract class CustomWorldGen extends WorldGenerator
{
	public int			flags;
	public IBlockState	block;
	
	public CustomWorldGen(boolean update)
	{
		super(update);
		this.flags = update ? 3 : 2;
	}
	
	public void setBlock(World world, int x, int y, int z, Random random)
	{
		world.setBlockState(new BlockPos(x, y, z), this.block, this.flags);
	}
	
	public void setBlock(World world, int x, int y, int z, IBlockState block)
	{
		world.setBlockState(new BlockPos(x, y, z), block, this.flags);
	}
	
	public final void drawLineX(World world, int x, int y, int z, int length, Random random)
	{
		for (int x1 = x; x1 < x + length; x1++)
		{
			this.setBlock(world, x1, y, z, random);
		}
	}
	
	public final void drawLineY(World world, int x, int y, int z, int length, Random random)
	{
		for (int y1 = y; y1 < y + length; y1++)
		{
			this.setBlock(world, x, y1, z, random);
		}
	}
	
	public final void drawLineZ(World world, int x, int y, int z, int length, Random random)
	{
		for (int z1 = z; z1 < z + length; z1++)
		{
			this.setBlock(world, x, y, z1, random);
		}
	}
	
	public final void drawPlaneX(World world, int x, int y, int z, int ySize, int zSize, Random random)
	{
		for (int y1 = y; y1 < y + ySize; y1++)
		{
			for (int z1 = z; z1 < z + zSize; z1++)
			{
				this.setBlock(world, x, y1, z1, random);
			}
		}
	}
	
	public final void drawPlaneY(World world, int x, int y, int z, int xSize, int zSize, Random random)
	{
		for (int x1 = x; x1 < x + xSize; x1++)
		{
			for (int z1 = z; z1 < z + zSize; z1++)
			{
				this.setBlock(world, x1, y, z1, random);
			}
		}
	}
	
	public final void drawPlaneZ(World world, int x, int y, int z, int xSize, int ySize, Random random)
	{
		for (int x1 = x; x1 < x + xSize; x1++)
		{
			for (int y1 = y; y1 < y + ySize; y1++)
			{
				this.setBlock(world, x1, y1, z, random);
			}
		}
	}
	
	public final void drawSquareTube(World world, int x, int y, int z, int xSize, int ySize, int zSize, int direction, Random random)
	{
		this.drawSolidBox(world, x, y, z, xSize, ySize, zSize, random);
		if (direction == 0 || direction == 2)
		{
			this.drawPlaneY(world, x, y, z, xSize, zSize, random);
			this.drawPlaneY(world, x, y + ySize - 1, z, xSize, zSize, random);
		}
		if (direction == 1 || direction == 2)
		{
			this.drawPlaneX(world, x, y, z, ySize, zSize, random);
			this.drawPlaneX(world, x + xSize - 1, y, z, ySize, zSize, random);
		}
		if (direction == 0 || direction == 1)
		{
			this.drawPlaneZ(world, x, y, z, xSize, ySize, random);
			this.drawPlaneZ(world, x, y, z + zSize - 1, xSize, ySize, random);
		}
	}
	
	public final void drawHollowBox(World world, int x, int y, int z, int xSize, int ySize, int zSize, Random random)
	{
		this.drawSolidBox(world, x + 1, y + 1, z + 1, xSize - 1, ySize - 1, zSize - 1, Blocks.air.getDefaultState());
		this.drawPlaneY(world, x, y, z, xSize, zSize, random);
		this.drawPlaneY(world, x, y + ySize - 1, z, xSize, zSize, random);
		this.drawPlaneX(world, x, y, z, ySize, zSize, random);
		this.drawPlaneX(world, x + xSize - 1, y, z, ySize, zSize, random);
		this.drawPlaneZ(world, x, y, z, xSize, ySize, random);
		this.drawPlaneZ(world, x, y, z + zSize - 1, xSize, ySize, random);
	}
	
	public final void drawSolidBox(World world, int x, int y, int z, int sizeX, int sizeY, int sizeZ, Random random)
	{
		for (int x1 = x; x1 < x + sizeX; x1++)
		{
			for (int y1 = y; y1 < y + sizeY; y1++)
			{
				for (int z1 = z; z1 < z + sizeZ; z1++)
				{
					this.setBlock(world, x1, y1, z1, random);
				}
			}
		}
	}
	
	public final void drawHollowSphere(World world, int x, int y, int z, int radius, Random random)
	{
		int sqradius = radius * radius;
		for (int i = -radius; i <= radius; i++)
		{
			for (int j = -radius; j <= radius; j++)
			{
				for (int k = -radius; k <= radius; k++)
				{
					if (i * i + j * j + k * k == sqradius)
					{
						this.setBlock(world, x + i, y + j, z + k, random);
					}
				}
			}
		}
	}
	
	public final void drawSolidSphere(World world, int x, int y, int z, int radius, Random random)
	{
		int sqradius = radius * radius;
		for (int i = -radius; i <= radius; i++)
		{
			for (int j = -radius; j <= radius; j++)
			{
				for (int k = -radius; k <= radius; k++)
				{
					if (i * i + j * j + k * k <= sqradius)
					{
						this.setBlock(world, x + i, y + j, z + k, random);
					}
				}
			}
		}
	}
	
	public final void drawLineX(World world, int x, int y, int z, int length, IBlockState block)
	{
		for (int x1 = x; x1 < x + length; x1++)
		{
			world.setBlockState(new BlockPos(x1, y, z), block, this.flags);
		}
	}
	
	public final void drawLineY(World world, int x, int y, int z, int length, IBlockState block)
	{
		for (int y1 = y; y1 < y + length; y1++)
		{
			world.setBlockState(new BlockPos(x, y1, z), block, this.flags);
		}
	}
	
	public final void drawLineZ(World world, int x, int y, int z, int length, IBlockState block)
	{
		for (int z1 = z; z1 < z + length; z1++)
		{
			world.setBlockState(new BlockPos(x, y, z1), block, this.flags);
		}
	}
	
	public final void drawPlaneX(World world, int x, int y, int z, int ySize, int zSize, IBlockState block)
	{
		for (int y1 = y; y1 < y + ySize; y1++)
		{
			for (int z1 = z; z1 < z + zSize; z1++)
			{
				world.setBlockState(new BlockPos(x, y1, z1), block, this.flags);
			}
		}
	}
	
	public final void drawPlaneY(World world, int x, int y, int z, int xSize, int zSize, IBlockState block)
	{
		for (int x1 = x; x1 < x + xSize; x1++)
		{
			for (int z1 = z; z1 < z + zSize; z1++)
			{
				world.setBlockState(new BlockPos(x1, y, z1), block, this.flags);
			}
		}
	}
	
	public final void drawPlaneZ(World world, int x, int y, int z, int xSize, int ySize, IBlockState block)
	{
		for (int x1 = x; x1 < x + xSize; x1++)
		{
			for (int y1 = y; y1 < y + ySize; y1++)
			{
				world.setBlockState(new BlockPos(x1, y1, z), block, this.flags);
			}
		}
	}
	
	public final void drawSquareTube(World world, int x, int y, int z, int xSize, int ySize, int zSize, int direction, IBlockState block)
	{
		this.drawSolidBox(world, x, y, z, xSize, ySize, zSize, block);
		if (direction == 0 || direction == 2)
		{
			this.drawPlaneY(world, x, y, z, xSize, zSize, block);
			this.drawPlaneY(world, x, y + ySize - 1, z, xSize, zSize, block);
		}
		if (direction == 1 || direction == 2)
		{
			this.drawPlaneX(world, x, y, z, ySize, zSize, block);
			this.drawPlaneX(world, x + xSize - 1, y, z, ySize, zSize, block);
		}
		if (direction == 0 || direction == 1)
		{
			this.drawPlaneZ(world, x, y, z, xSize, ySize, block);
			this.drawPlaneZ(world, x, y, z + zSize - 1, xSize, ySize, block);
		}
	}
	
	public final void drawHollowBox(World world, int x, int y, int z, int xSize, int ySize, int zSize, IBlockState block)
	{
		this.drawSolidBox(world, x + 1, y + 1, z + 1, xSize - 1, ySize - 1, zSize - 1, Blocks.air.getDefaultState());
		this.drawPlaneY(world, x, y, z, xSize, zSize, block);
		this.drawPlaneY(world, x, y + ySize - 1, z, xSize, zSize, block);
		this.drawPlaneX(world, x, y, z, ySize, zSize, block);
		this.drawPlaneX(world, x + xSize - 1, y, z, ySize, zSize, block);
		this.drawPlaneZ(world, x, y, z, xSize, ySize, block);
		this.drawPlaneZ(world, x, y, z + zSize - 1, xSize, ySize, block);
	}
	
	public final void drawSolidBox(World world, int x, int y, int z, int sizeX, int sizeY, int sizeZ, IBlockState block)
	{
		for (int x1 = x; x1 < x + sizeX; x1++)
		{
			for (int y1 = y; y1 < y + sizeY; y1++)
			{
				for (int z1 = z; z1 < z + sizeZ; z1++)
				{
					world.setBlockState(new BlockPos(x1, y1, z1), block, this.flags);
				}
			}
		}
	}
	
	public final void drawHollowSphere(World world, int x, int y, int z, int radius, IBlockState block)
	{
		int sqradius = radius * radius;
		for (int i = -radius; i <= radius; i++)
		{
			for (int j = -radius; j <= radius; j++)
			{
				for (int k = -radius; k <= radius; k++)
				{
					if (i * i + j * j + k * k == sqradius)
					{
						world.setBlockState(new BlockPos(x + i, y + j, z + k), block, this.flags);
					}
				}
			}
		}
	}
	
	public final void drawSolidSphere(World world, int x, int y, int z, int radius, IBlockState block)
	{
		int sqradius = radius * radius;
		for (int i = -radius; i <= radius; i++)
		{
			for (int j = -radius; j <= radius; j++)
			{
				for (int k = -radius; k <= radius; k++)
				{
					if (i * i + j * j + k * k <= sqradius)
					{
						world.setBlockState(new BlockPos(x + i, y + j, z + k), block, this.flags);
					}
				}
			}
		}
	}
}
