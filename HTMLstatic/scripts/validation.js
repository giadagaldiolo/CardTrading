function validateName(name) {
    const nameRegex = /^[A-Za-z]+$/;
    return nameRegex.test(name);
}


function validateUsername(username) {
    const usernameRegex = /^[A-Za-z0-9_]+$/;
    return usernameRegex.test(username);
}


function validatePassword(password) {
    const passwordRegex = /^[A-Za-z0-9_]{8,15}$/;
    return passwordRegex.test(password);
}


function handleInputChange() {
    const firstName = document.getElementById("first-name").value;
    const lastName = document.getElementById("last-name").value;
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;
    const confirmPassword = document.getElementById("password-confirm").value;

    let isFormValid = true;

    const firstNameInput = document.getElementById("first-name");
    if (!validateName(firstName)) {
        firstNameInput.style.borderColor = "red";
        isFormValid = false;
    } else {
        firstNameInput.style.borderColor = "green";
    }

    const lastNameInput = document.getElementById("last-name");
    if (!validateName(lastName)) {
        lastNameInput.style.borderColor = "red";
        isFormValid = false;
    } else {
        lastNameInput.style.borderColor = "green";
    }

    const usernameInput = document.getElementById("username");
    if (!validateUsername(username)) {
        usernameInput.style.borderColor = "red";
        isFormValid = false;
    } else {
        usernameInput.style.borderColor = "green";
    }

    const passwordInput = document.getElementById("password");
    if (!validatePassword(password)) {
        passwordInput.style.borderColor = "red";
        isFormValid = false;
    } else {
        passwordInput.style.borderColor = "green";
    }

    const confirmPasswordInput = document.getElementById("password-confirm");
    if (password !== confirmPassword || !validatePassword(confirmPassword)) {
        confirmPasswordInput.style.borderColor = "red";
        isFormValid = false;
    } else {
        confirmPasswordInput.style.borderColor = "green";
    }

    const submitContainer = document.getElementById("submit-container");

    if (isFormValid) {
        if (!document.getElementById("submit-button")) {
            const submitButton = document.createElement("button");
            submitButton.id = "submit-button";
            submitButton.textContent = "Submit";
            submitButton.onclick = function() {
                alert("Account creation in progress");
            };
            submitContainer.appendChild(submitButton);
        }
    } else {
        const submitButton = document.getElementById("submit-button");
        if (submitButton) {
            submitContainer.removeChild(submitButton);
        }
    }
}

document.getElementById("first-name").addEventListener("input", handleInputChange);
document.getElementById("last-name").addEventListener("input", handleInputChange);
document.getElementById("username").addEventListener("input", handleInputChange);
document.getElementById("password").addEventListener("input", handleInputChange);
document.getElementById("password-confirm").addEventListener("input", handleInputChange);

handleInputChange();
