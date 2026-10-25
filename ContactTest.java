// Author: Benjamin Roark
package contactservice; // Tests the Contact object in its own package.

import static org.junit.jupiter.api.Assertions.assertEquals; // Checks stored values and field lengths.
import static org.junit.jupiter.api.Assertions.assertThrows; // Checks that invalid inputs are rejected.

import org.junit.jupiter.api.Test; // Marks individual JUnit test methods.

// Verify constructor data, required fields, and the exact length boundaries.
public class ContactTest {

    @Test
    void testCreateValidContact() {
        // Arrange valid values for every required contact field.
        Contact contact = new Contact(
                "C1001",
                "Benjamin",
                "Roark",
                "3045551234",
                "100 Main Street");

        // Verify that construction stores every supplied value without alteration.
        assertEquals("C1001", contact.getContactId());
        assertEquals("Benjamin", contact.getFirstName());
        assertEquals("Roark", contact.getLastName());
        assertEquals("3045551234", contact.getPhone());
        assertEquals("100 Main Street", contact.getAddress());
    }

    @Test
    void testContactIdCannotBeTooLong() {
        // An 11-character ID exceeds the 10-character maximum by one.
        assertThrows(IllegalArgumentException.class, () ->
                new Contact(
                        "12345678901",
                        "Benjamin",
                        "Roark",
                        "3045551234",
                        "100 Main Street"));
    }

    @Test
    void testContactIdCannotBeNull() {
        // A missing ID must fail even when all other fields are valid.
        assertThrows(IllegalArgumentException.class, () ->
                new Contact(
                        null,
                        "Benjamin",
                        "Roark",
                        "3045551234",
                        "100 Main Street"));
    }

    @Test
    void testFirstNameCannotBeTooLong() {
        // A first name longer than 10 characters must be rejected.
        assertThrows(IllegalArgumentException.class, () ->
                new Contact(
                        "C1002",
                        "Christopher11",
                        "Roark",
                        "3045551234",
                        "100 Main Street"));
    }

    @Test
    void testFirstNameCannotBeNull() {
        // Null is invalid for the required first-name field.
        assertThrows(IllegalArgumentException.class, () ->
                new Contact(
                        "C1002",
                        null,
                        "Roark",
                        "3045551234",
                        "100 Main Street"));
    }

    @Test
    void testLastNameCannotBeTooLong() {
        // Apply the same 10-character maximum to the last name.
        assertThrows(IllegalArgumentException.class, () ->
                new Contact(
                        "C1003",
                        "Benjamin",
                        "Montgomery12",
                        "3045551234",
                        "100 Main Street"));
    }

    @Test
    void testLastNameCannotBeNull() {
        // A valid first name does not make a null last name acceptable.
        assertThrows(IllegalArgumentException.class, () ->
                new Contact(
                        "C1003",
                        "Benjamin",
                        null,
                        "3045551234",
                        "100 Main Street"));
    }

    @Test
    void testPhoneMustBeExactlyTenDigits() {
        // Nine digits is one below the required phone length.
        assertThrows(IllegalArgumentException.class, () ->
                new Contact(
                        "C1004",
                        "Benjamin",
                        "Roark",
                        "304555123",
                        "100 Main Street"));

        // Eleven digits is one above the required phone length.
        assertThrows(IllegalArgumentException.class, () ->
                new Contact(
                        "C1004",
                        "Benjamin",
                        "Roark",
                        "30455512345",
                        "100 Main Street"));
    }

    @Test
    void testPhoneCannotContainNonDigits() {
        // A 10-character phone still fails when it includes letters.
        assertThrows(IllegalArgumentException.class, () ->
                new Contact(
                        "C1005",
                        "Benjamin",
                        "Roark",
                        "30455512AB",
                        "100 Main Street"));
    }

    @Test
    void testPhoneCannotBeNull() {
        // Every contact must supply a non-null phone number.
        assertThrows(IllegalArgumentException.class, () ->
                new Contact(
                        "C1005",
                        "Benjamin",
                        "Roark",
                        null,
                        "100 Main Street"));
    }

    @Test
    void testAddressCannotBeTooLong() {
        // A 31-character address is one character over the permitted maximum.
        assertThrows(IllegalArgumentException.class, () ->
                new Contact(
                        "C1006",
                        "Benjamin",
                        "Roark",
                        "3045551234",
                        "1234567890123456789012345678901"));
    }

    @Test
    void testAddressCannotBeNull() {
        // Reject a missing address while keeping all other inputs valid.
        assertThrows(IllegalArgumentException.class, () ->
                new Contact(
                        "C1006",
                        "Benjamin",
                        "Roark",
                        "3045551234",
                        null));
    }

    @Test
    void testMaximumAllowedFieldLengths() {
        // Exercise exactly 10 characters for IDs and names, 10 phone digits, and 30 address characters.
        Contact contact = new Contact(
                "1234567890",
                "1234567890",
                "1234567890",
                "1234567890",
                "123456789012345678901234567890");

        // Values at each maximum must be accepted and stored without truncation.
        assertEquals(10, contact.getContactId().length());
        assertEquals(10, contact.getFirstName().length());
        assertEquals(10, contact.getLastName().length());
        assertEquals(10, contact.getPhone().length());
        assertEquals(30, contact.getAddress().length());
    }
}
