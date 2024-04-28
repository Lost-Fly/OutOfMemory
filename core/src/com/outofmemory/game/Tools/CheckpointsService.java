package com.outofmemory.game.Tools;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class CheckpointsService {
    private static final Random randomizer = new Random();

    private static final List<String> pointIds = List.of("task1", "task2", "task3","task4", "task5", "task6",
            "task7", "task8", "task9","task10", "task11", "task12","task13", "task14", "task15",
            "task16", "task17", "task18","task19", "task20");
    private static final List<String> phrases = List.of("dfghjkl", "oiu", "oufghjkkmnbv"); // Изменено на "цензура" для удаления ненормативной лексики
    private static final List<String> tasks = List.of("vjjhgu", "poiuujk", "pojhgujk");

    public static List<Checkpoint> getRandomized(int numOfPoints) {
        if (numOfPoints < 1) {
            throw new IllegalArgumentException("numOfPoints must be at least 1");
        }

        String randomPhrase = phrases.get(randomizer.nextInt(phrases.size()));
        // Split the chosen phrase into individual letters and create a list
        List<String> letters = new ArrayList<>(Arrays.asList(randomPhrase.split("")));
        // Shuffle the letters to randomize their order
        Collections.shuffle(letters);

//        if (numOfPoints > letters.size()) {
//            throw new IllegalArgumentException("numOfPoints is greater than number of letters in the phrase");
//        }

        List<Checkpoint> checkpoints = new ArrayList<>(numOfPoints);
        for (int i = 0; i < numOfPoints && !letters.isEmpty(); i++) {
            Checkpoint checkpoint = new Checkpoint("DEF");

            checkpoint.setPhrase(randomPhrase);
            checkpoint.setTask(tasks.get(randomizer.nextInt(tasks.size())));
            checkpoint.setId(pointIds.get(randomizer.nextInt(pointIds.size())));

            // Check for empty list to avoid IllegalArgumentException
            if (!letters.isEmpty()) {
                checkpoint.setLetter(letters.remove(0)); // Use the first letter since the list has been shuffled
            } else {
                checkpoint.setLetter(""); // Assign an empty string or some default value
            }

            checkpoints.add(checkpoint);


            for(Checkpoint checkpoint1: checkpoints){
                Gdx.app.log("CHECKPIN", "id " + checkpoint1.getId() + "  letter  " + checkpoint1.getLetter());
            }
        }

        return checkpoints;
    }
}

