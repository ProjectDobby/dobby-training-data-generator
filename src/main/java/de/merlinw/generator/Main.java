package de.merlinw.generator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static boolean canRun = true;
    public static String Model;

    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if (args.length < 2) {
            System.out.println("Please execute with 2 arguments (1: model name, 2: dataset amount)");
            System.exit(1);
        }

        Model = args[0];

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

    private static Model getModel(String name) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        return (Model) Main.class.getClassLoader().loadClass("de.merlinw.generator.models." + name).getDeclaredMethod("getModel").invoke(Model.class);
    }

}
