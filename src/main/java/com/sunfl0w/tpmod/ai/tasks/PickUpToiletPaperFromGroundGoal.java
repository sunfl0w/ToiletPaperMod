package com.sunfl0w.tpmod.ai.tasks;

import java.util.ArrayList;
import java.util.Random;

import com.sunfl0w.tpmod.init.TPModItems;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.util.math.AxisAlignedBB;

public class PickUpToiletPaperFromGroundGoal extends Goal {
	private CreatureEntity entity;
	private ItemEntity toiletPaperEntity;
	private Random random = new Random();

	public PickUpToiletPaperFromGroundGoal(CreatureEntity entity) {
		this.entity = entity;
	}

	@Override
	public boolean shouldExecute() {
		return true;
	}

	public void tick() {
		if (toiletPaperEntity == null) {
			toiletPaperEntity = getToiletPaperEntity();
		} else {
			entity.getNavigator().tryMoveToXYZ((double) ((float) toiletPaperEntity.getPosition().getX()) + 0.5D, (double) (toiletPaperEntity.getPosition().getY() + 1),
					(double) ((float) toiletPaperEntity.getPosition().getZ()) + 0.5D, 0.7f);
			if (entity.getPosition().distanceSq(toiletPaperEntity.getPosition()) < 3.0f) {
				toiletPaperEntity.remove();
				toiletPaperEntity = null;
			}
		}
	}

	private ItemEntity getToiletPaperEntity() {
		ArrayList<ItemEntity> toiletPaperEntities = new ArrayList<ItemEntity>();
		ArrayList<ItemEntity> entities = (ArrayList<ItemEntity>) entity.world.getEntitiesWithinAABB(ItemEntity.class,
				new AxisAlignedBB(entity.getPosition().getX() - 25, 0, entity.getPosition().getZ() - 25, entity.getPosition().getX() + 25, 255, entity.getPosition().getZ() + 25));
		for (ItemEntity entity : entities) {
			if (((ItemEntity) entity).getItem().getItem().equals(TPModItems.TOILET_PAPER.get())) {
				toiletPaperEntities.add(entity);
			}
		}
		
		if(toiletPaperEntities.isEmpty()) {
			return null;
		} else {
			return toiletPaperEntities.get(random.nextInt(toiletPaperEntities.size()));
		}
	}
}