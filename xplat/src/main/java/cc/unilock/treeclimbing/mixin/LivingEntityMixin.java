package cc.unilock.treeclimbing.mixin;

import cc.unilock.treeclimbing.TreeHelper;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	@Shadow private Optional<BlockPos> lastClimbablePos;

	public LivingEntityMixin(EntityType<?> type, Level level) {
		super(type, level);
		throw new AssertionError();
	}

	@ModifyReturnValue(method = "onClimbable", at = @At("RETURN"))
	private boolean onClimbable(boolean original) {
		if (!this.isSpectator()) {
			AABB bb = this.getBoundingBox();
			for (BlockPos pos : BlockPos.betweenClosed(Mth.floor(bb.minX), Mth.floor(bb.minY), Mth.floor(bb.minZ), Mth.floor(bb.maxX), Mth.floor(bb.maxY), Mth.floor(bb.maxZ))) {
				var helper = new TreeHelper(this.level());
				helper.scan(pos);
				if (helper.isTree()) {
					this.lastClimbablePos = Optional.of(pos);
					return true;
				}
			}
		}
		return original;
	}
}
