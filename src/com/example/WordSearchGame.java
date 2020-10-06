package com.example;

import java.util.Arrays;
import java.util.List;

public class WordSearchGame {

  public static void main(String[] args) {

    Grid grid = new Grid(10);

    List<String> wordsToHide = Arrays.asList("One", "Two", "Three");
    // provide a method in Grid class for adding all words
    wordsToHide.forEach(grid::placeInGrid);

    grid.printGrid();
  }
}
