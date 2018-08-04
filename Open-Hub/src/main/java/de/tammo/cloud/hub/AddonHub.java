package de.tammo.cloud.hub;

import de.tammo.cloud.core.logging.Logger;
import de.tammo.cloud.core.service.Service;

import java.io.File;
import java.io.IOException;

public class AddonHub implements Service {

    public static final File HUB_FOLDER = new File("hub");
    public static final File HUB_ADDON_FILE = new File(HUB_FOLDER, "addons.json");
    public static final File HUB_ADDON_FOLDER = new File(HUB_FOLDER, "addons");

    /**
     * Initialize the service
     */
    @Override
    public void init() {
        try {
            createFiles();
        } catch (IOException e) {
            Logger.error("Error creating File", e);
        }
    }

    /**
     * Creates the files and folders necessary
     *
     * @throws IOException if the files cannot be created
     */
    private void createFiles() throws IOException {
        if(!HUB_FOLDER.exists()) HUB_FOLDER.mkdir();
        if(!HUB_ADDON_FILE.exists()) HUB_ADDON_FILE.createNewFile();
        if(!HUB_ADDON_FOLDER.exists()) HUB_ADDON_FOLDER.mkdir();
    }
}
