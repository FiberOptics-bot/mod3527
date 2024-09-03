package org.fiberoptics.mod3527;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagEntry;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = Mod3527.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    /*
    private static final ForgeConfigSpec.BooleanValue LOG_DIRT_BLOCK = BUILDER.comment("Whether to log the dirt block on common setup").define("logDirtBlock", true);

    private static final ForgeConfigSpec.IntValue MAGIC_NUMBER = BUILDER.comment("A magic number").defineInRange("magicNumber", 42, 0, Integer.MAX_VALUE);

    public static final ForgeConfigSpec.ConfigValue<String> MAGIC_NUMBER_INTRODUCTION = BUILDER.comment("What you want the introduction message to be for the magic number").define("magicNumberIntroduction", "The magic number is... ");



    // a list of strings that are treated as resource locations for items
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> ITEM_STRINGS = BUILDER.comment("A list of items to log on common setup.").defineListAllowEmpty("items", List.of("minecraft:iron_ingot"), Config::validateItemName);
    */

    private static final ForgeConfigSpec.DoubleValue BULLETPROOF_VEST_MULTIPLIER = BUILDER
            .comment("Damage multiplier of normal vest")
            .translation(Mod3527.MODID+".config."+"bulletproof_vest_multiplier")
            .defineInRange("bulletproofVestMultiplier",0.8d,0d,1d);

    private static final ForgeConfigSpec.DoubleValue UPGRADED_BULLETPROOF_VEST_MULTIPLIER = BUILDER
            .comment("Damage multiplier of upgraded vest")
            .translation(Mod3527.MODID+".config."+"upgraded_bulletproof_vest_multiplier")
            .defineInRange("upgradedBulletproofVestMultiplier",0.6d,0d,1d);

    private static final ForgeConfigSpec.IntValue BULLETPROOF_VEST_DURABILITY = BUILDER
            .comment("Durability of normal vest")
            .translation(Mod3527.MODID+".config."+"bulletproof_vest_durability")
            .defineInRange("bulletproofVestDurability",250,1,Integer.MAX_VALUE);

    private static final ForgeConfigSpec.IntValue UPGRADED_BULLETPROOF_VEST_DURABILITY = BUILDER
            .comment("Durability of upgraded vest")
            .translation(Mod3527.MODID+".config."+"upgraded_bulletproof_vest_durability")
            .defineInRange("upgradedBulletproofVestDurability",500,1,Integer.MAX_VALUE);

    private static final ForgeConfigSpec.IntValue AGILE_BULLETPROOF_VEST_AMPLIFIER = BUILDER
            .comment("Amplifier of movement speed effect when wearing agile bulletproof vest " +
                    "- amplifier value 0 refers to effect level 1")
            .translation(Mod3527.MODID+".config."+"agile_bulletproof_vest_amplifier")
            .defineInRange("agileBulletproofVestAmplifier",0,0,127);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    private static double bulletproofVestMultiplier;
    private static double upgradedBulletproofVestMultiplier;
    private static int bulletproofVestDurability;
    private static int upgradedBulletproofVestDurability;
    private static int agileBulletproofVestAmplifier;

    public static double getBulletproofVestMultiplier() {
        return bulletproofVestMultiplier;
    }

    public static double getUpgradedBulletproofVestMultiplier() {
        return upgradedBulletproofVestMultiplier;
    }

    public static int getBulletproofVestDurability() {
        return bulletproofVestDurability;
    }

    public static int getUpgradedBulletproofVestDurability() {
        return upgradedBulletproofVestDurability;
    }

    public static int getAgileBulletproofVestAmplifier() {
        return agileBulletproofVestAmplifier;
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        bulletproofVestMultiplier=BULLETPROOF_VEST_MULTIPLIER.get();
        upgradedBulletproofVestMultiplier=UPGRADED_BULLETPROOF_VEST_MULTIPLIER.get();
        bulletproofVestDurability=BULLETPROOF_VEST_DURABILITY.get();
        upgradedBulletproofVestDurability=UPGRADED_BULLETPROOF_VEST_DURABILITY.get();
        agileBulletproofVestAmplifier=AGILE_BULLETPROOF_VEST_AMPLIFIER.get();
    }
}
