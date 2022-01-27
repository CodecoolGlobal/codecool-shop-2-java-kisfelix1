export {addEventListener, addEventListenerToAll, showProducts, showCart};

function addEventListener(selector, func) {
    document.querySelector(selector).addEventListener('click', func);
}

function loadContent(selector, content) {
    document.querySelector(selector).innerHTML = content;
}

function showProducts(products) {
    let productContent = products.map(product => {
        return buildCard(product);
    }).join('');
    loadContent('#products', productContent);
}

function buildCard(product) {
    return `<div class="col col-sm-12 col-md-6 col-lg-4">
                <div class="card">
                    <img class="" src="/static/img/product_${product.id}.jpg" alt="http://placehold.it/400x250/000/fff"/>
                    <div class="card-header">
                        <h4 class="card-title">${product.name}</h4>
                        <p class="card-text">${product.description}</p>
                    </div>
                    <div class="card-body">
                        <div class="card-text">
                            <p class="lead">${product.defaultPrice} ${product.defaultCurrency}</p>
                        </div>
                        <div class="card-text">
                            <a class="btn btn-success">Add to cart</a>
                        </div>
                    </div>
                </div>
            </div>`;
}

function showCart(cart) {
    let cartContent = cart.map(prod => {
        return buildCart(prod);
    }).join('');
    loadContent("#cart-body", cartContent);
}

function buildCart(product) {
    return `<tr>
            <td class="w-25">
                <img src="/static/img/product_${product.id}.jpg"  alt="${product.name} + '.jpg'" class="img-fluid img-thumbnail" >
            </td>
            <td>${product.name}</td>
            <td>${product.defaultPrice} ${product.defaultCurrency}</td>
            <td class="qty"><p id=${"amountId" + product.id} type="text" class="amount form-control" >${product.amount}</p></td>
            <td id=${"totalId" + product.id} data-default-price=${product.defaultPrice} data-default-currency=${product.defaultCurrency}>${product.defaultPrice * product.amount} ${product.defaultCurrency}</td>
            <td data-product-id=${product.id}>
                <h2 data-amount="1" class="edit">+</h2>
                <h2 data-amount="-1" class="edit"> -</h2>
            </td>
        </tr>`
}

function addEventListenerToAll(selector, func) {
    const elements = document.querySelectorAll(selector)
    for (let i = 0; i < elements.length; i++) {
        elements[i].addEventListener('click', func)
    }
}


