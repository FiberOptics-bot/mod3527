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
import org.fiberoptics.mod3527.util.Cooldowns;

import java.util.*;
import java.util.stream.Collectors;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = Mod3527.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {


    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

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

    private static final ForgeConfigSpec.IntValue AGILE_BULLETPROOF_VEST_COOLDOWN = BUILDER
            .comment("Cooldown in ticks before the agile bulletproof vest can apply effects again")
            .translation(Mod3527.MODID+".config."+"agile_bulletproof_vest_cooldown")
            .defineInRange("agileBulletproofVestCooldown",200,0,Integer.MAX_VALUE);

    private static final ForgeConfigSpec.IntValue AGILE_BULLETPROOF_VEST_EFFECT_TIME = BUILDER
            .comment("Effect time in ticks applied when taking damage wearing agile bulletproof vest")
            .translation(Mod3527.MODID+".config."+"agile_bulletproof_vest_effect_time")
            .defineInRange("agileBulletproofVestEffectTime",100,0,Integer.MAX_VALUE);

    private static final ForgeConfigSpec.IntValue STUN_BULLETPROOF_VEST_AMPLIFIER = BUILDER
            .comment("Amplifier of effects applied on the attacker when wearing stun " +
                    "bulletproof suit - amplifier value 0 refers to effect level 1")
            .translation(Mod3527.MODID+".config."+"stun_bulletproof_vest_amplifier")
            .defineInRange("stunBulletproofVestAmplifier",0,0,127);

    private static final ForgeConfigSpec.IntValue STUN_BULLETPROOF_VEST_COOLDOWN = BUILDER
            .comment("Cooldown in ticks before the stun bulletproof vest can apply effects again")
            .translation(Mod3527.MODID+".config."+"stun_bulletproof_vest_cooldown")
            .defineInRange("stunBulletproofVestCooldown",100,0,Integer.MAX_VALUE);

    private static final ForgeConfigSpec.IntValue STUN_BULLETPROOF_VEST_EFFECT_TIME = BUILDER
            .comment("Effect time in ticks applied on the attacker when wearing stun bulletproof vest")
            .translation(Mod3527.MODID+".config."+"stun_bulletproof_vest_effect_time")
            .defineInRange("stunBulletproofVestEffectTime",20,0,Integer.MAX_VALUE);

    static final ForgeConfigSpec SPEC = BUILDER.build();

    private static double bulletproofVestMultiplier;
    private static double upgradedBulletproofVestMultiplier;
    private static int bulletproofVestDurability;
    private static int upgradedBulletproofVestDurability;
    private static int agileBulletproofVestAmplifier;
    private static int agileBulletproofVestCooldown;
    private static int agileBulletproofVestEffectTime;
    private static int stunBulletproofVestAmplifier;
    private static int stunBulletproofVestCooldown;
    private static int stunBulletproofVestEffectTime;

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

    public static int getAgileBulletproofVestCooldown() {
        return agileBulletproofVestCooldown;
    }

    public static int getAgileBulletproofVestEffectTime() {
        return agileBulletproofVestEffectTime;
    }

    public static int getStunBulletproofVestAmplifier() {
        return stunBulletproofVestAmplifier;
    }

    public static int getStunBulletproofVestCooldown() {
        return stunBulletproofVestCooldown;
    }

    public static int getStunBulletproofVestEffectTime() {
        return stunBulletproofVestEffectTime;
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        bulletproofVestMultiplier=BULLETPROOF_VEST_MULTIPLIER.get();
        upgradedBulletproofVestMultiplier=UPGRADED_BULLETPROOF_VEST_MULTIPLIER.get();
        bulletproofVestDurability=BULLETPROOF_VEST_DURABILITY.get();
        upgradedBulletproofVestDurability=UPGRADED_BULLETPROOF_VEST_DURABILITY.get();
        agileBulletproofVestAmplifier=AGILE_BULLETPROOF_VEST_AMPLIFIER.get();
        agileBulletproofVestCooldown=AGILE_BULLETPROOF_VEST_COOLDOWN.get();
        agileBulletproofVestEffectTime=AGILE_BULLETPROOF_VEST_EFFECT_TIME.get();
        stunBulletproofVestAmplifier=STUN_BULLETPROOF_VEST_AMPLIFIER.get();
        stunBulletproofVestCooldown=STUN_BULLETPROOF_VEST_COOLDOWN.get();
        stunBulletproofVestEffectTime=STUN_BULLETPROOF_VEST_EFFECT_TIME.get();
    }

    private static final Map<String,Cooldowns> playerCooldowns = new HashMap<>();

    public static Cooldowns getCooldownsByUUID(String uuid) {
        if(playerCooldowns.get(uuid) == null)
            playerCooldowns.put(uuid,new Cooldowns(0,0));
        return playerCooldowns.get(uuid);
    }
}
