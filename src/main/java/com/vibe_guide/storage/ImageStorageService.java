package com.vibe_guide.storage;

import org.springframework.web.multipart.MultipartFile;

public interface ImageStorageService {
  String store(MultipartFile file, String folderName);

  void delete(String storedPath);
}

