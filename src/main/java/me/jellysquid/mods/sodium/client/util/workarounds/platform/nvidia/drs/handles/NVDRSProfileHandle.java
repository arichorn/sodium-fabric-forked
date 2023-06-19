package me.jellysquid.mods.sodium.client.util.workarounds.platform.nvidia.drs.handles;

import me.jellysquid.mods.sodium.client.util.workarounds.platform.nvidia.core.util.NvHandle;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;

// NvDRSProfileHandle
public class NVDRSProfileHandle extends NvHandle {
    protected NVDRSProfileHandle(long address, ByteBuffer container) {
        super(address, container);
    }

    public static NVDRSProfileHandle allocateStack(MemoryStack stack) {
        return allocateStack(NVDRSProfileHandle.class, stack);
    }
}
