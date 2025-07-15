package de.cyn2021.cursed.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;

public class SadAppleItem extends Item {
    public SadAppleItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!level.isClientSide && entity instanceof Player player) {
            // Effekte wie Enchanted Golden Apple
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 400, 1)); // 20 Sekunden, Stufe 2
            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 2400, 3));  // 2 Minuten, Stufe 4
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 6000, 0));  // 5 Minuten, Stufe 1
            player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 6000, 0)); // 5 Minuten, Stufe 1
        }
        return super.finishUsingItem(stack, level, entity);
    }


    public int getUseDuration(ItemStack stack) {
        return 32;
    }

    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.EAT;
    }
}