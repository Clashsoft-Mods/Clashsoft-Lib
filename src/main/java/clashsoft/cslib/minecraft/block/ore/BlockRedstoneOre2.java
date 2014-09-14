package clashsoft.cslib.minecraft.block.ore;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRedstoneOre2 extends BlockOre2
{
	private static final Random	rand	= new Random();
	
	protected boolean			isActive;
	
	public BlockRedstoneOre2(String type, boolean lit)
	{
		super(type);
		
		if (lit)
		{
			this.isActive = true;
			this.setCreativeTab(null);
			this.setTickRandomly(true);
		}
	}
	
	@Override
	public int tickRate(World world)
	{
		return 30;
	}
	
	@Override
	public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player)
	{
		this.updateState(world, x, y, z);
		super.onBlockClicked(world, x, y, z, player);
	}
	
	@Override
	public void onEntityWalking(World world, int x, int y, int z, Entity entity)
	{
		this.updateState(world, x, y, z);
		super.onEntityWalking(world, x, y, z, entity);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		this.updateState(world, x, y, z);
		return super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ);
	}
	
	private void updateState(World world, int x, int y, int z)
	{
		this.spawnParticles(world, x, y, z);
		
		if (!this.isActive)
		{
			world.setBlock(x, y, z, Blocks.lit_redstone_ore, world.getBlockMetadata(x, y, z), 3);
		}
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random random)
	{
		if (this.isActive)
		{
			world.setBlock(x, y, z, Blocks.redstone_ore, world.getBlockMetadata(x, y, z), 3);
		}
	}
	
	@Override
	public Item getItemDropped(int metadata, Random random, int fortune)
	{
		return Items.redstone;
	}
	
	@Override
	public int quantityDroppedWithBonus(int metadata, Random random)
	{
		return this.quantityDropped(random) + random.nextInt(metadata + 1);
	}
	
	@Override
	public int quantityDropped(Random random)
	{
		return 4 + random.nextInt(2);
	}
	
	@Override
	public int getExpDrop(IBlockAccess world, int metadata, int fortune)
	{
		if (this.getItemDropped(metadata, rand, fortune) != Item.getItemFromBlock(this))
		{
			return 1 + rand.nextInt(5);
		}
		return 0;
	}
	
	@Override
	protected ItemStack createStackedBlock(int metadata)
	{
		return new ItemStack(Blocks.redstone_ore, 1, metadata);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int x, int y, int z, Random random)
	{
		if (this.isActive)
		{
			this.spawnParticles(world, x, y, z);
		}
	}
	
	private void spawnParticles(World world, int x, int y, int z)
	{
		Random random = world.rand;
		for (int l = 0; l < 6; ++l)
		{
			double d1 = x + random.nextFloat();
			double d2 = y + random.nextFloat();
			double d3 = z + random.nextFloat();
			
			if (l == 0 && !world.getBlock(x, y + 1, z).isOpaqueCube())
			{
				d2 = y + 1.0625D;
			}
			
			if (l == 1 && !world.getBlock(x, y - 1, z).isOpaqueCube())
			{
				d2 = y - 0.0625D;
			}
			
			if (l == 2 && !world.getBlock(x, y, z + 1).isOpaqueCube())
			{
				d3 = z + 1.0625D;
			}
			
			if (l == 3 && !world.getBlock(x, y, z - 1).isOpaqueCube())
			{
				d3 = z - 0.0625D;
			}
			
			if (l == 4 && !world.getBlock(x + 1, y, z).isOpaqueCube())
			{
				d1 = x + 1.0625D;
			}
			
			if (l == 5 && !world.getBlock(x - 1, y, z).isOpaqueCube())
			{
				d1 = x - 0.0625D;
			}
			
			if (d1 < x || d1 > x + 1 || d2 < 0.0D || d2 > y + 1 || d3 < z || d3 > z + 1)
			{
				world.spawnParticle("reddust", d1, d2, d3, 0.0D, 0.0D, 0.0D);
			}
		}
	}
}
