package clashsoft.cslib.minecraft.block;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.Icon;

import net.minecraft.block.IGrowable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import clashsoft.cslib.minecraft.common.CSLibProxy;

public class BlockCustomBush extends BlockCustomPlant implements IGrowable, IShearable
{
	/** The metadata value this block has to reach to by fully grown. */
	public int			fullGrownMetadata;
	protected int		tickRate;
	protected float		growChance;
	
	public float		bushMinX	= 0.125F;
	public float		bushMinY	= 0F;
	public float		bushMinZ	= 0.125F;
	public float		bushMaxX	= 0.875F;
	public float		bushMaxY	= 0.875F;
	public float		bushMaxZ	= 0.875F;
	
	public ItemStack	drop;
	
	public BlockCustomBush()
	{
		super(DEFAULT_NAMES, null);
		this.setTickRandomly(true);
		this.fullGrownMetadata = 3;
		this.setTicksToGrow(180 * 20);
	}
	
	public BlockCustomBush setItem(ItemStack item)
	{
		this.drop = item;
		return this;
	}
	
	public BlockCustomBush setFullGrownMetadata(int metadata)
	{
		this.fullGrownMetadata = metadata;
		return this;
	}
	
	public BlockCustomBush setTicksToGrow(int ticks)
	{
		this.growChance = 1F / (this.fullGrownMetadata + 1F);
		this.tickRate = (int) (ticks * this.growChance);
		return this;
	}
	
	public BlockCustomBush setBushBounds(float minX, float minY, float minZ, float maxX, float maxY, float maxZ)
	{
		this.bushMinX = minX;
		this.bushMinY = minY;
		this.bushMinZ = minZ;
		this.bushMaxX = maxX;
		this.bushMaxY = maxY;
		this.bushMaxZ = maxZ;
		return this;
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos)
	{
		if (world.getBlockState(pos).get >= this.fullGrownMetadata)
		{
			this.setBlockBounds(this.bushMinX, this.bushMinY, this.bushMinZ, this.bushMaxX, this.bushMaxY, this.bushMaxZ);
		}
		else
		{
			this.setBlockBounds(0.3F, 0F, 0.3F, 0.7F, 0.9F, 0.7F);
		}
	}
	
	@Override
	public float getBlockHardness(World world, int x, int y, int z)
	{
		return world.getBlockMetadata(x, y, z) >= this.fullGrownMetadata ? 0.6F : 0.2F;
	}
	
	@Override
	public int tickRate(World world)
	{
		return this.tickRate;
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random random)
	{
		super.updateTick(world, x, y, z, random);
		
		int i = world.getBlockMetadata(x, y, z);
		if (i < this.fullGrownMetadata && random.nextFloat() < this.growChance)
		{
			world.setBlockMetadataWithNotify(x, y, z, i + 1, 2);
		}
	}
	
	@Override
	public Icon getIcon(int side, int metadata)
	{
		return this.blockIcon;
	}
	
	@Override
	public void registerBlockIcons(IIconRegister iconRegister)
	{
		this.blockIcon = iconRegister.registerIcon(this.textureName);
		this.stemIcon = iconRegister.registerIcon(this.stemIconName);
	}
	
	@Override
	public int getRenderType()
	{
		return CSLibProxy.CUSTOMBUSH_RENDER_ID;
	}
	
	@Override
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side)
	{
		return true;
	}
	
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	// canApplyBonemeal
	@Override
	public boolean func_149851_a(World world, int x, int y, int z, boolean flag)
	{
		return world.getBlockMetadata(x, y, z) < this.fullGrownMetadata;
	}
	
	// canFertilize
	@Override
	public boolean func_149852_a(World world, Random random, int x, int y, int z)
	{
		return random.nextFloat() < this.growChance;
	}
	
	// fertilize
	@Override
	public void func_149853_b(World world, Random random, int x, int y, int z)
	{
		world.setBlockMetadataWithNotify(x, y, z, this.fullGrownMetadata, 2);
	}
	
	@Override
	public Item getItemDropped(int metadata, Random random, int fortune)
	{
		return this.drop == null ? null : this.drop.getItem();
	}
	
	@Override
	public int quantityDropped(int metadata, int fortune, Random random)
	{
		return this.drop == null ? 0 : this.drop.stackSize;
	}
	
	@Override
	public int damageDropped(int metadata)
	{
		return this.drop == null ? 0 : this.drop.getItemDamage();
	}
	
	@Override
	public boolean isShearable(ItemStack stack, IBlockAccess world, int x, int y, int z)
	{
		return true;
	}
	
	@Override
	public ArrayList<ItemStack> onSheared(ItemStack stack, IBlockAccess world, int x, int y, int z, int fortune)
	{
		ArrayList<ItemStack> list = new ArrayList();
		list.add(new ItemStack(this, 1, 0));
		return list;
	}
}
