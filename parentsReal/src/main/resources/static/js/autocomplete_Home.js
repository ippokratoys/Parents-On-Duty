
 var input = document.getElementById('autocomplete');
  google.maps.event.addDomListener(input, 'keydown', function(event) {
    if (event.keyCode === 13) {
        event.preventDefault();
    }
  });

      function initAutocomplete() {
        // Create the autocomplete object, restricting the search to geographical
        // location types.
        autocomplete = new google.maps.places.Autocomplete(
            /** @type {!HTMLInputElement} */(document.getElementById('autocomplete')),
            {types: ['geocode']});

        // When the user selects an address from the dropdown, populate the address
        // fields in the form.
        autocomplete.addListener('place_changed', geocodeAddress);
      }


	function geocodeAddress() {
  	    var address = document.getElementById('autocomplete').value;
		var geocoder = new google.maps.Geocoder();
  	     geocoder.geocode({'address': address}, function(results, status) {
       	  	 if (status === 'OK') {
    			document.getElementById("lng").value =results[0].geometry.location.lng();
                document.getElementById("lat").value =results[0].geometry.location.lat();

           	 } else {
             	 var a=1;
              }
       	  });
      	}

