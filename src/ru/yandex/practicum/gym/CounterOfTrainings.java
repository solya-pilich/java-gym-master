package ru.yandex.practicum.gym;

public class CounterOfTrainings implements Comparable<CounterOfTrainings> {

    private Coach coach;
    private int numberOfSession;

    public CounterOfTrainings(Coach coach, int numberOfSession) {
        this.coach = coach;
        this.numberOfSession = numberOfSession;
    }

    @Override
    public int compareTo(CounterOfTrainings o) {
        return o.numberOfSession - this.numberOfSession;
    }

    public Coach getCoach() {
        return coach;
    }

    public int getNumberOfSession() {
        return numberOfSession;
    }
}
