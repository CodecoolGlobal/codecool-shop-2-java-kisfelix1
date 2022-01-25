import {getResponse} from "./model.js";
import {addEventListener, addEventListenerToAll, loadCart} from "./view.js";

function initialize(){
    addEventListener("cart", loadCart)
    addEventListenerToAll("cart-btn", addToCart)
}

initialize();