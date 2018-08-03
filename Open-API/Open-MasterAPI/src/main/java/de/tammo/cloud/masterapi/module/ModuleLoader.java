package de.tammo.cloud.masterapi.module;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class ModuleLoader
{
    public void load(File folder)
    {
        final List<File> moduleFiles = Arrays.stream(folder.listFiles()).filter(file -> file.getName().endsWith(".jar")).collect(Collectors.toList());

        URL[] urls = new URL[moduleFiles.size()];
        int i = 0;
        for (File f : moduleFiles)
        {
            try
            {
                urls[i] = f.toURL();
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
            i++;
        }
        URLClassLoader loader = new URLClassLoader(urls);
        for(File file : moduleFiles)
        {
            try
            {
                JarFile jf = new JarFile(file);
                Enumeration<JarEntry> entry = jf.entries();
                while(entry.hasMoreElements())
                {
                    JarEntry je = entry.nextElement();
                    if(je.getName().endsWith(".class"))
                    {
                        Class<?> clazz = loader.loadClass(je.getName().replace("/", ".").substring(0, je.getName().length() - 6));

                        if(clazz.getSuperclass().equals(Module.class))
                        {
                            if(clazz.isAnnotationPresent(ModuleInfo.class))
                            {
                                Module module = (Module) clazz.newInstance();
                                //TODO: Register Module with ModuleManager instance
                            }
                        }
                    }
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            catch (ClassNotFoundException e)
            {
                e.printStackTrace();
            }
            catch (IllegalAccessException e)
            {
                e.printStackTrace();
            }
            catch (InstantiationException e)
            {
                e.printStackTrace();
            }
        }
    }
}
