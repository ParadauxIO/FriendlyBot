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

package io.paradaux.csbot.managers;

import io.paradaux.csbot.FriendlyBot;
import io.paradaux.csbot.models.exceptions.ManagerNotReadyException;
import io.paradaux.csbot.models.interal.ConfigurationEntry;
import org.slf4j.Logger;

import javax.activation.DataHandler;
import javax.annotation.Nullable;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Objects;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Pattern;

public class SMTPManager {

    private final Properties properties = new Properties();
    private final ConfigurationEntry config;
    public static SMTPManager instance;
    private final Logger logger;
    private final Session session;

    public SMTPManager(ConfigurationEntry config, Logger logger) {
        this.config = config;
        this.logger = logger;

        logger.info("Initialising: EmailController");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        session = login();

        instance = this;
    }

    public static SMTPManager getInstance() {
        if (instance == null) {
            throw new ManagerNotReadyException();
        }

        return instance;
    }

    public Session login() {
        properties.put("mail.smtp.host", config.getSmtpHost());
        properties.put("mail.smtp.port", config.getSmtpPort());

        return Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(config.getSmtpUser(), config.getSmtpPass());
            }
        });
    }

    public void sendVerificationEmail(String email, String verificationCode,
                                      String discordUserName) throws MessagingException {

        Message message = new MimeMessage(session);

        message.setFrom(new InternetAddress("verification@paradaux.io"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
        message.setReplyTo(InternetAddress.parse("verification-support@paradaux.io"));

        message.setSubject("Your CSFC Verification Code has arrived!");
        MimeMultipart multipart = new MimeMultipart("related");

        // Add the html
        BodyPart messageBodyPart = new MimeBodyPart();
        String htmlText = Objects.requireNonNull(IOManager.getInstance().readEmailTemplate())
                .replace("%discord_username%", discordUserName)
                .replace("%verification_code%", verificationCode);
        messageBodyPart.setContent(htmlText, "text/html");
        multipart.addBodyPart(messageBodyPart);

        // Add the header image
        messageBodyPart = new MimeBodyPart();
        messageBodyPart.setDisposition(MimeBodyPart.INLINE);
        messageBodyPart.setDataHandler(new DataHandler(FriendlyBot.class
                .getResource("/verification.png")));
        messageBodyPart.setHeader("Content-ID", "<verification-header>");
        multipart.addBodyPart(messageBodyPart);

        // Add the forest image
        messageBodyPart = new MimeBodyPart();
        messageBodyPart.setDisposition(MimeBodyPart.INLINE);
        messageBodyPart.setDataHandler(new DataHandler(FriendlyBot.class
                .getResource("/fir-forest.png")));
        messageBodyPart.setHeader("Content-ID", "<fir-forest>");
        multipart.addBodyPart(messageBodyPart);

        message.setContent(multipart);

        try {
            Transport.send(message);
        } catch (AuthenticationFailedException exception) {
            logger.error("Failed to login to the SMTP Server,"
                    + " is the login information set?");
        }

    }

    /**
     * Checks against a regex pattern whether or not the email provided is valid.
     * @param email The Email you wish to verify is valid
     * @return Whether or not the email is valid
     * */
    public static boolean isValidEmail(String email) {
        Pattern emailValidator = Pattern.compile("(?:[a-z0-9!#$%&'*+\\/=?^_`{|}~-]+(?:\\"
                + ".[a-z0-9!#$%&'*+\\/=?^_`{|}~-]+)*|\""
                + "(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01"
                + "-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)"
                + "+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:"
                + "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}"
                + "(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:"
                + "(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09"
                + "\\x0b\\x0c\\x0e-\\x7f])+)\\])");

        if (email == null) {
            return false;
        }

        return emailValidator.matcher(email.toLowerCase()).matches();
    }

    /**
     * Gets the domain (the section after the @ sign) from an email address.
     * @param email The Email you wish to get the domain of
     * @return The Domain of an email. Returns null if the email is invalid
     * */
    @Nullable
    public static String getEmailDomain(String email) {
        if (!isValidEmail(email)) {
            return null;
        }
        return email.substring(email.indexOf("@") + 1);
    }

    /**
     * Generates a six-digit code with leading zeros.
     * @return 6-digit number with leading zeroes.
     * */
    public static String generateVerificationCode() {
        return String.format("%06d", new Random().nextInt(999999));
    }

}