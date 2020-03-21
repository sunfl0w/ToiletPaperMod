package com.sunfl0w.tpmod.ai.tasks;

import java.util.ArrayList;
import java.util.Random;

import com.sunfl0w.tpmod.TPMod;
import com.sunfl0w.tpmod.init.TPModItems;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;

public class GoToPlayerIfHeHasToiletPaperInHandGoal extends Goal {
	private CreatureEntity entity;
	private PlayerEntity playerEntity;
	private Random random = new Random();
	
	public GoToPlayerIfHeHasToiletPaperInHandGoal(CreatureEntity entity) {
		this.entity = entity;
	}

	@Override
	public boolean shouldExecute() {
		return true;
	}
	
	public void tick() {
		if (playerEntity == null) {
			playerEntity = getPlayerWithToiletPaperInHand();
		} else {
			entity.getNavigator().tryMoveToXYZ((double) ((float) playerEntity.getPosition().getX()) + 0.5D, (double) (playerEntity.getPosition().getY() + 1),
					(double) ((float) playerEntity.getPosition().getZ()) + 0.5D, 0.7f);
			if (entity.getPosition().distanceSq(playerEntity.getPosition()) < 2.0f || !playerEntity.getHeldItemMainhand().getItem().equals(TPModItems.TOILET_PAPER.get())) {
				playerEntity = null;
			}
		}
	}
	
	public PlayerEntity getPlayerWithToiletPaperInHand() {
		ArrayList<PlayerEntity> playerEntitiesWithToiletPaper = new ArrayList<PlayerEntity>();
		ArrayList<PlayerEntity> entities = (ArrayList<PlayerEntity>) entity.world.getEntitiesWithinAABB(PlayerEntity.class,
				new AxisAlignedBB(entity.getPosition().getX() - 25, 0, entity.getPosition().getZ() - 25, entity.getPosition().getX() + 25, 255, entity.getPosition().getZ() + 25));
		for (PlayerEntity entity : entities) {
			if (entity.getHeldItemMainhand().getItem().equals(TPModItems.TOILET_PAPER.get())) {
				playerEntitiesWithToiletPaper.add(entity);
			}
		}
		
		if(playerEntitiesWithToiletPaper.isEmpty()) {
			return null;
		} else {
			return playerEntitiesWithToiletPaper.get(random.nextInt(playerEntitiesWithToiletPaper.size()));
		}
	}
}
