package de.tammo.cloud.hub.model;

import de.tammo.cloud.core.logging.Logger;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.IOException;
import java.util.List;

@Getter
@Setter
public class GitAddonFile {
    private List<GitAddon> addons;

    /**
     * Update all addons, which addons contains.
     */
    public void updateAllAddons() {
        addons.forEach(addon -> {
            try {
                addon.compileAddon();
                addon.updateAddon();
            } catch (IOException | GitAPIException e) {
                Logger.error("Error during addon update: " + addon.getRepositoryName(), e);
            }
        });
    }
}
