package de.cyn2021.cursed;

import de.cyn2021.cursed.item.*;
import net.minecraft.resources.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.*;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;

public class SadAppleEntity extends PathfinderMob {
    private Player targetPlayer;

    public SadAppleEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
    }



    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, Player.class, 32.0F, 2.2D, 2.3D)); // Höchste Priorität, größere Distanz, höhere Geschwindigkeit
        this.goalSelector.addGoal(1, new RandomStrollGoal(this, 1.0D));
    }
    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide) {
            Player nearest = level().getNearestPlayer(this, 200);
            if (nearest != null) {
                double dist = this.distanceTo(nearest);
                if (dist > 100.0) {
                    this.discard();
                    return;
                }
                // Immer wegrennen, solange Spieler in der Nähe
                if (dist <= 32.0) {
                    double dx = getX() - nearest.getX();
                    double dz = getZ() - nearest.getZ();
                    double len = Math.sqrt(dx * dx + dz * dz);
                    if (len > 0.01) {
                        double speed = 0.35; // Geschwindigkeit anpassen
                        setDeltaMovement((dx / len) * speed, getDeltaMovement().y, (dz / len) * speed);
                    }
                }
                if (dist <= 24.0 && tickCount % 20 == 0) {
                    ((ServerLevel) level()).playSound(
                            null,
                            getX(), getY(), getZ(),
                            net.minecraft.sounds.SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("cursed", "sad_apple_scream")),
                            net.minecraft.sounds.SoundSource.NEUTRAL,
                            1.5F, 1.0F
                    );
                }
            }
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (!level().isClientSide) {
            player.addItem(new ItemStack(ModItems.SAD_APPLE.get()));
            this.discard();
            return InteractionResult.CONSUME;
        }
        return super.mobInteract(player, hand);
    }
}