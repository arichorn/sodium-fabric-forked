package me.jellysquid.mods.sodium.client.util.workarounds.platform.nvidia.core.util;

import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.Struct;

import java.nio.ByteBuffer;

public class NvHandle extends Struct {
    private static final int SIZEOF = 8, ALIGNOF = 8;

    protected NvHandle(long address, ByteBuffer container) {
        super(address, container);
    }

    protected static <T extends NvHandle> T allocateStack(Class<T> type, MemoryStack stack) {
        return wrap(type, stack.ncalloc(ALIGNOF, 1, SIZEOF));
    }

    public final long value() {
        return MemoryUtil.memGetAddress(this.address);
    }

    @Override
    public final int sizeof() {
        return SIZEOF;
    }
}
