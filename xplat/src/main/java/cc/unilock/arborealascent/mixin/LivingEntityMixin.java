package cc.unilock.arborealascent.mixin;

import cc.unilock.arborealascent.TreeHelper;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
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
		if (Player.class.isAssignableFrom(getClass()) && !this.isSpectator()) {
			AABB bb = this.getBoundingBox();
			for (BlockPos pos : BlockPos.betweenClosed(Mth.floor(bb.minX-0.03125), Mth.floor(bb.minY), Mth.floor(bb.minZ-0.03125), Mth.floor(bb.maxX), Mth.floor(bb.maxY), Mth.floor(bb.maxZ))) {
				if (TreeHelper.create(this.level()).isTreeTrunk(pos)) {
					this.lastClimbablePos = Optional.of(pos);
					return true;
				}
			}
		}
		return original;
	}
}
