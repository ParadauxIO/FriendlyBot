/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.listeners.verification.VerificationEmailReceivedListener :  31/01/2021, 01:26
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

package io.paradaux.friendlybot.listeners.verification;

import io.paradaux.friendlybot.managers.MongoManager;
import io.paradaux.friendlybot.managers.VerificationManager;
import io.paradaux.friendlybot.utils.StringUtils;
import io.paradaux.friendlybot.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.utils.models.exceptions.VerificationException;
import io.paradaux.friendlybot.utils.models.types.DiscordEventListener;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

public class VerificationEmailReceivedListener extends DiscordEventListener {

    public VerificationEmailReceivedListener(ConfigurationEntry config, Logger logger) {
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

        if (!event.getChannel().getId().equals(getConfig().getVerificationChannelID())) {
            return;
        }

        // We need to handle the message deletion.
        message.delete().queue();

        String email = message.getContentRaw();

        // If the message is a verification code.
        if (email.matches("^[0-9]{1,6}")) {
            return;
        }

        String discordID = event.getAuthor().getId();
        String guildID = event.getGuild().getId();

        MongoManager mongo = MongoManager.getInstance();

        if (mongo.isVerified(discordID)) {
            event.getAuthor().openPrivateChannel().queue((channel) -> channel
                    .sendMessage("Error: You are already verified.").queue());
            return;
        }

        if (mongo.isPendingVerification(discordID)) {
            event.getAuthor().openPrivateChannel().queue((channel) -> channel
                    .sendMessage("Error: You are already pending verification.").queue());
            return;
        }

        if (!StringUtils.isValidEmail(email)) {
            event.getAuthor().openPrivateChannel().queue((channel) -> channel
                    .sendMessage("Your message did contain a valid email address.").queue());
            return;
        }

        // If it isn't an @tcd.ie email

        String emailDomain = StringUtils.getEmailDomain(email);

        if (emailDomain == null) {
            event.getAuthor().openPrivateChannel().queue((channel) -> channel
                    .sendMessage("An unknown error occurred: Email Address was Null").queue());
            return;
        }

        if (!emailDomain.equals("tcd.ie")) {
            event.getAuthor().openPrivateChannel().queue((channel) -> channel
                    .sendMessage("You must use a valid @tcd.ie email address to go through "
                            + "automatic verification.\nIf you are not a trinity student please "
                            + "respond to the bot in this channel and a moderator will be with you"
                            + " shortly.\nPlease message the bot if you run into issues with this,"
                            + " a moderator/technician will be with you shortly.").queue());
            return;
        }

        VerificationManager verification = VerificationManager.getInstance();

        try {
            verification.setPendingVerification(email, guildID, discordID);
        } catch (VerificationException exception) {
            event.getAuthor().openPrivateChannel().queue((channel) -> channel.sendMessage("An "
                    + "unknown error occurred during verification. Please contact staff. \nError "
                    + "Details: " + exception.getMessage()).queue());
        }

        // Notify the user that there's an email waiting for them.
        event.getAuthor().openPrivateChannel().queue((channel) -> channel
                .sendMessage("Please check your email for a verification token. Once you have"
                        + " it, please paste it back into #verification.\nPlease message the bot"
                        + " if you run into issues with this, a moderator/technician will be with"
                        + " you shortly.").queue());
    }
}
