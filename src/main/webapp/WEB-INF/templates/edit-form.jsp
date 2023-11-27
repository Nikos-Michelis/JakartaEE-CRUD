<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<jsp:useBean id="product" class="com.stock.stockmanager.model.Products" scope="request" />
<html>
<%@ include file="/WEB-INF/segments/head.jspf"%>
<body>
<!-- Popup form -->
<c:choose>
<c:when test="${requestScope['success']}">
    <div class="success-message">
        Product has successfully updated.
    </div>
    <a href="product" class="home-button">Home</a>
</c:when>
<c:otherwise>
<section class="auto-popup" id="login-section">
        <div class="blur-bg-overlay"></div>
        <div class="form-popup">
            <c:if test="${requestScope['errors'] != null}">
                <div class="error-message">${requestScope['errors']} </div>
            </c:if>
            <div class="form-box login">
                <div class="form-content">
                    <h2>Edit Products</h2>
                    <form class="form-custom" action="edit-product" method="POST">
                        <input type="hidden" name="action" value="update">
                        <input type="hidden" id="productId" name="id" value="${product.id}">
                        <div class="input-field">
                            <input type="text" id="txtName" name="name" value="${product.name}">
                            <label>Card Name</label>
                        </div>
                        <div class="input-field">
                            <input type="text" id="txtDescription" name="description" value="${product.description}">
                            <label>Description</label>
                        </div>
                        <div class="input-field">
                            <input pattern="^\d+$" id="numberQuantity" name="quantity" value="${product.quantity}">
                            <label>Quantity</label>
                        </div>
                        <div class="input-field">
                            <input pattern="^\d+(\.\d{1,2})?$" id="numberPrice" name="price" value="${product.price}">
                            <label>Price</label>
                        </div>
                        <div class="input-image">
                            <label for="fileImage" class="image-upload-label">
                                <img src="/card-images/${product.image}" alt="Initial Image">
                                <span>Click to change</span>
                            </label>
                            <input type="file" id="fileImage" name="image" accept="image/*" style="display: none;" value="${product.image}">
                        </div>
                        <button type="submit">Update Product</button>
                    </form>
                </div>
            </div>
        </div>
</section>
</c:otherwise>
</c:choose>

<script src="${pageContext.request.contextPath}/js/custom.js"></script>
</body>
</html>
