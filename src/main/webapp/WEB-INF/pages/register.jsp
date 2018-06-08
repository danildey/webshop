<%@ taglib tagdir="/WEB-INF/tags" prefix="info" %>
<div class="container">
    <jsp:include page="/sidebar"/>
    <div class="register">
        <form id="registration-form" method="post" action="register" enctype="multipart/form-data">
            <div class="register-top-grid">
                <h3>PERSONAL INFORMATION</h3>
                <div class="mation">
                    <span>First Name
                        <label id="fname-error-msg">&nbsp;${errors['fname-error-msg']}</label>
                    </span>
                    <input type="text" name="user-fname" value="${sessionScope.firstName}">

                    <span>Last Name
                        <label id="lname-error-msg">&nbsp;${errors['lname-error-msg']}</label>
                    </span>
                    <input type="text" name="user-lname" value="${sessionScope.lastName}">

                    <span>Email Address
                        <label id="email-error-msg">&nbsp;${errors['email-error-msg']}</label>
                    </span>
                    <input type="text" name="user-email" value="${sessionScope.email}">
                </div>
                <div class="clearfix"></div>
                <a class="news-letter" href="#">
                    <label class="checkbox"><input type="checkbox" name="checkbox" checked=""><i> </i>Sign Up</label>
                </a>
            </div>
            <div class="register-bottom-grid">
                <h3>LOGIN INFORMATION</h3>
                <div class="mation">
                    <span>Password
                        <label id="password-error-msg">&nbsp;${errors['password-error-msg']}</label>
                    </span>
                    <input type="password" name="user-password">

                    <span>Confirm Password
                        <label id="password-confirm-error-msg">&nbsp;${errors['password-confirm-error-msg']}</label>
                    </span>
                    <input type="password" name="confirm-password">

                    <span>Image
                        <label id="avatar-error-msg">&nbsp;${errors['avatar-error-msg']}</label>
                    </span>
                    <input id="registration-image" type="file" name="avatar"/>

                    <span>Captcha
                        <label id="confirm-captcha-error-msg">&nbsp;${errors['confirm-captcha-error-msg']}</label>
                    </span>

                    <info:captcha captchaId="${CaptchaId}" image="${captchaImage}"/>

                    <input type="text" name="confirm-captcha">
                </div>
                <c:remove var="errors" scope="session"/>
                <input id="registration-submit" type="submit" value="submit">
                <span>
                    <label id="create-user-error-msg">${errors['create-user-error-msg']}</label>
                </span>


            </div>
        </form>
    </div>

    <!--<script src="js/validation.js"></script>-->
    <!--<script src="js/jquery.validation.js"></script>-->
    <div class="clearfix"></div>

</div>



