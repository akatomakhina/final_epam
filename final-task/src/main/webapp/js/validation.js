let optionalEmailsNumber = 0;

function validateUserForm() {
  let userForm = document.forms[`userForm`];
  let isValidForm = false;

  let name = userForm["userName"].value;
  let acceptedCharsForName =
    "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_";

  function validateName(name, acceptedCharsForName) {
    let isValidName = true;
    name.split("").forEach(char => {
      if (acceptedCharsForName.indexOf(char) === -1) {
        isValidName = false;
        return;
      }
    });
    let isLetterFirst = acceptedCharsForName.indexOf(name[0]) < 51;
    let isSizeBiggerThanFive = name.length > 5;
    return isValidName && isLetterFirst && isSizeBiggerThanFive;
  }
  let isValidName = validateName(name, acceptedCharsForName);

  let password = userForm["userPassword"].value;
  let passwordConfirmation = userForm["userPasswordConfirmation"].value;
  let acceptedCharsForPassword =
    "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
  function validatePassword(
    password,
    passwordConfirmation,
    acceptedCharsForPassword
  ) {
    let isEquals = password === passwordConfirmation;
    let isValidPassword = true;
    let haveUpperCaseLetter = false;
    let haveLowerCaseLetter = false;
    let haveNumber = false;
    let isBiggerThanSix = password.split("").length >= 6;
    if (!isEquals) {
      return isEquals;
    } else {
      password.split("").forEach(char => {
        if (acceptedCharsForPassword.indexOf(char) !== -1) {
          if (acceptedCharsForPassword.indexOf(char) >= 25) {
            if (acceptedCharsForPassword.indexOf(char) <= 51) {
              haveLowerCaseLetter = true;
            } else {
              haveNumber = true;
            }
          } else {
            haveUpperCaseLetter = true;
          }
        } else {
          isValidPassword = false;
          return;
        }
      });
    }
    return (
      isValidPassword &&
      haveLowerCaseLetter &&
      haveUpperCaseLetter &&
      haveNumber &&
      isBiggerThanSix
    );
  }
  let isValidPassword = validatePassword(
    password,
    passwordConfirmation,
    acceptedCharsForPassword
  );

  let email = userForm["userEmail"].value;
  function validateEmail(email) {
    return (
      email.split("").indexOf("@") !== -1 && email.split("").indexOf(".") !== -1
    );
  }
  let isValidEmail = validateEmail(email);

  let optionalEmails = document.getElementById("optionalEmails");
  function validateOptionalEmails() {
    if(optionalEmailsNumber === 0 ) return true;
    for(let i = 0; i < optionalEmailsNumber; i++ ) {
      if(!validateEmail(document.getElementById("userEmailAdditional" + i).value)) {
        return false;
      }
    }
    return true;
  }
  let isValidOptionalEmails = validateOptionalEmails();

  if (isValidName) {
    document.getElementById("userNameHint").classList.remove("show");
    document.getElementById("userNameHint").classList.add("hide");
  } else {
    document.getElementById("userNameHint").classList.remove("hide");
    document.getElementById("userNameHint").classList.add("show");
  }

  if (isValidEmail && isValidOptionalEmails) {
    document.getElementById("userEmailHint").classList.remove("show");
    document.getElementById("userEmailHint").classList.add("hide");
  } else {
    document.getElementById("userEmailHint").classList.remove("hide");
    document.getElementById("userEmailHint").classList.add("show");
  }

  if (isValidPassword) {
    document.getElementById("userPasswordHint").classList.remove("show");
    document.getElementById("userPasswordHint").classList.add("hide");
  } else {
    document.getElementById("userPasswordHint").classList.remove("hide");
    document.getElementById("userPasswordHint").classList.add("show");
  }

  isValidForm = isValidName && isValidEmail && isValidAge && isValidPassword && isValidOptionalEmails;
  return isValidForm;
}

function addEmail() {
  let optionalEmailsWrapper = document.getElementById("optionalEmails");
  let newEmailWrapper = document.createElement("div");
  let newDeleteOptionalEmail = document.createElement("button");
  let newEmail = document.createElement("input");

  newEmailWrapper.id = "newEmailWrapper" + optionalEmailsNumber;
  newEmailWrapper.classList.add('newEmailWrapper');

  newDeleteOptionalEmail.name = "deleteNewEmail" + optionalEmailsNumber;
  newDeleteOptionalEmail.type = "button";
  newDeleteOptionalEmail.innerText = "Delete";
  newDeleteOptionalEmail.classList.add('delete__option');
  newDeleteOptionalEmail.onclick = (function () {
    let currentEmailNumber = optionalEmailsNumber;
    return function () {
      let currentEmail = document.getElementById("newEmailWrapper" + currentEmailNumber);
      document.getElementById('optionalEmails').removeChild(currentEmail);
      optionalEmailsNumber--;
    }
  })();
  newEmail.classList.add("registration__input");
  newEmail.placeholder = "Email";
  newEmail.id = "userEmailAdditional" + optionalEmailsNumber++;
  newEmailWrapper.appendChild(newEmail);
  newEmailWrapper.appendChild(newDeleteOptionalEmail);
  optionalEmailsWrapper.appendChild(newEmailWrapper);
}

function sendForm() {
  document.getElementById("userNameHint").classList.add("show");
  if (validateUserForm()) {
    console.log("yes");
    return true;
  } else {
    console.log("no");
    return false;
  }
}
