package com.nicolas.fileuploader.repository;

import com.nicolas.fileuploader.domain.UploadedFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<UploadedFile, Long> {
}
