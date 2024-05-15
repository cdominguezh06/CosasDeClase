

import java.util.ArrayList;
import java.util.List;

public class DataTreater {

	public static List<Object> treat(List<Object> data){
		List<Object> returnList = new ArrayList<>();
		for(int i = 0; i<data.size(); i++) {
			try {
				if(data.get(i).toString().matches("[^\\d]+")) {
					returnList.add(data.get(i).toString());
				}
				if(data.get(i).toString().matches("[0-9]+") && data.get(i).toString().contains(".")) {
					returnList.add(Double.valueOf(data.get(i).toString()));
				}
				if(data.get(i).toString().matches("[0-9]+") && data.get(i).toString().contains(".")==false) {
					try {
						returnList.add(Integer.valueOf(data.get(i).toString()));						
					} catch (Exception e) {
						returnList.add(Long.valueOf(data.get(i).toString()));
					}
				}
			}catch(NullPointerException e) {
				returnList.add("null");
			}
		}
		
		return returnList;
		
	}
}
