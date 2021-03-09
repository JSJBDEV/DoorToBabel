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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Random;

public class SolarFurnaceBlock extends Block {
    private HashMap<Item,ItemStack> recipeCache = new HashMap<>();
    public SolarFurnaceBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        world.getBlockTickScheduler().schedule(pos,this,Integer.parseInt(ConfigUtils.config.get("ticks")));
        if(!placer.getScoreboardTags().contains("dtb_furnace"))
        {
            placer.sendMessage(new LiteralText("Placing the solar furnace casts your mind to a door to another dimension..."));
            placer.addScoreboardTag("dtb_furnace");
        }
        if(placer.getScoreboardTags().contains("dtb_furnace") && placer.getScoreboardTags().contains("dtb_hopper") && world.getBlockState(new BlockPos(10000,100,9999)).getBlock()!= DoorToBabel.BABELSTONE)
        {
            placer.sendMessage(new LiteralText("Piecing your information together you decide to head out to 10000 10000 a dungeon awaits..."));
            BabelGenerator.generate(world,new BlockPos(10000,200,10000));
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(!world.isClient)
        {
            ItemStack stack = Utils.getCookingOutcome(player.getStackInHand(hand),world);
            if(stack!=null)
            {
                player.sendMessage(new LiteralText("this would smelt into: "+stack));
            }
        }
        return super.onUse(state, world, pos, player, hand, hit);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if(world.getBlockState(pos.up()).getBlock()instanceof HopperBlock && world.getBlockState(pos.down()).getBlock()instanceof HopperBlock && !world.isClient)
        {
            System.out.println("conditions set 1 met");
            float hottemp = Float.parseFloat(ConfigUtils.config.get("hottemp"));
            if(!world.isRaining() && world.getBiome(pos).getTemperature()> hottemp && world.isSkyVisible(pos.north()))
            {
                System.out.println("conditions set 2 met");
                HopperBlockEntity input = (HopperBlockEntity) world.getBlockEntity(pos.up());
                HopperBlockEntity output = (HopperBlockEntity) world.getBlockEntity(pos.down());
                if(Utils.doesInventoryHaveSpace(output))
                {
                    System.out.println("space found");
                    ItemStack stackOut = ItemStack.EMPTY;
                    for (int i = 0; i < input.getInvSize(); i++) {
                        if(recipeCache.containsKey(input.getInvStack(i).getItem()))
                        {
                            stackOut=recipeCache.get(input.getInvStack(i).getItem());
                            input.getInvStack(i).decrement(1);
                        }
                        else
                        {
                            ItemStack stack = Utils.getCookingOutcome(input.getInvStack(i),world);
                            if(stack!=null)
                            {
                                if(stack.getItem()!=Items.AIR)
                                {
                                    stackOut=stack.copy();
                                    recipeCache.put(input.getInvStack(i).getItem(),stackOut);
                                    input.getInvStack(i).decrement(1);
                                    break;
                                }
                            }
                        }
                    }
                    if(stackOut!=ItemStack.EMPTY){
                        for (int i = 0; i < output.getInvSize(); i++) {
                            if(output.getInvStack(i)==ItemStack.EMPTY)
                            {
                                output.setInvStack(i,stackOut.copy());
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
        return Block.createCuboidShape(1d,0d,1d,15d,16d,15d);
    }
}
