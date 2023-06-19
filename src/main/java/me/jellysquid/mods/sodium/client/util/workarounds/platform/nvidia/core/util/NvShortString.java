package me.jellysquid.mods.sodium.client.util.workarounds.platform.nvidia.core.util;

import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.Struct;

import java.nio.ByteBuffer;

// NvAPI_ShortString
public class NvShortString extends Struct {
    private static final int ARRAY_LENGTH = 64;
    private static final int SIZEOF, ALIGNOF;

    static {
        var layout = __struct(
                __array(Byte.BYTES, ARRAY_LENGTH)
        );

        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();
    }

    protected NvShortString(long address, ByteBuffer container) {
        super(address, container);
    }

    public static NvShortString allocateStack(MemoryStack stack) {
        return wrap(NvShortString.class, stack.ncalloc(ALIGNOF, 1, SIZEOF));
    }

    public String value() {
        return MemoryUtil.memUTF8(this.address);
    }

    @Override
    public int sizeof() {
        return SIZEOF;
    }
}
