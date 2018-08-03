package de.tammo.cloud.masterapi.module;

import java.io.File;

public class Module
{
    private File configFolder;

    /**
     * The onEnable Method.
     * Called if the Module gets Enabled
     */
    public void onEnable() {}

    /**
     * The onDisable Method.
     * Called if the Module gets Disabled
     */
    public void onDisable() {}

    /**
     * @return the ModuleInfo Annotation
     */
    public ModuleInfo getModuleInfo()
    {
        return getClass().getAnnotation(ModuleInfo.class);
    }

    public Module getModule() { return this; }

    public File getConfigFolder()
    {
        if(configFolder == null)
        {
            configFolder = new File(getModuleInfo().name());
        }

        return configFolder;
    }

    public String getModuleEnableMessage()
    {
        return "Enabled Module " + toString() + ".";
    }

    public String getModuleDisableMessage()
    {
        return "Disabled Module " + toString() + ".";
    }

    @Override
    public String toString()
    {
        return getModuleInfo().name() + "[" + getModuleInfo().version() + "]";
    }
}
