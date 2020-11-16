package fi.hyperlearner;

import static java.lang.System.out;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Unit test for simple App.
 */
public class AppTest {
    /**
     * Rigorous Test :-)
     */
    List<Person> personList = new ArrayList<>();

    @Before
   public void populatePersonList(){
        for (int i = 1; i < 6; i++) {
            Person person = new Person();
            List<Shifts> shifts = new ArrayList<>();
            person.setId(i);
            person.setName("Person_" + i);
            shifts.add(Shifts.AFTERNOON);
            shifts.add(Shifts.NIGHT);
            if (i % 2 == 0) {
                person.setEmploymentType(EmploymentType.PERMANENT);
                shifts.add(Shifts.MORNING);
            } else {
                person.setEmploymentType(EmploymentType.CONTRACT);

            }
            person.setSalary(1000d * i);
            person.setShifts(shifts);
            personList.add(person);
        }

        Person person = new Person();
        person.setSalary(4000d);
        person.setId(6);
        person.setName("Person_2");
        person.setEmploymentType(EmploymentType.PERMANENT);
        person.setShifts(Arrays.asList(Shifts.MORNING,Shifts.NIGHT));
        personList.add(person);
    }

    @Test
    public void  checkIfEverythingIsGood(){
        out.println(" ");
        out.print("*****print all persons*******\n");
        personList.forEach(person -> out.println(person));
        assertTrue(personList.size() > 0);
    }

    @Test
    public void printOnlyContractPersons(){
        out.println(" ");
        out.print("*****print only contract person*******\n");
        List<Person> contractors =  personList.stream().filter(person -> person.getEmploymentType().equals(EmploymentType.CONTRACT)).collect(Collectors.toList());
        contractors.forEach(person -> out.println(person));
        assertTrue(contractors.stream().allMatch(person -> person.getEmploymentType().equals(EmploymentType.CONTRACT)));
    }

    @Test
    public void printOnlyNamesPersons(){
        out.println(" ");
        out.print("*****print only names of all person*******\n");
        List<String> personNames =  personList.stream().map(person -> person.getName()).collect(Collectors.toList());
        personNames.forEach(personName -> out.println(personName));
        assertTrue(personNames.size() == 6);

    }


    @Test
    public void printTotalOfSalaries(){
        out.println(" ");
        out.print("*****print total salaries of all persons*******\n");
        Double sumOfSalaries = personList.stream().mapToDouble(person -> person.getSalary()).sum();
        out.println(sumOfSalaries);
        assertTrue(sumOfSalaries == 19000d);

    }


    @Test
    public void printAverageOfSalaries(){
        out.println(" ");
        out.print("*****print average of salaries*******\n");
        OptionalDouble avgOfSalaries = personList.stream().mapToDouble(person -> person.getSalary() ).average();
        if(avgOfSalaries.isPresent()){
            out.println(avgOfSalaries.getAsDouble());
        }
        assertTrue(avgOfSalaries.getAsDouble() == 3166.6666666666665);
    }


    @Test
    public void printCountOfPermanentPersons(){
        out.println(" ");
        out.print("*****print count of permanent person*******\n");
        long count  =  personList.stream().filter(person -> person.getEmploymentType().equals(EmploymentType.PERMANENT)).count();
        out.println(count);
        assertTrue(count == 3);

    }



    @Test
    public void printPersonWithHighestSalary(){
        out.println(" ");
        out.print("*****print person with highest salary*******\n");
        Optional<Person> personOptional = personList.stream().max(Comparator.comparingDouble(person -> person.getSalary()));
        if(personOptional.isPresent()){
            out.println(personOptional.get());
        }
        assertTrue(personOptional.get().getName().equalsIgnoreCase("Person_5"));
    }

    @Test
    public void printPersonWithLowestSalary(){
        out.println(" ");
        out.print("*****print person with lowest salary*******\n");
        Optional<Person> personOptional = personList.stream().min(Comparator.comparingDouble(person -> person.getSalary()));
        if(personOptional.isPresent()){
            out.println(personOptional.get());
        }
        assertTrue(personOptional.get().getName().equalsIgnoreCase("Person_1"));
    }


    @Test
    public void printPersonWorkingMorningShifts(){
        out.println(" ");
        out.print("*****print person working in morning shift*******\n");
        List<Person> morningPerson = personList.stream().filter(person -> person.getShifts().contains(Shifts.MORNING)).collect(Collectors.toList());
        morningPerson.forEach(person -> out.println(person));
        assertTrue(morningPerson.size() == 3);

    }

    @Test
    public void printSalaryBonusX2(){
        out.println(" ");
        out.print("*****Salary bonus X2*******\n");
        personList.forEach(person -> person.setSalary(person.getSalary()*2));
        personList.forEach(person -> out.println(person));
        assertTrue(personList.get(0).getSalary() == 2000d);
    }



    @Test
    public void printSalaryBy2(){
        out.println(" ");
        out.print("*****Salary/2*******\n");
        personList.forEach(person -> person.setSalary(person.getSalary()/2));
        personList.forEach(person -> out.println(person));
        assertTrue(personList.get(0).getSalary() == 500d);
    }

    @Test
    public void printGroupByNumberOfShiftsPerPerson(){
        out.println(" ");
        out.print("*****group by number shifts of each person*******\n");
        Map<Integer,List<Person>> personShiftMap =  personList.stream().collect(Collectors.groupingBy(person -> person.getShifts().size()));
        personShiftMap.forEach((integer, people) -> out.println(integer+":"+people));
    }

    @Test
    public void printGroupByEmploymentType(){
        out.println(" ");
        out.print("*****group by Employment Type *******\n");
        Map<EmploymentType,List<Person>> employmentTypeListMap =  personList.stream().collect(Collectors.groupingBy(person -> person.getEmploymentType()));
        employmentTypeListMap.forEach((employmentType, people) -> out.println(employmentType+":"+people));
        assertTrue(employmentTypeListMap.get(EmploymentType.CONTRACT).size() == 3);
    }


    @Test
    public void printSumOfSalariesGroupByEmploymentType(){
        out.println(" ");
        out.print("*****sum of salaries grouped by Employment Type *******\n");
        Map<EmploymentType,Double> employmentTypeDoubleMap =  personList.stream().collect(Collectors.groupingBy(person -> person.getEmploymentType(),Collectors.summingDouble(person -> person.getSalary() )));
        employmentTypeDoubleMap.forEach((employmentType, salary) -> out.println(employmentType+":"+salary));
    }

    @Test
    public void printSalarySummary(){
        out.println(" ");
        out.print("******Using summary functions on salary *******\n");
        DoubleSummaryStatistics doubleSummaryStatistics = personList.stream().mapToDouble(person->person.getSalary()).summaryStatistics();
        out.println(doubleSummaryStatistics);
    }


    @Test
    public void printPossibleShiftsPeopleAreWorking(){
        out.println(" ");
        out.print("******Get possible shifts persons are working in *******\n");
        Set<Shifts> shiftsSet =  personList.stream().flatMap(person -> person.getShifts().stream()).collect(Collectors.toSet());
        out.println(shiftsSet);
    }

    @Test
    public void printSortedPersonBySalaries(){
        out.println(" ");
        out.print("******Sort by salaries *******\n");
        List<Person> sortedList =  personList.stream().sorted(Comparator.comparingDouble(person->person.getSalary())).collect(Collectors.toList());
        sortedList.forEach(person -> out.println(person));
    }


    @Test
    public void printSimpleExampleOfConsumerAccept(){
        out.println(" ");
        out.print("******Consumer - accepts one arg , returns nothing *******\n");
        Consumer<List<Person>> listConsumer = people -> out.println(people.get(0));
        listConsumer.accept(personList);
    }

    @Test
    public void printSimpleExampleOfConsumerAndThenAccept(){
        out.println(" ");
        out.print("******Consumer - accepts one arg , returns nothing , Consumer chaining*******\n");
        Consumer<List<Person>> firstConsumer = people -> out.println(people.get(0));
        Consumer<List<Person>> secondConsumer = people -> out.println(people.get(1));
        Consumer<List<Person>> finalConsumer = firstConsumer.andThen(secondConsumer);
        finalConsumer.accept(personList);

    }


    @Test
    public void printSimpleExampleOfSupplierGet(){
        out.println(" ");
        out.print("******Supplier - accepts no arg , returns something *******\n");
        Supplier<Person> personSupplier = ()->personList.get(0);
        out.println(personSupplier.get());
    }


    @Test
    public void printSimpleExampleOfPredicateTest(){
        out.println(" ");
        out.print("******Predicate - accepts one arg, returns true/false *******\n");
        Predicate<Double> doublePredicate = (salary)-> salary < 4000d;
        out.println(doublePredicate.test(personList.get(0).getSalary()));
    }









}
