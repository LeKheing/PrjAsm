

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="header.jsp"></jsp:include>
    <hr>
    <div class="container bootstrap snippets bootdey mt-5">
        <h1 class="text-primary">Edit Profile</h1>
        <hr>
        <div class="row">


            <!-- edit form column -->
            <div class="col-md-6 personal-info">

                <h3>Personal info</h3>

                <form class="form-horizontal" role="form" action="info" method="post">
                    <div class="form-group">
                        <label class="col-lg-3 control-label">First name:</label>
                        <div class="col-lg-8">
                            <input class="form-control" type="text" name="firstName" value="${currentUser.firstName}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-3 control-label">Last name:</label>
                    <div class="col-lg-8">
                        <input class="form-control" type="text" name="lastName" value="${currentUser.lastName}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-3 control-label">Phone Number</label>
                    <div class="col-lg-8">
                        <input class="form-control" type="text" name="phoneNumber" value="${currentUser.phoneNumber}">
                    </div>
                </div>
                <input hidden="hidden" class="form-control" type="email" name="email" value="${currentUser.email}">
                <br>
                <div class="form-group">
                    <div class="col-sm-10 col-sm-offset-2">
                        <button type="submit" class="btn btn-primary">Submit</button>
                        <button type="reset" class="btn btn-default">Cancel</button>
                    </div>
                </div>
            </form>
        </div>



        <div class="col-md-6 personal-info">
            <h3>Change Password</h3>
            <i class="text-danger">${err}</i>
            <i class="text-success">${success}</i>
            <form class="form-horizontal" role="form" action="change-pass" method="post">
                <div class="form-group">
                    <label class="col-lg-3 control-label">Old password:</label>
                    <div class="col-lg-8">
                        <input class="form-control" type="password" name="oldPass">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-lg-3 control-label">New password</label>
                    <div class="col-lg-8">
                        <input class="form-control" type="password" name="newPass" ">
                    </div>
                </div>
                <div class="form-group">
                    
                    <label class="col-lg-3 control-label">Confirm password</label>
                    <div class="col-lg-8">
                        <input class="form-control" type="password" name="conNewPass">
                    </div>
                </div>
                <br>
                <div class="form-group">
                    <div class="col-sm-10 col-sm-offset-2">
                        <button type="submit" class="btn btn-primary">Submit</button>
                        <button type="reset" class="btn btn-default">Cancel</button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<hr>
<jsp:include page="footer.jsp"></jsp:include>
