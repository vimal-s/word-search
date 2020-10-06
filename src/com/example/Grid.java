package com.example;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Grid {
  private final int GRID_SIZE;
  private char[][] grid;

  public Grid(int gridSize) {
    this.GRID_SIZE = gridSize;
    grid = new char[GRID_SIZE][GRID_SIZE];
  }

  public void placeInGrid(String word) {
      int rowIndex;
      int colIndex;
      // loop until you find the value is not yet assigned to this index
      do {
        rowIndex = ThreadLocalRandom.current().nextInt(10);
        colIndex = ThreadLocalRandom.current().nextInt(10);
      } while (grid[rowIndex][colIndex] != '\u0000');

      grid[rowIndex][colIndex] = word.charAt(0);
  }

  public void printGrid() {
    // External iterator
    /*
        for (char[] chars : grid) {
          for (char colVal : chars) {
            if (colVal == '\u0000') {
              System.out.print('-' + " ");
            } else {
              System.out.print(colVal + " ");
            }
          }
          System.out.println();
        }
    */

    // Internal iterator
    Arrays.stream(grid).map(Grid::charArrayToString).forEach(System.out::println);
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
}
