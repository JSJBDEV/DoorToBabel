package gd.rf.acro.doortobabel;

import net.minecraft.block.Block;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.SmeltingRecipe;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.apache.commons.lang3.RandomUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Utils {
    public static BlockRotation from3i(Vec3i vec)
    {
        if(vec.getX()>0)
        {
            return BlockRotation.CLOCKWISE_90;
        }
        if(vec.getZ()>0)
        {
            return BlockRotation.CLOCKWISE_180;
        }
        if(vec.getX()<0)
        {
            return BlockRotation.COUNTERCLOCKWISE_90;
        }
        return BlockRotation.NONE;
    }

    public static Vec3i rotate(Vec3i vec)
    {
        if(vec.getZ()<0)
        {
            return new Vec3i(1,0,0);
        }
        if(vec.getX()<0)
        {
            return new Vec3i(0,0,1);
        }
        if(vec.getZ()>0)
        {
            return new Vec3i(-1,0,0);
        }
        if(vec.getX()>0)
        {
            return new Vec3i(0,0,-1);
        }
        return null;

    }
    public static Vec3i mirror(Vec3i vec)
    {
        if(vec.getZ()<0)
        {
            return new Vec3i(0,0,1);
        }
        if(vec.getX()<0)
        {
            return new Vec3i(1,0,0);
        }
        if(vec.getZ()>0)
        {
            return new Vec3i(0,0,-1);
        }
        if(vec.getX()>0)
        {
            return new Vec3i(-1,0,0);
        }
        return null;

    }

    public static List<Vec3i> getValidFacings(Vec3i facing)
    {
        if(facing.getZ()<0)
        {
            return Arrays.asList(facing,new Vec3i(1,0,0));
        }
        if(facing.getZ()>0)
        {
            return Arrays.asList(facing,new Vec3i(-1,0,0));
        }
        if(facing.getX()>0)
        {
            return Arrays.asList(facing,new Vec3i(0,0,1));
        }
        if(facing.getX()<0)
        {
            return Arrays.asList(facing,new Vec3i(0,0,-1));
        }
        return null;
    }

    public static Vec3i getDirectionFromInput(Vec3i ductFacing, Vec3i cornerFacing)
    {
        if(ductFacing.getX()>0 && cornerFacing.getX()>0) //facing east
        {
            return new Vec3i(0,0,1);
        }
        if(ductFacing.getZ()<0 && cornerFacing.getX()>0)
        {
            return new Vec3i(-1,0,0);
        }
        if(ductFacing.getZ()<0 && cornerFacing.getZ()<0) //facing north
        {
            return new Vec3i(1,0,0);
        }
        if(ductFacing.getX()<0 && cornerFacing.getZ()<0)
        {
            return new Vec3i(0,0,1);
        }
        if(ductFacing.getZ()>0 && cornerFacing.getZ()>0) // facing south
        {
            return new Vec3i(-1,0,0);
        }
        if(ductFacing.getX()>0 && cornerFacing.getZ()>0)
        {
            return new Vec3i(0,0,-1);
        }
        if(ductFacing.getX()<0 && cornerFacing.getX()<0) //facing west
        {
            return new Vec3i(0,0,-1);
        }
        if(ductFacing.getZ()>0 && cornerFacing.getX()<0)
        {
            return new Vec3i(1,0,0);
        }
        return null;
    }


    public static Vec3i cornerPieceRedirect(Vec3i thisRot, Vec3i thatRot)
    {
        if(thisRot==thatRot) //it is "facing" the same way as the previous ducts
        {
            return rotate(thisRot);
        }
         //it is rotated so that it is the other way, validity should be checked externally
        return mirror(rotate(thisRot));
    }



    public static List<SmeltingRecipe> getSmeltingRecipes(World world)
    {
        List<SmeltingRecipe> recipes = new ArrayList<>();
        world.getRecipeManager().values().forEach(recipe->
        {
            if(recipe.getType()==RecipeType.SMELTING)
            {
                recipes.add((SmeltingRecipe)recipe);
            }
        });
        return recipes;
    }

    public static ItemStack getCookingOutcome(ItemStack input, World world)
    {
        List<SmeltingRecipe> recipes = getSmeltingRecipes(world);
        for (SmeltingRecipe recipe : recipes) {
            if (recipe.getPreviewInputs().get(0).test(input)) {
                return recipe.getOutput();
            }
        }
        return null;
    }

    public static boolean doesInventoryHaveSpace(ChestBlockEntity entity)
    {
        for (int i = 0; i < entity.getInvSize(); i++) {
            if(entity.getInvStack(i)==ItemStack.EMPTY)
            {
                return true;
            }
        }
        return false;
    }

    public static void spawnStructure(ServerWorld world, BlockPos pos, String name)
    {
        StructureManager manager = world.getStructureManager();


        Identifier load = new Identifier("doortobabel",name);
        Structure structure = manager.getStructure(load);
        if(structure!=null)
        {
            StructurePlacementData data = new StructurePlacementData()
                    .setMirrored(BlockMirror.NONE)
                    .setIgnoreEntities(true)
                    .setRotation(BlockRotation.NONE);
            structure.place(world,pos.west(4).north(4).up(),data);
        }
        else
        {
            System.out.println("this structure is null!");
        }



    }
    private static final String[] names = {"Achilles",
            "Adonis",
            "Adrian",
            "Aegeus",
            "Alec",
            "Alesandro",
            "Basil",
            "Barak",
            "Belen",
            "Bemus",
            "Caesar",
            "Calix",
            "Christophe",
            "Cicero",
            "Claus",
            "Cole",
            "Constantine",
            "Corban",
            "Cy",
            "Damen",
            "Darius",
            "Deacon",
            "Demitrius",
            "Dennis",
            "Deo",
            "Dru",
            "Egan",
            "Eros",
            "Estevan",
            "Eugene",
            "Evan",
            "Ezio",
            "Faustus",
            "Felipe",
            "Flavian",
            "George",
            "Giles",
            "Gregory",
            "Griffin",
            "Hercules",
            "Homer",
            "Icarus",
            "Isidore",
            "Jace",
            "Jerry",
            "Jorges",
            "Julian",
            "Kal",
            "Karan",
            "Keelan",
            "Kosmos",
            "Kristo",
            "Kyril",
            "Lander",
            "Layland",
            "Leo",
            "Magus",
            "Mateo",
            "Maximus",
            "Miles",
            "Moe",
            "Neo",
            "Nicholas",
            "Nicos",
            "Niles",
            "Nyke",
            "Obelius",
            "Odell",
            "Odysseus",
            "Orien",
            "Orrin",
            "Othello",
            "Otis",
            "Owen",
            "Pancras",
            "Pearce",
            "Philip",
            "Phoenix",
            "Proteus",
            "Quinn",
            "Rastus",
            "Sander",
            "Santos",
            "Sirius",
            "Spiro",
            "Stavros",
            "Tadd",
            "Tassos",
            "Theo",
            "Timon",
            "Titan",
            "Tomaso",
            "Tyrone",
            "Ulysses",
            "Urion",
            "Vasilios",
            "Vitalis",
            "Xander",
            "Adara",
            "Adrianna",
            "Aegea",
            "Agatha",
            "Agnes",
            "Aileen",
            "Alicia",
            "Anastasia",
            "Anjelica",
            "Aphrodite",
            "Aria",
            "Athena",
            "Ava",
            "Barbara",
            "Bryony",
            "Cadie",
            "Calista",
            "Calla",
            "Cara",
            "Celia",
            "Chloe",
            "Clara",
            "Clarissa",
            "Cleo",
            "Dalia",
            "Daria",
            "Demi",
            "Desa",
            "Diana",
            "Dora",
            "Dorothy",
            "Echo",
            "Effie",
            "Eileen",
            "Electra",
            "Elie",
            "Ellen",
            "Falana",
            "Fannie",
            "Fauna",
            "Finn",
            "Gaia",
            "Galena",
            "Gemina",
            "Georgeanne",
            "Goldie",
            "Halia",
            "Hatria",
            "Helen",
            "Hera",
            "Hester",
            "Idylla",
            "Iliana",
            "Ina",
            "Irene",
            "Iria",
            "Ivy",
            "Jacinda",
            "Jenesis",
            "Jeno",
            "Justina",
            "Kaia",
            "Kalika",
            "Karen",
            "Kat",
            "Kiersten",
            "Kristen",
            "Lacie",
            "Layna",
            "Leah",
            "Lenore",
            "Lexie",
            "Lilah",
            "Lindy",
            "Lois",
            "Lyssa",
            "Madelia",
            "Madge",
            "Mariam",
            "Maya",
            "Meagan",
            "Melani",
            "Melody",
            "Millicent",
            "Monica",
            "Nara",
            "Natasha",
            "Nella",
            "Nicole",
            "Nike",
            "Nora",
            "Orelle",
            "Pamela",
            "Penelope",
            "Petra",
            "Phyllis",
            "Rhoda",
            "Seema",
            "Selena",
            "Tabatha"};

    private static final String[] descriptors = {"attractive",
            "bald",
            "beautiful",
            "chubby",
            "clean",
            "dazzling",
            "drab",
            "elegant",
            "fancy",
            "fit",
            "flabby",
            "glamorous",
            "gorgeous",
            "handsome",
            "long",
            "magnificent",
            "muscular",
            "plain",
            "plump",
            "quaint",
            "scruffy",
            "shapely",
            "short",
            "skinny",
            "stocky",
            "ugly",
            "unkempt",
            "unsightly",
            "alive",
            "better",
            "careful",
            "clever",
            "dead",
            "easy",
            "famous",
            "gifted",
            "hallowed",
            "helpful",
            "important",
            "inexpensive",
            "mealy",
            "mushy",
            "odd",
            "poor",
            "powerful",
            "rich",
            "shy",
            "tender",
            "unimportant",
            "uninterested",
            "vast",
            "wrong",
            "aggressive",
            "agreeable",
            "ambitious",
            "brave",
            "calm",
            "delightful",
            "eager",
            "faithful",
            "gentle",
            "happy",
            "jolly",
            "kind",
            "lively",
            "nice",
            "obedient",
            "polite",
            "proud",
            "silly",
            "thankful",
            "victorious",
            "witty",
            "wonderful",
            "zealous",
            "angry",
            "bewildered",
            "clumsy",
            "defeated",
            "embarrassed",
            "fierce",
            "grumpy",
            "helpless",
            "itchy",
            "jealous",
            "lazy",
            "mysterious",
            "nervous",
            "obnoxious",
            "panicky",
            "pitiful",
            "repulsive",
            "scary",
            "thoughtless",
            "uptight",
            "worried"};
    public static String compoundName()
    {
        return names[RandomUtils.nextInt(0,names.length)]+" the "+descriptors[RandomUtils.nextInt(0,descriptors.length)];
    }

    public static boolean canGenerateAnotherRoom(Vec3i dir,int relx, int relz, int maxx, int maxz)
    {
        if(dir.getX()>0 && relx>=maxx)
        {
            return false;
        }
        if(dir.getX()<0 && relx<=(0-maxx))
        {
            return false;
        }
        if(dir.getZ()>0 && relz>=maxz)
        {
            return false;
        }
        if(dir.getZ()<0 && relz<=(0-maxz))
        {
            return false;
        }
        return true;
    }

}
