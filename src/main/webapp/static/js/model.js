export {getProductsFiltered, sendEmailToBackend};

async function getResponse(url) {
    const response = await fetch(url);
    return response.json();
}

async function sendResponse(url, data) {
    await fetch(url, {
        method: "POST",
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(data)
})}

async function getProductsFiltered(categoryId, supplierId) {
    return getResponse(`/api/product?categoryId=${categoryId}&supplierId=${supplierId}`);
}

async function sendEmailToBackend(email) {
    await sendResponse("/api/sendEmail", email)
}
