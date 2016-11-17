/* ==========================================================
 * sco.confirm.js
 * http://github.com/terebentina/sco.js
 * ==========================================================
 * Copyright 2013 Dan Caragea.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ========================================================== */

/*jshint laxcomma:true, sub:true, browser:true, jquery:true, devel:true */

;(function($, undefined) {
	"use strict";

	var pluginName = 'scojs_confirm';

	function Confirm(options) {
		this.options = $.extend({}, $.fn[pluginName].defaults, options);
		var $modal = $(this.options.target);
		if (!$modal.length) {
			$modal = $('<div class="modal" id="' + this.options.target.substr(1) + '"><div class="modal-body inner"/><div class="modal-footer"><a class="btn cancel" href="#" data-dismiss="modal">cancel</a> <a href="#" class="btn btn-danger" data-action="1">yes</a></div></div>').appendTo(this.options.appendTo).hide();
		}else{
			$modal=$("#confirm_modal");
		}
//		alert(333);
		if (typeof this.options.action == 'function') {
//			alert(222);
			var self = this;
//			alert(self);
			$modal.find('[data-action]').one("click",function(e) {
//				alert("param  "+self.options.param);
				e.preventDefault();
				self.options.action.call(self,self.options.param);
				self.close();
			});
		} else if (typeof this.options.action == 'string') {
//			$modal.find('[data-action]').attr('href', this.options.action);
			$.post(this.options.action);
		}
		 
		this.scomodal = $.scojs_modal(this.options);
	}

	$.extend(Confirm.prototype, {
		show: function() {
			this.scomodal.show();
			return this;
		}

		,close: function() {
			this.scomodal.close();
			return this;
		}

		,destroy: function() {
			this.scomodal.destroy();
			return this;
		}
	});


	$.fn[pluginName] = function(options) {
		return this.each(function() {
			var obj,opts;
			if (!(obj = $.data(this, pluginName))) {
				var $this = $(this)
					,data = $this.data()
					,title = $this.attr('title') || data.title
					,opts = $.extend({}, $.fn[pluginName].defaults, options, data)
					;
				if (!title) {
					title = 'this';
				}
				opts.content = opts.content.replace(':title', title);
				if (!opts.action) {
					opts.action = $this.attr('href');
				} else if (typeof window[opts.action] == 'function') {
					opts.action = window[opts.action];
				}
				$.data(this, pluginName, obj);
			}
			obj = new Confirm(opts);
//				alert(obj.toSource());
			obj.show();
		});
	};

	$[pluginName] = function(options) {
		return new Confirm(options);
	};

	$.fn[pluginName].defaults = {
		param :"",
		content: 'Are you sure you want to delete :title?'
		,cssclass: 'confirm_modal'
		,target: '#confirm_modal'	// this must be an id. This is a limitation for now, @todo should be fixed
		,appendTo: 'body'	// where should the modal be appended to (default to document.body). Added for unit tests, not really needed in real life.
	};

	$(document).on('click.' + pluginName, '[data-trigger="confirm"]', function() {
		$(this)[pluginName]();
		return false;
	});
})(jQuery);
