package com.chrissionair.tutorial.world;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.util.ForgeDirection;

import com.chrissionair.tutorial.Tutorial;
import com.chrissionair.tutorial.TutorialSapling01;

public class WorldGenSavannaTree_Tut extends WorldGenAbstractTree
{
    private static final String __OBFID = "CL_00000432";

    public WorldGenSavannaTree_Tut(boolean p_i45463_1_)
    {
        super(p_i45463_1_);
    }

    public boolean generate(World par1World, Random par2Random, int par3, int par4, int par5)
    {
    	// l ist H�he des Stamms
        int l = par2Random.nextInt(3) + par2Random.nextInt(3) + 5;
        boolean flag = true;

        // check ob �berhaupt Platz ist, wobei b0 ganz oben einen Umfang von 2 hat
        if (par4 >= 1 && par4 + l + 1 <= 256)
        {
            int j1;
            int k1;

            for (int i1 = par4; i1 <= par4 + 1 + l; ++i1)
            {
                byte b0 = 1;

                if (i1 == par4)
                {
                    b0 = 0;
                }

                if (i1 >= par4 + 1 + l - 2)
                {
                    b0 = 2;
                }

                for (j1 = par3 - b0; j1 <= par3 + b0 && flag; ++j1)
                {
                    for (k1 = par5 - b0; k1 <= par5 + b0 && flag; ++k1)
                    {
                        if (i1 >= 0 && i1 < 256)
                        {
                            Block block = par1World.getBlock(j1, i1, k1);

                            if (!this.isReplaceable(par1World, j1, i1, k1))
                            {
                                flag = false;
                            }
                        }
                        else
                        {
                            flag = false;
                        }
                    }
                }
            }

            if (!flag)
            {
                return false;
            }
            else
            {
                Block block3 = par1World.getBlock(par3, par4 - 1, par5);

                boolean isSoil = block3.canSustainPlant(par1World, par3, par4 - 1, par5, ForgeDirection.UP, (TutorialSapling01)Tutorial.tutorialSapling);
                if (isSoil && par4 < 256 - l - 1)
                {
                    block3.onPlantGrow(par1World, par3, par4 - 1, par5, par3, par4, par5);
                    int j3 = par2Random.nextInt(4); // 0-3: die Richtung x, z
                    j1 = l - par2Random.nextInt(4) - 1; // ab wo die erste Verzweigung beginnt
                    k1 = 3 - par2Random.nextInt(3); // wie weit diese Verzweigung reichen soll
                    int k3 = par3;
                    int l1 = par5;
                    int i2 = 0; // wird letzte H�he sein
                    int j2; // von 0 bis zur H�he kleiner des Stamms; neues relatives y
                    int k2; // das neue absolute y, mit H�he des Stamms wachsend

                    // der Stamm
                    for (j2 = 0; j2 < l; ++j2)
                    {
                        k2 = par4 + j2;

                        if (j2 >= j1 && k1 > 0)
                        {
                            k3 += Direction.offsetX[j3];
                            l1 += Direction.offsetZ[j3];
                            --k1;
                        }

                        Block block1 = par1World.getBlock(k3, k2, l1);

                        if (block1.isAir(par1World, k3, k2, l1) || block1.isLeaves(par1World, k3, k2, l1))
                        {
                            this.setBlockAndNotifyAdequately(par1World, k3, k2, l1, Blocks.log2, 0);
                            i2 = k2;
                        }
                    }

                    // macht eine erste Krone �ber dem letzten Stammblock, im Quadrat
                    for (j2 = -1; j2 <= 1; ++j2)
                    {
                        for (k2 = -1; k2 <= 1; ++k2)
                        {
                            this.func_150525_a(par1World, k3 + j2, i2 + 1, l1 + k2);
                        }
                    }

                    // an den Kanten eines dazu
                    this.func_150525_a(par1World, k3 + 2, i2 + 1, l1);
                    this.func_150525_a(par1World, k3 - 2, i2 + 1, l1);
                    this.func_150525_a(par1World, k3, i2 + 1, l1 + 2);
                    this.func_150525_a(par1World, k3, i2 + 1, l1 - 2);
                    
                    // eine Ebene darunter, also h�chster Stamm, eine Quadrat ohne Ecken
                    for (j2 = -3; j2 <= 3; ++j2)
                    {
                        for (k2 = -3; k2 <= 3; ++k2)
                        {
                            if (Math.abs(j2) != 3 || Math.abs(k2) != 3)
                            {
                                this.func_150525_a(par1World, k3 + j2, i2, l1 + k2);
                            }
                        }
                    }
                    
                    k3 = par3; // Zur�cksetzen x und z
                    l1 = par5;
                    j2 = par2Random.nextInt(4); // neue Richtung

                    if (j2 != j3)
                    {
                        k2 = j1 - par2Random.nextInt(2) - 1; // neuer Beginn Verzweigung; achtung: letztes k2 (z) �berschrieben
                        int l3 = 1 + par2Random.nextInt(3); // wie weit Verzweigung reicht
                        i2 = 0; // die letzte H�he
                        int i3; // neues absolutes y
                        int l2; // neues relatives y

                        // dadurch dass l2 mit l3 verkn�pft ist, kann der Stamm nur soweit wie die weiteste Verzweigung wachsen
                        for (l2 = k2; l2 < l && l3 > 0; --l3)
                        {
                            if (l2 >= 1)
                            {
                                i3 = par4 + l2;
                                k3 += Direction.offsetX[j2];
                                l1 += Direction.offsetZ[j2];
                                Block block2 = par1World.getBlock(k3, i3, l1);

                                if (block2.isAir(par1World, k3, i3, l1) || block2.isLeaves(par1World, k3, i3, l1))
                                {
                                    this.setBlockAndNotifyAdequately(par1World, k3, i3, l1, Blocks.log2, 0);
                                    i2 = i3;
                                }
                            }

                            ++l2;
                        }
                        
                        if (i2 > 0)
                        {
                        	// neue Krone on top; achtung wieder l2 und i3 �berschrieben
                        	for (l2 = -1; l2 <= 1; ++l2)
                            {
                                for (i3 = -1; i3 <= 1; ++i3)
                                {
                                    this.func_150525_a(par1World, k3 + l2, i2 + 1, l1 + i3);
                                }
                            }

                        	// und Quadrat ohne Ecken darunter
                            for (l2 = -2; l2 <= 2; ++l2)
                            {
                                for (i3 = -2; i3 <= 2; ++i3)
                                {
                                    if (Math.abs(l2) != 2 || Math.abs(i3) != 2)
                                    {
                                        this.func_150525_a(par1World, k3 + l2, i2, l1 + i3);
                                    }
                                }
                            }
                        }
                    }

                    return true;
                }
                else
                {
                    return false;
                }
            }
        }
        else
        {
            return false;
        }
    }

    // platziert den Leavesblock, aber nicht �ber Stamm
    private void func_150525_a(World p_150525_1_, int p_150525_2_, int p_150525_3_, int p_150525_4_)
    {
        Block block = p_150525_1_.getBlock(p_150525_2_, p_150525_3_, p_150525_4_);

        if (block.isAir(p_150525_1_, p_150525_2_, p_150525_3_, p_150525_4_) || block.isLeaves(p_150525_1_, p_150525_2_, p_150525_3_, p_150525_4_))
        {
            this.setBlockAndNotifyAdequately(p_150525_1_, p_150525_2_, p_150525_3_, p_150525_4_, Blocks.leaves2, 0);
        }
    }
}