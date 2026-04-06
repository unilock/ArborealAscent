package cc.unilock.arborealascent;

import folk.sisby.kaleido.api.ReflectiveConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.Comment;
import folk.sisby.kaleido.lib.quiltconfig.api.values.TrackedValue;

public class ModConfig extends ReflectiveConfig {
	@Comment("Skip checking if a log is part of a tree - always climb logs!")
	public final TrackedValue<Boolean> skipTreeCheck = value(false);

	@Comment("The maximum depth to scan for blocks to discover a tree")
	public final TrackedValue<Integer> maxDepth = value(100);

	@Comment("The minimum number of leaves to check for in a tree")
	public final TrackedValue<Integer> minLeaves = value(5);

	@Comment("The minimum number of logs to check for in a tree")
	public final TrackedValue<Integer> minLogs = value(2);
}
