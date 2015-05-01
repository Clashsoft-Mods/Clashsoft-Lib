package clashsoft.cslib.block.ore;

import java.util.List;
import java.util.Random;

import net.minecraft.block.BlockOre;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import clashsoft.cslib.block.CustomBlock;
import clashsoft.cslib.block.ICustomBlock;
import dyvil.math.MathUtils;

public class BlockOre2 extends BlockOre implements ICustomBlock
{
	private String		bases;
	private IProperty	oreType;
	
	public BlockOre2(String type)
	{
		this.bases = type;
		this.oreType = PropertyInteger.create("ore_type", 0, OreBase.getArray(type).length);
	}
	
	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, this.oreType);
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return (int) state.getValue(this.oreType);
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(this.oreType, meta);
	}
	
	public OreBase getBase(int metadata)
	{
		return OreBase.getOreBase(this.bases, metadata);
	}
	
	public OreBase getBase(IBlockState state)
	{
		return OreBase.getOreBase(this.bases, (Integer) state.getValue(this.oreType));
	}
	
	@Override
	public int getHarvestLevel(IBlockState state)
	{
		int i = super.getHarvestLevel(state);
		OreBase base = this.getBase(state);
		if (base != null)
		{
			i += base.harvestLevel;
			return i < 0 ? 0 : i;
		}
		return i;
	}
	
	@Override
	public String getHarvestTool(IBlockState state)
	{
		OreBase base = this.getBase(state);
		if (base != null)
			return base.harvestTool;
		return super.getHarvestTool(state);
	}
	
	@Override
	public boolean isToolEffective(String type, IBlockState state)
	{
		OreBase base = this.getBase(state);
		if (base != null)
			return type.equals(base.harvestTool);
		return super.isToolEffective(type, state);
	}
	
	@Override
	public float getBlockHardness(World world, BlockPos pos)
	{
		float f = super.getBlockHardness(world, pos);
		OreBase base = this.getBase(world.getBlockState(pos));
		if (base != null)
			return base.hardness + f;
		return f;
	}
	
	@Override
	public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion)
	{
		float f = super.getExplosionResistance(world, pos, exploder, explosion);
		OreBase base = this.getBase(world.getBlockState(pos));
		if (base != null)
			return base.resistance + f;
		return f;
	}
	
	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random)
	{
		int i = super.quantityDropped(state, fortune, random);
		OreBase base = this.getBase(state);
		if (base != null)
			return MathUtils.ceil(base.amountMultiplier * i);
		return i;
	}
	
	@Override
	public int damageDropped(IBlockState state)
	{
		if (this == Blocks.lapis_ore)
			return 4;
		return 0;
	}
	
	@Override
	public int getExpDrop(IBlockAccess world, BlockPos pos, int fortune)
	{
		int i = super.getExpDrop(world, pos, fortune);
		OreBase base = this.getBase(world.getBlockState(pos));
		if (base != null)
			return MathUtils.ceil(base.xpMultiplier * i);
		return i;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		OreBase base = this.getBase(stack.getItemDamage());
		if (base != null)
			return base.getUnlocalizedName(this);
		return this.getUnlocalizedName();
	}
	
	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> list)
	{
		CustomBlock.addInformation(this, stack, list);
	}
	
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list)
	{
		if (this.bases == null)
			return;
		
		OreBase[] bases = OreBase.getOreBases(this.bases);
		for (int i = 0; i < bases.length; i++)
		{
			OreBase base = bases[i];
			if (base != null)
			{
				list.add(new ItemStack(item, 1, i));
			}
		}
	}
}
