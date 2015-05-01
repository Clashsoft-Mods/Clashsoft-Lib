package clashsoft.cslib.block.ore;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
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
	public void onBlockClicked(World world, BlockPos pos, EntityPlayer player)
	{
		this.updateState(world, pos, world.getBlockState(pos));
		super.onBlockClicked(world, pos, player);
	}
	
	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, Entity entity)
	{
		this.updateState(world, pos, world.getBlockState(pos));
		super.onEntityCollidedWithBlock(world, pos, entity);
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		this.updateState(world, pos, state);
		return super.onBlockActivated(world, pos, state, player, side, hitX, hitY, hitZ);
	}
	
	private void updateState(World world, BlockPos pos, IBlockState state)
	{
		this.spawnParticles(world, pos);
		
		if (!this.isActive)
		{
			world.setBlockState(pos, Blocks.lit_redstone_ore.getStateFromMeta(this.getMetaFromState(state)), 3);
		}
	}
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
	{
		if (this.isActive)
		{
			world.setBlockState(pos, Blocks.redstone_ore.getStateFromMeta(this.getMetaFromState(state)), 3);
		}
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random random, int fortune)
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
	public int getExpDrop(IBlockAccess world, BlockPos pos, int fortune)
	{
		if (this.getItemDropped(world.getBlockState(pos), rand, fortune) != Item.getItemFromBlock(this))
			return 1 + rand.nextInt(5);
		return 0;
	}
	
	@Override
	protected ItemStack createStackedBlock(IBlockState state)
	{
		return new ItemStack(Blocks.redstone_ore, 1, this.getMetaFromState(state));
	}
	
	@Override
	public void randomDisplayTick(World world, BlockPos pos, IBlockState state, Random rand)
	{
		if (this.isActive)
		{
			this.spawnParticles(world, pos);
		}
	}
	
	private void spawnParticles(World world, BlockPos pos)
	{
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		Random random = world.rand;
		for (int l = 0; l < 6; ++l)
		{
			double d1 = x + random.nextFloat();
			double d2 = y + random.nextFloat();
			double d3 = z + random.nextFloat();
			
			if (l == 0 && !world.getBlockState(new BlockPos(x, y + 1, z)).getBlock().isOpaqueCube())
			{
				d2 = y + 1.0625D;
			}
			
			if (l == 1 && !world.getBlockState(new BlockPos(x, y + 1, z)).getBlock().isOpaqueCube())
			{
				d2 = y - 0.0625D;
			}
			
			if (l == 2 && !world.getBlockState(new BlockPos(x + 1, y, z)).getBlock().isOpaqueCube())
			{
				d1 = z + 1.0625D;
			}
			
			if (l == 3 && !world.getBlockState(new BlockPos(x - 1, y, z)).getBlock().isOpaqueCube())
			{
				d1 = z - 0.0625D;
			}
			
			if (l == 4 && !world.getBlockState(new BlockPos(x, y, z + 1)).getBlock().isOpaqueCube())
			{
				d3 = x + 1.0625D;
			}
			
			if (l == 5 && !world.getBlockState(new BlockPos(x, y, z - 1)).getBlock().isOpaqueCube())
			{
				d3 = x - 0.0625D;
			}
			
			if (d1 < x || d1 > x + 1 || d2 < 0.0D || d2 > y + 1 || d3 < z || d3 > z + 1)
			{
				world.spawnParticle(EnumParticleTypes.REDSTONE, d1, d2, d3, 0.0D, 0.0D, 0.0D);
			}
		}
	}
}
