package me.distcalc;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.Scanner;

public class DistCalc {
    public static void main(String[] args) {
        boolean talk = false;
        Unit unit = Unit.M;
        String[] params = new String[2];

        if (args.length > 0) {
            if (args.length == 1 && args[0].equals("-help")) {
                System.out.println("Distance Calculator is a program capable of computing distance "
                        + "between two geographical points (basically cities).\n\n"
                        + "-U=   -   specifies the unit of returned value (km/m/ft/yd)\n\n"
                        + "-t    -   makes program talkative\n\n");
                System.exit(0);
            } else {
                int j = 0;
                boolean done = false;
                for (int i = 0; i < args.length && !done; i++) {
                    if (args[i].equals("-t")) {
                        talk = true;
                    } else if (args[i].startsWith("-U=")) {
                        unit = Unit.parse(args[i].substring(3));
                    } else if (j <= 1) {
                        params[j] = args[i];
                        j++;
                    } else {
                        done = true;
                    }
                }
            }
        }

        if (params[0] == null || params[1] == null) {
            Scanner in = new Scanner(System.in);
            if (params[0] == null) {
                System.out.print("Enter the first city's name: ");
                params[0] = in.nextLine();
            }
            System.out.print("Enter the second city's name: ");
            params[1] = in.nextLine();
        }

        double distance = 0;
        try {
            distance = DistanceCalculator.between(params[0], params[1], talk);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }

        System.out.printf("The distance between %s and %s is equal to %.2f %s\n", params[0], params[1],
                                                                                distance * unit.index, unit);
    }

    private enum Unit {
        KM(0.001), M(1), FT(0.3048), YD(0.9144);

        double index;

        Unit(double index) {
            this.index = index;
        }

        public static Unit parse(String s) {
            if (s.equalsIgnoreCase("m")) {
                return Unit.M;
            } else if (s.equalsIgnoreCase("km")) {
                return Unit.KM;
            } else if (s.equalsIgnoreCase("ft")) {
                return Unit.FT;
            } else if (s.equalsIgnoreCase("yd")) {
                return Unit.YD;
            } else {
                throw new IllegalArgumentException("Unknown unit");
            }
        }
    }
}
