import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * 
 * @author 
 *
 */
public class Parser {

	public static boolean parseWordsToTable(String filePath, StringHashTable table) {
		if(table!=null && filePath!=null){
			try {
				BufferedReader reader = new BufferedReader((new FileReader(filePath)));
				String text;
				while ((text = reader.readLine()) != null) {
					table.insert(text);
				}
				reader.close();
			}
			catch (IOException exception){
				exception.printStackTrace();
				return false;
			}
			return true;
		}
		return false;
	}
}
