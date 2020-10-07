package com.example;

import java.util.Arrays;
import java.util.List;

public class WordSearchGame {

  public static void main(String[] args) {

    Grid grid = new Grid(10);

    List<String> wordsToHide = Arrays.asList("One", "Two", "Three");

    grid.placeWords(wordsToHide);

    grid.displayGrid();
  }
}
