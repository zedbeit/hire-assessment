<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <title>Book Catalog</title>
    <link rel="stylesheet" type="text/css" href="/css/styles.css">
</head>
<body>
    <h1>Book Catalog</h1>

    <!-- Add Book Form -->
    <form action="/add" method="post" class="form-inline">
        <input type="text" name="name" placeholder="Name" required />
        <input type="text" name="isbnNumber" placeholder="ISBN" required />
        <input type="date" name="publishDate" required />
        <input type="number" name="price" placeholder="Price (ZAR)" required />
        <select name="bookType" required>
            <option value="">Select Book Type</option>
            <option value="Hard Cover">Hard Cover</option>
            <option value="Soft Cover">Soft Cover</option>
            <option value="eBook">eBook</option>
        </select>
        <button type="submit" class="add-btn">Add Book</button>
    </form>

    <!-- Display Books -->
    <table>
        <thead>
            <tr>
                <th>Name</th>
                <th>ISBN</th>
                <th>Publish Date</th>
                <th>Price</th>
                <th>Book Type</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:choose>
                <c:when test="${not empty books}">
                    <c:forEach var="book" items="${books}">
                        <tr>
                            <form action="/edit/${book.id}" method="post">
                                <td><input type="text" name="name" value="${book.name}" /></td>
                                <td><input type="text" name="isbnNumber" value="${book.isbnNumber}" /></td>
                                <td><input type="text" name="publishDate" value="${book.publishDate}" /></td>
                                <td><input type="number" name="price" value="${book.price}" /></td>
                                <td>
                                    <select name="bookType">
                                        <option value="Hard Cover" ${book.bookType == 'Hard Cover' ? 'selected' : ''}>Hard Cover</option>
                                        <option value="Soft Cover" ${book.bookType == 'Soft Cover' ? 'selected' : ''}>Soft Cover</option>
                                        <option value="eBook" ${book.bookType == 'eBook' ? 'selected' : ''}>eBook</option>
                                    </select>
                                </td>
                                <td>
                                    <button type="submit" class="edit-btn">Edit</button>
                                </td>
                            </form>
                            <form action="/delete/${book.id}" method="post" style="display:inline;">
                                <td>
                                    <button type="submit" class="delete-btn">Delete</button>
                                </td>
                            </form>
                        </tr>
                    </c:forEach>
                </c:when>
                <c:otherwise>
                    <tr class="no-records-row">
                        <td colspan="6">No Records Found</td>
                    </tr>
                </c:otherwise>
            </c:choose>
        </tbody>
    </table>
</body>
</html>