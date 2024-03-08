<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="header.jsp"></jsp:include>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- Modal Search Start -->
<div class="modal fade" id="searchModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-fullscreen">
        <div class="modal-content rounded-0">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Search by keyword</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body d-flex align-items-center">
                <div class="input-group w-75 mx-auto d-flex">
                    <input type="search" class="form-control p-3" placeholder="keywords" aria-describedby="search-icon-1">
                    <span id="search-icon-1" class="input-group-text p-3"><i class="fa fa-search"></i></span>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Modal Search End -->


<!-- Single Page Header start -->
<div class="container-fluid page-header py-5">
    <h1 class="text-center text-white display-6">Shop</h1>
    <ol class="breadcrumb justify-content-center mb-0">
        <li class="breadcrumb-item"><a href="#">Home</a></li>
        <li class="breadcrumb-item"><a href="#">Pages</a></li>
        <li class="breadcrumb-item active text-white">Shop</li>
    </ol>
</div>
<!-- Single Page Header End -->


<!-- Fruits Shop Start-->
<div class="container-fluid fruite py-5">
    <div class="container py-5">
        <h1 class="mb-4">Fresh fruits shop</h1>
        <div class="row g-4">
            <div class="col-lg-12">
                <div class="row g-4">
                    <div class="col-xl-3">
                        <form action="search" method="post">
                            <div class="input-group w-100 mx-auto d-flex">
                                <input type="text" name="proName" class="form-control p-3" placeholder="keywords" aria-describedby="search-button">
                                <button type="submit" id="search-button" class="input-group-text p-3" style="background-color: gray;border: none; background: none; cursor: pointer;"><i class="fa fa-search"></i></button>
                            </div>
                        </form>
                    </div>
                    <div class="col-6"></div>
                    <div class="col-xl-3">
                        <div class="bg-light ps-3 py-3 rounded d-flex justify-content-between mb-4">
                            <label for="fruits">Default Sorting:</label>
                            <form id="fruitform" action="sort" method="post">
                                <input type="text" hidden="hidden" name="catId" value="${catId}" />
                                <select onchange="submitForm()" id="fruits" name="sortMethod" class="border-0 form-select-sm bg-light me-3">
                                    <option value="">Nothing</option>
                                    <option value="price">Price</option>
                                    <option value="name">Name</option>
                                </select>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="row g-4">
                    <div class="col-lg-3">
                        <div class="row g-4">
                            <div class="col-lg-12">
                                <div class="mb-3">
                                    <h4>Categories</h4>
                                    <ul class="list-unstyled fruite-categorie">
                                        <c:forEach items="${cat}" var="cate">
                                            <li>
                                                <div class="d-flex justify-content-between fruite-name">
                                                    <a href="#" onclick="submitFormCat('${cate.id}')"><i class="fas fa-apple-alt me-2"></i>${cate.name}</a>
                                                </div>
                                            </li>
                                        </c:forEach>

                                    </ul>
                                </div>
                            </div>

                            <div class="col-lg-12">
                                <div class="position-relative">
                                    <img src="img/banner-fruits.jpg" class="img-fluid w-100 rounded" alt="">
                                    <div class="position-absolute" style="top: 50%; right: 10px; transform: translateY(-50%);">
                                        <h3 class="text-secondary fw-bold">Fresh <br> Fruits <br></h3>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-9">
                        <div class="row g-4 justify-content-center">

                            <c:forEach items="${pros}" var="product">
                                <div class="col-md-6 col-lg-4 col-xl-3">
                                    <div class="rounded position-relative fruite-item">
                                        <div class="fruite-img">
                                            <img src="img/${product.image}" class="img-fluid w-100 rounded-top" alt="">
                                        </div>
                                        <div class="text-white bg-secondary px-3 py-1 rounded position-absolute" style="top: 10px; left: 10px;">
                                            <c:choose>
                                                <c:when test="${product.c_id == 1}">
                                                    Fruits
                                                </c:when>
                                                <c:when test="${product.c_id == 2}">
                                                    Vegetables
                                                </c:when>
                                                <c:when test="${product.c_id == 3}">
                                                    Breads
                                                </c:when>
                                            </c:choose>
                                        </div>
                                        <div class="p-4 border border-secondary border-top-0 rounded-bottom">
                                            <form action="detail" method="post">
                                                <input type="hidden" name="id" id="id" value="${product.id}">
                                                <h4>
                                                    <button type="submit" style="background-color: #fff; color: green; border: none; padding: 10px 20px; font-size: 32px; border-radius: 5px; cursor: pointer;">${product.name}</button>
                                                </h4>
                                            </form>
                                            <p>${product.description}</p>
                                            <div class="d-flex justify-content-between flex-lg-wrap">
                                                <p class="text-dark fs-5 fw-bold mb-0"><fmt:formatNumber value="${product.price}" pattern="#0.00"/>$/kg</p>
                                                <a href="detail?id=${product.id}" class="btn border border-secondary rounded-pill px-3 text-primary"><i class="fa fa-shopping-bag me-2 text-primary"></i> Add to cart</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>  


                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Fruits Shop End-->


<jsp:include page="footer.jsp"></jsp:include>




<!-- JavaScript Libraries -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="lib/easing/easing.min.js"></script>
<script src="lib/waypoints/waypoints.min.js"></script>
<script src="lib/lightbox/js/lightbox.min.js"></script>
<script src="lib/owlcarousel/owl.carousel.min.js"></script>
<script>
                                                        function submitFormCat(searchText) {
                                                            var form = document.createElement("form");
                                                            form.setAttribute("action", "search");
                                                            form.setAttribute("method", "get");

                                                            var input = document.createElement("input");
                                                            input.setAttribute("type", "hidden");
                                                            input.setAttribute("name", "catId");
                                                            input.setAttribute("value", searchText);

                                                            form.appendChild(input);
                                                            document.body.appendChild(form);
                                                            form.submit();
                                                        }
</script>
<script>
    function submitForm() {
        document.getElementById("fruitform").submit();
    }
</script>
<!-- Template Javascript -->
<script src="js/main.js"></script>
</body>

</html>