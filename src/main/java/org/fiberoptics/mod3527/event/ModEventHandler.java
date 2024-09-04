package org.fiberoptics.mod3527.event;

import com.tacz.guns.api.event.common.EntityHurtByGunEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.fiberoptics.mod3527.Config;
import org.fiberoptics.mod3527.item.AgileBulletproofVest;
import org.fiberoptics.mod3527.item.BulletproofVest;
import org.fiberoptics.mod3527.item.StunBulletproofVest;
import org.fiberoptics.mod3527.util.Cooldowns;
import top.theillusivec4.curios.api.CuriosApi;

import org.fiberoptics.mod3527.Mod3527;

public class ModEventHandler {

    @SubscribeEvent
    public static void beforeEntityGunShot(EntityHurtByGunEvent.Pre event) {
        if(event.getHurtEntity() instanceof Player && event.getHurtEntity().isAlive()) {
            CuriosApi.getCuriosInventory((LivingEntity) event.getHurtEntity()).ifPresent(curiosInventory -> {
                curiosInventory.getStacksHandler(BulletproofVest.CURIO_SLOT).ifPresent(slotInventory -> {
                    int i=0;
                    double multiplier = Config.getBulletproofVestMultiplier();
                    for(i=0;i<slotInventory.getStacks().getSlots();i++) {
                        if (slotInventory.getStacks().getStackInSlot(i).getItem() instanceof BulletproofVest) {
                            if(slotInventory.getStacks().getStackInSlot(i).getItem()
                                    instanceof AgileBulletproofVest || slotInventory.getStacks()
                                    .getStackInSlot(i).getItem() instanceof StunBulletproofVest ) {
                                multiplier=Config.getUpgradedBulletproofVestMultiplier();
                            }
                            break;
                        }
                    }
                    if(i<slotInventory.getStacks().getSlots()) {
                        int damaged=slotInventory.getStacks().getStackInSlot(i).getDamageValue();
                        int damage=(int)(event.getAmount()*(1-multiplier));
                        if(damaged+damage>slotInventory.getStacks().getStackInSlot(i).getMaxDamage()) {
                            slotInventory.getStacks().getStackInSlot(i).setCount(0);
                            event.getHurtEntity().sendSystemMessage(Component.translatable(
                                    "message."+Mod3527.MODID+".bulletproof_vest_broken"));
                        }
                        else {
                            if(slotInventory.getStacks().getStackInSlot(i).
                                    getEnchantmentLevel(Enchantments.UNBREAKING)!=0){
                                if(Mod3527.RANDOM_GENERATOR.nextDouble(1d)<(1d/(slotInventory.
                                        getStacks().getStackInSlot(i).
                                        getEnchantmentLevel(Enchantments.UNBREAKING)+1))) {
                                    slotInventory.getStacks().getStackInSlot(i).setDamageValue(damaged+damage);
                                }
                            }
                            else slotInventory.getStacks().getStackInSlot(i).setDamageValue(damaged+damage);
                        }
                        event.setBaseAmount((float)(event.getBaseAmount()*multiplier));
                    }
                });
            });
        }
    }

    @SubscribeEvent
    public static void onPlayerHurt(LivingDamageEvent event) {
        if(event.getEntity() instanceof Player player) {
            CuriosApi.getCuriosInventory(event.getEntity()).ifPresent(curiosInventory -> {
                curiosInventory.getStacksHandler(BulletproofVest.CURIO_SLOT).ifPresent(slotInventory -> {
                    for(int i=0;i<slotInventory.getSlots();i++) {
                        if(slotInventory.getStacks().getStackInSlot(i).getItem() instanceof AgileBulletproofVest) {
                            Cooldowns cooldowns = Config.getCooldownsByUUID(player.getStringUUID());
                            if(cooldowns.getAgileCooldown() == 0) {
                                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,
                                        Config.getAgileBulletproofVestEffectTime(),
                                        Config.getAgileBulletproofVestAmplifier()));
                                cooldowns.setAgileCooldown(Config.getAgileBulletproofVestCooldown());
                            }
                            break;
                        }
                    }
                });
            });
        }
    }

    @SubscribeEvent
    public static void onPlayerAttack(AttackEntityEvent event) {
        if(event.getTarget() instanceof Player target) {
            Player player = event.getEntity();
            CuriosApi.getCuriosInventory(target).ifPresent(curioInventory -> {
                curioInventory.getStacksHandler(BulletproofVest.CURIO_SLOT).ifPresent(slotInventory -> {
                    for(int i=0;i<slotInventory.getSlots();i++) {
                        if(slotInventory.getStacks().getStackInSlot(i).getItem() instanceof StunBulletproofVest) {
                            Cooldowns cooldowns=Config.getCooldownsByUUID(target.getStringUUID());
                            if(cooldowns.getStunCooldown() == 0) {
                                player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS,
                                        Config.getStunBulletproofVestEffectTime(),
                                        Config.getStunBulletproofVestAmplifier()));
                                player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION,
                                        Config.getStunBulletproofVestEffectTime(),
                                        Config.getStunBulletproofVestAmplifier()));
                                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,
                                        Config.getStunBulletproofVestEffectTime(),
                                        Config.getStunBulletproofVestAmplifier()));
                                cooldowns.setStunCooldown(Config.getStunBulletproofVestCooldown());
                            }
                            break;
                        }
                    }
                });
            });
        }
    }
}
