package de.merlinw.generator.models;

import de.merlinw.generator.Model;

import java.io.IOException;

import static de.merlinw.generator.Model.ModelFrom;
import static de.merlinw.generator.RandomSentencePart.partFrom;
import static de.merlinw.generator.RandomSentencePartCollection.collectionFrom;

public class pause {

    public static Model getModel() throws IOException {
        return ModelFrom(
                collectionFrom(
                        partFrom(
                                "PAUSE"
                        )
                )
        );
    }

}
