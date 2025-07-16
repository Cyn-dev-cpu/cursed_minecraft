package de.cyn2021.cursed.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;

public class DarkSoulsDeathScreen extends Screen {
    private long deathTime;
    private static final int FADE_TIME = 40; // 2 Sekunden Fade-In (40 Ticks)
    private static final int WAIT_TIME = 100; // 5 Sekunden (100 Ticks)
    private boolean interactionAllowed = false;

    public DarkSoulsDeathScreen() {
        super(Component.empty());
        this.deathTime = System.currentTimeMillis();
        // Sound abspielen
        if (Minecraft.getInstance().level != null && Minecraft.getInstance().player != null) {
            Minecraft.getInstance().level.playLocalSound(
                    Minecraft.getInstance().player.getX(),
                    Minecraft.getInstance().player.getY(),
                    Minecraft.getInstance().player.getZ(),
                    net.minecraft.sounds.SoundEvent.createVariableRangeEvent(
                            net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("cursed", "you_died")
                    ),
                    SoundSource.MASTER, 1.0F, 1.0F, false
            );
        }
    }

    @Override
    protected void init() {
        // Keine Buttons mehr notwendig
    }

    // Kein @Override, da nicht in der Oberklasse vorhanden
    public boolean shouldRenderBackground() {
        return false;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        // Schwarzer Hintergrund ohne Blur
        graphics.fill(0, 0, this.width, this.height, 0xFF000000);

        // Fade-In Overlay (halbtransparentes Schwarz)
        long ticksSinceDeath = (System.currentTimeMillis() - deathTime) / 50;
        float alpha = Math.min(1.0F, ticksSinceDeath / (float)FADE_TIME);
        int bgAlpha = (int)(alpha * 255);
        graphics.fill(0, 0, this.width, this.height, (bgAlpha << 24));

        // Scharfer Text ohne Skalierung
        if (alpha > 0.0F) {
            String text = "YOU DIED";
            int textAlpha = (int)(alpha * 255);
            int shadowColor = (textAlpha << 24) | 0x220000;
            int mainColor = (textAlpha << 24) | 0xFF0000;

            int x = this.width / 2;
            int y = this.height / 2 - 50;
            for (int dx = -2; dx <= 2; dx++) {
                for (int dy = -2; dy <= 2; dy++) {
                    if (dx != 0 || dy != 0)
                        graphics.drawCenteredString(this.font, text, x + dx, y + dy, shadowColor);
                }
            }
            graphics.drawCenteredString(this.font, text, x, y, mainColor);
        }

        // Hinweise wie gehabt
        if (ticksSinceDeath > WAIT_TIME) {
            interactionAllowed = true;
            String continueText = "Click to Continue";
            String exitText = "Press ESC to Exit World";

            int y = this.height / 2 + 80;
            graphics.drawCenteredString(this.font, continueText, this.width / 2, y, 0xFFFFFFFF);
            graphics.drawCenteredString(this.font, exitText, this.width / 2, y + 20, 0xFFDDDDDD);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (interactionAllowed && button == 0) { // Linksklick
            respawnPlayer();
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private void respawnPlayer() {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player != null && minecraft.getConnection() != null) {
            minecraft.player.respawn();
            minecraft.setScreen(this);
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 256 && interactionAllowed) { // 256 = ESC
            respawnPlayer();
            Minecraft.getInstance().execute(() -> {
                try { Thread.sleep(50); } catch (InterruptedException ignored) {}
                exitToMenu();
            });
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private void exitToMenu() {
        if (Minecraft.getInstance().getSingleplayerServer() != null) {
            Minecraft.getInstance().getConnection().getConnection().disconnect(Component.literal("Leaving world"));
            Minecraft.getInstance().setScreen(new TitleScreen());
        } else {
            Minecraft.getInstance().getConnection().getConnection().disconnect(Component.literal("Leaving world"));
            Minecraft.getInstance().setScreen(new JoinMultiplayerScreen(new TitleScreen()));
        }
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    @Override
    public void tick() {
        // Keine Button-Logik mehr notwendig
    }
}