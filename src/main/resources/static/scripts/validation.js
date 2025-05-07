// Validation patterns using regexp
const patterns = {
    name: /^[A-Za-z]+$/,                      // Only upper and lowercase letters
    username: /^[A-Za-z0-9_]+$/,              // Letters, numbers and underscore
    password: /^[A-Za-z0-9_]{8,15}$/          // Letters, numbers, underscore, 8-15 chars
};

// Get all form elements
const form = document.querySelector('form');
const firstNameInput = document.getElementById('first-name');
const lastNameInput = document.getElementById('last-name');
const usernameInput = document.getElementById('username');
const passwordInput = document.getElementById('password');
const confirmPasswordInput = document.getElementById('password-confirm');

// Track which fields have been touched
const touchedFields = new Set();

// Update error message function
function updateErrorMessage(field, message) {
    const feedbackElement = field.nextElementSibling;
    if (feedbackElement && feedbackElement.classList.contains('invalid-feedback')) {
        feedbackElement.textContent = message;
    }
}

// Validation function
function validate(field, regex, fieldType) {
    // Only validate if the field has been touched or is being submitted
    if (!touchedFields.has(field) && !form.classList.contains('was-validated')) {
        field.classList.remove('is-invalid');
        field.classList.remove('is-valid');
        return false;
    }

    if (field.value === '') {
        field.classList.remove('is-valid');
        field.classList.add('is-invalid');
        updateErrorMessage(field, `Please enter your ${fieldType}.`);
        return false;
    }

    const isValid = regex.test(field.value);
    field.classList.toggle('is-invalid', !isValid);
    field.classList.toggle('is-valid', isValid);

    if (!isValid) {
        let errorMessage = '';
        switch(fieldType) {
            case 'first name':
            case 'last name':
                errorMessage = `Please enter your ${fieldType} using only letters.`;
                break;
            case 'username':
                errorMessage = 'Username can only contain letters, numbers, and underscores.';
                break;
            case 'password':
                errorMessage = 'Password must be 8-15 characters long and can only contain letters, numbers, and underscores.';
                break;
        }
        updateErrorMessage(field, errorMessage);
    }

    return isValid;
}

// Function to check if passwords match
function checkPasswordsMatch() {
    // Only validate if the field has been touched or is being submitted
    if (!touchedFields.has(confirmPasswordInput) && !form.classList.contains('was-validated')) {
        confirmPasswordInput.classList.remove('is-invalid');
        confirmPasswordInput.classList.remove('is-valid');
        return false;
    }

    if (passwordInput.value === '' || confirmPasswordInput.value === '') {
        confirmPasswordInput.classList.remove('is-valid');
        confirmPasswordInput.classList.add('is-invalid');
        updateErrorMessage(confirmPasswordInput, 'Please confirm your password.');
        return false;
    }

    if (!patterns.password.test(passwordInput.value)) {
        confirmPasswordInput.classList.remove('is-valid');
        confirmPasswordInput.classList.add('is-invalid');
        updateErrorMessage(confirmPasswordInput, 'Password must be 8-15 characters long and can only contain letters, numbers, and underscores.');
        return false;
    }

    const passwordsMatch = passwordInput.value === confirmPasswordInput.value;

    confirmPasswordInput.classList.toggle('is-invalid', !passwordsMatch);
    confirmPasswordInput.classList.toggle('is-valid', passwordsMatch);

    if (!passwordsMatch) {
        updateErrorMessage(confirmPasswordInput, 'Passwords do not match.');
    }

    return passwordsMatch;
}

// Function to check if all fields are valid
function checkAllValid() {
    const firstName = validate(firstNameInput, patterns.name, 'first name');
    const lastName = validate(lastNameInput, patterns.name, 'last name');
    const username = validate(usernameInput, patterns.username, 'username');
    const password = validate(passwordInput, patterns.password, 'password');
    const passwordsMatch = checkPasswordsMatch();

    return firstName && lastName && username && password && passwordsMatch;
}

// Event listeners for each input field
firstNameInput.addEventListener('input', () => {
    touchedFields.add(firstNameInput);
    validate(firstNameInput, patterns.name, 'first name');
    checkAllValid();
});

lastNameInput.addEventListener('input', () => {
    touchedFields.add(lastNameInput);
    validate(lastNameInput, patterns.name, 'last name');
    checkAllValid();
});

usernameInput.addEventListener('input', () => {
    touchedFields.add(usernameInput);
    validate(usernameInput, patterns.username, 'username');
    checkAllValid();
});

passwordInput.addEventListener('input', () => {
    touchedFields.add(passwordInput);
    validate(passwordInput, patterns.password, 'password');
    checkPasswordsMatch();
    checkAllValid();
});

confirmPasswordInput.addEventListener('input', () => {
    touchedFields.add(confirmPasswordInput);
    checkPasswordsMatch();
    checkAllValid();
});

// Form submission handler
form.addEventListener('submit', (e) => {
    e.preventDefault();

    if (!checkAllValid()) {
        e.stopPropagation();
        form.classList.add('was-validated');
        return;
    }

    // If all validations pass, you can proceed with form submission
    alert('Account creation in progress');
    // form.submit(); // Uncomment this when you're ready to actually submit the form
});

// Add validation icons
document.querySelectorAll('.form-group').forEach(group => {
    const validationIcon = document.createElement('span');
    validationIcon.className = 'validation-icon';
    group.appendChild(validationIcon);
});
