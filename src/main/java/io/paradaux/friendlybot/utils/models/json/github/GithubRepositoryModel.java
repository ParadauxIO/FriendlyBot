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

package io.paradaux.friendlybot.utils.models.json.github;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.time.Instant;

public class GithubRepositoryModel implements Serializable {

    private long id;
    private String name;
    private boolean priv;
    private GithubUserModel owner;

    @SerializedName("html_url")
    private String htmlUrl;

    private String description;
    private boolean fork;
    private String url;

    @SerializedName("created_at")
    private Instant createdAt;

    @SerializedName("updated_at")
    private Instant updatedAt;

    @SerializedName("pushed_at")
    private Instant pushedAt;

    private String homepage;
    private long size;

    @SerializedName("stargazers_count")
    private long stargazers;

    private String language;

    @SerializedName("has_wiki")
    private boolean wiki;

    private boolean archived;
    private boolean disabled;
    private GithubRepositoryLicenseModel license;
    private long forks;

    @SerializedName("open_issues")
    private long openIssues;

    private long watchers;

    @SerializedName("default_branch")
    private String defaultBranch;

    @SerializedName("subscribers_count")
    private long subscribers;

    public GithubRepositoryModel() {
        this.id = -1L;
        this.name = null;
        this.priv = false;
        this.owner = null;
        this.htmlUrl = null;
        this.description = null;
        this.fork = false;
        this.url = null;
        this.createdAt = null;
        this.updatedAt = null;
        this.pushedAt = null;
        this.homepage = null;
        this.size = -1L;
        this.stargazers = -1L;
        this.language = null;
        this.wiki = false;
        this.archived = false;
        this.disabled = false;
        this.license = null;
        this.forks = -1L;
        this.openIssues = -1L;
        this.watchers = -1L;
        this.defaultBranch = null;
        this.subscribers = -1L;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isPriv() {
        return priv;
    }

    public GithubUserModel getOwner() {
        return owner;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public String getDescription() {
        return description;
    }

    public boolean isFork() {
        return fork;
    }

    public String getUrl() {
        return url;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getPushedAt() {
        return pushedAt;
    }

    public String getHomepage() {
        return homepage;
    }

    public long getSize() {
        return size;
    }

    public long getStargazers() {
        return stargazers;
    }

    public String getLanguage() {
        return language;
    }

    public boolean isWiki() {
        return wiki;
    }

    public boolean isArchived() {
        return archived;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public GithubRepositoryLicenseModel getLicense() {
        return license;
    }

    public long getForks() {
        return forks;
    }

    public long getOpenIssues() {
        return openIssues;
    }

    public long getWatchers() {
        return watchers;
    }

    public String getDefaultBranch() {
        return defaultBranch;
    }

    public long getSubscribers() {
        return subscribers;
    }

    public GithubRepositoryModel setId(long id) {
        this.id = id;
        return this;
    }

    public GithubRepositoryModel setName(String name) {
        this.name = name;
        return this;
    }

    public GithubRepositoryModel setPriv(boolean priv) {
        this.priv = priv;
        return this;
    }

    public GithubRepositoryModel setOwner(GithubUserModel owner) {
        this.owner = owner;
        return this;
    }

    public GithubRepositoryModel setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
        return this;
    }

    public GithubRepositoryModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public GithubRepositoryModel setFork(boolean fork) {
        this.fork = fork;
        return this;
    }

    public GithubRepositoryModel setUrl(String url) {
        this.url = url;
        return this;
    }

    public GithubRepositoryModel setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public GithubRepositoryModel setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public GithubRepositoryModel setPushedAt(Instant pushedAt) {
        this.pushedAt = pushedAt;
        return this;
    }

    public GithubRepositoryModel setHomepage(String homepage) {
        this.homepage = homepage;
        return this;
    }

    public GithubRepositoryModel setSize(long size) {
        this.size = size;
        return this;
    }

    public GithubRepositoryModel setStargazers(long stargazers) {
        this.stargazers = stargazers;
        return this;
    }

    public GithubRepositoryModel setLanguage(String language) {
        this.language = language;
        return this;
    }

    public GithubRepositoryModel setWiki(boolean wiki) {
        this.wiki = wiki;
        return this;
    }

    public GithubRepositoryModel setArchived(boolean archived) {
        this.archived = archived;
        return this;
    }

    public GithubRepositoryModel setDisabled(boolean disabled) {
        this.disabled = disabled;
        return this;
    }

    public GithubRepositoryModel setLicense(GithubRepositoryLicenseModel license) {
        this.license = license;
        return this;
    }

    public GithubRepositoryModel setForks(long forks) {
        this.forks = forks;
        return this;
    }

    public GithubRepositoryModel setOpenIssues(long openIssues) {
        this.openIssues = openIssues;
        return this;
    }

    public GithubRepositoryModel setWatchers(long watchers) {
        this.watchers = watchers;
        return this;
    }

    public GithubRepositoryModel setDefaultBranch(String defaultBranch) {
        this.defaultBranch = defaultBranch;
        return this;
    }

    public GithubRepositoryModel setSubscribers(long subscribers) {
        this.subscribers = subscribers;
        return this;
    }

    public static final class GithubRepositoryLicenseModel implements Serializable {
        private String key;
        private String name;
        private String url;

        public GithubRepositoryLicenseModel() {
            this.key = null;
            this.name = null;
            this.url = null;
        }

        public String getKey() {
            return key;
        }
        public String getName() {
            return name;
        }

        public String getUrl() {
            return url;
        }

        public GithubRepositoryLicenseModel setKey(String key) {
            this.key = key;
            return this;
        }

        public GithubRepositoryLicenseModel setName(String name) {
            this.name = name;
            return this;
        }

        public GithubRepositoryLicenseModel setUrl(String url) {
            this.url = url;
            return this;
        }
    }
}
