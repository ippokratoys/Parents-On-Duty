package webapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static webapp.services.FileUploadService.FileType.CERFICATE;
import static webapp.services.FileUploadService.FileType.EVENT;
import static webapp.services.FileUploadService.FileType.LOCATION;

/**
 * Created by thanasis on 1/7/2017.
 */
@Service
public class FileUploadService{

    @Autowired
    Environment env;
    public enum FileType{
        EVENT,
        CERFICATE,
        LOCATION;
    }

    Path eventPhoto;

    Path locationPhoto;

    Path certificateLocation;

    @Value("${file_save.folder.prefix}")
    String MAIN_PREFIX;

    @Value("${file_save.folder.location.prefix}")
    String LOCATION_PREFIX;

    @Value("${file_save.folder.event.pictures.prefix}")
    String PHOTO_PREFIX;

    @Value("${file_save.folder.event.certificates.prefix}")
    String CERTIFICATE_PREFIX;

    private Path rootLocation;


    public void store(MultipartFile file,String fileName,FileType fileType) {
        try {
            if (file.isEmpty()) {
//                throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
            }

            if(fileType==EVENT){
                Files.copy(file.getInputStream(), this.eventPhoto.resolve(fileName));
            }else if(fileType==LOCATION){
                Files.copy(file.getInputStream(), this.locationPhoto.resolve(fileName));
            }else if(fileType==CERFICATE){
                Files.copy(file.getInputStream(), this.certificateLocation.resolve(fileName));
            }
        } catch (IOException e) {
//            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    public Path load(String filename,FileType fileType) {
        if(fileType==EVENT){
            return eventPhoto.resolve(filename);
        }else if(fileType==LOCATION){
            return locationPhoto.resolve(filename);
        }else if(fileType==CERFICATE){
            return certificateLocation.resolve(filename);
        }
        return null;
    }

    public Resource loadAsResource(String filename,FileType fileType) {
        try {
            Path file = load(filename,fileType);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
//                throw new StorageFileNotFoundException("Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
//            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
            e.printStackTrace();
        }
        return null;
    }

    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @PostConstruct()
    public void init() {
        System.out.println("FileUploadService.FileUploadService");
        try {
            rootLocation=Paths.get(MAIN_PREFIX);
            eventPhoto =Paths.get(MAIN_PREFIX+PHOTO_PREFIX);
            certificateLocation=Paths.get(MAIN_PREFIX+CERTIFICATE_PREFIX);
            locationPhoto=Paths.get(MAIN_PREFIX+LOCATION_PREFIX);


            if(!Files.exists(rootLocation)){
                Files.createDirectory(rootLocation);
            }
            if(!Files.exists(eventPhoto)){
                Files.createDirectory(eventPhoto);
            }
            if(!Files.exists(certificateLocation)){
                Files.createDirectory(certificateLocation);
            }
            if(!Files.exists(locationPhoto)){
                Files.createDirectory(locationPhoto);
            }
        } catch (IOException e) {
//            throw new StorageException("Could not initialize storage", e);
        }
    }
}
