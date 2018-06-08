document.getElementById("registration-submit").addEventListener("click", formValidation);

const lettersRegex = /^[A-Za-z\u0400-\u04FF]+$/;
const emailRegex = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;  
const passRegex = /^[a-zA-Z\d\s\u0400-\u04FF]{8,}$/;

const fnameErrorMsg = "First name must have alphabet characters only!";
const lnameErrorMsg = "Last name must have alphabet characters only!";
const emailErrorMsg = "You have entered an invalid email address!";
const passwordErrorMsg = " Password should contains more then 6 symbol!";
const confirmPasswordsErrorMsg ="Password confirm should not be empty / should be equels with Password" ;

function formValidation() {
    var fname = document.forms["registration-form"]["user-fname"].value;
    var lname = document.forms["registration-form"]["user-lname"].value;
    var email = document.forms["registration-form"]["user-email"].value;
    var password = document.forms["registration-form"]["user-password"].value;
    var confirmPassword = document.forms["registration-form"]["confirm-password"].value;


    validate(fname,lettersRegex,"fname-error-msg",fnameErrorMsg);
    validate(lname,lettersRegex,"lname-error-msg",lnameErrorMsg);
    validate(email,emailRegex,"email-error-msg",emailErrorMsg);
    validate(password,passRegex,"password-error-msg",passwordErrorMsg);
    validate(confirmPassword,password,"password-confirm-error-msg",confirmPasswordsErrorMsg);
}

function validate(field, regex, lableId, errorMsg) {  
    var msgLable = document.getElementById(lableId);

    if(!field.match(regex)|| field.length == 0) { 
        msgLable.style.color = "#cc0000";
        msgLable.innerHTML = ":  " + errorMsg;
        msgLable.focus();  
        event.preventDefault();
    } else {
        msgLable.style.color = "#009900";
        msgLable.innerHTML = ": &#10003";
    } 
}


