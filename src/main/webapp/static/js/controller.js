import {getResponse} from "./model.js";
import {addEventListener, addEventListenerToAll, loadCart, addToCart} from "./view.js";

function initialize(){
    addEventListener("#cart", loadCart)
    addEventListenerToAll(".cart-btn", addToCart)
}

initialize();