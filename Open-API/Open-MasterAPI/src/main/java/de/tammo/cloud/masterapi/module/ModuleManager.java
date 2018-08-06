package de.tammo.cloud.masterapi.module;

import java.util.HashMap;

public class ModuleManager
{
    private HashMap<String, Module> modules = new HashMap<>();

    /**
     * Unregisters all modules of the modules HashMap
     */
    public void unregisterAllModules()
    {
        for(Module module : modules.values())
        {
            unregisterModule(module);
        }
    }

    /**
     * Method to register a Module
     *
     * @param module the module to register
     */
    public void registerModule(Module module)
    {
        modules.put(module.getModuleInfo().name(), module);
        module.onEnable();
        System.out.println(module.getModuleEnableMessage());
    }

    /**
     * Method to unregister a Module
     *
     * @param module the module to unregister
     */
    public void unregisterModule(Module module)
    {
        modules.remove(module.getModuleInfo().name());
        module.onDisable();
        System.out.println(module.getModuleDisableMessage());
    }

    /**
     * Method to find a Module by a specified name
     *
     * @param name of the module
     * @return The module found by the name
     */
    public Module getModuleByName(String name)
    {
        return modules.get(name);
    }
}
