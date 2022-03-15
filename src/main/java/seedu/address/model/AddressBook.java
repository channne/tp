package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.person.MasterLabList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.lab.Lab;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList students;

    private final MasterLabList labs;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        students = new UniquePersonList();
    }

    public AddressBook() {
        labs = new MasterLabList();
    }

    /**
     * Creates a TAddressBook using the Students in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the Student list with {@code persons}.
     * {@code students} must not contain duplicate Students.
     */
    public void setPersons(List<Person> students) {
        this.students.setPersons(students);
    }

    /**
     * Replaces the contents of the Lab list with {@code labs}.
     * {@code labs} must not contain duplicate Labs.
     */
    public void setLabs(MasterLabList labs) {
        this.labs.setLabs(labs);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setLabs(newData.getMasterLabList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the TAddressBook.
     */
    public boolean hasPerson(Person student) {
        requireNonNull(student);
        return students.contains(student);
    }

    /**
     * Adds a person to the TAddressBook.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        students.add(p);
    }

    /**
     * Replaces the given student {@code target} in the list with {@code editedStudent}.
     * {@code target} must exist in the TAddressBook.
     * The Student identity of {@code editedStudent} must not be the same as
     * another existing Student in the TAddressBook.
     */
    public void setPerson(Person target, Person editedStudent) {
        requireNonNull(editedStudent);

        students.setPerson(target, editedStudent);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the TAddressBook.
     */
    public void removePerson(Person key) {
        students.remove(key);
    }

    /**
     * Returns true if a Lab with the same identity as {@code lab} exists in the TAddressBook.
     */
    public boolean hasLab(Lab lab) {
        requireNonNull(lab);
        return labs.contains(lab);
    }

    /**
     * Adds a lab to the TAddressBook.
     * The lab must not already exist in the TAddressBook.
     */
    public void addLab(Lab lab) {
        labs.add(lab);
        students.addLabToAll(lab);
    }

    //// util methods

    @Override
    public String toString() {
        return students.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return students.asUnmodifiableObservableList();
    }

    @Override
    public MasterLabList getMasterLabList() {
        return labs;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && students.equals(((AddressBook) other).students));
    }

    @Override
    public int hashCode() {
        return students.hashCode();
    }
}
