export {addEventListener,addEventListenerToAll, showProducts, showCart};

function addEventListener(selector, func){
    document.querySelector(selector).addEventListener('click', func);
}

function loadContent(selector, content){
    document.querySelector(selector).innerHTML = content;
}

function showProducts(products){
    loadContent('#products',products.map(product => {return buildCard(product)}).join(''));
}

function showCart(cart){
    let cartContent = cart.map(prod => {
        return `<tr>
            <td class="w-25">
                <img src="/static/img/product_${prod.id}.jpg"  alt="${prod.name} + '.jpg'" class="img-fluid img-thumbnail" >
            </td>
            <td>${prod.name}</td>
            <td>${prod.defaultPrice} ${prod.defaultCurrency}</td>
            <td class="qty"><p id=${"amountId" + prod.id} type="text" class="amount form-control" >${prod.amount}</p></td>
            <td id=${"totalId" + prod.id} data-default-price=${prod.defaultPrice} data-default-currency=${prod.defaultCurrency}>${prod.defaultPrice * prod.amount} ${prod.defaultCurrency}</td>
            <td data-product-id=${prod.id}>
                <h2 data-amount="1" class="edit">+</h2>
                <h2 data-amount="-1" class="edit"> -</h2>
            </td>
        </tr>`
    }).join('');
    loadContent("#cart-body", cartContent);
}

function buildCard(product){
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
                            <a class="btn btn-success" href="#">Add to cart</a>
                        </div>
                    </div>
                </div>
            </div>`;
}

function addEventListenerToAll(selector, func) {
    const elements = document.querySelectorAll(selector)
    for (let i = 0; i < elements.length; i++) {
        elements[i].addEventListener('click', func)
    }
}


