package com.sunfl0w.tpmod;

import com.sunfl0w.tpmod.init.TPModItems;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class TPModItemGroup extends ItemGroup {

	public TPModItemGroup() {
		super("TPMod");
	}

	@Override
	public ItemStack createIcon() {
		return new ItemStack(TPModItems.TOILET_PAPER.get());
	}
}