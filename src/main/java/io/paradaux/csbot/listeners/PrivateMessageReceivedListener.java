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

package io.paradaux.csbot.listeners;

import io.paradaux.csbot.api.ConfigurationCache;
import io.paradaux.csbot.api.VerificationSystem;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class PrivateMessageReceivedListener extends ListenerAdapter {

    ConfigurationCache configurationCache;

    public PrivateMessageReceivedListener(ConfigurationCache configurationCache) {
        this.configurationCache = configurationCache;
    }

    @Override
    public void onPrivateMessageReceived(@NotNull PrivateMessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        if (!VerificationSystem.isPending(event.getAuthor().getId())) {
            event.getChannel().sendMessage("You are not currently pending verification.").queue();
        }

        Message message = event.getMessage();
        String verificationCode = VerificationSystem.getVerificationCode(event.getAuthor().getId());


        if (verificationCode != null && verificationCode.equals(message.getContentRaw())) {

            Guild guild = message.getJDA().getGuildById(configurationCache.getGuild());

            message.getChannel().sendMessage("You have been successfully verified.").queue();
            guild.addRoleToMember(Objects.requireNonNull(message.getAuthor()).getId(), Objects.requireNonNull(guild.getRoleById(configurationCache.getVerifiedRole()))).queue();
            VerificationSystem.setVerified(message.getAuthor());
        } else {
            message.getChannel().sendMessage("Invalid Verification Code provided. Please check the email and try again.").queue();
        }

    }

}
