import {getProductsByCategoryId} from "./model.js";
import {addEventListener, loadProducts} from "./view.js";

function initialize(){
    addEventListener('#categories', loadFilteredProducts);
    addEventListener('#suppliers', loadFilteredProducts);
}

async function loadFilteredProducts(e) {
    let products = await getProductsByCategoryId(e.target.value);
    loadProducts(products);
}

initialize();