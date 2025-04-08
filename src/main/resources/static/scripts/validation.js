// Validation patterns using regexp
const patterns = {
    name: /^[A-Za-z]+$/,                      // Only upper and lowercase letters
    username: /^[A-Za-z0-9_]+$/,              // Letters, numbers and underscore
    password: /^[A-Za-z0-9_]{8,15}$/          // Letters, numbers, underscore, 8-15 chars
};

// Get all form elements
const firstNameInput = document.getElementById('first-name');
const lastNameInput = document.getElementById('last-name');
const usernameInput = document.getElementById('username');
const passwordInput = document.getElementById('password');
const confirmPasswordInput = document.getElementById('password-confirm');
const formElement = document.querySelector('form');

// Create submit button
function createRegisterButton() {
    const registerBtn = document.createElement('input');
    registerBtn.id = 'register';
    registerBtn.type = 'submit';
    registerBtn.value = 'Register';
    return registerBtn;
}

// Validation function
function validate(field, regex) {
    if(field.value === '') {
        field.classList.remove('valid');
        field.classList.remove('invalid');
        return false;
    }
    if (regex.test(field.value)) {
        field.classList.remove('invalid');
        field.classList.add('valid');
        return true;
    } else {
        field.classList.remove('valid');
        field.classList.add('invalid');
        return false;
    }
}

// Function to check if passwords match
function checkPasswordsMatch() {
    if (passwordInput.value === '' || confirmPasswordInput.value === '') {
        confirmPasswordInput.classList.remove('valid');
        confirmPasswordInput.classList.remove('invalid');
        return false;
    }
    if (passwordInput.value === confirmPasswordInput.value &&
        passwordInput.value !== '' &&
        patterns.password.test(passwordInput.value)) {
        confirmPasswordInput.classList.remove('invalid');
        confirmPasswordInput.classList.add('valid');
        return true;
    } else {
        confirmPasswordInput.classList.remove('valid');
        confirmPasswordInput.classList.add('invalid');
        return false;
    }
}

// Function to check if all fields are valid
function checkAllValid() {
    const firstName = validate(firstNameInput, patterns.name);
    const lastName = validate(lastNameInput, patterns.name);
    const username = validate(usernameInput, patterns.username);
    const password = validate(passwordInput, patterns.password);
    const passwordsMatch = checkPasswordsMatch();

    const existingButton = document.getElementById('register');
    if (existingButton) {
        existingButton.remove();
    }

    if (firstName && lastName && username && password && passwordsMatch) {
        // Add button to DOM if all fields are valid
        formElement.appendChild(createRegisterButton());
    }
}

// Event listeners for each input field
firstNameInput.addEventListener('input', () => {
    validate(firstNameInput, patterns.name);
    checkAllValid();
});

lastNameInput.addEventListener('input', () => {
    validate(lastNameInput, patterns.name);
    checkAllValid();
});

usernameInput.addEventListener('input', () => {
    validate(usernameInput, patterns.username);
    checkAllValid();
});

passwordInput.addEventListener('input', () => {
    validate(passwordInput, patterns.password);
    checkPasswordsMatch();
    checkAllValid();
});

confirmPasswordInput.addEventListener('input', () => {
    checkPasswordsMatch();
    checkAllValid();
});

// Form submission handler
formElement.addEventListener('submit', (e) => {
    // Prevents to actually submit the form for now
    e.preventDefault();
    alert('Account creation in progress');
});

// Add validation icons
document.querySelectorAll('.form-group').forEach(group => {
    const validationIcon = document.createElement('span');
    validationIcon.className = 'validation-icon';
    group.appendChild(validationIcon);
});
