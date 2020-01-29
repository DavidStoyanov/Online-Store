(function ($) {
    const URLS = {
        create: '/api/offer/create'
    };

    const CATEGORIES = {
        none: ''
    };

    CATEGORIES['1'] = `
        <section>
            <label class="label-flex">
                <span>Season:</span>
                <input class="input input-attr" id="season" type="text" name="attributes_season" />
            </label>
        </section>
    `;

    CATEGORIES['2'] = `
        <section>
            <label class="label-flex">
                <span>Matter:</span>
                <input class="input input-attr" id="matter" type="text" name="attributes_matter" />
            </label>
        </section>
    `;

    const createOffer = (data) => {
        return fetch(URLS.create, {
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            method: 'POST',
            body: data
        })
        .then((response) => response.json())
        .then((result) => {
            return result;
        })
        .catch((error) => {
            return error;
        });
    };

    $(() => {
        const container = $('#container');
        const categorySelect = $('#categoryId');
        const additionalData = $('#offerData');
        const submitBtn = $('#submitBtn');

        categorySelect.on('change', loadCategoryInputs);
        submitBtn.on('click', function (event) {
            event.preventDefault();
            $(this).attr("disabled", true);

            const form = container.find('#form').get(0);
            let jsonData = formToJSON(form);
            jsonData = modifyDataAttr(jsonData);
            let inputPhotosData = $('#photos').val();
            if (inputPhotosData.length === 0) inputPhotosData = "[]";
            jsonData.photos = JSON.parse(inputPhotosData);
            const data = JSON.stringify(jsonData);

            createOffer(data)
                .then(response => {
                    // Offer not found
                    if (response.status < 200 || response.status >= 300) {
                        return;
                    }

                    // Redirect to new offer location
                    if (response.location !== null &&
                        response.location !== undefined) {
                        window.location = response.location;
                    }
                })
                .catch(error => {
                    console.warn(error);
                });
        });

        function loadCategoryInputs() {
            additionalData.fadeOut(0);
            const categoryId = categorySelect.val();
            additionalData.html(CATEGORIES[categoryId]);
            additionalData.fadeIn(0);
        }

        function formToJSON(elem) {
            let output = {};
            new FormData( elem ).forEach(
                ( value, key ) => {
                    // Check if property already exist
                    if ( Object.prototype.hasOwnProperty.call( output, key ) ) {
                        let current = output[ key ];
                        if ( !Array.isArray( current ) ) {
                            // If it's not an array, convert it to an array.
                            current = output[ key ] = [ current ];
                        }
                        current.push( value ); // Add the new value to the array.
                    } else {
                        output[ key ] = value;
                    }
                }
            );

            return output;
        }

        function modifyDataAttr(data) {
            const output = data;
            const attributes = new Map();
            for (const key in output) {
                if (output.hasOwnProperty(key)) {
                    // Attribute separator
                    if (key.includes('_')) {
                        const attributeGroup = key.substr(0, key.indexOf('_'));
                        const attribute = key.substr(attributeGroup.length + 1);

                        // Add attribute group if not exist
                        if (!attributes.has(attributeGroup)) {
                            attributes.set(attributeGroup, {});
                        }

                        // Add attribute to attribute group and delete it form object
                        attributes.get(attributeGroup)[attribute] = output[key];
                        delete output[key];
                    }
                }
            }

            attributes.forEach((value, key) => {
                output[key] = value;
            });

            return output;
        }
    });

})(jQuery);