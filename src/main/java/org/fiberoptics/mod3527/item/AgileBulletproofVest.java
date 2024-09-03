package org.fiberoptics.mod3527.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

public class AgileBulletproofVest extends BulletproofVest{

    public AgileBulletproofVest(int durability) {
        super(durability);
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack itemStack) {
        LivingEntity entity=slotContext.entity();
        if(entity.getEffect(MobEffects.MOVEMENT_SPEED) == null ||
                entity.getEffect(MobEffects.MOVEMENT_SPEED).getDuration() < 1) {
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,5));
        }
    }
}
