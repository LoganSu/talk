<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://www.youlb.com/tags/role" prefix="r"%>
    
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script type="text/javascript">

</script>
</head>
<body>
	 <!-- 查询form div  --> 
    <div class="searchInfoDiv">
          <form id="charsSearchForm">
           <table>
              <tr>
                <td><div class="leftFont"><button class="btn btn-info btn-sm reset">重置</button></div></td>
                <td><div class="leftFont"><button class="btn btn-info btn-sm showChars" rel="${path}/mc/chars/data.do">line</button></div></td>
                <td><div class="leftFont"><button class="btn btn-info btn-sm showChars" rel="${path}/mc/chars/column.do">column</button></div></td>
                <td><div class="leftFont"><button class="btn btn-info btn-sm showChars" rel="${path}/mc/chars/pie.do">pie</button></div></td>
              </tr>
           </table>
        </form>
                
       </div>  
       
</body>
