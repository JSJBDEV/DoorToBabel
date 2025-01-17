package gd.rf.acro.doortobabel.items;

import gd.rf.acro.doortobabel.Utils;
import gd.rf.acro.doortobabel.world.BabelGenerator;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.List;

public class StructureItem extends Item {
    public StructureItem(Settings settings) {
        super(settings);
    }
    private static final List<String> STRUCTURES = Arrays.asList("BABEL","room_template");
    private static String selectedStructure = "BABEL";

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(user.isSneaking())
        {
            if(world.isClient)
            {
                if(STRUCTURES.indexOf(selectedStructure)+1==STRUCTURES.size())
                {
                    selectedStructure=STRUCTURES.get(0);
                }
                else
                {
                    selectedStructure = STRUCTURES.get(STRUCTURES.indexOf(selectedStructure)+1);
                }
                user.sendMessage(new LiteralText("Selected Structure: "+selectedStructure));
            }
        }
        else{
            if(selectedStructure.equals("BABEL"))
            {
                BabelGenerator.generate(world,user.getBlockPos());
            }
            else if(!world.isClient)
            {
                System.out.println(selectedStructure);
                Utils.spawnStructure((ServerWorld) world,user.getBlockPos(),selectedStructure);
            }
        }
        return super.use(world, user, hand);
    }
}
