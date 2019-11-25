package de.merlinw.generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class RandomSentencePart {

    private Map<String, List<String>> placeholderSets = new HashMap<>();
    private List<String> dataSet;
    private int chance;

    public static RandomSentencePart partFrom(String name, String... placeholderFiles) throws IOException {
        return new RandomSentencePart(name, placeholderFiles);
    }

    public static RandomSentencePart partFrom(int chance, String name, String... placeholderFiles) throws IOException {
        return new RandomSentencePart(chance, name, placeholderFiles);
    }

    public RandomSentencePart(String name, String... placeholderFiles) throws IOException {
        Arrays.asList(placeholderFiles).forEach(ph -> {
            File wordSet = new File("datasets." + Main.Model + "/" + name + "-" + ph.split(":")[1] + "-slot.set");
            try {
                if (!wordSet.exists()) {
                    wordSet.createNewFile();
                    Main.canRun = false;
                }
                this.placeholderSets.put(ph, this.readSet(wordSet));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        File dataSet = new File("datasets." + Main.Model + "/" + name + "-parts.set");
        if (!dataSet.exists()) {
            dataSet.createNewFile();
            Main.canRun = false;
        }
        this.chance = 100;
        this.dataSet = readSet(dataSet);
    }

    public RandomSentencePart(int chance, String name, String... placeholderFiles) throws IOException {
        this(name, placeholderFiles);
        this.chance = chance;
    }

    private List<String> readSet(File dataSet) throws IOException {
        List<String> set = new ArrayList<>();
        FileReader reader = new FileReader(dataSet);
        BufferedReader read = new BufferedReader(reader);
        String line;
        while ((line = read.readLine()) != null)
            if (!line.equals("") && !set.contains(line)) set.add(line);
        return set;
    }

    public String get() {
        int maxTries = 30;
        Collections.shuffle(dataSet);
        String part = dataSet.get(0);
        try {
            while (containsPlaceHolder(part)) {
                if (maxTries-- <= 0) return get();
                for (String slot : placeholderSets.keySet()) {
                    if (!part.contains("(" + slot.split(":")[0] + ")")) continue;
                    List<String> phs = placeholderSets.get(slot);
                    Collections.shuffle(phs);
                    part = part.replaceFirst("\\(" + slot.split(":")[0] + "\\)", Main.Engine.formatSlot(slot.split(":")[1], phs.get(0)));
                }
            }
        } catch (Exception e) {
            return get();
        }
        return part;
    }

    private boolean containsPlaceHolder(String part) {
        List<String> placeHolders = this.placeholderSets.keySet().stream().map(s -> s.split(":")[0]).collect(Collectors.toList());
        return Arrays.stream(part.split(" ")).map(p -> p.replaceAll("^\\(|\\)$", "")).anyMatch(placeHolders::contains);
    }

    public int getChance() {
        return chance;
    }
}
