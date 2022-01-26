export {getProductsFiltered};

async function getResponse(url) {
    const response = await fetch(url);
    return response.json();
}

async function getProductsFiltered(categoryId, supplierId) {
    return getResponse(`/api/product?categoryId=${categoryId}&supplierId=${supplierId}`);
}

