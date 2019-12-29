$(() => {
    $('.offers .offer-cnr').on('click', function() {
        const offerId = $(this).attr('data-offer-id');
        const offerCategoryId = $(this).attr('data-offer-category-id');
        window.location = '/offer/details/' + offerCategoryId + '/' + offerId;
    })
});