package de.cyn2021.cursed.entity;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import de.cyn2021.cursed.item.ModItems;
import java.util.UUID;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;

public class FleeingPlayerEntity extends PathfinderMob {
    private final UUID playerUUID;
    private final String playerName;

    public FleeingPlayerEntity(EntityType<? extends PathfinderMob> type, Level level, UUID playerUUID, String playerName) {
        super(type, level);
        this.playerUUID = playerUUID;
        this.playerName = playerName;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Player.class, 8.0F, 1.6D, 2.0D));
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (!level().isClientSide && hand == InteractionHand.MAIN_HAND) {
            player.addItem(new ItemStack(ModItems.SAD_APPLE.get()));
            this.discard();
            ((ServerLevel) level()).sendParticles(
                    net.minecraft.core.particles.ParticleTypes.SMOKE,
                    getX(), getY() + 1, getZ(),
                    30, 0.5, 1, 0.5, 0.05
            );
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.PASS;
    }
}