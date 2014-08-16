package clashsoft.cslib.minecraft.block;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Direction;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public abstract class BlockCustomPortal extends BlockImpl
{
	public static int[][]	metadataMap	= { null, { 3, 1 }, { 2, 0 } };
	
	public int				dimensionID;
	public Block			frameBlock;
	public int				frameMetadata;
	
	public BlockCustomPortal(String name, String iconName, int dimensionID)
	{
		super(Material.portal, name, iconName);
		this.setHardness(-1F);
		this.setLightLevel(1F);
		this.setTickRandomly(true);
		
		this.dimensionID = dimensionID;
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
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return null;
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
			this.setBlockBounds(0F, 0.0F, 0.475F, 1F, 1.0F, 0.625F);
		}
		else if (i == 2)
		{
			this.setBlockBounds(0.475F, 0.0F, 0F, 0.625F, 1.0F, 1F);
		}
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	public boolean renderAsNormalBlock()
	{
		return true;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderBlockPass()
	{
		return 1;
	}
	
	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
	{
		if (!world.isRemote && entity.ridingEntity == null && entity.riddenByEntity == null)
		{
			if (entity.timeUntilPortal == 0)
			{
				entity.timeUntilPortal = entity.getPortalCooldown();
				this.transferEntity(entity);
			}
			else
			{
				entity.setInPortal();
			}
		}
	}
	
	public void transferEntity(Entity entity)
	{
		MinecraftServer server = MinecraftServer.getServer();
		ServerConfigurationManager manager = server.getConfigurationManager();
		int src = entity.dimension;
		int dest = src == 0 ? this.dimensionID : 0;
		WorldServer destWorld = manager.getServerInstance().worldServerForDimension(dest);
		Teleporter teleporter = this.createTeleporter(destWorld);
		
		if (entity instanceof EntityPlayerMP)
		{
			manager.transferPlayerToDimension((EntityPlayerMP) entity, dest, teleporter);
		}
		else
		{
			WorldServer srcWorld = server.worldServerForDimension(src);
			manager.transferEntityToWorld(entity, dest, srcWorld, destWorld, teleporter);
		}
	}
	
	public abstract Teleporter createTeleporter(WorldServer world);
	
	public abstract void spawnParticle(World world, double x, double y, double z, double vX, double vY, double vZ);
	
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random random)
	{
		if (random.nextInt(100) == 0)
		{
			world.playSound(x + 0.5D, y + 0.5D, z + 0.5D, "portal.portal", 0.5F, random.nextFloat() * 0.4F + 0.8F, false);
		}
		for (int i = 0; i < 4; ++i)
		{
			double d1 = x + random.nextFloat();
			double d2 = y + random.nextFloat();
			double d3 = z + random.nextFloat();
			double d4 = (random.nextFloat() - 0.5D) * 0.5D;
			double d5 = (random.nextFloat() - 0.5D) * 0.5D;
			double d6 = (random.nextFloat() - 0.5D) * 0.5D;
			
			int j = random.nextInt(2) * 2 - 1;
			if (world.getBlock(x - 1, y, z) == this || world.getBlock(x + 1, y, z) == this)
			{
				d3 = z + 0.5D + 0.25D * j;
				d6 = random.nextFloat() * 2.0F * j;
			}
			else
			{
				d1 = x + 0.5D + 0.25D * j;
				d4 = random.nextFloat() * 2.0F * j;
			}
			
			this.spawnParticle(world, d1, d2, d3, d4, d5, d6);
		}
	}
	
	public static int limitToValidMetadata(int metadata)
	{
		return metadata & 0x3;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World world, int x, int y, int z)
	{
		return Item.getItemById(0);
	}
	
	@Override
	public int quantityDropped(Random paramRandom)
	{
		return 0;
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
