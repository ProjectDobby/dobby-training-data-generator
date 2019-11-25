package de.merlinw.generator.engines;

import de.merlinw.generator.classes.BaseEngine;

public class Snips extends BaseEngine {

    public String formatSlot(String slot, String value) {
        return String.format("[%s](%s)", value, slot);
    }

}
