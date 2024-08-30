package org.fiberoptics.mod3527.item;

import net.minecraft.world.item.Item;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class BulletproofVest extends Item implements ICurioItem {
    public BulletproofVest() {
        super(new Item.Properties().defaultDurability(500).requiredFeatures());
    }
}
