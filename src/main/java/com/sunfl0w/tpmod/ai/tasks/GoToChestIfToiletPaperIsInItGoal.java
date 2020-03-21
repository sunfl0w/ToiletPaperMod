package com.sunfl0w.tpmod.ai.tasks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.sunfl0w.tpmod.TPMod;
import com.sunfl0w.tpmod.init.TPModItems;

import net.minecraft.block.Blocks;
import net.minecraft.block.ChestBlock;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.util.math.BlockPos;

public class GoToChestIfToiletPaperIsInItGoal extends Goal {
	private CreatureEntity entity;
	private BlockPos chestWithToiletPaperPos;
	private Random random = new Random();

	public GoToChestIfToiletPaperIsInItGoal(CreatureEntity entity) {
		this.entity = entity;
	}

	@Override
	public boolean shouldExecute() {
		return true;
	}

	public void tick() {
		if (chestWithToiletPaperPos == null) {
			chestWithToiletPaperPos = getChestWithToiletPaperPos();
		} else {
			entity.getNavigator().tryMoveToXYZ((double) ((float) chestWithToiletPaperPos.getX()) + 0.5D, (double) (chestWithToiletPaperPos.getY() + 1),
					(double) ((float) chestWithToiletPaperPos.getZ()) + 0.5D, 0.7f);
			if (entity.getPosition().distanceSq(chestWithToiletPaperPos) < 3.0f) {
				chestWithToiletPaperPos = null;
			}
		}
	}

	private BlockPos getChestWithToiletPaperPos() {
		ArrayList<BlockPos> chestsWithToiletPaper = new ArrayList<BlockPos>();

		Iterable<BlockPos> blockPositions = BlockPos.getAllInBoxMutable(entity.getPosition().getX() - 15, entity.getPosition().getY() - 5,
				entity.getPosition().getZ() - 15, entity.getPosition().getX() + 15, entity.getPosition().getY() + 5, entity.getPosition().getZ() + 15);
		for (BlockPos blockPosition : blockPositions) {
			if (entity.world.getBlockState(blockPosition).getBlock().equals(Blocks.CHEST)) {
				ChestTileEntity chest = (ChestTileEntity) entity.world.getTileEntity(blockPosition);
				if (chest.count(TPModItems.TOILET_PAPER.get()) > 0) {
					chestsWithToiletPaper.add(new BlockPos(blockPosition));
				}
			}
		}

		if (chestsWithToiletPaper.isEmpty()) {
			return null;
		} else {
			return chestsWithToiletPaper.get(random.nextInt(chestsWithToiletPaper.size()));
		}
	}
}