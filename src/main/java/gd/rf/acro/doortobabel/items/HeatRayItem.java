package gd.rf.acro.doortobabel.items;

import gd.rf.acro.doortobabel.ConfigUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.Random;

public class HeatRayItem extends Item {
    public HeatRayItem(Settings settings) {
        super(settings);

    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        float hottemp = Float.parseFloat(ConfigUtils.config.get("hottemp"));
        if(!world.isRaining() && world.getBiome(user.getBlockPos()).getTemperature()> hottemp && world.isSkyVisible(user.getBlockPos()))
        {
            //mostly modified from GuardianEntity
            user.playSound(SoundEvents.BLOCK_LAVA_EXTINGUISH,1,1);
            HitResult result = user.rayTrace(100,1,true);
            Random random = new Random();
            LivingEntity livingEntity = world.getClosestEntity(LivingEntity.class, TargetPredicate.DEFAULT,user,result.getPos().getX(),result.getPos().getY(),result.getPos().getZ(),new Box(result.getPos().getX()-2,result.getPos().getY()-2,result.getPos().getZ()-2,result.getPos().getX()+2,result.getPos().getY()+2,result.getPos().getZ()+2));
            if (livingEntity!=null) {
                   double d = 1D;
                double e = livingEntity.getX() - user.getBlockPos().getX();
                double f = livingEntity.getBodyY(0.5D) - user.getEyeY();
                double g = livingEntity.getZ() - user.getZ();
                double h = Math.sqrt(e * e + f * f + g * g);
                e /= h;
                f /= h;
                g /= h;
                double j = random.nextDouble();
                livingEntity.setFireTicks(100);
                user.getStackInHand(hand).damage(1,user,(dobreak)-> dobreak.sendToolBreakStatus(hand));
                while(j < h) {
                    j += 1.8D - d + random.nextDouble() * (1.7D - d);
                    world.addParticle(ParticleTypes.SMOKE, user.getBlockPos().getX() + e * j, user.getEyeY() + f * j, user.getBlockPos().getZ() + g * j, 0.0D, 0.0D, 0.0D);
                }
            }

        }
        return super.use(world, user, hand);
    }
}
