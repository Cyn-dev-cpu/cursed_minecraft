package de.cyn2021.cursed.entity;

import de.cyn2021.cursed.Cursed;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import de.cyn2021.cursed.SadAppleEntity;
public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Cursed.MOD_ID);


    public static final RegistryObject<EntityType<SadAppleEntity>> SAD_APPLE =
            ENTITY_TYPES.register("sad_apple", () ->
                    EntityType.Builder.<SadAppleEntity>of(
                            SadAppleEntity::new,
                            MobCategory.CREATURE
                    ).sized(0.6F, 1.0F).build("sad_apple")
            );
    public static final RegistryObject<EntityType<FleeingPlayerEntity>> FLEEING_PLAYER =
            ENTITY_TYPES.register("fleeing_player", () ->
                    EntityType.Builder.<FleeingPlayerEntity>of(
                            (type, level) -> new FleeingPlayerEntity(type, level, null, null),
                            MobCategory.CREATURE
                    ).sized(0.6F, 1.8F).build("fleeing_player")
            );
}
