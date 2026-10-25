// Author: Benjamin Roark
package contactservice; // Shares the package with Contact and its unit tests.

import java.util.HashMap; // Provides in-memory storage with lookup by ID.
import java.util.Map; // Defines the mapping from contact IDs to contact objects.

/** Stores contacts in memory and supports adding, retrieving, updating, and deleting by ID. */
public class ContactService {

    private final Map<String, Contact> contacts; // Each service owns a separate collection.

    public ContactService() {
        contacts = new HashMap<>(); // Start this service with an empty map.
    }

    public void addContact(Contact contact) {
        if (contact == null) { // Reject a missing object before reading its ID.
            throw new IllegalArgumentException("Contact cannot be null.");
        }

        String contactId = contact.getContactId(); // Use the contact's validated, immutable ID as its key.

        if (contacts.containsKey(contactId)) { // Compare String contents to prevent duplicate IDs.
            throw new IllegalArgumentException(
                    "A contact with this ID already exists.");
        }

        contacts.put(contactId, contact); // Add only after confirming the ID is available.
    }

    /** Returns the stored contact, or null when the supplied ID is not present. */
    public Contact getContact(String contactId) {
        return contacts.get(contactId); // A missing or null ID has no matching entry.
    }

    public void deleteContact(String contactId) {
        if (!contacts.containsKey(contactId)) { // Reject an unknown ID before attempting deletion.
            throw new IllegalArgumentException(
                    "No contact exists with the provided ID.");
        }

        contacts.remove(contactId); // Remove only the contact with the requested ID.
    }

    public void updateFirstName(String contactId, String firstName) {
        Contact contact = requireContact(contactId); // Require an existing target for the update.
        contact.setFirstName(firstName); // Let Contact validate and change only the first name.
    }

    public void updateLastName(String contactId, String lastName) {
        Contact contact = requireContact(contactId); // Reject a missing target before changing data.
        contact.setLastName(lastName); // Apply Contact's validation to the new last name.
    }

    public void updatePhone(String contactId, String phone) {
        Contact contact = requireContact(contactId); // Find the contact associated with the ID.
        contact.setPhone(phone); // Require exactly 10 digits before replacing the phone.
    }

    public void updateAddress(String contactId, String address) {
        Contact contact = requireContact(contactId); // Require an existing contact for the update.
        contact.setAddress(address); // Validate and change only the address.
    }

    // Share the missing-contact check across all four field update operations.
    private Contact requireContact(String contactId) {
        Contact contact = contacts.get(contactId); // Retrieve the stored object using its ID.

        if (contact == null) { // A null result means the requested contact is not stored.
            throw new IllegalArgumentException(
                    "No contact exists with the provided ID.");
        }

        return contact; // Return the existing contact for a validated field update.
    }
}
