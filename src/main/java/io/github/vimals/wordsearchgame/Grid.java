package io.github.vimals.wordsearchgame;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Grid {

  private final int GRID_SIZE;
  private final char[][] contents;

  private enum Direction {
    RIGHT,
    DOWN,
    BOTH
  }

  public Grid(int gridSize) {
    this.GRID_SIZE = gridSize;
    contents = new char[GRID_SIZE][GRID_SIZE];
  }

  public void displayGrid() {
    Arrays.stream(contents).map(Grid::charArrayToString).forEach(System.out::println);
  }

  public void placeWords(List<String> words) {
    words.forEach(this::placeWord);
  }

  public void placeWord(String word) {
    if (word.length() > GRID_SIZE) {
      return;
    }

    int rowIndex;
    int colIndex;

    List<Direction> directions = Arrays.asList(Direction.values());
    Collections.shuffle(directions);

    // loop until you find the value is not yet assigned to this index
    boolean isNotAdded = true;
    // todo: maintain array for neglected indexes and reset after loop is completed
    //        else infinite loop may occur
    while (isNotAdded) {
      rowIndex = ThreadLocalRandom.current().nextInt(GRID_SIZE);
      colIndex = ThreadLocalRandom.current().nextInt(GRID_SIZE);

      for (Direction direction : directions) {
        boolean testFailed = testCondition(word, colIndex, rowIndex, direction);

        if (!testFailed) {
          for (char curr : word.toCharArray()) {
            contents[rowIndex][colIndex] = curr;

            switch (direction) {
              case RIGHT:
                colIndex++;
                break;
              case DOWN:
                rowIndex++;
                break;
              case BOTH:
                rowIndex++;
                colIndex++;
                break;
            }
          }
          isNotAdded = false;
          break;
        }
      }
    }
  }

  private boolean testCondition(String word, int colIndex, int rowIndex, Direction direction) {
    return wordExceedsGrid(word, rowIndex, colIndex, direction)
        || wordOverwrites(word, rowIndex, colIndex, direction);
  }

  private static String charArrayToString(char[] chars) {
    String charsToFill = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ";

    StringBuilder sb = new StringBuilder();

    for (char value : chars) {
      if (value == '\u0000') {
        int randomIndex = ThreadLocalRandom.current().nextInt(charsToFill.length());
        sb.append(charsToFill.charAt(randomIndex));
      } else {
        sb.append(value);
      }
      sb.append(' ');
    }

    return sb.toString();
  }

  // todo: what am I doing here?
  private boolean wordExceedsGrid(String word, int rowIndex, int colIndex, Direction direction) {

    int temp = 0;

    switch (direction) {
      case RIGHT:
        temp = colIndex;
        break;
      case DOWN:
        temp = rowIndex;
        break;
      case BOTH:
        temp = GRID_SIZE - colIndex <= GRID_SIZE - rowIndex ? colIndex : rowIndex;
        break;
    }

    return temp + word.length() > GRID_SIZE;
  }

  private boolean wordOverwrites(String word, int rowIndex, int colIndex, Direction direction) {
    // maybe this loop is bad and should be replaced with a while loop
    for (char ignore : word.toCharArray()) {
      if (contents[rowIndex][colIndex] != '\u0000') {
        return true;
      }

      switch (direction) {
        case RIGHT:
          colIndex++;
          break;
        case DOWN:
          rowIndex++;
          break;
        case BOTH:
          rowIndex++;
          colIndex++;
          break;
      }
    }

    return false;
  }
}
