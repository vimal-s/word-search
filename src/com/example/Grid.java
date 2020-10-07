package com.example;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Grid {

  private final int GRID_SIZE;
  private final char[][] grid;

  public Grid(int gridSize) {
    this.GRID_SIZE = gridSize;
    grid = new char[GRID_SIZE][GRID_SIZE];
  }

  public void displayGrid() {
    Arrays.stream(grid).map(Grid::charArrayToString).forEach(System.out::println);
  }

  public void placeWords(List<String> wordsToHide) {
    wordsToHide.forEach(this::placeWord);
  }

  public void placeWord(String word) {
    int rowIndex;
    int colIndex;

    // loop until you find the value is not yet assigned to this index
    do {
      rowIndex = ThreadLocalRandom.current().nextInt(GRID_SIZE);
      colIndex = ThreadLocalRandom.current().nextInt(GRID_SIZE);
    } while (wordExceedsGrid(word, colIndex) || wordOverwrites(word, rowIndex, colIndex));

    for (char curr : word.toCharArray()) {
      grid[rowIndex][colIndex] = curr;
      colIndex++;
    }
  }

  private static String charArrayToString(char[] chars) {
    StringBuilder sb = new StringBuilder();

    for (char value : chars) {
      if (value == '\u0000') {
        sb.append('-');
      } else {
        sb.append(value);
      }
      sb.append(' ');
    }

    return sb.toString();
  }

  private boolean wordExceedsGrid(String word, int colIndex) {
    return colIndex + word.length() > GRID_SIZE;
  }

  private boolean wordOverwrites(String word, int rowIndex, int colIndex) {
    // maybe this loop is bad and should be replaced with a while loop
    for (char ignore : word.toCharArray()) {
      if (grid[rowIndex][colIndex] != '\u0000') {
        return true;
      }
      colIndex++;
    }
    return false;
  }
}
