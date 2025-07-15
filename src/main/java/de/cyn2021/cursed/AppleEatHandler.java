package de.cyn2021.cursed;

import de.cyn2021.cursed.entity.ModEntities;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class AppleEatHandler {

    @SubscribeEvent
    public static void onRightClick(PlayerInteractEvent.RightClickItem event) {
        Player player = event.getEntity();
        if (!player.level().isClientSide && event.getItemStack().is(Items.APPLE)) {
            event.getItemStack().shrink(1);
            player.playSound(SoundEvents.VILLAGER_NO);

            if (player.level() instanceof ServerLevel serverLevel) {
// Entferne diese Zeile, um keine Partikel zu erzeugen:
                serverLevel.sendParticles(
                        new net.minecraft.core.particles.ItemParticleOption(
                                net.minecraft.core.particles.ParticleTypes.ITEM,
                                new net.minecraft.world.item.ItemStack(net.minecraft.world.item.Items.APPLE)
                        ),
                        player.getX(), player.getY() + 1, player.getZ(),
                        20, 0.5, 1, 0.5, 0.05
                );

                boolean spawnEnabled = serverLevel.getGameRules().getRule(ModGameRules.SPAWN_ON_APPLE_EAT).get();
                if (spawnEnabled) {
                    var entityType = ModEntities.SAD_APPLE.orElse(null);
                    if (entityType != null) {
                        SadAppleEntity entity = entityType.create(serverLevel);
                        if (entity != null) {
                            entity.moveTo(player.getX(), player.getY(), player.getZ());
                            serverLevel.addFreshEntity(entity);
                            player.sendSystemMessage(Component.literal("A Sad Apple has spawned!"));
                        } else {
                            player.sendSystemMessage(Component.literal("Fehler: Sad Apple konnte nicht erzeugt werden!"));
                        }
                    } else {
                        player.sendSystemMessage(Component.literal("Fehler: EntityType nicht registriert!"));
                    }
                } else {
                    player.sendSystemMessage(Component.literal("Mob-Spawning per GameRule deaktiviert!"));
                }
            }
        }
    }
}