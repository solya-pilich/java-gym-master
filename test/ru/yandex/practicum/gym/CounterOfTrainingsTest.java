package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class CounterOfTrainingsTest {

    @Test
    void shouldReturnSingleCoachWithCorrectCount() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession firstTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession secondTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.WEDNESDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(firstTrainingSession);
        timetable.addNewTrainingSession(secondTrainingSession);

        // Проверяем, что для одного тренера выводится две тренировки
        ArrayList<CounterOfTrainings> coachList = timetable.getCountByCoaches();
        assertEquals(1, coachList.size());

        CounterOfTrainings coachFromList = coachList.get(0);
        assertEquals(coach, coachFromList.getCoach());
        assertEquals(2, coachFromList.getNumberOfSession());
    }

    @Test
    void shouldReturnMultipleCoachesSortedByCountDescending() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Петров", "Илья", "Григорьевич");
        TrainingSession firstTrainingSession = new TrainingSession(group, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession secondTrainingSession = new TrainingSession(group, coach2,
                DayOfWeek.WEDNESDAY, new TimeOfDay(13, 0));
        TrainingSession thirdTrainingSession = new TrainingSession(group, coach2,
                DayOfWeek.FRIDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(firstTrainingSession);
        timetable.addNewTrainingSession(secondTrainingSession);
        timetable.addNewTrainingSession(thirdTrainingSession);

        // Проверяем, что первым в списке коуч2, т.к. у него 2 урока, а у коуча1 всего 1 урок
        ArrayList<CounterOfTrainings> coachList = timetable.getCountByCoaches();
        assertEquals(2, coachList.size());

        CounterOfTrainings coach2FromList = coachList.get(0);
        assertEquals(coach2, coach2FromList.getCoach());
        assertEquals(2, coach2FromList.getNumberOfSession());

        CounterOfTrainings coach1FromList = coachList.get(1);
        assertEquals(coach1, coach1FromList.getCoach());
        assertEquals(1, coach1FromList.getNumberOfSession());
    }

    @Test
    void shouldReturnEmptyList() {
        Timetable timetable = new Timetable();

        // Проверяем, что при пустом расписании возвращается пустой список
        ArrayList<CounterOfTrainings> coachList = timetable.getCountByCoaches();
        assertEquals(0, coachList.size());
        assertNotNull(coachList);
    }

    @Test
    void twoCoachesWithTheSameAmountOfTraining() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Петров", "Илья", "Григорьевич");
        TrainingSession firstTrainingSessionCoach1 = new TrainingSession(group, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession secondTrainingSessionCoach1 = new TrainingSession(group, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(20, 0));
        TrainingSession firstTrainingSessionCoach2 = new TrainingSession(group, coach2,
                DayOfWeek.FRIDAY, new TimeOfDay(13, 0));
        TrainingSession secondTrainingSessionCoach2 = new TrainingSession(group, coach2,
                DayOfWeek.WEDNESDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(firstTrainingSessionCoach1);
        timetable.addNewTrainingSession(secondTrainingSessionCoach1);
        timetable.addNewTrainingSession(firstTrainingSessionCoach2);
        timetable.addNewTrainingSession(secondTrainingSessionCoach2);

        // Проверяем, что при одинаковом количестве тренировок у двух трениров, они оба возвращаются, порядок может быть любым
        ArrayList<CounterOfTrainings> coachList = timetable.getCountByCoaches();
        assertEquals(2, coachList.size());

        // Если добавить еще одного тренера с одной тренировкой, он должен появиться в конце списка
        Coach coach3 = new Coach("Кошкин", "Дмитрий", "Иванович");
        TrainingSession firstTrainingSessionCoach3 = new TrainingSession(group, coach3,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        timetable.addNewTrainingSession(firstTrainingSessionCoach3);

        coachList = timetable.getCountByCoaches();
        assertEquals(3, coachList.size());
        CounterOfTrainings coach3FromList = coachList.get(2);
        assertEquals(coach3, coach3FromList.getCoach());
    }
}
