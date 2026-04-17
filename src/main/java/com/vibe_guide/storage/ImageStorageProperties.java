package com.vibe_guide.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.storage")
public record ImageStorageProperties(String cloudinaryFolder) {}
