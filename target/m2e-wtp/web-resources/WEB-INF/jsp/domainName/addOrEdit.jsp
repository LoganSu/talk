<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<body>
<script type="text/javascript">
</script>
<div>
	   <div>
		 <form id="domainNamesaveForm" action="">
		   <input type="hidden" name="id" value="${domainName.id}"/>
		   <input type="hidden" name="parentid" value="${parentDomain.id}"/>
		   <input type="hidden" name="layer" value="${parentDomain.layer eq null?1:parentDomain.layer+1}"/>
		   
           <table>
              <tr>
                <td><div class="firstFont"><span class="starColor">*</span>平台：</div></td>
                <td><div>
                  <select name="platform" class="form-control province">
                     <option value="二级平台">二级平台</option>
                  </select>
                </div></td>
                <td><div class="leftFont"><span class="starColor">*</span>名称：</div></td>
                <td><div><input name="fname" class="form-control required" title="名称不能为空" maxlength="10" value="${domainName.fname}"/></div></td>
                <td><div class="leftFont"><span class="starColor">*</span>域名：</div></td>
                <td><div><input name="domain" class="form-control required" style="width: 220px" title="域名不能为空" maxlength="20" value="${domainName.domain}"/></div></td>
                <td colspan="2"><div style="margin-left: 0px" class="firstFont"></div></td>
              </tr>
              <tr>
                <td><div class="firstFont">备注：</div></td>
                <td colspan="5"><div><input style="width: 660px" name="remark" class="form-control" value="${domainName.remark}"/></div></td>
              </tr>
           </table>
         </form>
	   </div>
 </div>
</body>
</html>