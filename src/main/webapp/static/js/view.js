export {addEventListener};

function addEventListener(selector, func){
    document.querySelector(selector).addEventListener('click', func);
}

