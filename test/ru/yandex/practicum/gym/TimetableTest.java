package ru.yandex.practicum.gym;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import java.util.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник вернулось одно занятие
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> dayMapMonday = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        int numberOfTrainingMonday = 0;
        for (ArrayList<TrainingSession> training : dayMapMonday.values()) {
            numberOfTrainingMonday += training.size();
        }
        assertEquals(1, numberOfTrainingMonday);

        //Проверить, что за вторник не вернулось занятий
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> dayMapTuesday = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        int numberOfTrainingTuesday = 0;
        for (ArrayList<TrainingSession> training : dayMapTuesday.values()) {
            numberOfTrainingTuesday += training.size();
        }
        assertEquals(0, numberOfTrainingTuesday);
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> dayMapMonday = timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        int numberOfTrainingMonday = 0;
        for (ArrayList<TrainingSession> training : dayMapMonday.values()) {
            numberOfTrainingMonday += training.size();
        }
        assertEquals(1, numberOfTrainingMonday);

        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> dayMapThursday = timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);
        int numberOfTrainingThursday = 0;
        for (ArrayList<TrainingSession> training : dayMapThursday.values()) {
            numberOfTrainingThursday += training.size();
        }
        assertEquals(2, numberOfTrainingThursday); // Убедиться, что в четверг всего две записи
        assertEquals(new TimeOfDay(13, 0), dayMapThursday.firstKey());
        assertEquals(new TimeOfDay(20, 0), dayMapThursday.lastKey());

        // Проверить, что за вторник не вернулось занятий
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> dayMapTuesday = timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);
        int numberOfTrainingTuesday = 0;
        for (ArrayList<TrainingSession> training : dayMapTuesday.values()) {
            numberOfTrainingTuesday += training.size();
        }
        assertEquals(0, numberOfTrainingTuesday);
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        ArrayList<TrainingSession> trainingMondayAt13 = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(13, 0));
        assertEquals(1, trainingMondayAt13.size());

        //Проверить, что за понедельник в 14:00 не вернулось занятий
        ArrayList<TrainingSession> trainingMondayAt14 = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,
                new TimeOfDay(14, 0));
        assertEquals(0, trainingMondayAt14.size());
    }

    @Test
    void testMultipleSessionsAtSameTime() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Петров", "Илья", "Валерьевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession AdultTrainingSession = new TrainingSession(groupAdult, coach1,
                DayOfWeek.WEDNESDAY, new TimeOfDay(20, 0));

        Group groupChildren = new Group("Акробатика для детей", Age.CHILD, 90);
        TrainingSession ChildrenTrainingSession = new TrainingSession(groupChildren, coach2,
                DayOfWeek.WEDNESDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(AdultTrainingSession);
        timetable.addNewTrainingSession(ChildrenTrainingSession);

        // Проверить, что корректно возвращает оба занятия в одно и то же время, в один и тот же день
        ArrayList<TrainingSession> training = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.WEDNESDAY,
                new TimeOfDay(20, 0));
        assertEquals(2, training.size());
        assertTrue(training.contains(AdultTrainingSession));
        assertTrue(training.contains(ChildrenTrainingSession));
    }

    @Test
    void testBoundaryTimes() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupChildren = new Group("Акробатика для детей", Age.CHILD, 90);
        TrainingSession ChildrenTrainingSessionAt00 = new TrainingSession(groupChildren, coach,
                DayOfWeek.WEDNESDAY, new TimeOfDay(0, 0));

        TrainingSession ChildrenTrainingSessionAt2359 = new TrainingSession(groupChildren, coach,
                DayOfWeek.WEDNESDAY, new TimeOfDay(23, 59));

        timetable.addNewTrainingSession(ChildrenTrainingSessionAt00);
        timetable.addNewTrainingSession(ChildrenTrainingSessionAt2359);

        // Проверить, что с граничными значениями времени занятия корректно добавляются и возвращаются
        ArrayList<TrainingSession> trainingAt00 = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.WEDNESDAY,
                new TimeOfDay(0, 0));
        assertEquals(1, trainingAt00.size());
        assertTrue(trainingAt00.contains(ChildrenTrainingSessionAt00));

        ArrayList<TrainingSession> trainingAt2359 = timetable.getTrainingSessionsForDayAndTime(DayOfWeek.WEDNESDAY,
                new TimeOfDay(23, 59));
        assertEquals(1, trainingAt2359.size());
        assertTrue(trainingAt2359.contains(ChildrenTrainingSessionAt2359));
    }

    @Test
    void testEmptyTimetable() {
        Timetable timetableEmpty = new Timetable();
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> tableMonday = timetableEmpty.getTrainingSessionsForDay(DayOfWeek.MONDAY);
        assertTrue(tableMonday.isEmpty());
    }

}