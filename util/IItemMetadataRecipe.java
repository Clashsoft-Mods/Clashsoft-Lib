package clashsoft.clashsoftapi.util;

/**
 * The Interface IItemMetadataRecipe.
 */
public interface IItemMetadataRecipe
{
	
	/**
	 * Gets the crafting type.
	 * 
	 * @return the crafting type
	 */
	public int getCraftingType();
	
	/**
	 * Gets the amount.
	 * 
	 * @return the amount
	 */
	public int getAmount();
	
	/**
	 * Gets the data.
	 * 
	 * @return the data
	 */
	public Object[] getData();
}
