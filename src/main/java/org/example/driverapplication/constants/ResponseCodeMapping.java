package org.example.driverapplication.constants;

public enum ResponseCodeMapping {


    NOT_FOUND("ERR_DATA_001","Status not found"),
    DRIVER_NOT_FOUND("ERR_DATA_002","Driver not found"),

    DRIVER_ALREADY_EXISTS("ERR_DATA_003", "Driver already exits"),
    STORAGE_FAILURE("ERR_DATA_003","Failed to upload document"),

    TOKEN_NOT_FOUND("ERR_AUTH_001","No token found in request headers"),
    DB_FAILURE("ERR_DB_001","Failed to save drive profile"),
    FILE_CREATE_FAILURE("ERR_FILE_001","Failed to create directory"),

    //ON_BOARDING_INCOMPLETE("ERR_PROCESS_001","Please complete on-boarding process first"),
    ON_BOARDING_INCOMPLETE("ERR_PROCESS_002", "Driver is not onboarded to take rides"),
    FILE_EMPTY("ERR_DOC_001", "File is empty"),
    FILE_INVALID_FORMAT("ERR_DOC_002", "Invalid format"),
    FILE_LARGER_SIZE("ERR_DOC_003", "File size id greater than 5 MB");



    private String code;
  private String message;

   ResponseCodeMapping(String errorCode, String errorMessage){
      this.code =errorCode;
      this.message = errorMessage;
  }

  public String getCode(){
       return code;
  }

  public  String getMessage(){
       return message;
  }
}
