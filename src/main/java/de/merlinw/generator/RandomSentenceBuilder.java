package de.merlinw.generator;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RandomSentenceBuilder {

    private final String prefix;
    private final List<RandomSentencePartCollection> collections;

    public RandomSentenceBuilder(String prefix, RandomSentencePartCollection... collections) {
        this.prefix = prefix;
        this.collections = Arrays.asList(collections);
    }

    public String buildSentence() {
        if (!Main.canRun) {
            System.out.println("Files were created. Aborting sentence generation...\n");
            System.exit(0);
        }
        StringBuilder builder = new StringBuilder(prefix);
        while (builder.toString().equals(prefix))
            collections.forEach(c -> {
                Collections.shuffle(c);
                if (rand() <= c.get(0).getChance())
                    builder.append(" ").append(c.get(0).get());
            });
        return builder.toString();
    }

    private static double rand() {
        return (int) (Math.random() * (100 + 1));
    }

    public void buildSentences(int amount, OutputStream stream) throws IOException {
        buildSentences(amount, stream, null);
    }

    public void buildSentences(int amount, OutputStream stream, PercentCallback callback) throws IOException {
        for (int i = 0; i < amount; i++) {
            stream.write((buildSentence() + '\n').getBytes());
            if (callback != null) callback.callback((float) i / (float) amount);
        }
    }

    public interface PercentCallback {
        void callback(double percent) throws IOException;
    }
}
