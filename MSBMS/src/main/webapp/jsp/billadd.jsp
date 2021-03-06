<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="common/head.jsp"%>

<div class="right">
     <div class="location">
         <strong>你现在所在的位置是:</strong>
         <span>订单管理页面 >> 订单添加页面</span>
     </div>
     <div class="providerAdd">
         <form id="billForm" name="billForm" method="post" action="${pageContext.request.contextPath}/bill.do">
             <input type="hidden" name="method" value="save">
             <div class="">
                 <label for="billCode">订单编码：</label>
                 <input type="text" name="billCode" class="text" id="billCode" value=""> 
				 <!-- 放置提示信息 -->
				 <font color="red"></font>
             </div>
             <div>
                 <label for="productName">商品名称：</label>
                 <input type="text" name="productName" id="productName" value=""> 
				 <font color="red"></font>
             </div>
             <div>
                 <label for="productUnit">商品单位：</label>
                 <input type="text" name="productUnit" id="productUnit" value=""> 
				 <font color="red"></font>
             </div>
             <div>
                 <label for="productCount">商品数量：</label>
                 <input type="text" name="productCount" id="productCount" value=""> 
				 <font color="red"></font>
             </div>
             <div>
                 <label for="totalPrice">总金额：</label>
                 <input type="text" name="totalPrice" id="totalPrice" value=""> 
				 <font color="red"></font>
             </div>
             <div>
                 <label >供应商：</label>
                 <select name="providerId" id="providerId">
                     <option value="1">北京三木堂商贸有限公司</option>
                     <option value="2">石家庄帅益食品贸易有限公司</option>
                     <option value="3">深圳市泰香米业有限公司</option>
                     <option value="4">深圳市喜来客商贸有限公司</option>
                     <option value="5">兴化佳美调味品厂</option>
                     <option value="6">北京纳福尔食用油有限公司</option>
                     <option value="7">北京国粮食用油有限公司</option>
                     <option value="9">慈溪市广和绿色食品厂</option>
                     <option value="8">优百商贸有限公司</option>

		         </select>
				 <font color="red"></font>
             </div>
             <div>
                 <label >是否付款：</label>
                 <input type="radio" name="isPayment" value="1" checked="checked">未付款
				 <input type="radio" name="isPayment" value="2" >已付款
             </div>
             <div class="providerAddBtn">
                  <input type="submit" name="add" id="add" value="保存">
				  <input type="button" id="back" name="back"  onclick="window.location.href='${pageContext.request.contextPath}/bill.do?method=init'"  value="返回" >
             </div>
         </form>
     </div>
 </div>
</section>
<%@include file="common/foot.jsp" %>