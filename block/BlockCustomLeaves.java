package clashsoft.clashsoftapi.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

public class BlockCustomLeaves extends BlockLeavesBase implements IShearable, ICustomBlock
{
	public String[]		names, iconNames;
	
	public ItemStack[]	appleStacks		= new ItemStack[4];
	public ItemStack[]	saplingStacks	= new ItemStack[4];
	public boolean[]	isColored		= new boolean[4];
	
	public Icon[]		icons, opaqueIcons;
	public int[]		adjacentTreeBlocks;
	
	public BlockCustomLeaves(int blockID, String[] names, String[] icons)
	{
		super(blockID, Material.leaves, false);
		this.setTickRandomly(true);
		this.setStepSound(soundGrassFootstep);
		
		this.names = names;
		this.iconNames = icons;
	}
	
	public BlockCustomLeaves setAppleStacks(ItemStack... appleStacks)
	{
		this.appleStacks = appleStacks;
		return this;
	}
	
	public BlockCustomLeaves setSaplingStacks(ItemStack... saplingStacks)
	{
		this.saplingStacks = saplingStacks;
		return this;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getBlockColor()
	{
		if (isColored[0])
		{
			double d0 = 0.5D;
			double d1 = 1.0D;
			return ColorizerFoliage.getFoliageColor(d0, d1);
		}
		return super.getBlockColor();
	}
	
	/**
	 * Returns the color this block should be rendered. Used by leaves.
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderColor(int par1)
	{
		return isColored[par1 & 3] ? ColorizerFoliage.getFoliageColorBasic() : super.getRenderColor(par1);
	}
	
	/**
	 * Returns a integer with hex for 0xrrggbb with this color multiplied
	 * against the blocks color. Note only called when first determining what to
	 * render.
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		int l = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
		
		if (isColored[l & 3])
		{
			if ((l & 3) == 1)
				return ColorizerFoliage.getFoliageColorPine();
			else if ((l & 3) == 2)
				return ColorizerFoliage.getFoliageColorBirch();
			else
			{
				int i1 = 0;
				int j1 = 0;
				int k1 = 0;
				
				for (int l1 = -1; l1 <= 1; ++l1)
				{
					for (int i2 = -1; i2 <= 1; ++i2)
					{
						int j2 = par1IBlockAccess.getBiomeGenForCoords(par2 + i2, par4 + l1).getBiomeFoliageColor();
						i1 += (j2 & 16711680) >> 16;
						j1 += (j2 & 65280) >> 8;
						k1 += j2 & 255;
					}
				}
				
				return (i1 / 9 & 255) << 16 | (j1 / 9 & 255) << 8 | k1 / 9 & 255;
			}
		}
		return super.colorMultiplier(par1IBlockAccess, par2, par3, par4);
	}
	
	/**
	 * Called on server worlds only when the block has been replaced by a
	 * different block ID, or the same block with a different metadata value,
	 * but before the new metadata value is set. Args: World, x, y, z, old block
	 * ID, old metadata
	 */
	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
	{
		byte b0 = 1;
		int j1 = b0 + 1;
		
		if (par1World.checkChunksExist(par2 - j1, par3 - j1, par4 - j1, par2 + j1, par3 + j1, par4 + j1))
		{
			for (int k1 = -b0; k1 <= b0; ++k1)
			{
				for (int l1 = -b0; l1 <= b0; ++l1)
				{
					for (int i2 = -b0; i2 <= b0; ++i2)
					{
						int j2 = par1World.getBlockId(par2 + k1, par3 + l1, par4 + i2);
						
						if (Block.blocksList[j2] != null)
							Block.blocksList[j2].beginLeavesDecay(par1World, par2 + k1, par3 + l1, par4 + i2);
					}
				}
			}
		}
	}
	
	/**
	 * Ticks the block if it's been scheduled
	 */
	@Override
	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		if (!par1World.isRemote)
		{
			int l = par1World.getBlockMetadata(par2, par3, par4);
			
			if ((l & 8) != 0 && (l & 4) == 0)
			{
				byte b0 = 4;
				int i1 = b0 + 1;
				byte b1 = 32;
				int j1 = b1 * b1;
				int k1 = b1 / 2;
				
				if (this.adjacentTreeBlocks == null)
				{
					this.adjacentTreeBlocks = new int[b1 * b1 * b1];
				}
				
				int l1;
				
				if (par1World.checkChunksExist(par2 - i1, par3 - i1, par4 - i1, par2 + i1, par3 + i1, par4 + i1))
				{
					int i2;
					int j2;
					int k2;
					
					for (l1 = -b0; l1 <= b0; ++l1)
					{
						for (i2 = -b0; i2 <= b0; ++i2)
						{
							for (j2 = -b0; j2 <= b0; ++j2)
							{
								k2 = par1World.getBlockId(par2 + l1, par3 + i2, par4 + j2);
								
								Block block = Block.blocksList[k2];
								
								if (block != null && block.canSustainLeaves(par1World, par2 + l1, par3 + i2, par4 + j2))
								{
									this.adjacentTreeBlocks[(l1 + k1) * j1 + (i2 + k1) * b1 + j2 + k1] = 0;
								}
								else if (block != null && block.isLeaves(par1World, par2 + l1, par3 + i2, par4 + j2))
								{
									this.adjacentTreeBlocks[(l1 + k1) * j1 + (i2 + k1) * b1 + j2 + k1] = -2;
								}
								else
								{
									this.adjacentTreeBlocks[(l1 + k1) * j1 + (i2 + k1) * b1 + j2 + k1] = -1;
								}
							}
						}
					}
					
					for (l1 = 1; l1 <= 4; ++l1)
					{
						for (i2 = -b0; i2 <= b0; ++i2)
						{
							for (j2 = -b0; j2 <= b0; ++j2)
							{
								for (k2 = -b0; k2 <= b0; ++k2)
								{
									if (this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1] == l1 - 1)
									{
										if (this.adjacentTreeBlocks[(i2 + k1 - 1) * j1 + (j2 + k1) * b1 + k2 + k1] == -2)
										{
											this.adjacentTreeBlocks[(i2 + k1 - 1) * j1 + (j2 + k1) * b1 + k2 + k1] = l1;
										}
										
										if (this.adjacentTreeBlocks[(i2 + k1 + 1) * j1 + (j2 + k1) * b1 + k2 + k1] == -2)
										{
											this.adjacentTreeBlocks[(i2 + k1 + 1) * j1 + (j2 + k1) * b1 + k2 + k1] = l1;
										}
										
										if (this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1 - 1) * b1 + k2 + k1] == -2)
										{
											this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1 - 1) * b1 + k2 + k1] = l1;
										}
										
										if (this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1 + 1) * b1 + k2 + k1] == -2)
										{
											this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1 + 1) * b1 + k2 + k1] = l1;
										}
										
										if (this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1) * b1 + (k2 + k1 - 1)] == -2)
										{
											this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1) * b1 + (k2 + k1 - 1)] = l1;
										}
										
										if (this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1 + 1] == -2)
										{
											this.adjacentTreeBlocks[(i2 + k1) * j1 + (j2 + k1) * b1 + k2 + k1 + 1] = l1;
										}
									}
								}
							}
						}
					}
				}
				
				l1 = this.adjacentTreeBlocks[k1 * j1 + k1 * b1 + k1];
				
				if (l1 >= 0)
				{
					par1World.setBlockMetadataWithNotify(par2, par3, par4, l & -9, 4);
				}
				else
				{
					this.removeLeaves(par1World, par2, par3, par4);
				}
			}
		}
	}
	
	/**
	 * A randomly called display update to be able to add particles or other
	 * items for display
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		if (par1World.canLightningStrikeAt(par2, par3 + 1, par4) && !par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4) && par5Random.nextInt(15) == 1)
		{
			double d0 = par2 + par5Random.nextFloat();
			double d1 = par3 - 0.05D;
			double d2 = par4 + par5Random.nextFloat();
			par1World.spawnParticle("dripWater", d0, d1, d2, 0.0D, 0.0D, 0.0D);
		}
	}
	
	public void removeLeaves(World par1World, int par2, int par3, int par4)
	{
		this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
		par1World.setBlockToAir(par2, par3, par4);
	}
	
	/**
	 * Returns the quantity of items to drop on block destruction.
	 */
	@Override
	public int quantityDropped(Random par1Random)
	{
		return par1Random.nextInt(20) == 0 ? 1 : 0;
	}
	
	/**
	 * Returns the ID of the items to drop on destruction.
	 */
	@Override
	public int idDropped(int par1, Random par2Random, int par3)
	{
		ItemStack stack = saplingStacks[par1 % saplingStacks.length];
		return stack != null ? stack.itemID : 0;
	}
	
	/**
	 * Drops the block items with a specified chance of dropping the specified
	 * items
	 */
	@Override
	public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
	{
		if (!par1World.isRemote)
		{
			int j1 = 20;
			int metadata = par1World.getBlockMetadata(par2, par3, par4);
			
			if ((par5 & 3) == 3)
			{
				j1 = 40;
			}
			
			if (par7 > 0)
			{
				j1 -= 2 << par7;
				
				if (j1 < 10)
				{
					j1 = 10;
				}
			}
			
			if (par1World.rand.nextInt(j1) == 0)
			{
				this.dropBlockAsItem_do(par1World, par2, par3, par4, this.saplingStacks[metadata]);
			}
			
			j1 = 200;
			
			if (par7 > 0)
			{
				j1 -= 10 << par7;
				
				if (j1 < 40)
				{
					j1 = 40;
				}
			}
			
			if ((par5 & 3) == 0 && par1World.rand.nextInt(j1) == 0)
			{
				this.dropBlockAsItem_do(par1World, par2, par3, par4, this.appleStacks[metadata]);
			}
		}
	}
	
	/**
	 * Called when the player destroys a block with an item that can harvest it.
	 * (i, j, k) are the coordinates of the block and l is the block's
	 * subtype/damage.
	 */
	@Override
	public void harvestBlock(World par1World, EntityPlayer par2EntityPlayer, int par3, int par4, int par5, int par6)
	{
		super.harvestBlock(par1World, par2EntityPlayer, par3, par4, par5, par6);
	}
	
	/**
	 * Determines the damage on the item the block drops. Used in cloth and
	 * wood.
	 */
	@Override
	public int damageDropped(int par1)
	{
		return par1 & 3;
	}
	
	/**
	 * Is this block (a) opaque and (b) a full 1m cube? This determines whether
	 * or not to render the shared face of two adjacent blocks and also whether
	 * the player can attach torches, redstone wire, etc to this block.
	 */
	@Override
	public boolean isOpaqueCube()
	{
		return !this.graphicsLevel;
	}
	
	/**
	 * From the specified side and block metadata retrieves the blocks texture.
	 * Args: side, metadata
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int par1, int par2)
	{
		this.graphicsLevel = Minecraft.getMinecraft().gameSettings.fancyGraphics;
		return this.graphicsLevel ? this.icons[par2 & 3] : this.opaqueIcons[par2 & 3];
	}
	
	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood
	 * returns 4 blocks)
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for (int i = 0; i < names.length; i++)
			par3List.add(new ItemStack(this, 1, i));
	}
	
	/**
	 * Returns an item stack containing a single instance of the current block
	 * type. 'i' is the block's subtype/damage and is ignored for blocks which
	 * do not support subtypes. Blocks which cannot be harvested should return
	 * null.
	 */
	@Override
	protected ItemStack createStackedBlock(int par1)
	{
		return new ItemStack(this.blockID, 1, par1 & 3);
	}
	
	/**
	 * When this method is called, your block should register all the icons it
	 * needs with the given IconRegister. This is the only chance you get to
	 * register icons.
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
		this.icons = new Icon[iconNames.length];
		this.opaqueIcons = new Icon[iconNames.length];
		
		for (int i = 0; i < iconNames.length; ++i)
		{
			this.icons[i] = par1IconRegister.registerIcon(iconNames[i]);
			this.opaqueIcons[i] = par1IconRegister.registerIcon(iconNames[i] + "_opaque");
		}
	}
	
	@Override
	public boolean isShearable(ItemStack item, World world, int x, int y, int z)
	{
		return true;
	}
	
	@Override
	public ArrayList<ItemStack> onSheared(ItemStack item, World world, int x, int y, int z, int fortune)
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		ret.add(createStackedBlock(world.getBlockMetadata(x, y, z)));
		return ret;
	}
	
	@Override
	public void beginLeavesDecay(World world, int x, int y, int z)
	{
		world.setBlockMetadataWithNotify(x, y, z, world.getBlockMetadata(x, y, z) | 8, 4);
	}
	
	@Override
	public boolean isLeaves(World world, int x, int y, int z)
	{
		return true;
	}
	
	@Override
	public void addNames()
	{
		for (int i = 0; i < this.names.length; i++)
		{
			LanguageRegistry.addName(new ItemStack(this, 1, i), this.names[i]);
		}
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> list)
	{
	}
}
