package de.merlinw.generator;

import de.merlinw.generator.classes.BaseEngine;
import de.merlinw.generator.classes.BaseModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static boolean canRun = true;
    public static String Model;
    public static BaseEngine Engine;

    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if (args.length < 2) {
            System.out.println("Please execute with 2 arguments (1: model name, 2: dataset amount)");
            System.exit(1);
        }

        Model = args[0];
        Engine = getEngine(args.length > 2 ? args[2] : "OpenNLP");
        
        File datasetFolder = new File("datasets." + args[0].toLowerCase());
        if (!datasetFolder.exists() || !datasetFolder.isDirectory()) {
            datasetFolder.mkdir();
        }

        RandomSentenceBuilder builder = new RandomSentenceBuilder(
                "Spiele",
                getModel(args[0]).toArray(new RandomSentencePartCollection[]{})
        );

        FileOutputStream stream = new FileOutputStream(new File(args[0] + ".out.set"));
        AtomicInteger count = new AtomicInteger();
        builder.buildSentences(Integer.parseInt(args[1]), stream, percent -> {
            percent *= 100;
            int terminalWidth = 50;
            System.out.printf("\r%.3f%%", percent);
            StringBuilder equals = new StringBuilder(), spaces = new StringBuilder();
            for (int i = 0; i < (percent / 100 * terminalWidth); i++)
                equals.append("=");
            for (int i = 0; i < (terminalWidth - equals.length()); i++)
                spaces.append(" ");
            System.out.print(" [" + equals + spaces + "] " + count.incrementAndGet());
        });
        stream.close();

        if (!canRun)
            System.out.println("Model files for model " + args[0] + " generated. Please fill with data and restart.");
    }

    private static Model getModel(String name) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException, IOException {
        return ((BaseModel) Main.class.getClassLoader().loadClass("de.merlinw.generator.models." + name).getDeclaredConstructor().newInstance()).getModel();
    }

    private static BaseEngine getEngine(String name) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        return (BaseEngine) Main.class.getClassLoader().loadClass("de.merlinw.generator.engines." + name).getDeclaredConstructor().newInstance();
    }

}
