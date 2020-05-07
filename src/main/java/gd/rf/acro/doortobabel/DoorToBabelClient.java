package gd.rf.acro.doortobabel;

import gd.rf.acro.doortobabel.blocks.WaterWheelRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.render.RenderLayer;

import static gd.rf.acro.doortobabel.DoorToBabel.*;

public class DoorToBabelClient implements ClientModInitializer {

    //ModelLoadingRegistry.INSTANCE.registerAppender((manager, out) -> out.accept(/* your model identifier */))
    @Override
    public void onInitializeClient() {
        registerColourProviders();
        registerTranslucentBlocks();

        BlockEntityRendererRegistry.INSTANCE.register(WATERWHEEL_BLOCK_ENTITY, WaterWheelRenderer::new);
    }

    private void registerColourProviders()
    {
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0x3495eb, AQUEDUCT_WATER);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0x3495eb, AQUEDUCT_CORNER_WATER);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0x3495eb, AQUEDUCT_ROT_DROP_WATER);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0x3495eb, WATER_COLLECTOR);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0x3495eb, WATER_DISTRIBUTOR);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0x3495eb, WATERWHEEL_BLOCK);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0x3495eb, WATER_ELEVATOR_ROTOR);
        ColorProviderRegistry.BLOCK.register((state, view, pos, tintIndex) -> 0x3495eb, WATER_ELEVATOR_STACK);
    }
    private void registerTranslucentBlocks()
    {
        BlockRenderLayerMap.INSTANCE.putBlock(AQUEDUCT, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(AQUEDUCT_CORNER, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(AQUEDUCT_ROT_DROP, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(AQUEDUCT_WATER, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(AQUEDUCT_CORNER_WATER, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(AQUEDUCT_ROT_DROP_WATER, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(WATER_COLLECTOR, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(WATER_DISTRIBUTOR, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(WATERWHEEL_BLOCK, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(WATERWHEEL_BLOCK_STATIC, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(GRINDING_HOPPER, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(WATER_ELEVATOR_ROTOR, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(WATER_ELEVATOR_STACK, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(SPRING_LOADED_SCAFFOLDING, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(WINCH_LINE, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(WINCH_LINE_SPRING, RenderLayer.getTranslucent());
    }
}
