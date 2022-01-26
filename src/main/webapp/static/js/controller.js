import {getProductsFiltered, sendEmailToBackend} from "./model.js";
import {addEventListener, loadProducts} from "./view.js";

async function initialize(){
    addEventListener('#categories', loadFilteredProducts);
    addEventListener('#suppliers', loadFilteredProducts);
    modalCloseOpen();
    modalPaymentChange();
    await doPayment();
}

async function loadFilteredProducts(e) {
    let categoryId = document.getElementById('categories').value;
    let supplierId = document.getElementById('suppliers').value;
    let productsFiltered = await getProductsFiltered(categoryId, supplierId);
    loadProducts(productsFiltered);
}

function modalCloseOpen() {
    let modal = document.getElementById("myModal");

    let btn = document.getElementById("myBtn");

    let span = document.getElementsByClassName("close")[0];

    btn.onclick = function() {
        modal.style.display = "block";
    }

    span.onclick = function() {
        modal.style.display = "none";
    }

    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }
}

function modalPaymentChange() {
    document.querySelector("#paypal-id").addEventListener("click", () => {
        document.querySelector("#paypal-container").classList.add("show");
        document.querySelector("#credit-container").classList.remove("show");
        document.querySelector("#paypal-id").classList.add("blue");
        document.querySelector("#creditcard-id").classList.remove("blue");
    })
    document.querySelector("#creditcard-id").addEventListener("click", () => {

        document.querySelector("#paypal-container").classList.remove("show");
        document.querySelector("#credit-container").classList.add("show");
        document.querySelector("#paypal-id").classList.remove("blue");
        document.querySelector("#creditcard-id").classList.add("blue");
    })
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