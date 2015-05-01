package clashsoft.cslib.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockCustomSlab extends BlockSlab implements ICustomBlock
{
	public BlockCustomSlab	otherSlab;
	
	public String[]			names;
	
	public BlockCustomSlab(Material material, String[] names, boolean doubleSlab)
	{
		super(material);
		
		this.names = names;
		this.fullBlock = doubleSlab;
	}
	
	public static void bind(BlockCustomSlab halfSlab, BlockCustomSlab doubleSlab)
	{
		halfSlab.otherSlab = doubleSlab;
		doubleSlab.otherSlab = halfSlab;
	}
	
	@Override
	public boolean isDouble()
	{
		return this.fullBlock;
	}
	
	@Override
	public IProperty func_176551_l()
	{
		return CustomBlock.typeProperty;
	}
	
	@Override
	public Object func_176553_a(ItemStack stack)
	{
		return this.names[stack.getItemDamage()];
	}
	
	@Override
	public String getFullSlabName(int meta)
	{
		return this.names[meta];
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		IBlockState iblockstate = this.getDefaultState().withProperty(CustomBlock.typeProperty, meta);
		
		if (this.fullBlock)
		{
			iblockstate = iblockstate.withProperty(BlockStoneSlab.SEAMLESS_PROP, (meta & 8) != 0);
		}
		else
		{
			iblockstate = iblockstate.withProperty(HALF_PROP, (meta & 8) == 0 ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP);
		}
		
		return iblockstate;
	}
	
	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state)
	{
		int i = (int) state.getValue(CustomBlock.typeProperty);
		
		if (this.isDouble())
		{
			if ((boolean) state.getValue(BlockStoneSlab.SEAMLESS_PROP))
			{
				i |= 8;
			}
		}
		else if (state.getValue(HALF_PROP) == BlockSlab.EnumBlockHalf.TOP)
		{
			i |= 8;
		}
		
		return i;
	}
	
	@Override
	public Item getItem(World world, BlockPos pos)
	{
		return this.fullBlock ? this.otherSlab.getItem(world, pos) : super.getItem(world, pos);
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random random, int fortune)
	{
		return this.fullBlock ? this.otherSlab.getItemDropped(state, random, fortune) : super.getItemDropped(state, random, fortune);
	}
	
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list)
	{
		for (int j = 0; j < this.names.length; ++j)
		{
			list.add(new ItemStack(item, 1, j));
		}
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
