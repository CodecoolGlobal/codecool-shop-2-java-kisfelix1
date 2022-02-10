import {
    getCart,
    getProductsFiltered,
    sendProductToCart,
    sendEmailToBackend,
    editCartContent,
    checkCorrectLogin
} from "./model.js";
import {
    addEventListener,
    showProducts,
    addEventListenerToAll,
    showCart,
    editCartProductAmount,
    editTotalCartPrice
} from "./view.js";

async function initialize(){
    addEventListener("#cart", loadCart);
    addEventListenerToAll(".cart-btn", addToCart);
    addEventListener('#categories', loadFilteredProducts);
    addEventListener('#suppliers', loadFilteredProducts);
    addEventListener('#paypal-id', modalPaymentPaypalChange);
    addEventListener('#creditcard-id',modalPaymentCreditChange);
    addEventListener('#login-btn', loginUserCheck)
    modalCloseOpen();
    await doPayment();
}

async function loadFilteredProducts() {
    let categoryId = document.getElementById('categories').value;
    let supplierId = document.getElementById('suppliers').value;
    let productsFiltered = await getProductsFiltered(categoryId, supplierId);
    showProducts(productsFiltered);
    addEventListenerToAll(".cart-btn", addToCart);
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
    const paypalForm = document.querySelector("#paypal-form");
    const creditForm = document.querySelector("#credit-form");

    paypalForm.addEventListener("submit", () => {
        const email = document.querySelector("#paypal-email").value;
        sendEmailToBackend(email);
    })

    creditForm.addEventListener("submit", () => {
        const email = document.querySelector("#credit-email").value;
        sendEmailToBackend(email);
    })
}

export async function editCart(e){
    const value = Number(e.currentTarget.dataset.value)
    const id = e.currentTarget.parentElement.dataset.productId
    const quantity = Number(document.querySelector(`#amountId${id}`).textContent)
    await editCartContent("/api/cart/edit", {"id" : id, "amount": value})
    if (isProductRemoved(quantity, value)){
        await loadCart()
    } else{
        const defaultPrice = Number(document.querySelector(`#product-total${id}`).dataset.defaultPrice)
        const defaultCurrency = document.querySelector(`#product-total${id}`).dataset.defaultCurrency
        const total = Number(document.querySelector("#total-cart-price").dataset.total)
        editCartProductAmount(value, quantity, id, defaultCurrency, defaultPrice)
        editTotalCartPrice(value, total, defaultPrice, defaultCurrency)
    }
}

function isProductRemoved(quantity, value){
    return quantity + value < 1
}

async function loginUserCheck() {
    const email = document.querySelector("#login-email").value
    const password = document.querySelector("#login-password").value
    let databaseLogin = await checkCorrectLogin("/api/user/login", { "email" : email, "password" : password})
    if (databaseLogin == null) {
        document.getElementById("login-error").innerText = "Email or Password was incorrect! Please try again!"
    } else {
        console.log(databaseLogin.id)
        localStorage.setItem("user_id", databaseLogin.id)
        localStorage.setItem("user_name", databaseLogin.name)
        checkUserInSession()
    }

}

function checkUserInSession() {
    if (localStorage.getItem("user_id") != null) {
        document.getElementById("login").style.display = 'none'
        document.getElementById("register").style.display = 'none'
    } else {
        document.getElementById("login").style.display = 'block'
        document.getElementById("register").style.display = 'block'
    }
}

await initialize();