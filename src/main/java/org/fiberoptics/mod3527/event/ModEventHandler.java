package org.fiberoptics.mod3527.event;

import com.tacz.guns.api.event.common.EntityHurtByGunEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.DataPackRegistryEvent;
import org.fiberoptics.mod3527.item.BulletproofVest;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.Random;
import java.util.Timer;

import org.fiberoptics.mod3527.Mod3527;

public class ModEventHandler {

    @SubscribeEvent
    public static void beforeEntityGunShot(EntityHurtByGunEvent.Pre event) {
        if(event.getHurtEntity() instanceof Player && event.getHurtEntity().isAlive() &&
                event.getBaseAmount() >= 1) {
            CuriosApi.getCuriosInventory((LivingEntity) event.getHurtEntity()).ifPresent(curiosInventory -> {
                curiosInventory.getStacksHandler(BulletproofVest.CURIO_SLOT).ifPresent(slotInventory -> {
                    int i=0;
                    for(i=0;i<slotInventory.getStacks().getSlots();i++) {
                        if (slotInventory.getStacks().getStackInSlot(i).getItem() instanceof BulletproofVest) {
                            break;
                        }
                    }
                    if(i<slotInventory.getStacks().getSlots()) {
                        int damaged=slotInventory.getStacks().getStackInSlot(i).getDamageValue();
                        int damage=(int)(event.getBaseAmount());
                        if(damaged+damage>slotInventory.getStacks().getStackInSlot(i).getMaxDamage()) {
                            slotInventory.getStacks().getStackInSlot(i).setCount(0);
                            event.getHurtEntity().sendSystemMessage(Component.literal("你的防弹衣已损坏"));
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
                        event.setBaseAmount(0.5f);
                        event.setHeadshot(false);
                        event.setHeadshotMultiplier(1f);
                    }
                });
            });
        }
    }
}
