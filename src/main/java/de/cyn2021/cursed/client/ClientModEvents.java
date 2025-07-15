package de.cyn2021.cursed.client;

import de.cyn2021.cursed.Cursed;
import de.cyn2021.cursed.client.model.*;
import de.cyn2021.cursed.client.renderer.*;
import de.cyn2021.cursed.entity.ModEntities;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Cursed.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientModEvents {

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.SAD_APPLE.get(), context -> new SadAppleEntityRenderer(context));
    }

    @SubscribeEvent
    public static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(
                SadAppleEntityModel.LAYER_LOCATION,
                SadAppleEntityModel::createBodyLayer
        );
    }
}