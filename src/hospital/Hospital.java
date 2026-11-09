package hospital;

import exceptions.NurseExhaustedException;
import people.Nurse;
import people.Patient;
import weather.Weather;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Hospital {


    @FunctionalInterface
    public interface NewDayListener {
        void onNewDay();
    }

    private static final int MAX_DAYS = 10;

    private Weather weather;
    private final ArrayList<Nurse> nurses;
    private final Patient patient;
    private final Kitchen kitchen = new Kitchen();
    private final ArrayList<NewDayListener> listeners = new ArrayList<>();
    private final Random random;
    private int day;

    public Hospital(Weather weather, List<Nurse> nurses, Patient patient, Random random) {
        this.weather = weather;
        this.nurses = new ArrayList<>(nurses);
        this.patient = patient;
        this.random = random;

        listeners.add(kitchen);
        for (Nurse nurse : this.nurses) {
            listeners.add(() -> nurse.onNewDay(this.weather));
        }
        listeners.add(() -> patient.onNewDay(this.weather));
    }

    public void runScenario() {
        System.out.println("После бегства Ворчуна и Пилюлькина весь персонал больницы занят единственным больным — "
                + patient.getName() + ".");
        System.out.print("За ним ухаживают нянечки:");
        for (Nurse n : nurses) System.out.print(" " + n.getName());
        System.out.println(".");

        while (!isFinished()) {
            playDay();
        }
        printEnding();
    }

    private void playDay() {
        day++;
        if (day > 1) {
            weather = weather.next(random);
        }
        System.out.println();
        System.out.println("=== День " + day + ". За окном " + weather + " ===");
        for (NewDayListener l : listeners) {
            l.onNewDay();
        }

        for (Nurse nurse : nurses) {
            while (nurse.canApproach()) {
                try {
                    if (nurse.approach(patient, kitchen)) {
                        break;
                    }
                } catch (NurseExhaustedException e) {
                    System.out.println("  " + e.getMessage() + ".");
                }
            }
            if (patient.isInLove() || patient.isRecovered()) {
                break;
            }
        }
        System.out.println("За день кухня приготовила блюд: " + kitchen.getCookedToday() + ".");
    }

    private boolean isFinished() {
        if (patient.isInLove() || patient.isRecovered() || day >= MAX_DAYS) {
            return true;
        }
        for (Nurse n : nurses) {
            if (!n.isExhausted()) return false;
        }
        return true;
    }

    private void printEnding() {
        System.out.println();
        if (patient.isInLove()) {
            System.out.println(patient.getName() + " влюбился в нянечку " + patient.favourite().getName()
                    + " и с тех пор ест всё, что она приносит.");
        } else if (patient.isRecovered()) {
            System.out.println(patient.getName() + " наконец выздоровел. Нянечки вздохнули с облегчением.");
        } else if (day >= MAX_DAYS) {
            System.out.println("Прошло " + day + " дней. Нянечки говорили, что это сущее наказание, а не больной, "
                    + "и чтобы он выздоравливал уж поскорее, что ли.");
        } else {
            System.out.println("Все нянечки сбились с ног, исполняя капризы. Спокон веку у них такого больного не было.");
        }
    }
}
