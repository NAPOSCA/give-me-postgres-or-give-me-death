function initialize() {
    const manyCategoryItemsDivs = document.querySelectorAll(".category-items");
	for (let i = 0; i < manyCategoryItemsDivs.length; i++) {
		const categoryDiv = manyCategoryItemsDivs[i];
		const categorySection = categoryDiv.querySelector(".category");
		const accordionIcons = categorySection.querySelectorAll(".icon.accordion");
		const items = categoryDiv.querySelector(".items");
		categorySection.addEventListener("click", () => {
			toggleVisibility(items);
			accordionIcons.forEach(indicator => {
				toggleClasses(indicator, "collapsed", "expanded");
			});
		});
	}
	const dichotomousProductButtons = document.querySelectorAll(".icon.dichotomous-product");
	for (let i = 0; i < dichotomousProductButtons.length; i++) {
		const dichotomousProductButton = dichotomousProductButtons[i];
		dichotomousProductButton.addEventListener("click", () => {
			toggleClasses(dichotomousProductButton, "plus", "x");
		});
	}
}

function toggleClasses(element) {
    for(let i = 1; i < arguments.length; i++) {
        element.classList.toggle(arguments[i]);
    }
}

function toggleVisibility(element) {
    toggleClasses(element, "hidden", "visible");
}