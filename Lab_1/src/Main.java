import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
//
public class Main {
    public static void main(String[] args) {
        String sentence = "";
        int wordLength = 0;
        String mySubString = "";

        InputStreamReader is = new InputStreamReader(System.in);
        BufferedReader bis = new BufferedReader(is);

        try {
            System.out.println("Introdu propoziția: ");
            sentence = bis.readLine();
            System.out.println("Introdu subșirul care trebuie înlocuit:");
            mySubString = bis.readLine();
            System.out.println("Introdu lungimea cuvântului înlocuit:");
            wordLength = Integer.parseInt(bis.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }


        Text myText = new Text(mySubString, sentence, wordLength);
        myText.changeSentence();
        System.out.println("Propoziția nouă: " + myText.getSentence());
    }
}
class Text {
    private String mySentence;
    private int charNumber;
    private String wordToChange;
    private String newSentence = "1.";

    public Text(String wordToChange, String mySentence, int charNumber) {
        this.mySentence = mySentence;
        this.wordToChange = wordToChange;
        this.charNumber = charNumber;
    }
    public String getSentence() {
        return newSentence.trim();
    }
    public void changeSentence() {
        int firstPos = 0;
        int i;
        for (i = 0; i < mySentence.length(); i++) {
            if (mySentence.charAt(i) == ' ') {
                String currentWord = mySentence.substring(firstPos, i);
                if (currentWord.length() == charNumber) {
                    newSentence = newSentence.concat(wordToChange + " ");
                } else {
                    newSentence = newSentence.concat(currentWord + " ");
                }
                firstPos = i + 1;
            } else if (i == mySentence.length() - 1) {
                String currentWord = mySentence.substring(firstPos);
                if (currentWord.length() == charNumber) {
                    newSentence = newSentence.concat(wordToChange);
                } else {
                    newSentence = newSentence.concat(currentWord);
                }
            }
        }
    }
}
