package org.fiberoptics.mod3527.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.fiberoptics.mod3527.Config;
import org.fiberoptics.mod3527.Mod3527;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Mod3527.MODID);

    public static final RegistryObject<Item> AGILE_BULLETPROOF_VEST = ITEMS.register(
            "agile_bulletproof_vest",
            () -> new AgileBulletproofVest(Config.getUpgradedBulletproofVestDurability()));

    public static final RegistryObject<Item> BULLETPROOF_VEST = ITEMS.register("bulletproof_vest",
            () -> new BulletproofVest(Config.getBulletproofVestDurability()));

    public static final RegistryObject<Item> BULLETPROOF_VEST_INTERMEDIATE = ITEMS.register(
            "bulletproof_vest_intermediate",() -> new Item(new Item.Properties().stacksTo(1)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
