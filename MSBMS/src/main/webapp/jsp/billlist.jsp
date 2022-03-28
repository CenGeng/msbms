<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="common/head.jsp"%>

<div class="right">
       <div class="location">
           <strong>你现在所在的位置是:</strong>
           <span>订单管理页面</span>
       </div>
       <div class="search">
       <form method="get" action="${pageContext.request.contextPath}/bill.do">
			<span>商品名称：</span>

		   	<input type="hidden" name="method" value="query">

			<input name="productname" type="text" value="${productname}">
		
			<span>供应商：</span>
			<select name="proname">
				   <option value="">--请选择--</option>
				   <c:forEach items="${proNames}" var="pronames">
				   		<option value="${pronames}" <c:if test="${proname==pronames}">selected</c:if>>${pronames}</option>
				   </c:forEach>
       		</select>
			 
			<span>是否付款：</span>
			<select name="ispayment">
				<option value="">--请选择--</option>
				<option value="1">未付款</option>
				<option value="2">已付款</option>
       		</select>
			
			 <input	value="查 询" type="submit" id="searchbutton">
			 <a href="${pageContext.request.contextPath}/jsp/billadd.jsp">添加订单</a>
		</form>
		
       </div>
       <!--账单表格 样式和供应商公用-->
      <table class="providerTable" cellpadding="0" cellspacing="0">
          <tr class="firstTr">
              <th width="10%">订单编码</th>
              <th width="15%">供应商</th>
              <th width="15%">商品名称</th> 
              <th width="5%">商品单位</th>
              <th width="10%">商品单价</th>  
              <th width="10%">商品数量</th> 
               
              <th width="10%">订单金额</th>
              <th width="5%">是否付款</th>
              <th width="10%">创建时间</th>
              <th width="30%">操作</th>
          </tr>
          <c:forEach items="${bills}" var="bill">
				<tr>
					<td>
					<span>${bill.billCode} </span>
					</td>
					
					<td>
					<span>${bill.providerName}</span>
					</td>
					
					<td>
					<span>${bill.productName}</span>
					</td>
					
					<td>
					<span>${bill.productUnit}</span>
					</td>
										
					<td>
					<span>${bill.totalPrice/bill.productCount}￥</span>
					</td>
					
					<td>
					<span>${bill.productCount}</span>
					</td>
					
					<td>
					<span>${bill.totalPrice}￥</span>
					</td>
					<td>
					<span>${bill.isPayment==1?"未付款":"已付款"}</span>
					</td>
					<td>
					<span><f:formatDate value="${bill.creationDate}" pattern="yyyy-MM-dd"/></span>
					</td>
					<td>
					<span><a class="deleteBill" onClick="return confirm('确定删除?');" href="${pageContext.request.contextPath }/bill.do?method=del&code=${bill.billCode}"><img src="${pageContext.request.contextPath }/images/schu.png" alt="删除" title="删除"/></a></span>
					</td>
				</tr>
				</c:forEach>
      </table>
      <div class="page-bar">
		<ul class="page-num-ul clearfix">
			<li>共${pageInfo.totalCount}条记录&nbsp;&nbsp; ${pageInfo.currentPageNo}/${pageInfo.totalPageCount}页</li>
			<a href="${pageContext.request.contextPath}/bill.do?method=frontpage&productname=${productname}&proname=${proname}&ispayment=${ispayment}">首页</a>
			<a href="${pageContext.request.contextPath}/bill.do?method=previouspage&current=${pageInfo.currentPageNo}&productname=${productname}&proname=${proname}&ispayment=${ispayment}">上一页</a>
			<a href="${pageContext.request.contextPath}/bill.do?method=nextpage&current=${pageInfo.currentPageNo}&productname=${productname}&proname=${proname}&ispayment=${ispayment}">下一页</a>
		</ul>
  </div>
</div>
</section>


<%@include file="common/foot.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/billlist.js"></script>