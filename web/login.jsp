<%@ page pageEncoding="UTF-8" contentType="text/html; charset = UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<!--<style>


.btn-login {
  font-size: 0.9rem;
  letter-spacing: 0.05rem;
  padding: 0.75rem 1rem;
}

.btn-google {
  color: white !important;
  background-color: #ea4335;
}

.btn-facebook {
  color: white !important;
  background-color: #3b5998;
}
</style>-->

<jsp:include page="header.jsp"></jsp:include>
    <div class="container">
        <div class="row">
            <div class="col-sm-9 col-md-7 col-lg-5 mx-auto">
                <div class="card border-0 shadow rounded-3" style="margin-top: 200px" >
                    <div class="card-body p-4 p-sm-5">
                        <h5 class="card-title text-center mb-5 fw-light fs-5">Login</h5>
                    <c:if test="${not empty errorMessage}">
                        <p style="color:red">${errorMessage}</p>
                    </c:if>

                    <form action="login" method="post">

                        <div class="form-floating mb-3">
                            <input type="email" class="form-control" id="email" placeholder="Email" name="email">
                            <label for="email">Email</label>
                        </div>
                        <div class="form-floating mb-3">
                            <input type="password" name="password" class="form-control" id="floatingPassword" placeholder="Password">
                            <label for="floatingPassword">Password</label>
                        </div>

                        <!--                        <div class="form-check mb-3">
                                                    <input class="form-check-input" type="checkbox" value="" name="remember" id="rememberPasswordCheck">
                                                    <label class="form-check-label" for="term">
                                                        Remember Password
                                                    </label>
                                                </div>-->
                        <div class="d-grid">
                            <button class="btn btn-primary btn-login text-uppercase fw-bold" type="submit">Login</button>
                        </div>
                        <hr class="my-4">
                        <div class="d-grid mb-2">

                            <a href="register.jsp" class="btn btn-google btn-login text-uppercase fw-bold" type="submit">
                                Register
                            </a>

                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="footer.jsp"/>

