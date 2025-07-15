package de.cyn2021.cursed;

import net.minecraft.world.level.GameRules;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Cursed.MOD_ID)
public class ModGameRules {

    public static final GameRules.Key<GameRules.BooleanValue> ENABLE_CAPTCHA =
            GameRules.register("enableCaptcha", GameRules.Category.PLAYER, GameRules.BooleanValue.create(true));
    public static final GameRules.Key<GameRules.BooleanValue> SPAWN_ON_APPLE_EAT =
            GameRules.register("spawnMobOnAppleEat", GameRules.Category.PLAYER, GameRules.BooleanValue.create(false));

    public static void register(IEventBus bus) {
        // Hier nichts weiter notwendig, da die GameRule über static-Init registriert wird.
    }

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        // Beispiel: Log-Info beim Start mit dem aktuellen Wert
        boolean enabled = event.getServer().getGameRules().getRule(SPAWN_ON_APPLE_EAT).get();
        System.out.println("spawnMobOnAppleEat Gamerule aktiviert: " + enabled);
    }
}
