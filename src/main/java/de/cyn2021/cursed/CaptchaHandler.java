package de.cyn2021.cursed;

import de.cyn2021.cursed.client.gui.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.*;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.event.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Cursed.MOD_ID)
public class CaptchaHandler {
    private static boolean skipNextCaptcha = false;
    private static Screen lastScreen = null;

    @SubscribeEvent
    public static void onScreenInit(ScreenEvent.Init event) {
        var screen = event.getScreen();
        var player = Minecraft.getInstance().player;
        if (player == null) return;
        boolean captchaEnabled = player.level().getGameRules().getRule(ModGameRules.ENABLE_CAPTCHA).get();
        if (!captchaEnabled) return;

        if (skipNextCaptcha) {
            skipNextCaptcha = false;
            lastScreen = screen;
            return;
        }
        if ((screen instanceof InventoryScreen || screen instanceof CraftingScreen)
                && !(screen instanceof CaptchaScreen)
                && screen != lastScreen) {
            lastScreen = screen;
            CaptchaScreen.openFor(player);
        }
    }

    public static void setSkipNextCaptcha() {
        skipNextCaptcha = true;
    }
}