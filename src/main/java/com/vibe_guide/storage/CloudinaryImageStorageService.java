package com.vibe_guide.storage;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.IOException;
import java.net.URI;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CloudinaryImageStorageService implements ImageStorageService {
  private static final String UPLOAD_SEGMENT = "/upload/";

  private final Cloudinary cloudinary;
  private final ImageStorageProperties properties;

  public CloudinaryImageStorageService(Cloudinary cloudinary, ImageStorageProperties properties) {
    this.cloudinary = cloudinary;
    this.properties = properties;
  }

  @Override
  public String store(MultipartFile file, String folderName) {
    validateImage(file);

    String originalFilename = Objects.requireNonNullElse(file.getOriginalFilename(), "image");
    String fileName = StringUtils.cleanPath(originalFilename).replace(" ", "_");
    String publicId = buildPublicId(folderName, fileName);

    try {
      @SuppressWarnings("unchecked")
      Map<String, Object> uploadResult =
          cloudinary
              .uploader()
              .upload(
                  file.getBytes(),
                  ObjectUtils.asMap(
                      "public_id", publicId,
                      "folder", properties.cloudinaryFolder(),
                      "resource_type", "image",
                      "overwrite", true));

      return Objects.toString(uploadResult.get("secure_url"), null);
    } catch (IOException e) {
      throw new IllegalArgumentException("Failed to upload image to Cloudinary", e);
    }
  }

  @Override
  public void delete(String storedPath) {
    if (storedPath == null || storedPath.isBlank()) {
      return;
    }

    String publicId = extractPublicId(storedPath);
    if (publicId == null) {
      return;
    }

    try {
      cloudinary
          .uploader()
          .destroy(publicId, ObjectUtils.asMap("resource_type", "image", "invalidate", true));
    } catch (IOException e) {
      throw new IllegalStateException("Failed to delete Cloudinary image", e);
    }
  }

  private void validateImage(MultipartFile file) {
    if (file == null || file.isEmpty()) {
      throw new IllegalArgumentException("Image file is empty");
    }

    String contentType = file.getContentType();
    if (contentType == null || !contentType.startsWith("image")) {
      throw new IllegalArgumentException("Uploaded file is not an image");
    }
  }

  private String buildPublicId(String folderName, String fileName) {
    String extension = StringUtils.getFilenameExtension(fileName);
    String baseName = StringUtils.stripFilenameExtension(fileName);
    String uniqueName = UUID.randomUUID() + "-" + baseName;

    if (extension == null || extension.isBlank()) {
      return folderName + "/" + uniqueName;
    }

    return folderName + "/" + uniqueName;
  }

  private String extractPublicId(String secureUrl) {
    URI uri = URI.create(secureUrl);
    String path = uri.getPath();
    int uploadIndex = path.indexOf(UPLOAD_SEGMENT);
    if (uploadIndex < 0) {
      return null;
    }

    String afterUpload = path.substring(uploadIndex + UPLOAD_SEGMENT.length());
    String[] segments = afterUpload.split("/");
    int publicIdStart = 0;
    for (int i = 0; i < segments.length; i++) {
      if (segments[i].matches("v\\d+")) {
        publicIdStart = i + 1;
        break;
      }
    }

    if (publicIdStart >= segments.length) {
      return null;
    }

    String publicIdWithExtension = String.join("/", java.util.Arrays.copyOfRange(segments, publicIdStart, segments.length));
    int extensionIndex = publicIdWithExtension.lastIndexOf('.');
    if (extensionIndex < 0) {
      return publicIdWithExtension;
    }

    return publicIdWithExtension.substring(0, extensionIndex);
  }
}
