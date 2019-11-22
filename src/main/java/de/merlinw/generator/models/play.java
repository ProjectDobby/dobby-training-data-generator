package de.merlinw.generator.models;

import de.merlinw.generator.Model;

import java.io.IOException;

import static de.merlinw.generator.Model.ModelFrom;
import static de.merlinw.generator.RandomSentencePart.partFrom;
import static de.merlinw.generator.RandomSentencePartCollection.collectionFrom;

public class play {

    public static Model getModel() throws IOException {
        return ModelFrom(
                collectionFrom(
                        partFrom(80, "TRACK", "s:scope", "t:track", "a:artist"),
                        partFrom(70, "GENRE", "s:scope", "g:genre"),
                        partFrom(60, "PLAYLIST", "s:scope", "p:track"),
                        partFrom(70, "ARTIST", "s:scope", "a:artist"),
                        partFrom(70, "ALBUM", "s:scope", "a:artist", "al:track")
                ), collectionFrom(
                        partFrom(45, "PROVIDER", "p:provider")
                )
        );
    }

}
