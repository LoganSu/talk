 <%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!-- 模态框    提示框   确认框 -->    
<!-- Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
         <!-- title -->
        <h4 class="modal-title" id="myModalLabel"></h4>
      </div>
      <div class="modal-body">
        <!-- 添加模态框里面的页面 -->
      </div>
      <div class="modal-footer">
        <!-- 操作按钮-->
        <button type="button" class="btn btn-primary modalSave">确定</button>
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      </div>
    </div>
  </div>
</div>

<!-- Modal 只有关闭按钮 -->
<div class="modal fade" id="noSureModal" tabindex="-1" role="dialog" aria-labelledby="noSureModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
         <!-- title -->
        <h4 class="modal-title" id="noSureModallLabel"></h4>
      </div>
      <div class="modal-body">
        <!-- 添加模态框里面的页面 -->
      </div>
      <div class="modal-footer">
        <!-- 操作按钮-->
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      </div>
    </div>
  </div>
</div>

<!-- Modal 不公共处理事件的模态框-->
<div class="modal fade" id="unnormalModal" tabindex="-1" role="dialog" aria-labelledby="unnormalModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
         <!-- title -->
        <h4 class="modal-title" id="unnormalModalLabel"></h4>
      </div>
      <div class="modal-body">
        <!-- 添加模态框里面的页面 -->
      </div>
    </div>
  </div>
</div> 
<!--     <div class="modal fade confirm_modal"  role="dialog" id="confirm_modal"> -->
<!--       <div class="modal-body inner"></div> -->
<!--       <div class="modal-footer"> -->
<!--         <a class="btn cancel" href="#" data-dismiss="modal">qqqqq</a> -->
<!--         <a href="#" class="btn btn-danger" data-action="1">yes</a> -->
<!--       </div> -->
<!--     </div> -->
    
    <!-- confirm -->
<div class="modal fade confirm_modal" id="confirm_modal" tabindex="-1" role="dialog" aria-labelledby="confirmModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
         <!-- 标题 -->
        <h4 class="modal-title" id="confirmModalLabel">提示</h4>
      </div>
      <div class="modal-body inner">
        <!-- 添加模态框里面的页面 -->
      </div>
      <div class="modal-footer">
        <!-- 操作按钮-->
        <button type="button" class="btn btn-primary" data-action="1">确定</button>
        <button type="button" class="btn btn-default cancel" data-dismiss="modal">关闭</button>
<!--             <a class="btn cancel" href="#" data-dismiss="modal">cancel</a> -->
<!--             <a href="#" class="btn btn-danger" data-action="1">yes</a> -->
      </div>
    </div>
  </div>
 </div>  
<!-- alert -->
<div class="modal fade" id="alertModal" tabindex="-1" role="dialog" aria-labelledby="alertModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-sm">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
         <!-- 标题 -->
        <h4 class="modal-title" id="alertModalLabel"></h4>
      </div>
      <div class="modal-body">
        <!-- 添加模态框里面的页面 -->
      </div>
      <div class="modal-footer">
        <!-- 操作按钮-->
        <button type="button" class="btn btn-primary" data-dismiss="modal">确定</button>
      </div>
    </div>
  </div>
</div>
  
  <!-- system modal start -->
    <div id="ycf-alert" class="modal">
      <div class="modal-dialog modal-sm">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span class="sr-only">Close</span></button>
            <h5 class="modal-title"><i class="fa fa-exclamation-circle"></i> [Title]</h5>
          </div>
          <div class="modal-body small">
            <p>[Message]</p>
          </div>
          <div class="modal-footer" >
            <button type="button" class="btn btn-primary ok" data-dismiss="modal">[BtnOk]</button>
            <button type="button" class="btn btn-default cancel" data-dismiss="modal">[BtnCancel]</button>
          </div>
        </div>
      </div>
    </div>
  <!-- system modal end -->
