package me.jellysquid.mods.sodium.client.util.workarounds.platform.nvidia.drs.enums;

import me.jellysquid.mods.sodium.client.util.workarounds.platform.nvidia.core.util.NvUnicodeString;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.Struct;

import java.nio.ByteBuffer;

// NVDRS_SETTING_VALUES
public class NVDRSSettingValues extends Struct {
    public static final int SIZEOF, ALIGNOF;

    private static final int OFFSET_U32_VALUE;
    private static final int OFFSET_BINARY_VALUE;
    private static final int OFFSET_WSZ_VALUE;

    static {
        var layout = __union(
                __member(Integer.BYTES), // u32
                __member(Binary.SIZEOF, Binary.ALIGNOF), // binary,
                __member(NvUnicodeString.SIZEOF, NvUnicodeString.ALIGNOF) // wsz
        );

        OFFSET_U32_VALUE = layout.offsetof(0);
        OFFSET_BINARY_VALUE = layout.offsetof(1);
        OFFSET_WSZ_VALUE = layout.offsetof(2);

        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();
    }

    protected NVDRSSettingValues(long address, ByteBuffer container) {
        super(address, container);
    }

    public static NVDRSSettingValues fromPointer(long ptr) {
        return wrap(NVDRSSettingValues.class, ptr);
    }

    public void setValue(int value) {
        MemoryUtil.memPutInt(this.address + OFFSET_U32_VALUE, value);
    }

    public void setValue(byte[] value) {
        Binary.fromPointer(this.address + OFFSET_BINARY_VALUE)
                .setValue(value);
    }

    public void setValue(String value) {
        NvUnicodeString.fromPointer(this.address + OFFSET_WSZ_VALUE)
                .setValue(value);
    }

    @Override
    public int sizeof() {
        return SIZEOF;
    }

    public static class Binary extends Struct {
        private static final int MAX_LENGTH = 4096;
        public static final int SIZEOF;
        public static final int ALIGNOF;

        private static final int OFFSET_VALUE_LENGTH;
        private static final int OFFSET_VALUE_DATA;

        static {
            var layout = __struct(
                    __member(Integer.BYTES), // valueLength
                    __array(Byte.BYTES, MAX_LENGTH) // valueData
            );

            OFFSET_VALUE_LENGTH = layout.offsetof(0);
            OFFSET_VALUE_DATA = layout.offsetof(1);

            SIZEOF = layout.getSize();
            ALIGNOF = layout.getAlignment();
        }

        protected Binary(long address, ByteBuffer container) {
            super(address, container);
        }

        public static Binary fromPointer(long ptr) {
            return wrap(Binary.class, ptr);
        }

        public void setValue(byte[] data) {
            MemoryUtil.memPutInt(this.address + OFFSET_VALUE_LENGTH, data.length);

            for (int idx = 0; idx < data.length; idx++) {
                MemoryUtil.memPutByte(this.address + OFFSET_VALUE_DATA + idx, data[idx]);
            }
        }

        @Override
        public int sizeof() {
            return SIZEOF;
        }
    }
}
