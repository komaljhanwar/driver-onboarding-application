package org.example.driverapplication.utils;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.EnumUtils;
import org.example.driverapplication.constants.FileExtensionType;
import org.example.driverapplication.constants.ResponseCodeMapping;
import org.example.driverapplication.exception.IllegalArgumentException;
import org.springframework.web.multipart.MultipartFile;

public class DocumentValidator {

    public static final Long MAX_FILE_SIZE = 10485760L;

    public static void validate(MultipartFile file) throws IllegalArgumentException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException(ResponseCodeMapping.FILE_EMPTY.getCode(), ResponseCodeMapping.FILE_EMPTY.getMessage());
        }
        if (file.getSize() > MAX_FILE_SIZE)  {
            throw new IllegalArgumentException(ResponseCodeMapping.FILE_LARGER_SIZE.getCode(), ResponseCodeMapping.FILE_LARGER_SIZE.getMessage());
        }
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        boolean isValidDocType = EnumUtils.isValidEnum(FileExtensionType.class, extension.toUpperCase());

        if(!isValidDocType) {
            throw new IllegalArgumentException(ResponseCodeMapping.FILE_INVALID_FORMAT.getCode(), ResponseCodeMapping.FILE_INVALID_FORMAT.getMessage());
        }
    }

}
