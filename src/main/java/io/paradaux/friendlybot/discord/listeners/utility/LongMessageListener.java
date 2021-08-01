/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.listeners.utility.LongMessageListener :  03/02/2021, 05:02
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

package io.paradaux.friendlybot.discord.listeners.utility;

import io.paradaux.friendlybot.core.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.core.utils.models.types.DiscordEventListener;
import io.paradaux.http.HttpApi;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;
import org.slf4j.Logger;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.CompletableFuture;

public class LongMessageListener extends DiscordEventListener {

    private static final HashSet<String> TEXT_FILE_ENDINGS = new HashSet<>();

    static {
        Collections.addAll(TEXT_FILE_ENDINGS, ".txt", ".yml", ".json", ".html", ".htm", ".php", ".py", ".java", ".c", "sh", ".cgi",
                ".pl", ".cpp", ".r", ".xml");
    }

    public LongMessageListener(ConfigurationEntry config, Logger logger) {
        super(config, logger);
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        Message message = event.getMessage();

        if (message.getAttachments().isEmpty()) {
            return;
        }

        HttpApi http = new HttpApi(getLogger());

        for (Message.Attachment attachment : message.getAttachments()) {
            String attachmentUrl = attachment.getUrl();

            boolean found = false;
            // Make sure only approved file types are uploaded to bytebin.
            for (String fileType : TEXT_FILE_ENDINGS) {
                if (attachmentUrl.endsWith(fileType)) {
                    found = true;
                    break;
                }
            }

            if (!found) {
                return;
            }

            HttpRequest request = http.plainRequest(attachmentUrl);
            CompletableFuture<HttpResponse<byte[]>> responseFuture = http.sendAsync(request, HttpResponse.BodyHandlers.ofByteArray());

            if (responseFuture == null) {
                throw new RuntimeException("There was an issue pertaining to the HTTP Request for the attachment: " + attachmentUrl);
            }

            responseFuture.thenAccept((response) -> {
                if (response.body() == null) {
                    throw new RuntimeException("There was an issue pertaining to the HTTP Request for the attachment: " + attachmentUrl);
                }

                HttpRequest postToBytebin = http.postBytes(getConfig().getBytebinInstance() + "/post", response.body());
                HttpResponse<String> bytebinLink = http.sendSync(postToBytebin, HttpResponse.BodyHandlers.ofString());

                if (bytebinLink == null || bytebinLink.body() == null) {
                    throw new RuntimeException("There was an issue with the response received from bytebin for the attachment: " + attachmentUrl);
                }

                String link = getConfig().getBytebinInstance() + "/" + new JSONObject(bytebinLink.body()).getString("key");

                EmbedBuilder builder = new EmbedBuilder()
                        .setAuthor("Bytebin Service", getConfig().getBytebinInstance(), event.getAuthor().getAvatarUrl())
                        .setDescription("Here's the above message in text form, so you don't have to download anything! " + link);

                message.getChannel().sendMessage(builder.build()).queue();
            }).join();

        }

    }
}
