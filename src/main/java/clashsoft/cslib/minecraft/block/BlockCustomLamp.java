package clashsoft.cslib.minecraft.block;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCustomLamp extends CustomBlock
{
	private final boolean	isActive;
	
	public Block			otherLamp;
	
	public int[]			lightValues;
	
	public BlockCustomLamp(String[] names, Object icons, boolean active)
	{
		super(Material.redstoneLight, names, icons, null);
		this.isActive = active;
		
		if (active)
		{
			this.lightValue = 15;
		}
	}
	
	public static void bind(BlockCustomLamp off, BlockCustomLamp on)
	{
		off.otherLamp = on;
		on.otherLamp = off;
	}
	
	public void setLightValue(int metadata, float value)
	{
		this.setLightValue(metadata, (int) (value * 15F));
	}
	
	public void setLightValue(int metadata, int value)
	{
		if (this.lightValues == null)
		{
			this.lightValues = new int[16];
		}
		this.lightValues[metadata] = value;
	}
	
	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z)
	{
		int metadata = world.getBlockMetadata(x, y, z);
		if (this.lightValues == null || metadata < 0 || metadata >= this.lightValues.length)
		{
			return this.lightValue;
		}
		return this.lightValues[metadata];
	}
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z)
	{
		if (!world.isRemote)
		{
			if (this.isActive)
			{
				if (!world.isBlockIndirectlyGettingPowered(x, y, z))
				{
					world.setBlock(x, y, z, this.otherLamp, world.getBlockMetadata(x, y, z), 2);
				}
			}
			else if (world.isBlockIndirectlyGettingPowered(x, y, z))
			{
				world.setBlock(x, y, z, this.otherLamp, 0, 2);
			}
		}
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		if (!world.isRemote)
		{
			if (this.isActive)
			{
				if (!world.isBlockIndirectlyGettingPowered(x, y, z))
				{
					world.setBlock(x, y, z, this.otherLamp, world.getBlockMetadata(x, y, z), 2);
				}
			}
			else if (world.isBlockIndirectlyGettingPowered(x, y, z))
			{
				world.setBlock(x, y, z, this.otherLamp, world.getBlockMetadata(x, y, z), 2);
			}
		}
	}
	
	private Item getItem()
	{
		return Item.getItemFromBlock(this.isActive ? this.otherLamp : this);
	}
	
	@Override
	public Item getItemDropped(int metadata, Random random, int fortune)
	{
		return this.getItem();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World world, int x, int y, int z)
	{
		return this.getItem();
	}
	
	@Override
	protected ItemStack createStackedBlock(int metadata)
	{
		return new ItemStack(this.getItem(), 1, metadata);
	}
}
