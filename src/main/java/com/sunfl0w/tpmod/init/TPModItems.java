package com.sunfl0w.tpmod.init;

import com.sunfl0w.tpmod.TPMod;
import com.sunfl0w.tpmod.objects.items.ToiletPaperItem;

import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TPModItems {
public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, TPMod.MOD_ID);
	
	public static final RegistryObject<Item> TOILET_PAPER = ITEMS.register("toilet_paper", () -> new ToiletPaperItem());
}
