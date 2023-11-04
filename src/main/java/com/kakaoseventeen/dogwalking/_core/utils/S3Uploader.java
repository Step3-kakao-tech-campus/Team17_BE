package com.kakaoseventeen.dogwalking._core.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j // log찍기 위함
@RequiredArgsConstructor
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // 다른 메서드에서 호출되는 부분
    // dirName -> S3 Bucket 내부에 해당 이름의 디렉토리가 생성이 된다.
    @Transactional
    public String uploadFiles(Long userId, MultipartFile multipartFile, String dirName) throws IOException {
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("Error: MultipartFile -> File로 전환이 실패했습니다."));
        return upload(userId, uploadFile, dirName);
    }

    private String upload(Long userId, File uploadFile, String filePath) {
        String fileName = filePath + "/" + userId + uploadFile.getName(); // S3에 저장된 파일 이름
        String uploadImageUrl = putS3(uploadFile, fileName); // S3로 업로드
        log.info("uploadImageUrl = " + uploadImageUrl);
        removeNewFile(uploadFile);

        // 업로드된 이미지 url을 제공한다.
        return uploadImageUrl;
    }

    // S3로 업로드
    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(
                CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    // 로컬에 저장된 이미지 지우기
    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        } else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }

    // 로컬에 파일 업로드 하기
    /*
        업로드할때 파일이 로컬에 없으면 에러가 발생하기 때문에,
          convert로 입력받은 파일을 로컬에 저장하고 upload로 S3 버킷에 업로드 해야 한다.
     */
    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile =  new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());
        if(convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }


}