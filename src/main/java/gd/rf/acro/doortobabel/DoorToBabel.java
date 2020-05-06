package gd.rf.acro.doortobabel;

import gd.rf.acro.doortobabel.blocks.*;
import gd.rf.acro.doortobabel.items.HeatRayItem;
import gd.rf.acro.doortobabel.items.MagnifyingGlassItem;
import gd.rf.acro.doortobabel.items.RepeatingCrossbowItem;
import gd.rf.acro.doortobabel.items.SLSBlockItem;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class DoorToBabel implements ModInitializer {
	public static final ItemGroup AQUEDUCTS = FabricItemGroupBuilder.build(
			new Identifier("doortobabel", "aqueducts_tab"),
			() -> new ItemStack(DoorToBabel.WATERWHEEL_BLOCK_STATIC));
	public static final ItemGroup SPRINGS = FabricItemGroupBuilder.build(
			new Identifier("doortobabel", "springs_tab"),
			() -> new ItemStack(DoorToBabel.SPRING_LOADED_SCAFFOLDING));
	public static final ItemGroup OPTICS = FabricItemGroupBuilder.build(
			new Identifier("doortobabel", "optics_tab"),
			() -> new ItemStack(DoorToBabel.MAGNIFYING_GLASS));
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		ConfigUtils.checkConfigs();
		registerBlocks();
		registerItems();
		registerBlockEntities();
		System.out.println("Salve, Mundi!");



	}
	public static final AqueductBlock AQUEDUCT = new AqueductBlock(FabricBlockSettings.of(Material.METAL).build());
	public static final AqueductBlock AQUEDUCT_CORNER = new AqueductBlock(FabricBlockSettings.of(Material.METAL).build());
	public static final AqueductBlock AQUEDUCT_WATER = new AqueductBlock(FabricBlockSettings.of(Material.METAL).ticksRandomly().build());
	public static final AqueductBlock AQUEDUCT_CORNER_WATER = new AqueductBlock(FabricBlockSettings.of(Material.METAL).ticksRandomly().build());
	public static final CollectorBlock WATER_COLLECTOR = new CollectorBlock(FabricBlockSettings.of(Material.METAL).build());
	public static final DistributorBlock WATER_DISTRIBUTOR = new DistributorBlock(FabricBlockSettings.of(Material.METAL).ticksRandomly().build());
	public static final AqueductBlock AQUEDUCT_ROT_DROP = new AqueductBlock(FabricBlockSettings.of(Material.METAL).build());
	public static final AqueductBlock AQUEDUCT_ROT_DROP_WATER = new AqueductBlock(FabricBlockSettings.of(Material.METAL).ticksRandomly().build());
	public static final WaterWheelBlock WATERWHEEL_BLOCK = new WaterWheelBlock(FabricBlockSettings.of(Material.METAL).ticksRandomly().build());
	public static final WaterWheelStatic WATERWHEEL_BLOCK_STATIC = new WaterWheelStatic(FabricBlockSettings.of(Material.METAL).ticksRandomly().build());
	public static final GrindingHopperBlock GRINDING_HOPPER = new GrindingHopperBlock(FabricBlockSettings.of(Material.METAL).ticksRandomly().build());
	public static final WaterElevatorBlock WATER_ELEVATOR_ROTOR = new WaterElevatorBlock(FabricBlockSettings.of(Material.METAL).ticksRandomly().build());
	public static final WaterElevatorBlock WATER_ELEVATOR_STACK = new WaterElevatorBlock(FabricBlockSettings.of(Material.METAL).build());
	public static final SpringLoadedScaffolding SPRING_LOADED_SCAFFOLDING = new SpringLoadedScaffolding(FabricBlockSettings.of(Material.METAL).build());
	public static final WinchLineBlock WINCH_LINE = new WinchLineBlock(FabricBlockSettings.of(Material.METAL).ticksRandomly().build(),100,false);
	public static final WinchLineBlock WINCH_LINE_SPRING = new WinchLineBlock(FabricBlockSettings.of(Material.METAL).ticksRandomly().build(),100,true);
	private void registerBlocks()
	{
		Registry.register(Registry.BLOCK, new Identifier("doortobabel", "aqueduct"), AQUEDUCT);

		Registry.register(Registry.BLOCK, new Identifier("doortobabel", "aqueduct_corner"), AQUEDUCT_CORNER);

		Registry.register(Registry.BLOCK, new Identifier("doortobabel", "aqueduct_rot_drop"), AQUEDUCT_ROT_DROP);


		Registry.register(Registry.BLOCK, new Identifier("doortobabel", "aqueduct_water"), AQUEDUCT_WATER);

		Registry.register(Registry.BLOCK, new Identifier("doortobabel", "aqueduct_corner_water"), AQUEDUCT_CORNER_WATER);

		Registry.register(Registry.BLOCK, new Identifier("doortobabel", "aqueduct_rot_drop_water"), AQUEDUCT_ROT_DROP_WATER);

		Registry.register(Registry.BLOCK, new Identifier("doortobabel", "water_collector"), WATER_COLLECTOR);
		Registry.register(Registry.BLOCK, new Identifier("doortobabel", "water_distributor"), WATER_DISTRIBUTOR);

		Registry.register(Registry.BLOCK, new Identifier("doortobabel", "water_wheel"), WATERWHEEL_BLOCK);

		Registry.register(Registry.BLOCK, new Identifier("doortobabel", "water_wheel_static"), WATERWHEEL_BLOCK_STATIC);

		Registry.register(Registry.BLOCK, new Identifier("doortobabel", "grinding_hopper"), GRINDING_HOPPER);
		Registry.register(Registry.BLOCK, new Identifier("doortobabel", "winch_line"), WINCH_LINE);
		Registry.register(Registry.BLOCK, new Identifier("doortobabel", "winch_line_spring"), WINCH_LINE_SPRING);

		Registry.register(Registry.BLOCK, new Identifier("doortobabel", "water_elevator_rotor"), WATER_ELEVATOR_ROTOR);
		Registry.register(Registry.BLOCK, new Identifier("doortobabel", "water_elevator_stack"), WATER_ELEVATOR_STACK);
		Registry.register(Registry.BLOCK, new Identifier("doortobabel", "spring_loaded_scaffolding"), SPRING_LOADED_SCAFFOLDING);

	}
	public static final Item IRON_CHUNK = new Item(new Item.Settings().group(AQUEDUCTS));
	public static final Item GOLD_CHUNK = new Item(new Item.Settings().group(AQUEDUCTS));
	public static final Item SPRING_MECHANISM = new Item(new Item.Settings().group(SPRINGS));
	public static final RepeatingCrossbowItem REPEATING_CROSSBOW = new RepeatingCrossbowItem(new Item.Settings().group(SPRINGS),16);
	public static final MagnifyingGlassItem MAGNIFYING_GLASS = new MagnifyingGlassItem(new Item.Settings().group(OPTICS).maxDamage(100));
	public static final HeatRayItem HEAT_RAY = new HeatRayItem(new Item.Settings().group(OPTICS));
	public void registerItems()
	{
		Registry.register(Registry.ITEM, new Identifier("doortobabel", "aqueduct"), new BlockItem(AQUEDUCT, new Item.Settings().group(AQUEDUCTS)));
		Registry.register(Registry.ITEM, new Identifier("doortobabel", "aqueduct_corner"), new BlockItem(AQUEDUCT_CORNER, new Item.Settings().group(AQUEDUCTS)));
		Registry.register(Registry.ITEM, new Identifier("doortobabel", "water_collector"), new BlockItem(WATER_COLLECTOR, new Item.Settings().group(AQUEDUCTS)));
		Registry.register(Registry.ITEM, new Identifier("doortobabel", "water_distributor"), new BlockItem(WATER_DISTRIBUTOR, new Item.Settings().group(AQUEDUCTS)));
		Registry.register(Registry.ITEM, new Identifier("doortobabel", "aqueduct_rot_drop"), new BlockItem(AQUEDUCT_ROT_DROP, new Item.Settings().group(AQUEDUCTS)));
		Registry.register(Registry.ITEM, new Identifier("doortobabel", "water_wheel_static"), new BlockItem(WATERWHEEL_BLOCK_STATIC, new Item.Settings().group(AQUEDUCTS)));
		Registry.register(Registry.ITEM, new Identifier("doortobabel", "grinding_hopper"), new BlockItem(GRINDING_HOPPER, new Item.Settings().group(AQUEDUCTS)));

		Registry.register(Registry.ITEM, new Identifier("doortobabel", "water_elevator_rotor"), new BlockItem(WATER_ELEVATOR_ROTOR, new Item.Settings().group(AQUEDUCTS)));
		Registry.register(Registry.ITEM, new Identifier("doortobabel", "water_elevator_stack"), new BlockItem(WATER_ELEVATOR_STACK, new Item.Settings().group(AQUEDUCTS)));
		Registry.register(Registry.ITEM, new Identifier("doortobabel", "winch_line"), new BlockItem(WINCH_LINE, new Item.Settings().group(AQUEDUCTS)));
		Registry.register(Registry.ITEM, new Identifier("doortobabel", "winch_line_spring"), new BlockItem(WINCH_LINE_SPRING, new Item.Settings().group(SPRINGS)));

		Registry.register(Registry.ITEM, new Identifier("doortobabel", "spring_loaded_scaffolding"), new SLSBlockItem(SPRING_LOADED_SCAFFOLDING, new Item.Settings().group(SPRINGS)));

		Registry.register(Registry.ITEM,new Identifier("doortobabel","iron_chunk"),IRON_CHUNK);
		Registry.register(Registry.ITEM,new Identifier("doortobabel","gold_chunk"),GOLD_CHUNK);
		Registry.register(Registry.ITEM,new Identifier("doortobabel","spring_mechanism"),SPRING_MECHANISM);
		Registry.register(Registry.ITEM,new Identifier("doortobabel","repeating_crossbow"),REPEATING_CROSSBOW);

		Registry.register(Registry.ITEM,new Identifier("doortobabel","magnifying_glass"),MAGNIFYING_GLASS);
		Registry.register(Registry.ITEM,new Identifier("doortobabel","heat_ray"),HEAT_RAY);

	}
	public static BlockEntityType<AqueductBlockEntity> AQUEDUCT_ENTITY;
	public static BlockEntityType<AqueductBlockEntity> AQUEDUCT_CORNER_ENTITY;
	public static BlockEntityType<AqueductBlockEntity> AQUEDUCT_ROT_DROP_ENTITY;
	public static BlockEntityType<WaterWheelBlockEntity> WATERWHEEL_BLOCK_ENTITY;
	private void registerBlockEntities()
	{
		AQUEDUCT_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE,"doortobabel:aqueduct_entity",BlockEntityType.Builder.create(AqueductBlockEntity::new,AQUEDUCT_WATER).build(null));
		AQUEDUCT_ROT_DROP_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE,"doortobabel:aqueduct_rot_drop_entity",BlockEntityType.Builder.create(AqueductBlockEntity::new,AQUEDUCT_ROT_DROP_WATER).build(null));
		AQUEDUCT_CORNER_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE,"doortobabel:aqueduct_corner_entity",BlockEntityType.Builder.create(AqueductBlockEntity::new,AQUEDUCT_CORNER_WATER).build(null));
		WATERWHEEL_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE,"doortobabel:water_wheel_entity",BlockEntityType.Builder.create(WaterWheelBlockEntity::new,WATERWHEEL_BLOCK).build(null));

	}

}