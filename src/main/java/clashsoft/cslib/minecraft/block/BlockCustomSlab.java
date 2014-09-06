package clashsoft.cslib.minecraft.block;

import java.util.List;
import java.util.Random;

import clashsoft.cslib.minecraft.client.icon.IIconSupplier;
import clashsoft.cslib.minecraft.client.icon.slab.SlabIconSupplier;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockCustomSlab extends BlockSlab implements ICustomBlock
{
	public BlockCustomSlab	otherSlab;
	
	public String[]			names;
	
	public IIconSupplier	iconSupplier;
	
	public BlockCustomSlab(Material material, String[] names, Object icons, boolean doubleSlab)
	{
		super(doubleSlab, material);
		
		this.names = names;
		this.iconSupplier = SlabIconSupplier.create(icons);
	}
	
	public static void bind(BlockCustomSlab halfSlab, BlockCustomSlab doubleSlab)
	{
		halfSlab.otherSlab = doubleSlab;
		doubleSlab.otherSlab = halfSlab;
	}
	
	@Override
	public IIcon getIcon(int side, int metadata)
	{
		if (this.isOpaqueCube() && (metadata & 8) != 0)
		{
			side = 1;
		}
		metadata &= 7;
		
		return this.iconSupplier.getIcon(metadata, side);
	}
	
	@Override
	public void registerBlockIcons(IIconRegister iconRegister)
	{
		this.iconSupplier.registerIcons(iconRegister);
	}
	
	@Override
	public Item getItem(World world, int x, int y, int z)
	{
		return this.isOpaqueCube() ? this.otherSlab.getItem(world, x, y, z) : super.getItem(world, x, y, z);
	}
	
	@Override
	public Item getItemDropped(int metadata, Random random, int fortune)
	{
		return this.isOpaqueCube() ? this.otherSlab.getItemDropped(metadata, random, fortune) : super.getItemDropped(metadata, random, fortune);
	}
	
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list)
	{
		for (int j = 0; j < this.names.length; ++j)
		{
			list.add(new ItemStack(item, 1, j));
		}
	}
	
	public String getFullSlabName(int metadata)
	{
		return this.func_150002_b(metadata);
	}
	
	/*
	 * getFullSlabName
	 */
	@Override
	public String func_150002_b(int metadata)
	{
		return this.names[metadata];
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return CustomBlock.getUnlocalizedName(this, stack, this.names);
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> list)
	{
		CustomBlock.addInformation(this, stack, list);
	}
}
