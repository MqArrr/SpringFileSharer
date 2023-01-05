package by.makar.nekitweb8;

import lombok.Data;

import java.io.File;

@Data
public class FileRequest {
    private String filePath;
    private long size;
}
