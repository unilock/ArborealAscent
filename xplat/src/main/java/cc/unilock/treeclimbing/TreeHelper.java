package cc.unilock.treeclimbing;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

import java.util.HashSet;
import java.util.Iterator;

public class TreeHelper {
	// TODO: config
	private static final int maxDepth = 100;
	private static final int minLeaves = 5;
	private static final int minLogs = 2;

	private final BlockGetter level;

	private final HashSet<BlockPos> scanned = new HashSet<>();
	private int leaves = 0;
	private int logs = 0;

	public TreeHelper(BlockGetter level) {
		this.level = level;
	}

	public boolean isTree() {
		return this.leaves >= minLeaves && this.logs >= minLogs;
	}

	public void scan(BlockPos center) {
		HashSet<BlockPos> scanning = new HashSet<>();
		HashSet<BlockPos> todo = new HashSet<>();
		this.add(center, scanning);

		int depth = 0;
		while (!scanning.isEmpty() && depth < maxDepth) {
			depth++;

			Iterator<BlockPos> it = scanning.iterator();
			while (it.hasNext()) {
				BlockPos scan = it.next();
				this.scanned.add(scan);

				for (int x = -1; x <= 1; x++) {
					for (int y = -1; y <= 1; y++) {
						for (int z = -1; z <= 1; z++) {
							if (x == 0 && y == 0 && z == 0)
								continue;

							BlockPos pos = scan.offset(x, y, z);
							if (!this.scanned.contains(pos)) {
								this.add(pos, todo);
							}
						}
					}
				}

				it.remove();
			}

			if (this.isTree()) {
				return;
			}

			scanning.addAll(todo);
			todo.clear();
		}
	}

	private void add(BlockPos pos, HashSet<BlockPos> set) {
		BlockState state = level.getBlockState(pos);

		if (state.is(BlockTags.LEAVES)) {
			set.add(pos);
			leaves++;
		}
		if (state.is(BlockTags.LOGS)) {
			set.add(pos);
			logs++;
		}
	}
}
