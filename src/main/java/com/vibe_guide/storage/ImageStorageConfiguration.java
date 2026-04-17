package com.vibe_guide.storage;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ImageStorageProperties.class)
public class ImageStorageConfiguration {}

