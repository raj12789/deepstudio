package in.deepstudio;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CreateInsertExpenseManagerScriptFile {

	private static List<String> expenseTypeList = new ArrayList<>();
	private static List<String> queryList = new LinkedList<>();
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try{
			expenseTypeList = Arrays.asList(new String[]{"Other"});
			for(String type : expenseTypeList){
				queryList.add("INSERT INTO expense_type (created_date, expense_type_desc, expense_type_name, ip_address, updated_date) VALUES ("
					  	+"CURDATE()"+","
			  			+"''"+","
			  			+"'"+type+"',"
			  			+"''"+","
			  			+"CURDATE()"
			  			+");");
			}
			/*Write Data to File */
			try
			{
			  File file = new File("E:\\git repo\\deepstudio\\src\\main\\resources\\ExpenseManager.sql");
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
