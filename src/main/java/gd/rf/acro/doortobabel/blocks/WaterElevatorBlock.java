package gd.rf.acro.doortobabel.blocks;

import gd.rf.acro.doortobabel.DoorToBabel;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.EntityContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

import java.util.Random;

public class WaterElevatorBlock extends Block {
    public WaterElevatorBlock(Settings settings) {
        super(settings);
    }
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext context) {
        return Block.createCuboidShape(1d,0d,1d,15d,16d,15d);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if(world.getBlockState(pos.up()).getBlock()== DoorToBabel.WATER_ELEVATOR_STACK
                && world.getBlockState(pos.south()).getBlock()==DoorToBabel.WATERWHEEL_BLOCK
                && world.getBlockState(pos.down()).getBlock()instanceof ChestBlock)
        {
            ChestBlockEntity input = (ChestBlockEntity) world.getBlockEntity(pos.down());
            BlockPos stack = pos.up();
            while (world.getBlockState(stack).getBlock()== DoorToBabel.WATER_ELEVATOR_STACK)
            {
                stack=stack.up();
            }
            if(world.getBlockState(stack).getBlock()instanceof ChestBlock)
            {
                ChestBlockEntity output = (ChestBlockEntity) world.getBlockEntity(stack);
                ItemStack item = ItemStack.EMPTY;
                for (int i = 0; i < input.getInvSize(); i++) {
                    if(input.getInvStack(i)!= ItemStack.EMPTY)
                    {
                        item=input.getInvStack(i);
                        input.setInvStack(i,ItemStack.EMPTY);
                        break;
                    }
                }
                if(item!=ItemStack.EMPTY)
                {
                    for (int i = 0; i < output.getInvSize(); i++) {
                        if(output.getInvStack(i)==ItemStack.EMPTY)
                        {
                            output.setInvStack(i,item);
                            break;
                        }
                    }
                }
            }
        }
        world.getBlockTickScheduler().schedule(pos,this,40);

    }
}
