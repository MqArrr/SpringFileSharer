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

    private String extension;

    private String uploadedFileName;

    public FileRequest(File file){
        filePath = file.getPath();
        size = file.length();
        isFolder = file.isDirectory();
        name = file.getName();
        extension = extension(filePath);
    }
    public FileRequest(File file, boolean isDrive){
        filePath = file.getPath();
        size = file.length();
        isFolder = file.isDirectory();
        name = file.getName();
        this.isDrive = isDrive;
        extension = extension(filePath);
    }

    private String extension(String filePath){
        String[] interDots = filePath.split("\\.");
        return interDots[interDots.length - 1];
    }

    public void extension(){
        String[] interDots = filePath.split("\\.");
        extension = interDots[interDots.length - 1];
    }
    public boolean isVideo(){
        if(extension == null)
            extension = extension(filePath);
        return extension.equals("mp4");
    }

    public boolean isPic(){
        if(extension == null)
            extension = extension(filePath);
        if(extension.equals("jpeg") || extension.equals("jpg") || extension.equals("gif") || extension.equals("tiff") || extension.equals("png"))
            return true;
        return false;
    }

}
