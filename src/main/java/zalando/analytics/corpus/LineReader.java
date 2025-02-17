package zalando.analytics.corpus;

import java.io.*;
import java.util.Iterator;

/**
 * Simple helper class for reading a text file line by line.
 * <p>
 * Created by Alan Akbik on 3/3/17.
 */
public class LineReader implements Iterable<String> {

    // Internal reader
    BufferedReader parseReader;

    public static LineReader readFromClasspathFile(String fileOnClasspath) {

        LineReader conllFileReader = new LineReader();
        try {
            conllFileReader.parseReader = new BufferedReader(new InputStreamReader(
                    conllFileReader.getClass().getClassLoader().getResourceAsStream(
                            fileOnClasspath), "UTF8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return conllFileReader;
    }

    public static LineReader readFromFile(String file) {

        LineReader conllFileReader = new LineReader();
        try {
            conllFileReader.parseReader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(file), "UTF8"));
        } catch (UnsupportedEncodingException | FileNotFoundException e) {
            e.printStackTrace();
        }

        return conllFileReader;
    }


    /**
     * Constructor for this method.
     *
     */
    private LineReader() {
//        try {
//            parseReader = new BufferedReader(new InputStreamReader(
//                    new FileInputStream(path), "UTF8"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    /**
     * Returns the next line in the file.
     *
     * @return next line in the file
     */
    public String getNextLine() {
        String line;
        try {
            line = parseReader.readLine();
            return line;
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return null;
    }

    @Override
    public Iterator<String> iterator() {
        return new Iterator<String>() {

            @Override
            public boolean hasNext() {
                try {
                    parseReader.mark(1);
                    if (parseReader.read() < 0) {
                        return false;
                    }
                    parseReader.reset();
                    return true;
                } catch (IOException e) {
                    return false;
                }
            }

            @Override
            public String next() {
                try {
                    return parseReader.readLine();
                } catch (IOException e) {
                    return null;
                }
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }

        };
    }

}
