package com.sunfl0w.tpmod;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.sunfl0w.tpmod.init.TPModItems;

import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("tpmod")
@Mod.EventBusSubscriber(modid = TPMod.MOD_ID, bus = Bus.MOD)
public class TPMod {
	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MOD_ID = "tpmod";
	public static final ItemGroup TP_ITEM_GROUP = new TPModItemGroup();
	public static TPMod modInstance;

	public TPMod() {
		modInstance = this;
		register();

		MinecraftForge.EVENT_BUS.register(this);
	}
	
	private void register() {
		TPModItems.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
}