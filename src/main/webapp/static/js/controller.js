import {getCart, getProductsFiltered, sendProductToCart, sendEmailToBackend} from "./model.js";
import {addEventListener, showProducts, addEventListenerToAll, showCart} from "./view.js";

async function initialize(){
    addEventListener("#cart", loadCart);
    addEventListenerToAll(".cart-btn", addToCart);
    addEventListener('#categories', loadFilteredProducts);
    addEventListener('#suppliers', loadFilteredProducts);
    addEventListener('#paypal-id', modalPaymentPaypalChange);
    addEventListener('#creditcard-id',modalPaymentCreditChange);
    modalCloseOpen();
    await doPayment();
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

function modalCloseOpen() {
    let paymentModal = document.getElementById("payment-modal");
    let cartCheckoutButton = document.getElementById("cart-checkout");
    let span = document.getElementsByClassName("close")[0];

    cartCheckoutButton.onclick = function() {
        paymentModal.style.display = "block";
    }
    span.onclick = function() {
        paymentModal.style.display = "none";
    }
    window.onclick = function(event) {
        if (event.target === paymentModal) {
            paymentModal.style.display = "none";
        }
    }
}
function modalPaymentPaypalChange() {
    document.querySelector("#paypal-container").classList.add("show");
    document.querySelector("#credit-container").classList.remove("show");
    document.querySelector("#paypal-id").classList.add("blue");
    document.querySelector("#creditcard-id").classList.remove("blue");
}

function modalPaymentCreditChange(){
    document.querySelector("#paypal-container").classList.remove("show");
    document.querySelector("#credit-container").classList.add("show");
    document.querySelector("#paypal-id").classList.remove("blue");
    document.querySelector("#creditcard-id").classList.add("blue");
}

async function doPayment() {
    const paypalForm = document.querySelector("#paypal-form")
    const creditForm = document.querySelector("#credit-form")

    paypalForm.addEventListener("submit", () => {
        const email = document.querySelector("#paypal-email").value
        sendEmailToBackend(email);
    })

    creditForm.addEventListener("submit", () => {
        const email = document.querySelector("#credit-email").value
        sendEmailToBackend(email);
    })
}

await initialize();