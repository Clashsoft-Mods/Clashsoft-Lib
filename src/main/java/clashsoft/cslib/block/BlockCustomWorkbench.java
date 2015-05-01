package clashsoft.cslib.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;

public class BlockCustomWorkbench extends Block implements ICustomBlock
{
	public String[]				names;
	public IInteractionObject[]	guis;
	
	public BlockCustomWorkbench(int blockID, String[] names, IInteractionObject[] guis)
	{
		super(Material.wood);
		this.setStepSound(soundTypeWood);
		this.setCreativeTab(CreativeTabs.tabDecorations);
		this.names = names;
		this.guis = guis;
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
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			player.displayGui(this.guis[(int) state.getValue(CustomBlock.typeProperty)]);
			return true;
		}
		return false;
	}
	
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list)
	{
		for (int i = 0; i < this.names.length; i++)
		{
			list.add(new ItemStack(item, 1, i));
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
