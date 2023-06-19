package me.jellysquid.mods.sodium.client.util.workarounds.platform.nvidia.drs.handles;

import me.jellysquid.mods.sodium.client.util.workarounds.platform.nvidia.core.util.NvHandle;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;

// NvDRSSessionHandle
public class NVDRSSessionHandle extends NvHandle {
    protected NVDRSSessionHandle(long address, ByteBuffer container) {
        super(address, container);
    }

    public static NVDRSSessionHandle allocateStack(MemoryStack stack) {
        return allocateStack(NVDRSSessionHandle.class, stack);
    }
}
