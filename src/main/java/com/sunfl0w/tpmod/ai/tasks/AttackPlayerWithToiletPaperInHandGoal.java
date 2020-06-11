package com.sunfl0w.tpmod.ai.tasks;

import java.util.ArrayList;
import java.util.Random;

import com.sunfl0w.tpmod.init.TPModItems;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;

public class AttackPlayerWithToiletPaperInHandGoal extends Goal {
	private CreatureEntity entity;
	private PlayerEntity playerEntity;
	private Random random = new Random();

	private int attackTick;
	private int delayCounter;
	private double targetX;
	private double targetY;
	private double targetZ;

	public AttackPlayerWithToiletPaperInHandGoal(CreatureEntity entity) {
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
			entity.getNavigator().tryMoveToXYZ((double) ((float) playerEntity.getPosition().getX()) + 0.5D,
					(double) (playerEntity.getPosition().getY() + 1),
					(double) ((float) playerEntity.getPosition().getZ()) + 0.5D, 0.7f);

			this.entity.getLookController().setLookPositionWithEntity(playerEntity, 30.0F, 30.0F);
			double d0 = this.entity.getDistanceSq(playerEntity.func_226277_ct_(), playerEntity.func_226278_cu_(),
					playerEntity.func_226281_cx_());
			--this.delayCounter;
			if ((this.entity.getEntitySenses().canSee(playerEntity)) && this.delayCounter <= 0
					&& (this.targetX == 0.0D && this.targetY == 0.0D && this.targetZ == 0.0D
							|| playerEntity.getDistanceSq(this.targetX, this.targetY, this.targetZ) >= 1.0D
							|| this.entity.getRNG().nextFloat() < 0.05F)) {
				this.targetX = playerEntity.func_226277_ct_();
				this.targetY = playerEntity.func_226278_cu_();
				this.targetZ = playerEntity.func_226281_cx_();
				this.delayCounter = 4 + this.entity.getRNG().nextInt(7);
				if (d0 > 1024.0D) {
					this.delayCounter += 10;
				} else if (d0 > 256.0D) {
					this.delayCounter += 5;
				}

				if (!this.entity.getNavigator().tryMoveToEntityLiving(playerEntity, 0.8f)) {
					this.delayCounter += 15;
				}
			}

			this.attackTick = Math.max(this.attackTick - 1, 0);
			this.checkAndPerformAttack(playerEntity, d0);
			if (entity.getPosition().distanceSq(playerEntity.getPosition()) < 2.0f
					|| !playerEntity.getHeldItemMainhand().getItem().equals(TPModItems.TOILET_PAPER.get())) {
				playerEntity = null;
			}
		}
	}

	private void checkAndPerformAttack(LivingEntity enemy, double distToEnemySqr) {
		//Using MobEntity attackEntityAsMob() code.
		double range = this.getAttackReachSqr(enemy);
		if (distToEnemySqr <= range && this.attackTick <= 0) {
			this.attackTick = 50;

			float damage = 0.5f;
			float knockback = 1;
			if (enemy instanceof LivingEntity) {
				damage += EnchantmentHelper.getModifierForCreature(entity.getHeldItemMainhand(),
						((LivingEntity) enemy).getCreatureAttribute());
				knockback += (float) EnchantmentHelper.getKnockbackModifier(entity);
			}

			boolean flag = enemy.attackEntityFrom(DamageSource.causeMobDamage(this.entity), damage);
			if (flag) {
				if (knockback > 0.0F && enemy instanceof LivingEntity) {
					((LivingEntity) enemy).knockBack(entity, knockback * 0.5F,
							(double) MathHelper.sin(entity.rotationYaw * ((float) Math.PI / 180F)),
							(double) (-MathHelper.cos(entity.rotationYaw * ((float) Math.PI / 180F))));
					entity.setMotion(entity.getMotion().mul(0.6D, 1.0D, 0.6D));
				}
			}
		}
	}

	private double getAttackReachSqr(LivingEntity attackTarget) {
		return (double) (this.entity.getWidth() * 2.0F * this.entity.getWidth() * 2.0F + attackTarget.getWidth());
	}

	private PlayerEntity getPlayerWithToiletPaperInHand() {
		ArrayList<PlayerEntity> playerEntitiesWithToiletPaper = new ArrayList<PlayerEntity>();
		ArrayList<PlayerEntity> entities = (ArrayList<PlayerEntity>) entity.world.getEntitiesWithinAABB(
				PlayerEntity.class,
				new AxisAlignedBB(entity.getPosition().getX() - 25, 0, entity.getPosition().getZ() - 25,
						entity.getPosition().getX() + 25, 255, entity.getPosition().getZ() + 25));
		for (PlayerEntity entity : entities) {
			if (entity.getHeldItemMainhand().getItem().equals(TPModItems.TOILET_PAPER.get())) {
				playerEntitiesWithToiletPaper.add(entity);
			}
		}

		if (playerEntitiesWithToiletPaper.isEmpty()) {
			return null;
		} else {
			return playerEntitiesWithToiletPaper.get(random.nextInt(playerEntitiesWithToiletPaper.size()));
		}
	}

	public boolean shouldContinueExecuting() {
		if (playerEntity == null) {
			return false;
		} else if (!playerEntity.isAlive()) {
			return false;
		} else {
			return !(playerEntity instanceof PlayerEntity)
					|| !playerEntity.isSpectator() && !((PlayerEntity) playerEntity).isCreative();
		}
	}
}