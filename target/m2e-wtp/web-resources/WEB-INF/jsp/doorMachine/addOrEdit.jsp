<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<body>
<script type="text/javascript">


</script>
<div>
	   <div>
		 <form id="doorMachineForm" action="">
		   <input type="hidden" name="id" value="${doorMachine.id}"/>
           <table>
              <tr>
                <td><div class="firstFont"><span class="starColor">*</span>型号：</div></td>
                <td><div><input name="softwareType" class="form-control areaNum required" title="型号不能为空" maxlength="20" value="${doorMachine.machineType}"/></div></td>
                <td><div class="leftFont"><span class="starColor">*</span>硬件型号：</div></td>
                <td><div><input name="hardwareModel" class="form-control areaNum required" title="硬件型号不能为空" maxlength="20" value="${doorMachine.hardwareModel}"/></div></td>
              </tr>
              <tr>
                <td><div>备注：</div></td>
                <td colspan="3"><div><input style="width: 740px" name="remark" class="form-control" value="${doorMachine.remark}"/></div></td>
              </tr>
           </table>
         </form>
	   </div>
 </div>
</body>
</html>