export {getProductsFiltered, getCart, sendProductToCart};

function sendProductToCart(id){
    const url = `/api/add_cart?itemId=${id}`;
    return getResponse(url);
}

async function getResponse(url) {
    const response = await fetch(url);
    return response.json();
}

async function getProductsFiltered(categoryId, supplierId) {
    return getResponse(`/api/product?categoryId=${categoryId}&supplierId=${supplierId}`);
}

async function getCart() {
    return await getResponse("/api/review_cart");
}

