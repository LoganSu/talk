<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<body>
<div>
	   <div>
		 <form id="staticParamsaveForm" action="">
		   <input type="hidden" name="id" value="${staticParam.id}"/>
           <table>
              <tr>
                <td><div class="firstFont"><span class="starColor">*</span>参数唯一key：</div></td>
                <td><div>
                  <input name="fkey" class="form-control required" title="key不能为空" value="${staticParam.fkey}"/>
                </div></td>
                <td><div class="leftFont"><span class="starColor">*</span>参数值：</div></td>
                <td><div>
                   <input name="fvalue" class="form-control required" title="参数值不能为空" value="${staticParam.fvalue}"/>
                </div></td>
                <td><div class="leftFont"><span class="starColor">*</span>参数描述：</div></td>
                <td><div><input name="fdescr" class="form-control required" title="参数描述不能为空"  value="${staticParam.fdescr}"/></div></td>
              </tr>
           </table>
         </form>
	   </div>
 </div>
</body>
</html>