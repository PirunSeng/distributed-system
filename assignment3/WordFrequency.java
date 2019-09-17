import java.io.Serializable;
import java.util.Map;
import java.util.HashMap;

public class WordFrequency implements Serializable {
  private static final long serialVersionUID = 1190476516911661470L;
  private Map<String, Integer> dictionary;

  public WordFrequency() {
    dictionary = new HashMap<String, Integer>();
  }

  public void addWord(String word) {
    if(this.dictionary.containsKey(word)) {
      this.dictionary.put(word, this.dictionary.get(word) + 1);
    } else {
      this.dictionary.put(word, 1);
    }
  }

  public void mergeWord(WordFrequency other) {
    String key = "";
    Integer value;
    for(Map.Entry<String, Integer> word : other.getDictionary().entrySet()) {
      key = word.getKey();
      value = this.dictionary.get(key);
      value = value == null ? 0 : value;
      this.dictionary.put(key, value + word.getValue());
    }
  }

  public Map<String, Integer> getDictionary() {
    return this.dictionary;
  }
}
