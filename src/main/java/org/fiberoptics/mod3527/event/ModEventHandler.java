package org.fiberoptics.mod3527.event;

import com.tacz.guns.api.event.common.EntityHurtByGunEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.fiberoptics.mod3527.item.BulletproofVest;
import top.theillusivec4.curios.api.CuriosApi;

public class ModEventHandler {

    @SubscribeEvent
    public static void beforeEntityGunShot(EntityHurtByGunEvent.Pre event) {
        if(event.getHurtEntity() instanceof Player && event.getHurtEntity().isAlive()) {
            CuriosApi.getCuriosInventory((LivingEntity) event.getHurtEntity()).ifPresent(curiosInventory -> {
                curiosInventory.getStacksHandler("charm").ifPresent(slotInventory -> {
                    int i=0;
                    for(i=0;i<slotInventory.getStacks().getSlots();i++) {
                        if (slotInventory.getStacks().getStackInSlot(i).getItem() instanceof BulletproofVest) {
                            break;
                        }
                    }
                    if(i<slotInventory.getStacks().getSlots()) {
                        int damaged=slotInventory.getStacks().getStackInSlot(i).getDamageValue();
                        int damage=(int)(event.getBaseAmount())-1;
                        if(damaged+damage>slotInventory.getStacks().getStackInSlot(i).getMaxDamage())
                            slotInventory.getStacks().getStackInSlot(i).setCount(0);
                        else
                            slotInventory.getStacks().getStackInSlot(i).setDamageValue(damaged+damage);
                        event.setBaseAmount(1f);
                        event.setHeadshot(false);
                        event.setHeadshotMultiplier(1f);
                    }
                });
            });
        }
    }
}
