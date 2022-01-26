import {getProductsFiltered} from "./model.js";
import {addEventListener, loadProducts} from "./view.js";

function initialize(){
    addEventListener('#categories', loadFilteredProducts);
    addEventListener('#suppliers', loadFilteredProducts);
}

async function loadFilteredProducts() {
    let categoryId = document.getElementById('categories').value;
    let supplierId = document.getElementById('suppliers').value;
    let productsFiltered = await getProductsFiltered(categoryId, supplierId);
    console.log(productsFiltered);
    loadProducts(productsFiltered);
}

initialize();