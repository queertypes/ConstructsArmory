package c4.conarm.client;

import c4.conarm.armor.ArmorHelper;
import c4.conarm.lib.armor.ArmorCore;
import c4.conarm.lib.modifiers.AccessoryModifier;
import c4.conarm.lib.modifiers.IAccessoryRender;
import c4.conarm.lib.traits.IArmorAbility;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.modifiers.IModifier;
import slimeknights.tconstruct.library.traits.ITrait;
import slimeknights.tconstruct.library.utils.TagUtil;
import slimeknights.tconstruct.library.utils.TinkerUtil;
import slimeknights.tconstruct.library.utils.ToolHelper;

import javax.annotation.Nonnull;

public class LayerAccessories <T extends ModelBase> implements LayerRenderer<EntityLivingBase> {

    private final RenderLivingBase<?> renderer;

    public LayerAccessories(RenderLivingBase<?> renderIn) {
        this.renderer = renderIn;
    }

    @Override
    public void doRenderLayer(@Nonnull EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        this.renderAccessoryLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EntityEquipmentSlot.CHEST);
        this.renderAccessoryLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EntityEquipmentSlot.LEGS);
        this.renderAccessoryLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EntityEquipmentSlot.FEET);
        this.renderAccessoryLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EntityEquipmentSlot.HEAD);
    }

    protected void renderAccessoryLayer(EntityLivingBase entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale, EntityEquipmentSlot slotIn) {

        ItemStack itemstack = entityLivingBaseIn.getItemStackFromSlot(slotIn);

        if (itemstack.getItem() instanceof ArmorCore && !ToolHelper.isBroken(itemstack))
        {
            ArmorCore armorCore = (ArmorCore) itemstack.getItem();
            NBTTagList list = TagUtil.getBaseModifiersTagList(itemstack);
            IModifier modifier = TinkerRegistry.getModifier(list.getStringTagAt(list.tagCount()-1));

            if (armorCore.getEquipmentSlot() == slotIn && modifier != null && modifier instanceof IAccessoryRender)
            {
                ((IAccessoryRender) modifier).onAccessoryRender(entityLivingBaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
            }
        }
    }

    @Override
    public boolean shouldCombineTextures()
    {
        return false;
    }
}