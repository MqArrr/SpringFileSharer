package by.makar.nekitweb8.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.File;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class FileRequest {
    private String filePath;
    private long size;
    private boolean isFolder = false;
    private boolean isDrive = false;
    private String name;

    public FileRequest(File file){
        filePath = file.getPath();
        size = file.length();
        isFolder = file.isDirectory();
        name = file.getName();
    }
    public FileRequest(File file, boolean isDrive){
        filePath = file.getPath();
        size = file.length();
        isFolder = file.isDirectory();
        name = file.getName();
        this.isDrive = isDrive;
    }

}
