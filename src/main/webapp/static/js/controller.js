import {getCategoriesById, getProductsByCategoryId, getResponse} from "./model.js";
import {addEventListener} from "./view.js";

function initialize(){
    addEventListener('#categories', loadFilteredProducts);
    addEventListener('#suppliers', loadFilteredProducts);
}

function loadFilteredProducts(e){
    let products = getProductsByCategoryId(e.target.value);
}

initialize();