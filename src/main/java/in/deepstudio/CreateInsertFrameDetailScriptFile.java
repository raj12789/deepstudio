package in.deepstudio;

import in.deepstudio.Utils.Constants;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CreateInsertFrameDetailScriptFile {

	private static List<String> frameTypeList = new ArrayList<>();
	private static List<String> frameNumberList = new ArrayList<>();
	private static List<String> frameThicknessList = new ArrayList<>();
	private static List<String[]> frameSizeList = new ArrayList<>();
	private static List<String> clientTypeList = new ArrayList<>();
	private static List<String> queryList = new LinkedList<>();
	
	public static void main(String [] args){
		
		/*
		 * 
		 * 	insert into frame_info (frame_info_id, created_date, frame_desc, frame_type, frame_type_photo, ip_address, updated_date) values (null, ?, ?, ?, ?, ?, ?)
			insert into frame_thickness (frame_thickness_id, created_date, frame_desc, frame_measurements_type, frame_thickness_size, ip_address, updated_date) values (null, ?, ?, ?, ?, ?, ?)
			insert into frame_number (frame_number_id, created_date, frame_desc, frame_number, frame_number_photos, ip_address, updated_date) values (null, ?, ?, ?, ?, ?, ?)
			insert into frame_size (frame_size_id, created_date, frame_desc, frame_measurements_type, frame_size_chart, frame_size_height, frame_size_width, ip_address, updated_date) values (null, ?, ?, ?, ?, ?, ?, ?, ?)
			insert into frame_client_type (client_type_id, client_desc, client_type_name, created_date, ip_address, updated_date) values (null, ?, ?, ?, ?, ?)
		 * 
		 */
		
		try{
			
			frameTypeList = Arrays.asList(new String[]{"Single Mount Framing","Double Mounted Or Sandwich Framing","Framing On Lamination/lamination Framing","Size to Size Framing/Direct Framing","Other"});
			for(String frameType : frameTypeList){
				queryList.add("INSERT INTO frame_info (created_date, frame_desc, frame_type, frame_type_photo, ip_address, updated_date) VALUES ("
					  	+"CURDATE()"+","
			  			+"''"+","
			  			+"'"+frameType+"',"
			  			+"''"+","
			  			+"''"+","
			  			+"CURDATE()"
			  			+");");
			}
			
			frameNumberList= Arrays.asList(new String[]{"1","Other"});
			for(String frameNumber : frameNumberList){
				queryList.add("INSERT INTO frame_number (created_date, frame_desc, frame_number, frame_number_photos, ip_address, updated_date) VALUES ("
					  	+"CURDATE()"+","
			  			+"''"+","
			  			+"'"+frameNumber+"',"
			  			+"''"+","
			  			+"''"+","
			  			+"CURDATE()"
			  			+");");
				
			}
			frameThicknessList= Arrays.asList(new String[]{"0.5","0.75","1","1.25","1.50","1.75","2.50","Other"});
			for(String frameThickness : frameThicknessList){
				queryList.add("INSERT INTO frame_thickness (created_date, frame_desc, frame_measurements_type, frame_thickness_size, ip_address, updated_date) VALUES ("
					  	+"CURDATE()"+","
			  			+"''"+","
			  			+"'inches'"+"," //"inches","pixels","cm","mm","points","picas","columns"
			  			+"'"+frameThickness+"',"
			  			+"''"+","
			  			+"CURDATE()"
			  			+");");
			}
			frameSizeList.add(new String[]{"6", "8"});
			frameSizeList.add(new String[]{"8", "10"});
			frameSizeList.add(new String[]{"8", "12"});
			frameSizeList.add(new String[]{"10", "12"});
			frameSizeList.add(new String[]{"10", "15"});
			frameSizeList.add(new String[]{"12", "15"});
			frameSizeList.add(new String[]{"12", "18"});
			frameSizeList.add(new String[]{"16", "20"});
			frameSizeList.add(new String[]{"12", "24"});
			frameSizeList.add(new String[]{"16", "24"});
			frameSizeList.add(new String[]{"12", "30"});
			frameSizeList.add(new String[]{"12", "36"});
			frameSizeList.add(new String[]{"20", "24"});
			frameSizeList.add(new String[]{"20", "30"});
			frameSizeList.add(new String[]{"24", "36"});
			frameSizeList.add(new String[]{"0", "0"});
			for(String[] frameSize : frameSizeList){
				queryList.add("INSERT INTO frame_size (created_date, frame_desc, frame_measurements_type, frame_size_chart, frame_size_height, frame_size_width, ip_address, updated_date) VALUES ("
					  	+"CURDATE()"+","
			  			+"''"+","
			  			+"'inches'"+"," //"inches","pixels","cm","mm","points","picas","columns"
			  			+"''"+","
			  			+"'"+frameSize[1]+"',"
			  			+"'"+frameSize[0]+"',"
			  			+"''"+","
			  			+"CURDATE()"
			  			+");");
			}
			
			
			clientTypeList = Arrays.asList(new String[]{"Photographer","Regular Customer","Other"});
			for(String clientType : clientTypeList){
				queryList.add("INSERT INTO frame_client_type (client_desc, client_type_name, created_date, ip_address, updated_date) VALUES ("
						+"''"+","
						+"'"+clientType+"',"
						+"CURDATE()"+","
						+"''"+","
						+"CURDATE()"
						+");");
			}
			/*Write Data to File */
			try
			{
			  File file = new File(Constants.Context_Resource_Path + File.separator +"framedetails.sql");
			  StringBuffer sb = new StringBuffer();
			  if(!file.exists()){
				  file.createNewFile();
			  }
			  
			  BufferedWriter output = new BufferedWriter(new FileWriter(file));
	          //System.out.println(sb.toString());
			  for(int i=0;i < queryList.size() ; i++){
				  sb.append(queryList.get(i)+"\n");
			  }
			  output.write(sb.toString());
	          output.close();
			}
			catch(IOException io)
			{
				io.printStackTrace();
			}
			finally 
			{
				System.out.println("Data Written");
			}
				
		}catch(Exception e){
			e.printStackTrace();
		}
			
	}
}
