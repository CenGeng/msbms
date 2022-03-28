<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@include file="common/head.jsp" %>
<%--<%@taglib prefix="s" uri="http://www.springframework.org/tags/form" %>--%>

<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.min.js"></script>
<script type="text/javascript">

    $(function () {
        let oldPwdFlag = false;
        $("#oldpassword").blur(function () {
            $.ajax({
                type: "GET",
                // url:path + "/jsp/user.do",
                url: "${pageContext.request.contextPath}/user.do",
                data: {method: "oldPwdModifyValidate", oldpassword: $("#oldpassword").val()},// ajax传递的参数
                dataType: "json",
                success: function (data) {
                    if (data.result === "true") {
                        validateTip($("#oldpassword").next(), {"color": "green"}, "原密码正确", true)
                        oldPwdFlag = true;
                    } else if (data.result === "false") {
                        validateTip($("#oldpassword").next(), {"color": "red"}, "原密码错误", false)
                    } else if (data.result === "sessionerror") {
                        validateTip($("#oldpassword").next(), {"color": "red"}, "Session过期", false)
                    } else if (data.result === "error") {
                        validateTip($("#oldpassword").next(), {"color": "red"}, "请输入旧密码", false)
                    }
                },
                error: function (data) {
                    validateTip($("#oldpassword").next(), {"color": "red"}, "请求错误", false)
                }
            });
        });
        $("#rnewpassword").blur(function () {
            $.ajax({
                type: "GET",
                url: "${pageContext.request.contextPath}/user.do",
                data: {
                    method: "newPwdModifyValidate",
                    newPwd: $("#newpassword").val(),
                    renewPwd: $("#rnewpassword").val()
                },
                dataType: "json",
                success: function (data) {
                    if (data.result === "true") {
                        validateTip($("#rnewpassword").next(), {"color": "green"}, "*", true)
                        validateTip($("#newpassword").next(), {"color": "green"}, "*", true)
                        if (oldPwdFlag){
                            $("#submit").attr("disabled",false);
                        }else {
                            validateTip($("#newpassword").next(), {"color": "red"}, "请输入旧密码", false)
                        }
                    }else if (data.result === "false"){
                        validateTip($("#rnewpassword").next(), {"color": "red"}, "两次输入的密码不一样", false)
                    }
                },
                error: function (data) {
                    validateTip($("#rnewpassword").next(), {"color": "red"}, "请求错误", false)
                }
            });
        });
    })
</script>
<div class="right">
    <div class="location">
        <strong>你现在所在的位置是:</strong>
        <span>密码修改页面</span>
    </div>
    <div class="providerAdd">
        <form id="userForm" name="userForm" method="post" action="${pageContext.request.contextPath}/user.do">
            <input type="hidden" id="method" name="method" value="savepwd">
            <!--div的class 为error是验证错误，ok是验证成功-->
            <div class="info">${msg}</div>
            <div class="">
                <label for="oldPassword">旧密码：</label>
                <input type="password" name="oldpassword" id="oldpassword" value="">
                <font color="red"></font>
            </div>
            <div>
                <label for="newPassword">新密码：</label>
                <input type="password" name="newpassword" id="newpassword" value="">
                <font color="red"></font>
            </div>
            <div>
                <label for="rnewpassword">确认新密码：</label>
                <input type="password" name="rnewpassword" id="rnewpassword" value="">
                <font color="red"></font>
            </div>
            <div class="providerAddBtn">
                <!--<a href="#">保存</a>-->
                <%--                        <input type="button" onclick="save()" name="save" id="save" value="保存" class="input-button">--%>
                <input type="submit" value="保存" id="submit" disabled>
            </div>
        </form>

    </div>
</div>
</section>
<%@include file="common/foot.jsp" %>