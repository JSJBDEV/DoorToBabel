package gd.rf.acro.doortobabel.items;

import gd.rf.acro.doortobabel.DoorToBabel;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SLSBlockItem extends BlockItem {
    private boolean handOff;
    private BlockPos lastPos;
    public SLSBlockItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        lastPos=context.getBlockPos();
        handOff=true;
        return super.useOnBlock(context);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        super.inventoryTick(stack, world, entity, slot, selected);
        if(entity instanceof PlayerEntity && !world.isClient)
        {
            PlayerEntity player = (PlayerEntity) entity;
            if(player.getMainHandStack().getItem()==DoorToBabel.SPRING_LOADED_SCAFFOLDING.asItem())
            {
                if(handOff)
                {
                    lastPos=lastPos.up();
                    world.setBlockState(lastPos,DoorToBabel.SPRING_LOADED_SCAFFOLDING.getDefaultState());

                    stack.decrement(1);
                }
            }
            else
            {
                handOff=false;
            }
        }
        if(handOff)
        {
            entity.playSound(SoundEvents.BLOCK_DISPENSER_DISPENSE,1,1);
        }
    }
}
