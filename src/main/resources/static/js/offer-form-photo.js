const URLS = {
    sizes: '/api/shoe-sizes',
    types: '/api/shoe-types',
};

$(() => {
    fetch(URLS.sizes)
        .then(sizes => sizes.json())
        .then(sizes => {
            console.log(sizes);
            for (let i = 0; i < sizes.length; i++) {
                const typeSelect = $('#shoeSizes');
                console.log(typeSelect);
                $('<option>')
                    .val(sizes[i])
                    .html(i + 35)
                    .appendTo(typeSelect)
            }
        });

    fetch(URLS.types)
        .then(types => types.json())
        .then(types => {
            console.log(types);
            for (let i = 0; i < types.length; i++) {
                const typeSelect = $('#shoeTypes');
                console.log(typeSelect);
                $('<option>')
                    .val(types[i])
                    .html(types[i])
                    .appendTo(typeSelect)
            }
        });
});