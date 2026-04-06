package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private HashMap<DayOfWeek, TreeMap<TimeOfDay, ArrayList<TrainingSession>>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek day = trainingSession.getDayOfWeek();
        TimeOfDay time = trainingSession.getTimeOfDay();

        TreeMap<TimeOfDay, ArrayList<TrainingSession>> dayMap = timetable.get(day);
        if (dayMap == null) {
            dayMap = new TreeMap<>();
            timetable.put(day, dayMap);
        }
        ArrayList<TrainingSession> trainingList = dayMap.get(time);
        if (trainingList == null) {
            trainingList = new ArrayList<>();
            dayMap.put(time, trainingList);
        }

        trainingList.add(trainingSession);
    }

    public TreeMap<TimeOfDay, ArrayList<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return timetable.getOrDefault(dayOfWeek, new TreeMap<>());
    }

    public ArrayList<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> dayMap = timetable.get(dayOfWeek);
        if (dayMap == null) {
            return new ArrayList<>();
        }
        return dayMap.getOrDefault(timeOfDay, new ArrayList<>());
    }

    public ArrayList<CounterOfTrainings> getCountByCoaches() {
        HashMap<Coach, Integer> coachMap = new HashMap<>();
        for (TreeMap<TimeOfDay, ArrayList<TrainingSession>> treeMap : timetable.values()) {
            for (ArrayList<TrainingSession> arrayList : treeMap.values()) {
                for (TrainingSession trainingSession : arrayList) {
                    Coach coach = trainingSession.getCoach();
                    coachMap.put(coach, coachMap.getOrDefault(coach, 0) + 1);
                }
            }
        }
        ArrayList<CounterOfTrainings> coachList = new ArrayList<>();
        for (Map.Entry<Coach, Integer> entry : coachMap.entrySet()) {
            coachList.add(new CounterOfTrainings(entry.getKey(), entry.getValue()));
        }
        Collections.sort(coachList);
        return coachList;
    }
}
