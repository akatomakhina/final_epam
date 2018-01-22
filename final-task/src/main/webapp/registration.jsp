<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    <%@ include file="css/reset.css" %>
    <%@ include file="css/styles.css" %>
    <%@ include file="css/media.css" %>
    <%@ include file="js/validation.js" %>
</style>
<html>
<head>
    <title>Title</title>

</head>
<body>

<div class="wrapper">

    <div class="header">
        <div class="header__container">
            <div class="header__logo">
                <a class="logo__href" href="form.html"></a>
            </div>
            <div class="header__right">
                <p><a href="#">Войти</a></p>
                <p><a href="#">Зарегистрироваться</a></p>
            </div>
        </div>
    </div>


    <div class = "menu__container">
        <ul class="menu">
            <li class="menu__item"><p><a class="main__href" href="form.html"><b>Главная</b></a></p></li>
            <li class="menu__item"><p><a class="oder__href" href="#">Товары</a></p>
                <ul class="sub__menu">
                    <li> <p><a href="#">Товары для детей</a></p> </li>
                    <li> <p><a href="#">Техника</a></p> </li>
                    <li> <p><a href="#">Аксессуары</a></p> </li>
                    <li> <p><a href="#">Одежда для женщин</a></p> </li>
                    <li> <p><a href="#">Одежда для мужчин</a></p> </li>
                    <li> <p><a href="#">Обувь</a></p> </li>
                </ul>
            </li>
            <li class="menu__item"><p><a class="oder__href" href="#">Доставка</a></p></li>
            <li class="menu__item"><p><a class="oder__href" href="#">Профиль</a></p></li>
            <li class="menu__item"><p><a class="oder__href" href="#">Контакты</a></p></li>
        </ul>
    </div>


    <div class="free-registration">
        <div class="container">
            <div class="registration">
                <div class="title">
                    <p>Пожалуйста, заполните форму регистрации</p>
                </div>
                <div class="registration__form">
                    <form name="userForm" method="POST" onsubmit="return sendForm()">
                        <input name="userName" class="registration__input" placeholder="Имя пользователя" required><br>
                        <div id="userNameHint" class="userNameHint hide check">Имя должно включать латинские буквы, цифры,  первый символ – латинская буква, количество символов не менее 5.</div>

                        <input name="userName" class="registration__input" placeholder="Фамилия пользователя" required><br>
                        <div id="userNameHint" class="userNameHint hide check">Фамилия должна включать латинские буквы, цифры,  первый символ – латинская буква, количество символов не менее 5.</div>

                        <input name="userName" class="registration__input" placeholder="Логин пользователя" required><br>
                        <div id="userNameHint" class="userNameHint hide check">Логин должен включать латинские буквы, цифры,  первый символ – латинская буква, количество символов не менее 5.</div>

                        <input name="userEmail" class="registration__input" placeholder="Email" required><br>
                        <div id="optionalEmails" class="optionalEmails"></div>

                        <div id="userEmailHint" class="userEmailHint hide check">Проверьте e-mail.</div>
                        <button class="add__button" type="button" onclick="addEmail()">Добавить Email</button>
                        <input name="userPassword" class="registration__input" placeholder="Пароль" required><br>
                        <input name="userPasswordConfirmation" class="registration__input" placeholder="Повторите пароль" required><br>
                        <div id="userPasswordHint" class="userPasswordHint hide check">Пароль должен включать не менее 6 символов, не менее одной буквы в каждом регистре и не менее одной цифры.</div>
                        <button class="registration__button">Отправить</button>
                    </form>
                </div>
            </div>
        </div>
    </div>


    <div class="container">
        <ul class="loader">
            <li class="line"></li>
            <li class="line"></li>
            <li class="line"></li>
        </ul>
    </div>


    <div class="footer">
        <div class="container">
            <div class="footer__left">
                <p>© 2018 «Shop»</p>
                <p>+495 884-0005, info@shop.ru</p>
            </div>
            <div class="footer__right">
                <p><a href="#">Политика конфеденциальности</a></p>
                <p><a href="#">Условия использования</a></p>
            </div>
        </div>
    </div>

</div>
</body>
</html>
