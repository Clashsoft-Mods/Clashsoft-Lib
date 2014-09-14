package clashsoft.cslib.minecraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Direction;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockCustomPortal extends BlockCustomPortalBase
{
	public static int[][]	metadataMap	= { null, { 3, 1 }, { 2, 0 } };
	
	public BlockCustomPortal(String iconName, int dimensionID)
	{
		super(iconName, dimensionID);
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		int i = limitToValidMetadata(world.getBlockMetadata(x, y, z));
		PortalSize size1 = new PortalSize(world, x, y, z, 1);
		PortalSize size2 = new PortalSize(world, x, y, z, 2);
		
		if (i == 1 && (!size1.isValid() || size1.portals < size1.height * size1.width))
		{
			world.setBlock(x, y, z, Blocks.air);
		}
		else if (i == 2 && (!size2.isValid() || size2.portals < size2.height * size2.width))
		{
			world.setBlock(x, y, z, Blocks.air);
		}
		else if (i == 0 && !size1.isValid() && !size2.isValid())
		{
			world.setBlock(x, y, z, Blocks.air);
		}
	}
	
	public boolean generatePortal(World world, int x, int y, int z)
	{
		PortalSize size1 = new PortalSize(world, x, y, z, 1);
		PortalSize size2 = new PortalSize(world, x, y, z, 2);
		
		if (size1.isValid() && size1.portals == 0)
		{
			size1.generatePortal();
			return true;
		}
		if (size2.isValid() && size2.portals == 0)
		{
			size2.generatePortal();
			return true;
		}
		
		return false;
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
	{
		int i = limitToValidMetadata(world.getBlockMetadata(x, y, z));
		
		if (i == 0)
		{
			if (world.getBlock(x - 1, y, z) == this || world.getBlock(x + 1, y, z) == this)
			{
				i = 1;
			}
			else
			{
				i = 2;
			}
			
			if (world instanceof World && !((World) world).isRemote)
			{
				((World) world).setBlockMetadataWithNotify(x, y, z, i, 2);
			}
		}
		
		if (i == 1)
		{
			this.setBlockBounds(0F, 0.0F, 0.375F, 1F, 1.0F, 0.625F);
		}
		else if (i == 2)
		{
			this.setBlockBounds(0.375F, 0.0F, 0F, 0.625F, 1.0F, 1F);
		}
	}
	
	public class PortalSize
	{
		private final World			world;
		private final int			metadata;
		private final int			direction1;
		private final int			direction2;
		private int					portals	= 0;
		private ChunkCoordinates	chunkPos;
		private int					height;
		private int					width;
		
		public PortalSize(World world, int x, int y, int z, int metadata)
		{
			this.world = world;
			this.metadata = metadata;
			this.direction2 = metadataMap[metadata][0];
			this.direction1 = metadataMap[metadata][1];
			
			for (int i1 = y; y > i1 - 21 && y > 0 && this.isValidBlock(x, y - 1, z); --y)
			{
				;
			}
			
			int j1 = this.calculateWidth(x, y, z, this.direction2) - 1;
			
			if (j1 >= 0)
			{
				this.chunkPos = new ChunkCoordinates(x + j1 * Direction.offsetX[this.direction2], y, z + j1 * Direction.offsetZ[this.direction2]);
				this.width = this.calculateWidth(this.chunkPos.posX, this.chunkPos.posY, this.chunkPos.posZ, this.direction1);
				
				if (this.width < 2 || this.width > 21)
				{
					this.chunkPos = null;
					this.width = 0;
				}
			}
			
			if (this.chunkPos != null)
			{
				this.height = this.calculateHeight();
			}
		}
		
		protected int calculateWidth(int x, int y, int z, int direction)
		{
			int xoff = Direction.offsetX[direction];
			int zoff = Direction.offsetZ[direction];
			int width;
			
			for (width = 0; width < 22; ++width)
			{
				int i = xoff * width;
				int j = zoff * width;
				
				if (!this.isValidBlock(x + i, y, z + j))
				{
					break;
				}
				
				if (!this.isFrameBlock(x + i, y - 1, z + j))
				{
					break;
				}
			}
			
			return this.isFrameBlock(x + xoff * width, y, z + zoff * width) ? width : 0;
		}
		
		protected int calculateHeight()
		{
			int var0 = metadataMap[this.metadata][0];
			int var1 = metadataMap[this.metadata][1];
			
			outer:
			for (this.height = 0; this.height < 21; ++this.height)
			{
				int y = this.chunkPos.posY + this.height;
				
				for (int i = 0; i < this.width; ++i)
				{
					int x = this.chunkPos.posX + i * Direction.offsetX[var1];
					int z = this.chunkPos.posZ + i * Direction.offsetZ[var1];
					Block block = this.world.getBlock(x, y, z);
					int metadata = this.world.getBlockMetadata(x, y, z);
					
					if (!this.isValidBlock(block, metadata))
					{
						break outer;
					}
					
					if (block == BlockCustomPortal.this)
					{
						++this.portals;
					}
					
					if (i == 0)
					{
						if (!this.isFrameBlock(x + Direction.offsetX[var0], y, z + Direction.offsetZ[var0]))
						{
							break outer;
						}
					}
					else if (i == this.width - 1)
					{
						if (!this.isFrameBlock(x + Direction.offsetX[var1], y, z + Direction.offsetZ[var1]))
						{
							break outer;
						}
					}
				}
			}
			
			for (int i = 0; i < this.width; ++i)
			{
				int x = this.chunkPos.posX + i * Direction.offsetX[var1];
				int y = this.chunkPos.posY + this.height;
				int z = this.chunkPos.posZ + i * Direction.offsetZ[var1];
				
				if (!this.isFrameBlock(x, y, z))
				{
					this.height = 0;
					break;
				}
			}
			
			if (this.height <= 21 && this.height >= 3)
			{
				return this.height;
			}
			else
			{
				this.chunkPos = null;
				this.width = 0;
				this.height = 0;
				return 0;
			}
		}
		
		protected boolean isValidBlock(int x, int y, int z)
		{
			return this.isValidBlock(this.world.getBlock(x, y, z), this.world.getBlockMetadata(x, y, z));
		}
		
		protected boolean isValidBlock(Block block, int metadata)
		{
			return block.getMaterial() == Material.air || block == Blocks.fire || block == BlockCustomPortal.this;
		}
		
		protected boolean isFrameBlock(int x, int y, int z)
		{
			return this.isFrameBlock(this.world.getBlock(x, y, z), this.world.getBlockMetadata(x, y, z));
		}
		
		protected boolean isFrameBlock(Block block, int metadata)
		{
			return block == BlockCustomPortal.this.frameBlock && metadata == BlockCustomPortal.this.frameMetadata;
		}
		
		public boolean isValid()
		{
			return this.chunkPos != null && this.width >= 2 && this.width <= 21 && this.height >= 3 && this.height <= 21;
		}
		
		public void generatePortal()
		{
			for (int i = 0; i < this.width; ++i)
			{
				int j = this.chunkPos.posX + Direction.offsetX[this.direction1] * i;
				int k = this.chunkPos.posZ + Direction.offsetZ[this.direction1] * i;
				
				for (int l = 0; l < this.height; ++l)
				{
					int i1 = this.chunkPos.posY + l;
					this.world.setBlock(j, i1, k, BlockCustomPortal.this, this.metadata, 2);
				}
			}
		}
	}
}
