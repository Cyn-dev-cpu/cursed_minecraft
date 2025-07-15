package de.cyn2021.cursed;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = Cursed.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.BooleanValue ENABLE_SOUND = BUILDER
            .comment("Play sound when eating apple")
            .define("enableSound", true);

    public static final ForgeConfigSpec.DoubleValue ENTITY_SPEED = BUILDER
            .comment("Speed multiplier for fleeing entity")
            .defineInRange("entitySpeedMultiplier", 1.0, 0.1, 5.0);

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean enableSound;
    public static double entitySpeedMultiplier;

    @SubscribeEvent
    public static void onReload(ModConfigEvent event) {
        // Hier verwenden wir weiterhin get()
        enableSound = ENABLE_SOUND.get();
        entitySpeedMultiplier = ENTITY_SPEED.get();
    }

    @SuppressWarnings("deprecation")
    public static void register() {
        // Auch wenn deprecated, ist dies der einzig verfügbare Aufruf
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SPEC);
    }
}
