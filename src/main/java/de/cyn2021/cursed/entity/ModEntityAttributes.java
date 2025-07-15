// Datei: src/main/java/de/cyn2021/cursed/entity/ModEntityAttributes.java
package de.cyn2021.cursed.entity;

import de.cyn2021.cursed.SadAppleEntity;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntityAttributes {
    @SubscribeEvent
    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(ModEntities.SAD_APPLE.get(), Mob.createMobAttributes().build());
        event.put(ModEntities.FLEEING_PLAYER.get(), Mob.createMobAttributes().build());
    }
}