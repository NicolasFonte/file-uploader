package com.nicolas.fileuploader.service;

import com.nicolas.fileuploader.domain.UploadedFile;
import com.nicolas.fileuploader.repository.FileRepository;
import java.io.IOException;
import java.sql.SQLException;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class FileUploaderServiceTest {

  private FileUploaderService service;

  @Mock
  private FileRepository fileRepository;

  @Before
  public void setUp() {
    service = new FileUploaderService(fileRepository);
  }

  @Test
  public void testFileServiceCanFindById() {
    UploadedFile uploadedFile = new UploadedFile("name", "desc", "date", null);
    uploadedFile.setId(10L);
    Mockito.when(fileRepository.findOne(10L)).thenReturn(uploadedFile);

    UploadedFile reloadedFile = service.getUploadFile(10L);

    assertNotNull(reloadedFile);
    assertEquals("name", reloadedFile.getName());
    assertEquals("desc", reloadedFile.getDescription());
    assertEquals("date", reloadedFile.getCreationDate());
  }

  @Test
  public void testUploadFileAddDataToDBConvertingResourceValuesToDomainAttributes()
      throws IOException, SQLException {

    ClassLoader classLoader = getClass().getClassLoader();

    MultipartFile multipartFile =
        new MockMultipartFile("file", "my-file", "multipart/form-data",
            classLoader.getResourceAsStream("test-files/text.txt"));
    ArgumentCaptor<UploadedFile> argumentCaptor = ArgumentCaptor.forClass(UploadedFile.class);
    Mockito.when(fileRepository.save(argumentCaptor.capture())).thenReturn(new UploadedFile());

    service.uploadMultipartFile(multipartFile, "file-desc");

    UploadedFile expected = argumentCaptor.getValue();
    assertEquals("file-desc", expected.getDescription());
    assertEquals("my-file", expected.getName());
    String fileTextContent = IOUtils.toString(expected.getFile().getBinaryStream(), "UTF-8");
    assertEquals("just test file", fileTextContent);
  }
}