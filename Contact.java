// Author: Benjamin Roark
package contactservice; // Groups the contact object, service, and JUnit tests.

/** A contact with an immutable ID and validated, editable personal information. */
public class Contact {

    private final String contactId; // Assigned once; no ID setter is provided.
    private String firstName; // Editable first name, limited to 10 characters.
    private String lastName; // Editable last name, limited to 10 characters.
    private String phone; // Stored as a String to preserve leading zeros.
    private String address; // Editable address, limited to 30 characters.

    // Validate every required field before storing the new contact's data.
    public Contact(String contactId, String firstName, String lastName,
                   String phone, String address) {

        validateContactId(contactId); // Enforce a non-null ID of at most 10 characters.
        validateFirstName(firstName); // Apply the same first-name rules used by later updates.
        validateLastName(lastName); // Enforce the last-name length and null restrictions.
        validatePhone(phone); // Require exactly 10 digits.
        validateAddress(address); // Accept addresses up to and including 30 characters.

        this.contactId = contactId; // Keep the validated ID fixed for service lookups.
        // Store the editable fields only after all validation succeeds.
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.address = address;
    }

    public String getContactId() {
        return contactId; // Expose the ID for lookup without allowing it to change.
    }

    public String getFirstName() {
        return firstName; // Return the current first name.
    }

    public String getLastName() {
        return lastName; // Return the current last name.
    }

    public String getPhone() {
        return phone; // Return all 10 digits, including any leading zeros.
    }

    public String getAddress() {
        return address; // Return the current mailing address.
    }

    public void setFirstName(String firstName) {
        validateFirstName(firstName); // Reject invalid input before changing the stored name.
        this.firstName = firstName; // Assign only after validation succeeds.
    }

    public void setLastName(String lastName) {
        validateLastName(lastName); // Preserve the old last name if validation fails.
        this.lastName = lastName; // Store the accepted replacement.
    }

    public void setPhone(String phone) {
        validatePhone(phone); // Check length and digits before assigning a replacement.
        this.phone = phone; // Keep the original phone when invalid input is rejected.
    }

    public void setAddress(String address) {
        validateAddress(address); // Reuse the constructor's address rules for updates.
        this.address = address; // Change the address only when the new value is valid.
    }

    private void validateContactId(String contactId) {
        // Check null first so short-circuit evaluation avoids calling length() on null.
        if (contactId == null || contactId.length() > 10) {
            throw new IllegalArgumentException(
                    "Contact ID cannot be null or longer than 10 characters.");
        }
    }

    private void validateFirstName(String firstName) {
        // Exactly 10 characters is valid; no minimum length is specified.
        if (firstName == null || firstName.length() > 10) {
            throw new IllegalArgumentException(
                    "First name cannot be null or longer than 10 characters.");
        }
    }

    private void validateLastName(String lastName) {
        // Construction and updates both reject null and names over 10 characters.
        if (lastName == null || lastName.length() > 10) {
            throw new IllegalArgumentException(
                    "Last name cannot be null or longer than 10 characters.");
        }
    }

    private void validatePhone(String phone) {
        // The pattern accepts exactly 10 ASCII digits and rejects spaces or punctuation.
        if (phone == null || !phone.matches("[0-9]{10}")) {
            throw new IllegalArgumentException(
                    "Phone number must contain exactly 10 digits.");
        }
    }

    private void validateAddress(String address) {
        // Accept the 30-character boundary while rejecting null or longer addresses.
        if (address == null || address.length() > 30) {
            throw new IllegalArgumentException(
                    "Address cannot be null or longer than 30 characters.");
        }
    }
}
