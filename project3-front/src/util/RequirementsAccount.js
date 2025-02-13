let PASSWORD_REGEX = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[~`!@#$%^&*()\-_+={}[\]|;:<>,./?]).{8,}$/;
let EMAIL_REGEX = /^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$/;

let regexEmail = new RegExp(EMAIL_REGEX);
let regexPassword = new RegExp(PASSWORD_REGEX);

export function RequirementsEmail(test_string) {
    return regexEmail.test(test_string);
}

export function RequirementsPassword(testPassword) {
    return regexPassword.test(testPassword);
}