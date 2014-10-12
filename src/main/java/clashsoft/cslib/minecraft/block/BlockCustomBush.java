package clashsoft.cslib.minecraft.block;

import java.util.ArrayList;
import java.util.Random;

import clashsoft.cslib.minecraft.common.CSLibProxy;

import net.minecraft.block.IGrowable;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

public class BlockCustomBush extends BlockCustomPlant implements IGrowable, IShearable
{
	/** The metadata value this block has to reach to by fully grown. */
	public int			fullGrownMetadata;
	protected int		tickRate;
	protected float		growChance;
	
	public ItemStack	drop;
	
	public String		bushIconName;
	public IIcon		bushIcon;
	
	public BlockCustomBush(String bushIconName, String stemIconName)
	{
		super(DEFAULT_NAMES, null);
		this.setTickRandomly(true);
		this.setBlockTextureName(stemIconName);
		this.bushIconName = bushIconName;
		this.fullGrownMetadata = 3;
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
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
	{
		if (world.getBlockMetadata(x, y, z) >= this.fullGrownMetadata)
		{
			this.setBlockBounds(0.1F, 0F, 0.1F, 0.9F, 0.9F, 0.9F);
		}
		else
		{
			this.setBlockBounds(0.3F, 0F, 0.3F, 0.7F, 0.9F, 0.7F);
		}
		this.setBlockBoundsForItemRender();
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
	public IIcon getIcon(int side, int metadata)
	{
		return this.blockIcon;
	}
	
	@Override
	public void registerBlockIcons(IIconRegister iconRegister)
	{
		this.blockIcon = iconRegister.registerIcon(this.textureName);
		this.bushIcon = iconRegister.registerIcon(this.bushIconName);
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
	public boolean isShearable(ItemStack stack, IBlockAccess world, int x, int y, int z)
	{
		return world.getBlockMetadata(x, y, z) >= this.fullGrownMetadata;
	}
	
	@Override
	public ArrayList<ItemStack> onSheared(ItemStack stack, IBlockAccess world, int x, int y, int z, int fortune)
	{
		ArrayList<ItemStack> list = new ArrayList();
		Item item = this.drop.getItem();
		int metadata = this.drop.getItemDamage();
		for (int i = 0; i < this.drop.stackSize; i++)
		{
			list.add(new ItemStack(item, 1, metadata));
		}
		return list;
	}
}
