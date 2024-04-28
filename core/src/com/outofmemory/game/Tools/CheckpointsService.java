package com.outofmemory.game.Tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CheckpointsService {
    private static final Random randomizer = new Random();

    private static final List<String> pointIds = List.of("Point 1", "Point 2", "Point 3");
    private static final List<String> phrases = List.of("хуй", "ДММ", "электроперфоратор");
    private static final List<String> tasks = List.of("Потанцуй", "Выпей пива", "Стань ЧОРНЫМ");

    public static List<Checkpoint> getRandomized(int numOfPoints) {
        if (numOfPoints < 1) {
            throw new IllegalArgumentException("numOfPoints must be at least 1");
        }

        String randomPhrase = phrases.get(randomizer.nextInt(phrases.size()));
        // Split the chosen phrase into individual letters
        List<String> letters = new ArrayList<>(Arrays.asList(randomPhrase.split("")));

//        if (numOfPoints > letters.size()) {
//            throw new IllegalArgumentException("numOfPoints is greater than number of letters in the phrase");
//        }

        List<Checkpoint> checkpoints = new ArrayList<>(numOfPoints);
        for (int i = 0; i < numOfPoints; i++) {
            Checkpoint checkpoint = new Checkpoint();
            checkpoint.setPhrase(randomPhrase);
            checkpoint.setTask(tasks.get(randomizer.nextInt(tasks.size())));
            checkpoint.setId(pointIds.get(randomizer.nextInt(pointIds.size())));
            checkpoint.setLetter(letters.remove(randomizer.nextInt(letters.size())));

            checkpoints.add(checkpoint);
        }

        return checkpoints;
    }
}

