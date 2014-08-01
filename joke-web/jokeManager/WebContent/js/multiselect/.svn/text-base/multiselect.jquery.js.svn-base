/**
 * Created with IntelliJ IDEA.
 * User: SJia
 * Date: 12-9-11
 * Time: 下午12:04
 * To change this template use File | Settings | File Templates.
 */
(function($, undefined){
	"use strict";
	$.fn.multiSelect = function(data,options){
		var opts = $.extend({}, $.fn.multiSelect.defaults, options);
		return this.each(function(){
			var $input = $(this);
			var $list;
			var $listItems;
			var totalCount = data.length;
			var $visibleInput = $input.clone(true).removeAttr('name').removeAttr('id').addClass('visible');
			$visibleInput.val(opts.selectNoneText);
			$input.after($visibleInput).hide();
			$list = createList(data);
			if(totalCount>8){
				$list.find('ul').css('height','200px');
			}else{
				$list.find('ul').css('height','auto');
			}
			$listItems = $list.find('li');
			setPosition($visibleInput[0],$list);
			$input.after($list);
			initEvent($list,$listItems,$visibleInput,$input);
		});

		/**
		 * 创建下拉列表
		 * @param data
		 * @return {*}
		 */
		function createList(data){
			var html = [];
			html.push('<div class="selectList" style="position: absolute;width: 200px;border: solid 1px #ccc;display: none;z-index: 9999;">');
			html.push('<div style="height:23px;line-height:23px;background-color: #e0e0e0;width: 100%;"><a href="javascript:;" class="hideList" style="float: right;padding:5px 0 0;height: 18px;" title="关闭"><img src="'+contextPath+'/js/multiselect/close.gif" alt="关闭" style="border: none;"></a><label><input class="selectAll" type="checkbox">全选</label></div>');
			html.push('<ul style="list-style: none;height: 200px;overflow-y: auto;padding: 0;margin: 0;background-color: #fff;border: solid 1px #E0E0E0;">');
			$.each(data,function(index,item){
				html.push('<li style="padding: 2px;border-bottom: solid 1px #e0e0e0;color: #666;font-size: 12px;font-family: Verdana,Arial,Helvetica,sans-serif;"><input type="checkbox" onclick="return true;" value="'+item.key+'">'+ (opts.setTextFunc?opts.setTextFunc(item.value):item.value)+'</li>');
			});
			html.push('</ul>');
			html.push('</div>');
			return $(html.join(''));
		}

		/**
		 * 初始化事件
		 * @param $list
		 * @param $listItems
		 * @param $visibleInput
		 * @param $input
		 */
		function initEvent($list,$listItems,$visibleInput,$input){
			$listItems.bind('click',function(){
				if($(this).hasClass('selected')){
					$(this).removeClass('selected').find('input').attr('checked',false);
					$list.find('input.selectAll').attr('checked',false);
				}else{
					$(this).addClass('selected').find('input').attr('checked',true);
					if($list.find('li input:not(":checked")').length === 0){
						$list.find('input.selectAll').attr('checked',true);
					}
				}
				setValue($list,$visibleInput,$input);
			});
			$listItems.hover(function(){
				$(this).addClass('hover');
			}).mouseleave(function(){
					$(this).removeClass('hover');
				});
			$list.find('input.selectAll').bind('click',function(){
				if($(this).attr('checked')){
					$listItems.addClass('selected').find('input').attr('checked',true);
				}else{
					$listItems.removeClass('selected').find('input').attr('checked',false);
				}
				setValue($list,$visibleInput,$input);
			});
			$list.find('a.hideList').bind('click',function(){
				$list.hide();
			});
			$visibleInput.bind('click',function(){
				$list.toggle();
				setPosition(this,$list);
			});
			$(document).bind('mouseup',function(event){
				if($(event.target).parents('div.selectList')[0] !== $list[0]&& event.target !== $visibleInput[0]){
					$list.hide();
				}
			});
		}

		/**
		 * 设置下拉列表位置
		 * @param input
		 * @param $list
		 */
		function setPosition(input,$list){
			var width = input.clientWidth+'px';
			var left = input.offsetLeft+'px';
			var top = input.offsetTop+input.offsetHeight-1+'px';
			$list.css({
				left:left,
				top:top,
				width:width
			});
		}

		/**
		 * 设值
		 * @param $list
		 * @param $visibleInput
		 * @param $input
		 * @param selected
		 */
		function setValue($list,$visibleInput,$input){
			var selected = [];
			$.each($list.find('li input:checked'),function(index,input){
				selected.push(input.value);
			});
			$input.val(selected);
			if(selected.length>0){
				$visibleInput.val('共选择'+selected.length+'项');
			}else{
				$visibleInput.val(opts.selectNoneText);
			}
		}
	};

	$.fn.multiSelect.defaults = {
		selectNoneText:'',
		multiple: true,
		setTextFunc: undefined
	};
})(jQuery);