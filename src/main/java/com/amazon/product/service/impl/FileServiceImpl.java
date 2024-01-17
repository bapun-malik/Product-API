package com.amazon.product.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazon.product.service.FileService;

@Service
public class FileServiceImpl implements FileService{

    @Override
    public String uploadFile(String path, MultipartFile file) throws IOException {
        // get the file name from the file
        String name=file.getOriginalFilename();

        // to get the file path
        String filePath=path+File.pathSeparator+name;
        
        //create file object
        File f=new File(path);
        if(!f.exists()){
            f.mkdir();
        }
        
        //copy the file to the path
        Files.copy(file.getInputStream(),Paths.get(filePath),StandardCopyOption.REPLACE_EXISTING);

        return name;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String filePath=path+File.pathSeparator+fileName;
        return new FileInputStream(filePath);
    }

    @Override
    public boolean deleteResource(String path,String fileName) throws IOException {
        return Files.deleteIfExists(Paths.get(path+File.pathSeparator+fileName));
    }
    
    
}
