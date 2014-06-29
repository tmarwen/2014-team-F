/*!
 * datepair.js v0.2.1 - A javascript plugin for intelligently selecting date and time ranges inspired by Google Calendar.
 * Copyright (c) 2014 Jon Thornton - http://jonthornton.github.com/Datepair.js
 * License: MIT
 */

(function($) {

	////////////
	// Plugin //
	////////////

	$.fn.datepair = function(option) {
		var out;
		this.each(function() {
			var $this = $(this);
			var data = $this.data('datepair');
			var options = typeof option === 'object' && option;

			if (!data) {
				data = new Datepair(this, options);
				$this.data('datepair', data);
			}

			if (typeof option === 'string') {
				out = data[option]();
			}
		});

		return out;
	};

	//////////////
	// Data API //
	//////////////

	$('[data-datepair]').each(function() {
		var $this = $(this);
		$this.datepair($this.data());
	});

})(jQuery);