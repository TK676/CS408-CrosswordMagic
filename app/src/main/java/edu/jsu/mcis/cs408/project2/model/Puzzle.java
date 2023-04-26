package edu.jsu.mcis.cs408.project2.model;

import java.util.HashMap;
import java.util.HashSet;

public class Puzzle {
    public static final char BLOCK_CHAR = '*';
    public static final char BLANK_CHAR = ' ';
    private HashMap<String, Word> words;
    private HashSet<String> guessed;
    private String name, description;
    private Integer height, width;
    private Character[][] letters;
    private Integer[][] numbers;
    private boolean solved = false;
    private StringBuilder cluesAcrossBuffer, cluesDownBuffer;

    public Puzzle(HashMap<String, String> params) {
        this.name = params.get("name");
        this.description = params.get("description");
        this.height = Integer.parseInt(params.get("height"));
        this.width = Integer.parseInt(params.get("width"));

        guessed = new HashSet<>();
        words = new HashMap<>();

        letters = new Character[height][width];
        numbers = new Integer[height][width];

        cluesAcrossBuffer = new StringBuilder();
        cluesDownBuffer = new StringBuilder();

        for (int i = 0; i < height; ++i) {
            for (int j = 0; j < width; ++j) {
                letters[i][j] = BLOCK_CHAR;
                numbers[i][j] = 0;
            }
        }
    }

    public void addWord(Word word) {
        String key = (word.getBox() + word.getDirection().toString());

        words.put(key, word);

        int row = word.getRow();
        int column = word.getColumn();
        int length = word.getWord().length();

        numbers[row][column] = word.getBox();

        for (int i = 0; i < length; ++i) {
            letters[row][column] = BLANK_CHAR;

            if (word.isAcross()) {
                column++;
            }
            else if (word.isDown()) {
                row++;
            }
        }

        if (word.isAcross()) {
            cluesAcrossBuffer.append(word.getBox()).append(": ").append(word.getClue()).append("\n");
        }
        else if (word.isDown()) {
            cluesDownBuffer.append(word.getBox()).append(": ").append(word.getClue()).append("\n");
        }
    }

    public Word guess(Integer num, String guess) {
        Word result = null;

        String acrossKey = num + WordDirection.ACROSS.toString();
        String downKey = num + WordDirection.DOWN.toString();

        Word aWord = getWord(acrossKey);
        Word dWord = getWord(downKey);

        try {
            if (guess.equals(aWord.getWord()) && !guessed.contains(acrossKey) && aWord != null) {
                result = getWord(acrossKey);
                addWordToGrid(acrossKey);
            }
        }
        catch (Exception e) {}

        try {
            if (guess.equals(dWord.getWord()) && !guessed.contains(downKey) & dWord != null) {
                result = getWord(downKey);
                addWordToGrid(downKey);
            }
        }
        catch (Exception e) { }

        checkSolved();

        return result;
    }

    private void checkSolved() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (letters[i][j].equals(BLANK_CHAR)) {
                    solved = false;
                    return;
                }
            }
        }
        solved = true;
    }

    public void addWordToGrid(String key) {
        Word w = words.get(key);

        guessed.add(key);

        int row = w.getRow();
        int column = w.getColumn();
        int length = w.getWord().length();
        WordDirection direction = w.getDirection();

        for (int i = 0; i < length; i++) {
            Character letter = w.getWord().charAt(i);

            if (direction.ordinal() == 0) {
                letters[row][column + i] = letter;
            }
            else {
                letters[row + i][column] = letter;
            }
        }
    }

    public Word getWord(String key) {
        return words.get(key);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getHeight() {
        return height;
    }

    public String getCluesAcross() {
        return cluesAcrossBuffer.toString();
    }

    public String getCluesDown() {
        return cluesDownBuffer.toString();
    }

    public int getNumWords() {
        return words.size();
    }

    public Character[][] getLetters() {
        return letters;
    }

    public Integer[][] getNumbers() {
        return numbers;
    }

    public boolean isSolved() {
        return solved;
    }
}