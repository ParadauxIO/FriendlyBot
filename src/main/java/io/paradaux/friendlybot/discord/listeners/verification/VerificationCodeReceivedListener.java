/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.listeners.verification.VerificationCodeReceivedListener :  31/01/2021, 01:26
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

package io.paradaux.friendlybot.discord.listeners.verification;

import io.paradaux.friendlybot.core.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.core.utils.models.exceptions.VerificationException;
import io.paradaux.friendlybot.core.utils.models.types.DiscordEventListener;
import io.paradaux.friendlybot.managers.DiscordBotManager;
import io.paradaux.friendlybot.managers.MongoManager;
import io.paradaux.friendlybot.managers.VerificationManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

public class VerificationCodeReceivedListener extends DiscordEventListener {

    public VerificationCodeReceivedListener(ConfigurationEntry config, Logger logger) {
        super(config, logger);
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        Message message = event.getMessage();

        if (message.getChannelType() == ChannelType.PRIVATE) {
            return;
        }

        if (event.getAuthor().isBot()) {
            return;
        }

        if (!event.getChannel().getId().equals(getConfig().getVerificationInputChannelId())) {
            return;
        }

        MongoManager mongo = MongoManager.getInstance();

        // If they aren't expected to input a verification code
        if (!mongo.isPendingVerification(event.getAuthor().getId())) {
            return;
        }

        // We don't want the message
        message.delete().queue();

        // If the message isn't a six-digit number
        if (!message.getContentRaw().matches("^[0-9]{1,6}")) {
            return;
        }

        String verificationCode = message.getContentRaw();
        String discordID = event.getAuthor().getId();
        String guildID = event.getGuild().getId();

        VerificationManager verification = VerificationManager.getInstance();

        try {
            if (verification.setVerified(discordID, guildID, verificationCode)) {
                event.getAuthor().openPrivateChannel().queue((channel) -> channel.sendMessage(
                        "You" + " have successfully verified your friendly corner discord account"
                                + ".").queue());

                var user = event.getAuthor();
                var embed = new EmbedBuilder()
                        .setTitle(user.getAsTag() + " has passed verification.")
                        .setColor(0x00cc99)
                        .build();

                DiscordBotManager.getInstance().getChannel(getConfig().getPublicAuditLogChannelId()).sendMessage(embed).queue();

            } else {
                event.getAuthor().openPrivateChannel().queue((channel) -> channel.sendMessage(
                        "The Verification code you provided was incorrect. If this issue "
                                + "persists please contact staff.").queue());
            }
        } catch (VerificationException exception) {
            event.getAuthor().openPrivateChannel().queue((channel) -> channel.sendMessage("An "
                    + "unknown error occurred during verification. Please contact staff. \nError "
                    + "Details: " + exception.getMessage()).queue());
        }
    }

}
