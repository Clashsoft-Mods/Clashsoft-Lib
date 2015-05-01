package clashsoft.cslib.item.datatools;

import java.util.List;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.BlockPos;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import clashsoft.cslib.stack.CSStacks;

import com.google.common.collect.Multimap;

public class ItemDataTool extends ItemTool
{
	public float		toolDamage;
	
	private Set<Block>	blocksEffectiveAgainst;
	
	private String		toolType;
	public DataToolSet	toolSet;
	
	public ItemDataTool(float toolDamage, ToolMaterial material, Set<Block> blocksEffectiveAgainst, String type)
	{
		super(toolDamage, material, blocksEffectiveAgainst);
		this.blocksEffectiveAgainst = blocksEffectiveAgainst;
		this.toolType = type;
		this.toolDamage = toolDamage;
		this.setNoRepair();
	}
	
	public static ToolMaterial getToolMaterial(ItemStack stack)
	{
		if (stack != null)
			return DataToolSet.getToolMaterial(stack);
		return null;
	}
	
	public static ItemStack setToolMaterial(ItemStack stack, ToolMaterial toolMaterial)
	{
		if (stack != null)
			return DataToolSet.setToolMaterial(stack, toolMaterial);
		return stack;
	}
	
	public void registerMaterial(ToolMaterial material, String name)
	{
		this.toolSet.registerToolMaterial(material, name);
	}
	
	@Override
	public int getMaxDamage(ItemStack stack)
	{
		ToolMaterial tm = DataToolSet.getToolMaterial(stack);
		if (tm != null)
			return tm.getMaxUses();
		return this.toolMaterial.getMaxUses();
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		return StatCollector.translateToLocal(DataToolSet.getMaterialName(stack) + " " + this.toolType);
	}
	
	@Override
	public float getDigSpeed(ItemStack stack, IBlockState state)
	{
		if (this.blocksEffectiveAgainst.contains(state.getBlock()))
		{
			ToolMaterial tm = DataToolSet.getToolMaterial(stack);
			if (tm == null)
			{
				tm = this.toolMaterial;
			}
			return tm.getEfficiencyOnProperMaterial();
		}
		return 1.0F;
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase living, EntityLivingBase living2)
	{
		if (!(this instanceof ItemDataSword || this instanceof ItemDataHoe))
		{
			stack.damageItem(2, living);
		}
		return true;
	}
	
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, Block block, BlockPos pos, EntityLivingBase living)
	{
		if (block.getBlockHardness(world, pos) != 0.0D)
		{
			stack.damageItem(1, living);
		}
		return true;
	}
	
	@Override
	public Multimap getItemAttributeModifiers()
	{
		Multimap multimap = super.getItemAttributeModifiers();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(itemModifierUUID, "Tool modifier", this.toolDamage, 0));
		return multimap;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public boolean isFull3D()
	{
		return true;
	}
	
	@Override
	public int getItemEnchantability()
	{
		return this.toolMaterial.getEnchantability();
	}
	
	@Override
	public String getToolMaterialName()
	{
		return this.toolMaterial.toString();
	}
	
	@Override
	public boolean getIsRepairable(ItemStack stack, ItemStack repairItem)
	{
		ToolMaterial tm = DataToolSet.getToolMaterial(stack);
		if (tm == null)
		{
			tm = this.toolMaterial;
		}
		return CSStacks.itemEquals(repairItem, tm.getRepairItemStack());
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tab, List list)
	{
		for (ToolMaterial tm : this.toolSet.materials)
		{
			ItemStack stack = new ItemStack(this);
			list.add(DataToolSet.setToolMaterial(stack, tm));
		}
	}
}
