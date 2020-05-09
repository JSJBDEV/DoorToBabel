package gd.rf.acro.doortobabel.items;

import gd.rf.acro.doortobabel.ConfigUtils;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import sun.awt.ConstrainableGraphics;

public class MagnifyingGlassItem extends Item {
    public MagnifyingGlassItem(Settings settings) {
        super(settings);

    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        float hottemp = Float.parseFloat(ConfigUtils.config.get("hottemp"));
        if(!context.getWorld().isRaining() && context.getWorld().isSkyVisible(context.getPlayer().getBlockPos()) && context.getWorld().getBiome(context.getPlayer().getBlockPos()).getTemperature()> hottemp)
        {
            if(context.getWorld().getBlockState(context.getBlockPos().up()).getBlock()==Blocks.AIR)
            {
                context.getWorld().setBlockState(context.getBlockPos().up(), Blocks.FIRE.getDefaultState());
                context.getPlayer().playSound(SoundEvents.BLOCK_LAVA_EXTINGUISH,1,1);
                context.getStack().damage(1,context.getPlayer(),(dobreak)-> dobreak.sendToolBreakStatus(context.getHand()));
            }
        }
        return super.useOnBlock(context);
    }
}
