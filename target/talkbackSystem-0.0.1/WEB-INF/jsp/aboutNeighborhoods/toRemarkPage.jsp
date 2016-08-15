<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
   <div id="aboutNeighborhoodsRemarkDiv">
     <form id="aboutNeighborhoodsRemarkForm" action="">
          <input type="hidden" name="status" value="${aboutNeighborhoods.status}"/>
          <input type="hidden" name="id" value="${aboutNeighborhoods.id}"/>
	      <table style="margin-top: 6px">
	               <tr>
	                <td><div class="firstFont">备注：</div></td>
	               </tr>
	               <tr>
	                <td><div>
	                    <textarea style="width: 700px;height: 60px" name="remark" rows="" cols="" class="form-control"></textarea>
	                </div></td>
	              </tr>
	       </table>
      </form>   
           <!-- 详情不显示按钮 -->
           <div class="modal-footer">
            <input type="submit" class="btn btn-primary sure" value="确定"/> 
            <input type="button" class="btn btn-default" data-dismiss="modal" value="关闭"/> 
           </div>
   </div>
</body>
</html>