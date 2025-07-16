package de.cyn2021.cursed.client;

import de.cyn2021.cursed.Cursed;
import de.cyn2021.cursed.client.gui.DarkSoulsDeathScreen;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Cursed.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DeathScreenHandler {

    @SubscribeEvent
    public static void onScreenOpen(ScreenEvent.Opening event) {
        // Wenn der Vanilla Death Screen geöffnet werden soll, ersetze ihn
        if (event.getScreen() instanceof DeathScreen) {
            event.setCanceled(true);
            net.minecraft.client.Minecraft.getInstance().setScreen(new DarkSoulsDeathScreen());
        }
    }
}