package in.deepstudio.Utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

public class CommonUtils {

	
	public static String uploadFileHandler(String name, MultipartFile file,HttpServletRequest request,String dirPath) {
 
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
 
                // Creating the directory to store file
                String rootPath = Constants.Context_Resource_Path + File.separator +"static/img";
                File dir = new File(rootPath + File.separator + dirPath);
                if (!dir.exists())
                    dir.mkdirs();
 
                // Create the file on server	
                File serverFile = new File(dir.getAbsolutePath()+ File.separator + name);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();
 
                System.out.println("Server File Location="+ serverFile.getAbsolutePath());
 
                return "You successfully uploaded file=" + name;
            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + name + " because the file was empty.";
        }
    }
	
}
