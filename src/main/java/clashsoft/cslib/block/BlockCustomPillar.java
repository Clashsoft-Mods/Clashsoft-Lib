package clashsoft.cslib.block;

import java.util.List;

import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCustomPillar extends BlockRotatedPillar implements ICustomBlock
{
	public String[]	names;
	
	public BlockCustomPillar(Material material, String name)
	{
		this(material, new String[] { name });
	}
	
	public BlockCustomPillar(Material material, String[] names)
	{
		super(material);
		this.names = names;
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return (int) state.getValue(CustomBlock.typeProperty);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(CustomBlock.typeProperty, meta);
	}
	
	@Override
	public int getDamageValue(World world, BlockPos pos)
	{
		return (int) world.getBlockState(pos).getValue(CustomBlock.typeProperty);
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
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs creativeTab, List list)
	{
		for (int i = 0; i < this.names.length; i++)
		{
			list.add(new ItemStack(this, 1, i));
		}
	}
}
