package by.makar.nekitweb8.services;

import by.makar.nekitweb8.util.FileRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@Component
public class FileManager {

    public List<FileRequest> getRoot(){
        return Arrays.stream(File.listRoots()).map(n -> new FileRequest(n, true)).toList();
    }

    public List<FileRequest> getFiles(String path){
        File file = new File(path);
        return Arrays.stream(file.listFiles()).map(n -> new FileRequest(n, false)).toList();
    }

    public void uploadFile(MultipartFile file, String loc, String uploadedFileName) throws IOException {
        File nFile = new File(loc + uploadedFileName);
        Files.copy(file.getInputStream(), nFile.toPath());
    }
}
