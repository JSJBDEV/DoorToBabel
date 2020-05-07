package gd.rf.acro.doortobabel;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleType;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmeltingRecipe;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biomes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {
    public static BlockRotation from3i(Vec3i vec)
    {
        if(vec.getX()>0)
        {
            return BlockRotation.CLOCKWISE_90;
        }
        if(vec.getZ()>0)
        {
            return BlockRotation.CLOCKWISE_180;
        }
        if(vec.getX()<0)
        {
            return BlockRotation.COUNTERCLOCKWISE_90;
        }
        return BlockRotation.NONE;
    }

    public static Vec3i rotate(Vec3i vec)
    {
        if(vec.getZ()<0)
        {
            return new Vec3i(1,0,0);
        }
        if(vec.getX()<0)
        {
            return new Vec3i(0,0,1);
        }
        if(vec.getZ()>0)
        {
            return new Vec3i(-1,0,0);
        }
        if(vec.getX()>0)
        {
            return new Vec3i(0,0,-1);
        }
        return null;

    }
    public static Vec3i mirror(Vec3i vec)
    {
        if(vec.getZ()<0)
        {
            return new Vec3i(0,0,1);
        }
        if(vec.getX()<0)
        {
            return new Vec3i(1,0,0);
        }
        if(vec.getZ()>0)
        {
            return new Vec3i(0,0,-1);
        }
        if(vec.getX()>0)
        {
            return new Vec3i(-1,0,0);
        }
        return null;

    }

    public static List<Vec3i> getValidFacings(Vec3i facing)
    {
        if(facing.getZ()<0)
        {
            return Arrays.asList(facing,new Vec3i(1,0,0));
        }
        if(facing.getZ()>0)
        {
            return Arrays.asList(facing,new Vec3i(-1,0,0));
        }
        if(facing.getX()>0)
        {
            return Arrays.asList(facing,new Vec3i(0,0,1));
        }
        if(facing.getX()<0)
        {
            return Arrays.asList(facing,new Vec3i(0,0,-1));
        }
        return null;
    }

    public static Vec3i getDirectionFromInput(Vec3i ductFacing, Vec3i cornerFacing)
    {
        if(ductFacing.getX()>0 && cornerFacing.getX()>0) //facing east
        {
            return new Vec3i(0,0,1);
        }
        if(ductFacing.getZ()<0 && cornerFacing.getX()>0)
        {
            return new Vec3i(-1,0,0);
        }
        if(ductFacing.getZ()<0 && cornerFacing.getZ()<0) //facing north
        {
            return new Vec3i(1,0,0);
        }
        if(ductFacing.getX()<0 && cornerFacing.getZ()<0)
        {
            return new Vec3i(0,0,1);
        }
        if(ductFacing.getZ()>0 && cornerFacing.getZ()>0) // facing south
        {
            return new Vec3i(-1,0,0);
        }
        if(ductFacing.getX()>0 && cornerFacing.getZ()>0)
        {
            return new Vec3i(0,0,-1);
        }
        if(ductFacing.getX()<0 && cornerFacing.getX()<0) //facing west
        {
            return new Vec3i(0,0,-1);
        }
        if(ductFacing.getZ()>0 && cornerFacing.getX()<0)
        {
            return new Vec3i(1,0,0);
        }
        return null;
    }


    public static Vec3i cornerPieceRedirect(Vec3i thisRot, Vec3i thatRot)
    {
        if(thisRot==thatRot) //it is "facing" the same way as the previous ducts
        {
            return rotate(thisRot);
        }
         //it is rotated so that it is the other way, validity should be checked externally
        return mirror(rotate(thisRot));
    }



    public static List<SmeltingRecipe> getSmeltingRecipes(World world)
    {
        List<SmeltingRecipe> recipes = new ArrayList<>();
        world.getRecipeManager().values().forEach(recipe->
        {
            if(recipe.getType()==RecipeType.SMELTING)
            {
                recipes.add((SmeltingRecipe)recipe);
            }
        });
        return recipes;
    }

    public static ItemStack getCookingOutcome(ItemStack input, World world)
    {
        List<SmeltingRecipe> recipes = getSmeltingRecipes(world);
        for (SmeltingRecipe recipe : recipes) {
            if (recipe.getPreviewInputs().size() == 1 && recipe.getPreviewInputs().get(0).test(input)) {
                return recipe.getOutput();
            }
        }
        return null;
    }
}
