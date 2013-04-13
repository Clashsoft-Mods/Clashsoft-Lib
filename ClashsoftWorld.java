package clashsoft.clashsoftapi;

import net.minecraft.world.*;

public class ClashsoftWorld
{
	/**
	 * Generates a Block with meta
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param block
	 * @param meta
	 */
	public static void setBlock(World world, int x, int y, int z, int block, int meta)
	{
		world.setBlock(x, y, z, block, meta, 0x02);
	}
	
	/**
	 * Places a block at the side of another block
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param side
	 * @param block
	 * @param meta
	 */
	public static void setBlockAtSide(World world, int x, int y, int z, int side, int block, int meta)
	{
		switch(side)
		{
		case 0:
			setBlock(world, x, y - 1, z, block, meta);
		case 1:
			setBlock(world, x, y + 1, z, block, meta);
		case 2:
			setBlock(world, x, y, z - 1, block, meta);
		case 3:
			setBlock(world, x, y, z + 1, block, meta);
		case 4:
			setBlock(world, x - 1, y, z, block, meta);
		case 5:
			setBlock(world, x + 1, y, z, block, meta);
		default:
			setBlock(world, x, y, z, block, meta);	
		}
		
	}
	
	/**
	 * Generates a Block/Line/Cube
	 * WARNING: x1 < x2 !!!
	 * @param world
	 * @param x1
	 * @param y1
	 * @param z1
	 * @param x2
	 * @param y2
	 * @param z2
	 * @param block
	 * @param meta
	 */
	public static void setCube(World world, int x1, int y1, int z1, int x2, int y2, int z2, int block, int meta)
	{
		for (int x = x1; x <= x2; x++)
		{
			for (int y = y1; y <= y2; y++)
			{
				for (int z = z1; z <= z2; z++)
				{
					setBlock(world, x, y, z, block, meta);
				}
			}
		}
	}
	
	public static void setFrame(World world, int x1, int y1, int z1, int x2, int y2, int z2, int block, int meta)
	{
		//Lower 2D-Frame
		setCube(world, x1, y1, z1, x2, y1, z1, block, meta);
		setCube(world, x1, y1, z1, x1, y1, z2, block, meta);
		setCube(world, x2, y1, z1, x2, y1, z2, block, meta);
		setCube(world, x1, y1, z2, x2, y1, z2, block, meta);
		
		//Columns
		setCube(world, x1, y1, z1, x1, y2, z1, block, meta);
		setCube(world, x2, y1, z1, x2, y2, z1, block, meta);
		setCube(world, x1, y1, z2, x1, y2, z2, block, meta);
		setCube(world, x2, y1, z2, x2, y2, z2, block, meta);
		
		//Upper 2D-Frame
		setCube(world, x1, y2, z1, x2, y2, z1, block, meta);
		setCube(world, x1, y2, z1, x1, y2, z2, block, meta);
		setCube(world, x2, y2, z1, x2, y2, z2, block, meta);
		setCube(world, x1, y2, z2, x2, y2, z2, block, meta);
	}
}
