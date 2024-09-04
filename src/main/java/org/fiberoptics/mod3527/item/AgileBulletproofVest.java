package org.fiberoptics.mod3527.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.fiberoptics.mod3527.Config;
import org.fiberoptics.mod3527.util.Cooldowns;
import top.theillusivec4.curios.api.SlotContext;

public class AgileBulletproofVest extends BulletproofVest{

    public AgileBulletproofVest() {
        super();
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return Config.getUpgradedBulletproofVestDurability();
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack itemStack) {
        super.curioTick(slotContext,itemStack);
        LivingEntity entity=slotContext.entity();
        Cooldowns cooldowns = Config.getCooldownsByUUID(entity.getStringUUID());
        int cooldown = cooldowns.getAgileCooldown();
        if(cooldown>0) cooldowns.setAgileCooldown(cooldown-1);
    }
}
