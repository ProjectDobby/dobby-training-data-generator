package de.merlinw.generator.models;

import de.merlinw.generator.Model;
import de.merlinw.generator.classes.BaseModel;

import java.io.IOException;

import static de.merlinw.generator.Model.ModelFrom;
import static de.merlinw.generator.RandomSentencePart.partFrom;
import static de.merlinw.generator.RandomSentencePartCollection.collectionFrom;

public class pause extends BaseModel {

    public Model getModel() throws IOException {
        return ModelFrom(
                collectionFrom(
                        partFrom(
                                "PAUSE"
                        )
                )
        );
    }

}
