import {getProductsFiltered} from "./model.js";
import {addEventListener, loadProducts, addEventListenerToAll, addToCart, loadCart} from "./view.js";

function initialize(){
    addEventListener("#cart", loadCart)
    addEventListenerToAll(".cart-btn", addToCart)
    addEventListener('#categories', loadFilteredProducts);
    addEventListener('#suppliers', loadFilteredProducts);
}

async function loadFilteredProducts() {
    let categoryId = document.getElementById('categories').value;
    let supplierId = document.getElementById('suppliers').value;
    let productsFiltered = await getProductsFiltered(categoryId, supplierId);
    loadProducts(productsFiltered);
}

initialize();