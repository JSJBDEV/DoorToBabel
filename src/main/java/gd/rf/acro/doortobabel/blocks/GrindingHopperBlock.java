package gd.rf.acro.doortobabel.blocks;

import gd.rf.acro.doortobabel.DoorToBabel;
import gd.rf.acro.doortobabel.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.EntityContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GrindingHopperBlock extends Block {
    public GrindingHopperBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random)
    {
        if(world.getBlockState(pos.up()).getBlock()instanceof ChestBlock && world.getBlockState(pos.down()).getBlock()instanceof ChestBlock)
        {
            if(world.getBlockState(pos.south()).getBlock()==DoorToBabel.WATERWHEEL_BLOCK)
            {
                List<Item> GRINDABLE = Arrays.asList(Items.WHEAT,Items.IRON_ORE, Items.GOLD_ORE);
                List<Item> OUTPUTS = Arrays.asList(Items.BREAD, DoorToBabel.IRON_CHUNK,DoorToBabel.GOLD_CHUNK);

                ChestBlockEntity input = (ChestBlockEntity) world.getBlockEntity(pos.up());
                ChestBlockEntity output = (ChestBlockEntity) world.getBlockEntity(pos.down());
                if(Utils.doesInventoryHaveSpace(output))
                {
                    Item toProcess = Items.AIR;
                    int amount=0;
                    for (int i = 0; i < input.getInvSize(); i++) {
                        if(GRINDABLE.contains(input.getInvStack(i).getItem()))
                        {
                            toProcess = input.getInvStack(i).getItem();
                            amount= input.getInvStack(i).getCount();
                           if(amount>32)
                           {
                               input.setInvStack(i, new ItemStack(toProcess,amount-32));
                               amount=32;
                           }
                           else
                           {
                               input.setInvStack(i, ItemStack.EMPTY);
                           }
                           break;
                        }
                    }
                    if(toProcess!=Items.AIR)
                    {
                        for (int i = 0; i < output.getInvSize(); i++)
                        {
                            if(output.getInvStack(i)==ItemStack.EMPTY)
                            {
                                output.setInvStack(i,new ItemStack(OUTPUTS.get(GRINDABLE.indexOf(toProcess)),amount*2));
                                break;
                            }
                        }
                    }

                }
            }
        }
        world.getBlockTickScheduler().schedule(pos,this,20);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext context) {
        return Block.createCuboidShape(0d,7d,0d,16d,16d,16d);
    }
}
