const OFFERS = {none: ''};

OFFERS["1"] = `
    <section class="">
        <select id="shoeTypes" name="type">
        <option value="SPRING_AUTUMN">SPRING_AUTUMN</option>
        <option value="SUMMER">SUMMER</option>
        <option value="WINTER">WINTER</option>
        <option value="SNEAKERS">SNEAKERS</option>
        <option value="BOOTS">BOOTS</option>
        <option value="OTHER">OTHER</option></select>
    </section>

    <section class="">
        <select id="shoeSizes" name="sizes" multiple="">
        <option value="THIRTY_FIVE">35</option>
        <option value="THIRTY_SIX">36</option>
        <option value="THIRTY_SEVEN">37</option>
        <option value="THIRTY_EIGHT">38</option>
        <option value="THIRTY_NINE">39</option>
        <option value="FOURTY">40</option>
        <option value="FOURTY_ONE">41</option>
        <option value="FOURTY_TWO">42</option>
        <option value="FOURTY_THREE">43</option>
        <option value="FOURTY_FOUR">44</option>
        <option value="FOURTY_FIVE">45</option>
        <option value="FOURTY_SIX">46</option>
        <option value="FOURTY_SEVEN">47</option>
        <option value="FOURTY_EIGHT">48</option>
        <option value="FOURTY_NINE">49</option>
        <option value="FIFTY">50</option></select>
    </section>
`;

OFFERS["2"] = `
    <section class="">
        <input type="text" name="matter" placeholder="matter">
    </section>
`;


$(() => {
    const form = $('#offerForm');
    const additionalData = $('#offerData');
    const categorySelect = $('#categoryId');

    const loadDataFunc = function () {
        const url = '/offer/create/' + this.value;
        form.attr('action', url);
        additionalData.fadeOut(0);
        additionalData.html(OFFERS[this.value]);
        additionalData.fadeIn(300);
    };

    categorySelect.on('change', loadDataFunc);
});