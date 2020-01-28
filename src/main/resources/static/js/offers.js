$(() => {
    $('.offers .offer-cnr').on('click', function() {
        const offerId = $(this).attr('data-offer-id');
        window.location = '/offer/details/' + offerId;
    })
});