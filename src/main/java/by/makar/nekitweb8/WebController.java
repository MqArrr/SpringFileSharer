package by.makar.nekitweb8;

import jakarta.servlet.http.HttpSession;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class WebController {
    @GetMapping("/hello")
    public String hello(@ModelAttribute("fileRequest") FileRequest fileRequest){
        return "hello";
    }

    @GetMapping("/errorFile")
    public String error(){
        return "errorFile";
    }

    @PostMapping(value = "/file")
    public String getFile(@ModelAttribute("fileRequest") FileRequest fileRequest, HttpSession session, Model model) {
        String path = fileRequest.getFilePath();
        File file = new File(path);
        boolean exist = file.exists();
        if(!exist)
            return "redirect:/errorFile";
        else {
            session.setAttribute("path", path);
            model.addAttribute("path", path);
            model.addAttribute("size", "Размер: " + String.format("%.2f",((double) file.length() / 1048576)) + " mb");
            return "download";
        }
    }

    @GetMapping(value = "/download/file")
    @ResponseBody
    public ResponseEntity<InputStreamResource> getFile(HttpSession session) throws IOException {

        String path = (String) session.getAttribute("path");
        File file = new File(path);

        final HttpHeaders httpHeaders = new HttpHeaders();
        final InputStream inputStream = new FileInputStream(file);
        final InputStreamResource resource = new InputStreamResource(inputStream);
        httpHeaders.set(HttpHeaders.LAST_MODIFIED, String.valueOf(file.lastModified()));
        httpHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"");
        httpHeaders.set(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.length()));
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

}
