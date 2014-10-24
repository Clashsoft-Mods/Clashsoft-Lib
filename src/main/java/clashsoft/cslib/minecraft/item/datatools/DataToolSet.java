package clashsoft.cslib.minecraft.item.datatools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import clashsoft.cslib.minecraft.stack.CSStacks;

import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * The Class DataToolSet.
 */
public class DataToolSet
{
	public static Map<String, ToolMaterial>	nameToMaterial	= new HashMap();
	public static Map<ToolMaterial, String>	materialToName	= new HashMap();
	
	public List<ToolMaterial>				materials		= new ArrayList();
	
	/** The sword. */
	public ItemDataSword					sword;
	
	/** The shovel. */
	public ItemDataSpade					shovel;
	
	/** The pickaxe. */
	public ItemDataPickaxe					pickaxe;
	
	/** The axe. */
	public ItemDataAxe						axe;
	
	/** The hoe. */
	public ItemDataHoe						hoe;
	
	/**
	 * Instantiates a new data tool set.
	 * 
	 * @param sword
	 *            the sword
	 * @param shovel
	 *            the shovel
	 * @param pickaxe
	 *            the pickaxe
	 * @param axe
	 *            the axe
	 * @param hoe
	 *            the hoe
	 */
	public DataToolSet(ItemDataSword sword, ItemDataSpade shovel, ItemDataPickaxe pickaxe, ItemDataAxe axe, ItemDataHoe hoe)
	{
		this.sword = sword;
		this.shovel = shovel;
		this.pickaxe = pickaxe;
		this.axe = axe;
		this.hoe = hoe;
	}
	
	/**
	 * Register tool material.
	 * 
	 * @param material
	 *            the material
	 * @param name
	 *            the name
	 */
	public void registerToolMaterial(ToolMaterial material, String name)
	{
		this.materials.add(material);
		DataToolSet.materialToName.put(material, name);
		DataToolSet.nameToMaterial.put(name, material);
	}
	
	public static ToolMaterial getToolMaterial(ItemStack stack)
	{
		return nameToMaterial.get(getMaterialName(stack));
	}
	
	public static String getMaterialName(ItemStack stack)
	{
		if (stack != null)
		{
			NBTTagCompound nbt = stack.getTagCompound();
			return nbt.getString("ToolMaterial");
		}
		return null;
	}
	
	public static ItemStack setToolMaterial(ItemStack stack, ToolMaterial material)
	{
		return setMaterialName(stack, materialToName.get(material));
	}
	
	public static ItemStack setMaterialName(ItemStack stack, String name)
	{
		if (stack != null)
		{
			NBTTagCompound nbt = CSStacks.getNBT(stack);
			nbt.setString("ToolMaterial", name);
		}
		return stack;
	}
}
