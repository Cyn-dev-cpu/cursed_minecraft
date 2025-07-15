package de.cyn2021.cursed;

import de.cyn2021.cursed.item.*;
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

public class SadAppleEntity extends PathfinderMob {
    private Player targetPlayer;

    public SadAppleEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Player.class, 12.0F, 0.5D, 0.5D));
    }

    @Override
    public void tick() {
        super.tick();
        if (!level().isClientSide) {
            if (targetPlayer == null || !targetPlayer.isAlive()) {
                targetPlayer = level().getNearestPlayer(this, 100);
            }
            if (targetPlayer != null) {
                double dist = this.distanceTo(targetPlayer);
                if (dist > 80.0) {
                    ((ServerLevel) level()).playSound(
                            null,
                            targetPlayer.getX(), targetPlayer.getY(), targetPlayer.getZ(),
                            SoundEvents.VILLAGER_NO,
                            SoundSource.PLAYERS,
                            1.0F, 1.0F
                    );
                    this.discard();
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