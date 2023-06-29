import java.util.HashMap;
import java.util.Map;

public class EnigmaProject{
    public static void main(String[] args) {
        Enigma Enigma = new Enigma();
        String plugBoard = "af, bm, gh, jc, xe, op, nr, zl";
        String rotor3 = "luwjhikdycaxmnqbztrfgesvpo";
        String rotor2 = "qnghszafebjrluctxyimpdwkov";
        String rotor1 = "cmfqsbhioakrtenzldywugpjxv";
        Enigma.setPlugBoard(plugBoard);
        Enigma.setRotor(Enigma.rotor1 , rotor1);
        Enigma.setRotor(Enigma.rotor2 , rotor2);
        Enigma.setRotor(Enigma.rotor3 , rotor3);
        Enigma.setReflector();

    }
}
class Enigma{
    Map<Character , Character> plugBoard = new HashMap<>();
    Map<Character , Character> rotor1 = new HashMap<>();
    Map<Character , Character> rotor2 = new HashMap<>();
    Map<Character , Character> rotor3 = new HashMap<>();
    Map<Character , Character> reflector = new HashMap<>();
    void setPlugBoard(String plugBoard){
        String[] plugBoardArray = plugBoard.split(", ");
        for (int i = 0; i < plugBoardArray.length; i++) {
            this.plugBoard.put(plugBoardArray[i].charAt(0) , plugBoardArray[i].charAt(1));
            this.plugBoard.put(plugBoardArray[i].charAt(1) , plugBoardArray[i].charAt(0));
        }
        for (int i = 0; i < 26; i++)
            this.plugBoard.putIfAbsent((char) (97 + i), (char) (97 + i));
    }
    void setRotor(Map<Character, Character> map , String rotor){
        for (int i = 0; i < 26; i++) {
            map.put((char)(97+i) , rotor.charAt(i));
        }
    }
    void setReflector(){
        for (int i = 0; i < 26; i++)
            this.reflector.put((char) (97 + i) , (char) (122 - i));
    }

    Map<Character, Character> changeKeyAndValue(Map<Character , Character> map){
        Map<Character, Character> tempMap = new HashMap<>();
        for (int i = 0; i < 26; i++) {
            tempMap.put(map.get((char) (97 + i)) , (char) (97 + i));
        }
        return tempMap;
    }
    String shiftRotor(Map<Character, Character> rotor){
        String rotorInput = null;
        for (int i = 0; i < 26; i++) {
            rotorInput += rotor.get((char) (97 + i));
        }
        rotorInput = rotorInput.charAt(rotorInput.length() - 1) + rotorInput.substring(0, rotorInput.length() - 1);
        return rotorInput;
    }

    String enigmaMachine(String encodedInput){
        String decodedInput = null;
        int counter2 = 0;
        int counter3 = 0;
        for (int i = 0; i < encodedInput.length(); i++) {
            char currentCharacter = encodedInput.charAt(i);
            currentCharacter = this.plugBoard.get(currentCharacter);
            currentCharacter = this.rotor1.get(this.rotor2.get(this.rotor3.get(currentCharacter)));
            currentCharacter = this.reflector.get(currentCharacter);
            currentCharacter = changeKeyAndValue(this.rotor3).get(changeKeyAndValue(this.rotor2).get(changeKeyAndValue(this.rotor1).get(currentCharacter)));
            currentCharacter = this.plugBoard.get(currentCharacter);
            decodedInput += currentCharacter;
            setRotor(this.rotor3 , shiftRotor(rotor3));
            counter3 ++;
            if (counter3 % 26 == 0) {
                setRotor(this.rotor2 , shiftRotor(rotor2));
                counter2++;
            }
            if (counter2 != 0 && counter2 % 26 == 0) {
                setRotor(this.rotor1 , shiftRotor(rotor1));
            }
        }
        return decodedInput;
    }
}
