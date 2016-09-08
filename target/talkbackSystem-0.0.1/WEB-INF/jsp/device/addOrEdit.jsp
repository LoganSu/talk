<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <script type="text/javascript">
//时间控件
 $(".datepicker").datepicker();
 </script>
<body>
<div>
	   <div>
		 <form id="devicesaveForm" action="">
		   <div>
            <table>
              <tr>
                     <td><div class="firstFont">设备SN号：</div></td> 
                     <td colspan="3"><div><input name="id" class="form-control" style="width: 300px" readonly="readonly" value="${device.id}"/></div></td>
              </tr>
              <tr>
	                <td><div class="firstFont">设备编号：</div></td>
	                <td><div><input name="deviceNum" class="form-control" readonly="readonly" value="${device.deviceNum}"/></div></td>
	                <td><div class="leftFont">设备型号：</div></td>
	                <td><div><input name="deviceModel" class="form-control" readonly="readonly" title="" value="${device.deviceModel}"/></div></td>
                    <td><div class="leftFont">应用版本：</div></td>
	                <td><div><input name="app_version" class="form-control" readonly="readonly" title="" value="${device.app_version}"/></div></td>
              </tr>
               <tr> 	 
	                <td><div class="firstFont">内存大小：</div></td>
	                <td><div><input name="memory_size" class="form-control" readonly="readonly" title="" value="${device.memory_size}"/></div></td>
	                <td><div class="leftFont">存储容量：</div></td>
	                <td><div><input name="storage_capacity" class="form-control" readonly="readonly" title="" value="${device.storage_capacity}"/></div></td>                
                    <td><div class="leftFont">系统版本：</div></td>
	                <td><div><input name="deviceModel" class="form-control" readonly="readonly" title="" value="${device.deviceModel}"/></div></td>
              </tr>
              <tr> 	 
	                <td><div class="firstFont">处理器：</div></td>
	                <td><div><input name="processor_type" class="form-control" readonly="readonly" title="" value="${device.processor_type}"/></div></td>
	                <td><div class="leftFont">固件版本：</div></td>
	                <td><div><input name="firmware_version" class="form-control" title="" readonly="readonly" value="${device.firmware_version}"/></div></td>
	                <td><div class="leftFont">内核版本：</div></td>
	                <td><div><input name="kernal_version" class="form-control" title="" readonly="readonly" value="${device.kernal_version}"/></div></td>
	                
              </tr>
              <tr> 	
                    <td><div class="firstFont">设备状态：</div></td>
	                <td><div>
	                   <select  class="form-control" name="deviceStatus">
		                     <option <c:if test="${device.deviceStatus==null}">selected="selected"</c:if>  value="">未激活</option>
		                     <option <c:if test="${device.deviceStatus=='1'}">selected="selected"</c:if>  value="1">激活</option>
		                </select>
	                </div></td>
	                <td><div class="leftFont">厂家：</div></td>
	                <td><div><input name="deviceFactory" class="form-control" title="" value="${device.deviceFactory}"/></div></td>         
	                <td><div class="leftFont">出厂日期：</div></td>
	                <td><div><input name="deviceBorn" class="form-control datepicker"  value="${device.deviceBorn}"/></div></td>
              
              </tr>
              <tr>
                    <td><div class="firstFont">备注：</div></td>
	                <td colspan="3"><div><input name="remark" class="form-control" style="width: 300px" value="${device.remark}"/></div></td>

              </tr>
           </table>
           </div>
         </form>
	   </div>
 </div>
</body>
<style>
.showDomainTableDiv{
padding-left: 20px
}
</style>
 
