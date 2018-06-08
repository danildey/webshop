<div class="container">
    <jsp:include page="/sidebar"/>
    <div class="account_grid">
        <div class=" login-right">
            <h3>REGISTERED CUSTOMERS</h3>
            <p>If you have an account with us, please log in.</p>
            <form method="post" action="login">
                <div>
                    <span>Email
                        <label id="email-error-msg">&nbsp;${errors['email-error-msg']}</label>
                    </span>
                    <input type="text" name="user-email">
                </div>
                <div>
                    <span>Password
                        <label id="password-error-msg">&nbsp;${errors['password-error-msg']}</label>
                    </span>
                    <input type="password" name="user-password">
                </div>
                <c:remove var="errors" scope="session"/>
                <input type="submit" value="Login">
                <label id="access-error-msg">&nbsp;${errors['access-error-msg']}</label><br>
                <a class="forgot" href="#">Forgot Your Password?</a>
            </form>
        </div>
        <div class=" login-left">
            <h3>NEW CUSTOMERS</h3>
            <p>By creating an account with our store, you will be able to move through the checkout process faster,
                store multiple shipping addresses, view and track your orders in your account and more.</p>
            <a class="acount-btn" href="pages/register">Create an Account</a>
        </div>
        <div class="clearfix"></div>
    </div>
    <div class="clearfix"></div>
</div>

