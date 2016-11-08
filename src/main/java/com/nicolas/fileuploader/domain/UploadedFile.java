package com.nicolas.fileuploader.domain;

import java.sql.Blob;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "uploaded_file")
public class UploadedFile {
  @Id
  @GeneratedValue
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "description", nullable = false)
  private String description;

  @Column(name = "creation_date", nullable = false)
  private String creationDate;

  @Column(name = "file", nullable = false)
  private Blob file;

  public UploadedFile() {
  }

  public UploadedFile(String name, String description, String creationDate, Blob file) {
    this.name = name;
    this.description = description;
    this.creationDate = creationDate;
    this.file = file;

  }
}
