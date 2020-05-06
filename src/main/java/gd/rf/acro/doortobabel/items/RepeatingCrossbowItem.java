package gd.rf.acro.doortobabel.items;

import gd.rf.acro.doortobabel.DoorToBabel;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FireworkEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;
import java.util.UUID;

public class RepeatingCrossbowItem extends Item {
    private boolean handOff =false;
    private int jamChance;
    public RepeatingCrossbowItem(Settings settings, int jam) {
        super(settings);
        handOff=false;
        jamChance=jam;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (user.getOffHandStack().getItem()== Items.ARROW)
        {
            handOff=true;
        }

        return super.use(world, user, hand);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(entity instanceof  PlayerEntity && !world.isClient)
        {
            PlayerEntity player = (PlayerEntity) entity;
            if(handOff && player.getOffHandStack().getItem()== Items.ARROW)
            {
                ArrowEntity arrow = new ArrowEntity(world,player);
                arrow.setVelocity(player.getRotationVector().multiply(4));
                world.spawnEntity(arrow);
                player.getOffHandStack().decrement(1);
                if(RandomUtils.nextInt(0,jamChance)==0)
                {
                    player.sendMessage(new LiteralText("Crossbow Jammed!"));
                    handOff=false;
                    if(RandomUtils.nextInt(0,32)==0)
                    {
                        stack=new ItemStack(Items.STICK,4);
                        player.setStackInHand(Hand.MAIN_HAND,stack);

                    }
                }
            }
            else
            {
                handOff=false;
            }
        }
        if(handOff)
        {
            entity.playSound(SoundEvents.BLOCK_DISPENSER_LAUNCH,1,1);
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(new LiteralText("Jam chance: 1/"+jamChance));
    }
}
