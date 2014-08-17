package clashsoft.cslib.minecraft.block;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class BlockCustomCrops extends BlockCustomPlant implements IGrowable
{
	public ItemStack	seed;
	public ItemStack	crop;
	public int			fullGrownMetadata;
	
	public IIcon[]		icons;
	
	public BlockCustomCrops(int fullGrownMetadata)
	{
		super(DEFAULT_NAMES, null);
		this.setTickRandomly(true);
		this.setBlockBounds(0F, 0F, 0F, 1F, 0.25F, 1F);
		this.setHardness(0.0F);
		this.setStepSound(Block.soundTypeGrass);
		this.disableStats();
		
		this.fullGrownMetadata = fullGrownMetadata;
	}
	
	public BlockCustomCrops setItem(ItemStack item)
	{
		this.seed = item;
		this.crop = item;
		return this;
	}
	
	public BlockCustomCrops setSeed(ItemStack seed)
	{
		this.seed = seed;
		return this;
	}
	
	public BlockCustomCrops setCrop(ItemStack crop)
	{
		this.crop = crop;
		return this;
	}
	
	@Override
	public boolean isValidGround(World world, int x, int y, int z)
	{
		return world.getBlock(x, y - 1, z).canSustainPlant(world, x, y - 1, z, ForgeDirection.UP, this);
	}
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random random)
	{
		super.updateTick(world, x, y, z, random);
		
		if (world.getBlockLightValue(x, y + 1, z) >= 9)
		{
			int metadata = world.getBlockMetadata(x, y, z);
			
			if (metadata < this.fullGrownMetadata)
			{
				float growthRate = this.getGrowthRate(world, x, y, z);
				
				if (random.nextInt((int) (25.0F / growthRate) + 1) == 0)
				{
					world.setBlockMetadataWithNotify(x, y, z, metadata + 1, 2);
				}
			}
		}
	}
	
	// canGrow
	@Override
	public boolean func_149851_a(World world, int x, int y, int z, boolean flag)
	{
		return world.getBlockMetadata(x, y, z) != this.fullGrownMetadata;
	}
	
	// consumeBonemeal
	@Override
	public boolean func_149852_a(World world, Random random, int x, int y, int z)
	{
		return true;
	}
	
	// fertilize
	@Override
	public void func_149853_b(World world, Random random, int x, int y, int z)
	{
		world.setBlockMetadataWithNotify(x, y, z, this.fullGrownMetadata, 2);
	}
	
	private float getGrowthRate(World world, int x, int y, int z)
	{
		float f = 1.0F;
		Block block1 = world.getBlock(x, y, z - 1);
		Block block2 = world.getBlock(x, y, z + 1);
		Block block3 = world.getBlock(x - 1, y, z);
		Block block4 = world.getBlock(x + 1, y, z);
		Block block5 = world.getBlock(x - 1, y, z - 1);
		Block block6 = world.getBlock(x + 1, y, z - 1);
		Block block7 = world.getBlock(x + 1, y, z + 1);
		Block block8 = world.getBlock(x - 1, y, z + 1);
		boolean neighbors1 = block1 == this || block2 == this || block3 == this || block4 == this;
		boolean neighbors2 = block5 == this || block6 == this || block7 == this || block8 == this;
		
		for (int x1 = x - 1; x1 <= x + 1; ++x1)
		{
			for (int z1 = z - 1; z1 <= z + 1; ++z1)
			{
				int y1 = y - 1;
				Block block = world.getBlock(x1, y1, z1);
				float growthRate = 0.0F;
				
				if (block != null && block.canSustainPlant(world, x1, y1, z1, ForgeDirection.UP, this))
				{
					if (block.isFertile(world, x1, y1, z1))
					{
						growthRate = 0.75F;
					}
					else
					{
						growthRate = 0.25F;
					}
				}
				
				if (x1 == x && z1 == z)
				{
					growthRate *= 4.0F;
				}
				
				f += growthRate;
			}
		}
		
		if (neighbors1 && neighbors2)
		{
			f /= 2.0F;
		}
		
		return f;
	}
	
	@Override
	public IIcon getIcon(int side, int metadata)
	{
		return metadata < this.icons.length ? this.icons[metadata] : this.icons[0];
	}
	
	@Override
	public void registerBlockIcons(IIconRegister iconRegister)
	{
		this.icons = new IIcon[this.fullGrownMetadata + 1];
		for (int i = 0; i <= this.fullGrownMetadata; i++)
		{
			this.icons[i] = iconRegister.registerIcon(this.textureName + "_" + i);
		}
	}
	
	@Override
	public int getRenderType()
	{
		return 6;
	}
	
	protected ItemStack getSeedItem()
	{
		return this.seed.copy();
	}
	
	protected ItemStack getCropItem()
	{
		return this.crop.copy();
	}
	
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
	{
		ArrayList<ItemStack> ret = super.getDrops(world, x, y, z, metadata, fortune);
		
		if (metadata >= this.fullGrownMetadata)
		{
			for (int i = 0; i < 3 + fortune; i++)
			{
				if (world.rand.nextInt(this.fullGrownMetadata * 2) <= metadata)
				{
					ret.add(this.getSeedItem());
				}
			}
		}
		
		return ret;
	}
	
	@Override
	public Item getItemDropped(int metadata, Random random, int fortune)
	{
		return metadata >= this.fullGrownMetadata ? this.getCropItem().getItem() : this.getSeedItem().getItem();
	}
	
	@Override
	public int damageDropped(int metadata)
	{
		return metadata >= this.fullGrownMetadata ? this.getCropItem().getItemDamage() : this.getSeedItem().getItemDamage();
	}
	
	@Override
	public int quantityDropped(int metadata, int fortune, Random random)
	{
		return 1;
	}
	
	@Override
	public Item getItem(World world, int x, int y, int z)
	{
		return this.seed.getItem();
	}
	
	@Override
	public int getDamageValue(World world, int x, int y, int z)
	{
		return this.seed.getItemDamage();
	}
}
