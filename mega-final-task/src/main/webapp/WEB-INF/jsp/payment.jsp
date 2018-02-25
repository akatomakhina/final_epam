<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<fmt:bundle basename="i18n">
    <fmt:message key="payment.label.payment" var="payment"/>
    <fmt:message key="payment.label.cardNumber" var="cardNumber"/>
    <fmt:message key="payment.label.date" var="date"/>
    <fmt:message key="payment.button.process" var="process"/>
    <fmt:message key="cart.label.totalAmount" var="totalAmount"/>
    <fmt:message key="order.label.payByBalance" var="payByBalance"/>
    <fmt:message key="payment.label.notEnoughMoney" var="notEnoughMoney"/>
    <fmt:message key="user.label.balance" var="inBalanceLabel"/>

    <fmt:message key="user.label.addBalance" var="add"/>
    <fmt:message key="month.month" var="month"/>
    <fmt:message key="month.year" var="year"/>

    <c:if test="${not empty cardNumEmpty}">
        <fmt:message key="cart.message.cardNumEmpty" var="cardNumEmptyMsg"/>
    </c:if>
    <c:if test="${not empty invalidCard}">
        <fmt:message key="cart.message.invalidCard" var="invalidCardMsg"/>
    </c:if>
    <c:if test="${not empty cvcEmpty}">
        <fmt:message key="cart.message.cvcEmpty" var="cvcEmptyMsg"/>
    </c:if>
    <c:if test="${not empty invalidCVC}">
        <fmt:message key="cart.message.invalidCVC" var="invalidCVCMsg"/>
    </c:if>
    <fmt:message key="month.jan" var="jan"/>
    <fmt:message key="month.feb" var="feb"/>
    <fmt:message key="month.mar" var="mar"/>
    <fmt:message key="month.apr" var="apr"/>
    <fmt:message key="month.may" var="may"/>
    <fmt:message key="month.jun" var="jun"/>
    <fmt:message key="month.jul" var="jul"/>
    <fmt:message key="month.aug" var="aug"/>
    <fmt:message key="month.sep" var="sep"/>
    <fmt:message key="month.oct" var="oct"/>
    <fmt:message key="month.nov" var="nov"/>
    <fmt:message key="month.dec" var="dec"/>

</fmt:bundle>

<t:genericpage>
    <jsp:attribute name="content">

        <div class="payment container">
            <div class="payment-title">
                 ${payment}
            </div>

            <div class="payment-summa">
                 ${totalAmount}: <fmt:formatNumber value="${orderTotalAmount}" type="currency" currencySymbol="$"/>
            </div>

            <form id="simplify-payment-form" action="<c:url value="/nk/payment"/>" method="POST">
                <div class="payment__add payment__cart-number">
                    ${add} <span class="weight">${cardNumber}</span>:
                    <input placeholder="${cardNumber}" type="text" name="cc-number" class="payment__input" maxlength="20" autocomplete="off" value="ex:4222222222222" autofocus>
                    <span class="error">${cardNumEmptyMsg}${invalidCardMsg}</span>
                </div>

                <div class="payment__add payment__cvc">
                    ${add} <span class="weight">CVC</span>:
                    <input placeholder="CVC"  name="cc-cvc" type="text" maxlength="4" autocomplete="off" value="ex:2222" class="payment__input">
                    <span class="error"> ${cvcEmptyMsg}${invalidCVCMsg}</span>
                </div>

                <div class="payment__add payment__yeahr">
                    <span class="weight">${date}</span>:
                    <select id="cc-exp-month" class="payment-month__list">
                        <option disabled="" selected="">${month}</option>
                        <option value="01">${jan}</option>
                        <option value="02">${feb}</option>
                        <option value="03">${mar}</option>
                        <option value="04">${apr}</option>
                        <option value="05">${may}</option>
                        <option value="06">${jun}</option>
                        <option value="07">${jul}</option>
                        <option value="08">${aug}</option>
                        <option value="09">${sep}</option>
                        <option value="10">${oct}</option>
                        <option value="11">${nov}</option>
                        <option value="12">${dec}</option>
                    </select>

                    <select id="cc-exp-year" class="payment-yeahr__list">
                        <option disabled="" selected="">${year}</option>
                        <option value="13">2015</option>
                        <option value="14">2016</option>
                        <option value="15">2017</option>
                        <option value="16">2018</option>
                        <option value="17">2019</option>
                        <option value="18">2020</option>
                        <option value="19">2021</option>
                        <option value="20">2022</option>
                        <option value="21">2023</option>
                        <option value="22">2024</option>
                    </select>
                </div>
                <button id="process-payment-btn" type="submit" class="payment__button">${process}</button>
            </form>

            <div class="payment-balance">
                ${inBalanceLabel}: <fmt:formatNumber value="${userBalance}" type="currency" currencySymbol="$"/></br>
            </div>

            <c:remove var="userBalance"/>
            <c:if test="${not empty allowBalance}">
            <form action="<c:url value="/nk/payment"/>" method="POST">
                <input type="hidden" name="byBalance" value="byBalance" class="payment-balance">
                <input type="submit" class="payment__button" value="${payByBalance}">
            </form>
            </c:if>
                <c:if test="${empty allowBalance}">
                <span class="payment-balance">${notEnoughMoney}</span>
            </c:if>
            <c:remove var="allowBalance" scope="session"/>
        </div>

  </jsp:attribute>
</t:genericpage>