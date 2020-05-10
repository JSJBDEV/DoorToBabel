package gd.rf.acro.doortobabel.blocks;

import gd.rf.acro.doortobabel.ConfigUtils;
import gd.rf.acro.doortobabel.DoorToBabel;
import gd.rf.acro.doortobabel.Utils;
import gd.rf.acro.doortobabel.world.BabelGenerator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.HopperBlock;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GrindingHopperBlock extends Block {
    public GrindingHopperBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        world.getBlockTickScheduler().schedule(pos,this,Integer.parseInt(ConfigUtils.config.get("ticks")));
        if(!placer.getScoreboardTags().contains("dtb_hopper"))
        {
            placer.sendMessage(new LiteralText("Placing the grinding hopper makes your mind drift to a huge tower..."));
            placer.addScoreboardTag("dtb_hopper");
        }
        if(placer.getScoreboardTags().contains("dtb_furnace") && placer.getScoreboardTags().contains("dtb_hopper") && world.getBlockState(new BlockPos(10000,100,9999)).getBlock()!=DoorToBabel.BABELSTONE)
        {
            placer.sendMessage(new LiteralText("Piecing your information together you decide to head out to 10000 10000 a dungeon awaits... [prepare for some lag]"));
            BabelGenerator.generate(world,new BlockPos(10000,200,10000));
        }
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random)
    {
        if(world.getBlockState(pos.up()).getBlock()instanceof HopperBlock && world.getBlockState(pos.down()).getBlock()instanceof HopperBlock)
        {
            if(world.getBlockState(pos.south()).getBlock()==DoorToBabel.WATERWHEEL_BLOCK)
            {
                List<Item> GRINDABLE = Arrays.asList(Items.WHEAT,Items.IRON_ORE, Items.GOLD_ORE,Items.DIAMOND_ORE,Items.SANDSTONE,Items.QUARTZ_BLOCK);
                List<Item> OUTPUTS = Arrays.asList(Items.BREAD, DoorToBabel.IRON_CHUNK,DoorToBabel.GOLD_CHUNK,Items.DIAMOND,Items.SAND,Items.QUARTZ);

                HopperBlockEntity input = (HopperBlockEntity) world.getBlockEntity(pos.up());
                HopperBlockEntity output = (HopperBlockEntity) world.getBlockEntity(pos.down());
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
        world.getBlockTickScheduler().schedule(pos,this,Integer.parseInt(ConfigUtils.config.get("ticks")));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext context) {
        return Block.createCuboidShape(0d,7d,0d,16d,16d,16d);
    }
}
