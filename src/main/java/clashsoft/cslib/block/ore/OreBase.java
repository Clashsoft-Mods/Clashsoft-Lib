package clashsoft.cslib.block.ore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import clashsoft.cslib.util.I18n;

public class OreBase
{
	protected static Map<String, OreBase[]>	oreBases		= new HashMap();
	
	public static final String				TYPE_OVERWORLD	= "overworld";
	public static final String				TYPE_NETHER		= "nether";
	public static final String				TYPE_END		= "end";
	
	public static OreBase					stone			= new OreBase("stone", 1F).register(TYPE_OVERWORLD, 0);
	public static OreBase					netherrack		= new OreBase("netherrack", 1F).register(TYPE_NETHER, 0);
	public static OreBase					endstone		= new OreBase("endstone", 1F).register(TYPE_END, 0);
	
	protected String						type;
	protected int							id;
	public final String						name;
	
	public int								harvestLevel;
	public String							harvestTool		= "pickaxe";
	public float							hardness		= 1.5F;
	public float							resistance		= 2.5F;
	public float							amountMultiplier;
	public float							xpMultiplier;
	
	public boolean							hasCustomName;
	
	public OreBase(String name)
	{
		this(name, 1F);
	}
	
	public OreBase(String name, float multiplier)
	{
		this.name = name;
		
		this.amountMultiplier = multiplier;
		this.xpMultiplier = multiplier;
	}
	
	public static OreBase getOreBase(String type, int id)
	{
		OreBase[] array = oreBases.get(type);
		if (array == null)
			return null;
		return array[id];
	}
	
	public static OreBase[] getOreBases(String type)
	{
		return oreBases.get(type);
	}
	
	protected static OreBase[] getArray(String type)
	{
		OreBase[] array = oreBases.get(type);
		if (array == null)
		{
			array = new OreBase[16];
			oreBases.put(type, array);
		}
		return array;
	}
	
	public OreBase register(String type)
	{
		OreBase[] array = getArray(type);
		int index = Arrays.binarySearch(array, null);
		if (index != -1)
		{
			array[index] = this;
			this.type = type;
			this.id = index;
			return this;
		}
		return null;
	}
	
	public OreBase register(String type, int id)
	{
		OreBase[] array = getArray(type);
		array[id] = this;
		this.type = type;
		this.id = id;
		return this;
	}
	
	public OreBase setHarvestLevel(int harvestLevel)
	{
		this.harvestLevel = harvestLevel;
		return this;
	}
	
	public OreBase setHarvestTool(String harvestTool)
	{
		this.harvestTool = harvestTool;
		return this;
	}
	
	public OreBase setHardness(float hardness)
	{
		this.hardness = hardness;
		return this;
	}
	
	public OreBase setResistance(float resistance)
	{
		this.resistance = resistance;
		return this;
	}
	
	public OreBase setAmountMultiplier(float amountMultiplier)
	{
		this.amountMultiplier = amountMultiplier;
		return this;
	}
	
	public OreBase setXpMultiplier(float xpMultiplier)
	{
		this.xpMultiplier = xpMultiplier;
		return this;
	}
	
	public OreBase setHasCustomName(boolean hasCustomName)
	{
		this.hasCustomName = hasCustomName;
		return this;
	}
	
	public String getUnlocalizedName(BlockOre2 ore)
	{
		if (this.hasCustomName)
			return ore.getUnlocalizedName() + "." + this.name;
		return ore.getUnlocalizedName();
	}
	
	public String getBaseDisplayName()
	{
		String s = this.hasCustomName ? "orebase." + this.name : "tile." + this.name + ".name";
		return I18n.getString(s);
	}
}
