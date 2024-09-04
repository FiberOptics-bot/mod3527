package org.fiberoptics.mod3527.item;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.extensions.IForgeItem;
import org.fiberoptics.mod3527.Config;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.concurrent.atomic.AtomicBoolean;

public class BulletproofVest extends Item implements ICurioItem {
    public static final String CURIO_SLOT = "vest";

    public BulletproofVest() {
        super(new Item.Properties().durability(1).requiredFeatures());
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        return Config.getBulletproofVestDurability();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        CuriosApi.getCuriosInventory(player).ifPresent(curiosInventory -> {
            curiosInventory.getStacksHandler(CURIO_SLOT).ifPresent(slotInventory -> {
                int i;
                ItemStack itemStack=player.getItemInHand(hand);
                for(i=0;i<slotInventory.getSlots();i++) {
                    if(slotInventory.getStacks().getStackInSlot(i).isEmpty()) {
                        ItemStack newStack=itemStack.copy();
                        slotInventory.getStacks().setStackInSlot(i,newStack);
                        break;
                    }
                }
                if(i!=slotInventory.getSlots()) itemStack.setCount(0);
            });
        });
        return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand),level.isClientSide());
    }
}
