<%--
  Created by IntelliJ IDEA.
  User: Денис
  Date: 19.08.2020
  Time: 10:30
  Page to create new candidate
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="store.PsqlStore" %>
<%@ page import="model.Post" %>
<%@ page import="model.Candidate" %>
<%@ page import="java.time.LocalDateTime" %>
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
</head>
<body>
<%
    String id = request.getParameter("id");
    Candidate candidate = new Candidate(0, "", null, null);
    if (id != null) {
        candidate = PsqlStore.instOf().findByIdCandidate(Integer.valueOf(id));
    }
%>
<script>
    window.onload = function getContent() {
        $.ajax({
            type: 'GET',
            url: '<%=request.getContextPath()%>/candidates.do',
            data: {request: 'from edit jsp'},
            dataType: "json"
        }).done(function (data) {
            let head = '<label for="cities">Выберите город:</label>' +
                '<select id="city" name="city">';
            for (var key in data) {
                head += '<option value=' + data[key] + '>' + data[key] + '</option>';
            }
            head += '</select><br>';
            $('#divName label:first').before(head);
        }).fail(function (err) {
            alert(err);
        });
    }

    function validate() {
        var name = $('#candidateName').val();
        var file = $('#fileCandidate').val();
        if (name == '' || file == '') {
            alert("Please fill the form field and upload photo")
            return false;
        }
        return true;
    }

    function sendData() {
        if (validate()) {
            var name = $('#candidateName').val();
            var city = $('#city').val();
            var $file = $('#fileCandidate');
            var fd = new FormData;
            fd.append("name", name);
            fd.append("city", city);
            fd.append("file", $file.prop('files')[0]);
            $.ajax({
                url: '<%=request.getContextPath()%>/candidates.do?id=<%=candidate.getId()%>',
                data: fd,
                processData: false,
                contentType: false,
                type: 'POST',
                success: function (data) {
                    alert(data);
                }
            });
        }
    }
</script>

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
                <% if (id == null) { %>
                Новый кандидат
                <% } else { %>
                Редактирование кандидата.
                <% } %>
            </div>
            <div class="card-body">
                <form>
                    <div class="form-group" id="divName">
                        <label>Имя</label>
                        <input type="text" class="form-control" name="name" value="<%=candidate.getName()%>"
                               id="candidateName">
                    </div>
                    <div class="checkbox">
                        <input type="file" name="file" id="fileCandidate">
                    </div>
                    <button type="submit" class="btn btn-primary" onclick="sendData()">Сохранить</button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>

