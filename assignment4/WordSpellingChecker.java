import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author 
 *
 */
public class WordSpellingChecker {
	StringHashTable dictionary;
	public WordSpellingChecker(StringHashTable wordTable) {
		this.dictionary = wordTable;
	}

	/**
	 * 
	 * @param word 
	 * @return 
	 */
	public boolean isWordExistingInDictionary(String word) {
		if(word == null)
			return false;
		return dictionary.search(word);
	}

	public List<String> addSpaceCheck(String word) {
		List<String> words = new ArrayList<String>();
		if(word == null){
			return words;
		}
		for (int i = 1; i < word.length(); i++){
			String temp1=word.substring(0,i);
			String temp2=word.substring(i);
			if(dictionary.search(temp1)==true && dictionary.search(temp2)== true && !words.contains(temp2) && !words.contains(temp1)){
				String temp3 = temp1 + ' ' + temp2;
				words.add(temp3);
			}
		}
		return words;
	}

	public List<String> replaceCharacterCheck(String word) {
		List<String> words = new ArrayList<String>();
		if(word == null){
			return words;
		}
		for (int i = 1; i <= word.length(); i++){
			for(char a = 'a'; a<= 'z'; a++){
				String temp1 = word.substring(0,i);
				temp1=temp1.substring(0,temp1.length()-1);
				temp1= temp1+a;
				String temp2 = word.substring(i);
				temp2 = temp1 + temp2;
				if(dictionary.search(temp2)==true && !words.contains(temp2)){
					words.add(temp2);
				}
			}
			words.remove(word);
		}
		return words;
	}

	public List<String> deleteCharacterCheck(String word) {
		List<String> words = new ArrayList<String>();
		if(word == null){
			return words;
		}
		for (int i = 1; i < word.length(); i++){
			String temp1 = word.substring(0,i);
			temp1=temp1.substring(0,temp1.length()-1);
			String temp2 = word.substring(i);
			temp2 = temp1 + temp2;
			if(dictionary.search(temp2)==true && !words.contains(temp2) ){
				words.add(temp2);
			}
			words.remove(word);
		}
		return words;
	}

	public List<String> addCharacterCheck(String word) {
		List<String> words = new ArrayList<String>();
		if(word == null || word.length()==0){
			return words;
		}
		for (int i = 0; i <= word.length(); i++){
			for(char a = 'a'; a<= 'z'; a++){
				String temp1 = word.substring(0,i);
				temp1= temp1+a;
				String temp2 = word.substring(i);
				temp2 = temp1 + temp2;
				if(dictionary.search(temp2)==true && !words.contains(temp2)){	
					words.add(temp2);
				}
			}
			words.remove(word);
		}
		return words;
	}

	public List<String> switchAdjacentCharacterCheck(String word) {
		List<String> words = new ArrayList<String>();
		if(word == null || word.length() <=1){
			return words;
		}
		for (int i = 1; i < word.length(); i++){
			String temp1 = word.substring(0,i);
			String temp2 = word.substring(i);
			char a = temp2.charAt(0);
			char b = temp1.charAt(temp1.length()-1);
			temp1= temp1.substring(0,temp1.length()-1);
			temp2= temp2.substring(1);
			temp2 = temp1 + a + b + temp2;
			if(dictionary.search(temp2)==true && !words.contains(temp2)){	
				words.add(temp2);
			}
		}
		words.remove(word);
		return words;
	}

	public List<String> getWordSuggestions(String word) {
		List<String> words = new ArrayList<String>();
		if(word == null || word.length()==0){
			return words;
		}
		List<String> tmp1=addSpaceCheck(word);
		List<String> tmp2=replaceCharacterCheck(word);
		List<String> tmp3=deleteCharacterCheck(word);
		List<String> tmp4=addCharacterCheck(word);
		List<String> tmp5=switchAdjacentCharacterCheck(word);
		words.addAll(tmp1);
		words.addAll(tmp2);
		words.addAll(tmp3);
		words.addAll(tmp4);
		words.addAll(tmp5);
		return words;
	}
}
