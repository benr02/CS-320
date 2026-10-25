// Author: Benjamin Roark
package contactservice; // Tests contact storage and updates in the service's package.

import static org.junit.jupiter.api.Assertions.assertEquals; // Checks stored and updated field values.
import static org.junit.jupiter.api.Assertions.assertNotNull; // Confirms a contact was stored.
import static org.junit.jupiter.api.Assertions.assertNull; // Confirms deletion leaves no matching entry.
import static org.junit.jupiter.api.Assertions.assertThrows; // Checks that invalid operations are rejected.

import org.junit.jupiter.api.BeforeEach; // Recreates test data before each test.
import org.junit.jupiter.api.Test; // Marks an individual JUnit test method.

// Verify add, lookup, update, and delete operations using known contact data.
public class ContactServiceTest {

    private ContactService contactService; // Reset between tests so stored contacts cannot carry over.
    private Contact contact; // A valid contact available for each test to add when needed.

    @BeforeEach
    void setUp() {
        contactService = new ContactService(); // Begin each test with an empty in-memory map.
        // Construct valid data without adding it until the test requests an add.
        contact = new Contact(
                "C2001",
                "Benjamin",
                "Roark",
                "3045551234",
                "100 Main Street");
    }

    @Test
    void testAddSingleContact() {
        contactService.addContact(contact); // Store the valid contact under C2001.

        Contact savedContact = contactService.getContact("C2001"); // Retrieve it by its unique ID.

        assertNotNull(savedContact); // Confirm that the contact is available after adding it.
        assertEquals("Benjamin", savedContact.getFirstName()); // Confirm the stored data is correct.
    }

    @Test
    void testAddMultipleContacts() {
        // A second contact with a distinct ID must coexist with the first contact.
        Contact secondContact = new Contact(
                "C2002",
                "Alex",
                "Smith",
                "3045555678",
                "200 Oak Street");

        contactService.addContact(contact);
        contactService.addContact(secondContact);

        assertNotNull(contactService.getContact("C2001")); // The first entry remains available.
        assertNotNull(contactService.getContact("C2002")); // The second entry has its own ID.
    }

    @Test
    void testDuplicateContactIdIsRejected() {
        // Different contact data does not make an already-used ID available again.
        Contact duplicate = new Contact(
                "C2001",
                "Taylor",
                "Jones",
                "3045559999",
                "300 Pine Street");

        contactService.addContact(contact); // Reserve C2001 before attempting the duplicate add.

        // The service must reject another contact with the same ID.
        assertThrows(IllegalArgumentException.class, () ->
                contactService.addContact(duplicate));
    }

    @Test
    void testAddAndGetContact() {
        contactService.addContact(contact);

        Contact returnedContact = contactService.getContact("C2001"); // Look up the stored contact.

        // Confirm that adding and retrieving preserves every supplied field.
        assertEquals("C2001", returnedContact.getContactId());
        assertEquals("Benjamin", returnedContact.getFirstName());
        assertEquals("Roark", returnedContact.getLastName());
        assertEquals("3045551234", returnedContact.getPhone());
        assertEquals("100 Main Street", returnedContact.getAddress());
    }

    @Test
    void testUpdateContactFields() {
        contactService.addContact(contact);

        // Exercise all four editable fields through the service's update methods.
        contactService.updateFirstName("C2001", "Ben");
        contactService.updateLastName("C2001", "RoarkJr");
        contactService.updatePhone("C2001", "6815554321");
        contactService.updateAddress("C2001", "250 Mountain Road");

        Contact updatedContact = contactService.getContact("C2001"); // Read the contact after updates.

        // Verify that each replacement value reached the stored contact.
        assertEquals("Ben", updatedContact.getFirstName());
        assertEquals("RoarkJr", updatedContact.getLastName());
        assertEquals("6815554321", updatedContact.getPhone());
        assertEquals("250 Mountain Road", updatedContact.getAddress());
    }

    @Test
    void testInvalidUpdatedFieldIsRejected() {
        contactService.addContact(contact);

        // Five digits violates Contact's requirement for an exactly 10-digit phone.
        assertThrows(IllegalArgumentException.class, () ->
                contactService.updatePhone("C2001", "12345"));

        // Rejection must preserve the previously stored phone number.
        assertEquals("3045551234",
                contactService.getContact("C2001").getPhone());
    }

    @Test
    void testDeleteContact() {
        contactService.addContact(contact);
        contactService.deleteContact("C2001"); // Remove the contact by its ID.

        assertNull(contactService.getContact("C2001")); // Missing contacts are returned as null.
    }

    @Test
    void testDeleteMissingContactIsRejected() {
        // Deleting an ID that was never added must report an invalid operation.
        assertThrows(IllegalArgumentException.class, () ->
                contactService.deleteContact("DOESNOTEX"));
    }

    @Test
    void testUpdateMissingContactIsRejected() {
        // An update needs an existing target even when the replacement name is valid.
        assertThrows(IllegalArgumentException.class, () ->
                contactService.updateFirstName("MISSING", "Ben"));
    }
}
