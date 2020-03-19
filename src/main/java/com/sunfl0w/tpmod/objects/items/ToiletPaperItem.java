package com.sunfl0w.tpmod.objects.items;

import com.sunfl0w.tpmod.TPMod;
import com.sunfl0w.tpmod.objects.entities.ToiletPaperEntity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;

public class ToiletPaperItem extends Item {

	public ToiletPaperItem() {
		super(new Item.Properties().maxStackSize(64).group(TPMod.TP_ITEM_GROUP));
	}
	
	public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
	      ItemStack itemstack = player.getHeldItem(hand);
	      world.playSound((PlayerEntity)null, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (random.nextFloat() * 0.4F + 0.8F));
	      if (!world.isRemote) {
	         ToiletPaperEntity toiletPaper = new ToiletPaperEntity(world, player);
	         toiletPaper.func_213884_b(itemstack);
	         toiletPaper.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.0F, 6.0F);
	         world.addEntity(toiletPaper);
	      }

	      player.addStat(Stats.ITEM_USED.get(this));
	      if (!player.abilities.isCreativeMode) {
	         itemstack.shrink(1);
	      }

	      return ActionResult.func_226248_a_(itemstack);
	   }
}