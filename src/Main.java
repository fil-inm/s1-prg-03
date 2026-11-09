import hospital.Hospital;
import people.Nurse;
import people.Patient;
import weather.Weather;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();

        Weather weather;
        if (args.length == 3) {
            try {
                weather = new Weather(Double.parseDouble(args[0]), Double.parseDouble(args[1]),
                        Double.parseDouble(args[2]));
            } catch (IllegalArgumentException e) {
                System.out.println("Неверная погода: " + e.getMessage() + ". Беру случайную.");
                weather = Weather.random(random);
            }
        } else {
            weather = Weather.random(random);
        }

        List<String> names = new ArrayList<>(Arrays.asList("Ромашка", "Ласточка", "Белочка", "Кисонька", "Галочка"));
        Collections.shuffle(names, random);
        int count = 2 + random.nextInt(3);
        List<Nurse> nurses = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            nurses.add(new Nurse(names.get(i), random));
        }

        Patient pulka = new Patient("Пулька", random);
        new Hospital(weather, nurses, pulka, random).runScenario();
    }
}