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

import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import io.paradaux.csbot.api.ConfigurationCache;
import io.paradaux.csbot.commands.*;
import net.dv8tion.jda.api.entities.Activity;

public class CommandController implements IController {

    public static CommandController INSTANCE;

    public static CommandClient commandClient;
    public static CommandClient getCommandClient() { return commandClient; }

    private static final ConfigurationCache configurationCache = ConfigurationController.getConfigurationCache();

    @Override
    public void initialise() {
        commandClient = createCommandClient();
        INSTANCE = this;
    }

    /**
     * Creates a CommanddClient instance which is provided by JDAUtilities. It handles a lot of the command listening, and acts as a clean command wrapper.
     * @return An instance of CommandClient.
     * */
    public static CommandClient createCommandClient() {
        CommandClientBuilder builder = new CommandClientBuilder()
                .setOwnerId(configurationCache.getAdmins().get(0))
                .setPrefix(configurationCache.getPrefix())
                .setActivity(Activity.playing("with your emotions"))
                .addCommands(
                        new AdminCommand(),
                        new BanCommand(),
                        new InviteCommand(),
                        new KickCommand(),
                        new PingCommand(),
                        new VerifyCommand(),
                        new WarnCommand()
                );

        return builder.build();
    }

}