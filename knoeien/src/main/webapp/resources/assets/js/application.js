!function($) {	
	$(document).foundation();
	dateSelect();
	
	$(window).scroll(function() {
		var height = $(window).scrollTop();
		if(height  > 64) {
			$('.top-bar .menu').addClass('top-bar-color');
		}
		if(height  <= 64) {
			$('.top-bar .menu').removeClass('top-bar-color');
		}
	});
	
	$(document).on('click tap touchstart', '.reveal .modal-close', function() {
		return $('[data-reveal="data-reveal"]').foundation('close');
	});
	
	var scrolling = true;
	$(".scroll-left").bind("mouseover", function(e){
		e.preventDefault();
		scrolling = true;
		scrollContent("left");
	}).bind("mouseout", function(event) {
	    scrolling = false;
	});
	
	$(".scroll-right").bind("mouseover", function(e){
		e.preventDefault();
	  scrolling = true;
	  scrollContent("right");
	}).bind("mouseout", function(event) {
	    scrolling = false;
	});
	
	function scrollContent(direction) {
	    var amount = (direction === "left" ? "-=10px" : "+=10px");
	    $(".job-type-wraper").animate({
	    		scrollLeft: amount
	    }, 1, function() {
	        if (scrolling) {
	            scrollContent(direction);
	        }
	    });
	};
	
}(jQuery);

/* Generate date, month and year select options */
function dateSelect() {
	var monthNames = ["January", "February", "March", "April", "May", "June","July", "August", "September", "October", "November", "December"];
	var monthNamesShort = ["Jan", "Feb", "Mar", "Apr", "May", "Jun","Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];
	var currentDate = new Date;
	
	/* Target date input dd-mm-yyyy (2 digit - 2 digit - 4 digit) */
	for(var i=1; i <= 100; i++) {
		var valueText = ("0"+i).slice(-2);
		var year = currentDate.getFullYear() - i + 1;
		var futureYear = currentDate.getFullYear() + i - 1;
		
		/* Set dates to day drop-down list */
		if(i <= 31) {
			$('select.day-select').append($('<option>', {
				value: valueText,
				text: valueText
			}));
		}
		
		/* Set month names to month drop-down list */
		if(i <= 12) {
			/* Show month numbers */
			$('select.month-select').append($('<option>', {
				value: valueText,
				text: valueText
			}));
			/* Show short month names */
			$('select.short-month-select').append($('<option>', {
				value: valueText,
				text: monthNamesShort[i-1]
			}));
			/* Show full month names */
			$('select.full-month-select').append($('<option>', {
				value: valueText,
				text: monthNames[i-1]
			}));
		}
		
		/* Set year to year drop-down list */
		$('select.year-select').append($('<option>', {
			value: year,
			text: year
		}));
		
		/* Set futureYear to year drop-down list */
		$('select.future-year-select').append($('<option>', {
			value: futureYear,
			text: futureYear
		}));
	}
};