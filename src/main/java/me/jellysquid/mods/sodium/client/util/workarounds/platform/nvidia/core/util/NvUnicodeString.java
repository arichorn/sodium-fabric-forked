package me.jellysquid.mods.sodium.client.util.workarounds.platform.nvidia.core.util;

import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.Struct;

import java.nio.ByteBuffer;

// NvAPI_UnicodeString
public class NvUnicodeString extends Struct {
    private static final int MAX_LENGTH = 2048;

    public static final int SIZEOF;
    public static final int ALIGNOF;

    static {
        var layout = __struct(
                __array(Short.BYTES, MAX_LENGTH)
        );

        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();
    }

    protected NvUnicodeString(long address, ByteBuffer container) {
        super(address, container);
    }

    public static NvUnicodeString fromPointer(long addr) {
        return wrap(NvUnicodeString.class, addr);
    }

    public String getValue() {
        return MemoryUtil.memUTF16(this.address);
    }

    public void setValue(String string) {
        if (string == null) {
            MemoryUtil.memPutShort(this.address, (short) '\0');
        } else {
            MemoryUtil.memUTF16(string, true,
                    MemoryUtil.memByteBuffer(this.address, MAX_LENGTH));
        }
    }

    @Override
    public int sizeof() {
        return SIZEOF;
    }
}
