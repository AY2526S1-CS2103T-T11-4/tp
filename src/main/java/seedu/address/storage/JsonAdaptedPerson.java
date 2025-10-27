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
import seedu.address.model.person.Category;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Parent;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonFactory;
import seedu.address.model.person.PersonId;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Student;
import seedu.address.model.person.Tutor;
import seedu.address.model.tag.Tag;
import seedu.address.model.tuitionclass.Day;
import seedu.address.model.tuitionclass.Time;

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
    private final JsonAdaptedClassId classId;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("id") String id, @JsonProperty("category") String category,
                             @JsonProperty("name") String name, @JsonProperty("phone") String phone,
                             @JsonProperty("email") String email, @JsonProperty("address") String address,
                             @JsonProperty("tags") List<JsonAdaptedTag> tags,
                             @JsonProperty("linkedParentId") String linkedParentId,
                             @JsonProperty("childrenIds") List<String> childrenIds,
                             @JsonProperty("classId") JsonAdaptedClassId classId) {
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
        this.classId = classId;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        id = source.getId().getValue();
        category = source.getCategory().toString();
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .toList());

        // Handle Student, Parent and TuitionClass specific fields
        if (source instanceof Student) {
            Student student = (Student) source;
            this.linkedParentId = student.getParentId() != null ? student.getParentId().getValue() : null;
            this.childrenIds = new ArrayList<>(); // Student has no children
            this.classId = student.getClassId().map(JsonAdaptedClassId::new).orElse(null);
        } else if (source instanceof Parent) {
            Parent parent = (Parent) source;
            this.linkedParentId = null; // Parent has no linked parent
            this.childrenIds = parent.getChildrenIds().stream()
                    .map(PersonId::getValue)
                    .collect(Collectors.toList());
            this.classId = null;
        } else if (source instanceof Tutor) {
            Tutor tutor = (Tutor) source;
            this.linkedParentId = null; // Tutor has no linked parent
            this.childrenIds = new ArrayList<>();
            this.classId = null;
        } else {
            // Default case for other Person types
            this.linkedParentId = null;
            this.childrenIds = new ArrayList<>();
            this.classId = null;
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
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    PersonId.class.getSimpleName()));
        }
        final PersonId modelId = PersonId.of(id);

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

        if (category == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Category.class.getSimpleName()));
        }

        Category modelCategory;
        try {
            modelCategory = Category.fromString(category);
        } catch (IllegalArgumentException ex) {
            throw new IllegalValueException("Invalid category");
        }
        Person person = PersonFactory.createPerson(modelId, modelCategory, modelName, modelPhone,
                modelEmail, modelAddress, modelTags);

        if (person instanceof Student) {
            Student student = (Student) person;
            if (classId != null) {
                student.setTuitionClass(classId.toModelType());
            }
            if (linkedParentId != null) {
                student.setParentId(PersonId.of(linkedParentId));
            }
        }
        if (person instanceof Parent && childrenIds != null) {
            Parent parent = (Parent) person;
            for (String childId : childrenIds) {
                parent.addChildId(PersonId.of(childId));
            }
        }
        return person;
    }

}
