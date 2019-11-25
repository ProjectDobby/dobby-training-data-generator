package de.merlinw.generator.engines;

import de.merlinw.generator.classes.BaseEngine;

public class OpenNLP extends BaseEngine {

    public String formatSlot(String slot, String value) {
        return String.format("<START:%s> %s <END>", slot, value);
    }

}
