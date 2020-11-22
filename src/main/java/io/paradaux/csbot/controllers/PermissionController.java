/*
 * Copyright (c) 2020, Rían Errity. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 3 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 3 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 3 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Rían Errity <rian@paradaux.io> or visit https://paradaux.io
 * if you need additional information or have any questions.
 * See LICENSE.md for more details.
 */

package io.paradaux.csbot.controllers;

import io.paradaux.csbot.models.interal.ConfigurationEntry;
import io.paradaux.csbot.interfaces.IController;
import io.paradaux.csbot.models.interal.PermissionEntry;
import org.slf4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;

public class PermissionController implements IController {

    // Singleton Instance
    public static PermissionController INSTANCE;

    // Dependencies
    private static final ConfigurationEntry configurationEntry = ConfigurationController
            .getConfigurationEntry();
    private static final Logger logger = LogController.getLogger();

    // Singleton Fields
    private static PermissionEntry permissionEntry;
    public static PermissionEntry getPermissionEntry() { return permissionEntry; }

    @Override
    public void initialise() {
        try {
            permissionEntry = FileController.INSTANCE.readPermissionFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        INSTANCE = this;

    }

    public void save() {
        try {
            FileController.INSTANCE.updatePermissionFile(permissionEntry);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addAdmin(String discordID) {
        permissionEntry.getAdministrators().add(discordID);
        save();
    }

    public void removeAdmin(String discordID) {
        permissionEntry.getAdministrators().remove(discordID);
        save();
    }

    public boolean isAdmin(String discordID) {
        return permissionEntry.getAdministrators().contains(discordID);
    }

    public void addMod(String discordID) {
        permissionEntry.getModerators().add(discordID);
        save();
    }

    public void removeMod(String discordID) {
        permissionEntry.getModerators().remove(discordID);
        save();
    }

    public boolean isMod(String discordID) {
        return permissionEntry.getModerators().contains(discordID);
    }

    public void addTechnician(String discordID) {
        permissionEntry.getTechnicians().add(discordID);
        save();
    }

    public void removeTechnician(String discordID) {
        permissionEntry.getTechnicians().add(discordID);
        save();
    }

    public boolean isTechnician(String discordID) {
        return permissionEntry.getTechnicians().contains(discordID);
    }

    public boolean isStaff(String discordID) {
        return isMod(discordID) || isAdmin(discordID);
    }

}
