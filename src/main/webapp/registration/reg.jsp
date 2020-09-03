<%--
  Created by IntelliJ IDEA.
  User: Denis
  Date: 27.08.2020
  Time: 20:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="model.Candidate" %>
<%@ page import="store.PsqlStore" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
            crossorigin="anonymous"></script>
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <title>Работа мечты</title>

    <script>
        function validate() {
            var email = $('#email').val();
            var password = $('#pass').val();
            var name = $('#name').val();
            var copy_pass = $('#copy_pass').val();
            if (email == '' || password == '' || name == '' || copy_pass == '') {
                alert("Please fill form field");
                return false;
            }
            return true;
        }

        function sendPost() {
            if (validate()) {
                $.ajax({
                    type: 'POST',
                    url: '<%=request.getContextPath()%>/reg.do',
                    data: $('#form').serialize(),
                    dataType: 'json',
                }).done(function (data) {
                    alert(data['answer']);
                }).fail(function (err) {
                    alert(err);
                });
            }
        }
    </script>
</head>
<body>
<div class="container pt-3">

    <div class="row">
        <ul class="nav">
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/posts.do">Вакансии</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/candidates.do">Кандидаты</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/post/edit.jsp">Добавить вакансию</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/candidate/edit.jsp">Добавить кандидата</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/login/login.jsp"> <c:out value="${user.name}"/>
                    |
                    Выйти</a>
            </li>
        </ul>
        <div class="card" style="width: 100%">
            <div class="card-header">
                Регистрация
            </div>
            <div class="card-body">
                <form id="form">
                    <div class="form-group">
                        <label>Имя пользователя</label>
                        <input type="text" class="form-control" name="name" id="name">
                    </div>
                    <div class="form-group">
                        <label>Почта</label>
                        <input type="text" class="form-control" name="email" id="email">
                    </div>
                    <div class="form-group">
                        <label>Пароль</label>
                        <input type="text" class="form-control" name="password" id="pass">
                    </div>
                    <div class="form-group">
                        <label>Подтверждение</label>
                        <input type="text" class="form-control" name="copy_password" id="copy_pass">
                    </div>
                    <button type="submit" class="btn btn-primary" onclick="sendPost()">Регистрация</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
