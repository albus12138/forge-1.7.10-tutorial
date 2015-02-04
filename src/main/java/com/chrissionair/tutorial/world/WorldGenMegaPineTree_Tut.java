package com.chrissionair.tutorial.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class WorldGenMegaPineTree_Tut extends WorldGenHugeTrees_Tut
{
    private boolean field_150542_e;
    private static final String __OBFID = "CL_00000421";

    public WorldGenMegaPineTree_Tut(boolean p_i45457_1_, boolean p_i45457_2_)
    {
        super(p_i45457_1_, 13, 15, 1, 1);
        this.field_150542_e = p_i45457_2_;// ob baseHeight zur Krone dazukommt ..
    }

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
        int l = this.func_150533_a(par2Random);// neue rel maxH�he

        if (!this.func_150537_a(par1World, par2Random, par3, par4, par5, l))// check Platz und Boden
        {
            return false;
        }
        else
        {
        	// Topkrone
            this.func_150541_c(par1World, par3, par5, par4 + l, 0, par2Random);

            // Stamm
            for (int i1 = 0; i1 < l; ++i1)
            {
                Block block = par1World.getBlock(par3, par4 + i1, par5);

                if (block.isAir(par1World, par3, par4 + i1, par5) || block.isLeaves(par1World, par3, par4 + i1, par5))
                {
                    this.setBlockAndNotifyAdequately(par1World, par3, par4 + i1, par5, Blocks.log, this.woodMetadata);
                }

                if (i1 < l - 1)
                {
                    block = par1World.getBlock(par3 + 1, par4 + i1, par5);

                    if (block.isAir(par1World, par3 + 1, par4 + i1, par5) || block.isLeaves(par1World, par3 + 1, par4 + i1, par5))
                    {
                        this.setBlockAndNotifyAdequately(par1World, par3 + 1, par4 + i1, par5, Blocks.log, this.woodMetadata);
                    }

                    block = par1World.getBlock(par3 + 1, par4 + i1, par5 + 1);

                    if (block.isAir(par1World, par3 + 1, par4 + i1, par5 + 1) || block.isLeaves(par1World, par3 + 1, par4 + i1, par5 + 1))
                    {
                        this.setBlockAndNotifyAdequately(par1World, par3 + 1, par4 + i1, par5 + 1, Blocks.log, this.woodMetadata);
                    }

                    block = par1World.getBlock(par3, par4 + i1, par5 + 1);

                    if (block.isAir(par1World, par3, par4 + i1, par5 + 1) || block.isLeaves(par1World, par3, par4 + i1, par5 + 1))
                    {
                        this.setBlockAndNotifyAdequately(par1World, par3, par4 + i1, par5 + 1, Blocks.log, this.woodMetadata);
                    }
                }
            }

            return true;
        }
    }

    // y-Wert und Spanne f�r Top Krone; achtung Wert_4 ist abs H�he, Wert_5 Spanne
    private void func_150541_c(World p_150541_1_, int p_150541_2_, int p_150541_3_, int p_150541_4_, int p_150541_5_, Random p_150541_6_)
    {
        int i1 = p_150541_6_.nextInt(5);// bewachsener Stammbereich

        if (this.field_150542_e)
        {
            i1 += this.baseHeight;
        }
        else
        {
            i1 += 3;
        }

        int j1 = 0;

        for (int k1 = p_150541_4_ - i1; k1 <= p_150541_4_; ++k1)// von unten nach oben
        {
            int l1 = p_150541_4_ - k1;// rel restH�he bewachsener Bereich
            // der Mechanismus f�r die mit der H�he ver�nderten Spanne ist relativ kompliziert .. weil so dicht programmiert
            int i2 = p_150541_5_ + MathHelper.floor_float((float)l1 / (float)i1 * 3.5F);// wird kleiner
            // i2 == j1 && (k1 & 1) == 0: die beiden wesentlichen Kriterien f�r die Stufen
            this.func_150535_a(p_150541_1_, p_150541_2_, k1, p_150541_3_, i2 + (l1 > 0 && i2 == j1 && (k1 & 1) == 0 ? 1 : 0), p_150541_6_);
            j1 = i2;
        }
    }

    // folgende 3 Funktionen sind scheinbar nur daf�r da, Dirt im Bodenbereich zu setzen (weil unter Baum? bleibt Dirt oder wird Podzol?) 
    // nur bei GenLayer, also nicht beim Sapling setzen
    
    // diese Funktion wird aufgerufen, wenn im BiomeDecorator der TREE schon generiert wurde
    // scheinbar nur hier und beim "ForestBiome" also Birkenwald
    public void func_150524_b(World p_150524_1_, Random p_150524_2_, int p_150524_3_, int p_150524_4_, int p_150524_5_)
    {
    	// rund um den 2 Block Stamm
        this.func_150539_c(p_150524_1_, p_150524_2_, p_150524_3_ - 1, p_150524_4_, p_150524_5_ - 1);
        this.func_150539_c(p_150524_1_, p_150524_2_, p_150524_3_ + 2, p_150524_4_, p_150524_5_ - 1);
        this.func_150539_c(p_150524_1_, p_150524_2_, p_150524_3_ - 1, p_150524_4_, p_150524_5_ + 2);
        this.func_150539_c(p_150524_1_, p_150524_2_, p_150524_3_ + 2, p_150524_4_, p_150524_5_ + 2);

        // und ein bisschen noch weiter drumherum
        for (int l = 0; l < 5; ++l)
        {
            int i1 = p_150524_2_.nextInt(64);
            int j1 = i1 % 8;
            int k1 = i1 / 8;

            if (j1 == 0 || j1 == 7 || k1 == 0 || k1 == 7)
            {
                this.func_150539_c(p_150524_1_, p_150524_2_, p_150524_3_ - 3 + j1, p_150524_4_, p_150524_5_ - 3 + k1);
            }
        }
    }

    private void func_150539_c(World p_150539_1_, Random p_150539_2_, int p_150539_3_, int p_150539_4_, int p_150539_5_)
    {
        for (int l = -2; l <= 2; ++l)
        {
            for (int i1 = -2; i1 <= 2; ++i1)
            {
                if (Math.abs(l) != 2 || Math.abs(i1) != 2)
                {
                    this.func_150540_a(p_150539_1_, p_150539_3_ + l, p_150539_4_, p_150539_5_ + i1);
                }
            }
        }
    }

    // hier wird auch in y + 2 Dirt gesetzt ..??
    private void func_150540_a(World p_150540_1_, int p_150540_2_, int p_150540_3_, int p_150540_4_)
    {
        for (int l = p_150540_3_ + 2; l >= p_150540_3_ - 3; --l)// +2 ??
        {
            Block block = p_150540_1_.getBlock(p_150540_2_, l, p_150540_4_);
            
            // nur wenn es da schon einen bewachsbaren Block gibt
            if (block.canSustainPlant(p_150540_1_, p_150540_2_, l, p_150540_4_, ForgeDirection.UP, (BlockSapling)Blocks.sapling))
            {
                this.setBlockAndNotifyAdequately(p_150540_1_, p_150540_2_, l, p_150540_4_, Blocks.dirt, 2);
                break;
            }

            if (block.isAir(p_150540_1_, p_150540_2_, l, p_150540_4_) && l < p_150540_3_)
            {
                break;
            }
        }
    }
}