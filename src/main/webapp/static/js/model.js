export {getProductsByCategoryId};

async function getResponse(url) {
    const response = await fetch(url);
    return response.json();
}

async function getProductsByCategoryId(id) {
    return getResponse(`/get/category/${id}`);
}

