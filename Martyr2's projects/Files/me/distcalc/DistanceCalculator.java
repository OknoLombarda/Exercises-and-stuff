package me.distcalc;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class DistanceCalculator {
    private static final String REQUEST = "https://geocode-maps.yandex.ru/1.x/?geocode=%s&format=xml&lang=en_US";
    private static final int EARTH_RADIUS = 6371000;

    private static boolean talks;

    public static double between(String first, String second, boolean talk) throws IOException, XMLStreamException {
        talks = talk;
        double[] firstCoords = getData(first);
        double[] secondCoords = getData(second);

        say("Calculating distance...\n\n");
        double phi_1 = Math.toRadians(firstCoords[1]);
        double phi_2 = Math.toRadians(secondCoords[1]);
        double d_phi = Math.toRadians(secondCoords[1] - firstCoords[1]);
        double d_lam = Math.toRadians(secondCoords[0] - firstCoords[0]);

        double a = Math.pow(Math.sin(d_phi / 2.0), 2) + Math.cos(phi_1) * Math.cos(phi_2)
                * Math.pow(Math.sin(d_lam / 2.0), 2);                       // Haversine
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));          // formula
        double d = EARTH_RADIUS * c;                                        // distance

        return d;
    }

    public static double between(String first, String second) throws IOException, XMLStreamException {
        return between(first, second, false);
    }

    private static double[] getData(String input) throws IOException, XMLStreamException {
        HttpURLConnection connection = getConnection(validateParameters(input));
        connection.connect();
        say("Sending request (" + input + ")...\n");

        XMLStreamReader parser = XMLInputFactory.newInstance().createXMLStreamReader(connection.getInputStream());

        double[] coords = new double[2];

        say("Reading response...\n");
        boolean done = false;
        while (parser.hasNext() && !done) {
            int event = parser.next();
            if (event == XMLStreamConstants.START_ELEMENT) {
                String localName = parser.getLocalName();
                if (localName.equals("found")) {
                    parser.next();
                    int found = Integer.parseInt(parser.getText());
                    if (found <= 0) {
                        return null;
                    }
                } else if (localName.equals("Point")) {
                    do {
                        event = parser.next();
                        if (event == XMLStreamConstants.START_ELEMENT) {
                            localName = parser.getLocalName();
                        }
                    } while (parser.hasNext() && !localName.equals("pos"));
                    parser.next();
                    String[] coordsTmp = parser.getText().split("\\s+");
                    coords[0] = Double.parseDouble(coordsTmp[0]); // longitude
                    coords[1] = Double.parseDouble(coordsTmp[1]); // latitude
                    done = true;
                }
            }
        }

        return coords;
    }

    private static String validateParameters(String s) {
        if ((s.startsWith("\"") && s.endsWith("\"")) || (s.startsWith("'") && s.endsWith("'"))) {
            s = s.substring(1, s.length() - 1);
        }
        s = String.join("+", s.split("\\s+"));
        return s;
    }

    private static HttpURLConnection getConnection(String parameters) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) new URL(String.format(REQUEST, parameters)).openConnection();
        connection.setInstanceFollowRedirects(true);
        return connection;
    }

    private static void say(String s) {
        if (talks) {
            System.out.print(s);
        }
    }
}
