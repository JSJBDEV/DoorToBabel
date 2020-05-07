package gd.rf.acro.doortobabel.blocks;

import gd.rf.acro.doortobabel.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.EntityContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmeltingRecipe;
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
import java.util.List;
import java.util.Random;

public class SolarFurnaceBlock extends Block {
    public SolarFurnaceBlock(Settings settings) {
        super(settings);
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
        if(world.getBlockState(pos.up()).getBlock()instanceof ChestBlock && world.getBlockState(pos.down()).getBlock()instanceof ChestBlock)
        {
            ChestBlockEntity input = (ChestBlockEntity) world.getBlockEntity(pos.up());
            ChestBlockEntity output = (ChestBlockEntity) world.getBlockEntity(pos.down());
            ItemStack stackOut = ItemStack.EMPTY;
            for (int i = 0; i < input.getInvSize(); i++) {
                    ItemStack stack = Utils.getCookingOutcome(input.getInvStack(i),world);
                    if(stack!=null)
                    {
                        stackOut=stack;
                        break;
                    }
            }
            if(stackOut!=ItemStack.EMPTY){
                for (int i = 0; i < output.getInvSize(); i++) {
                    if(output.getInvStack(i)==ItemStack.EMPTY)
                    {
                        output.setInvStack(i,stackOut);
                        break;
                    }
                }
            }
        }
        world.getBlockTickScheduler().schedule(pos,this,100);
    }
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView view, BlockPos pos, EntityContext context) {
        return Block.createCuboidShape(1d,0d,1d,15d,16d,15d);
    }
}
