package com.nicolas.fileuploader.resource;

import com.nicolas.fileuploader.domain.UploadedFile;
import com.nicolas.fileuploader.service.FileUploaderService;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@EnableAutoConfiguration
@RequestMapping(value = "/api/files", produces = APPLICATION_JSON_VALUE)
public class FileUploaderResource {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(FileUploaderResource.class);

  private final FileUploaderService fileUploaderService;

  public FileUploaderResource(FileUploaderService fileUploaderService) {
    this.fileUploaderService = fileUploaderService;
  }

  @RequestMapping(value = "/upload", method = RequestMethod.POST)
  void uploadFile(@RequestParam("file") MultipartFile file,
      @RequestParam("description") String description) {
    fileUploaderService.uploadMultipartFile(file, description);
  }

  @GetMapping(value = "/list")
  public List<UploadedFile> listFiles() {
    List<UploadedFile> uploadedFiles = fileUploaderService.listFiles();
    /**
     * Note: Possibly add a custom jackson serializer for ignoring Blob property in serialization
     * cleaning up line below. Will keep for simplicity
     */
    uploadedFiles.stream().forEach(uf -> uf.setFile(null));
    return uploadedFiles;
  }

  @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
  public void getFile(@PathVariable Long id, HttpServletResponse response) {
    UploadedFile uploadFile = fileUploaderService.getUploadFile(id);
    try {
      InputStream is = uploadFile.getFile().getBinaryStream();
      IOUtils.copy(is, response.getOutputStream());
      response.flushBuffer();
    } catch (IOException ex) {
      LOGGER.info("Error writing file to output stream. Filename was '{}'", uploadFile.getName(),
          ex);
      throw new RuntimeException("IOError writing file to output stream");
    } catch (SQLException ex) {
      LOGGER.info("Error converting sql blob to binary stream", uploadFile.getName(),
          ex);
      throw new RuntimeException("Error converting sql blob to binary stream");
    }
  }
}