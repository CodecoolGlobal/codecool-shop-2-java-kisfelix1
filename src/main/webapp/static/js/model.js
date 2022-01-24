export {getResponse};

async function getResponse(url) {
    const response = await fetch(url);
    return response.json();
}