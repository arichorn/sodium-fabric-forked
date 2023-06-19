package me.jellysquid.mods.sodium.client.util.workarounds.platform.nvidia;

import me.jellysquid.mods.sodium.client.SodiumClientMod;
import me.jellysquid.mods.sodium.client.util.workarounds.platform.nvidia.core.NvAPI;
import me.jellysquid.mods.sodium.client.util.workarounds.platform.nvidia.drs.enums.NVDRSGPUSupport;
import me.jellysquid.mods.sodium.client.util.workarounds.platform.nvidia.drs.enums.NVDRSSettingLocation;
import me.jellysquid.mods.sodium.client.util.workarounds.platform.nvidia.drs.enums.NVDRSSettingType;
import me.jellysquid.mods.sodium.client.util.workarounds.platform.nvidia.drs.handles.NVDRSProfileHandle;
import me.jellysquid.mods.sodium.client.util.workarounds.platform.nvidia.drs.handles.NVDRSSessionHandle;
import me.jellysquid.mods.sodium.client.util.workarounds.platform.nvidia.drs.settings.ESettings;
import me.jellysquid.mods.sodium.client.util.workarounds.platform.nvidia.drs.settings.EValues;
import me.jellysquid.mods.sodium.client.util.workarounds.platform.nvidia.drs.structures.NVDRSProfileV1;
import me.jellysquid.mods.sodium.client.util.workarounds.platform.nvidia.drs.structures.NVRDSApplicationV4;
import me.jellysquid.mods.sodium.client.util.workarounds.platform.nvidia.drs.structures.NVRDSSettingV1;
import org.lwjgl.system.MemoryStack;

import static me.jellysquid.mods.sodium.client.util.workarounds.platform.nvidia.drs.NvDRS.*;

public class NvidiaWorkarounds {
    public static void forceInstallProfile() {
        try {
            installProfile();
        } catch (Throwable t) {
            SodiumClientMod.logger()
                    .error("Couldn't install NVIDIA DRS profile!", t);
            throw t; // TODO: make this a non-fatal error
        }
    }

    public static void installProfile() {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            NvAPI.checkError(NvAPI.NvAPI_Initialize());

            NVDRSSessionHandle hSession = NVDRSSessionHandle.allocateStack(stack);
            NvAPI.checkError(NvAPI_DRS_CreateSession(hSession.address()));
            NvAPI.checkError(NvAPI_DRS_LoadSettings(hSession.value()));

            NVDRSProfileV1 profile = NVDRSProfileV1.allocateStack(stack);
            profile.setVersion(NVDRSProfileV1.VERSION);
            profile.setGpuSupport(NVDRSGPUSupport.all());
            profile.setIsPredefined(false);
            profile.setProfileName("Sodium Driver Overrides");

            NVDRSProfileHandle hProfile = NVDRSProfileHandle.allocateStack(stack);
            NvAPI.checkError(NvAPI_DRS_CreateProfile(hSession.value(), profile.address(), hProfile.address()));

            NVRDSApplicationV4 application = NVRDSApplicationV4.allocateStack(stack);
            application.setVersion(NVRDSApplicationV4.VERSION);
            application.setIsPredefined(false);
            application.setAppName("javaw.exe");
            application.setUserFriendlyName("Minecraft using Sodium Renderer");

            NvAPI.checkError(NvAPI_DRS_CreateApplication(hSession.value(), hProfile.value(), application.address()));

            NVRDSSettingV1 setting = NVRDSSettingV1.allocateStack(stack);
            setting.setVersion(NVRDSSettingV1.VERSION);
            setting.setSettingId(ESettings.OGL_THREAD_CONTROL_ID);
            setting.setSettingType(NVDRSSettingType.DWORD);
            setting.setSettingLocation(NVDRSSettingLocation.CURRENT_PROFILE);
            setting.setIsCurrentPredefined(false);
            setting.setPredefinedValid(false);
            setting.getPredefinedValue()
                    .setValue(EValues.OGLThreadControl.DISABLE);
            setting.getCurrentValue()
                    .setValue(EValues.OGLThreadControl.DISABLE);

            NvAPI.checkError(NvAPI_DRS_SetSetting(hSession.value(), hProfile.value(), setting.address()));
            NvAPI.checkError(NvAPI_DRS_SaveSettings(hSession.value()));
            NvAPI.checkError(NvAPI_DRS_DestroySession(hSession.value()));
            NvAPI.checkError(NvAPI.NvAPI_Unload());
        }
    }
}
