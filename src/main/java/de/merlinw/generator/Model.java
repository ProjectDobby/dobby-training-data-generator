package de.merlinw.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Model extends ArrayList<RandomSentencePartCollection> {

    public static Model ModelFrom(RandomSentencePartCollection... sentenceParts) {
        return new Model(Arrays.asList(sentenceParts));
    }

    private Model(List<RandomSentencePartCollection> list) {
        super(list);
    }

}
