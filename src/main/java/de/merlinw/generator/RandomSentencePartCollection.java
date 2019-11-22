package de.merlinw.generator;

import java.util.ArrayList;
import java.util.Arrays;

public class RandomSentencePartCollection extends ArrayList<RandomSentencePart> {

    public static RandomSentencePartCollection collectionFrom(RandomSentencePart... parts) {
        return new RandomSentencePartCollection(parts);
    }

    private RandomSentencePartCollection(RandomSentencePart[] parts) {
        this.addAll(Arrays.asList(parts));
    }
}
