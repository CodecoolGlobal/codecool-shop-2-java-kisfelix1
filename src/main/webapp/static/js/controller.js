import {getResponse} from "./model.js";
import {addEventListener} from "./view.js";

function initialize(){
    addEventListener('#categories', loadFilteredProducts);
    addEventListener('#suppliers', loadFilteredProducts);
}

function loadFilteredProducts(e){
    console.log(e.target.value);
}

initialize();