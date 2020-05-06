package gd.rf.acro.doortobabel.blocks;

import gd.rf.acro.doortobabel.DoorToBabel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityContext;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmeltingRecipe;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

import java.util.Random;

public class WaterWheelStatic extends Block {
    public WaterWheelStatic(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext context) {
        return Block.createCuboidShape(1d,1d,1d,14d,14d,14d);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if(world.getBlockState(pos.up().east()).getBlock()== DoorToBabel.AQUEDUCT_WATER)
        {
            AqueductBlockEntity entity = (AqueductBlockEntity) world.getBlockEntity(pos.up().east());
            if(entity.getSpeed()>4)
            {
                world.setBlockState(pos,DoorToBabel.WATERWHEEL_BLOCK.getDefaultState());
                WaterWheelBlockEntity wheel = (WaterWheelBlockEntity) world.getBlockEntity(pos);
                wheel.setSpeed(entity.getSpeed());
            }
        }
    }
}
