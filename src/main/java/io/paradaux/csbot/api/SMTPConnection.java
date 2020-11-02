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

package io.paradaux.csbot.api;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * SMTPConnection represents the connection to the SMTP Server and facilitates the sending of emails for the project.
 *
 * @author Rían Errity
 * @version Last Modified for 0.1.0-SNAPSHOT
 * @since 2/11/2020 DD/MM/YY
 * @see io.paradaux.csbot.CSBot
 * */

public class SMTPConnection {

    Properties props = new Properties();
    String smtpUser, smtpPass, smtpServer, smtpPort;
    Session session;

    public SMTPConnection(ConfigurationCache configurationCache) {
        this.smtpUser = configurationCache.getSmtpUser();
        this.smtpPass = configurationCache.getSmtpPass();
        this.smtpServer = configurationCache.getSmtpServer();
        this.smtpPort = configurationCache.getSmtpPort();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", smtpServer);
        props.put("mail.smtp.port", smtpPort);

        session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(smtpUser, smtpPass);
            }
        });
    }

    public void sendVerificationEmail(String email, String verificationCode) throws MessagingException {
        Message message = new MimeMessage(session);

        message.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
        message.setFrom(new InternetAddress("verification@paradaux.io"));
        message.setReplyTo(InternetAddress.parse("verification-support@paradaux.io"));

        message.setSubject("Your Friendly Verification Code has arrived!");
        message.setText("We're so happy you've gone through verification. Your email address is not saved on our servers, we respect your privacy! " +
                "We just needed to make sure you're actually a student here. It helps us mitigate against raiding, and makes our members accountable for their actions." +
                "\n\nYour Verification Code is: " + verificationCode + "\n\n Message this code to the bot via a private message (DM) Do not post this into the verification channel.");

        Transport.send(message);
    }

}
