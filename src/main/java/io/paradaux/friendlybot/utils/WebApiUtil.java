/*
 * Copyright (c) 2021 |  Rían Errity. GPLv3
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

package io.paradaux.friendlybot.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class WebApiUtil {

    private static final Logger logger = LoggerFactory.getLogger(WebApiUtil.class);

    private WebApiUtil() {

    }

    @CheckReturnValue
    @Nonnull
    public static String getStringWithThrows(@Nonnull URL url) throws IOException {
        return getStringWithThrows(url, null, 5000, null, null, null, 20);
    }

    @CheckReturnValue
    @Nonnull
    public static String getStringWithThrows(@Nonnull URL url, String method) throws IOException {
        return getStringWithThrows(url, method, 5000, null, null, null, 20);
    }

    @CheckReturnValue
    @Nonnull
    public static String getStringWithThrows(@Nonnull URL url, String method, int timeout) throws IOException {
        return getStringWithThrows(url, method, timeout, null, null, null, 20);
    }

    @CheckReturnValue
    @Nonnull
    public static String getStringWithThrows(@Nonnull URL url, String method, int timeout, String userAgent) throws IOException {
        return getStringWithThrows(url, method, timeout, userAgent, null, null, 20);
    }

    @CheckReturnValue
    @Nonnull
    public static String getStringWithThrows(@Nonnull URL url, String method, int timeout,
                                             String userAgent, Map<String, String> headers) throws IOException {
        return getStringWithThrows(url, method, timeout, userAgent, headers, null, 20);
    }

    @CheckReturnValue
    @Nonnull
    public static String getStringWithThrows(@Nonnull URL url, String method, int timeout,
                                             String userAgent, Map<String, String> headers,
                                             Map<String, String> postData) throws IOException {
        return getStringWithThrows(url, method, timeout, userAgent, headers, postData, 20);
    }

    @CheckReturnValue
    @Nonnull
    public static String getStringWithThrows(@Nonnull URL url, String method, int timeout, String userAgent,
                                             Map<String, String> headers, Map<String, String> postData,
                                             int maxRedirects) throws IOException {
        if (headers == null) {
            headers = new HashMap<>();
        }
        if (!hasKey(headers, "Accept-Language")) {
            headers.put("Accept-Language", "en-US,en;q=0.8");
        }

        try (InputStream in = getInputStreamWithThrows(url, method, timeout, userAgent, headers, postData, maxRedirects);
             InputStreamReader reader = new InputStreamReader(in); BufferedReader buffer = new BufferedReader(reader)) {
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = buffer.readLine()) != null) {
                builder.append(line);
            }
            return builder.toString();
        }
    }

    @CheckReturnValue
    @Nonnull
    public static InputStream getInputStreamWithThrows(@Nonnull URL url) throws IOException {
        return getInputStreamWithThrows(url, null, 5000, null, null, null, 20);
    }

    @CheckReturnValue
    @Nonnull
    public static InputStream getInputStreamWithThrows(@Nonnull URL url, String method) throws IOException {
        return getInputStreamWithThrows(url, method, 5000, null, null, null, 20);
    }

    @CheckReturnValue
    @Nonnull
    public static InputStream getInputStreamWithThrows(@Nonnull URL url, String method, int timeout) throws IOException {
        return getInputStreamWithThrows(url, method, timeout, null, null, null, 20);
    }

    @CheckReturnValue
    @Nonnull
    public static InputStream getInputStreamWithThrows(@Nonnull URL url, String method, int timeout, String userAgent) throws IOException {
        return getInputStreamWithThrows(url, method, timeout, userAgent, null, null, 20);
    }

    @CheckReturnValue
    @Nonnull
    public static InputStream getInputStreamWithThrows(@Nonnull URL url, String method, int timeout, String userAgent,
                                                       Map<String, String> headers) throws IOException {
        return getInputStreamWithThrows(url, method, timeout, userAgent, headers, null, 20);
    }

    @CheckReturnValue
    @Nonnull
    public static InputStream getInputStreamWithThrows(@Nonnull URL url, String method, int timeout, String userAgent,
                                                       Map<String, String> headers, Map<String, String> postData) throws IOException {
        return getInputStreamWithThrows(url, method, timeout, userAgent, headers, postData, 20);
    }

    @CheckReturnValue
    @Nonnull
    public static InputStream getInputStreamWithThrows(@Nonnull URL url, String method, int timeout, String userAgent,
                                                       Map<String, String> headers, Map<String, String> postData,
                                                       int maxRedirects) throws IOException {
        HttpURLConnection conn = getConnectionWithThrows(url, method, timeout, userAgent, headers, postData, maxRedirects);
        int status = conn.getResponseCode();

        if (status >= 400 && status < 600) {
            // 400-500 errors
            throw new IOException("Server returned status code " + status);
        }

        return conn.getInputStream();
    }

    @CheckReturnValue
    @Nonnull
    public static HttpURLConnection getConnectionWithThrows(@Nonnull URL url) throws IOException {
        return getConnectionWithThrows(url, null, 5000, null, null, null, 20);
    }

    @CheckReturnValue
    @Nonnull
    public static HttpURLConnection getConnectionWithThrows(@Nonnull URL url, String method) throws IOException {
        return getConnectionWithThrows(url, method, 5000, null, null, null, 20);
    }

    @CheckReturnValue
    @Nonnull
    public static HttpURLConnection getConnectionWithThrows(@Nonnull URL url, String method, int timeout) throws IOException {
        return getConnectionWithThrows(url, method, timeout, null, null, null, 20);
    }

    @CheckReturnValue
    @Nonnull
    public static HttpURLConnection getConnectionWithThrows(@Nonnull URL url, String method, int timeout,
                                                            String userAgent) throws IOException {
        return getConnectionWithThrows(url, method, timeout, userAgent, null, null, 20);
    }

    @CheckReturnValue
    @Nonnull
    public static HttpURLConnection getConnectionWithThrows(@Nonnull URL url, String method, int timeout, String userAgent,
                                                            Map<String, String> headers) throws IOException {
        return getConnectionWithThrows(url, method, timeout, userAgent, headers, null, 20);
    }

    @CheckReturnValue
    @Nonnull
    public static HttpURLConnection getConnectionWithThrows(@Nonnull URL url, String method, int timeout, String userAgent,
                                                            Map<String, String> headers, Map<String, String> postData) throws IOException {
        return getConnectionWithThrows(url, method, timeout, userAgent, headers, postData, 20);
    }

    @CheckReturnValue
    @Nonnull
    public static HttpURLConnection getConnectionWithThrows(@Nonnull  URL url, String method, int timeout, String userAgent,
                                                            Map<String, String> headers, Map<String, String> postData,
                                                            int maxRedirects) throws IOException {
        HttpURLConnection conn = getConnection(url, method, timeout, userAgent, headers, postData, maxRedirects);
        int status = conn.getResponseCode();

        if (status >= 200 && status < 300) {
            if (status == 205) { // Reset
                throw new IOException("Could not get connection (HTTP status " + status + " - reset connection)");
            }
            return conn;
        } else if (status >= 400 && status < 500) {
            if (status == 401 || status == 403) { // Unauthorized, forbidden
                throw new IOException("Could not get connection (HTTP status " + status + " - access denied)");
            }
            if (status == 429) { // Too many queries
                throw new IOException("Could not get connection (HTTP status " + status + " - too many queries, temporary issue)");
            }
            throw new IOException("Could not get connection (HTTP status " + status + ")");
        } else if (status >= 500 && status < 600) { // Server errors (usually temporary)
            throw new IOException("Could not get connection (HTTP status " + status + ")");
        }
        throw new IOException("Could not get connection (HTTP status " + status + ")");
    }

    @CheckReturnValue
    @Nonnull
    public static HttpURLConnection getConnection(@Nonnull URL url) throws IOException {
        return getConnection(url, null, 5000, null, null, null, 20);
    }

    @CheckReturnValue
    @Nonnull
    public static HttpURLConnection getConnection(@Nonnull URL url, String method) throws IOException {
        return getConnection(url, method, 5000, null, null, null, 20);
    }

    @CheckReturnValue
    @Nonnull
    public static HttpURLConnection getConnection(@Nonnull URL url, String method, int timeout) throws IOException {
        return getConnection(url, method, timeout, null, null, null, 20);
    }

    @CheckReturnValue
    @Nonnull
    public static HttpURLConnection getConnection(@Nonnull URL url, String method, int timeout, String userAgent) throws IOException {
        return getConnection(url, method, timeout, userAgent, null, null, 20);
    }

    @CheckReturnValue
    @Nonnull
    public static HttpURLConnection getConnection(@Nonnull URL url, String method, int timeout, String userAgent,
                                                  Map<String, String> headers) throws IOException {
        return getConnection(url, method, timeout, userAgent, headers, null, 20);
    }

    @CheckReturnValue
    @Nonnull
    public static HttpURLConnection getConnection(@Nonnull URL url, String method, int timeout, String userAgent,
                                                  Map<String, String> headers, Map<String, String> postData) throws IOException {
        return getConnection(url, method, timeout, userAgent, headers, postData, 20);
    }

    @CheckReturnValue
    @Nonnull
    public static HttpURLConnection getConnection(@Nonnull URL url, String method, int timeout, String userAgent,
                                                  Map<String, String> headers, Map<String, String> postData,
                                                  int maxRedirects) throws IOException {
        logger.debug("Fetching URL: " + url);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        setConnectionProperties(conn, method, timeout, userAgent, headers, postData, null);

        Set<String> previousUrls = new HashSet<>();
        previousUrls.add(url.toExternalForm() + (headers != null && headers.containsKey("Set-Cookie") ? ":" + headers.get("Set-Cookie") : ""));

        int status;
        boolean redirect;
        do {
            status = conn.getResponseCode();
            redirect = status == HttpURLConnection.HTTP_MOVED_TEMP || status == HttpURLConnection.HTTP_MOVED_PERM || status == HttpURLConnection.HTTP_SEE_OTHER;

            if (redirect) {
                String cookies = conn.getHeaderField("Set-Cookie");
                String newUrl = conn.getHeaderField("Location");
                if (newUrl.charAt(0) == '/') {
                    newUrl = new URL(url.getProtocol(), url.getHost(), url.getPort(), newUrl, null).toExternalForm();
                }
                if (!previousUrls.add(newUrl + (cookies != null ? ":" + cookies : ""))) {
                    throw new IOException("Recursive redirect detected.");
                }
                if (maxRedirects >= 0 && previousUrls.size() > maxRedirects) {
                    throw new IOException("Too many redirects.");
                }
                logger.debug("Redirected to URL: " + newUrl);
                conn = (HttpURLConnection) new URL(newUrl).openConnection();
                setConnectionProperties(conn, method, timeout, userAgent, headers, postData, cookies);
            }
        } while (redirect);

        return conn;
    }

    @CheckReturnValue
    @Nonnull
    public static InputStream getInputStream(@Nonnull HttpURLConnection conn) throws IOException {
        int status = conn.getResponseCode();

        if (status >= 400 && status < 600) {
            // 400-500 errors
            throw new IOException("Server returned status code " + status);
        }

        return conn.getInputStream();
    }

    @CheckReturnValue
    private static boolean hasKey(@Nonnull Map<String, String> map, @Nonnull String key) {
        for (String k : map.keySet()) {
            if (k.equalsIgnoreCase(key)) {
                return true;
            }
        }
        return false;
    }

    private static void setConnectionProperties(@Nonnull HttpURLConnection conn, String method, int timeout, String userAgent,
                                                Map<String, String> headers, Map<String, String> postData, String cookies) throws IOException {
        conn.setInstanceFollowRedirects(false);
        conn.setConnectTimeout(timeout);
        conn.setReadTimeout(timeout);

        if (method != null && !method.isEmpty()) {
            conn.setRequestMethod(method);
        }
        if (userAgent != null && !userAgent.isEmpty()) {
            conn.setRequestProperty("User-Agent", userAgent);
        }
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, String> kvp : headers.entrySet()) {
                conn.setRequestProperty(kvp.getKey(), kvp.getValue());
            }
        }
        if (cookies != null && !cookies.isEmpty()) {
            conn.setRequestProperty("Cookie", cookies);
        }
        if (postData != null) {
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            StringBuilder data = new StringBuilder();
            for (Map.Entry<String, String> kvp : postData.entrySet()) {
                data.append(URLEncoder.encode(kvp.getKey(), StandardCharsets.UTF_8.name()));
                data.append('=');
                data.append(URLEncoder.encode(kvp.getValue(), StandardCharsets.UTF_8.name()));
                data.append('&');
            }
            if (data.length() > 0) {
                data.deleteCharAt(data.length() - 1);
            }
            byte[] dataBytes = data.toString().getBytes(StandardCharsets.UTF_8);
            conn.setRequestProperty("Content-Length", String.valueOf(dataBytes.length));
            conn.setDoOutput(true);
            try (OutputStream out = conn.getOutputStream()) {
                out.write(dataBytes);
            }
        }
    }
}
