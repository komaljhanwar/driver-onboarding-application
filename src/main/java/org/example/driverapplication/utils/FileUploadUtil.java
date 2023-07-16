package org.example.driverapplication.utils;

import lombok.extern.log4j.Log4j2;
import org.example.driverapplication.constants.ResponseCodeMapping;
import org.example.driverapplication.exception.CustomIOException;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Configuration
@Log4j2
public class FileUploadUtil {

    private static String baseUrl = "driver/document/";
    public static String saveFile(String fileName, MultipartFile multipartFile)
            throws CustomIOException {
        log.info("file path " + baseUrl);
        Path uploadPath = Paths.get(baseUrl);
        try {
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        } catch (IOException ex) {
            log.error(ResponseCodeMapping.FILE_CREATE_FAILURE.getMessage(), ex);
            throw new CustomIOException(ResponseCodeMapping.FILE_CREATE_FAILURE.getCode(), ResponseCodeMapping.FILE_CREATE_FAILURE.getMessage());
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
            return filePath.toString();
        } catch (IOException ioe) {
            log.error(ResponseCodeMapping.STORAGE_FAILURE.getMessage(), ioe);
            throw new CustomIOException(ResponseCodeMapping.STORAGE_FAILURE.getCode(), ResponseCodeMapping.STORAGE_FAILURE.getMessage());
        }
    }
}
