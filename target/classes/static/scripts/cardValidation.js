// Validation patterns for text fields
const patterns = {
    cname: /^[A-Za-z0-9\s\-']{2,50}$/, // Nome della carta: lettere, numeri, spazi, apostrofi
    author: /^[A-Za-z\s\-']{2,50}$/,   // Autore: lettere, spazi, trattini
    description: /^.{10,500}$/         // Descrizione: minimo 10 caratteri
};

// Get form and inputs
const form = document.querySelector('form');
const cnameInput = document.getElementById('name');
const authorInput = document.getElementById('author');
const dateInput = document.getElementById('date');
const conditionSelect = document.getElementById('condition');
const typeSelect = document.getElementById('type');
const priceInput = document.getElementById('price');
const descriptionInput = document.getElementById('description');
const imageInput = document.getElementById('image');

const touchedFields = new Set();

// Generic error update
function updateErrorMessage(field, message) {
    const feedback = field.nextElementSibling;
    if (feedback && feedback.classList.contains('invalid-feedback')) {
        feedback.textContent = message;
    }
}

// Text + Regex validator
function validateText(field, regex, fieldName) {
    if (!touchedFields.has(field) && !form.classList.contains('was-validated')) return false;

    if (field.value.trim() === '') {
        field.classList.add('is-invalid');
        field.classList.remove('is-valid');
        updateErrorMessage(field, `Please enter the ${fieldName}.`);
        return false;
    }

    const valid = regex.test(field.value.trim());
    field.classList.toggle('is-valid', valid);
    field.classList.toggle('is-invalid', !valid);

    if (!valid) {
        updateErrorMessage(field, `Invalid ${fieldName}.`);
    }

    return valid;
}

// Select validator
function validateSelect(field, fieldName) {
    if (!touchedFields.has(field) && !form.classList.contains('was-validated')) return false;

    const valid = field.value !== '';
    field.classList.toggle('is-valid', valid);
    field.classList.toggle('is-invalid', !valid);

    if (!valid) {
        updateErrorMessage(field, `Please select a ${fieldName}.`);
    }

    return valid;
}

// Date validator
function validateDate(field) {
    if (!touchedFields.has(field) && !form.classList.contains('was-validated')) return false;

    const valid = field.value !== '';
    field.classList.toggle('is-valid', valid);
    field.classList.toggle('is-invalid', !valid);

    if (!valid) {
        updateErrorMessage(field, 'Please select a date.');
    }

    return valid;
}

// Price validator
function validatePrice(field) {
    if (!touchedFields.has(field) && !form.classList.contains('was-validated')) return false;

    const price = parseFloat(field.value);
    const valid = !isNaN(price) && price >= 0;

    field.classList.toggle('is-valid', valid);
    field.classList.toggle('is-invalid', !valid);

    if (!valid) {
        updateErrorMessage(field, 'Please enter a valid price (e.g. 10.00).');
    }

    return valid;
}

// Image validator
function validateImage(field) {
    if (!touchedFields.has(field) && !form.classList.contains('was-validated')) return false;

    // Se nessun file è stato caricato, è comunque valido (opzionale)
    if (field.files.length === 0) {
        field.classList.remove('is-invalid');
        field.classList.remove('is-valid');
        return true;
    }

    // Se presente, controlla che sia un'immagine
    const file = field.files[0];
    const valid = file.type.startsWith('image/');

    field.classList.toggle('is-valid', valid);
    field.classList.toggle('is-invalid', !valid);

    if (!valid) {
        updateErrorMessage(field, 'Please upload a valid image file.');
    }

    return valid;
}


// Description validator
function validateDescription(field) {
    if (!touchedFields.has(field) && !form.classList.contains('was-validated')) return false;

    const valid = patterns.description.test(field.value.trim());
    field.classList.toggle('is-valid', valid);
    field.classList.toggle('is-invalid', !valid);

    if (!valid) {
        updateErrorMessage(field, 'Please enter a description of at least 10 characters.');
    }

    return valid;
}

// Validate all fields
function checkAllValid() {
    const v1 = validateText(cnameInput, patterns.cname, 'card name');
    const v2 = validateText(authorInput, patterns.author, 'author');
    const v3 = validateDate(dateInput);
    const v4 = validateSelect(conditionSelect, 'condition');
    const v5 = validateSelect(typeSelect, 'card type');
    const v6 = validatePrice(priceInput);
    const v7 = validateDescription(descriptionInput);
    const v8 = validateImage(imageInput);

    return v1 && v2 && v3 && v4 && v5 && v6 && v7 && v8;
}

// Attach input listeners
[
    [cnameInput, validateText, patterns.cname, 'card name'],
    [authorInput, validateText, patterns.author, 'author'],
    [dateInput, validateDate],
    [conditionSelect, validateSelect, 'condition'],
    [typeSelect, validateSelect, 'card type'],
    [priceInput, validatePrice],
    [descriptionInput, validateDescription],
    [imageInput, validateImage]
].forEach(([field, fn, ...args]) => {
    field.addEventListener('input', () => {
        touchedFields.add(field);
        fn(field, ...args);
        checkAllValid();
    });
    field.addEventListener('change', () => {
        touchedFields.add(field);
        fn(field, ...args);
        checkAllValid();
    });
});

// Form submit
form.addEventListener('submit', (e) => {
    if (!checkAllValid()) {
        e.preventDefault();
        e.stopPropagation();
        form.classList.add('was-validated');
    }
});
