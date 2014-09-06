package clashsoft.cslib.minecraft.block;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public abstract class BlockCustomPortalBase extends Block implements ICustomBlock
{
	public int		dimensionID;
	public Block	frameBlock;
	public int		frameMetadata;
	
	public BlockCustomPortalBase(String iconName, int dimensionID)
	{
		super(Material.portal);
		this.setBlockTextureName(iconName);
		this.setHardness(-1F);
		this.setLightLevel(1F);
		this.setTickRandomly(true);
		
		this.dimensionID = dimensionID;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return this.getUnlocalizedName();
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> list)
	{
		CustomBlock.addInformation(this, stack, list);
	}
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return null;
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
		int dest = this.getDestination(src);
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
	
	public int getDestination(int source)
	{
		return source == 0 ? this.dimensionID : 0;
	}
	
	public abstract Teleporter createTeleporter(WorldServer world);
	
	public void spawnParticle(World world, double x, double y, double z, double vX, double vY, double vZ)
	{
	}
	
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
}
