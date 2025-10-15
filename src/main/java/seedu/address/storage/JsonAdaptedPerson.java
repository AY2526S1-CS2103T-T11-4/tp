package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Parent;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonId;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Student;
import seedu.address.model.person.Tutor;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String id;
    private final String category;
    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    private final String linkedParentId;
    private final List<String> childrenIds;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("id") String id, @JsonProperty("category") String category,
        @JsonProperty("name") String name, @JsonProperty("phone") String phone,
            @JsonProperty("email") String email, @JsonProperty("address") String address,
            @JsonProperty("tags") List<JsonAdaptedTag> tags,
            @JsonProperty("linkedParentId") String linkedParentId,
            @JsonProperty("childrenIds") List<String> childrenIds) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        this.linkedParentId = linkedParentId;
        if (childrenIds != null) {
            this.childrenIds = new ArrayList<>(childrenIds);
        } else {
            this.childrenIds = new ArrayList<>();
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        id = source.getId().getValue();
        category = source.getCategory();
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));

        // Handle Student and Parent specific fields
        if (source instanceof Student) {
            Student student = (Student) source;
            this.linkedParentId = student.getParentId() != null ? student.getParentId().getValue() : null;
            this.childrenIds = new ArrayList<>(); // Student has no children
        } else if (source instanceof Parent) {
            Parent parent = (Parent) source;
            this.linkedParentId = null; // Parent has no linked parent
            this.childrenIds = parent.getChildrenIds().stream()
                    .map(PersonId::getValue)
                    .collect(Collectors.toList());
        } else {
            // Default case for Tutor or other Person types
            this.linkedParentId = null;
            this.childrenIds = new ArrayList<>();
        }
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }
        if (id == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, PersonId.class.getSimpleName()));
        }
        final PersonId modelId = new PersonId(id);

        if (category == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "category"));
        }
        final String modelCategory = category;

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        final Set<Tag> modelTags = new HashSet<>(personTags);

        Person person;
        switch(modelCategory.toLowerCase()) {
        case "student":
            Student student = new Student(modelId, modelCategory, modelName, modelPhone, modelEmail, modelAddress, modelTags);
            if (linkedParentId != null) {
                student.setParentId(new PersonId(linkedParentId));
            }
            person = student;
            break;
        case "parent":
            Parent parent = new Parent(modelId, modelCategory, modelName, modelPhone, modelEmail, modelAddress, modelTags);
            for (String childId : childrenIds) {
                parent.addChildId(new PersonId(childId));
            }
            person = parent;
            break;
        case "tutor":
            // Handle tutor similarly if it has specific fields to save
            person = new Tutor(modelId, modelCategory, modelName, modelPhone, modelEmail, modelAddress, modelTags);
            break;
        default:
            person = new Person(modelId, modelCategory, modelName, modelPhone, modelEmail, modelAddress, modelTags);
        }

        return person;
    }

}
