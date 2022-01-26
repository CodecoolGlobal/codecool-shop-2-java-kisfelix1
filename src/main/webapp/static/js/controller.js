import {getCart, getProductsFiltered, sendProductToCart} from "./model.js";
import {addEventListener, showProducts, addEventListenerToAll, showCart} from "./view.js";

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
    showProducts(productsFiltered);
}

async function loadCart() {
    let cart = await getCart();
    showCart(cart);
}

async function addToCart(e) {
    const id = e.target.dataset.btnId;
    await sendProductToCart(id);
}

initialize();