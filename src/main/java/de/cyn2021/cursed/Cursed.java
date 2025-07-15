package de.cyn2021.cursed;

import de.cyn2021.cursed.client.renderer.SadAppleItemRenderer;
import de.cyn2021.cursed.entity.*;
import de.cyn2021.cursed.item.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Cursed.MOD_ID)
public class Cursed {

    public static final String MOD_ID = "cursed";

    public Cursed() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.register(modBus);
        ModEntities.ENTITY_TYPES.register(modBus);
        ModGameRules.register(modBus);
        Config.register();



        modBus.addListener(this::addToCreativeTab);
        modBus.addListener(this::onClientSetup);

        MinecraftForge.EVENT_BUS.register(AppleEatHandler.class);
    }

    private void addToCreativeTab(BuildCreativeModeTabContentsEvent event) {
        ResourceKey<CreativeModeTab> tab = event.getTabKey();
        if (tab.equals(CreativeModeTabs.INGREDIENTS)) {
            event.accept(ModItems.SAD_APPLE);
        }
    }

    @SubscribeEvent
    public void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            ItemProperties.register(
                    ModItems.SAD_APPLE.get(),
                    ResourceLocation.fromNamespaceAndPath(Cursed.MOD_ID, "sad_apple_property"),
                    SadAppleItemRenderer::getProperty
            );
            // Die Renderer-Registrierung erfolgt im passenden Event in ClientModEvents.java
        });
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Optional: Server-Start-Logik
    }
}