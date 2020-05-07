package gd.rf.acro.doortobabel.items;

import gd.rf.acro.doortobabel.world.BabelGenerator;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class StructureItem extends Item {
    public StructureItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        BabelGenerator.generate(world,user.getBlockPos());
        return super.use(world, user, hand);
    }
}
