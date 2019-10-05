
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author 
 *
 */
public class SpellingChecker {
	private StringHashTable dictionary;

	public SpellingChecker(StringHashTable wordTable) {
		this.dictionary = wordTable;
	}


	/**
	 * 
	 * @param text
	 * @return
	 */
	public List<SpellingSuggestion> spellingCheck(String text) {
		WordSpellingChecker checker = new WordSpellingChecker(dictionary);
		List<SpellingSuggestion> misspelledWords = new ArrayList<SpellingSuggestion>();
		String tempWord = "";
		if (text == null || text.length()==0)
			return misspelledWords;
		for ( int i = 0; i < text.length(); i++){
			char c = text.charAt(i);
			if (c != ' '){
				tempWord = tempWord + c;
			}
			if (c == ' ' || i == text.length()-1){
				SpellingSuggestion wordSuggestion = new SpellingSuggestion(tempWord, checker.getWordSuggestions(tempWord));
				if(!checker.isWordExistingInDictionary(tempWord) && !misspelledWords.contains(wordSuggestion))
					misspelledWords.add(wordSuggestion);
				tempWord = "";
			}
		}
		return misspelledWords;
	}
}
