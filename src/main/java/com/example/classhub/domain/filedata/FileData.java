package com.example.classhub.domain.filedata;

import com.example.classhub.domain.BaseEntity;
import com.example.classhub.domain.filedata.dto.FileDataDto;
import com.example.classhub.domain.post.ClassHub_Post;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileData extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileDataName;
    private String fileDataType;

    @ManyToOne
    @JoinColumn(name = "postId")
    private ClassHub_Post post;

    public static FileData from(FileDataDto fileDataDto, ClassHub_Post post) {
        return FileData.builder()
                .fileDataName(fileDataDto.getFileDataName())
                .fileDataType(fileDataDto.getFileDataType())
                .post(post)
                .build();
    }

    public void update(FileDataDto fileDataDto) {
        this.fileDataName = fileDataDto.getFileDataName();
        this.fileDataType = fileDataDto.getFileDataType();
    }
}
