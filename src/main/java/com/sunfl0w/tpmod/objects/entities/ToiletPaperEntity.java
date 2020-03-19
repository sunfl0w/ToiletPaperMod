package com.sunfl0w.tpmod.objects.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ToiletPaperEntity extends ProjectileItemEntity {

	public ToiletPaperEntity(EntityType<? extends ToiletPaperEntity> entityType, World world) {
		super(entityType, world);
	}

	public ToiletPaperEntity(World world, LivingEntity thrower) {
		super(EntityType.SNOWBALL, thrower, world);
	}

	public ToiletPaperEntity(World world, double x, double y, double z) {
		super(EntityType.SNOWBALL, x, y, z, world);
	}

	// Returns the corresponding item.
	@Override
	protected Item func_213885_i() {
		return null;
	}

	@OnlyIn(Dist.CLIENT)
	private IParticleData getParticleData() {
		ItemStack itemstack = this.func_213882_k();
		return new ItemParticleData(ParticleTypes.ITEM, itemstack);
		//return (IParticleData) (itemstack.isEmpty() ? ParticleTypes.ITEM_SNOWBALL : new ItemParticleData(ParticleTypes.ITEM, itemstack));
	}

	@OnlyIn(Dist.CLIENT)
	public void handleStatusUpdate(byte id) {
		if (id == 3) {
			IParticleData iparticledata = getParticleData();
			for (int i = 0; i < 8; ++i) {
				this.world.addParticle(iparticledata, this.getPosition().getX(), this.getPosition().getY(), this.getPosition().getZ(), 0.0D, 0.0D, 0.0D);
			}
		}

	}
	
	protected void onImpact(RayTraceResult result) {
		if (result.getType() == RayTraceResult.Type.ENTITY) {
			Entity entity = ((EntityRayTraceResult) result).getEntity();
			entity.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), (float) 1);
		}

		if (!this.world.isRemote) {
			this.world.setEntityState(this, (byte) 3);
			this.remove();
		}
	}
}