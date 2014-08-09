package clashsoft.cslib.minecraft.world.gen;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public abstract class CustomWorldGen extends WorldGenerator
{
	public int	flags;
	public Block genBlock = Blocks.stone;
	public int genMetadata;
	
	public CustomWorldGen(boolean update)
	{
		super(update);
		this.flags = update ? 3 : 2;
	}
	
	public void setBlock(World world, int x, int y, int z, Random random)
	{
		world.setBlock(x, y, z, this.genBlock, this.genMetadata, this.flags);
	}
	
	public void drawLineX(World world, int x, int y, int z, int length, Random random)
	{
		for (int x1 = x; x1 < x + length; x1++)
		{
			this.setBlock(world, x1, y, z, random);
		}
	}
	
	public void drawLineY(World world, int x, int y, int z, int length, Random random)
	{
		for (int y1 = y; y1 < y + length; y1++)
		{
			setBlock(world, x, y1, z, random);
		}
	}
	
	public void drawLineZ(World world, int x, int y, int z, int length, Random random)
	{
		for (int z1 = z; z1 < z + length; z1++)
		{
			setBlock(world, x, y, z1, random);
		}
	}
	
	public void drawPlaneX(World world, int x, int y, int z, int ySize, int zSize, Random random)
	{
		for (int y1 = y; y1 < y + ySize; y1++)
		{
			for (int z1 = z; z1 < z + zSize; z1++)
			{
				setBlock(world, x, y1, z1, random);
			}
		}
	}
	
	public void drawPlaneY(World world, int x, int y, int z, int xSize, int zSize, Random random)
	{
		for (int x1 = x; x1 < x + xSize; x1++)
		{
			for (int z1 = z; z1 < z + zSize; z1++)
			{
				setBlock(world, x1, y, z1, random);
			}
		}
	}
	
	public void drawPlaneZ(World world, int x, int y, int z, int xSize, int ySize, Random random)
	{
		for (int x1 = x; x1 < x + xSize; x1++)
		{
			for (int y1 = y; y1 < y + ySize; y1++)
			{
				setBlock(world, x1, y1, z, random);
			}
		}
	}
	
	public void drawHollowBox(World world, int x, int y, int z, int xSize, int ySize, int zSize, Random random)
	{
		drawSolidBox(world, x + 1, y + 1, z + 1, xSize - 1, ySize - 1, zSize - 1, Blocks.air, 0);
		drawPlaneY(world, x, y, z, xSize, zSize, random);
		drawPlaneY(world, x, y + ySize - 1, z, xSize, zSize, random);
		drawPlaneX(world, x, y, z, ySize, zSize, random);
		drawPlaneX(world, x + xSize - 1, y, z, ySize, zSize, random);
		drawPlaneZ(world, x, y, z, xSize, ySize, random);
		drawPlaneZ(world, x, y, z + zSize - 1, xSize, ySize, random);
	}
	
	public void drawSquareTube(World world, int x, int y, int z, int xSize, int ySize, int zSize, int direction, Random random)
	{
		drawSolidBox(world, x, y, z, xSize, ySize, zSize, random);
		if (direction == 0 || direction == 2)
		{
			drawPlaneY(world, x, y, z, xSize, zSize, random);
			drawPlaneY(world, x, y + ySize - 1, z, xSize, zSize, random);
		}
		if (direction == 1 || direction == 2)
		{
			drawPlaneX(world, x, y, z, ySize, zSize, random);
			drawPlaneX(world, x + xSize - 1, y, z, ySize, zSize, random);
		}
		if (direction == 0 || direction == 1)
		{
			drawPlaneZ(world, x, y, z, xSize, ySize, random);
			drawPlaneZ(world, x, y, z + zSize - 1, xSize, ySize, random);
		}
	}
	
	public void drawSolidBox(World world, int x, int y, int z, int sizeX, int sizeY, int sizeZ, Random random)
	{
		for (int x1 = x; x1 < x + sizeX; x1++)
		{
			for (int y1 = y; y1 < y + sizeY; y1++)
			{
				for (int z1 = z; z1 < z + sizeZ; z1++)
				{
					setBlock(world, x1, y1, z1, random);
				}
			}
		}
	}
	
	public void drawLineX(World world, int x, int y, int z, int length, Block block, int metadata)
	{
		for (int x1 = x; x1 < x + length; x1++)
		{
			world.setBlock(x1, y, z, block, metadata, this.flags);
		}
	}
	
	public void drawLineY(World world, int x, int y, int z, int length, Block block, int metadata)
	{
		for (int y1 = y; y1 < y + length; y1++)
		{
			world.setBlock(x, y1, z, block, metadata, this.flags);
		}
	}
	
	public void drawLineZ(World world, int x, int y, int z, int length, Block block, int metadata)
	{
		for (int z1 = z; z1 < z + length; z1++)
		{
			world.setBlock(x, y, z1, block, metadata, this.flags);
		}
	}
	
	public void drawPlaneX(World world, int x, int y, int z, int ySize, int zSize, Block block, int metadata)
	{
		for (int y1 = y; y1 < y + ySize; y1++)
		{
			for (int z1 = z; z1 < z + zSize; z1++)
			{
				world.setBlock(x, y1, z1, block, metadata, this.flags);
			}
		}
	}
	
	public void drawPlaneY(World world, int x, int y, int z, int xSize, int zSize, Block block, int metadata)
	{
		for (int x1 = x; x1 < x + xSize; x1++)
		{
			for (int z1 = z; z1 < z + zSize; z1++)
			{
				world.setBlock(x1, y, z1, block, metadata, this.flags);
			}
		}
	}
	
	public void drawPlaneZ(World world, int x, int y, int z, int xSize, int ySize, Block block, int metadata)
	{
		for (int x1 = x; x1 < x + xSize; x1++)
		{
			for (int y1 = y; y1 < y + ySize; y1++)
			{
				world.setBlock(x1, y1, z, block, metadata, this.flags);
			}
		}
	}
	
	public void drawSquareTube(World world, int x, int y, int z, int xSize, int ySize, int zSize, int direction, Block block, int metadata)
	{
		drawSolidBox(world, x, y, z, xSize, ySize, zSize, block, metadata);
		if (direction == 0 || direction == 2)
		{
			drawPlaneY(world, x, y, z, xSize, zSize, block, metadata);
			drawPlaneY(world, x, y + ySize - 1, z, xSize, zSize, block, metadata);
		}
		if (direction == 1 || direction == 2)
		{
			drawPlaneX(world, x, y, z, ySize, zSize, block, metadata);
			drawPlaneX(world, x + xSize - 1, y, z, ySize, zSize, block, metadata);
		}
		if (direction == 0 || direction == 1)
		{
			drawPlaneZ(world, x, y, z, xSize, ySize, block, metadata);
			drawPlaneZ(world, x, y, z + zSize - 1, xSize, ySize, block, metadata);
		}
	}
	
	public void drawHollowBox(World world, int x, int y, int z, int xSize, int ySize, int zSize, Block block, int metadata)
	{
		drawSolidBox(world, x + 1, y + 1, z + 1, xSize - 1, ySize - 1, zSize - 1, Blocks.air, 0);
		drawPlaneY(world, x, y, z, xSize, zSize, block, metadata);
		drawPlaneY(world, x, y + ySize - 1, z, xSize, zSize, block, metadata);
		drawPlaneX(world, x, y, z, ySize, zSize, block, metadata);
		drawPlaneX(world, x + xSize - 1, y, z, ySize, zSize, block, metadata);
		drawPlaneZ(world, x, y, z, xSize, ySize, block, metadata);
		drawPlaneZ(world, x, y, z + zSize - 1, xSize, ySize, block, metadata);
	}
	
	public void drawSolidBox(World world, int x, int y, int z, int sizeX, int sizeY, int sizeZ, Block block, int metadata)
	{
		for (int x1 = x; x1 < x + sizeX; x1++)
		{
			for (int y1 = y; y1 < y + sizeY; y1++)
			{
				for (int z1 = z; z1 < z + sizeZ; z1++)
				{
					world.setBlock(x1, y1, z1, block, metadata, this.flags);
				}
			}
		}
	}
	
	public void drawHollowSphere(World world, int x, int y, int z, int radius, Block block, int metadata)
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
						world.setBlock(x + i, y + j, z + k, block, metadata, this.flags);
					}
				}
			}
		}
	}
	
	public void drawSolidSphere(World world, int x, int y, int z, int radius, Block block, int metadata)
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
						world.setBlock(x + i, y + j, z + k, block, metadata, this.flags);
					}
				}
			}
		}
	}
}
