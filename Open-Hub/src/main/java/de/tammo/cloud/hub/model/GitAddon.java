package de.tammo.cloud.hub.model;

import de.tammo.cloud.hub.AddonHub;
import de.tammo.cloud.hub.OsChecker;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.File;
import java.io.IOException;

@RequiredArgsConstructor
@Getter
@Setter
public class GitAddon {

    private String repositoryUrl;
    private String branch;
    private boolean autoUpdate = true;

    /**
     * Clones the Addon via the repositoryUrl
     *
     * @return a Git object for further working
     * @throws GitAPIException this Exception occurs if the clone-fails
     */
    public Git cloneAddon() throws GitAPIException {
        return Git.cloneRepository()
                .setURI(repositoryUrl)
                .setBranch(branch)
                .setDirectory(new File(AddonHub.HUB_ADDON_FOLDER, getRepositoryName()))
                .call();
    }

    /**
     * Updates the addon
     *
     * @return a PullResult for further working
     * @throws IOException occurs if the Files cannot be created
     * @throws GitAPIException this Exception occurs if the pull-fails
     */
    public PullResult updateAddon() throws IOException, GitAPIException {
        return Git.open(new File(AddonHub.HUB_ADDON_FILE, getRepositoryName()))
                .pull()
                .call();
    }

    /**
     * Compiles an Addon with the specific fields
     *
     * @return the working compile Process from maven.
     * @throws IOException if the Files cannot be created
     */
    public Process compileAddon() throws IOException {
        String command = "./mvnw";

        if(OsChecker.isOnWindows()) {
            command += ".cmd";
        }

        command += " clean package";

        return new ProcessBuilder()
                .directory(new File(AddonHub.HUB_ADDON_FOLDER, getRepositoryName()))
                .command(command)
                .start();
    }

    /**
     * Gets the Repository-name splitted of the repositoryUrl
     *
     * @return the Repository-name
     */
    public String getRepositoryName() {
        return repositoryUrl.split(".")[repositoryUrl.split(".").length - (repositoryUrl.endsWith(".git") ? 2 : 1)];
    }
}
