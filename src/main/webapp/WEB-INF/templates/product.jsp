<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<%@ include file="/WEB-INF/segments/head.jspf"%>
<body>
<c:choose>
    <c:when test="${requestScope['success']}">
        <div class="success-message">
            Product has successfully Added.
        </div>
        <a href="product" class="home-button">Home</a>
    </c:when>
    <c:otherwise>
    <h1>Products</h1>
    <table>
        <!-- Table headers -->
        <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Description</th>
                <th>Price</th>
                <th>Quantity</th>
                <th>Image</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <!-- Loop through cart items -->
            <c:set var="sum" scope="request" value="0" />
            <c:forEach var="productItem" items="${requestScope['products']}">
                <tr>
                    <!-- Display cart item details -->
                    <td>${productItem.id}</td>
                    <td>${productItem.name}</td>
                    <td>${productItem.description}</td>
                    <td><fmt:formatNumber type="number" maxFractionDigits="2" value="${productItem.price}" /></td>
                    <td>${productItem.quantity}</td>
                    <td><img src="/card-images/${productItem.image}" alt="Product Image" class="image"></td>
                    <td class="actions">
                        <!-- Uncomment the next line if you want to use the edit functionality -->
                        <a href="edit-product?id=${productItem.id}" class="edit-button">Edit</a>
                        <form method="POST" action="product">
                            <input type="hidden" name="action" value="delete">
                            <input type="hidden" name="id" value="${productItem.id}">
                            <input class="delete-button" type="submit" value="delete" />
                        </form>
                    </td>
                </tr>
                <c:set var="sum" scope="request" value="${sum + productItem.price * productItem.quantity}" />
            </c:forEach>
        </tbody>
        <tfoot>
            <tr>
                <td colspan="5" class="total">
                    Total: <fmt:formatNumber type="number" maxFractionDigits="2" value="${sum}" />
                </td>
            </tr>
        </tfoot>
    </table>
    <button class="add-button">Add Product</button>
    </c:otherwise>
</c:choose>

    <!-- Popup form -->
    <section class="login-section" id="login-section">
        <div class="blur-bg-overlay"></div>
        <div class="form-popup">
            <span class="close-btn material-symbols-rounded"></span>
            <c:if test="${requestScope['errors'] != null}">
                <div class="error-message">${requestScope['errors']} </div>
            </c:if>
            <div class="form-box login">
                <div class="form-content">
                    <h2>Add Products</h2>
                    <form class="form-custom" action="product" method="POST">
                        <input type="hidden" name="action" value="add">
                        <input type="hidden" id="productId" name="id">
                        <div class="input-field">
                            <input type="text" id="txtName" name="name">
                            <label>Card Name</label>
                        </div>
                        <div class="input-field">
                            <input type="text" id="txtDescription" name="description">
                            <label>Description</label>
                        </div>
                        <div class="input-field">
                            <input pattern="^\d+$" id="numberQuantity" name="quantity">
                            <label>Quantity</label>
                        </div>
                        <div class="input-field">
                            <input pattern="^\d+(\.\d{1,2})?$" id="numberPrice" name="price">
                            <label>Price</label>
                        </div>
                        <div class="image-field">
                            <input type="file" id="fileImage" name="image">
                        </div>
                        <button type="submit">Add Product</button>
                    </form>
                </div>
            </div>
        </div>
    </section>
    <script src="${pageContext.request.contextPath}/js/custom.js"></script>
</body>
</html>
