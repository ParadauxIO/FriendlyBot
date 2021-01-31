/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.managers.PermissionManager :  31/01/2021, 01:26
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.paradaux.friendlybot.managers;

import io.paradaux.friendlybot.utils.models.configuration.PermissionEntry;
import io.paradaux.friendlybot.utils.models.exceptions.ManagerNotReadyException;
import org.slf4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;

public class PermissionManager {

    // Singleton Instance
    public static PermissionManager instance;

    // Singleton Fields
    private PermissionEntry permissions;
    private final Logger logger;

    public PermissionManager(Logger logger) {
        this.logger = logger;

        logger.info("Initialising: PermissionController");

        try {
            permissions = IOManager.getInstance().readPermissionFile();
        } catch (FileNotFoundException e) {
            logger.info("Permissions file was not found.");
        }

        instance = this;
    }

    public static PermissionManager getInstance() {
        if (instance == null) {
            throw new ManagerNotReadyException();
        }

        return instance;
    }

    public void save() {
        try {
            IOManager.getInstance().updatePermissionFile(permissions);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addAdmin(String discordID) {
        permissions.getAdministrators().add(discordID);
        save();
    }

    public void removeAdmin(String discordID) {
        permissions.getAdministrators().remove(discordID);
        save();
    }

    public boolean isAdmin(String discordID) {
        return permissions.getAdministrators().contains(discordID);
    }

    public void addMod(String discordID) {
        permissions.getModerators().add(discordID);
        save();
    }

    public void removeMod(String discordID) {
        permissions.getModerators().remove(discordID);
        save();
    }

    public boolean isMod(String discordID) {
        return permissions.getModerators().contains(discordID);
    }

    public void addTechnician(String discordID) {
        permissions.getTechnicians().add(discordID);
        save();
    }

    public void removeTechnician(String discordID) {
        permissions.getTechnicians().add(discordID);
        save();
    }

    public boolean isTechnician(String discordID) {
        return permissions.getTechnicians().contains(discordID);
    }

}
