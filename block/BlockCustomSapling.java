package clashsoft.clashsoftapi.block;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.BlockSapling;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.TerrainGen;

public abstract class BlockCustomSapling extends BlockSapling implements ICustomBlock
{
	public String[]	names, iconNames;
	
	@SideOnly(Side.CLIENT)
	public Icon[]	icons;
	
	public BlockCustomSapling(int blockID, String[] names, String[] icons)
	{
		super(blockID);
		this.setBlockBounds(0.1F, 0.0F, 0.1F, 0.9F, 0.8F, 0.9F);
		this.setHardness(0F);
		this.setStepSound(soundGrassFootstep);
		
		this.names = names;
		this.iconNames = icons;
	}
	
	/**
	 * Ticks the block if it's been scheduled
	 */
	@Override
	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		if (!par1World.isRemote)
		{
			super.updateTick(par1World, par2, par3, par4, par5Random);
			
			if (par1World.getBlockLightValue(par2, par3 + 1, par4) >= 9 && par5Random.nextInt(7) == 0)
			{
				this.markOrGrowMarked(par1World, par2, par3, par4, par5Random);
			}
		}
	}
	
	/**
	 * From the specified side and block metadata retrieves the blocks texture.
	 * Args: side, metadata
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int par1, int par2)
	{
		return this.icons[par2 & 3];
	}
	
	@Override
	public void markOrGrowMarked(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		int l = par1World.getBlockMetadata(par2, par3, par4);
		
		if ((l & 8) == 0)
		{
			par1World.setBlockMetadataWithNotify(par2, par3, par4, l | 8, 4);
		}
		else
		{
			this.growTree(par1World, par2, par3, par4, par5Random);
		}
	}
	
	/**
	 * Attempts to grow a sapling into a tree
	 */
	@Override
	public void growTree(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		if (!TerrainGen.saplingGrowTree(par1World, par5Random, par2, par3, par4))
			return;
		
		int l = par1World.getBlockMetadata(par2, par3, par4) & 3;
		WorldGenerator worldgen = getWorldGen(par1World, par2, par3, par4, par5Random);
		int i1 = 0;
		int j1 = 0;
		boolean flag = false;
		
		if (flag)
		{
			par1World.setBlock(par2 + i1, par3, par4 + j1, 0, 0, 4);
			par1World.setBlock(par2 + i1 + 1, par3, par4 + j1, 0, 0, 4);
			par1World.setBlock(par2 + i1, par3, par4 + j1 + 1, 0, 0, 4);
			par1World.setBlock(par2 + i1 + 1, par3, par4 + j1 + 1, 0, 0, 4);
		}
		else
		{
			par1World.setBlock(par2, par3, par4, 0, 0, 4);
		}
		
		if (!worldgen.generate(par1World, par5Random, par2 + i1, par3, par4 + j1))
		{
			if (flag)
			{
				par1World.setBlock(par2 + i1, par3, par4 + j1, this.blockID, l, 4);
				par1World.setBlock(par2 + i1 + 1, par3, par4 + j1, this.blockID, l, 4);
				par1World.setBlock(par2 + i1, par3, par4 + j1 + 1, this.blockID, l, 4);
				par1World.setBlock(par2 + i1 + 1, par3, par4 + j1 + 1, this.blockID, l, 4);
			}
			else
			{
				par1World.setBlock(par2, par3, par4, this.blockID, l, 4);
			}
		}
	}
	
	public abstract WorldGenerator getWorldGen(World world, int x, int y, int z, Random random);
	
	/**
	 * Determines if the same sapling is present at the given location.
	 */
	@Override
	public boolean isSameSapling(World par1World, int par2, int par3, int par4, int par5)
	{
		return par1World.getBlockId(par2, par3, par4) == this.blockID && (par1World.getBlockMetadata(par2, par3, par4) & 3) == par5;
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
	 * When this method is called, your block should register all the icons it
	 * needs with the given IconRegister. This is the only chance you get to
	 * register icons.
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
		this.icons = new Icon[iconNames.length];
		for (int i = 0; i < iconNames.length; ++i)
		{
			this.icons[i] = par1IconRegister.registerIcon(iconNames[i]);
		}
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
