package by.makar.nekitweb8.controllers;

import by.makar.nekitweb8.services.FileManager;
import by.makar.nekitweb8.util.FileException;
import by.makar.nekitweb8.util.FileRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Controller
public class WebController {



    private final FileManager fileManager;

    @Autowired
    public WebController(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    @GetMapping("/home")
    public String home(@ModelAttribute("fileRequest") FileRequest fileRequest, Model model){
        model.addAttribute("files", fileManager.getRoot());
        model.addAttribute("isRoot", true);
        return "hello";
    }
    @PostMapping("/files")
    public String files(@ModelAttribute("fileRequest") FileRequest fileRequest, Model model, HttpSession session){
        System.out.println(fileRequest);
        model.addAttribute("files", fileManager.getFiles(fileRequest.getFilePath()));
        model.addAttribute("isRoot", false);
        session.setAttribute("path", fileRequest.getFilePath());
        return "hello";
    }

//    @GetMapping("/errorFile")
//    public String error(){
//        return "errorFile";
//    }

    @PostMapping(value = "/file")
    public String getFile(@ModelAttribute("fileRequest") FileRequest fileRequest, HttpSession session, Model model) {
        String path = fileRequest.getFilePath();
        fileRequest.extension();
        File file = new File(path);
        if(file.isDirectory())
            return files(fileRequest, model, session);
        if(!file.exists())
            return "redirect:/error";
        else {
            session.setAttribute("path", path);
            model.addAttribute("path", path);
            model.addAttribute("size", "Размер: " + String.format("%.2f",((double) file.length() / 1048576)) + " mb");
            model.addAttribute("file", fileRequest);
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
    @ResponseBody
    @GetMapping("/health")
    public ResponseEntity<HttpStatus> health(){
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @PostMapping("/getUpload")
    public String getUploadPage(@ModelAttribute("fileRequest") FileRequest fileRequest){
        return "uploadForm";
    }

    @PostMapping("/upload")
    public String upload(@ModelAttribute FileRequest fileRequest,
                         @RequestParam("file") MultipartFile file, Model model, HttpSession session){
        try {
            fileManager.uploadFile(file, fileRequest.getFilePath(), file.getOriginalFilename());
        } catch (IOException e) {
            return "error";
        }
        model.addAttribute("files", fileManager.getFiles(fileRequest.getFilePath()));
        model.addAttribute("isRoot", false);
        session.setAttribute("path", fileRequest.getFilePath());
        return "hello";
    }

    @GetMapping(value = "/videos")
    @ResponseBody
    public final ResponseEntity<InputStreamResource> retrieveResource(HttpSession session) throws Exception {

        File initialFile = new File((String) session.getAttribute("path"));
        InputStream targetStream = new FileInputStream(initialFile);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("video/mp4"));
        headers.set("Accept-Ranges", "bytes");
        headers.set("Expires", "0");
        headers.set("Cache-Control", "no-cache, no-store");
        headers.set("Connection", "keep-alive");
        headers.set("Content-Transfer-Encoding", "binary");

        return new ResponseEntity<>(new InputStreamResource(targetStream), headers, HttpStatus.OK);
    }

//    @GetMapping(value = "/getImage", produces = MediaType.IMAGE_PNG_VALUE)
//    @ResponseBody
//    public byte[] getImageDynamicType(HttpSession session) throws IOException {
//        File initialFile = new File("D:\\Java\\NekitWeb8\\src\\main\\resources\\static\\video.png");
////        (String) session.getAttribute("path")
//        InputStream targetStream = new FileInputStream(initialFile);
//        return targetStream.readAllBytes();
//    }
//
    @GetMapping("/getImage")
    @ResponseBody
    public ResponseEntity<InputStreamResource> getImageDynamicType(HttpSession session) throws FileNotFoundException {
        MediaType contentType;

        File initialFile = new File((String) session.getAttribute("path"));
        String path = initialFile.getPath();
        if(path.endsWith(".gif"))
            contentType = MediaType.IMAGE_GIF;
        else if(path.endsWith(".jpg"))
            contentType = MediaType.IMAGE_JPEG;
        else if(path.endsWith(".png"))
            contentType = MediaType.IMAGE_PNG;
        else if(path.endsWith(".jpeg"))
            contentType = MediaType.IMAGE_GIF;
        else
            throw new RuntimeException("Not an image.");
        InputStream targetStream = new FileInputStream(initialFile);
        return ResponseEntity.ok()
                .contentType(contentType)
                .body(new InputStreamResource(new FileInputStream(initialFile)));
    }



}
