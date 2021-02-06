/*
 * MIT License
 *
 * Copyright (c) 2021 Rían Errity
 * io.paradaux.friendlybot.managers.MailGunManager :  31/01/2021, 01:26
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

import io.paradaux.friendlybot.utils.models.configuration.ConfigurationEntry;
import io.paradaux.friendlybot.utils.models.exceptions.ManagerNotReadyException;
import io.paradaux.friendlybot.utils.models.exceptions.VerificationException;
import okhttp3.*;
import org.slf4j.Logger;

import java.io.IOException;
import java.io.Reader;
import java.util.Base64;
import java.util.concurrent.CompletableFuture;

public class MailGunManager {

    private static MailGunManager instance;
    private final ConfigurationEntry config;
    private final Logger logger;

    public MailGunManager(ConfigurationEntry config, Logger logger) {
        this.config = config;
        this.logger = logger;

        instance = this;
    }

    public static MailGunManager getInstance() {
        if (instance == null) {
            throw new ManagerNotReadyException();
        }

        return instance;
    }

    public void sendEmail(String recipient, String tag, String code) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();

        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("from", config.getVerificationEmailSender())
                .addFormDataPart("to", recipient)
                .addFormDataPart("subject", config.getVerificationEmailSubject())
                .addFormDataPart("template",config.getVerificationEmailTemplateName())
                .addFormDataPart("v:a", tag)
                .addFormDataPart("v:b", code)
                .build();

        Request request = new Request.Builder()
                .url(config.getMailGunBaseUrl())
                .method("POST", body)
                .addHeader("Authorization", "Basic " + StringUtils.basicAuth("api", config.getMailGunApplicationKey()))
                .build();

        sendAsync(client, request).thenAccept((response) -> {

            if (response.body() == null) {
                throw new VerificationException("No response received.");
            }

            try (Reader reader = response.body().charStream()) {
                int charInt;
                StringBuilder strBuilder = new StringBuilder();
                while ((charInt = reader.read()) != -1) {
                    strBuilder.append((char) charInt);
                }

                System.out.println(strBuilder.toString());
            } catch (IOException ok) {
                logger.error("Error occurred whilst interacting with mailgun.");
                throw new VerificationException();
            }

        }).join();

    }

    public CompletableFuture<Response> sendAsync(OkHttpClient client, Request request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return client.newCall(request).execute();
            } catch (IOException e) {
                logger.error("Error occurred whilst interacting with mailgun.");
                return null;
            }
        });
    }

    private static String basicAuth(String user, String pass) {
        return Base64.getEncoder().encodeToString((user + ":" + pass).getBytes());
    }

}