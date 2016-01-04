
//WORK ON THIS LATER
// the semi-colon before function invocation is a safety net against concatenated
// scripts and/or other plugins which may not be closed properly.
;(function ( $, window, document, undefined ) {

	"use strict";

		// undefined is used here as the undefined global variable in ECMAScript 3 is
		// mutable (ie. it can be changed by someone else). undefined isn't really being
		// passed in so we can ensure the value of it is truly undefined. In ES5, undefined
		// can no longer be modified.

		// window and document are passed through as local variable rather than global
		// as this (slightly) quickens the resolution process and can be more efficiently
		// minified (especially when both are regularly referenced in your plugin).

		// Create the defaults once
		var pluginName = "motivosityEmoji",
				defaults = {
				propertyName: "value"
		};

		// The actual plugin constructor
		function Plugin ( element, options ) {
				this.element = element;
				// jQuery has an extend method which merges the contents of two or
				// more objects, storing the result in the first object. The first object
				// is generally empty as we don't want to alter the default options for
				// future instances of the plugin
				this.settings = $.extend( {}, defaults, options );
				this._defaults = defaults;
				this._name = pluginName;
				this.init();
		}

		// Avoid Plugin.prototype conflicts
		$.extend(Plugin.prototype, {
				init: function () {
						// Place initialization logic here
						// You already have access to the DOM element and
						// the options via the instance, e.g. this.element
						// and this.settings
						// you can add more functions like the one below and
						// call them like so: this.yourOtherFunction(this.element, this.settings).
						var that = this;
						var face = $('<div class="smileytrigger" style="cursor:pointer;" rel="popover">😀</div>');
						var checkExist = $(this.element).prev().hasClass('smileytrigger');
						if(!checkExist){
							$(this.element).parent().css('position','relative');
							$(face).insertBefore(this.element);
						}else{
							face = $(this.element).prev();
						}

						$(face).popover({
							html: true,
							trigger: 'manual',
							placement: 'auto bottom',
							content: that.emoticonDom()
						}).click(function() {
					        $(this).popover('toggle');
					    }).on('shown.bs.popover',function(){
					    	$('#'+ $(this).attr('aria-describedby')).css('margin-left','0px').css('margin-top','10px');
					    });
					    $(document).off('click','.mvEmoji');
					    $(document).on('click','.mvEmoji',function(){
							var emojiChar = $(this).html();
							var queryElement = that.element;
							if(that.element.id != 'thanksInput'){
								queryElement = document.querySelectorAll('#appreciationForm\\:thanksInput')[0];
							}
							that.insertAtCursor(queryElement,emojiChar);

							if(typeof(angular) != 'undefined'){
								angular.element(document.querySelectorAll('#thanksInput')).triggerHandler('change');
							}
						});

						$(document).mouseup(function(e){
							var containerpop = $($(face).popover('getContent')[0]).next();
							if (!containerpop.is(e.target) // if the target of the click isn't the container...
							        && containerpop.has(e.target).length === 0) // ... nor a descendant of the container
						    {
						    	$(face).popover('hide');
						    	return true; //clicked outside

						    }
						});
				},
				insertAtCursor: function(inputField, myValue) {
					if (document.selection) {
				      //For browsers like Internet Explorer
				      inputField.focus();
				      var sel = document.selection.createRange();
				      sel.text = myValue;
				      inputField.focus();
				    }
				    else if (inputField.selectionStart || inputField.selectionStart == '0') {
				      //For browsers like Firefox and Webkit based
				      var startPos = inputField.selectionStart;
				      var endPos = inputField.selectionEnd;
				      var scrollTop = inputField.scrollTop;
				      inputField.value = inputField.value.substring(0, startPos)+myValue+inputField.value.substring(endPos,inputField.value.length);
				      inputField.focus();
				      inputField.selectionStart = startPos + myValue.length;
				      inputField.selectionEnd = startPos + myValue.length;
				      inputField.scrollTop = scrollTop;
				    } else {
				      inputField.focus();
				      inputField.value += myValue;
				    }
				},
				emoticonDom: function(){
					var emoticonHtml="";
						emoticonHtml += "<div class=\"grid row-wrap fiveAcross\">";
						emoticonHtml += "			<div class=\"grid-cell mvEmoji\">😀<\/div>";
						emoticonHtml += "			<div class=\"grid-cell mvEmoji\">😊<\/div>";
						emoticonHtml += "			<div class=\"grid-cell mvEmoji\">😂<\/div>";
						emoticonHtml += "			<div class=\"grid-cell mvEmoji\">😉<\/div>";
						emoticonHtml += "			<div class=\"grid-cell mvEmoji\">😆<\/div>";
						emoticonHtml += "		<\/div>";
						emoticonHtml += "		<div class=\"grid row-wrap fiveAcross\">";
						emoticonHtml += "			<div class=\"grid-cell mvEmoji\">😋<\/div>";
						emoticonHtml += "			<div class=\"grid-cell mvEmoji\">😎<\/div>";
						emoticonHtml += "			<div class=\"grid-cell mvEmoji\">😍<\/div>";
						emoticonHtml += "			<div class=\"grid-cell mvEmoji\">😘<\/div>";
						emoticonHtml += "			<div class=\"grid-cell mvEmoji\">😇<\/div>";
						emoticonHtml += "		<\/div>";
						emoticonHtml += "		<div class=\"grid row-wrap fiveAcross\">";
						emoticonHtml += "			<div class=\"grid-cell mvEmoji\">😐<\/div>";
						emoticonHtml += "			<div class=\"grid-cell mvEmoji\">😑<\/div>";
						emoticonHtml += "			<div class=\"grid-cell mvEmoji\">😏<\/div>";
						emoticonHtml += "			<div class=\"grid-cell mvEmoji\">😣<\/div>";
						emoticonHtml += "			<div class=\"grid-cell mvEmoji\">😜<\/div>";
						emoticonHtml += "		<\/div>";
						emoticonHtml += "		<div class=\"grid row-wrap fiveAcross\">";
						emoticonHtml += "			<div class=\"grid-cell mvEmoji\">😝<\/div>";
						emoticonHtml += "			<div class=\"grid-cell mvEmoji\">😖<\/div>";
						emoticonHtml += "			<div class=\"grid-cell mvEmoji\">😲<\/div>";
						emoticonHtml += "			<div class=\"grid-cell mvEmoji\">😟<\/div>";
						emoticonHtml += "			<div class=\"grid-cell mvEmoji\">😭<\/div>";
						emoticonHtml += "		<\/div>";
						emoticonHtml += "		<div class=\"grid row-wrap fiveAcross\">";
						emoticonHtml += "			<div class=\"grid-cell mvEmoji\">😫<\/div>";
						emoticonHtml += "			<div class=\"grid-cell mvEmoji\">😱<\/div>";
						emoticonHtml += "			<div class=\"grid-cell mvEmoji\">😳<\/div>";
						emoticonHtml += "			<div class=\"grid-cell mvEmoji\">😡<\/div>";
						emoticonHtml += "			<div class=\"grid-cell mvEmoji\">👿<\/div>";
						emoticonHtml += "<\/div>";
						return emoticonHtml;
				}

		});

		// A really lightweight plugin wrapper around the constructor,
		// preventing against multiple instantiations
		$.fn[ pluginName ] = function ( options ) {
				return this.each(function() {
						if ( !$.data( this, "plugin_" + pluginName ) ) {
								$.data( this, "plugin_" + pluginName, new Plugin( this, options ) );
						}
				});
		};

})( jQuery, window, document );
