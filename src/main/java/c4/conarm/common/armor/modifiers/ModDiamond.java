package c4.conarm.common.armor.modifiers;

import c4.conarm.lib.armor.ArmorNBT;
import c4.conarm.common.armor.utils.ArmorTagUtil;
import c4.conarm.lib.modifiers.ArmorModifier;
import net.minecraft.nbt.NBTTagCompound;
import slimeknights.tconstruct.library.modifiers.ModifierAspect;
import slimeknights.tconstruct.library.utils.TagUtil;

public class ModDiamond extends ArmorModifier {

    public ModDiamond() {
        super("diamond", 0x8cf4e2);

        addAspects(new ModifierAspect.SingleAspect(this), new ModifierAspect.DataAspect(this), ModifierAspect.freeModifier);
    }

    @Override
    public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {
        ArmorNBT data = ArmorTagUtil.getArmorStats(rootCompound);
        data.durability += 150;

        data.defense += 4;
        data.toughness += 2;

        TagUtil.setToolTag(rootCompound, data.get());
    }
}