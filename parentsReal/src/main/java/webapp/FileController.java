package webapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import webapp.services.FileUploadService;

/**
 * Created by thanasis on 1/7/2017.
 */
@Controller
public class FileController {
    @Autowired
    FileUploadService fileUploadService;

    @RequestMapping("file/event/{fileName:.+}")
    ResponseEntity<Resource> getEventFile(@PathVariable String fileName){
        Resource file = fileUploadService.loadAsResource(fileName, FileUploadService.FileType.EVENT);
        if(file==null){
            return ResponseEntity
                    .notFound()
                    .build();
        }
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
                .body(file);
    }

    @RequestMapping("file/certificate/{fileName:.+}")
    @PreAuthorize("ADMIN")
    ResponseEntity<Resource> getCerficate(@PathVariable String fileName){
        Resource file = fileUploadService.loadAsResource(fileName, FileUploadService.FileType.CERFICATE);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
                .body(file);
    }

    @RequestMapping("file/location/{fileName:.+}")
    ResponseEntity<Resource> getLocationFile(@PathVariable String fileName){
        Resource file = fileUploadService.loadAsResource(fileName, FileUploadService.FileType.LOCATION);
        if(file==null){
            return ResponseEntity
                    .notFound()
                    .build();
        }
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
                .body(file);
    }

}
