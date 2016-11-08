package com.nicolas.fileuploader.service;

import com.nicolas.fileuploader.files.MultipartFileWrapper;
import com.nicolas.fileuploader.domain.UploadedFile;
import com.nicolas.fileuploader.repository.FileRepository;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Service;

@Service
public class FileUploaderService {

  private final FileRepository fileRepository;

  public FileUploaderService(FileRepository fileRepository) {
    this.fileRepository = fileRepository;
  }

  public List<UploadedFile> listFiles() {
    return fileRepository.findAll();
  }

  public UploadedFile getUploadFile(Long id) {
    return fileRepository.findOne(id);
  }

  public void uploadMultipartFile(MultipartFile multipartFile, String description) {
    MultipartFileWrapper fileWrapper = new MultipartFileWrapper(multipartFile);
    UploadedFile fileUpload = new UploadedFile(
        fileWrapper.name(), description, fileWrapper.creationDate(), fileWrapper.blob());
    fileRepository.save(fileUpload);
  }
}
