package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Parent;
import seedu.address.model.person.Person;
import seedu.address.model.person.Student;
import seedu.address.model.person.Tutor;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.tuitionclass.TuitionClass;
import seedu.address.model.tuitionclass.UniqueClassList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueClassList tuitionClasses;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        tuitionClasses = new UniqueClassList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setTuitionClasses(newData.getTuitionClassList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    /**
     * Adds a parent to the address book.
     * The parent must not already exist in the address book.
     */
    public void addParent(Parent parent) {
        persons.add(parent);
    }

    /**
     * Adds a tutor to the address book.
     * The tutor must not already exist in the address book.
     */
    public void addTutor(Tutor tutor) {
        persons.add(tutor);
    }

    /**
     * Adds a student to the address book.
     * The student must not already exist in the address book.
     */
    public void addStudent(Student student) {
        persons.add(student);
    }

    //// TuitionClass level operations
    /**
     * Returns true if a tuition class with the same identity as {@code tuitionClass} exists in the address book.
     */
    public boolean hasTuitionClass(TuitionClass tuitionClass) {
        requireNonNull(tuitionClass);
        return tuitionClasses.contains(tuitionClass);
    }

    /**
     * Adds a tuition class to the address book.
     * The class must not already exist in the address book.
     */
    public void addTuitionClass(TuitionClass t) {
        tuitionClasses.add(t);
    }

    /**
     * Replaces the given tuition class {@code target} with {@code editedTuitionClass}.
     * {@code target} must exist in the address book.
     * The tuition class identity of {@code editedTuitionClass} must not be the same as another existing
     * tuition class in the address book.
     */
    public void setTuitionClass(TuitionClass target, TuitionClass editedTuitionClass) {
        requireNonNull(editedTuitionClass);
        tuitionClasses.setTuitionClass(target, editedTuitionClass);
    }

    public void setTuitionClasses(List<TuitionClass> tuitionClasses) {
        this.tuitionClasses.setTuitionClasses(tuitionClasses);
    }

    @Override
    public ObservableList<TuitionClass> getTuitionClassList() {
        return tuitionClasses.asUnmodifiableObservableList();
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", persons)
                .toString();
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBook)) {
            return false;
        }

        AddressBook otherAddressBook = (AddressBook) other;
        return persons.equals(otherAddressBook.persons)
                && tuitionClasses.equals(otherAddressBook.tuitionClasses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(persons, tuitionClasses);
    }
}
