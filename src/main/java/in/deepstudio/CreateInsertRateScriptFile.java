package in.deepstudio;

import in.deepstudio.Utils.Constants;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
/*
 * Change clientTypeValue
 * Change sqlFileName as per for Regular Customer,Photographer rate sheets
 * here frame_qualified_id_counter is generated framedetail id after framedetail insert
 * framedetail insert fired only once so that all combination of frame creeate only once , should not fired every time when different clienttype rates inserted  
 */
public class CreateInsertRateScriptFile {

	private static int noOfCol = 0; 
	private static int noOfRow = 0; 
	private static String tableName="";
	private static String frameNumber="";
	private static String frameType="";
	private static int clientTypeValue=1;/*Photographer : 0 , Regular Customer : 1*/
	/*DM_RC,DM_PH,SM_RC,SM_PH,SS-DF_RC,SS-DF_PH,LM_RC,LM_PH*/
	//private static String[] sqlFileName = {"DM_PH","SM_PH","SS-DF_PH","LM_PH"};
	private static String[] sqlFileName = {"DM_RC","SM_RC","SS-DF_RC","LM_RC"};
	private static List<String> frameSizeValueList = new LinkedList<>();
	private static List<String> frameThicknessValueList = new LinkedList<>();
	private static List<String> queryList = new LinkedList<>();
	public static void main(String[] args){
		try{
		
			Workbook workbook = Workbook.getWorkbook(new File("C:\\Users\\Raj-Macbook\\Desktop\\Deep Studio\\Deep Rate Sheet.xls"));
			int frame_qualified_id_counter=0;
			for(int k=0;k< sqlFileName.length ; k++) {
				
				Sheet sheet = workbook.getSheet(sqlFileName[k]);
				
				noOfRow = sheet.getRows();
				noOfCol = sheet.getColumns();
				tableName = sheet.getCell(1, 0).getContents();
				frameType = sheet.getCell(1, 2).getContents();
				frameNumber = sheet.getCell(1, 3).getContents();
				
				for(int row = 6;row < noOfRow;row++){
					frameSizeValueList.add(sheet.getCell(1,row).getContents());
				}
				for(int col = 2;col < noOfCol;col++){
					frameThicknessValueList.add(sheet.getCell(col,5).getContents());
				}
				  //System.out.println(frameSizeValueList.toString());
				  //System.out.println(frameThicknessValueList.toString());
				  int sizeCounter=0;
				  
				  for(int row = 6;row < noOfRow;row++){
					  int thicknessCounter=0;
					  for(int col = 2;col < noOfCol;col++){
						 if(clientTypeValue == 0){
						  queryList.add("INSERT INTO frame_details (created_date, frame_description,frame_info_id, frame_number_id, frame_size_id, frame_thickness_id, ip_address, updated_date) VALUES ("
								  	+"CURDATE()"+","
						  			+"''"+","
						  			+frameType+","
						  			+frameNumber+","
						  			+frameSizeValueList.get(sizeCounter)+","
						  			+frameThicknessValueList.get(thicknessCounter)+","
						  			+"''"+","
						  			+"CURDATE()"
						  			+");");
						 }
						  queryList.add("INSERT INTO frame_rate (created_date, client_type_id, frame_qualified_id,frame_rate_photo, ip_address, price, updated_date) VALUES  ("
								  	+"CURDATE(),"
						  			+clientTypeValue+","
						  			+frame_qualified_id_counter+","
						  			+"''"+","
						  			+"''"+","
						  			+"'"+sheet.getCell(col,row).getContents()+"',"
						  			+"CURDATE()"
						  			+");");
						  thicknessCounter++;
						  frame_qualified_id_counter++;
					  }
					  sizeCounter++; 
				  }
				}
				  //frameSizeList.get(sizeCounter)+"|"+sheet.getCell(col,row).getContents()+"|"+frameThicknessList.get(thicknessCounter)+"|\n");
				  //System.out.println(queryList.toString());
				//Write Data to File 
				try
				{
				  File file = new File(Constants.Context_Resource_Path + File.separator +"importRate.sql");
				  StringBuffer sb = new StringBuffer();
				  if(!file.exists()){
					  file.createNewFile();
				  }
				  
				  BufferedWriter output = new BufferedWriter(new FileWriter(file,true));
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
			
				}catch (BiffException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}
	
}
