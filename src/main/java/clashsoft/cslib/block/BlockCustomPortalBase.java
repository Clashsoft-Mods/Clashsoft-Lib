package clashsoft.cslib.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class BlockCustomPortalBase extends Block implements ICustomBlock
{
	public int			dimensionID;
	public IBlockState	frameBlock;
	
	public BlockCustomPortalBase(int dimensionID)
	{
		super(Material.portal);
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
	public AxisAlignedBB getCollisionBoundingBox(World world, BlockPos pos, IBlockState state)
	{
		return null;
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, Entity entity)
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
	public void randomDisplayTick(World world, BlockPos pos, IBlockState state, Random random)
	{
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		
		if (random.nextInt(100) == 0)
		{
			world.playSound(x + 0.5D, y + 0.5D, z + 0.5D, "portal.portal", 0.5F, random.nextFloat() * 0.4F + 0.8F, false);
		}
		
		for (int i = 0; i < 4; ++i)
		{
			double x1 = x + random.nextFloat();
			double y1 = y + random.nextFloat();
			double z1 = z + random.nextFloat();
			double vx = (random.nextFloat() - 0.5D) * 0.5D;
			double vy = (random.nextFloat() - 0.5D) * 0.5D;
			double vz = (random.nextFloat() - 0.5D) * 0.5D;
			
			int j = random.nextInt(2) * 2 - 1;
			if (world.getBlockState(pos.offsetDown()).equals(state) || world.getBlockState(pos.offsetUp()).equals(state))
			{
				z1 = z + 0.5D + 0.25D * j;
				vz = random.nextFloat() * 2.0F * j;
			}
			else
			{
				x1 = x + 0.5D + 0.25D * j;
				vx = random.nextFloat() * 2.0F * j;
			}
			
			this.spawnParticle(world, x1, y1, z1, vx, vy, vz);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World world, BlockPos pos)
	{
		return Item.getItemById(0);
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Item.getItemById(0);
	}
	
	@Override
	public int quantityDropped(Random paramRandom)
	{
		return 0;
	}
}
