package com.nicolas.fileuploader.files;

import com.nicolas.fileuploader.resource.FileUploaderResource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Objects;
import javax.sql.rowset.serial.SerialBlob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import static java.nio.file.Files.*;

public class MultipartFileWrapper {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(FileUploaderResource.class);

  private final File uploadedFile;
  private final BasicFileAttributes metadata;
  private final Blob fileContent;

  public MultipartFileWrapper(final MultipartFile multipartFile) {
    Objects.requireNonNull(multipartFile);
    this.uploadedFile = multipartToFile(multipartFile);
    Path filePath = Paths.get(uploadedFile.getAbsolutePath());
    this.metadata = getBasicFileAttributes(filePath);
    this.fileContent = getSerialBlob(filePath);
  }

  private SerialBlob getSerialBlob(Path filePath) {
    SerialBlob serialBlob;
    try {
      serialBlob = new SerialBlob(readAllBytes(filePath));
      Files.delete(filePath);
    } catch (SQLException | IOException e) {
      LOGGER.error("File cannot be converted to Blob");
      throw new IllegalArgumentException(e);
    }
    return serialBlob;
  }

  private BasicFileAttributes getBasicFileAttributes(Path filePath) {
    try {
      return readAttributes(filePath, BasicFileAttributes.class);
    } catch (IOException e) {
      LOGGER.error("File Metadata could not be retrieved");
      throw new IllegalArgumentException(e);
    }
  }

  private File multipartToFile(MultipartFile file) {
    File convFile = new File(file.getOriginalFilename());
    try {
      FileOutputStream fos = new FileOutputStream(convFile);
      fos.write(file.getBytes());
      fos.close();
    } catch (IOException e) {
      LOGGER.error("Could not convert multipart file.");
      throw new IllegalArgumentException(e);
    }
    return convFile;
  }

  public String name() {
    return uploadedFile.getName();
  }

  public Blob blob() {
    return fileContent;
  }

  public String creationDate() {
    return metadata.creationTime().toString();
  }
}
