package de.cyn2021.cursed.item;

import de.cyn2021.cursed.Cursed;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Cursed.MOD_ID);

    // Datei: src/main/java/de/cyn2021/cursed/item/ModItems.java
    public static final RegistryObject<Item> SAD_APPLE = ITEMS.register("sad_apple", () ->
            new SadAppleItem(new Properties()
                    .stacksTo(1)
                    .food(new FoodProperties.Builder()
                            .nutrition(4)
                            .saturationModifier(0.3F)
                            .alwaysEdible()
                            .build())
            )
    );

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
