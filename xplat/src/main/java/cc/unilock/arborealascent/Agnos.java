package cc.unilock.arborealascent;

import java.nio.file.Path;

public abstract class Agnos {
	public static Agnos delegate;

	static {
		try {
			Class.forName("cc.unilock.arborealascent.AgnosFabric");
		} catch (Throwable ignored) {
		}
		try {
			Class.forName("cc.unilock.arborealascent.AgnosForge");
		} catch (Throwable ignored) {
		}
	}
	public static Path getConfigDirectory() {
		return delegate.getConfigDirectoryAgnos();
	}

	protected abstract Path getConfigDirectoryAgnos();
}
