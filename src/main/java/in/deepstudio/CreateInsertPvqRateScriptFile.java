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

public class CreateInsertPvqRateScriptFile {

	private static List<String> pvqConcernTypeList = new ArrayList<>();
	private static List<String> queryList = new LinkedList<>();
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try{
			pvqConcernTypeList = Arrays.asList(new String[]{"Photography","Videography","Photo Albums","LED","Plazma","Crain","Drone","Other"});
			for(String type : pvqConcernTypeList){
				queryList.add("INSERT INTO pvq_concern_type (created_date, ip_address, pvq_concern_type_desc, pvq_concern_type_name, updated_date) VALUES ("
					  	+"CURDATE()"+","
			  			+"''"+","
			  			+"''"+","
			  			+"'"+type+"',"
			  			+"CURDATE()"
			  			+");");
			}
			/*Write Data to File */
			try
			{
			  File file = new File(Constants.Context_Resource_Path + File.separator +"PvqRate.sql");
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
