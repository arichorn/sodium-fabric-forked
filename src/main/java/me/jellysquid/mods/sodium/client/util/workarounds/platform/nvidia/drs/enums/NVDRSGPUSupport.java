package me.jellysquid.mods.sodium.client.util.workarounds.platform.nvidia.drs.enums;

// NVDRS_GPU_SUPPORT
public enum NVDRSGPUSupport {
    GEFORCE,
    QUADRO,
    NVS;

    public static int all() {
        return -1;
    }
}
