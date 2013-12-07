package clashsoft.cslib.minecraft.item;

import clashsoft.cslib.reflect.CSReflection;

import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.ItemArmor;

public class ItemCustomArmor extends ItemArmor
{
	public ItemCustomArmor(int itemID, EnumArmorMaterial material, int renderIndex, int armorType)
	{
		super(itemID, material, renderIndex, armorType & 3);
		CSReflection.setValue(this, armorType, 4);
		CSReflection.setValue(this, getDamageReductionAmount(material), 5);
	}
	
	public float getDamageReductionAmount(EnumArmorMaterial material)
	{
		return material.getDamageReductionAmount(this.armorType & 3);
	}
}
