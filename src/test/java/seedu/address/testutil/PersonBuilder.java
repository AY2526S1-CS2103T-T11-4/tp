package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Category;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonFactory;
import seedu.address.model.person.PersonId;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Student;
import seedu.address.model.tag.Tag;
import seedu.address.model.tuitionclass.TuitionClass;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_CATEGORY = "student";
    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private PersonId id;
    private Category category;
    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;
    private TuitionClass tuitionClass;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        id = PersonId.newId();
        category = Category.fromString(DEFAULT_CATEGORY);
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
        tuitionClass = null; // Initialize to null
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        id = personToCopy.getId();
        category = personToCopy.getCategory();
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        tags = new HashSet<>(personToCopy.getTags());
        tuitionClass = null;
    }

    /**
     * Sets the {@code PersonId} of the {@code Person} that we are building.
     */
    public PersonBuilder withId(PersonId id) {
        this.id = id;
        return this;
    }

    /**
     * Sets the {@code Category} of the {@code Person} that we are building.
     */
    public PersonBuilder withCategory(String category) {
        this.category = Category.fromString(category);
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code TuitionClass} for the {@code Person} we are building.
     * This should only be used when building a Student.
     */
    public PersonBuilder withTuitionClass(TuitionClass tuitionClass) {
        this.tuitionClass = tuitionClass;
        return this;
    }

    /**
     * Builds and returns a {@link Person} object based on the current state of this builder.
     * If the Person is a {@link Student} and a tuition class has been set, the student's
     * tuition class will be initialized with the day and time of the assigned class.
     *
     * @return the constructed Person
     */
    public Person build() {
        Person person = PersonFactory.createPerson(id, category, name, phone, email, address, tags);

        if (person instanceof Student && this.tuitionClass != null) {
            Student student = (Student) person;
            student.setTuitionClass(tuitionClass);
        }

        return person;
    }

}
