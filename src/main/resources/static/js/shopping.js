function initialize() {
}

function toggleClasses(element) {
    for(let i = 0; i < arguments.length; i++) {
        element.classList.toggle(arguments[i]);
    }
}

function toggleVisibility(element) {
    toggleClasses(element, "hidden", "visible");
}